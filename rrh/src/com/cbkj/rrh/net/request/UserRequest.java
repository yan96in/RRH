package com.cbkj.rrh.net.request;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.cbkj.rrh.bean.UserBean;
import com.cbkj.rrh.main.account.model.ThirdLoginParam;
import com.cbkj.rrh.net.http.HttpRequest;
import com.cbkj.rrh.net.http.HttpRequestAsyncTask;
import com.cbkj.rrh.net.http.HttpURL;
import com.cbkj.rrh.net.http.HttpRequestAsyncTask.TaskListenerWithState;
import com.cbkj.rrh.others.utils.FileUtils;
import com.cbkj.rrh.others.utils.MD5;

/**
 * @todo:用户模块的请求
 * @date:2015-4-20 上午11:50:28
 * @author:hg_liuzl@163.com
 */
public class UserRequest extends HttpRequest {
	private static UserRequest instance = null;

	public synchronized static UserRequest getInstance() {
		if (null == instance)
			instance = new UserRequest();

		return instance;
	}

	
	
	
	/**
	 * 
	 * @todo:获取职业列表
	 * @date:2015-4-20 下午3:44:57
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param phoneNumber
	 */
	public void requestPositonList(TaskListenerWithState mHttpTaskListener,Context context) {
		setRequestUrl(HttpURL.URL_POSITION_LIST);
		JSONObject body = new JSONObject();
		setBody(body, context);
		new HttpRequestAsyncTask(false,this, mHttpTaskListener, context).execute();
	}
	
	
	/**
	 * 
	 * @todo:用户签到
	 * @date:2015-4-20 下午3:44:57
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param phoneNumber
	 */
	public void requestUserSign(TaskListenerWithState mHttpTaskListener,Context context, String userId) {
		//setRequestUrl(HttpURL.URL_USER_SIGN);
		JSONObject body = new JSONObject();
		try {
			body.put("userId", userId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(false,this, mHttpTaskListener, context).execute();
	}
	
	/**
	 * 
	 * @todo:检验手机号是否被占用
	 * @date:2015-4-20 下午3:44:57
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param phoneNumber
	 */
	public void requestCheckPhone(TaskListenerWithState mHttpTaskListener,Context context, String phoneNumber) {
		setRequestUrl(HttpURL.URL_CHECK_PHONE);
		JSONObject body = new JSONObject();
		try {
			body.put("telephone", phoneNumber);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(true,this, mHttpTaskListener, context).execute();
	}
	
	/**
	 * 
	 * @todo:获取验证码
	 * @date:2015-4-20 下午3:44:57
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param phoneNumber
	 */
	public void requestPhoneVerifyCode(TaskListenerWithState mHttpTaskListener,Context context, String phoneNumber) {
		setRequestUrl(HttpURL.URL_PHONE_VERIFYCODE);
		JSONObject body = new JSONObject();
		try {
			body.put("telephone", phoneNumber);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(this, mHttpTaskListener, context).execute();
	}

	/**
	 * 
	 * @todo:验证验证码
	 * @date:2015-4-20 下午3:44:57
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param phoneNumber
	 */
	public void requestCheckVerifyCode(TaskListenerWithState mHttpTaskListener,Context context, String verifyCode,String accessToken) {
		setRequestUrl(HttpURL.URL_CHECK_VERIFYCODE);
		JSONObject body = new JSONObject();
		try {
			body.put("accessToken", accessToken);
			body.put("verifyCode", verifyCode);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(this, mHttpTaskListener, context).execute();
	}

	/**
	 * 
	 * @todo:注册操作
	 * @date:2015-4-20 下午3:44:57
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param phoneNumber
	 */
	public void requestRegister(TaskListenerWithState mHttpTaskListener,Context context,String nick,String telphone,String pwd,int workType) {
		setRequestUrl(HttpURL.URL_REGISTER);
		JSONObject body = new JSONObject();
		try {
			body.put("nickName", nick);
			body.put("telephone", telphone);
			body.put("password", MD5.GetMD5Code(pwd));
			body.put("position", workType);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(this, mHttpTaskListener, context).execute();
	}

	/**
	 * 
	 * @todo:登录操作
	 * @date:2015-4-20 下午3:44:57
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param phoneNumber
	 */
	public void requestLogin(TaskListenerWithState mHttpTaskListener,Context context, String phoneNumber,String pwd) {
		setRequestUrl(HttpURL.URL_LOGIN);
		JSONObject body = new JSONObject();
		try {
			body.put("telephone", phoneNumber);
			body.put("password", MD5.GetMD5Code(pwd));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(this, mHttpTaskListener, context).execute();
	}
	
	/**
	 * 
	 * @todo:获取用户资料
	 * @date:2015-4-20 下午3:44:57
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param phoneNumber
	 */
	public void requestUserInfo(TaskListenerWithState mHttpTaskListener,Context context, String userId) {
		setRequestUrl(HttpURL.URL_USER_PROFILE);
		JSONObject body = new JSONObject();
		try {
			body.put("userId", userId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(false,this, mHttpTaskListener, context).execute();
	}
	
	/**
	 * 
	 * @todo:第三方登录操作
	 * @date:2015-4-20 下午3:44:57
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param phoneNumber
	 */
	public void requestThirdLogin(TaskListenerWithState mHttpTaskListener,Context context,ThirdLoginParam param) {
		setRequestUrl(HttpURL.URL_THIRD_LOGIN);
		
//		uid	第三方用户ID		Y	第三方用户ID
//		access_token	会话数据		N	第三方返回的会话数据
//		openid	平台id		N	如果有openid 就传进来
//		gender	性别		N	
//		nickName	昵称		Y	
//		profile_image_url	头像		Y	注意：是第三平台的头像（路径是全路径）
//		source	来源		Y	用户来源： 1：本地；2：腾讯qq；3：微信；4：新浪微博
//		signature	个性签名		N	个性签名
		
		JSONObject body = new JSONObject();
		try {
			body.put("source", param.getLoginType());
			body.put("uid", param.uid);
			body.put("access_token", param.accessToken);
			body.put("openid", param.openid);
			body.put("gender", param.getGender());
			body.put("nickName", param.nickName);
			body.put("profile_image_url", param.headImgUrl);
			body.put("signature", param.signature);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(this, mHttpTaskListener, context).execute();
	}
	
	/**
	 * 
	 * @todo:修改密码
	 * @date:2015-4-20 下午3:44:57
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param phoneNumber
	 */
	public void requestModifyPwd(TaskListenerWithState mHttpTaskListener,Context context, String telephone,String oldPwd,String newPwd) {
		setRequestUrl(HttpURL.URL_MODIFY_PWD);
		JSONObject body = new JSONObject();
		try {
			body.put("telephone", telephone);
			body.put("oldPwd", MD5.GetMD5Code(oldPwd));
			body.put("newPwd", MD5.GetMD5Code(newPwd));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(this, mHttpTaskListener, context).execute();
	}
	
	/**
	 * 
	 * @todo:重置密码
	 * @date:2015-4-20 下午3:44:57
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param phoneNumber
	 */
	public void requestReSetPwd(TaskListenerWithState mHttpTaskListener,Context context, String phoneNumber,String pwd) {
		setRequestUrl(HttpURL.URL_RESET_PWD);
		JSONObject body = new JSONObject();
		try {
			body.put("telephone", phoneNumber);
			body.put("newPwd", MD5.GetMD5Code(pwd));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(this, mHttpTaskListener, context).execute();
	}
	



	/**
	 * 
	 * @todo:修改用户资料
	 * @date:2015-4-20 下午5:04:56
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param userId
	 * @params:@param nick
	 * @params:@param sex 0是女的，1是男的，3是未知性别
	 * @params:@param signture
	 * @params:@param photo photo[img,img_thumb]
	 */
	public void requestModifyUserInfo(TaskListenerWithState mHttpTaskListener,Context context, UserBean userBean) {
		setRequestUrl(HttpURL.URL_MODIFY_USER);
		JSONObject body = new JSONObject();
		try {
			body.put("userId", userBean.userId);
			body.put("nickName", userBean.nickName);
			body.put("gender", userBean.gender);
			body.put("smallImg", userBean.smallImg);
			body.put("bigImg", userBean.bigImg);
			body.put("position", userBean.position);
			body.put("intro", userBean.intro);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(false,this, mHttpTaskListener, context).execute();
	}
	
	/**
	 * 
	 * @todo:维护支付宝账号
	 * @date:2015-4-20 下午5:04:56
	 * @author:hg_liuzl@163.com
	 */
	public void requestModifyAlipay(TaskListenerWithState mHttpTaskListener,Context context,String alipayNo,String userId,String password) {
		setRequestUrl(HttpURL.URL_AMALIPAY_URL);
		JSONObject body = new JSONObject();
		try {
			body.put("alipayNo", alipayNo);
			body.put("userId", userId);
			body.put("password", MD5.GetMD5Code(password));
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(this, mHttpTaskListener, context).execute();
	}
	

	/**
	 * 
	 * @todo:检查新版本
	 * @date:2015-4-20 下午3:44:57
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param phoneNumber
	 */
	public void requestCheckUpgrade(TaskListenerWithState mHttpTaskListener,Context context) {
		setRequestUrl(HttpURL.URL_CHECK_UPDATE);
		JSONObject body = new JSONObject();
		setBody(body, context);
		new HttpRequestAsyncTask(this, mHttpTaskListener, context).execute();
	}


	
	/**
	 * 
	 * @todo:意见反馈
	 * @date:2015-4-20 下午3:44:57
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param phoneNumber
	 */
	public void requestFeedback(TaskListenerWithState mHttpTaskListener,Context context, String userId,int type,String contact,String content) {
		setRequestUrl(HttpURL.URL_FEEDBACK);
		JSONObject body = new JSONObject();
		try {
			body.put("userId", userId);
			body.put("type", type);
			body.put("contact", contact);
			body.put("content", content);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(this, mHttpTaskListener, context).execute();
	}

	/**
	 * 
	 * @todo:上传头像
	 * @date:2015-6-22 下午3:40:46
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param nickName
	 */
	public void requestUploadHeadImg(TaskListenerWithState mHttpTaskListener,Context context,String userID,String password,File file) {
		setRequestUrl(HttpURL.URL_UPLOAD_HEAD_IMG);
		JSONObject body = new JSONObject();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", userID);
			map.put("password", MD5.GetMD5Code(password));
			map.put("file", FileUtils.file2Bytes(file));
			String strJson = JSON.toJSONString(map);
			body = new JSONObject(strJson);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(false,this, mHttpTaskListener, context).execute();
	}
	
	/**
	 * 
	 * @todo:上传证书
	 * @date:2015-6-22 下午3:40:46
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param nickName
	 */
	public void requestUploadCertificateImg(TaskListenerWithState mHttpTaskListener,Context context,String userID,String password,File file,boolean showProgress) {
		setRequestUrl(HttpURL.URL_UPLOAD_CERTIFICATE_IMG);
		JSONObject body = new JSONObject();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", userID);
			map.put("password", MD5.GetMD5Code(password));
			map.put("file", FileUtils.file2Bytes(file));
			String strJson = JSON.toJSONString(map);
			body = new JSONObject(strJson);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(showProgress,this, mHttpTaskListener, context).execute();
	}
	
	
	/**
	 * 
	 * @todo:添加证书
	 * @date:2015-6-22 下午3:40:46
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param nickName
	 */
	public void requestAddCertificateImg(TaskListenerWithState mHttpTaskListener,Context context,String userId,String smallImg,String bigImg) {
		setRequestUrl(HttpURL.URL_ADD_CERTIFICATE_IMG);
		JSONObject body = new JSONObject();
		try {
			body.put("userId", userId);
			body.put("smallImg", smallImg);
			body.put("bigImg", bigImg);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(false,this, mHttpTaskListener, context).execute();
	}
	
	/**
	 * 
	 * @todo:删除证书
	 * @date:2015-6-22 下午3:40:46
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param nickName
	 */
	public void requestDelCertificateImg(TaskListenerWithState mHttpTaskListener,Context context,String userID,int certificateID) {
		setRequestUrl(HttpURL.URL_DEL_CERTIFICATE);
		JSONObject body = new JSONObject();
		try {
			body.put("userId", userID);
			body.put("certificateId", certificateID);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(false,this, mHttpTaskListener, context).execute();
	}
	
	/**
	 * 
	 * @todo:我的证书列表
	 * @date:2015-6-22 下午3:40:46
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param nickName
	 */
	public void requestMyCertificates(TaskListenerWithState mHttpTaskListener,Context context,String userId) {
		setRequestUrl(HttpURL.URL_CERTIFICATE_LIST);
		JSONObject body = new JSONObject();
		try {
			body.put("userId", userId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(true,this, mHttpTaskListener, context).execute();
	}
		/**
		 * 
		 * @todo:申请接单人认证
		 * @date:2015-8-11 上午11:09:53
		 * @author:hg_liuzl@163.com
		 * @params:@param mHttpTaskListener
		 * @params:@param context
		 * @params:@param userId
		 * @params:@param realName
		 * @params:@param IDCard
		 * @params:@param telephone
		 * @params:@param fontCID
		 * @params:@param backCID
		 * @params:@param alipayNo
		 */
	public void requestApplay2Employee(TaskListenerWithState mHttpTaskListener,Context context,String userId,String realName,String IDCard,String fontCID,String backCID) {
		setRequestUrl(HttpURL.URL_APPLY_IDENTIFICATION);
		JSONObject body = new JSONObject();
		try {
			body.put("userId", userId);
			body.put("fullName", realName);
			body.put("IDCard", IDCard);
			body.put("IDCardPhoto1", fontCID);
			body.put("IDCardPhoto2", backCID);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(false,this, mHttpTaskListener, context).execute();
	}
	
	/**
	 * 
	 * @todo:文件上传
	 * @date:2015-4-20 下午5:39:48
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param contentId
	 */
	
	public void requestUpLoadFile(TaskListenerWithState mHttpTaskListener,Context context,String userId,int type,File file,File firstFrame,String fileExt) {
		setRequestUrl(HttpURL.URL_UPLOAD_FILE);
		JSONObject body = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", userId);
			map.put("type", type);
			map.put("firstFrame", FileUtils.file2Bytes(firstFrame));
			map.put("file", FileUtils.file2Bytes(file));
			map.put("fileExt", fileExt);
			String strJson = JSON.toJSONString(map);
			body = new JSONObject(strJson);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(false, this, mHttpTaskListener, context).execute();
	}
	
	/**
	 * 
	 * @todo:推送开关
		userId	用户Id	string	Y	用户Id
		open	开启标志	int	Y	开启标志; 0：不开启；1：开启

	 */
	public void requestSwitchPush(TaskListenerWithState mHttpTaskListener,Context context,String userID,int open) {
		setRequestUrl(HttpURL.URL_SWITCH_PUSH);
		JSONObject body = new JSONObject();
		try {
			body.put("userId", userID);
			body.put("open", open);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(false,this, mHttpTaskListener, context).execute();
	}
	
}
