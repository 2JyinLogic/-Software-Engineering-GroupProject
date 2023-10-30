package edu.cpt202.group9.projb.user;

import edu.cpt202.group9.projb.appointment.Appointment;
import edu.cpt202.group9.projb.appointment.AppointmentServices;
import edu.cpt202.group9.projb.appointment.AppointmentServicesImpl;
import edu.cpt202.group9.projb.groomer.Groomer;
import edu.cpt202.group9.projb.groomer.GroomerService;
import edu.cpt202.group9.projb.home.HomeController;
import edu.cpt202.group9.projb.pet.Pet;
import edu.cpt202.group9.projb.pet.PetServicesImpl;
import edu.cpt202.group9.projb.review.Review;
import edu.cpt202.group9.projb.review.ReviewService;
import edu.cpt202.group9.projb.security.Account;
import edu.cpt202.group9.projb.sellingStrategy.CrossSellingStrategy;
import edu.cpt202.group9.projb.sellingStrategy.CrossServices;
import edu.cpt202.group9.projb.sellingStrategy.UpSellingStrategy;
import edu.cpt202.group9.projb.sellingStrategy.UpService;
import edu.cpt202.group9.projb.service.Service;
import edu.cpt202.group9.projb.service.ServiceServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Handle most of the user's pages
 *
 * @author Kaijie Lai, Guanyuming He
 * @since 2023.4.5
 * @version 2023.5.3
 */
@Controller
public class UserController {
    @Autowired
    private UserServices userServices;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private GroomerService groomerService;
    @Autowired
    private ServiceServices serviceServices;
    @Autowired
    private AppointmentServices appointmentService;
    @Autowired
    private UpService upService;
    @Autowired
    private HomeController homeController;
    @Autowired
    private AppointmentServicesImpl appointmentServices;
    @Autowired
    private PetServicesImpl petservices;

//    @GetMapping("")
//    public String viewHomePage(Model model) {
//        return "main_and_help_page/mainpage_user";
//    }
    @Autowired
    private CrossServices crossService;

    @GetMapping("/appointment")
    public String viewAppointmentPage(Model model) {
        List<Groomer> groomerList = groomerService.findAllGroomers();
        List<Service> serviceList = serviceServices.findAllServices();
        model.addAttribute("groomerList", groomerList);
        model.addAttribute("serviceList", serviceList);

        return "appointment/appointmentpage";
    }

