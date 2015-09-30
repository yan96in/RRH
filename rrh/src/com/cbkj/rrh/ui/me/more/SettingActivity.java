package com.cbkj.rrh.ui.me.more;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.cbkj.rrh.R;
import com.cbkj.rrh.bean.ApkBean;
import com.cbkj.rrh.bean.UserBean;
import com.cbkj.rrh.db.DataCleanManager;
import com.cbkj.rrh.net.http.HttpRequest;
import com.cbkj.rrh.net.http.HttpRequestAsyncTask.TaskListenerWithState;
import com.cbkj.rrh.net.http.HttpResponse;
import com.cbkj.rrh.net.http.HttpResponse.HttpTaskState;
import com.cbkj.rrh.net.http.HttpURL;
import com.cbkj.rrh.net.request.UserRequest;
import com.cbkj.rrh.push.MessageReceiver;
import com.cbkj.rrh.system.Const;
import com.cbkj.rrh.ui.TaskUtil;
import com.cbkj.rrh.ui.account.LoginActivity;
import com.cbkj.rrh.ui.account.ModifyPasswordActivity;
import com.cbkj.rrh.ui.base.BaseBackActivity;
import com.cbkj.rrh.utils.SharePostUtils;
import com.cbkj.rrh.utils.update.UpdateManager;
import com.cbkj.rrh.view.BToast;
import com.cbkj.rrh.view.LoadingProgress;
import com.cbkj.rrh.view.dialog.BottomDialog;
import com.cbkj.rrh.view.dialog.DialogFactory;
import com.cbkj.rrh.widget.SelectItemView;
import com.cbkj.rrh.widget.TitleBar;
import com.umeng.analytics.MobclickAgent;

/**
 * @todo:设置模块
 * @date:2015-4-22 下午3:12:53
 * @author:hg_liuzl@163.com
 */
