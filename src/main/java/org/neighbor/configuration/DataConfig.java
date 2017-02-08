package org.neighbor.configuration;

import org.neighbor.entity.NeighborAccount;
import org.neighbor.repository.AccountRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackageClasses = NeighborAccount.class)
@EnableJpaRepositories(basePackageClasses = AccountRepository.class)
public class DataConfig {
}