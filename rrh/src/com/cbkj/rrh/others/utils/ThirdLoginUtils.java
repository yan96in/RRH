package com.cbkj.rrh.others.utils;


import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;

import com.cbkj.rrh.db.PreferenceUtil;
import com.cbkj.rrh.main.Const;
import com.cbkj.rrh.main.help.IThirdActionCallback;
import com.cbkj.rrh.view.BToast;
import com.cbkj.rrh.view.LoadingProgress;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SocializeClientListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMDataListener;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;


/**
 * 
 * @todo:登录或绑定第三方平台
 * @date:2015-5-27 下午2:55:41
 * @author:hg_liuzl@163.com
 */
public class ThirdLoginUtils {
	
    public final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.login");
    private PreferenceUtil pUtils;
    private Activity mActivity;
    public ThirdLoginUtils(Activity activity,PreferenceUtil util){
    	this.mActivity = activity;
    	this.pUtils = util;
    	initData();
    }
    
    /**
     * 初始化操作
     */
    private void initData() {
    	
    	//设置新浪SSO handler
    	mController.getConfig().setSsoHandler(new SinaSsoHandler());
        mController.getConfig().setSinaCallbackUrl("http://www.showneng.com");
    	
        //添加QQ平台
    	UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(mActivity, Const.QQ_APP_ID,Const.QQ_APP_KEY);
    	qqSsoHandler.addToSocialSDK();
    	// 添加微信平台
    	UMWXHandler wxHandler = new UMWXHandler(mActivity,Const.WX_APP_ID,Const.WX_APP_SECRET);
    	wxHandler.addToSocialSDK();
    }
   
    
    
	/**
	 * 
	 * @todo:登录授权
	 * @date:2015-5-27 下午2:27:46
	 * @author:hg_liuzl@163.com
	 * @params:@param mActivity
	 * @params:@param platform
	 */
	public void doThirdLogin(final SHARE_MEDIA platform,final IThirdActionCallback callback) {
		mController.doOauthVerify(mActivity, platform,
				new UMAuthListener() {

					@Override
					public void onStart(SHARE_MEDIA platform) {
						BToast.show(mActivity, "开始获取授权！");
					}

					@Override
					public void onError(SocializeException e,SHARE_MEDIA platform) {
						BToast.show(mActivity, "获取授权失败！");
					}

					@Override
					public void onComplete(Bundle value, SHARE_MEDIA platform) {
						String uid = value.getString("uid");
						if (value != null && !TextUtils.isEmpty(uid)) {
							BToast.show(mActivity, "获取授权成功！");
							// uid不为空，获取用户信息
							getUserInfo(mActivity,platform,callback);
						} else {
							BToast.show(mActivity, "获取授权失败！");
						}
					}

					@Override
					public void onCancel(SHARE_MEDIA platform) {
						BToast.show(mActivity, "取消授权！");
					}
				});
		}
    
	
	/**
	 * 获取用户信息
	 * @param platform
	 */
	private void getUserInfo(final Activity mActivity,final SHARE_MEDIA platform,final IThirdActionCallback callback) {
		mController.getPlatformInfo(mActivity, platform,
				new UMDataListener() {

					@Override
					public void onStart() {
						LoadingProgress.getInstance().show(mActivity, "开始获取用户资料...");
					}

					@Override
					public void onComplete(int status, Map<String, Object> info) {
						if (info != null) {
							if (platform == SHARE_MEDIA.SINA) {
								pUtils.setBindSina(true);
							} else if (platform == SHARE_MEDIA.QQ) {
								pUtils.setBindQQ(true);
							}else if(platform == SHARE_MEDIA.WEIXIN){
								pUtils.setBindWX(true);
							}
							if (null!=callback) {
								callback.setInfo(info);
								callback.actionType(platform);
							}
						}
					}
				});
	}

	/**
	 * 
	 * @todo:解绑第三方
	 * @date:2015-5-27 下午2:43:51
	 * @author:hg_liuzl@163.com
	 * @params:@param mActivity
	 * @params:@param platform
	 */
	public void doThirdUnBind(final SHARE_MEDIA platform,final IThirdActionCallback callback) {
		mController.deleteOauth(mActivity, platform,
				new SocializeClientListener() {

					@Override
					public void onStart() {

					}

					@Override
					public void onComplete(int status, SocializeEntity entity) {
						String showText = "解除" + platform.toString() + "平台授权成功";
						if (status != StatusCode.ST_CODE_SUCCESSED) {
							showText = "解除" + platform.toString() + "平台授权失败["+ status + "]";
						} else {
							if (platform == SHARE_MEDIA.SINA) {
								pUtils.setBindSina(false);
							} else if (platform == SHARE_MEDIA.QQ) {
								pUtils.setBindQQ(false);
							}else if(platform == SHARE_MEDIA.WEIXIN){
								pUtils.setBindWX(false);
							}
						}
						callback.actionType(platform);
						BToast.show(mActivity, showText);
					}
				});
	}
	
}
