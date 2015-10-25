package com.cbkj.rrh.me.view;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.cbkj.rrh.R;
import com.cbkj.rrh.bean.TaskBean;
import com.cbkj.rrh.main.CBApp;
import com.cbkj.rrh.main.MainActivity;
import com.cbkj.rrh.main.base.BaseBackActivity;
import com.cbkj.rrh.main.help.ShowNameCardListener;
import com.cbkj.rrh.me.user.ReportActivity;
import com.cbkj.rrh.net.http.HttpRequest;
import com.cbkj.rrh.net.http.HttpRequestAsyncTask.TaskListenerWithState;
import com.cbkj.rrh.net.http.HttpResponse;
import com.cbkj.rrh.net.http.HttpResponse.HttpTaskState;
import com.cbkj.rrh.net.http.HttpURL;
import com.cbkj.rrh.net.request.TaskRequest;
import com.cbkj.rrh.others.utils.DeviceUtils;
import com.cbkj.rrh.others.utils.ToolUtils;
import com.cbkj.rrh.view.BToast;
import com.cbkj.rrh.view.RoundImageView;
import com.cbkj.rrh.view.widget.TitleBar;

/**
 * @todo:我要接单
 * @date:2015-7-23 上午11:12:46
 * @author:hg_liuzl@163.com
 */
public class TaskRecivedActivity extends BaseBackActivity implements OnClickListener,TaskListenerWithState {
	
