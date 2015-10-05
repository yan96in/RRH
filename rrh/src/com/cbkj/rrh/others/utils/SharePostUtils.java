package com.cbkj.rrh.others.utils;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import com.cbkj.rrh.R;
import com.cbkj.rrh.main.Const;
import com.cbkj.rrh.view.BToast;
import com.cbkj.rrh.view.dialog.BottomDialog;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.TencentWbShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

/**
 * @todo:自定义分享界面
 * @date:2015-5-27 下午6:42:10
 * @author:hg_liuzl@163.com
 */
public class SharePostUtils implements OnClickListener {
	
	public static final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
	
	private Activity mActivity = null;
	
	/**
	 * 
	 * @todo:分享App给好友的
	 * @date:2015-5-27 下午7:03:39
	 * @author:hg_liuzl@163.com
	 */
	public SharePostUtils(Activity activity){
		this.mActivity = activity;
		configPlatforms();
	}

	/**
	 * 自定义分享界面,注意每次都需要这个lwd
	 */
	BottomDialog recommendDialog = null;
	private void doRecommendDialog() {
		if(recommendDialog == null) {
			recommendDialog = new BottomDialog(mActivity);
		}
		
		View v = LayoutInflater.from(mActivity).inflate(R.layout.model_share_layout, null);
		v.findViewById(R.id.iw_share_wx).setOnClickListener(this);
		v.findViewById(R.id.iw_share_qq).setOnClickListener(this);
		v.findViewById(R.id.iw_share_qzone).setOnClickListener(this);
		v.findViewById(R.id.iw_share_circle).setOnClickListener(this);
		v.findViewById(R.id.iw_share_sina).setOnClickListener(this);
		v.findViewById(R.id.tv_cancel).setOnClickListener(this);
		recommendDialog.setvChild(v);
		recommendDialog.show();
	}
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.iw_share_qq:
				postShare(SHARE_MEDIA.QQ);
				recommendDialog.dismiss();
				break;
			case R.id.iw_share_qzone:
				postShare(SHARE_MEDIA.QZONE);
				recommendDialog.dismiss();
				break;
			case R.id.iw_share_wx:
				postShare(SHARE_MEDIA.WEIXIN);
				recommendDialog.dismiss();
				break;
			case R.id.iw_share_circle:
				postShare(SHARE_MEDIA.WEIXIN_CIRCLE);
				recommendDialog.dismiss();
				break;
			case R.id.iw_share_sina:
				postShare(SHARE_MEDIA.SINA);
				recommendDialog.dismiss();
				break;
			case R.id.tv_cancel:
				recommendDialog.dismiss();
				break;

		default:
			break;
		}
	}
	
	/***
	 * 
	 * @todo:分享操作
	 * @date:2015-5-27 下午6:48:15
	 * @author:hg_liuzl@163.com
	 * @params:@param platform
	 */
    private void postShare(SHARE_MEDIA platform) {
        mController.postShare(mActivity, platform, new SnsPostListener() {

            @Override
            public void onStart() {
            	//BToast.show(mActivity, "开始分享");
            }
            

            @Override
            public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                if (eCode == StatusCode.ST_CODE_SUCCESSED) {
                	BToast.show(mActivity, "分享成功");
                } else {
                    BToast.show(mActivity, "分享失败");
                }
            }
        });
    }
    
    /**
     * 配置分享平台参数</br>
     */
    private void configPlatforms() {
        // 添加新浪SSO授权
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        
        mController.getConfig().setSinaCallbackUrl("https://api.weibo.com/oauth2/default.html");
        // 添加腾讯微博SSO授权
        mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
        
        // 添加QQ、QZone平台
        addQQQZonePlatform();
        // 添加微信、微信朋友圈平台
        addWXPlatform();
    }

    /**
     * 根据不同的平台设置不同的分享内容</br>
     */
    public void setShareApp(String content) {
    	setShareContent(content, null,Const.SHARE_APP_DOWNLOAD,-1);
    }

