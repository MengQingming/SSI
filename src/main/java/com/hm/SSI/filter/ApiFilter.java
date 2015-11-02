package com.hm.SSI.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**过滤所有请求路口**/
public class ApiFilter implements Filter {  
	  
    @Override  
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {  
        if (!(servletRequest instanceof HttpServletRequest) || !(servletResponse instanceof HttpServletResponse)) {  
            throw new ServletException("OncePerRequestFilter just supports HTTP requests");  
        }  
        filterChain.doFilter(servletRequest, servletResponse);  
        return;  
    }  
    
    @Override  
    public void init(FilterConfig filterConfig) throws ServletException {
    	//ConfUtil.load();
    	/*if(StringUtils.isBlank(Token.token)){
			//UserLogin.login();
		}*/
    }  
  
    @Override  
    public void destroy() {  
    }  
    
}
