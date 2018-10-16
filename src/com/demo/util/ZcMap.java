package com.demo.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.fasterxml.jackson.databind.ObjectMapper;


 
/** 
 * ZcMap is a customized LinkedHashMap for ZeroCode V2
 * 
 * @author Jagadeesh.T
 */
public class ZcMap extends LinkedHashMap<String,Object>{

	private static final long serialVersionUID = 2324896501900022794L;

	public final static ZcMap EMPTY=new ZcMap();
	
	/**
	 * Default Constructor 
	 * 
	 * @author Jagadeesh.T
	 * @since 2018-07-20
	 */
	public ZcMap() {
		super();
	}
	
	/**
	 * Constructor with object two dimension array
	 * 
	 * @param data
	 * @author Jagadeesh.T
	 * @since 2018-07-20
	 */
	public ZcMap(Object[][] data) {
		super();
		for(Object[] o:data) {
			put((String)o[0], o[1]);
		}
	}
	
	/**
	 * Removes the given keys
	 * 
	 * @param keys
	 * @author Jagadeesh.T
	 * @since 2018-07-20
	 */
	public void removeKey(String...keys) {
		for(String s:keys) {
			this.remove(s);
		}
	}
	
	public static ZcMap asZcMap(Map<String,Object> x) {
		ZcMap data=new ZcMap();
		data.putAll(x);
		return data;
	}
	
	public void toTitleCase(String key) {
		put(key, ZcUtil.titleCase(getS(key)));
	}
	public void toSentenceCase(String key) {
		put(key, ZcUtil.toSentenceCase(getS(key)));
	}
	public void toUpperCase(String key) {
		put(key, get(key)==null?null:getS(key).toUpperCase());
	}
	
	/**
	 * It clones the map object
	 * 
	 * @author Jagadeesh.T
	 * @since 2018-07-20
	 */
	@Override
	public ZcMap clone() {
		return (ZcMap)super.clone();
	}

	/** 
	 * Gets Object from the Map.
	 * 
	 * @param k
	 * @return Object
	 * @author Jagadeesh.T
	 * @since 2018-07-20
	 */
	public final Object getO(String k) {
		return get(k);
	}
	
	/**
	 * Verifies if the object with the key is Blank and
	 * returns true if it is blank else false.
	 * 
	 * @param k
	 * @return boolean
	 * @author Jagadeesh.T
	 * @since 2018-07-20
	 */
	public final boolean isBlank(String k) {
		return ZcUtil.isBlank(get(k));
	}
	
	/**
	 * Gets trimmed String from the Map.
	 * 
	 * @param k
	 * @return String/null
	 * @author Jagadeesh.T
	 * @since 2018-07-20
	 */
	public final String getS(String k) {
		return this.get(k)==null?null:this.get(k).toString().trim();
		//return this.get(k)==null?null: (((this.get(k).toString().trim()).length()>0) ? (String)(this.get(k).toString().trim()):null);
	}
	
	/**
	 * Verifies if the object is blank
	 * returns null if it is blank.
	 * 
	 * @param k
	 * @return String/null
	 * @author Jagadeesh.T
	 * @since 2018-07-20
	 */
	public final String getNullIfBlankS(String k) {
		String s=getS(k);
		return ZcUtil.isBlank(s)?null:s;
	}
	
	/**
	 * Verifies if the given value exists in the option of the given key.
	 * 
	 * @param k
	 * @param value
	 * @return boolean
	 * @author Jagadeesh.T
	 * @since 2018-07-20
	 */
	@SuppressWarnings("unchecked")
	public final boolean isValueSelected(String k,String value) {
		return ((List<Map<String,Object>>)get(k)).stream().filter(x->x.get("value").equals(value)).count()!=0;
	}
	
	public final ZcMap getZcMap(String k)throws IOException {
		if(this.get(k)==null) return EMPTY.clone();
		if(this.get(k) instanceof String) {
			if(isBlank(k)) return EMPTY.clone();
			if(this.getS(k).trim().charAt(0)=='{') {
				return new ObjectMapper().reader().forType(ZcMap.class).readValue(this.getS(k));
			}else {
				return null;
			}
		}else if(this.get(k) instanceof ZcMap)
			return (ZcMap) this.get(k);
		else
			return new ObjectMapper().convertValue(this.get(k), ZcMap.class);
	}
	
	
	
	
	/*
	public final String getUtcStringDate(String k)throws Exception {
		java.sql.Timestamp sqlTime=(java.sql.Timestamp)get(k);
		return ZcUtil.getUtcStringFromDate(new Date(sqlTime.getTime()));
	}
	*/
	
	/**
	 * Parses the object to Integer Object type.
	 * 
	 * @param k
	 * @return Integer Object
	 * @author Jagadeesh.T
	 * @since 2018-07-20
	 */
	public final Integer getIo(String k) {
		return ZcUtil.parseInt(this.get(k));
	}	
	
	/**
	 * Parses the Object to primitive int type.
	 * 
	 * @param k
	 * @return int
	 * @author Jagadeesh.T
	 * @since 2018-07-20
	 */
	public final int getI(String k) {
		Integer i=ZcUtil.parseInt(this.get(k));
		return i==null?0:i;
	}
	
	
	
	
	

	/**
	 * Parses the object to Boolean Object type
	 * 
	 * @param k
	 * @return Boolean Object type
	 * @author Jagadeesh.T
	 * @since 2018-07-20
	 */
	public final Boolean getBo(String k) {
		return this.get(k)==null?null:ZcUtil.parseBoolean(this.get(k));
	}
	
