package edu.cpt202.group9.projb.controllers;

import edu.cpt202.group9.projb.annualReport.AnnualReport;
import edu.cpt202.group9.projb.annualReport.AnnualReportService;
import edu.cpt202.group9.projb.appointment.Appointment;
import edu.cpt202.group9.projb.appointment.AppointmentServices;
import edu.cpt202.group9.projb.monthlyReport.MonthlyReport;
import edu.cpt202.group9.projb.monthlyReport.MonthlyReportService;
import edu.cpt202.group9.projb.sellingStrategy.CrossSellingStrategy;
import edu.cpt202.group9.projb.sellingStrategy.CrossServices;
import edu.cpt202.group9.projb.sellingStrategy.UpSellingStrategy;
import edu.cpt202.group9.projb.sellingStrategy.UpService;
import edu.cpt202.group9.projb.service.ServiceServices;
import edu.cpt202.group9.projb.shopAppearance.ShopAppearance;
import edu.cpt202.group9.projb.shopAppearance.ShopAppearanceRepository;
import edu.cpt202.group9.projb.shopAppearance.ShopAppearanceService;
import edu.cpt202.group9.projb.shopMasterFileItem.ShopMasterFileItem;
import edu.cpt202.group9.projb.shopMasterFileItem.ShopMasterFileItemRepo;
import edu.cpt202.group9.projb.shopMasterFileItem.ShopMasterFileItemService;
import edu.cpt202.group9.projb.userMasterFileItem.UserMasterFileItem;
import edu.cpt202.group9.projb.userMasterFileItem.UserMasterFileItemRepo;
import edu.cpt202.group9.projb.userMasterFileItem.UserMasterFileItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Month;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/manager/")
public class ManagerOperationController {

    @Autowired
    private UserMasterFileItemService userMasterFileItemService;
    @Autowired
    private ShopMasterFileItemService shopMasterFileItemService;
    @Autowired
    private AnnualReportService annualReportService;
    @Autowired
    private MonthlyReportService monthlyReportService;
    @Autowired
    private ShopAppearanceService shopAppearanceService;
    @Autowired
    private UpService upService;
    @Autowired
    private CrossServices crossService;
    @Autowired
    private AppointmentServices appointmentServices;
    @Autowired
    private ServiceServices serviceServices;


    @GetMapping("")
    public String viewManagerSystemPage(Model model) {
        return "manager/managersystem";
    }


    @GetMapping("/master-file")
    public String viewAllMasterFile(Model model, @RequestParam(name = "errorUpdateFail", required = false) String errorUpdateFail,
                                    @RequestParam(name = "updateSuccess", required = false) String updateSuccess,
                                    @RequestParam(name = "deleteError", required = false) String deleteError,
                                    @RequestParam(name = "deleteSuccess", required = false) String deleteSuccess,
                                    @RequestParam(name = "itemNameDuplicated", required = false) String itemNameDuplicated,
                                    @RequestParam(name = "addShopItemSuccess", required = false) String addShopItemSuccess,
                                    @RequestParam(name = "errorShopItemUpdateFail", required = false) String errorShopItemUpdateFail,
                                    @RequestParam(name = "shopItemUpdateSuccess", required = false) String shopItemUpdateSuccess,
                                    @RequestParam(name = "deleteShopItemError", required = false) String deleteShopItemError,
                                    @RequestParam(name = "deleteShopItemSuccess", required = false) String deleteShopItemSuccess) {
        if (errorUpdateFail != null) {
            model.addAttribute("errorUpdateFail", errorUpdateFail);
        }

        if (updateSuccess != null) {
            model.addAttribute("updateSuccess", updateSuccess);
        }

        if (deleteError != null) {
            model.addAttribute("deleteError", deleteError);
        }

        if (deleteSuccess != null) {
            model.addAttribute("deleteSuccess", deleteSuccess);
        }

        if (itemNameDuplicated != null) {
            model.addAttribute("itemNameDuplicated", itemNameDuplicated);
        }

        if (addShopItemSuccess != null) {
            model.addAttribute("addShopItemSuccess", addShopItemSuccess);
        }

        if (errorShopItemUpdateFail != null) {
            model.addAttribute("errorShopItemUpdateFail", errorShopItemUpdateFail);
        }

        if (shopItemUpdateSuccess != null) {
            model.addAttribute("shopItemUpdateSuccess", shopItemUpdateSuccess);
        }

        if (deleteShopItemError != null) {
            model.addAttribute("deleteShopItemError", deleteShopItemError);
        }

        if (deleteShopItemSuccess != null) {
            model.addAttribute("deleteShopItemSuccess", deleteShopItemSuccess);
        }


        List<UserMasterFileItem> userMasterFileList = userMasterFileItemService.findAllUser();
        List<ShopMasterFileItem> shopMasterFileList = shopMasterFileItemService.findAllItem();
        model.addAttribute("userMasterFileList", userMasterFileList);
        model.addAttribute("shopMasterFileList", shopMasterFileList);
        model.addAttribute("shopMasterFileItem", new ShopMasterFileItem());

        model.addAttribute("username", new String());
        model.addAttribute("itemname", new String());
        model.addAttribute("deleteUser", new UserMasterFileItem());
        model.addAttribute("deleteShop", new ShopMasterFileItem());
        model.addAttribute("updateUser", new UserMasterFileItem());
        model.addAttribute("updateShop", new ShopMasterFileItem());

        return "manager/masterfile";
    }

