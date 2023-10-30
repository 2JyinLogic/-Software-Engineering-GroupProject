// No longer needed. Left here for the same reason pointed out in the comments of WebSecurityConfig.

// /*
//  * AccountDaoAuthenticationProvider.java
//  * Last modified 2023.4.15
//  * Authored by Guanyuming He
//  * 
//  * Copyright (C) CPT202 Group 9
//  */
// package edu.cpt202.group9.projb.config;

// import org.springframework.stereotype.Component;
// import org.springframework.security.authentication.BadCredentialsException;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.AuthenticationException;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;

// /**
//  * Custom auth provider.
//  * 
//  * @author Guanyuming He
//  * @version 2023.4.15
//  * @since 2023.4.14
//  */
// @Component
// public class AccountDaoAuthenticationProvider extends DaoAuthenticationProvider {
    
//     /**
//      * Authenticate with name & pwd.
//      * 
//      * @returns an authenticated token on success.
//      * @throws AuthenticationException on failure.
//      */
//     @Override
//     public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//         String name = authentication.getName();
//         String input_password = authentication.getCredentials().toString();

//         UserDetails acc;
//         // Looks up the account in the user details service
//         try {
//             acc = getUserDetailsService().loadUserByUsername(name);
//         }
//         catch (UsernameNotFoundException err) { // user not found. authentication failed
//             throw new BadCredentialsException("Username not found.");
//         }

//         var encoder = getPasswordEncoder();
//         String encrypted_real_pwd = encoder.encode(acc.getPassword());
//         if (encoder.matches(input_password, encrypted_real_pwd)) { // authentication succeeded
//             return new UsernamePasswordAuthenticationToken(name, acc.getPassword(), acc.getAuthorities());
//         } else { // authentication failed
//             throw new BadCredentialsException("Password incorrect.");
//         }

//     }

//     @Override
//     public boolean supports(Class<?> authentication) {
//         return authentication.equals(UsernamePasswordAuthenticationToken.class);
//     }
// }