	/**
	 * Parses the object to boolean primitive type
	 * 
	 * @param k
	 * @return boolean 
	 * @author Jagadeesh.T
	 * @since 2018-07-20
	 */
	public final boolean getB(String k) {
		return this.get(k)==null?false:ZcUtil.parseBoolean(this.get(k));
	}	
	
	/**
	 * Gets trimmed String from the Map to 160 characters, used for Text field
	 * 
	 * @param k
	 * @return String
	 * @author Jagadeesh.T
	 * @since 2018-07-20
	 */
	public final String getText(String k) {
		return ZcUtil.truncate(getS(k), 160);
	}
	
	/**
	 * Gets trimmed String from the Map to 1024 characters, used for Description field
	 * 
	 * @param k
	 * @return String
	 * @author Jagadeesh.T
	 * @since 2018-07-20
	 */
	public final String getDesc(String k) {
		return ZcUtil.truncate(getS(k), 1024);
	}
	
	/**
	 * Converts the Map to JsonString
	 * 
	 * @return String
	 * @throws InvalidJsonException
	 * @author Jagadeesh.T
	 * @since 2018-07-20
	 */
	public final String toJsonString() {// throws InvalidJsonException {
		return ZcUtil.toJson(this);
	}
	
	/**
	 * Gets setting from ZcMap
	 * 
	 * @return ZcMap
	 * @author Jagadeesh.T
	 * @since 2018-07-20
	 */
	public final ZcMap getSetting() {
		if(get("setting")==null) return null;
		else return (ZcMap)get("setting");
	}
	  
	/**
	 * Converts JSON to ZcMap Object
	 * 
	 * @param s
	 * @return ZcMap
	 * @author Jagadeesh.T
	 * @since 2018-07-20
	 */
	public static final ZcMap jsonToObject(String s) {
		try{return new ObjectMapper().readValue(s==null?null:s, ZcMap.class);}catch (Exception e) {return null;}
	} 
	
	
	/**
	 * Parses the String Object to Map<String,Object> type
	 * 
	 * @param k
	 * @return Map
	 * @author Jagadeesh.T
	 * @since 2018-07-20
	 */
	@SuppressWarnings({ "unchecked"})
	public final Map<String,Object> getMap(String k) {
		if((Map<String,Object>)this.get(k)==null) return (ZcMap)EMPTY.clone();
		return (Map<String,Object>)get(k);
	}
	
	
	/**
	 * returns the List of String from the map
	 * 
	 * @param k
	 * @return List<String>
	 * @author Jagadeesh.T
	 * @since 2018-07-20
	 */
	@SuppressWarnings("unchecked")
	public final List<String> getStringList(String k){
		return (List<String>)get(k);
	}
	@SuppressWarnings("unchecked")
	public final String[] getStringArray(String k){
		List<String> list=(List<String>)get(k);
		return list==null?null:list.toArray(new String[list.size()]);
	}
	
	/**
	 * removes the blank from the given key set
	 * 
	 * @author Jagadeesh.T
	 * @since 2018-07-20
	 */
	public final void removeBlanks() {
		Set<String> keys=new HashSet<>();
		for(String s:this.keySet())	
			keys.add(s);
		for(String s:keys) {
			Object o=this.get(s); 
			if(o instanceof LinkedHashMap) {
				ZcMap map=null;
				try {
					map=(ZcMap)o;
					if(map!=null && !map.isEmpty()) {
						map.removeBlanks();
						if(ZcUtil.isEmptyOrNull(map)) this.remove(s);
					}
				}catch (Exception e) {
					if(ZcUtil.isEmptyOrNull(o)) this.remove(s);
				}
			}else {
				if(ZcUtil.isEmptyOrNull(o)) this.remove(s);
			}
		}
	}
	
	/**
	 * Converts a List of JSONs or Map to List of ZcMaps
	 * 
	 * @param k
	 * @return List of ZcMaps
	 * @author Jagadeesh.T
	 * @since 2018-07-20
	 */
	@SuppressWarnings("unchecked")
	public final List<ZcMap> getAsZcMapList(String k) {
		Object o=this.get(k);
		if(o==null) return null;
		List<ZcMap> li=new ArrayList<ZcMap>();
		((List<Map<String,Object>>)o).forEach(x->{
			ZcMap data=new ZcMap();
			data.putAll(x);
			li.add(data);
		});
		return li;
	}	 
	
	/**
	 * To put the object into the map
	 * 
	 * @param k
	 * @param v
	 * @return ZcMap
	 * @author Jagadeesh.T
	 * @since 2018-07-20
	 */
	public final ZcMap putO(String k,Object v) {
		put(k, v);
		return this;
	}
	
	/**
	 * Inserts key and value in the ZcMap
	 *  
	 * @param key
	 * @param value
	 * @return Object
	 * @author Jagadeesh.T
	 * @since 2018-07-20
	 */
	@Override
	public final Object put(String key, Object value) {
		if(!key.toLowerCase().endsWith("setting"))
			return super.put(key, value);
		else {
			if(value==null) super.put(key, ZcMap.EMPTY);
			if(value instanceof Map) return super.put(key, value);
			try {
				return super.put(key,jsonToObject(value==null?null:value.toString()));
			}catch (Exception e) {
				return super.put(key, null);
			}
		}
	}
	
	
	public List<ZcMap> getWidgets() {
		try {
			return this.getAsZcMapList("widgets");
		}catch (Exception e) {
			return null;
		}
	}
	public String getWidgetType() {
		return this.getS("type");
	}
}
