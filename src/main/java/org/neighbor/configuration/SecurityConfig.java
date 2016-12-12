package org.neighbor.configuration;

import org.neighbor.security.CustomBasicAuthenticationEntryPoint;
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
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /*
    * Security
For authorization purpose http header will be used. Name – 'Authorization', value – 'Basic base64encode(login + ':' + pin_code). In addition to
spring-sercutiry applicative security should be used to protect resources from unauthorized usage.

On security error (unauthorised access or allocative security violation) exception with code 'SECURITY_RULE_VIOLATION' throws. Http
response status in this case – 401 (Unauthorized)


auth-controller – should be accused without authorization headed

org-controller – access allowed only for users with 'nb_admin' role

account-controller – users with role 'nb_admin' or by user with 'nb_operator' role if user's account linked with the same nb_org as
       accessible operator could create/update new account for his own org only

user-controller – only user itself, 'nb_admin' or 'nb_operator' of user's org allows to update 'user_phone'
    * */

    public static final String REALM_NAME = "ng-server api";
    public static final String SECURITY_RULE_VIOLATION = "SECURITY_RULE_VIOLATION";

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        //todo adjust me!
        auth.inMemoryAuthentication()
                .withUser("user").password("password").roles("NB_DEV");
    }

   /* @Bean
    BasicAuthenticationFilter authenticationFilter() throws Exception {
        return new BasicAuthenticationFilter(authenticationManager(), authenticationEntryPoint());
    }*/

    @Bean
    CustomBasicAuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomBasicAuthenticationEntryPoint();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
//                .antMatchers("/auth/**").permitAll()
                .antMatchers("/swagger-ui.html").hasRole("NB_DEV")
                .antMatchers("/account-controller/**").hasAnyRole("NB_USER", "NB_ADMIN")
                .antMatchers("/org-controller**").hasRole("NB_ADMIN")
                .antMatchers("/user-controller**").hasRole("NB_USER")
                .and().httpBasic().realmName(REALM_NAME).authenticationEntryPoint(authenticationEntryPoint())
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    /* To allow Pre-flight [OPTIONS] request from browser */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }

}
