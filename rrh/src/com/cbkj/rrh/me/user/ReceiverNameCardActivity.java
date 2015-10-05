package com.cbkj.rrh.me.user;

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
import com.cbkj.rrh.main.BGApp;
import com.cbkj.rrh.main.base.BaseBackActivity;
import com.cbkj.rrh.me.view.EditSelfAssessmentActivity;
import com.cbkj.rrh.net.http.HttpRequest;
import com.cbkj.rrh.net.http.HttpRequestAsyncTask.TaskListenerWithState;
import com.cbkj.rrh.net.http.HttpResponse;
import com.cbkj.rrh.net.http.HttpResponse.HttpTaskState;
import com.cbkj.rrh.net.request.TaskRequest;
import com.cbkj.rrh.view.BToast;
import com.cbkj.rrh.view.CertificateView;
import com.cbkj.rrh.view.CommentView;
import com.cbkj.rrh.view.RoundImageView;
import com.cbkj.rrh.view.widget.SelectItemView;
import com.cbkj.rrh.view.widget.TitleBar;

/**
 * @todo:接单人名片
 * @date:2015-7-27 上午9:40:36
 * @author:hg_liuzl@163.com
 */
public class ReceiverNameCardActivity extends BaseBackActivity implements TaskListenerWithState,OnClickListener {
	
	private LinearLayout llAllScreen;
	private ImageView ivSex;
	private RoundImageView ivHead;
	private TextView tvNick;
	private SelectItemView sivReciverTask,sivScoreSelf;
	private String mEmployeeId;	//接单人ID
	private CertificateView view;
	private NameCardBean bean;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_reciver_namecard_layout);
		mEmployeeId = getIntent().getStringExtra(NameCardBean.KEY_USER_ID);
		initTitle();
		initView();
		requestData();
	}
	
	private void initTitle() {
		TitleBar title = new TitleBar(mActivity);
		title.initAllBar("接单人名片");
		
		llAllScreen = (LinearLayout) findViewById(R.id.ll_screen);
		llAllScreen.setVisibility(View.GONE);
		
	}
	
	private void initView() {
		
		ivHead = (RoundImageView) findViewById(R.id.riv_user);
		tvNick = (TextView) findViewById(R.id.tv_nick);
		ivSex = (ImageView) findViewById(R.id.iv_sex);
		
		sivScoreSelf = (SelectItemView) findViewById(R.id.siv_socre_self);
		sivScoreSelf.tvContent.setMaxLines(2);
		sivScoreSelf.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(ReceiverNameCardActivity.this,EditSelfAssessmentActivity.class);
				intent.putExtra(EditSelfAssessmentActivity.SELF_ASSESSMENT_TYPE, EditSelfAssessmentActivity.LOOK_ASSESSMENT_KEY);
				intent.putExtra(EditSelfAssessmentActivity.SELF_ASSESSMENT_KEY, bean.intro);
				startActivity(intent);
			}
		});

		sivReciverTask = (SelectItemView) findViewById(R.id.siv_recive_task);
		sivReciverTask.setTagVisible(View.GONE);
		
		view = new CertificateView(mActivity, View.GONE,"他的证书:");
	}
	
	private void requestData() {
		TaskRequest.getInstance().requestReciverNameCard(this, this, mEmployeeId);
	}
	
	private void setData() {
		BGApp.getInstance().setImageSqure(bean.smallImg, ivHead);
		tvNick.setText(bean.nickName);
		
		ivSex.setBackgroundResource(bean.gender == 0?R.drawable.icon_female:R.drawable.icon_male);
		
		sivReciverTask.setTvContent(bean.successRTimes+"次");
		
		sivScoreSelf.setTvContent(bean.intro);
		
		view.setData(bean.certificates, bean.userId);		
		
		 new CommentView(mActivity, "雇主对他的评价:", bean.scores);
		 
		 llAllScreen.setVisibility(View.VISIBLE);
	}

	@Override
	public void onTaskOver(HttpRequest request, HttpResponse response) {
		if (response.getState() == HttpTaskState.STATE_OK) {
			if (null != response.result && response.result.getSuccess()) {
				final String strJson = response.result.getStrBody();
				bean = JSON.parseObject(strJson, NameCardBean.class);
				setData();
			} else {
				BToast.show(mActivity, R.string.system_error);
				finish();
			}
			
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.siv_score_self:
			Intent intent = new Intent(this,EditSelfAssessmentActivity.class);
			intent.putExtra(EditSelfAssessmentActivity.SELF_ASSESSMENT_TYPE, EditSelfAssessmentActivity.LOOK_ASSESSMENT_KEY);
			intent.putExtra(EditSelfAssessmentActivity.SELF_ASSESSMENT_KEY, bean.intro);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
	
	
	/**
	 * 
	 * @todo:查看接单人名片
	 * @date:2015-8-20 下午3:57:35
	 * @author:hg_liuzl@163.com
	 * @params:@param activity
	 * @params:@param userId
	 */
	public static void lookNameCard(Activity activity,String userId) {
		Intent intent = new Intent(activity,ReceiverNameCardActivity.class);
		intent.putExtra(NameCardBean.KEY_USER_ID, userId);
		activity.startActivity(intent);
	}
	
}
