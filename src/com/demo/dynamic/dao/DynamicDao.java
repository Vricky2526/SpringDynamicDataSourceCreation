package com.demo.dynamic.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.jdbc.core.JdbcTemplate;
import com.context.config.AppDataSource;
import com.context.config.Support;
import com.demo.util.JqGridUtil;
import com.demo.util.ZcMap;
import com.demo.util.ZcUtil;

public class DynamicDao extends DaoCommon{
	public static DynamicDao get(String client) {
		try {
			return (DynamicDao)Support.context.getBean(client+"DynamicDao");
		}catch (NoSuchBeanDefinitionException e) {
			GenericBeanDefinition gbd = new GenericBeanDefinition();
		    	gbd.setBeanClass(DynamicDao.class);
		    	gbd.getPropertyValues().addPropertyValue("client", client);
		    	Support.context.registerBeanDefinition(client+"DynamicDao", gbd);
		    return (DynamicDao)Support.context.getBean(client+"DynamicDao");
		}
	}
	public void setClient(String client) throws SQLException {
		jdbcTemplate=new JdbcTemplate(AppDataSource.get(client));
		
	}
	private JdbcTemplate jdbcTemplate; 

	public String getUserById(int id) {
		if(jdbcTemplate==null) return null;
		try {
			Map<String, Object> data= jdbcTemplate.queryForMap("SELECT * FROM app_user WHERE pk_id=?",id);
			return data.get("name")+"";
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@SuppressWarnings("serial")
	public ZcMap getUsers(JqGridUtil req) throws Exception {
		if(jdbcTemplate==null) return null;
		ZcMap cols =  new ZcMap(new Object[][]{{"name","name"},{"pk_id", "pk_id"}});
		if(ZcUtil.isBlank(req.sidx))req.sidx=cols.getS("name");
		else req.sidx=cols.getS(req.sidx);
		String query="SELECT SQL_CALC_FOUND_ROWS name,pk_id FROM app_user  LIMIT "+req.start+","+req.rows+"";
		List<Map<String, Object>> data=jdbcTemplate.queryForList(query);
		int totalCount=ZcUtil.parseInt(jdbcTemplate.queryForMap("SELECT found_rows() rows").get("rows"));
		return new ZcMap(){{
			put("rows",data);
			put("totalCount",totalCount); 
		}};
	}
	public int updateUser(ZcMap reqData, int id) {
		jdbcTemplate.update("UPDATE app_user SET name=? WHERE pk_id=?",reqData.getS("name"),id);
		jdbcTemplate.queryForList("SELECT * FROM app_user WHERE ;pk_id=1;");
		return id;
		//
	}
	
	
	public int saveUser(ZcMap reqData) throws Exception {
		int id=getGeneratedKey(jdbcTemplate, "INSERT INTO app_user(`name`) VALUES (?)", new String[] {reqData.getS("name")});
		return id;
	}
	
	public void deleteUser(int id) {
		jdbcTemplate.update("DELETE FROM app_user WHERE pk_id=?",id);
	}
	
}
