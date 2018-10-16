package com.demo.util;

import java.util.List;

/**
 * @author Swetha.P
 *
 */
public class JqGridData {

	/** Total number of records */
	private String records;
	
	private boolean select;

	/** Total number of pages */
	private int total; // rows

	/** The current page number */
	private int page;

	/** The actual data */
	private Object rows;

	/** no of rec per page */
	private int size;

	public JqGridData() {

	}
	public JqGridData(int total, int page, int size, Object rows, int records,boolean select) {
		this(total,page,size,rows,records);
		this.select=select;
	}
	public JqGridData(int total, int page, int size, Object rows, int records) {
		this.total = total;
		this.page = page + 1;
		this.rows = rows;
		this.size = size;
		this.records = records + "";
	}

	public int getTotal() {
		return total;
	}

	public int getPage() {
		return page;
	}

	public Object getRows() {
		return rows;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(List<ZcMap> rows) {
		this.rows = rows;
	}

	public int getSize() {
		return size;
	}

	public String getRecords() {
		return records;
	}

	public void setRecords(String records) {
		this.records = records;
	}

	public void setSize(int size) {
		this.size = size;
	}
	public boolean isSelect() {
		return select;
	}
	public void setSelect(boolean select) {
		this.select = select;
	}

}
