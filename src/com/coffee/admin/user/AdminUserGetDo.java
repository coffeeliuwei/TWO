package com.coffee.admin.user;

import org.json.JSONObject;

import com.coffee.DB.DB;
import com.coffee.db.User;
import com.coffee.mysql.util.SqlWhere;
import com.coffee.web.restful.RestfulDo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;



public class AdminUserGetDo extends RestfulDo
{

	@Override
	public Object execute(JsonObject jreq) throws Exception
	{
		// 请求
		int id = jreq.get("id").getAsInt();
		
		// where
		SqlWhere asw = new SqlWhere();
		asw.add2("id", id);
		
		// 查询
		String sql = "select * from bbs_user " + asw;
		User user = (User) DB.get(sql, User.class);
		if(user == null)
			throw new Exception("无此用户, id=" + id);
		
		// 应答
		JsonObject jdata =new JsonParser().parse(new Gson().toJson(user)).getAsJsonObject() ;
		jdata.remove("password"); // 去除前端不需要看到的字段
		return jdata;
	}

}
