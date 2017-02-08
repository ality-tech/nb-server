package org.neighbor.server.configuration;

import org.neighbor.server.service.impl.AuthServiceImpl;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = AuthServiceImpl.class)
public class ServiceConfig {
}