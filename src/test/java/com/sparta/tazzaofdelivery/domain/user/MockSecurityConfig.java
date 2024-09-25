package com.sparta.tazzaofdelivery.domain.user;

import com.sparta.tazzaofdelivery.config.filter.AuthFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
//public class MockSecurityConfig {
//
//    @Bean
//    public FilterRegistrationBean<AuthFilter> disableAuthFilter(AuthFilter authFilter) {
//        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>(authFilter);
//        registrationBean.setEnabled(false);  // Disable the filter
//        return registrationBean;
//    }
//}
