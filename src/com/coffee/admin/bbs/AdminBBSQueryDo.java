package com.coffee.admin.bbs;

import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.coffee.DB.DB;
import com.coffee.mysql.util.SqlWhere;
import com.coffee.web.restful.RestfulDo;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


public class AdminBBSQueryDo extends RestfulDo
{
	@Override
	public Object execute(JsonObject jreq) throws Exception
	{
		// 查询条件 (暂无)
				SqlWhere where = new SqlWhere(); 		
				// 排序
				String order = " ORDER BY ID DESC ";
				// 条数限制
				String limit = " LIMIT 30 ";
				
				// 双表联合查询
				String sql = " SELECT a.id, a.title, a.timeCreated, b.username ,a.userId"
						+ " FROM bbs a LEFT JOIN `bbs_user` b "
						+ " ON a.userId=b.id "
						+ where
						+ order
						+ limit;
				System.out.println("查询: " + sql);
				
				// 返回的是原生结果，每行数据是一个 String[]
				List<String[]> rows = DB.query(sql);
				
				// 处理为前端需要的结果
				JsonArray jdata = new JsonArray();
				for(int i=0; i<rows.size(); i++)
				{
					String[] row = rows.get(i);			
					JsonObject j1 = new JsonObject();
					
					int k=0;
					j1.addProperty("id", row[k++]); //帖子id, 不转成Long也可以
					j1.addProperty("title", row[k++]);
					j1.addProperty("timeCreated", row[k++]);
					j1.addProperty("username", row[k++]);
					j1.addProperty("userId", row[k++]); // 用户ID
					
					jdata.add( j1 );
				}
			
				return jdata;
			}
	
}
