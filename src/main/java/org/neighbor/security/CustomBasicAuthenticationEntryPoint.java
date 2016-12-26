package org.neighbor.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.neighbor.configuration.SecurityConfig;
import org.neighbor.utils.ResponseGenerator;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    @Override
    public void commence(final HttpServletRequest request,
                         final HttpServletResponse response,
                         final AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName() + "");
        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        response.getOutputStream().println(
                mapper.writeValueAsString(ResponseGenerator.UNAUTHORIZED_ERROR)
        );
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName(SecurityConfig.REALM_NAME);
        super.afterPropertiesSet();
    }
}