package com.context.config;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.demo.util.JqGridUtil;
import com.demo.util.ZcMap;

public class DbTest {

	  
		public static void main(String[] args)throws Exception {  
		ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
				new String[] { "classpath:com/context/config/applicationContext.xml" });
		try {
			Support jagadeesh1=new Support("jagadeesh");
			Support jagadeesh2=new Support("jagadeesh");
			Support praneeth=new Support("praneeth");
			Support vikram=new Support("vikram");
			System.out.println(jagadeesh1);
			System.out.println(jagadeesh2);
			System.out.println(praneeth);
			System.out.println(vikram);
			System.out.println("--------");
			System.out.println(praneeth.dynamicService.getUsers((new JqGridUtil(new ZcMap()))).get("totalCount"));
			System.out.println(praneeth.dynamicService.updateUser(new ZcMap() {{put("name", "Praneeth");}},1));
			System.out.println(praneeth.dynamicService.getUserById(1));
			//System.out.println(jagadeesh1.dynamicService.getUsers((new JqGridUtil(new ZcMap()))).get("totalCount"));
			//System.out.println(jagadeesh1.dynamicService.getUserById(1));
			//System.out.println(jagadeesh2.dynamicService.getUserById(1));
		//	System.out.println(praneeth.dynamicService.getUserById(1));
			//System.out.println(vikram.dynamicService.getUserById(1));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			appContext.close();
		}

	}
}
