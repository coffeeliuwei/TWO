package com.coffee.bbsplus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.coffee.DB.DB;
import com.coffee.db.User;
import com.coffee.db.bbs;
import com.coffee.mysql.util.SqlWhere;



public class RecentBBS
{
	// 全局对象 
	public static RecentBBS i = new RecentBBS();
	
	// 这个 list 用于存放最新的20条记录
	private List<Item> list = new ArrayList<RecentBBS.Item>();
	private int MAXSIZE = 20;
	
	public synchronized void add(bbs topic, User user)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		Item item = new Item();
		item.id = topic.id;
		item.title = topic.title;
		item.timeCreated = sdf.format( topic.getTimeCreated());
		item.username = user.username;
		
		List<Item> copy = new ArrayList<RecentBBS.Item>();
		copy.add( 0, item);
		if( copy.size() > MAXSIZE)
		{
			copy.remove( copy.size() - 1); //删除最后一个
		}
		
		this.list = copy;
	}
	
	// 用于显示在首页
	public List<Item> getList()
	{
		return this.list;
	}
	
	// 首页显示用户名
	public static class Item
	{
		public long id; // bbs id
		public String title; // bbs title
		public String timeCreated; // yyyy-MM-dd HH:mm:ss
		public String username; // username
	}
	
	// 从数据库加载最新帖子列表
	public void load() throws Exception
	{
		// 查询条件 (暂无)
		SqlWhere asw = new SqlWhere(); 		
		// 排序
		String order = " ORDER BY ID DESC ";
		// 条数限制
		String limit = " LIMIT " + MAXSIZE;
		
		// 双表联合查询
		String sql = " SELECT a.id, a.title, a.timeCreated, b.username "
				+ " FROM bbs a LEFT JOIN `bbs_user` b "
				+ " ON a.userId=b.id "
				+ asw
				+ order
				+ limit;
		System.out.println("查询: " + sql);
		
		// 返回的是原生结果，每行数据是一个 String[]
		List<String[]> rows = DB.query(sql);
		
		// 处理为前端需要的结果
		for(int i=0; i<rows.size(); i++)
		{
			String[] row = rows.get(i);			
			
			Item item = new Item();
			int k=0;
			item.id = Long.valueOf( row[k++]);
			item.title = row[k++];
			item.timeCreated = row[k++];
			item.username = row[k++];
			
			this.list.add(item);
		}		
	}
}
