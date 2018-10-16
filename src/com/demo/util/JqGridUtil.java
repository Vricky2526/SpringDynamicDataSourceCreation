package com.demo.util;

 

public class JqGridUtil { 
	public ZcMap filters;
	public String sidx;
	public String sord;
	public String globalFilter;
	public int page;
	public int start;
	public int rows;
	public ZcMap other;
	public ZcMap reqData;
	public boolean select;
	public JqGridUtil(ZcMap reqData)throws Exception {
		this.reqData=reqData;
		this.select=reqData.getB("select");
		this.page = reqData.getI("page");
		this.rows = reqData.getI("rows");
		this.filters = reqData.getZcMap("filters");
		this.sidx = reqData.getS("sidx");
		this.sord = reqData.getS("sord"); 
		this.sord = ZcUtil.formatSord(this.sord);
		this.globalFilter = reqData.getS("globalFilter");
		if (this.page == 0)
			this.page = 1;
		if (this.rows == 0 || this.rows <= 0)
			this.rows = Integer.MAX_VALUE;
		this.start=(this.page - 1) * this.rows;
	}
}