//    /**
//     * 
//     * @todo:分享当前实体类
//     * @date:2015-6-12 上午9:33:52
//     * @author:hg_liuzl@163.com
//     * @params:@param bean
//     */
//    public void setShareBean(ContentBean bean){
//    	if (null == bean) {
//			return;
//		}
//    	this.mShareType = ShareType.SHARE_CONTENT;
//    	this.contentBean = bean;
//    	
//    	if (bean.type == Const.TYPE_PIC) {
//			setShareContent(bean.content, bean.fileUrl.size() > 0 ? bean.fileUrl.get(0).big:null,bean.getShareUrl());
//		} else {
//			setShareContent(bean.content, bean.firstFrame,bean.getShareUrl());
//		}
//    }
    
    
    
    /**
     * 根据不同的平台设置不同的分享内容</br>
     */
    public void setShareContent(String oldContent,String imgUrl,String linkUrl,long money) {
    	String title = Const.SHARE_WORD_TITLE;
    	String content = oldContent;
        if (money != -1) {
        	title = String.format(Const.SHARE_MONEY_TITLE,money);
        	content = Const.SHARE_WORD_TITLE+"\n"+oldContent;
		}
    	
    	
    	doRecommendDialog();
    	 // 配置SSO
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        mController.getConfig().setSsoHandler(new TencentWBSsoHandler());

        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(mActivity,Const.QQ_APP_ID, Const.QQ_APP_KEY);
        qZoneSsoHandler.addToSocialSDK();
        mController.setShareContent(content);

        UMImage urlImage = new UMImage(mActivity,TextUtils.isEmpty(imgUrl) ? Const.LOGO_URL:imgUrl);

        WeiXinShareContent weixinContent = new WeiXinShareContent();
        weixinContent.setShareContent(content);
       	weixinContent.setTitle(title);
        weixinContent.setTargetUrl(linkUrl);
        weixinContent.setShareMedia(urlImage);
        mController.setShareMedia(weixinContent);

        // 设置朋友圈分享的内容
        CircleShareContent circleMedia = new CircleShareContent();
        circleMedia.setShareContent(content);
        
        circleMedia.setTitle(title+"\n"+content);
        
        circleMedia.setShareImage(urlImage);
        circleMedia.setTargetUrl(linkUrl);
        mController.setShareMedia(circleMedia);

        UMImage qzoneImage = new UMImage(mActivity,TextUtils.isEmpty(imgUrl) ? Const.LOGO_URL:imgUrl);
        qzoneImage.setTargetUrl(Const.LOGO_URL);

        // 设置QQ空间分享内容
        QZoneShareContent qzone = new QZoneShareContent();
        qzone.setShareContent(content);
        qzone.setTargetUrl(linkUrl);
        qzone.setTitle(title);
        qzone.setShareImage(urlImage);
        mController.setShareMedia(qzone);


        QQShareContent qqShareContent = new QQShareContent();
        qqShareContent.setShareContent(content);
        qqShareContent.setTitle(title);
        qqShareContent.setShareImage(urlImage);
        qqShareContent.setTargetUrl(linkUrl);
        mController.setShareMedia(qqShareContent);


        TencentWbShareContent tencent = new TencentWbShareContent(urlImage);
        tencent.setShareContent(content);
        // 设置tencent分享内容
        mController.setShareMedia(tencent);

        SinaShareContent sinaContent = new SinaShareContent(urlImage);
//      SinaShareContent sinaContent = new SinaShareContent();
//      sinaContent.setTitle(title);
        sinaContent.setShareContent(money != -1?(title+"！"+content+linkUrl):content+linkUrl);
        sinaContent.setTargetUrl(linkUrl);
        mController.setShareMedia(sinaContent);
       
    }
    
    
    
    /**QQ平台支持*/
    private void addQQQZonePlatform() {
        // 添加QQ支持, 并且设置QQ分享内容的target url
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(mActivity,Const.QQ_APP_ID, Const.QQ_APP_KEY);
        qqSsoHandler.setTargetUrl(Const.SHARE_APP_DOWNLOAD);
        qqSsoHandler.addToSocialSDK();

        // 添加QZone平台
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(mActivity, Const.QQ_APP_ID, Const.QQ_APP_KEY);
        qZoneSsoHandler.addToSocialSDK();
    }
    
    
    /**
     * @功能描述 : 添加微信平台分享
     * @return
     */
    private void addWXPlatform() {
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(mActivity, Const.WX_APP_ID, Const.WX_APP_SECRET);
        wxHandler.addToSocialSDK();

        // 支持微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(mActivity,Const.WX_APP_ID, Const.WX_APP_SECRET);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
    }

}