    @PostMapping("/master-file/add-shop-file")
    public String addShopMasterFileItem(Model model, @ModelAttribute("shopMasterFileItem") ShopMasterFileItem shopmasterfileItem) {

        Optional<ShopMasterFileItem> item = shopMasterFileItemService.findByItemName(shopmasterfileItem.getItemName());
        shopMasterFileItemService.newShopMasterFileItem(shopmasterfileItem);
        if (item.isPresent()) {
            model.addAttribute("itemNameDuplicated", "The itemName with name '" + shopmasterfileItem.getItemName() + "' already exists.");
            return "redirect:/manager/master-file?itemNameDuplicated=The itemName already exists.";
        }

        model.addAttribute("addShopItemSuccess", "Succeed: Add successfully!");
        return "redirect:/manager/master-file?addShopItemSuccess=Succeed: Add successfully!";
    }

    @PutMapping("/master-file/update-shop-file")
    public String updateShopMasterFileItem(Model model, @ModelAttribute("updateShop") ShopMasterFileItem updateShop) {
        shopMasterFileItemService.updateShopMasterFileItem(updateShop.getItemName(), updateShop.getNumber());

        Optional<ShopMasterFileItem> item = shopMasterFileItemService.findByItemName(updateShop.getItemName());
        if (!item.isPresent()) {
            System.out.println("44444444");
            model.addAttribute("errorShopItemUpdateFail", "Shop item does not exist!");
            return "redirect:/manager/master-file?errorShopItemUpdateFail=Shop item does not exist!";
        }

        model.addAttribute("shopItemUpdateSuccess", "Succeed: Update successfully!");
        return "redirect:/manager/master-file?shopItemUpdateSuccess=Succeed: Update successfully!";
    }

    @DeleteMapping("/master-file/delete-shop-file")
    public String deleteShopMasterFileItem(Model model, @ModelAttribute("deleteShop") ShopMasterFileItem deleteShop) {
        boolean isDeleted = shopMasterFileItemService.deleteShopMasterFileItem(deleteShop.getItemName());

        if (isDeleted == false) {
            model.addAttribute("deleteShopItemError", "Error: Shop item does not exist!");
            return "redirect:/manager/master-file?deleteShopItemError=Error: Shop item does not exist!";
        }
        model.addAttribute("deleteShopItemSuccess", "Succeed: Shop item delete successfully!");
        return "redirect:/manager/master-file?deleteShopItemSuccess=Succeed: Shop item delete successfully!";

    }

