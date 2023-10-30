/*
 * WebSecurityConfig.java
 * Last modified 2023.4.15
 * Authored by Guanyuming He
 * 
 * Copyright (C) CPT202 Group 9
 */

package edu.cpt202.group9.projb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import edu.cpt202.group9.projb.security.AccountUserDetailsService;
import edu.cpt202.group9.projb.security.LoginRedirectSuccessHandler;

/**
 * Configures spring security.
 * 
 * Some lines of code commented are unnecessary while the other are new ways of writing code that don't work.
 * 
 * @author Guanyuming He
 * @version 2023.4.17
 * @since 2023.4.6
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	/*
	 * The built-in JDBC name & pwd authentication is not used, because we want to use custom tables.
	 * Therefore, it is treated that custom storage is used for usernames & passwords.
	 * 
	 * For this, these must be implemented and some provided as beans:
	 * UserDetails (AccountUserDetails); UserDetailsService (AccountUserDetailsService);
	 * Additionally these can also be provided:
	 * AuthenticationProvider, AuthenticationManager, AuthenticationHandler.
	 */

	@Autowired
    private AccountUserDetailsService udService;
	// /**
	//  * Spring injecting udService for me. A replacement of Autowired so that udService can be created before authProvider.
	//  * @param udService injected by Spring
	//  */
    // public WebSecurityConfig(AccountUserDetailsService udService) {
    //     this.udService = udService;
    // }

	/**
	 * Sets security related classes like userdetailsservice, auth provider, and password encoder.
	 * @param auth mgr builder
	 * @throws Exception on error
	 */
	// @Override 
	// protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	// 	auth.userDetailsService(udService).passwordEncoder(passwordEncoder());
	// }
	@Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(udService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

	@Bean
	public AuthenticationSuccessHandler myRedirectHandler() {
		return new LoginRedirectSuccessHandler();
	}
    
	/**
	 * Configures users of which roles can access which pages.
	 * Make a corresponding filter chain.
	 * 
	 * @param http The HTTPSecurity layer
	 * @returns the httpSecurityFilterChain built.
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.authorizeRequests()
		.antMatchers(
			"/",
			"/home",
			"/mainpage",
			"/account",
			"/agreement",
			"/history",
			"/groomer-schedules",
			"/appointment"
			).hasRole("USER")
		.antMatchers(
                "/admin-home",
			"/management_system",
			"/manager/master-file",
			"/manager/statistical-reports",
			"/statistical_reports/annual",
			"/statistical_reports/monthly",
			"/manager/managerorders",
			"/manager/SellingStrategy",
			"/manager/shop-appearance/find-all",
			"/manager/**").hasRole("MANAGER")
		.antMatchers(
			"/account/password"
			).hasAnyRole("USER", "MANAGER")
		.antMatchers(
			"/sign-up",
			"/help",
			"/help/**").permitAll()
		.and()
		.formLogin()
			.loginPage("/login")
			.loginProcessingUrl("/login")
			.defaultSuccessUrl("/login/redirect", false)
			//.successHandler(myRedirectHandler())
			//.failureUrl("/login")
			.permitAll()
		.and()
		.logout()
			.permitAll()
		.and()
		.httpBasic();

		return http.build();
	}
    // @Bean
    // public SecurityFilterChain securityFilter(HttpSecurity http) throws Exception {
	// 	http.authorizeHttpRequests((auth) -> auth
	// 		.requestMatchers(new AntPathRequestMatcher("/home", "/")).permitAll()
	// 	)
	// 	.formLogin((form) -> form
	// 		.loginPage("/login")
	// 		.loginProcessingUrl("/login")
	// 		.defaultSuccessUrl("/home")
	// 		.permitAll()
	// 	)
	// 	.logout((logout) -> logout
	// 		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	// 		.permitAll()
	// 	);

	// 	return http.build();
    // }

	/**
	 * Custom password encoder
	 * 
	 * @returns a strong password encoder.
	 */
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
		// If I use another password encoder then I probably have to configure the DAOAuthenticationProvider too.
		//return new BCryptPasswordEncoder(16);
	}

	// @Bean
	// public AccountDaoAuthenticationProvider authProvider() {
	// 	return new AccountDaoAuthenticationProvider();
	// }
}
