package edu.cpt202.group9.projb.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginRedirectController {

    // will be redirectedly here on log in success directly from the login page
    @RequestMapping(value = "/login/redirect", method = {RequestMethod.GET, RequestMethod.POST})
    public String redirect(Authentication auth) {
        String ret = "redirect:/home";

        String role = Account.getAuthenticatedRole(auth);
        if(role.equals("ROLE_USER")) {
            return "redirect:/mainpage";
        }
        else if(role.equals("ROLE_MANAGER")) {
            return "redirect:/management_system";
        }

        return ret;
    }
}