    @PutMapping("/master-file/update-user-file")
    public String updateUserMasterFileItem(Model model, @ModelAttribute("updateUser") UserMasterFileItem updateUser) {
        userMasterFileItemService.updateUserMasterFileItem(updateUser.getUser().getFirstName(), updateUser.getUser().getLastName(), updateUser.getUser().getPhoneNum(), updateUser.getUser().getUserEmail(), updateUser.getUser().getAccount().getUsername());
        Optional<UserMasterFileItem> item = userMasterFileItemService.findByUserName(updateUser.getUser().getAccount().getUsername());
        if (!item.isPresent()) {
            System.out.println("44444444");
            model.addAttribute("errorUpdateFail", "Username does not exist!");
            return "redirect:/manager/master-file?errorUpdateFail=Username does not exist!";
        }

        model.addAttribute("updateSuccess", "Succeed: Update successfully!");
        return "redirect:/manager/master-file?updateSuccess=Succeed: Update successfully!";

    }

    @DeleteMapping("/master-file/delete-user-file")
    public String deleteUserMasterFileItem(Model model, @ModelAttribute("deleteUser") UserMasterFileItem deleteUser) {
        boolean isDeleted = userMasterFileItemService.deleteUserMasterFileItem(deleteUser.getUser());

        System.out.println(isDeleted);
        if (isDeleted == false) {
            model.addAttribute("deleteError", "Error: User does not exist!");
            return "redirect:/manager/master-file?deleteError=Error: User does not exist!";
        }
        model.addAttribute("deleteSuccess", "Succeed: User delete successfully!");
        return "redirect:/manager/master-file?deleteSuccess=Succeed: File delete successfully!";
    }


    // /**
    //  * Required by th:object
    //  * @param model
    //  * @return
    //  */
    // @ModelAttribute("annualReport")
    // public AnnualReport annualReport() {
    //     return new AnnualReport();
    // }

    //     /**
    //  * Required by th:object
    //  * @param model
    //  * @return
    //  */
    // @ModelAttribute("monthlyReport")
    // public MonthlyReport monthlyReport() {
    //     return new MonthlyReport();
    // }

    @GetMapping("/statistical-reports")
    public String viewStatReportPage(Model model,
                                     @RequestParam(name = "monthIllegalError", required = false) String monthIllegalError,
                                     @RequestParam(name = "annualReportNullError", required = false) String annualReportNullError,
                                     @RequestParam(name = "monthReportNullErrorYear", required = false) String monthReportNullErrorYear,
                                     @RequestParam(name = "monthReportNullErrorMonth", required = false) String monthReportNullErrorMonth,
                                     @RequestParam(name = "monthReportNullErrorYearMonth", required = false) String monthReportNullErrorYearMonth) {
        if (monthIllegalError != null) {
            model.addAttribute("monthIllegalError", monthIllegalError);
        }

        if (annualReportNullError != null) {
            model.addAttribute("annualReportNullError", annualReportNullError);
        }

        if (monthReportNullErrorYear != null) {
            model.addAttribute("monthReportNullErrorYear", monthReportNullErrorYear);
        }

        if (monthReportNullErrorMonth != null) {
            model.addAttribute("monthReportNullErrorMonth", monthReportNullErrorMonth);
        }

        if (monthReportNullErrorYearMonth != null) {
            model.addAttribute("monthReportNullErrorYearMonth", monthReportNullErrorYearMonth);
        }

            return "manager/statisticalreport";

    }

