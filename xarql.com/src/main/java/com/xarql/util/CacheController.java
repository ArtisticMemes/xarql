package com.xarql.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CacheController implements Filter
{

    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        chain.doFilter(request, response);
        response.setHeader("Cache-Control", "public, max-age=86400");
    } // doFilter()

    @Override
    public void destroy()
    {
    } // destroy()

    @Override
    public void init(FilterConfig arg0) throws ServletException
    {
    } // init()

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest) arg0;
        HttpServletResponse response = (HttpServletResponse) arg1;
        doFilter(request, response, arg2);
    }
} // CacheController
