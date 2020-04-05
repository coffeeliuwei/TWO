package com.coffee.bbs;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coffee.DB.DB;
import com.coffee.db.bbs;
import com.coffee.mysql.util.SqlWhere;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateNotFoundException;



@WebServlet("/bbs2Query")
public class bbs2QueryDo extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	protected Configuration frmkConfig;
	
	@Override
	public void init() throws ServletException
	{
		// 初始化FreeMarker 
		File appRoot = new File(getServletContext().getRealPath("/02/"));
		try{
			frmkConfig = new Configuration(Configuration.VERSION_2_3_28);
			frmkConfig.setDirectoryForTemplateLoading(appRoot); // 设置模板根目录
			frmkConfig.setDefaultEncoding("UTF-8");
			frmkConfig.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
			frmkConfig.setLogTemplateExceptions(false);
		}
		catch(Exception e)
		{		
			System.out.println("This Should Not Happen!");
		}		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		
		// 生成 model
		Map<String,Object> model = new HashMap<String,Object>();
		String view = "index2.html";
		try{
			SqlWhere where = new SqlWhere(); 		
			// 排序
			String order = " ORDER BY ID DESC ";
			// 条数限制
			String limit = " LIMIT 30 ";
			
			// 双表联合查询
			String sql = " SELECT a.id, a.title, a.timeCreated, b.username "
					+ " FROM bbs a LEFT JOIN `bbs_user` b "
					+ " ON a.userId=b.id "
					+ where
					+ order
					+ limit;
			System.out.println("查询: " + sql);
			
			// 返回的是原生结果，每行数据是一个 String[]
			List<Map> rows = DB.query(sql,0);
			model.put("bbs", rows);
		}
		catch(Exception e)
		{
			resp.sendError(500, e.getMessage());
			return;
		}
		
		// 以下为固定套路, 根据 model 和 view 作出应答
		Template tp = null;
		try{
			tp = frmkConfig.getTemplate(view); 
		}catch(TemplateNotFoundException ex)
		{
			resp.sendError(404, "Cannot find view: " + view );
			return; // 目标HTML不存在，则直接返回404
		}
		
		// 处理并返回应答
		try{			
			tp.process(model, resp.getWriter()); // 输出给客户端
		}catch(Exception e)
		{
			//e.printStackTrace();
			resp.sendError(500, e.getMessage());
		}
	}

}
