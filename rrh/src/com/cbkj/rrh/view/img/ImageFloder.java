package com.cbkj.rrh.view.img;

/**
 * 图片实体类
 * @date:2015-7-9 下午5:30:27
 * @author:hg_liuzl@163.com
 */
public class ImageFloder{
	/**
	 * 图片的文件夹路径
	 */
	private String dir;

	/**
	 * 第一张图片的路径
	 */
	private String firstImagePath;

	/**
	 * 文件夹的名称
	 */
	private String name;

	/**
	 * 图片的数量
	 */
	private int count;

	private boolean  isSelected;
	
	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDir(){
		return dir;
	}

	public void setDir(String dir){
		this.dir = dir;
		int lastIndexOf = this.dir.lastIndexOf("/");
		this.name = this.dir.substring(lastIndexOf);
	}

	public String getFirstImagePath(){
		return firstImagePath;
	}

	public void setFirstImagePath(String firstImagePath){
		this.firstImagePath = firstImagePath;
	}

	public String getName(){
		return name.substring(1);
	}
	public int getCount(){
		return count;
	}

	public void setCount(int count){
		this.count = count;
	}

	

}
