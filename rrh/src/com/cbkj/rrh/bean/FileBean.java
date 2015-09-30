package com.cbkj.rrh.bean;

import java.io.Serializable;

/**
 * @todo:文件实体类
 * @date:2015-5-12 下午2:45:20
 * @author:hg_liuzl@163.com
 */
public class FileBean implements Serializable {
	/**
	 * @todo:TODO
	 * @date:2015-5-20 下午3:11:26
	 * @author:hg_liuzl@163.com
	 */
	private static final long serialVersionUID = 1L;
	public int id;
	public String small;
	public String big;
	
	
	public FileBean(int id, String small, String big) {
		super();
		this.id = id;
		this.small = small;
		this.big = big;
	}

	public FileBean(String small, String big) {
		super();
		this.small = small;
		this.big = big;
	}
	
	public FileBean(String small) {
		super();
		this.small = small;
	}
	
	



	public FileBean() {
		super();
	}
	
}
