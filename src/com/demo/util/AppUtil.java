package com.demo.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

public class AppUtil {

	public final static void putVersionClientInAttributes(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		String reqUri = request.getRequestURI();
		if(!isBlank(contextPath)) {
			reqUri = replace(reqUri, contextPath, "");
		}
		String[] data=reqUri.split("/");
		if(data.length > 2) {
			request.setAttribute("api-version", data[1]);
			request.setAttribute("api-client", data[2]);
		}else {
			request.setAttribute("api-version", "");
			request.setAttribute("api-client", "");
		}
	}
	
	public static boolean isBlank(Object o) {
		if (o == null)
			return true;
		else if (o instanceof String) {
			if (((String) o).trim().equals(""))
				return true;
		} else if (o instanceof Collection<?>) {
			if (((Collection<?>) o).isEmpty())
				return true;
		} else if (o instanceof Integer) {
			if (((Integer) o) <= 0)
				return true;
		} else if (o instanceof Long) {
			if (((Long) o) <= 0)
				return true;
		} else if (o instanceof Short) {
			if (((Short) o) <= 0)
				return true;
		} else if (o instanceof Byte) {
			if (((Byte) o) <= 0)
				return true;
		} else if (o instanceof Double) {
			if (((Double) o) <= 0)
				return true;
		} else if (o instanceof Float) {
			if (((Float) o) <= 0)
				return true;
		} else if (o instanceof Map<?, ?>) {
			if (((Map<?, ?>) o).isEmpty())
				return true;
		}
		return false;
	}
	
	public static String replace(String sOriginal, String sReplaceThis, String sWithThis) {
		int start = 0;
		int end = 0;
		StringBuffer sbReturn = new StringBuffer();

		if (sOriginal != null) {
			while ((end = sOriginal.indexOf(sReplaceThis, start)) >= 0) {
				sbReturn.append(sOriginal.substring(start, end));
				sbReturn.append(sWithThis);
				start = end + sReplaceThis.length();
			}
			sbReturn.append(sOriginal.substring(start));
		} else {
			sbReturn.append("");
		}

		return (sbReturn.toString());
	}
	
	@SuppressWarnings("serial")
	public static void main(String[] args) {
		String str="&ampassa&";
		
		
		 ArrayList<Integer> arrL = new ArrayList<Integer>(); 
	        arrL.add(1); 
	        arrL.add(2); 
	        arrL.add(3); 
	        arrL.add(4); 
		arrL.forEach(x -> {
			if (x % 2 == 0)
				System.out.println(x);
		});
		
		/*List<String> data=new ArrayList<String>(){{
			add("Praneeth");
			add("Vikram");
			add("Mahesh");
			add("Raja");
			add("Rakesh");
		}};
		
		List<String> result=data.stream().filter(p->p.equals("Praneeth")).map(String::new).collect(Collectors.toList());
		System.out.println(result);
		System.out.println("Hello "+result);*/
	}


	
}
