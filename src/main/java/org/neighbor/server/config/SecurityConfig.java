package org.neighbor.server.config;

import org.neighbor.server.security.LoggingFilter;
import org.neighbor.server.security.CustomBasicAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String REALM_NAME = "ng-server api";
    private static final String ROLE_DEV = "NB_DEV";
    private static final String ROLE_USER = "NB_USER";
    private static final String ROLE_ADMIN = "NB_ADMIN";
    private static final String ROLE_OPERATOR = "NB_OPERATOR";

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordencoder());
    }

    @Bean(name = "passwordEncoder")
    public PasswordEncoder passwordencoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CustomBasicAuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomBasicAuthenticationEntryPoint();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .addFilterAfter(new LoggingFilter(), FilterSecurityInterceptor.class)
                .authorizeRequests()
                .antMatchers("/auth/register", "/auth/confirm", "/auth/recovery", "/org/list").permitAll()
                .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security", "/swagger-ui.html", "/webjars/**").hasRole(ROLE_ADMIN)
                .antMatchers("/org/**").hasAnyRole(ROLE_ADMIN)
                .antMatchers("/account/**").hasAnyRole(ROLE_OPERATOR, ROLE_ADMIN)
                .antMatchers("/user/**").hasAnyRole(ROLE_USER, ROLE_OPERATOR, ROLE_ADMIN)
                .antMatchers("/auth/check").hasAnyRole(ROLE_USER, ROLE_OPERATOR, ROLE_ADMIN)
                .antMatchers("/**").denyAll()
                .and().httpBasic().realmName(REALM_NAME).authenticationEntryPoint(authenticationEntryPoint())
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    /* To allow Pre-flight [OPTIONS] request from browser */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.debug(false).ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }
}
