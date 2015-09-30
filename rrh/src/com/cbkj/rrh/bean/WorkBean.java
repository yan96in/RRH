package com.cbkj.rrh.bean;

/**
 * 
 * @todo:职业实体类
 * @date:2015-9-2 下午4:28:46
 * @author:hg_liuzl@163.com
 */
public class WorkBean {
	public String name;// 用户Id string Y
	public int position;// 职业Id int Y

	public WorkBean(String name, int position) {
		super();
		this.name = name;
		this.position = position;
	}

	public WorkBean() {

	}
}