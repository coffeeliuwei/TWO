package com.coffee.admin.user;

import com.coffee.DB.DB;
import com.coffee.db.Admin;
import com.coffee.mysql.util.SqlUpdate;
import com.coffee.mysql.util.SqlWhere;
import com.coffee.web.restful.RestfulDo;
import com.google.gson.JsonObject;



public class AdminUserSetDo extends RestfulDo
{

	@Override
	public Object execute(JsonObject jreq) throws Exception
	{
		int id = jreq.get("id").getAsInt();
		
		// 登录检查防止前台直接不登录进行破解
		Admin admin = (Admin) httpReq.getSession().getAttribute("admin");
		if(admin == null)
			throw new Exception("请先登录管理员界面!");
		
		// where
		SqlWhere asw = new SqlWhere();
		asw.add2("id", id);
		
		// update
		SqlUpdate asu = new SqlUpdate("bbs_user");
		asu.add2("canRead", jreq.get("canRead").getAsBoolean());
		asu.add2("canPost", jreq.get("canPost").getAsBoolean());
		asu.add2("canReply", jreq.get("canReply").getAsBoolean());
					
		// 执行更新
		String sql = asu + "" + asw;
		DB.execute( sql );
		
		return null;
	}

}
