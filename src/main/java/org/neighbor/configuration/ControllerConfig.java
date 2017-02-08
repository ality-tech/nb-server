package org.neighbor.configuration;

import org.neighbor.controller.AuthController;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = AuthController.class)
public class ControllerConfig {
}