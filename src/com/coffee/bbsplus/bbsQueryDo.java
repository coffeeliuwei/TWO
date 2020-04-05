package com.coffee.bbsplus;

import java.util.List;

import com.coffee.web.restful.RestfulDo;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;



public class bbsQueryDo extends RestfulDo
{
	@Override
	public Object execute(JsonObject jreq) throws Exception
	{	
		// 从缓存中取得列表	
		List<RecentBBS.Item> list = RecentBBS.i.getList();
		
		// 格式化为 JSON 返回
		JsonArray jdata = new JsonArray();
		for(int i=0; i<list.size(); i++)
		{
			RecentBBS.Item item = list.get(i);
			JsonObject j1 = new JsonObject();
			j1.addProperty("id", item.id);
			j1.addProperty("title", item.title);
			j1.addProperty("timeCreated", item.timeCreated);
			j1.addProperty("username",item.username);
			
			jdata.add(j1);
		}
		
		return jdata;
	}
	
}
