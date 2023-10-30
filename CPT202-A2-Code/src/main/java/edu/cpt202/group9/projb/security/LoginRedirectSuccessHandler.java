/**
 * File LoginRedirectSuccessHandler.java
 * Defines class LoginRedirectSuccessHandler
 * 
 * Authored by Guanyuming He
 * Last modified 2023.5.2
 */

package edu.cpt202.group9.projb.security;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * Handles login success redirection:
 * on user logging-in, redirect to user's home page
 * on manager logging-in, redirect to manager's home page
 * @deprecated Do not use.
 * 
 * @author Guanyuming He
 * @version 2023.5.2
 * @since 2023.5.2
 */
public class LoginRedirectSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    
    /**
     * Handles logging in success
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, 
      HttpServletResponse response, Authentication authentication)
      throws IOException {
        handle(request, response, authentication);
        // Tell Spring it's been handled.
        clearAuthenticationAttributes(request);
    }

    protected void handle(HttpServletRequest request,
    HttpServletResponse response, Authentication authentication) 
    throws IOException {
        String targetUrl = determineTargetUrl(authentication);
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(Authentication authentication) {
        String roleStr = Account.getAuthenticatedRole(authentication);

        if(roleStr.equals("ROLE_USER")) {
            //return "/home";
            return "/login/redirect";
        }
        else if(roleStr.equals("ROLE_MANAGER")) {
            //return "/admin-home";
            return "/login/redirect";
        }
        else {
            return "/home";
        }
    }

    /**
     * Tell Spring it's been handled by removing the authentication exception.
     * @param request
     */
    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
