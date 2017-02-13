package org.neighbor.server.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.neighbor.api.ErrorCode;
import org.neighbor.api.JsonError;
import org.neighbor.server.config.SecurityConfig;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    private ObjectMapper mapper;

    public CustomBasicAuthenticationEntryPoint() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public void commence(final HttpServletRequest request,
                         final HttpServletResponse response,
                         final AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName() + "");
        response.setContentType("application/json");

        JsonError error = new JsonError();
        error.setCode(ErrorCode.UNAUTHORIZED);
        error.setMessage("User is not authorized");

        response.getOutputStream().println(mapper.writeValueAsString(error));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName(SecurityConfig.REALM_NAME);
        super.afterPropertiesSet();
    }
}