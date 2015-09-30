package com.cbkj.rrh.bean;
//package com.bgood.task.bean;
//
//import java.io.Serializable;
//import java.util.List;
//
//import com.bgood.task.system.Const;
//import com.bgood.task.utils.SharePostUtils;
//
///**
// * @todo:图片与视频信息实体类
// * @date:2015-5-12 下午2:38:36
// * @author:hg_liuzl@163.com
// */
//public class ContentBean implements Serializable {
//	
//	
//	/**审核结果 1：给予通过，2：不给予通过*/
//	
//	/**给予通过**/
//	public static final int CONTENT_VERIFY_AGREE = 1;
//	
//	/**不给予通过**/
//	public static final int CONTENT_VERIFY_DISAGREE = 2;
//	
//	
//	//0 正在审核； 1审查通过；2审查未通过；null：全部
//	
//	/**审核中**/
//	public static final int CONTENT_VERIFY_ING = 0;
//	
//	/**审核通过**/
//	public static final int CONTENT_HAS_VERIFY = 1;
//	
//	/**审核未通过**/
//	public static final int CONTENT_FAIL_VERIFY = 2;
//	
//	
//
//	private static final long serialVersionUID = 1L;
//
//	public static final String KEY_CONTENT_REPLY = "key_content_reply";
//
//	public static final String KEY_CONTENT_BEAN = "key_content_bean";
//	
//	public static final String KEY_CONTENT_ID = "key_content_id";
//
//	public static final String KEY_CONTENT_TYPE = "key_content_type";
//	
//	
//	public boolean isSend = true;
//	public int mediaId;// 6,
//	public int moneys;//  0,
//	public String senderImageUrl;//  "http://img2.cache.netease.com/sports/2015/4/23/201504230849259a2e0.jpg",
//	public int likeCount;//  0,
//	public int replyCount;//  0,
//	public int unApproved;//  0,
//	public int status;//  1,
//	public int readCount;//  0,
//	public String senderNick;//  "六约四小龙",
//	public int type;//  1,
//	public List<FileBean> fileUrl;//  [],
//	public String content;//  "夏天最好了。可以用勺子吃冰西瓜，可以盖薄被子吹风扇，可以坐在台阶上聊天，可以大排档说到天亮，可以看见一个清凉
//	public int approved;//  0,
//	public String title;//  "小木匠",
//	public int playCount;//  0,
//	public String updated;//  "2015-05-07 09:35:02.000",
//	public String created;//  "2015-05-07 09:35:02.000",
//	public String nickName = "";//  "宝宝烤面包",
//	public String userId;//  "1",
//	public String firstFrame;//  ""
//	public String videoUrl;
//	public float videoSize;	//视频大小
//	public String videoLen;//  0,
//	
//	/**
//	 * 
//	 * @todo:内容分享
//	 * @date:2015-3-20 上午10:34:23
//	 * @author:hg_liuzl@163.com
//	 * @params:@param share
//	 */
//	public  void doShare(SharePostUtils share){
//		if (type == Const.TYPE_PIC) {
//			share.setShareContent(content,fileUrl.size() > 0 ? fileUrl.get(0).big : null,getShareUrl());
//		} else {
//			share.setShareContent(content, firstFrame, getShareUrl());
//		}
//	}
//	
//	
//	public String getShareUrl(){
//		return String.format(Const.SHARE_URL,mediaId);
//	}
//}
