package com.coffee.admin;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.coffee.db.Admin;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


@WebServlet("/AdminInfoJS")
public class AdminInfoJS extends HttpServlet
{
	/* 伪静态JS， 为前端提供当前用户信息 	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{	
		HttpSession ss = req.getSession();		
		Admin user = (Admin)ss.getAttribute("admin");
		
		// 构造JS
		String jsContent = null;
		if( user == null)
		{
			// 尚未登录
			jsContent = "var admin = null;";
		}
		else
		{
			JsonObject json =new JsonParser().parse(new Gson().toJson(user)).getAsJsonObject() ;
			json.remove("password"); // 去掉一些前端用不到的、或者敏感字段			
			jsContent = "var admin = " + new Gson().toJson(json) + " ; ";
		}
				
		// 应答
		resp.setContentType("application/javascript");
		resp.setCharacterEncoding("UTF-8");
		resp.addHeader ("Cache-Control", "no-cache"); // 禁止客户端缓存此文件
		resp.getWriter().print( jsContent );
	}
}
