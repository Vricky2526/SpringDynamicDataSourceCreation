package com.context.config;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.demo.dynamic.service.DynamicService;

public class Support {
	public static DefaultListableBeanFactory context = null;
	public DynamicService dynamicService;
	public Support(String client) {
		if(Support.context==null) Support.context=new DefaultListableBeanFactory();
		dynamicService=DynamicService.get(client);
	}
}