	private LinearLayout llAllScreen;
	private String taskId;
	private TaskBean mCurrentBean;
	private EditText etOfferMoney;
	private Button btnRecived;
	private Button btnContact;
	private TextView tvDeadline;
	private LinearLayout llNoOrder;
	private LinearLayout llHasOrder;
	private TextView tvMyOffer;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_recived_task_layout);
		taskId = (String) getIntent().getStringExtra(TaskBean.KEY_TASK_ID);
		taskId = getIntent().getStringExtra(TaskBean.KEY_TASK_ID);
		
		initTitle();
		
		if(!TextUtils.isEmpty(taskId)) {
			TaskRequest.getInstance().requestTaskDetail(this, mActivity, taskId, pUitl.getUserID());
		}
	}
	
	private void initTitle() {
		llAllScreen = (LinearLayout) findViewById(R.id.all_screen);
		llAllScreen.setVisibility(View.GONE);
		
		TitleBar mTitleBar = new TitleBar(mActivity);
		mTitleBar.initAllBar("详情", "举报");
		mTitleBar.rightBtn.setOnClickListener(this);

	}
	
	private void initView() {
		llAllScreen.setVisibility(View.VISIBLE);

		TextView tv = (TextView) findViewById(R.id.tv_name);
		tv.setText(mCurrentBean.nickName);
		tv.setOnClickListener(new ShowNameCardListener(mCurrentBean, mActivity));
		
		RoundImageView rvImage = (RoundImageView) findViewById(R.id.riv_user);
		CBApp.getInstance().setImageSqure(mCurrentBean.smallImg, rvImage);
		rvImage.setOnClickListener(new ShowNameCardListener(mCurrentBean, mActivity));
		
		tv = (TextView) findViewById(R.id.tv_money);
		mCurrentBean.setMoney(mActivity, tv);
		
		tv = (TextView) findViewById(R.id.tv_date);
		mCurrentBean.setPublishTime(mActivity, tv);
		
		tv = (TextView) findViewById(R.id.tv_task_id);
		tv.setText( mCurrentBean.taskId);
		
		tv = (TextView) findViewById(R.id.tv_describe);
		tv.setText( mCurrentBean.content);
		
		tv = (TextView) findViewById(R.id.tv_contact);
		tv.setText(mCurrentBean.contact);

		btnContact = (Button) findViewById(R.id.btn_contact);
		btnContact.setOnClickListener(this);
		
		tv = (TextView) findViewById(R.id.tv_phone);
		int status  = pUitl.getUserBean().employeeStatus;
		if (status == 3) {
			tv.setText(mCurrentBean.telephone);
			btnContact.setVisibility(View.VISIBLE);
		} else {
			tv.setText(ToolUtils.formatHiddenTelephone(mCurrentBean.telephone));
			btnContact.setVisibility(View.GONE);
		}

		tv = (TextView) findViewById(R.id.tv_address);
		tv.setText(mCurrentBean.getAddress());
		
		tv = (TextView) findViewById(R.id.tv_finishTime);
		mCurrentBean.setFinishTime(mActivity, tv);
		
		
		
		btnRecived = (Button) findViewById(R.id.btn_affirm_recived);
		btnRecived.setOnClickListener(this);
		
		llNoOrder = (LinearLayout) findViewById(R.id.ll_no_order);
		
		llHasOrder = (LinearLayout) findViewById(R.id.ll_has_order);
		
		etOfferMoney = (EditText) findViewById(R.id.et_offer_money);

		tvDeadline = (TextView) findViewById(R.id.tv_deadline);
		
		tvMyOffer = (TextView) findViewById(R.id.tv_myoffer_money);
		
		initTaskStatus();
	}
	
	/**
	 * 
	 * @todo:订单详情的状态
	 * @date:2015-9-9 上午10:14:54
	 * @author:hg_liuzl@163.com
	 * @params:
	 */
	private void initTaskStatus() {
		switch (mCurrentBean.commonStatus) {	
		case TaskBean.DETAIL_TASK_OF_ME:
			llNoOrder.setVisibility(View.GONE);
			llHasOrder.setVisibility(View.GONE);
			tvDeadline.setText("我发的单");
			btnContact.setVisibility(View.GONE);
			btnRecived.setVisibility(View.GONE);
			break;
		case TaskBean.DETAIL_TASK_HOLD_NO_OFFER:
			llNoOrder.setVisibility(View.VISIBLE);
			llHasOrder.setVisibility(View.GONE);
			mCurrentBean.setEndTime(mActivity, tvDeadline);
			break;
			
		case TaskBean.DETAIL_TASK_HOLD_OFFER:
			llNoOrder.setVisibility(View.GONE);
			llHasOrder.setVisibility(View.VISIBLE);
			tvDeadline.setText("我已报价");
			tvMyOffer.setText(String.valueOf(mCurrentBean.myCharges));
			btnRecived.setVisibility(View.GONE);
			break;
		case TaskBean.DETAIL_TASK_DECIDE_NO_OFFER:
			llNoOrder.setVisibility(View.GONE);
			llHasOrder.setVisibility(View.GONE);
			tvDeadline.setText("订单被接");
			btnContact.setVisibility(View.GONE);
			btnRecived.setVisibility(View.GONE);
			
			break;
		case TaskBean.DETAIL_TASK_DECIDE_OFFER:
			llNoOrder.setVisibility(View.GONE);
			llHasOrder.setVisibility(View.VISIBLE);
			
			tvDeadline.setText("我已接单");
			tvMyOffer.setText(String.valueOf(mCurrentBean.myCharges));
			btnRecived.setVisibility(View.GONE);
			break;
		}
	}
	

	
	/**
	 * 
	 * @todo:查看名片
	 * @date:2015-1-22 上午11:19:56
	 * @author:hg_liuzl@163.com
	 * @params:@param activity
	 * @params:@param userid
	 */
	public static void goTaskDetail(Activity activity,String taskId){
		 Intent intent = new Intent(activity, TaskRecivedActivity.class);
         intent.putExtra(TaskBean.KEY_TASK_ID, taskId);
         activity.startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_right:	//举报
			ReportActivity.doReportAction(mActivity, ReportActivity.KEY_EMPLOYER, mCurrentBean.taskId, mCurrentBean.userId);
			break;
		case R.id.btn_affirm_recived:	//确认接单
			String money = etOfferMoney.getText().toString();
			if (TextUtils.isEmpty(money)) {
				BToast.show(mActivity, "请填写您的报价！");
				return;
			} else {
				int status  = pUitl.getUserBean().employeeStatus;
				switch (status) {
				case 1:
					BToast.show(mActivity, "请先申请接单人认证！");
					break;
				case 2:
					BToast.show(mActivity, "您的接单人认证资料还在审核中！");
						break;
				case 3:
					doRecivedTask(money);
					break;
	
				default:
					break;
				}
			}
			break;

		case R.id.btn_contact:	//联系人
			DeviceUtils.dailPhone(mActivity, mCurrentBean.telephone);
			break;
			
		default:
			break;
		}
	}
	
	Dialog dialog = null;
	private void doRecivedTask(final String money) {

		if (pUitl.hasReadEmployeeTerms()) {	//阅读了服务接单人服务条款
			doSubmitRequest(money);
		} else {
			
//			dialog = DialogFactory.serviceTermDialog(mActivity, "接单人条款", getResources().getString(R.string.employee_service_terms), "同意并接单", new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					pUitl.setReadEmployeeTerms(true);
//					doSubmitRequest(money);
//					dialog.dismiss();
//				}
//			});
		}

	}

	@Override
	public void onTaskOver(HttpRequest request, HttpResponse response) {
		if (response.getState() == HttpTaskState.STATE_OK) {
			if (request.getRequestUrl() == HttpURL.URL_TASK_DETAIL  && null!=response.result) {	//订单详情
				if (response.result.getSuccess()) {
					mCurrentBean = JSON.parseObject(response.result.getStrBody(), TaskBean.class);
					initView();
				} else {
					BToast.show(mActivity, response.result.getErrorMsg());
				}
			} else if(request.getRequestUrl() == HttpURL.URL_IWANT_RECIVE_TASK  && null!=response.result) {	//我要接单
				if (response.result.getSuccess()) {
//					BToast.show(mActivity, "报价成功");
//					Intent intent = new Intent(MainActivity.TASK_CHECK_TAB);
//					intent.putExtra(MainActivity.CHECK_TAB_ID, MainActivity.SHOW_OF_SECOND_TAG);
//					sendBroadcast(intent);
//					finish();
				} else {
					BToast.show(mActivity, response.result.getErrorMsg());
				}
			}
		} 
	}
	
	/**
	 * 
	 * @todo:提交接单
	 * @date:2015-9-19 下午3:05:50
	 * @author:hg_liuzl@163.com
	 * @params:@param money
	 */
	private void doSubmitRequest(String money){
		TaskRequest.getInstance().requestWantReciveTask(this, mActivity, mCurrentBean.taskId, pUitl.getUserID(), Float.valueOf(money));
	}
}
