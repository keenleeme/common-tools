package com.latrell.design.chain;

import javax.servlet.*;
import java.io.IOException;

/**
 * TODO
 *
 * @author liz
 * @date 2023/3/22-22:54
 */
public class WebFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("web过滤");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }
}
