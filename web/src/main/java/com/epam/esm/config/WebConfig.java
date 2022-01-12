package com.epam.esm.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 *  WebConfig used for bean declarations .
 */
@EnableWebMvc
@Configuration
@ComponentScan(basePackages = "com.epam.esm.controller")
public class WebConfig {

    /// ...
}
