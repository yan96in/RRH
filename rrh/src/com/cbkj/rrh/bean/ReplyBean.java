package com.cbkj.rrh.bean;

import java.io.Serializable;
import java.util.List;

import com.cbkj.rrh.system.Const;

/**
 * @todo:评论实体类
 * @date:2015-4-30 下午4:56:28
 * @author:hg_liuzl@163.com
 */
public class ReplyBean implements Serializable {

	/**
	 * @todo:TODO
	 * @date:2015-5-5 上午10:24:26
	 * @author:hg_liuzl@163.com
	 */
	private static final long serialVersionUID = 1L;
	
	public String userId;//		string	
	public String nickName;//			string	评论者昵称
	public String imageUrl;//			string	评论者小头像
	public String bigImageUrl;//			string	评论者大头像
	public String content;//		评论内容	string	评论的内容
	public int mediaId;//		被评论媒体Id	int	
	public int type;//		被评论媒体类型	int	媒体类型 1：图片；2：视频
	public List<FileBean> fileUrl;//		被评论图片集合	图片路径集合数组	如：'fileUrl': [{'small':' http://domain/s.jpg','big':' http://domain/b.jpg'},{'small':' http://domain/s.jpg','big':' http://domain/b.jpg'},......]
	public String vedioUrl;//		被评论视频地址	string	
	public String firstFrame;//		被评论视频截图	string	
	public float radio;//		媒体高宽比列	float	注意：高宽比列是 高除以宽
	public String created;//		评论时间	string	
	
	
	/**
	 * 
	 * @todo:获取第一张小图
	 * @date:2015-5-21 上午9:15:43
	 * @author:hg_liuzl@163.com
	 * @params:@return
	 */
	public String getFirstSmallImg() {
		if (type == Const.TYPE_PIC) {
			if (null != fileUrl && fileUrl.size() > 0) {
				return fileUrl.get(0).small;
			} else {
				return "";
			}
		} else {
			return firstFrame;
		}
	}
}
