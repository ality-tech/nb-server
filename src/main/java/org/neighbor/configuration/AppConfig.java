package org.neighbor.configuration;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableSwagger2
@ComponentScan({"org.neighbor.service", "org.neighbor.controller", "org.neighbor.mappers"})
@EntityScan("org.neighbor.entity")
@EnableJpaRepositories("org.neighbor.repository")
@Import(SecurityConfig.class)
public class AppConfig {


    @Bean(initMethod = "migrate")
    Flyway flyway(@Autowired DataSource dataSource) {
        Flyway flyway = new Flyway();
        flyway.setBaselineOnMigrate(true);
        flyway.setDataSource(dataSource);
        return flyway;
    }

    @Primary
    @Bean(name = "dataSource")
    @FlywayDataSource
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource dataSource() {
        DataSource dataSource = DataSourceBuilder.create().build();
        return dataSource;
    }

    @Bean
    @DependsOn("flyway")
    EntityManagerFactory entityManagerFactory(@Autowired DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setDataSource(dataSource);
// other configurations
        return bean.getObject();
    }

    /*@Bean
    DataSource dataSource() {
        DataSource dataSource = new BasicDataSource();
// data source configuration
        return dataSource;
    }
*/
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
}