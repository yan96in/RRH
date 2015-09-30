package com.cbkj.rrh.ui.me.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.cbkj.rrh.R;
import com.cbkj.rrh.bean.NameCardBean;
import com.cbkj.rrh.net.http.HttpRequest;
import com.cbkj.rrh.net.http.HttpRequestAsyncTask.TaskListenerWithState;
import com.cbkj.rrh.net.http.HttpResponse;
import com.cbkj.rrh.net.http.HttpResponse.HttpTaskState;
import com.cbkj.rrh.net.request.TaskRequest;
import com.cbkj.rrh.system.BGApp;
import com.cbkj.rrh.ui.base.BaseBackActivity;
import com.cbkj.rrh.view.BToast;
import com.cbkj.rrh.view.CertificateView;
import com.cbkj.rrh.view.CommentView;
import com.cbkj.rrh.view.RoundImageView;
import com.cbkj.rrh.widget.SelectItemView;
import com.cbkj.rrh.widget.TitleBar;

/**
 * @todo:雇主名片
 * @date:2015-7-27 上午9:40:36
 * @author:hg_liuzl@163.com
 */
public class SentterNameCardActivity extends BaseBackActivity implements TaskListenerWithState,OnClickListener {
	
	private LinearLayout llAllScreen;
	private ImageView ivSex;
	private RoundImageView ivHead;
	private TextView tvNick;
	private SelectItemView sivSentSuccess,sivSentTask;
	private String mEmployerID = "";	//雇主ID
	private CertificateView view;
	
	public static void lookNameCard(Activity mActivity,String employerId){
		Intent intent = new Intent(mActivity,SentterNameCardActivity.class);
		intent.putExtra(NameCardBean.KEY_USER_ID, employerId);
		mActivity.startActivity(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mEmployerID = getIntent().getStringExtra(NameCardBean.KEY_USER_ID);
		setContentView(R.layout.a_sentter_namecard_layout);

		initTitle();
		initView();
		requestData();
	}
	
	private void initTitle() {
		TitleBar title = new TitleBar(mActivity);
		title.initAllBar("雇主名片");
		llAllScreen = (LinearLayout) findViewById(R.id.ll_screen);
		llAllScreen.setVisibility(View.GONE);

	}
	
	private void initView() {

		
		ivHead = (RoundImageView) findViewById(R.id.riv_user);
		tvNick = (TextView) findViewById(R.id.tv_nick);
		ivSex = (ImageView) findViewById(R.id.iv_sex);
		
		sivSentSuccess = (SelectItemView) findViewById(R.id.siv_sent_success);
		sivSentSuccess.setTagVisible(View.GONE);
		
		sivSentTask = (SelectItemView) findViewById(R.id.siv_sent_task);
		sivSentTask.setOnClickListener(this);
		
		view = new CertificateView(mActivity, View.GONE,"他的证书:");
	}
	
	private void requestData() {
		TaskRequest.getInstance().requestSenterNameCard(this, this, mEmployerID);
	}
	
	private void setData(final NameCardBean bean) {
		
		BGApp.getInstance().setImageSqure(bean.smallImg, ivHead);
		tvNick.setText(bean.nickName);
		ivSex.setBackgroundResource(bean.gender == 0 ? R.drawable.icon_female:R.drawable.icon_male);
		sivSentSuccess.setTvContent(bean.successLTimes+"次");
		
		view.setData(bean.certificates, bean.userId);
		
		new CommentView(mActivity, "接单人对他的评价:", bean.scores);
		
		llAllScreen.setVisibility(View.VISIBLE);
	}

	@Override
	public void onTaskOver(HttpRequest request, HttpResponse response) {
		if (response.getState() == HttpTaskState.STATE_OK && null != response.result ) {
			if (response.result.getSuccess()) {
				final String strJson = response.result.getStrBody();
				final NameCardBean bean = JSON.parseObject(strJson, NameCardBean.class);
				setData(bean);
			} else {
				BToast.show(mActivity,response.result.getErrorMsg());
				finish();
			}
		}else{
			BToast.show(mActivity, R.string.system_error);
			finish();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.siv_sent_task:
			Intent intent = new Intent(mActivity,PersonTaskActivity.class);
			intent.putExtra(NameCardBean.KEY_USER_ID, mEmployerID);
			startActivity(intent);
			break;

		default:
			break;
		}
		
	}
}
