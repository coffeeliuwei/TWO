package com.coffee.bbsplus;

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
		bbs bbs = new bbs();
		bbs.setContent(content);
		bbs.setTitle(title);
		bbs.setUserId(user.getId()); // 注意：取当前用户的ID
		bbs.setTimeCreated(new Date());
		bbs.setTimeMofified(new Date());
		
		bbs.setFlagNice((byte)0);
		bbs.setFlagTop((byte)0);
		bbs.setNumReply(0);;
		bbs.setNumView(0);;
		
		// 插入数据库
		DB.insert( bbs );
		
		// 更新最近列表
		RecentBBS.i.add(bbs, user);
		
		// 应答
		JsonObject jdata = new JsonObject();
		jdata.addProperty("id", bbs.getId()); // 帖子的ID		
		return jdata;
	}
	
}
