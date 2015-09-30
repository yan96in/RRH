package com.cbkj.rrh.ui;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.cbkj.rrh.R;
import com.cbkj.rrh.net.http.HttpRequest;
import com.cbkj.rrh.net.http.HttpRequestAsyncTask.TaskListenerWithState;
import com.cbkj.rrh.net.http.HttpResponse;
import com.cbkj.rrh.net.http.HttpResponse.HttpTaskState;
import com.cbkj.rrh.net.request.UserRequest;
import com.cbkj.rrh.ui.account.LoginActivity;
import com.cbkj.rrh.ui.account.RegisterActivity;
import com.cbkj.rrh.ui.base.BaseActivity;
import com.cbkj.rrh.ui.welcome.NavigateActivity;
import com.cbkj.rrh.utils.ConfigUtil;
import com.cbkj.rrh.utils.ToolUtils;
import com.cbkj.rrh.view.BToast;
import com.tencent.android.tpush.XGPushManager;
import com.umeng.analytics.MobclickAgent;

/**
 * @todo:加载页
 * @date:2014-10-28 下午6:49:41
 * @author:hg_liuzl@163.com
 */
public class IndexActivity extends BaseActivity implements TaskListenerWithState,OnClickListener {
	
	/**请求查看欢迎页*/
	private static final int REQUEST_WELCOME = 100;
	private RelativeLayout rlAccount;
	private String mAccountNumber;
	private String mPassWord;
	private boolean isLoginAgain = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MobclickAgent.onEvent(IndexActivity.this,"sys_start");
		isLoginAgain = getIntent().getBooleanExtra(LoginActivity.TO_LOGIN_REGISTER,false);
		Context context = getApplicationContext();
		if (!TextUtils.isEmpty(pUitl.getUserID())) {
			//注册信鸽推送,绑定推送账号
			XGPushManager.registerPush(context,pUitl.getUserID());
		}
		
		setContentView(R.layout.a_index);
		findViewById(R.id.ll_view).setOnClickListener(this);
		rlAccount = (RelativeLayout) findViewById(R.id.rl_account_enter);
		findViewById(R.id.btn_register).setOnClickListener(this);
		findViewById(R.id.btn_login).setOnClickListener(this);
		mAccountNumber = pUitl.getAccountNumber();
		mPassWord = pUitl.getAccountPassword();
		
		if(ToolUtils.hasUpdate(pUitl.getHistoryVersion(), mActivity)){	//如果当前版本大于历史版本
			pUitl.setHistoryVersion(ConfigUtil.getVersionName(mActivity));
			pUitl.setShowWelcomePage(false);	//重新展开引导页
		}
		initPage();
	}
	
	/**
	 * 
	 * @todo:初始化页面
	 * @date:2015-4-3 上午11:09:27
	 * @author:hg_liuzl@163.com
	 * @params:
	 */
	private void initPage() {
		if(!pUitl.getShowWelcomePage()){	//如果没有向导过
			gotoNavigate();
		}else{//已经向导过了
			requestServer();
		}
	}
	
	/**
	 * 
	 * @todo:登录服务器
	 * @date:2015-9-10 下午3:25:25
	 * @author:hg_liuzl@163.com
	 * @params:
	 */
	private void requestServer() {
		// 如果有网络，且本地已经保存了用户名和密码，则直接登录，获取用户资料
		if (!TextUtils.isEmpty(mAccountNumber) && !TextUtils.isEmpty(mPassWord)) {
			if (ConfigUtil.isConnect(mActivity)) {
				UserRequest.getInstance().requestLogin(this, this,mAccountNumber, mPassWord);
			} else {
				gotoMain();
			}
		}
	}
	
	/**
	 * 进入主界面
	 */
	private void gotoMain(){
		
/*		try {
			Thread.sleep(1500);	//延时两秒进入
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		
		Intent i = new Intent();
		i.setClass(IndexActivity.this, MainActivity.class);
		startActivity(i);
		finish();
		//overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
	}
	/**
	 * 进入使用向导
	 */
	private void gotoNavigate(){
		Intent i = new Intent();
		i.setClass(IndexActivity.this, NavigateActivity.class);
		startActivityForResult(i, REQUEST_WELCOME);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		   super.onActivityResult(requestCode, resultCode, data);
		   if(requestCode == REQUEST_WELCOME && resultCode == RESULT_OK){
			   requestServer();
		   }else{
			   initPage(); //重头再来
		   }
		}
	
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		
		if (isLoginAgain || TextUtils.isEmpty(mAccountNumber)||TextUtils.isEmpty(mPassWord)) {
			rlAccount.setVisibility(View.VISIBLE);
		} else {
			rlAccount.setVisibility(View.GONE);
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
			case R.id.ll_view:
				requestServer();
				break;
			case R.id.btn_register:
				intent = new Intent(mActivity, RegisterActivity.class);
				startActivity(intent);
				break;
			case R.id.btn_login:
				intent = new Intent(mActivity, LoginActivity.class);
				startActivity(intent);
				break;
			default:
				break;
		}
	}

	@Override
	public void onTaskOver(HttpRequest request, HttpResponse response) {
		if (response.getState() == HttpTaskState.STATE_OK) {
			if (null != response.result && response.result.getSuccess()) {
				dealLogin(response);
			} else {
				pUitl.setAccountNumber("");
				pUitl.setAccountPassword("");
				BToast.show(mActivity, response.result.getErrorMsg());
			}
		} 
	}
	
	/**
	 * 
	 * @todo:处理登录
	 * @date:2015-5-6 上午10:04:47
	 * @author:hg_liuzl@163.com
	 * @params:
	 */
	private void dealLogin(HttpResponse response) {
		if (response.result.getSuccess()) {
			pUitl.setHasLogin(true);
			pUitl.setUserBean(response.result.getStrBody());
			gotoMain();			
		}else{
			pUitl.setAccountNumber("");
			pUitl.setAccountPassword("");
			BToast.show(mActivity, response.result.getErrorMsg());
		}
	}

}
