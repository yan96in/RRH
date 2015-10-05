package com.cbkj.rrh.bean;

import java.io.Serializable;
import java.util.List;

import com.cbkj.rrh.main.Const;

/**
 * @todo:收藏实体类
 * @date:2015-4-30 下午4:56:28
 * @author:hg_liuzl@163.com
 */
public class CollectBean implements Serializable {

	/**
	 * @todo:TODO
	 * @date:2015-5-5 上午10:24:26
	 * @author:hg_liuzl@163.com
	 */
	private static final long serialVersionUID = 1L;
	
	/**图片类型*/
	public static final int TYPE_PIC = 1;
	/**视频类型*/
	public static final int TYPE_VIDEO = 2;
	
	
	public int collectionId;//	收藏Id	Y	
	public String senderId;//	发布者ID	Y	
	public String senderNick;//	发布者昵称	Y	
	public String senderImageUrl;//	发布者头像	Y	
	public int mediaId;//	图片/视频Id	Y	媒体Id
	public int type;//	图片或者视频	Y	媒体类型 1：图片；2：视频
	public String title;//	标题	Y	媒体标题
	public String content;//	正文	N	萌语
	public List<FileBean> fileUrl;//	图片地址集合的数组	N	媒体是图片时，字段是必选项
	//如：'fileUrl': [{'small':' http://domain/s.jpg','big':' http://domain/b.jpg'},{'small':' http://domain/s.jpg','big':' http://domain/b.jpg'},......]
	public String videoUrl;//	视频地址	N	媒体是视频时，字段是必选项
	public String firstFrame;//	视频第一帧截图	N	媒体是视频时，字段是必选项
	public double radio;//	媒体高宽比列	Y	注意：媒体的高宽比列，高除以宽
	public String created;//	收藏的时间	N	
	
	/**
	 * 
	 * @todo:获取第一张小图
	 * @date:2015-5-21 上午9:15:43
	 * @author:hg_liuzl@163.com
	 * @params:@return
	 */
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
