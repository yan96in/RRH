package com.cbkj.rrh.others.utils;
//package com.bgood.task.utils;
//
//
//import android.app.Activity;
//import android.text.TextUtils;
//
//import com.bgood.task.system.Const;
//import com.umeng.socialize.bean.SHARE_MEDIA;
//import com.umeng.socialize.controller.UMServiceFactory;
//import com.umeng.socialize.controller.UMSocialService;
//import com.umeng.socialize.media.QQShareContent;
//import com.umeng.socialize.media.QZoneShareContent;
//import com.umeng.socialize.media.SinaShareContent;
//import com.umeng.socialize.media.TencentWbShareContent;
//import com.umeng.socialize.media.UMImage;
//import com.umeng.socialize.sso.QZoneSsoHandler;
//import com.umeng.socialize.sso.SinaSsoHandler;
//import com.umeng.socialize.sso.TencentWBSsoHandler;
//import com.umeng.socialize.sso.UMQQSsoHandler;
//import com.umeng.socialize.weixin.controller.UMWXHandler;
//import com.umeng.socialize.weixin.media.CircleShareContent;
//import com.umeng.socialize.weixin.media.WeiXinShareContent;
//
//
///***
// * 42fd8d1759d69bf6c2fed61f50b24dd7
// * @todo:分享帮助类
// * @date:2014-11-20 上午11:13:31
// * @author:hg_liuzl@163.com
// */
//public class ShareUtils {
//	
//    public final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
//
//    private Activity mActivity;
//    public ShareUtils(Activity activity){
//    	this.mActivity = activity;
//    	  // 配置需要分享的相关平台
//        configPlatforms();
//        // 设置分享的内容
//      //  setShareContent();
//    }
//    
//    private void doShare(){
//    	mController.getConfig().setPlatforms(
//    			SHARE_MEDIA.WEIXIN,
//    			SHARE_MEDIA.WEIXIN_CIRCLE,
//    			SHARE_MEDIA.QQ,
//    			SHARE_MEDIA.QZONE,
//    			SHARE_MEDIA.SINA//SHARE_MEDIA.TENCENT
//    			);
//    	mController.openShare(mActivity, false);
//    }
//    
//   
//
//    /**
//     * 配置分享平台参数</br>
//     */
//    private void configPlatforms() {
//        // 添加新浪SSO授权
//        mController.getConfig().setSsoHandler(new SinaSsoHandler());
//        
//        mController.getConfig().setSinaCallbackUrl("http://www.showneng.com");
//        // 添加腾讯微博SSO授权
//        mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
//        
//        // 添加QQ、QZone平台
//        addQQQZonePlatform();
//        // 添加微信、微信朋友圈平台
//        addWXPlatform();
//    }
//    
//    
//    
//    
//
//
//    /**
//     * 根据不同的平台设置不同的分享内容</br>
//     */
//    public void setShareContent(String content,String imgUrl) {
//    	setShareContent(content, imgUrl, Const.SHARE_APP_DOWNLOAD);
//    }
//    
//    /**
//     * 根据不同的平台设置不同的分享内容</br>
//     */
//    public void setShareContent(String content,String imgUrl,String linkUrl) {
//    	 // 配置SSO
//        mController.getConfig().setSsoHandler(new SinaSsoHandler());
//        mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
//
//        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(mActivity,Const.QQ_APP_ID, Const.QQ_APP_KEY);
//        qZoneSsoHandler.addToSocialSDK();
//        mController.setShareContent(content);
//
//        UMImage urlImage = new UMImage(mActivity,TextUtils.isEmpty(imgUrl) ? Const.LOGO_URL:imgUrl);
//
//        WeiXinShareContent weixinContent = new WeiXinShareContent();
//        weixinContent.setShareContent(content);
//        weixinContent.setTitle(Const.SHARE_WORD_TITLE);
//        weixinContent.setTargetUrl(linkUrl);
//        weixinContent.setShareMedia(urlImage);
//        mController.setShareMedia(weixinContent);
//
//        // 设置朋友圈分享的内容
//        CircleShareContent circleMedia = new CircleShareContent();
//        circleMedia.setShareContent(content);
//        // circleMedia.setTitle("炫能，炫了更有可能!");
//        circleMedia.setTitle(content);
//        circleMedia.setShareImage(urlImage);
//        circleMedia.setTargetUrl(linkUrl);
//        mController.setShareMedia(circleMedia);
//
//        UMImage qzoneImage = new UMImage(mActivity,TextUtils.isEmpty(imgUrl) ? Const.LOGO_URL:imgUrl);
//        qzoneImage.setTargetUrl(Const.LOGO_URL);
//
//        // 设置QQ空间分享内容
//        QZoneShareContent qzone = new QZoneShareContent();
//        qzone.setShareContent(content);
//        qzone.setTargetUrl(linkUrl);
//        qzone.setTitle(Const.SHARE_WORD_TITLE);
//        qzone.setShareImage(urlImage);
//        mController.setShareMedia(qzone);
//
//
//        QQShareContent qqShareContent = new QQShareContent();
//        qqShareContent.setShareContent(content);
//        qqShareContent.setTitle(Const.SHARE_WORD_TITLE);
//        qqShareContent.setShareImage(urlImage);
//        qqShareContent.setTargetUrl(linkUrl);
//        mController.setShareMedia(qqShareContent);
//
//
//        TencentWbShareContent tencent = new TencentWbShareContent(urlImage);
//        tencent.setShareContent(content);
//        // 设置tencent分享内容
//        mController.setShareMedia(tencent);
//
//        SinaShareContent sinaContent = new SinaShareContent(urlImage);
//        sinaContent.setShareContent(content);
//        sinaContent.setTargetUrl(linkUrl);
//        mController.setShareMedia(sinaContent);
//
//        doShare();
//       
//    }
//    
//    
//    
//    /**QQ平台支持*/
//    private void addQQQZonePlatform() {
//        // 添加QQ支持, 并且设置QQ分享内容的target url
//        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(mActivity,Const.QQ_APP_ID, Const.QQ_APP_KEY);
//        qqSsoHandler.setTargetUrl(Const.SHARE_APP_DOWNLOAD);
//        qqSsoHandler.addToSocialSDK();
//
//        // 添加QZone平台
//        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(mActivity, Const.QQ_APP_ID, Const.QQ_APP_KEY);
//        qZoneSsoHandler.addToSocialSDK();
//    }
//    
//    
//    /**
//     * @功能描述 : 添加微信平台分享
//     * @return
//     */
//    private void addWXPlatform() {
//        // 添加微信平台
//        UMWXHandler wxHandler = new UMWXHandler(mActivity, Const.WX_APP_ID, Const.WX_APP_SECRET);
//        wxHandler.addToSocialSDK();
//
//        // 支持微信朋友圈
//        UMWXHandler wxCircleHandler = new UMWXHandler(mActivity,Const.WX_APP_ID, Const.WX_APP_SECRET);
//        wxCircleHandler.setToCircle(true);
//        wxCircleHandler.addToSocialSDK();
//    }
//}
