package com.spyrka.mindhunters.filters;


import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(
        filterName = "PageNumberFilter",
        urlPatterns = {"/list", "/favourites"}
        )
@Order(1)
public class InitialPageNumberFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
       
        String reqParameter = servletRequest.getParameter("page");
        if (reqParameter == null || reqParameter.isEmpty() ) {

            HttpServletRequest req = (HttpServletRequest) servletRequest;
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

            String servletContext = req.getRequestURI();

            httpResponse.sendRedirect(servletContext + "?page=1");

            return;

        } else if (!reqParameter.matches("[1-9]+[0-9]*") || Integer.valueOf(reqParameter) <= 0 ){
            servletRequest.setAttribute("page", 1);
        }

        filterChain.doFilter(servletRequest, servletResponse);

    }

}
