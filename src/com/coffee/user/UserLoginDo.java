package com.coffee.user;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.coffee.DB.DB;
import com.coffee.db.User;
import com.coffee.mysql.util.SqlWhere;
import com.coffee.web.restful.RestfulDo;
import com.google.gson.JsonObject;



public class UserLoginDo extends RestfulDo
{
	
	@Override
	public Object execute(JsonObject jreq) throws Exception
	{
		String username = jreq.get("username").getAsString();
		String password = jreq.get("password").getAsString();
		
		// 查询数据库
		SqlWhere where = new SqlWhere();
		where.add2("username", username);
		
		String sql = "select * from bbs_user " + where;
		System.out.println("登录查询: " + sql);
		User row = (User) DB.get(sql, User.class);
		
		// 认证
		if(row == null)
		{
			throw new Exception("无此用户, username=" + username);
		}
		else if(! row.getPassword().equals(password))
		{
			throw new Exception("密码不匹配!");
		}
		
		// 把用户信息保存到当前会话
		HttpSession ss = this.httpReq.getSession();
		ss.setAttribute("user", row); // 放一个User对象
		
		return null;
	}

}
