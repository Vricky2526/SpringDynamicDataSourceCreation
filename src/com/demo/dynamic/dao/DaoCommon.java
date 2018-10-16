package com.demo.dynamic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.demo.util.ZcMap;
import com.demo.util.ZcUtil;





/**
 * Contains common db operations 
 * @author swetha.p
 *
 */
public abstract class DaoCommon {
	
	protected String globalFilter(String filter,ZcMap cols,List<Object> paramData,String...exclude) {
		if(ZcUtil.isBlank(filter)) return "";
		String queryCondition="";
		if(exclude==null)exclude=new String[] {};
		if(!ZcUtil.isBlank(filter)) {
			for(Map.Entry<String, Object> e:cols.entrySet()) {
				if(!Arrays.asList(exclude).contains(e.getKey())) {
					queryCondition+= (ZcUtil.isBlank(queryCondition)?" AND (":" OR ")+e.getValue()+" LIKE ?";
					paramData.add(ZcUtil.likeSearchString(filter));
				}
			}
			queryCondition += ZcUtil.isBlank(queryCondition)?"":") ";
		}
		return queryCondition;
	}
	
	protected enum FilterFieldType {Literal,Object,Date}
	
	/**
	 * Checks whether field is literal type or not 
	 * 
	 * @author swetha.p
	 * @since 2018-07-18
	 * @param type
	 * @return boolean
	 */
	private boolean isLiteralTypeFilter(FilterFieldType type) {
		return FilterFieldType.Literal.equals(type);
	}
	
	/**
	 * Checks whether field is Object type or not 
	 * 
	 * @author swetha.p
	 * @since 2018-07-18
	 * @param type
	 * @return boolean
	 */
	private boolean isObjectTypeFilter(FilterFieldType type) {
		return FilterFieldType.Object.equals(type);
	}
	
	
	protected String fieldFilter(ZcMap filter,ZcMap cols,List<Object> paramData,ZcMap types,String...exclude)throws Exception {
		types=types==null?new ZcMap():types;
		/*
		types: String,String
			   name   [type,operator]   [Possible types: literal, object, date]
		 */
		if(ZcUtil.isBlank(filter)) return "";
		String queryCondition="";
		if(exclude==null)exclude=new String[] {};
		if(!ZcUtil.isBlank(filter)) {
			for(Map.Entry<String, Object> e:filter.entrySet()) {
				if(!Arrays.asList(exclude).contains(e.getKey())) {
					FilterFieldType type=(FilterFieldType)types.get(e.getKey());
					type = type==null?FilterFieldType.Literal:type;
					queryCondition+= (ZcUtil.isBlank(queryCondition)?" AND (":" AND ")+cols.getS(e.getKey())+"  "+(isLiteralTypeFilter(type)?" LIKE ":" = ")+" ? ";
					if(isObjectTypeFilter(type))
						paramData.add(e.getValue());
					else 
						paramData.add(ZcUtil.likeSearchString(e.getValue()));
				}
			}
			queryCondition += ZcUtil.isBlank(queryCondition)?"":") ";
		}
		return queryCondition;
	}
		

	/**
	 * This method returns the generated rowId
	 * 
	 * @author Jagadeesh.T
	 * @since 2018-07-19
	 * @param query
	 * @param values
	 * @return rowId
	 * @throws Exception
	 */
	public static int getGeneratedKey(JdbcTemplate jdbcTemplate,final String query,final Object[] values)throws Exception {
	     KeyHolder keyHolder = new GeneratedKeyHolder();
	    	jdbcTemplate.update(new PreparedStatementCreator() {
		    	  public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {
		    	    PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		    	    if(values!=null){
		    	    	int i=0;
		    	    	for(Object obj:values)
		    	    		ps.setObject(++i, obj);
		    	    }
		    	    return ps;}}, keyHolder);
	    	if(keyHolder==null || keyHolder.getKey()==null )
	    		return 0;
	    	Long generatedId = new Long(keyHolder.getKey().longValue());
	    	return generatedId.intValue();
	}
	

}
