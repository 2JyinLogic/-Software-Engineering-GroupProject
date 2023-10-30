package edu.cpt202.group9.projb.controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Handles HTTP errors
 * 
 * @author Guanyuming He
 * @version 2023.5.4
 * @since 2023.5.1
 */
@Controller
public class CustomErrorController implements ErrorController {
    
	private static final String PATH = "/error";

    @RequestMapping(PATH)
    public String handleError(HttpServletRequest request, 
    Model model) {     
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            model.addAttribute("errorCode", statusCode);
        
            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error/error-404";
            }
            else if(statusCode == HttpStatus.FORBIDDEN.value()) {
                return "error/error-403";
            }
        }
        
        /* general errors */
        return "error/error";
    }
}
