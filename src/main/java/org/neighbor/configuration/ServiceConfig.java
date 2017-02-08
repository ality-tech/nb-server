package org.neighbor.configuration;

import org.neighbor.service.impl.AuthServiceImpl;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = AuthServiceImpl.class)
public class ServiceConfig {
}