    @PostMapping("/statistical-reports/annual-report")
    public String selectAnnualReportTime(
        @RequestParam(value = "annualyear", required = false) Integer annualyear,
        @RequestParam(value = "monthlyyear", required = false) Integer monthlyyear,
        @RequestParam(value = "month", required = false) Integer month,
        Model model) {

        if (annualyear == null) {
            model.addAttribute("annualReportNullError", "Year cannot be null.");
            return "redirect:/manager/statistical-reports" + "?annualReportNullError=Year cannot be null.";
        }

        annualReportService.generateAnnualReport(annualyear);
        Optional<AnnualReport> annualReport = annualReportService.findByYear(annualyear);
        if(!annualReport.isPresent()){
            annualReport= Optional.of(new AnnualReport());
        }
        AnnualReport annualReport1=annualReport.get();
//        var annualReport = new AnnualReport();
//
//        annualReport.setSalesOfMonth1(100);
//        annualReport.setSalesOfMonth2(200);
//        annualReport.setSalesOfMonth3(300);
//        annualReport.setSalesOfMonth4(400);
//        annualReport.setSalesOfMonth5(500);
//        annualReport.setSalesOfMonth6(600);
//        annualReport.setSalesOfMonth7(700);
//        annualReport.setSalesOfMonth8(800);
//        annualReport.setSalesOfMonth9(900);
//        annualReport.setSalesOfMonth10(1000);
//        annualReport.setSalesOfMonth11(1100);
//        annualReport.setSalesOfMonth12(1200);

        model.addAttribute("annualReport", annualReport1);

        return "manager/annualreport";

    }

    @PostMapping("/statistical-reports/monthly-report")
    public String selectMonthlyReportTime(
        @RequestParam(value = "annualyear", required = false) Integer annualyear,
        @RequestParam(value = "monthlyyear", required = false) Integer monthlyyear,
        @RequestParam(value = "month", required = false) Integer month,
        Model model) {

        if (monthlyyear == null && month == null) {
            model.addAttribute("monthReportNullErrorYearMonth", "Year cannot be null.");
            return "redirect:/manager/statistical-reports" + "?monthReportNullErrorYearMonth=Year and month cannot be null.";
        }

        if (monthlyyear == null) {
            model.addAttribute("monthReportNullErrorYear", "Year cannot be null.");
            return "redirect:/manager/statistical-reports" + "?monthReportNullErrorYear=Year cannot be null.";
        }

        if (month == null) {
            model.addAttribute("monthReportNullErrorMonth", "Month cannot be null.");
            return "redirect:/manager/statistical-reports" + "?monthReportNullErrorMonth=Month cannot be null.";
        }

        if ((month < 1) || (month > 12)) {
            model.addAttribute("monthIllegalError", "Month should be a number between 1 and 12.");
            return "redirect:/manager/statistical-reports" + "?monthIllegalError=Month should be a number between 1 and 12.";
        }

        monthlyReportService.generateMonthlyReport(monthlyyear, month);
         Optional<MonthlyReport> monthlyReport = monthlyReportService.findByYearAndMonth(monthlyyear,month);
        if(!monthlyReport.isPresent()){
            monthlyReport= Optional.of(new MonthlyReport());
        }
        MonthlyReport monthlyReport1 = monthlyReport.get();
//        var monthlyReport = new MonthlyReport();
//
//        monthlyReport.setTotalSales(1000);
//        monthlyReport.setMaxOfSales(50);
//        monthlyReport.setMeanOfSales(40);
//        monthlyReport.setMinOfSales(10);
//        monthlyReport.setStdOfSales(10);

        model.addAttribute("monthlyReport", monthlyReport1);

        return "manager/monthlyreport";
    }