    /**
     * Handle appointment submission
     * @param petName
     * @param petBreed
     * @param petAge
     * @param petDescription
     * @param petSize
     * @param serviceName
     * @param date
     * @param employeeId
     * @param auth
     * @param model
     * @return
     */
    @PostMapping("/appointment")
    public String addAppointment(@RequestParam String petName,
                                 @RequestParam String petBreed,
                                 @RequestParam Integer petAge,
                                 @RequestParam String petDescription,
                                 @RequestParam String petSize,
                                 @RequestParam String serviceName,
                                 @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") String date,
                                 @RequestParam Long employeeId,
                                 Authentication auth,
                                 //RedirectAttributes redirectAttributes,
                                 Model model)
    {
        String error = "\"At least one field is empty.\"";

        if (petName == null ||
            petBreed == null ||
            petAge == null  ||
            petDescription == null ||
            petSize == null ||
            serviceName == null ||
            date == null ||
            employeeId == null
        ) {
            return "redirect:/appointment?error=" + error;
        }

        var selectedServ = serviceServices.findServiceByName(serviceName);
        if(!selectedServ.isPresent()) {
            error = "\"The service name cannot be found.\"";
            return "redirect:/appointment?error=" + error;
        }
        var selectedGroomer = groomerService.findGroomerByEmployeeId(employeeId);
        if(!selectedGroomer.isPresent()) {
            error = "\"The service name cannot be found.\"";
            return "redirect:/appointment?error=" + error;
        }
        Service validService = selectedServ.get();
        Groomer validGroomer = selectedGroomer.get();

        /* check if date is in one of the groomer's schedules */
        {
            boolean dateValid = false;

            for(var day : validGroomer.getScheduleList()) {
                if(day.getDay().equals(date)) {
                    dateValid = true;
                    break;
                }
            }

            if(!dateValid) {
                error = "\"The date you provided is not in the schedules of the groomer you selected. Check the groomer schedules using the link.\"";
                return "redirect:/appointment?error=" + error;
            }
        }

        // parse date string to date
        Date d;
        try {
            d = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        }
        catch(ParseException e) {
            error = "\"Unexpected error happened while parsing date. Please try again.\"";
            return "redirect:/appointment?error=" + error;
        }

        // get currently logged in user.
        Account a = Account.getAuthenticatedAccount(auth);
        var optUsr = userServices.findUserByAccount(a);
        if(optUsr.isEmpty()) {
            error = "\"Unexpected error: cannot get current user information. Please try again.\"";
            return "redirect:/appointment?error=" + error;
        }

        appointmentService.saveAppointment(new Appointment(d, "In Progress", validService.getServicePrice(), optUsr.get(), validGroomer, validService));

        String upSellingString = "You have already chosen the most premium service of the same type. Congratulations!";
        var up = tryGetExistingUpSellingStrategy(validService.getServiceName());
        if(up.isEmpty()) { // there is no exising one
            up = generateUpSellingStrategy(validService);
        }
        if(up.isEmpty()) { // there is no existing one and cannot be one
            // do nothing
        }
        else {
            upSellingString = "You can upgrade your service to a better one of the same type: " + up.get().getHighServiceName();
        }

        String crossSellingString = "Seems the service you have chosen is very complete. Congratulations!";
        var cross = tryGetExistingCrossSellingStrategy(validService.getServiceName());
        if(cross.isEmpty()) { // there is no exising one
            cross = generateCrossSellingStrategy(validService);
        }
        if(cross.isEmpty()) { // there is no existing one and cannot be one
            // do nothing
        }
        else {
            var cr = cross.get();
            crossSellingString = "You can try similar services: ";

            if(cr.getServiceA() != null) {
                var serv = serviceServices.findServiceByName(cr.getServiceA());
                assert serv.isPresent();
                if(!serv.get().getServiceType().equals(validService.getServiceType())) {
                    crossSellingString += cr.getServiceA() + " ";
                }
            }
            if(cr.getServiceB() != null) {
                var serv = serviceServices.findServiceByName(cr.getServiceB());
                assert serv.isPresent();
                if(!serv.get().getServiceType().equals(validService.getServiceType())) {
                    crossSellingString += cr.getServiceB() + " ";
                }
            }
            if(cr.getServiceC() != null) {
                var serv = serviceServices.findServiceByName(cr.getServiceC());
                assert serv.isPresent();
                if(!serv.get().getServiceType().equals(validService.getServiceType())) {
                    crossSellingString += cr.getServiceC() + " ";
                }
            }
            if(cr.getServiceD() != null) {
                var serv = serviceServices.findServiceByName(cr.getServiceD());
                assert serv.isPresent();
                if(!serv.get().getServiceType().equals(validService.getServiceType())) {
                    crossSellingString += cr.getServiceD() + " ";
                }
            }
            if(cr.getServiceE() != null) {
                var serv = serviceServices.findServiceByName(cr.getServiceE());
                assert serv.isPresent();
                if(!serv.get().getServiceType().equals(validService.getServiceType())) {
                    crossSellingString += cr.getServiceE() + " ";
                }
            }
        }

        model.addAttribute("upSellingString", upSellingString);
        model.addAttribute("crossSellingString", crossSellingString);

        /* success. go to success page */
        return "appointment/appointmentsuccess";
    }

    /**
     * Checks if the database has a upselling strategy for the currently selected service
     * @returns a upsellingstrategy if available, or an empty optional
     */
    private Optional<UpSellingStrategy> tryGetExistingUpSellingStrategy(String selectedServName) {
        var list = upService.findByLowName(selectedServName);

        if(!list.isEmpty()) {
            return Optional.of(list.get(0));
        }
        else {
            return Optional.empty();
        }
    }

    /**
     * Checks if the database has a cross-selling strategy for the currently selected service
     * @returns a upsellingstrategy if available, or an empty optional
     */
    private Optional<CrossSellingStrategy> tryGetExistingCrossSellingStrategy(String selectedServName) {
        var list = crossService.getCrossList();

        if(!list.isEmpty()) {
            CrossSellingStrategy cr = null;
            for(var c : list) {

                if( (c.getServiceA() != null ? c.getServiceA().equals(selectedServName) : false) ||
                        (c.getServiceB() != null ? c.getServiceB().equals(selectedServName) : false) ||
                        (c.getServiceC() != null ? c.getServiceC().equals(selectedServName) : false) ||
                        (c.getServiceD() != null ? c.getServiceD().equals(selectedServName) : false) ||
                        (c.getServiceE() != null ? c.getServiceE().equals(selectedServName) : false)
                ) {
                    cr = c;
                    break;
                }
            }

            if(cr != null) {
                return Optional.of(cr);
            }
            else {
                return Optional.empty();
            }
        }
        else {
            return Optional.empty();
        }
    }

