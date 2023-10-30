package edu.cpt202.group9.projb.home;

import edu.cpt202.group9.projb.groomer.GroomerService;
import edu.cpt202.group9.projb.security.*;
import edu.cpt202.group9.projb.token.AccountTokenServices;
import edu.cpt202.group9.projb.user.*;
import edu.cpt202.group9.projb.userMasterFileItem.UserMasterFileItem;
import edu.cpt202.group9.projb.userMasterFileItem.UserMasterFileItemRepo;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Objects;

/**
 * Handle all of the home pages
 *
 * @author Kaijie Lai, Guanyuming He
 * @since 2023.4.5
 * @version 2023.5.3
 */
@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private UserServicesImpl userServices;
    @Autowired
    private AccountServiceImpl accountService;
    @Autowired
    private GroomerService groomerService;
    @Autowired
    private AccountTokenServices accountTokenServices;
    @Autowired
    private UserMasterFileItemRepo userMasterFileItemRepo;

    @GetMapping("/")
    public String homeEmpty(Model model) {
        var groomerList = groomerService.findAllGroomers();
        model.addAttribute("groomerList", groomerList);

        return "main_and_help_page/mainpage_user";
    }

    @GetMapping("/mainpage")
    public String viewHomePage(Model model) {
         var groomerList = groomerService.findAllGroomers();
         model.addAttribute("groomerList", groomerList);

         return "main_and_help_page/mainpage_user";
    }

    @GetMapping("/home")
    public String viewHomePageHome(Model model) {
         var groomerList = groomerService.findAllGroomers();
         model.addAttribute("groomerList", groomerList);

         return "main_and_help_page/mainpage_user";
    }

    @GetMapping("/management_system")
    public String homeAdmin() {
        return "manager/managersystem";
    }

    @GetMapping("/sign-up")
    public String signUp(@RequestParam(name="error", required=false) String error, Model model) {
        model.addAttribute("account", new Account());
        model.addAttribute("user", new User());
        return "account/Register";
    }

    @PostMapping("/sign-up")
    public String signUp(@RequestParam String userEmail,
                         @RequestParam String username,
//                         @RequestParam String phoneNum,
                         @RequestParam String userPassword,
                         @RequestParam(required = false) String error, Model model) {
        try {
            accountService.tryAddNewAccount(username, userPassword);
        }
        catch(IllegalArgumentException e) {
            // display signup error
            return "redirect:/sign-up?error";
        }

        Account newAccount = accountService.findAccountByUsername(username).get();
        User u = userServices.addUser(userEmail, newAccount);
        userMasterFileItemRepo.save(new UserMasterFileItem(u));

        // redirect to logging in page.
        return "redirect:/login";
    }

    @GetMapping("/verification")
    public String reset() {
        return "account/Verification";
    }

    @GetMapping("/set-new-password")
    public String setNewPassword() {
        return "account/Password";
    }

    @GetMapping("/agreement")
    public String agreement() {
        return "account/Agreement";
    }

    @GetMapping("/help-page")
    public String helpPage() {
        return "main_and_help_page/helppage";
    }
}
