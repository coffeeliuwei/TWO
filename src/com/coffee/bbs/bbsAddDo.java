package com.coffee.bbs;

import java.util.Date;

import org.json.JSONObject;

import com.coffee.DB.DB;
import com.coffee.db.User;
import com.coffee.db.bbs;
import com.coffee.web.restful.RestfulDo;
import com.google.gson.JsonObject;



public class bbsAddDo extends RestfulDo
{

	@Override
	public Object execute(JsonObject jreq) throws Exception
	{
		String title = jreq.get("title").getAsString();
		String content = jreq.get("content").getAsString();
		
		// 从当前会话里取得当前用户信息
		User user = (User) httpReq.getSession().getAttribute("user");
		if(user == null)
			throw new Exception("请先登录!");
		if( ! user.canPost )
			throw new Exception("已被禁言，不能发帖");
		
		// 准备数据
		bbs b = new bbs();
		b.setContent(content);
		b.setTitle(title);
		b.setUserId(user.getId()); // 注意：取当前用户的ID
		b.setTimeCreated(new Date());
		b.setTimeMofified(new Date());
		
		b.setFlagNice((byte)0);
		b.setFlagTop((byte)0);
		b.setNumReply(0);;
		b.setNumView(0);;
		
		// 插入数据库
		DB.insert( b );
		
		// 应答
		JsonObject jdata = new JsonObject();
		jdata.addProperty("id", b.getId()); // 帖子的ID		
		return jdata;
	}
	
}
