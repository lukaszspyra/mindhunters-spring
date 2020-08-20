package com.spyrka.mindhunters.filters;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(
        filterName = "AdminAuthorizationFilter",
        urlPatterns = {"/admin/*"}
)
@Order(2)
public class AdminAuthorizationFilter implements Filter {


    private static final Logger LOGGER = LoggerFactory.getLogger(AdminAuthorizationFilter.class.getName());


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        final HttpSession httpSession = httpServletRequest.getSession(false);

        if (httpSession == null) {
            LOGGER.info("Session expired.");
            httpServletResponse.sendRedirect("/");
            return;
        }

        final String role = String.valueOf(httpSession.getAttribute("role"));

        boolean isAuthorized = (role.equals("ADMIN") || role.equals("SUPER_ADMIN"));

        if (!isAuthorized) {
            LOGGER.info("Unauthorized attempt to access admin restricted context");
            httpServletResponse.sendRedirect("/not-found");
            return;

        }

        filterChain.doFilter(servletRequest, servletResponse);

    }


}