    @GetMapping("/shop-appearance/find-all")
    public String findAllShopAppearances(Model model,
                                         @RequestParam(name = "fileNameError", required = false) String fileNameError,
                                         @RequestParam(name = "fileDuplicated", required = false) String fileDuplicated,
                                         @RequestParam(name = "deleteError", required = false) String deleteError,
                                         @RequestParam(name = "deleteSuccess", required = false) String deleteSuccess,
                                         @RequestParam(name = "descriptionCharacterError", required = false) String descriptionCharacterError,
                                         @RequestParam(name = "descriptionLengthError", required = false) String descriptionLengthError) {
        if (fileDuplicated != null) {
            model.addAttribute("fileDuplicated", fileDuplicated);
        }

        if (fileNameError != null) {
            model.addAttribute("fileNameError", fileNameError);
        }

        if (deleteError != null) {
            model.addAttribute("deleteError", deleteError);
        }

        if (deleteSuccess != null) {
            model.addAttribute("deleteSuccess", deleteSuccess);
        }

        if (descriptionCharacterError != null) {
            model.addAttribute("descriptionCharacterError", descriptionCharacterError);
        }

        if (descriptionLengthError != null) {
            model.addAttribute("descriptionLengthError", descriptionLengthError);
        }

        List<ShopAppearance> images = shopAppearanceService.findAllShopAppearances();
        for (ShopAppearance image : images) {
            byte[] imageData = image.getImageData();
            String base64Encoded = Base64.getEncoder().encodeToString(imageData);
            image.setBase64Encoded(base64Encoded);
        }

        model.addAttribute("images", images);
        model.addAttribute("shopAppearanceDelete", new ShopAppearance());
        return "manager/shopappearance";
    }

    @PostMapping("/shop-appearance/add-shop-appearance")
    public String addShopAppearance(Model model,
                                    @RequestParam("file") MultipartFile file,
                                    @RequestParam("description") String description) throws IOException {
        String fileName = file.getOriginalFilename();

        if ((fileName != null) && (!fileName.matches("[\\w\\s\\-\\._]+"))) {
            model.addAttribute("fileNameError",
                    "File name can only contain English letters, numbers, underscores, hyphens.");
            return "redirect:/manager/shop-appearance/find-all"
                    + "?fileNameError=File name can only contain English letters, numbers, underscores, hyphens.";
        }

        Optional<ShopAppearance> appearance = shopAppearanceService.findByFileName(fileName);
        if (appearance.isPresent()) {
            model.addAttribute("fileDuplicated", "The file" +
                    " with name '" + fileName + "' already exists.");
            return "redirect:/manager/shop-appearance/find-all"
                    + "?fileDuplicated=The file with name '" + fileName + "' already exists.";
        }

        if (file.getSize() > 0.5 * 1024 * 1024) {
            System.out.println("7777");
            model.addAttribute("message", "File size exceeds 0.5MB.");
            return "redirect:/manager/shop-appearance/find-all";
        }

        if (!description.matches("^['\"\\sA-Za-z0-9\n.,;:?!()\\-\\$&*#%]*$")) {
            model.addAttribute("descriptionCharacterError",
                    "Description cannot contain Chinese characters!");
            return "redirect:/manager/shop-appearance/find-all"
                    + "?descriptionCharacterError=Description cannot contain Chinese characters!";
        }

        int maxDescriptionLength = 100;
        if (description.length() > maxDescriptionLength) {
            model.addAttribute("descriptionLengthError",
                    "Description exceeds maximum length of " + maxDescriptionLength + " characters.");
            return "redirect:/manager/shop-appearance/find-all"
                    + "?descriptionLengthError=Description cannot exceed 100 words!";
        }

        try {
            shopAppearanceService.addShopAppearance(file, description);
            model.addAttribute("message", "File uploaded successfully");
        } catch (IOException e) {
            model.addAttribute("message", "Failed to upload file: " + e.getMessage());
        }
        return "redirect:/manager/shop-appearance/find-all";
    }

    @DeleteMapping("/shop-appearance/delete-shop-appearance")
    public String deleteShopAppearance(Model model,
                                       @ModelAttribute("shopAppearanceDelete") ShopAppearance shopAppearanceDelete) {
        boolean isDeleted = shopAppearanceService.deleteShopAppearanceByFileName(shopAppearanceDelete.getFileName());
        if (isDeleted == false) {
            model.addAttribute("deleteError", "Error: File does not exist!");
            return "redirect:/manager/shop-appearance/find-all?deleteError=Error: File does not exist!";
        }
        model.addAttribute("deleteSuccess", "Succeed: File delete successfully!");
        return "redirect:/manager/shop-appearance/find-all?deleteSuccess=Succeed: File delete successfully!";
    }

