package com.context.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.support.AutowireCandidateQualifier;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

public class AppDataSource{
	public static String transaction="";
	public static BasicDataSource get(String client) {
		try {
			return (BasicDataSource)Support.context.getBean(client+"BasicDataSource");
		}catch (NoSuchBeanDefinitionException e) {
			GenericBeanDefinition gbd = new GenericBeanDefinition();
	    	gbd.setBeanClass(BasicDataSource.class); 
	    		Map<String,Object> data=clientDetails(client);
	    		for(Map.Entry<String, Object> entry:data.entrySet()) {
	    			gbd.getPropertyValues().addPropertyValue(entry.getKey(),entry.getValue());
	    		}
	    	Support.context.registerBeanDefinition(client+"BasicDataSource", gbd);
	    	BasicDataSource basicDataSource=(BasicDataSource)Support.context.getBean(client+"BasicDataSource");
	    	attachTransactionManager(basicDataSource, client);
	    	return basicDataSource;
		}
	}
	public static DataSourceTransactionManager getTransactionManager(String client) {
		return (DataSourceTransactionManager)Support.context.getBean(client+"TransactionManager");
	}
	public static void attachTransactionManager(BasicDataSource basicDataSource,String client) {
		GenericBeanDefinition gbd = new GenericBeanDefinition();
    	gbd.setBeanClass(DataSourceTransactionManager.class); 
    	gbd.getPropertyValues().addPropertyValue("dataSource",basicDataSource);
    	gbd.addQualifier(new AutowireCandidateQualifier(client+"_TM"));
    	//transaction=client+"TransactionManager";
    	System.out.println(client+"TransactionManager");
    	Support.context.registerBeanDefinition(client+"TransactionManager", gbd); 
	}
	
	public static Map<String,Object> clientDetails(String client) {
		String host="";
		String db=client;
		if("jagadeesh".equals(client)) {
			host="192.168.0.43";
		}else if("praneeth".equals(client)) {
			host="192.168.0.38";
		}else if("vikram".equals(client)) {
			host="192.168.0.51";
		}
		Map<String,Object> data=new HashMap<String,Object>();
			data.put("driverClassName", "com.mysql.cj.jdbc.Driver");
			data.put("url", "jdbc:mysql://"+host+":3306/"+db+"?allowMultiQueries=true&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC");
			data.put("username","admin");
			data.put("password","the@123");
			data.put("poolPreparedStatements","true");
			data.put("maxIdle",10);
			data.put("validationQuery","SELECT 1");
			data.put("testOnBorrow","true");
			data.put("connectionProperties","autoReconnect=true;useUnicode=true;characterEncoding=UTF-8");
		return data;
	}
	
}
