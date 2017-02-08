package org.neighbor.server.configuration;

import org.neighbor.server.mappers.CreateAccountRequestToAccount;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = CreateAccountRequestToAccount.class)
public class MapperConfig {
}