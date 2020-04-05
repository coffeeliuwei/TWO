package com.coffee.admin.login;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.coffee.DB.DB;
import com.coffee.db.Admin;
import com.coffee.mysql.util.SqlWhere;
import com.coffee.web.restful.RestfulDo;
import com.google.gson.JsonObject;

public class AdminLoginDo extends RestfulDo
{

	@Override
	public Object execute(JsonObject jreq) throws Exception
	{
		String name = jreq.get("username").getAsString();
		String password = jreq.get("password").getAsString();
		
		// 查询数据库
		SqlWhere asw = new SqlWhere();
		asw.add2("username", name);
		
		String sql = "select * from bbs_admin " + asw;
		System.out.println("Admin登录查询: " + sql);
		Admin row = (Admin) DB.get(sql, Admin.class);
		
		// 认证
		if(row == null)
		{
			throw new Exception("无此用户, admin=" + name);
		}
		else if(! row.getPassword().equals(password))
		{
			throw new Exception("密码不匹配!");
		}
		
		// 把用户信息保存到当前会话
		HttpSession ss = this.httpReq.getSession();
		ss.setAttribute("admin", row); // 放一个Admin对象
		
		return null;
	}

}
