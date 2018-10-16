package com.demo.dynamic.service;

import java.sql.SQLException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import com.context.config.AppDataSource;
import com.context.config.Support;
import com.demo.dynamic.dao.DynamicDao;
import com.demo.util.JqGridUtil;
import com.demo.util.ZcMap;

public class DynamicService {
	private DynamicDao dynamicDao;
	private TransactionTemplate transactionTemplate;
	private static final String CLASS_NAME=DynamicService.class.getSimpleName();
	
	public static DynamicService get(String client) {
		try {
			return (DynamicService)Support.context.getBean(client+CLASS_NAME);
		}catch (NoSuchBeanDefinitionException e) {
			GenericBeanDefinition gbd = new GenericBeanDefinition();
		    	gbd.setBeanClass(DynamicService.class);
		    	gbd.getPropertyValues().addPropertyValue("client", client);
		    	Support.context.registerBeanDefinition(client+CLASS_NAME, gbd);
		     return (DynamicService)Support.context.getBean(client+CLASS_NAME);
		}
	}
	public void setClient(String client) {
		dynamicDao=DynamicDao.get(client);
		transactionTemplate=new TransactionTemplate(AppDataSource.getTransactionManager(client));
	}

	public String getUserById(int id) throws SQLException {
		return dynamicDao.getUserById(id);
	}
	
 
	public ZcMap getUsers(JqGridUtil jqGridUtil){
	    return transactionTemplate.execute(new TransactionCallback<ZcMap>(){
		     public ZcMap doInTransaction(TransactionStatus status){                
		        try {
					return dynamicDao.getUsers(jqGridUtil);
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}    
		     }
	    }); 
	}
	
	public Integer updateUser(ZcMap reqData, int id) {
		return transactionTemplate.execute(new TransactionCallback<Integer>(){
			 public Integer doInTransaction(TransactionStatus status){                
			        try {
			        	return dynamicDao.updateUser(reqData,id);
					} catch (Exception e) {
						status.setRollbackOnly();
						e.printStackTrace();
						return null;
					}    
			     }
		    }); 
	}
	
	public int saveUser(ZcMap reqData) throws Exception {
		return dynamicDao.saveUser(reqData);
	}
	
	public void deleteUser(int id) {
		 dynamicDao.deleteUser(id);
	}
}
