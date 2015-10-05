package com.cbkj.rrh.main;

import com.cbkj.rrh.net.http.HttpURL;

/**
 * @todo:常量类
 * @date:2014-11-4 下午2:44:21
 * @author:hg_liuzl@163.com
 */
public class Const {

	public static final String APP_CONTACT_TELEPHONE = "0755-89523078";
	
	
	public static final String TX_BUGLY_ID = "900009984";

	public static final String SINAWB_APP_KEY = "3632992911";
	public static final String SIANWB_APP_SECRET = "ba3bc58c2433093434844db7707890da";
	
	public static final String WX_APP_ID = "wxe28ecdff3da133cc";
	public static final String WX_APP_SECRET = "17427694ce472af7a9fdf6a8430400fa";
	
	
	public static final String QQ_APP_ID = "1104820002";
	public static final String QQ_APP_KEY = "4VPsJEt1MQNYLbe4";

	public static final String LOGO_URL = HttpURL.BASE_URL+"/static/images/logo.png";
	//public static final String LOGO_URL = "http://baby.showneng.com:9090/bbcwm/media/images/logo/512_512.png";
	
	
	public static final String SHARE_WORD_TITLE = "啦啦私活-私活交易平台！";
	
	public static final String SHARE_MONEY_TITLE = "悬赏%s元";
	
	
	/**
	 * 分享的地址，objid,分享内容的id,tag分享内容的类型,有xn,wq两种类型
	 */
	
	public static final String SHARE_URL = HttpURL.BASE_URL+"/share_task?taskId=%s";
	
	/**下载地址**/
	public static final String SHARE_APP_DOWNLOAD = "http://www.lalawitkey.com/";
	//public static final String SHARE_APP_DOWNLOAD = "http://a.app.qq.com/o/simple.jsp?pkgname=com.bgood.task";

	/**App信息分享**/
	//public static final String SHARE_APP = "给您提供兼职赚钱的机会！"+SHARE_APP_DOWNLOAD;
	public static final String SHARE_APP = "给您提供兼职赚钱的机会！";
	
	/**登录到环信聊天服务器的密码**/
	public static final String IM_PWD = "banggood123";
		
	// 默认timeout 时间 60s
	public final static int SOCKET_TIMOUT = 60 * 1000;
	
	public final static int SOCKET_READ_TIMOUT = 15 * 1000;
	
	//如果没有连接无服务器。读线程的sleep时间
	public final static int SOCKET_SLEEP_SECOND = 3 ;
	
	//心跳包发送间隔时间
	public final static int SOCKET_HEART_SECOND =3 ;
	
	public final static String BC = "BC";

	public static final String CHAT_GROUPTYPE ="groupType";
	
	
	/**编辑动作*/
	public enum ActionType{
		TYPE_EDIT,TYPE_OK
	}
	
	/**两个空格**/
	public static final String KEY_BLANK = "\t";
	public static final String KEY_DIVILDER = ",";
	
	/**1:推荐；2:最新；3:关注；4：随机；**/
	public static final int TYPE_CONTENT_RECOMMEND = 1;
	public static final int TYPE_CONTENT_LAST = 2;
	public static final int TYPE_CONTENT_ATTENTION = 3;
	public static final int TYPE_CONTENT_RANDOM = 4;
	
	
	/**图片1，视频2**/
	public static final int TYPE_PIC  = 1;
	public static final int TYPE_VIDEO = 2;
	

	
	/**1：媒体图片；2媒体视频；3用户头像**/
	public static final int UPLOAD_TYPE_PIC = 1;
	public static final int UPLOAD_TYPE_VIDEO = 2;
	public static final int UPLOAD_TYPE_USER = 3;
	
	/**文件类型 jpg,mp4**/
	public static final String UPLOAD_TYPE_JPG = "jpg";
	public static final String UPLOAD_TYPE_MP4 = "mp4";
	
	

	
	/**关注与取消关注**/
	public static final int TYPE_ATTENTION_CREATE = 1;
	public static final int TYPE_ATTENTION_CANCEL = 2;
	
	/**举报**/
	public static final int REPORT_TYPE_PIC = 1;
	public static final int REPORT_TYPE_VIDEO = 2;
	public static final int REPORT_TYPE_REPLY = 3;
	
	/***发萌币**/
	public static final int MONEY_SIGN = 1;
	public static final int MONEY_PUBLISH_PIC = 3;
	public static final int MONEY_PUBLISH_VIDEO = 5;
	public static final int MONEY_SHARE = 2;
	
	
	/**图片1，视频2**/
	public static final int REQUEST_PUBLISH_PIC  = 100;
	public static final int REQUEST_PUBLISH_VIDEO = 101;
	/**去详情看看,如果没有评论，则进入评论界面*/
	public static final int REQUEST_CONTENT_DETAIL = 102;
	public static final int REQUEST_CONTENT_REPLY = 103;
	
}
