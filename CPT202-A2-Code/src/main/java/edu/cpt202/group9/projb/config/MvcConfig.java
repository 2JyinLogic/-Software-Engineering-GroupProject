/*
 * MvcConfig.java
 * Last modified 2023.4.27
 * Authored by Guanyuming He
 * 
 * Copyright (C) CPT202 Group 9
 */

package edu.cpt202.group9.projb.config;

// import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
// import org.thymeleaf.spring5.view.ThymeleafViewResolver;
// import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * Overall configuration of MVC
 * 
 * @author Guanyuming He, 
 * @version 2023.4.26
 * @since 2023.4.6
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
 
    /**
     * Addes view controllers to MVC
     */
    public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("account/Login");
		//registry.setOrder(Ordered.HIGHEST_PRECEDENCE);

		registry.addViewController("/help").setViewName("main_and_help_page/helppage");

		registry.addViewController("/mainpage").setViewName("main_and_help_page/mainpage_user");
		registry.addViewController("/sign-up").setViewName("account/Register");
		registry.addViewController("/agreement").setViewName("account/TermOfUse");
		registry.addViewController("/history").setViewName("orders/orderhistory");
		registry.addViewController("/account").setViewName("orders/profile");
		registry.addViewController("/appointment").setViewName("appointment/appointmentpage");
		registry.addViewController("/groomer-schedules").setViewName("appointment/groomerschedules");

		registry.addViewController("/management_system").setViewName("manager/managersystem");
		//registry.addViewController("/manager/master-file").setViewName("manager/masterfile");
		registry.addViewController("/manager/statistical-reports").setViewName("manager/statisticalreport");
		registry.addViewController("/manager/managerorders").setViewName("manager/managerorders"); /* Manager's orders page */
		registry.addViewController("/manager/SellingStrategy").setViewName("manager/selling");
		registry.addViewController("/manager/shop-appearance/find-all").setViewName("manager/shopappearance");
	}

	// Commented out because it's unnecessary for now, but may be useful in the future.
	// @Bean
	// public ClassLoaderTemplateResolver secondaryTemplateResolver() {
	// 	ClassLoaderTemplateResolver secondaryTemplateResolver = new ClassLoaderTemplateResolver();
	// 	secondaryTemplateResolver.setSuffix(".html");
	// 	secondaryTemplateResolver.setCharacterEncoding("UTF-8");
	// 	secondaryTemplateResolver.setOrder(1);
	// 	secondaryTemplateResolver.setCheckExistence(true);
	//
	// 	return secondaryTemplateResolver;
	// }

}