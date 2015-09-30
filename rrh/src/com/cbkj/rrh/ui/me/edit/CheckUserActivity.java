package com.cbkj.rrh.ui.me.edit;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cbkj.rrh.R;
import com.cbkj.rrh.bean.UserBean;
import com.cbkj.rrh.net.http.HttpRequest;
import com.cbkj.rrh.net.http.HttpRequestAsyncTask.TaskListenerWithState;
import com.cbkj.rrh.net.http.HttpResponse;
import com.cbkj.rrh.net.http.HttpResponse.HttpTaskState;
import com.cbkj.rrh.net.request.UserRequest;
import com.cbkj.rrh.ui.base.BaseBackActivity;
import com.cbkj.rrh.view.BToast;
import com.cbkj.rrh.widget.TitleBar;

/**
 * @todo:验证身份
 * @date:2015-8-10 下午8:16:31
 * @author:hg_liuzl@163.com
 */
public class CheckUserActivity extends BaseBackActivity implements TaskListenerWithState {

	private UserBean mUserBean;
	private EditText etPwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_check_layout);
		mUserBean = pUitl.getUserBean();
		initView();
	}

	private void initView() {
		TitleBar mTitleBar = new TitleBar(mActivity);
		mTitleBar.initAllBar("身份验证");

		TextView tvNick = (TextView) findViewById(R.id.tv_nick);
		tvNick.setText(mUserBean.nickName);

		etPwd = (EditText) findViewById(R.id.et_password);

	}

	public void submitCheck(View v) {
		String pwd = etPwd.getText().toString().trim();

		if (TextUtils.isEmpty(pwd)) {
			BToast.show(mActivity, "请输入登录密码");
			return;
		} else {
			UserRequest.getInstance().requestLogin(this, mActivity,mUserBean.telephone, pwd);
		}
	}

	@Override
	public void onTaskOver(HttpRequest request, HttpResponse response) {
		if (response.getState() == HttpTaskState.STATE_OK && null!=response.result ) {
			if (response.result.getSuccess()) {
				Intent intent = new Intent(mActivity,EditAlipayActivity.class);
				startActivity(intent);
				finish();
			}else{
				BToast.show(mActivity, response.result.getErrorMsg());
			}
		}else{	//系统出错
			BToast.show(mActivity,R.string.system_error);
		}
		
	}
}
