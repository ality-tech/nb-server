package org.neighbor.server.config;

import org.neighbor.server.mapper.AccountMapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = AccountMapper.class)
public class MapperConfig {
}