    @GetMapping("/SellingStrategy")
    public String getAllStrategy(Model model,
                                 @RequestParam(name = "deleteError", required = false) String deleteError,
                                 @RequestParam(name = "deleteSuccess", required = false) String deleteSuccess) {
        if (deleteError != null) {
            System.out.println("222222");
            model.addAttribute("deleteError", deleteError);
        }

        if (deleteSuccess != null) {
            model.addAttribute("deleteSuccess", deleteSuccess);
        }
        model.addAttribute("upSellingStrategy", new UpSellingStrategy());
        model.addAttribute("upSellingStrategyList", upService.getUpList());
        model.addAttribute("upSellingStrategyAdd", new UpSellingStrategy());
        model.addAttribute("upSellingStrategyDelete", new UpSellingStrategy());
        model.addAttribute("crossSellingStrategy", new CrossSellingStrategy());
        model.addAttribute("crossSellingStrategyList", crossService.getCrossList());
        model.addAttribute("crossSellingStrategyAdd", new CrossSellingStrategy());
        model.addAttribute("crossSellingStrategyDelete", new CrossSellingStrategy());
        model.addAttribute("serviceList", serviceServices.findAllServices());
//        model.addAttribute("model",new UpSellingStrategy());
        model.addAttribute("serviceList", serviceServices.findAllServices());

        return "manager/selling";
    }

    @PostMapping("/SellingStrategy/AddUp")
    public String confirmNewUpStrategy(Model model, @ModelAttribute("upSellingStrategyAdd") UpSellingStrategy upSellingStrategy) {
        upService.newUpSellingStrategy(upSellingStrategy);
        return getAllStrategy(model, null, null);
    }

    @DeleteMapping("/SellingStrategy/DeleteUp")
    public String deleteUp(Model model, @ModelAttribute("upSellingStrategyDelete") UpSellingStrategy upSellingStrategyDelete) {
        boolean isDeleted = upService.deleteUpSellingStrategyById(upSellingStrategyDelete.getId());
        if (isDeleted == false) {
            System.out.println("3333333");
            model.addAttribute("deleteError", "Error: ID does not exist!");
            return "redirect:/manager/SellingStrategy?deleteError=Error:ID does not exist!";
        }
        model.addAttribute("deleteSuccess", "Succeed: Strategy delete successfully!");
        return "redirect:/manager/SellingStrategy?deleteSuccess=Succeed: Up strategy delete successfully!";
    }


    @PostMapping("/SellingStrategy/AddCross")
    public String confirmNewCrossStrategy(Model model, @ModelAttribute("crossSellingStrategyAdd") CrossSellingStrategy crossSellingStrategy) {
        crossService.newCrossSellingStrategy(crossSellingStrategy);
        return "redirect:/manager/SellingStrategy";
    }

    @DeleteMapping("/SellingStrategy/DeleteCross")
    public String deleteCross(Model model,
                              @ModelAttribute("crossSellingStrategyDelete") CrossSellingStrategy crossSellingStrategyDelete) {
        boolean isDeleted = crossService.deleteCrossSellingStrategyById(crossSellingStrategyDelete.getId());
        // System.out.println(crossSellingStrategyDelete.getId());
        // System.out.println(isDeleted);
        if (isDeleted == false) {
            System.out.println("3333333");
            model.addAttribute("deleteError", "Error: ID does not exist!");
            return "redirect:/manager/SellingStrategy?deleteError=Error:ID does not exist!";
        }
        model.addAttribute("deleteSuccess", "Succeed: Strategy delete successfully!");
        return "redirect:/manager/SellingStrategy?deleteSuccess=Succeed: Cross strategy delete successfully!";
    }

    @GetMapping("/managerorders")
    public String viewOrdersByManager(Model model){
        List<Appointment> appointmentList = appointmentServices.getAllAppointments();
        model.addAttribute("appointmentList", appointmentList);
        model.addAttribute("appointment", new Appointment());
        return "manager/managerorders";
    }
}