public class SettingActivity extends BaseBackActivity implements OnClickListener,TaskListenerWithState {
	private SelectItemView sivSwitchPush,sivContactService,sivUserGuide,sivClearCache,sivServiceTerms;
	private BottomDialog loginOutDialog = null;
	private UserBean userBean;
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		sivUserGuide.setIvTag(pUitl.hasReadGuide()?R.drawable.icon_next:R.drawable.icon_remenber_tip);
		sivServiceTerms.setIvTag(pUitl.hasReadServiceTerms()?R.drawable.icon_next:R.drawable.icon_remenber_tip);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_setting_layout);
		userBean = pUitl.getUserBean();
		new TitleBar(SettingActivity.this).initTitleBar("设置");
		
		sivSwitchPush = (SelectItemView) findViewById(R.id.siv_switch_push);
		sivSwitchPush.showToogle();
		sivSwitchPush.showIV(View.GONE);
		sivSwitchPush.tbn.setChecked(userBean.openPush == 1?true:false);
		sivSwitchPush.tbn.setText("");
		sivSwitchPush.tbn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				
				if (isChecked) {
					BToast.show(mActivity, "开启任务推送");
					userBean.openPush = 1;
				} else {
					BToast.show(mActivity, "关闭任务推送");
					userBean.openPush = 0;
				}
				pUitl.setUserBean(userBean);
				UserRequest.getInstance().requestSwitchPush(SettingActivity.this, SettingActivity.this, pUitl.getUserID(), isChecked?1:0);
			}
		});
		
		sivContactService = (SelectItemView) findViewById(R.id.siv_conact_servicer);
		sivContactService.setOnClickListener(this);
		sivContactService.showIV(View.GONE);
		sivContactService.showText(Const.APP_CONTACT_TELEPHONE);
		
		sivUserGuide = (SelectItemView) findViewById(R.id.siv_user_guide);
		sivUserGuide.setOnClickListener(this);
		
		sivServiceTerms = (SelectItemView) findViewById(R.id.siv_service_terms);
		sivServiceTerms.setOnClickListener(this);
		
		findViewById(R.id.siv_feedback).setOnClickListener(this);
		findViewById(R.id.siv_common_score).setOnClickListener(this);
		findViewById(R.id.siv_check_version).setOnClickListener(this);
		sivClearCache = (SelectItemView) findViewById(R.id.siv_clear_cache);
		sivClearCache.showIV(View.GONE);
		sivClearCache.setOnClickListener(this);
		sivClearCache.showText(DataCleanManager.getSize(this.getFilesDir(),this.getCacheDir()));
		findViewById(R.id.siv_about).setOnClickListener(this);
		findViewById(R.id.siv_recommend).setOnClickListener(this);
		findViewById(R.id.siv_modify_pwd).setOnClickListener(this);
		findViewById(R.id.tv_login_out).setOnClickListener(this);
		TextView tv = (TextView) findViewById(R.id.tv_tag);
		tv.setText(HttpURL.BASE_URL.startsWith("http://service.lalawitkey.com")?"外网":"内网");
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.siv_conact_servicer:
			TaskUtil.getInstance().callContact(mActivity, Const.APP_CONTACT_TELEPHONE);
			break;
		case R.id.siv_user_guide:
			intent = new Intent(mActivity, UserGuideActivity.class);
			startActivity(intent);
			pUitl.setReadGuide(true);
			sendBroadcast(new Intent(MessageReceiver.MSG_RECEIVER_TAG));	
			break;
		case R.id.siv_service_terms:	//服务条款
			intent = new Intent(mActivity, ServiceTermActivity.class);
			startActivity(intent);
			pUitl.setReadServiceTerms(true);
			sendBroadcast(new Intent(MessageReceiver.MSG_RECEIVER_TAG));	
			break;
		case R.id.siv_feedback:
			MobclickAgent.onEvent(this,"me_feedback_click");
			intent = new Intent(mActivity, FeedbackActivity.class);
			startActivity(intent);
			break;
		
		case R.id.siv_common_score:
			score();
			break;

		case R.id.siv_check_version:
			UserRequest.getInstance().requestCheckUpgrade(this, mActivity);
			break;
		case R.id.siv_clear_cache:
			clearCache();
			break;
		case R.id.siv_about:
			intent = new Intent(mActivity, AboutUsActivity.class);
			startActivity(intent);
			break;
		case R.id.siv_modify_pwd:
			intent = new Intent(mActivity, ModifyPasswordActivity.class);
			startActivity(intent);
			break;
		case R.id.siv_recommend:
			new SharePostUtils(mActivity).setShareApp(Const.SHARE_APP);
			break;
		case R.id.tv_login_out:
			loginOutDialog = DialogFactory.affirmDialogWithTitle(mActivity, "确定退出", "确  定", this, this);
			break;
		case R.id.tv_select_ok:
			LoginActivity.quitLogin(pUitl, mActivity);
			loginOutDialog.dismiss();
         	break;
         case R.id.tv_select_cancel:
         	loginOutDialog.dismiss();
		default:
			break;
		}
	}
	
	/**
	 * 
	 * @todo:评分
	 * @date:2014-10-29 下午8:00:39
	 * @author:hg_liuzl@163.com
	 * @params:
	 */
	private void score() {
		Uri uri = Uri.parse("market://details?id=" + getPackageName());
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
	
	/**
	 * 
	 * @todo:清除缓存
	 * @date:2015-4-22 下午3:28:22
	 * @author:hg_liuzl@163.com
	 * @params:
	 */
	private void clearCache() {
		(new ClearDataTask()).execute();
	}
	
	private class ClearDataTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected void onPreExecute() {
			LoadingProgress.getInstance().show(mActivity);
			super.onPreExecute();
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			DataCleanManager.customClean(SettingActivity.this.getFilesDir().getAbsolutePath(),SettingActivity.this.getCacheDir().getAbsolutePath());
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			LoadingProgress.getInstance().dismiss();
			sivClearCache.showText(DataCleanManager.getSize(SettingActivity.this.getFilesDir(),SettingActivity.this.getCacheDir()));
		}
		
	}

	@Override
	public void onTaskOver(HttpRequest request, HttpResponse response) {
		if (response.getState() == HttpTaskState.STATE_OK) {
			if (HttpURL.URL_CHECK_UPDATE == request.getRequestUrl()) {	//检查升级接口
				if (null != response.result && response.result.getSuccess()) {
					/**版本升级处理*/
					final ApkBean apk = JSON.parseObject(response.result.getStrBody(), ApkBean.class);
					UpdateManager manager = new UpdateManager(mActivity, apk);
					manager.checkUpdateInfo(true);
				}else{
					BToast.show(mActivity, response.result.getErrorMsg());
				}
			} else if(HttpURL.URL_SWITCH_PUSH == request.getRequestUrl()){	//是否开启推送
				
			}else {
				BToast.show(mActivity, R.string.system_error);
			}
		}
	}
}
