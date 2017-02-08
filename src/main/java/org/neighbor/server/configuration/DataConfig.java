package org.neighbor.server.configuration;

import org.neighbor.server.entity.NeighborAccountEntity;
import org.neighbor.server.repository.AccountRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackageClasses = NeighborAccountEntity.class)
@EnableJpaRepositories(basePackageClasses = AccountRepository.class)
public class DataConfig {
}