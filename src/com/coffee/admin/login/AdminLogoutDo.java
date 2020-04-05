package com.coffee.admin.login;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.coffee.web.restful.RestfulDo;
import com.google.gson.JsonObject;


public class AdminLogoutDo extends RestfulDo
{
	
	@Override
	public Object execute(JsonObject jreq) throws Exception
	{
		HttpSession ss = this.httpReq.getSession();
		ss.removeAttribute("admin"); 
		
		return null;
	}

}
