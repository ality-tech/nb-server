package org.neighbor.configuration;

import org.neighbor.mappers.CreateAccountRequestToAccount;
import org.neighbor.service.impl.AuthServiceImpl;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = CreateAccountRequestToAccount.class)
public class MapperConfig {
}