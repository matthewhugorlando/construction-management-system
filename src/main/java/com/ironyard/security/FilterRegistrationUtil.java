package com.ironyard.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by matthewhug on 3/22/17.
 */
@Configuration
public class FilterRegistrationUtil {


    @Bean
    public FilterRegistrationBean restFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new AuthFilter());
        registration.addUrlPatterns("/animal");
        return registration;

    }
}