package com.cbkj.rrh.bean;

import java.util.List;

import com.cbkj.rrh.system.Const;
import com.cbkj.rrh.utils.SharePostUtils;

/**
 * @todo:与我相关
 * @date:2015-5-26 上午9:54:05
 * @author:hg_liuzl@163.com
 */
public class MentionBean {
	
	/***活动分类	int	活动分类  1：打赏；2：点赞；3：被评；*/
	public static final int TYPE_REWARD = 1;
	public static final int TYPE_ZAN = 2;
	public static final int TYPE_REPLY = 3;
	
	public String senderId;//		string	打赏、点赞、评论的人（发送者）
	public String senderNick;//			评论者昵称
	public String senderImageUrl;//			评论者小头像
	public String senderBigImageUrl;//			评论者大头像
	public int mediaId;//	被评论媒体Id	int	
	public int type;//	被评论媒体类型	int	媒体类型 1：图片；2：视频
	public List<FileBean> fileUrl;//	被评论图片集合	图片路径集合数组	如：'fileUrl': [{'small':' http://domain/s.jpg','big':' http://domain/b.jpg'},{'small':' http://domain/s.jpg','big':' http://domain/b.jpg'},......]
	public String firstFrame;//	被评论视频截图	string	
	public String vedioUrl;//	被评论视频地址	string	
	public float radio;//	媒体 高宽比列	float	注意是 高宽比列： 高除以宽的值
	public String receiverId;//	接收者	string	被赏、赞、评的人（被动接收者）
	public String remark;//	相关说明	string	如：XX 赞了一下；XX 打赏了5个萌币；XX评论说：五毛不是毛爷爷！
	public int status;//	状态	int	1：未读；2：已读
	public int category;//	活动分类	int	活动分类  1：打赏；2：点赞；3：被评；
	public String created;//	活动时间	string	
	public float videoSize; //视频大小
	
	
	
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
			}
		} else {
			return firstFrame;
		}
		return "";
	}
	
}
