package com.coffee.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.coffee.db.Admin;
import com.coffee.db.User;

/**
 * 未登录管理员用户全部强制定向到login
 * @author coffeeliu
 *
 */

@WebFilter("/03/*")
public class LoginFilter implements Filter
{
	@Override
	public void init(FilterConfig filterCfg) throws ServletException
	{		
	}

	@Override
	public void destroy()
	{
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException
	{
		Logger logger = Logger.getLogger(LoginFilter.class);
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse)resp;
		logger.debug("** LoginFilter: servletPath" + request.getServletPath());
				String spString=request.getServletPath();
		// login.html 可以直接 访问
		String servletPath = spString.substring(spString.lastIndexOf("/"));
		logger.info(servletPath);
		if(servletPath.equals("/login.html")||servletPath.equals("/AdminLogin.do"))
		{
			chain.doFilter(request, response);
			return;
		}
		
		// 其他页面需要登录后才能访问
		HttpSession session = request.getSession();
		Admin admin = (Admin)session.getAttribute("admin");
		if(admin == null)
		{
			// 重定向到 /login.html
			String contextPath = request.getContextPath();
			logger.debug("** LoginFilter: contextPath" + contextPath);
			String sp=request.getServletPath();
			sp=sp.substring(0, spString.lastIndexOf("/"));
			response.sendRedirect(contextPath+ sp+ "/login.html");
			return;  
		}
		
		// 调用 chain.doFilter()表示 '通过'
		chain.doFilter(request, response);
	}


}