    /**
     * If the database has no upselling strategy for the service selected,
     * then call this to generate one and insert it into the database.
     * @param selectedSrv
     * @returns the upselling strategy generated,
     * or emptiness if there are not services of higher value with the same type as the service of lowName
     */
    private Optional<UpSellingStrategy> generateUpSellingStrategy(Service selectedSrv) {
        String lowName = selectedSrv.getServiceName();
        UpSellingStrategy up = new UpSellingStrategy();
        up.setLowServiceName(lowName);

        var selectedSrvType = selectedSrv.getServiceType();
        var servicesOfTheSameType = serviceServices.findServicesByType(selectedSrvType);

        Service serviceOfHigherValue = null;
        for(var s : servicesOfTheSameType) {
            if(s.getServicePrice() > selectedSrv.getServicePrice()) {
                serviceOfHigherValue = s;
                break;
            }
        }

        if(serviceOfHigherValue != null) { // can make a upselling strategy
            up.setHighServiceName(serviceOfHigherValue.getServiceName());
            upService.newUpSellingStrategy(up);
            return Optional.of(up);
        }
        else { // cannot
            return Optional.empty();
        }
    }

    /**
     * If the database has no upselling strategy for the service selected,
     * then call this to generate one and insert it into the database.
     * @param selectedService
     * @returns the upselling strategy generated,
     * or emptiness if there are not services of higher value with the same type as the service of lowName
     */
    private Optional<CrossSellingStrategy> generateCrossSellingStrategy(Service selectedService) {
        /* just put services that are of different types to the strategy */

        String selectedName = selectedService.getServiceName();
        CrossSellingStrategy cr = new CrossSellingStrategy();
        cr.setServiceA(selectedName);

        var allSrvList = serviceServices.findAllServices();
        int numOtherServices = 0;
        for(var s : allSrvList) {
            if(!s.getServiceType().equals(selectedService.getServiceType())) {
                ++numOtherServices;
                switch(numOtherServices) {
                    case 1:
                    cr.setServiceB(s.getServiceName());
                    continue;
                    case 2:
                    cr.setServiceC(s.getServiceName());
                    continue;
                    case 3:
                    cr.setServiceD(s.getServiceName());
                    continue;
                    case 4:
                    cr.setServiceE(s.getServiceName());
                    break;
                default:
                    break;
                }
            }
        }
        if(numOtherServices != 0) {
            crossService.newCrossSellingStrategy(cr);
            return Optional.of(cr);
        }
        else { // cannot
            return Optional.empty();
        }
    }

    /**
     * Show groomer schedules on the page
     * @param model
     * @return
     */
    @GetMapping("/groomer-schedules")
    public String showSchedules(Model model) {

        List<Groomer> groomerList = groomerService.findAllGroomers();
        model.addAttribute("groomerList", groomerList);

        return "appointment/groomerschedules";
    }

    @GetMapping("/account")
    public String viewAccount(Authentication auth, Model model) {
        Account account = Account.getAuthenticatedAccount(auth);
        Optional<User> optionalUser = userServices.findUserByAccount(account);
        User user = optionalUser.get();
        model.addAttribute("account", account);
        model.addAttribute("user", user);
//        String username = account.getUsername();
//        System.out.println(username);
        return "orders/profile";
    }

    @GetMapping("/account/edit")
    public String viewEditAccount(Authentication auth, Model model) {
        Account account = Account.getAuthenticatedAccount(auth);
        Optional<User> optionalUser = userServices.findUserByAccount(account);
        User user = optionalUser.get();
        model.addAttribute("account", account);
        model.addAttribute("user", user);
        return "forms/profile_information";
    }

    @PostMapping("/account/edit")
    public String editAccount(@RequestParam String userName,
                              @RequestParam Long phoneNumber,
                              @RequestParam String userEmail,
                              Authentication auth,
                              Model model) {
        Account account = Account.getAuthenticatedAccount(auth);
        Optional<User> optionalUser = userServices.findUserByAccount(account);
        User user = optionalUser.get();
        model.addAttribute("account", new Account());
        model.addAttribute("user", new User());
        return "redirect:/account";
    }

    @GetMapping("/account/pet")
    public String viewPet(Model model) {
        List<Pet> petList = petservices.findAllPet();
        Pet pet = petList.get(0);
        model.addAttribute("pet", pet);
        return "orders/petprofile";
    }

    @GetMapping("/history")
    public String viewOrderHistory(Authentication auth,
                                   Model model) {
        Account account = Account.getAuthenticatedAccount(auth);
        Optional<User> optionalUser = userServices.findUserByAccount(account);
        User user = optionalUser.get();
        List<Appointment> appointmentList = appointmentServices.getAppointmentsByUser(user);
        model.addAttribute("appointmentList", appointmentList);
        return "orders/orderhistory";
    }

    @GetMapping("/history/make-comments")
    public String viewComment(Model model) {
        model.addAttribute("review", new Review());
        return "comment/make_comments";
    }

    @PostMapping("/history/make-comments")
    public String addComment(@RequestParam String content,
                             @RequestParam String isAnonymous,
                             Model model) {
        int rank = 4;
        Service service = new Service("Super Bath", 32, "Bath");
        serviceServices.addService("Super Bath", 32, "Bath");
        reviewService.createReview(rank, content, isAnonymous, service);

        return viewComment(model);
    }
}