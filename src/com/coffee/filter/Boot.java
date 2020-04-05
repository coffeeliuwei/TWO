package com.coffee.filter;

import java.io.File;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import com.coffee.bbsplus.RecentBBS;



@WebFilter()
public class Boot implements Filter
{
	@Override
	public void init(FilterConfig filterCfg) throws ServletException
	{
		System.out.println("** 系统初始化工作 :....");
		
		// 加载最新帖子列表
		try
		{
			RecentBBS.i.load();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void destroy()
	{

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException
	{
		
	}



}
