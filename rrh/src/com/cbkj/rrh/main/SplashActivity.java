package com.cbkj.rrh.main;import android.content.Intent;import android.media.MediaPlayer;import android.media.MediaPlayer.OnCompletionListener;import android.os.Bundle;import android.text.TextUtils;import android.view.View;import android.view.View.OnClickListener;import android.widget.ImageView;import android.widget.RelativeLayout;import android.widget.VideoView;import com.alibaba.fastjson.JSON;import com.cbkj.rrh.R;import com.cbkj.rrh.bean.UserBean;import com.cbkj.rrh.main.account.LoginActivity;import com.cbkj.rrh.main.account.RegisterActivity;import com.cbkj.rrh.main.base.BaseActivity;import com.cbkj.rrh.net.http.HttpRequest;import com.cbkj.rrh.net.http.HttpRequestAsyncTask.TaskListenerWithState;import com.cbkj.rrh.net.http.HttpResponse;import com.cbkj.rrh.net.http.HttpResponse.HttpTaskState;import com.cbkj.rrh.net.request.UserRequest;import com.cbkj.rrh.others.utils.ConfigUtil;import com.cbkj.rrh.others.utils.LogUtils;import com.cbkj.rrh.others.utils.ToolUtils;import com.cbkj.rrh.view.BToast;import com.tencent.android.tpush.XGPushManager;import com.umeng.analytics.MobclickAgent;/** * @todo:应用的闪屏页 * @date:2014-10-28 下午6:49:41 * @author:hg_liuzl@163.com */public class SplashActivity extends BaseActivity implements TaskListenerWithState,OnClickListener {		/**请求查看欢迎页*/	private static final int REQUEST_WELCOME = 100;	private RelativeLayout rlAccount;	private String mAccountNumber;	private String mPassWord;	private ImageView ivReplay;	private VideoView vvideo;	private boolean isLoginAgain = false;	@Override	protected void onCreate(Bundle savedInstanceState) {		super.onCreate(savedInstanceState);		MobclickAgent.onEvent(SplashActivity.this,"sys_start");		isLoginAgain = getIntent().getBooleanExtra(LoginActivity.TO_LOGIN_REGISTER,false);		mAccountNumber = pUitl.getAccountNumber();		mPassWord = pUitl.getAccountPassword();				if (!TextUtils.isEmpty(pUitl.getUserID())) {			//注册信鸽推送,绑定推送账号			XGPushManager.registerPush(getApplicationContext(),pUitl.getUserID());		}				initView();				if(ToolUtils.hasUpdate(pUitl.getHistoryVersion(), mActivity)){	//如果当前版本大于历史版本			pUitl.setHistoryVersion(ConfigUtil.getVersionName(mActivity));			pUitl.setShowWelcomePage(false);	//重新展开引导页		}		requestServer();	}	private void initView() {		setContentView(R.layout.main_a_splash_layout);						ivReplay = (ImageView) findViewById(R.id.iv_replay);		ivReplay.setOnClickListener(this);				rlAccount = (RelativeLayout) findViewById(R.id.rl_account_enter);		findViewById(R.id.btn_register).setOnClickListener(this);		findViewById(R.id.btn_login).setOnClickListener(this);		vvideo = (VideoView) findViewById(R.id.vv_play);		vvideo.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.my_video_file);		vvideo.setOnCompletionListener(new OnCompletionListener() {			@Override			public void onCompletion(MediaPlayer mp) {				BToast.show(mActivity, "播放完毕!");			}		});	}				/**登录进入主页*/	private void requestServer() {		// 如果有网络，且本地已经保存了用户名和密码，则直接登录，获取用户资料		if (!TextUtils.isEmpty(mAccountNumber) && !TextUtils.isEmpty(mPassWord)) {			if (ConfigUtil.isConnect(mActivity)) {				UserRequest.getInstance().requestLogin(this, this,mAccountNumber, mPassWord);			} else {				gotoMain();			}		}	}		/**	 * 进入主界面	 */	private void gotoMain(){		Intent i = new Intent();		i.setClass(SplashActivity.this, MainActivity.class);		startActivity(i);		finish();	}	@Override	public void onClick(View v) {		Intent intent = null;		switch (v.getId()) {			case R.id.ll_view:				requestServer();				break;			case R.id.btn_register:				intent = new Intent(mActivity, RegisterActivity.class);				startActivity(intent);				break;			case R.id.btn_login:				intent = new Intent(mActivity, LoginActivity.class);				startActivity(intent);				break;			case R.id.iv_replay:								break;			default:				break;		}	}	@Override	public void onTaskOver(HttpRequest request, HttpResponse response) {		if (response.getState() == HttpTaskState.STATE_OK) {			if (null != response.result && response.result.getSuccess()) {				dealLogin(response);			} else {				pUitl.setAccountNumber("");				pUitl.setAccountPassword("");				BToast.show(mActivity, response.result.getErrorMsg());			}		} 	}		/**	 * 	 * @todo:处理登录	 * @date:2015-5-6 上午10:04:47	 * @author:hg_liuzl@163.com	 * @params:	 */	private void dealLogin(HttpResponse response) {		if (response.result.getSuccess()) {			pUitl.setHasLogin(true);			UserBean userBean = JSON.parseObject(response.result.getStrBody(), UserBean.class);			pUitl.setUserBean(userBean);			gotoMain();					}else{			pUitl.setAccountNumber("");			pUitl.setAccountPassword("");			BToast.show(mActivity, response.result.getErrorMsg());		}	}}