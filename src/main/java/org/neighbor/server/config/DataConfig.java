package org.neighbor.server.config;

import org.neighbor.server.entity.AccountEntity;
import org.neighbor.server.repository.AccountRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackageClasses = AccountEntity.class)
@EnableJpaRepositories(basePackageClasses = AccountRepository.class)
public class DataConfig {
}