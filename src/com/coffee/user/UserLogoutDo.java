package com.coffee.user;

import javax.servlet.http.HttpSession;

import com.coffee.web.restful.RestfulDo;
import com.google.gson.JsonObject;




public class UserLogoutDo extends RestfulDo
{
	
	@Override
	public Object execute(JsonObject jreq) throws Exception
	{
		HttpSession ss = this.httpReq.getSession();
		ss.removeAttribute("user"); // 或者 ss.setAttribute("user", null);
		
		return null;
	}

}
