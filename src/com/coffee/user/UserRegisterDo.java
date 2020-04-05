package com.coffee.user;

import java.util.Date;

import org.json.JSONObject;

import com.coffee.DB.DB;
import com.coffee.db.User;
import com.coffee.web.restful.RestfulDo;
import com.google.gson.JsonObject;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;



public class UserRegisterDo extends RestfulDo
{

	@Override
	public Object execute(JsonObject jreq) throws Exception
	{
		String username = jreq.get("username").getAsString();
		String password = jreq.get("password").getAsString();
		
		User row = new User();
		row.setUsername( username);
		row.setPassword( password);
		row.setCanPost(true);
		row.setCanRead(true);
		row.setCanReply(true);
		row.setLevel(0);
		row.setTimeCreated(new Date());
		
		// 插入一条记录
		try{
			DB.insert( row );
		}catch(MySQLIntegrityConstraintViolationException e)
		{
			// 否则抛出 com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException
			throw new Exception("用户名重复!");
		}
		
		return null;
	}

}
