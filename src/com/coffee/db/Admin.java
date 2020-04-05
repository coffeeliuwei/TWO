package com.coffee.db; 
import com.coffee.mysql.annotation.COLUMNS;
import com.coffee.mysql.annotation.TABLE;
import java.util.Date;
@TABLE(name="bbs_admin")  
@COLUMNS(auto=true,generated="id") 
public class Admin 
{ 
 
	public Integer id ; 
	public String username ; 
	public String password ; 
	public Date timeCreated ; 


	public void setId(Integer id)
	{
		this.id=id;
	}
	public Integer getId()
	{
		return this.id;
	}
	public void setUsername(String username)
	{
		this.username=username;
	}
	public String getUsername()
	{
		return this.username;
	}
	public void setPassword(String password)
	{
		this.password=password;
	}
	public String getPassword()
	{
		return this.password;
	}
	public void setTimeCreated(Date timeCreated)
	{
		this.timeCreated=timeCreated;
	}
	public Date getTimeCreated()
	{
		return this.timeCreated;
	}

} 
 