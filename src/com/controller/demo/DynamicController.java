package com.controller.demo;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.context.config.Support;
import com.demo.dynamic.service.DynamicService;
import com.demo.util.AppUtil;
import com.demo.util.JqGridUtil;
import com.demo.util.ZcMap;
import com.demo.util.ZcUtil;


@RestController
@Repository (value = "dynamicController")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DynamicController extends BaseController{
	public static DefaultListableBeanFactory context = null;
            
	public DynamicService dynamicService;

	public DynamicController(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		String reqUri = request.getRequestURI();
		if(!AppUtil.isBlank(contextPath)) {
			reqUri = AppUtil.replace(reqUri, contextPath, "");
		}
		String[] data=reqUri.split("/");
		client=data[1];
		Support service=new Support(client);
		dynamicService=service.dynamicService;
	}
	
	
	@RequestMapping(value ="/{client}/getUserById/{id}", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> getUserById(HttpServletRequest request,@PathVariable int id, @PathVariable String client) throws Exception {
		Map<String,Object> result=new HashMap<String, Object>(); 
		String res=dynamicService.getUserById(id);
		result.put("success",res==null?false:true);
		result.put("name",res);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value ="/{client}/getUsers", method = RequestMethod.POST)
	public ResponseEntity<ZcMap> getUsers(@RequestBody ZcMap reqData, HttpServletRequest request) throws Exception {
		JqGridUtil jqGridUtil=new JqGridUtil(reqData);
		ZcMap result=new ZcMap(); 
		result.put("success",true);
		ZcMap data=dynamicService.getUsers(jqGridUtil);
		result.put("data", ZcUtil.getJqGridData(reqData, data, jqGridUtil.page));
		return new ResponseEntity(result,HttpStatus.OK);
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value ="/{client}/saveUser", method = RequestMethod.POST)
	public ResponseEntity<ZcMap> saveUser(@RequestBody ZcMap reqData, HttpServletRequest request) throws Exception {
		ZcMap result=new ZcMap(); 
		int data=0;
//		try {
			data=dynamicService.saveUser(reqData);
			result.put("success",true);
			result.put("message", "Successfully Inserted");
			result.put("id",data);
//		}catch (Exception e) {
//			result.put("success",false);
//			result.put("message", "Not Inserted");
//		}
		return new ResponseEntity(result,HttpStatus.OK);
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value ="/{client}/updateUser/{id}", method = RequestMethod.PUT)
	public ResponseEntity<ZcMap> updateUser(@RequestBody ZcMap reqData, HttpServletRequest request,@PathVariable int id) throws Exception {
		ZcMap result=new ZcMap(); 
		int data=0;
	//	try {
			data=dynamicService.updateUser(reqData,id);
			result.put("success",true);
			result.put("message", "Successfully Updated");
			result.put("id",data);
//		}catch (Exception e) {
//			result.put("success",false);
//			result.put("message", "Update failed");
//		}
		return new ResponseEntity(result,HttpStatus.OK);
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value ="/{client}/deleteUser/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<ZcMap> deleteUser(HttpServletRequest request,@PathVariable int id) throws Exception {
		ZcMap result=new ZcMap(); 
		try {
			dynamicService.deleteUser(id);
			result.put("success",true);
			result.put("message", "Successfully deleted");
		}catch (Exception e) {
			result.put("success",false);
			result.put("message", "Deletion failed");
		}
		return new ResponseEntity(result,HttpStatus.OK);
	}
	
}
