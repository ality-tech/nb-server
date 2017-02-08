package org.neighbor.server.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import java.io.IOException;

/**
 *
 * Created by Konstantin Konyshev on 08/02/2017.
 */
public class LoggingFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            MDC.put("user", authentication.getName());
            LOG.debug("authorities: {}", authentication.getAuthorities());
        }
        filterChain.doFilter(servletRequest, servletResponse);

        MDC.clear();
    }

    @Override
    public void destroy() {

    }
}
