package com.coffee.user;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffee.db.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


@WebServlet("/UserInfoJson")
public class UserInfoJson extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{	
		HttpSession ss = req.getSession();		
		User user = (User)ss.getAttribute("user");
		
		// 构造JS
		String js = null;
		if( user == null)
		{
			// 尚未登录
			js = "var user = null;";
		}
		else
		{
			
			JsonObject json =new JsonParser().parse(new Gson().toJson(user)).getAsJsonObject() ;
			json.remove("password"); // 去掉一些前端用不到的、或者敏感字段
			json.remove("timeCreated");
			
			js = "var user = " + new Gson().toJson(json) + " ; ";
		}
				
		// 应答
		resp.setContentType("application/javascript");
		resp.setCharacterEncoding("UTF-8");
		resp.addHeader ("Cache-Control", "no-cache"); // 禁止客户端缓存此文件
		resp.getWriter().print( js );
	}
}
