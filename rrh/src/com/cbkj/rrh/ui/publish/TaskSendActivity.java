package com.cbkj.rrh.ui.publish;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;

import com.cbkj.rrh.R;
import com.cbkj.rrh.bean.TaskBean;
import com.cbkj.rrh.net.http.HttpRequest;
import com.cbkj.rrh.net.http.HttpRequestAsyncTask.TaskListenerWithState;
import com.cbkj.rrh.net.http.HttpResponse;
import com.cbkj.rrh.net.http.HttpResponse.HttpTaskState;
import com.cbkj.rrh.net.request.TaskRequest;
import com.cbkj.rrh.ui.MainActivity;
import com.cbkj.rrh.ui.base.BaseActivity;
import com.cbkj.rrh.view.BToast;
import com.cbkj.rrh.view.MyPopWindow;
import com.cbkj.rrh.widget.TitleBar;

/**
 * 
 * @todo:发布任务
 * @date:2015-7-20 下午3:44:54
 * @author:hg_liuzl@163.com
 */
public class TaskSendActivity extends BaseActivity implements TaskListenerWithState,OnClickListener
{
	
	private EditText etDescribe,etMoney,etContact,etPhone,etAddress,etEndDate,etFinishDate;
	private TaskBean bean = null;
	private MyPopWindow popWindow;
	private ScrollView scrollView;
	private Calendar calendar = Calendar.getInstance();
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_sendtask_layout);
		initView();
	}
	
	private void initView() {
		
		TitleBar mTitleBar = new TitleBar(mActivity);
		mTitleBar.initAllBar("发私活","发布");
		mTitleBar.backBtn.setVisibility(View.GONE);
		mTitleBar.rightBtn.setOnClickListener(this);
		
		scrollView = (ScrollView) findViewById(R.id.scroll_ll);
		
		popWindow = new MyPopWindow(mActivity,pUitl);
		popWindow.initWorkPosition(false);
		
		etDescribe = (EditText) findViewById(R.id.et_describe);
		etMoney= (EditText) findViewById(R.id.et_money);
		etContact = (EditText) findViewById(R.id.et_contact);
		etPhone = (EditText) findViewById(R.id.et_tel);
		etPhone.setText(pUitl.getUserBean().telephone);
		etAddress = (EditText) findViewById(R.id.et_address);
		
		etEndDate = (EditText) findViewById(R.id.et_enddate);
		etEndDate.setOnClickListener(this);
		etEndDate.setTag(etEndDate);
	//	setTextDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), etEndDate);
		
		etFinishDate = (EditText) findViewById(R.id.et_finishtime);
		etFinishDate.setOnClickListener(this);
		etFinishDate.setTag(etFinishDate);
	//	setTextDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), etFinishDate);
		
		findViewById(R.id.btn_send).setOnClickListener(this);
	}
	
	private  void doSubmit() {
		final String content = etDescribe.getText().toString().trim();
		
		final String money = etMoney.getText().toString().trim();
		final String endDate = etEndDate.getText().toString().trim();
		final String finishData = etFinishDate.getText().toString().trim();
		
		final String phone = etPhone.getText().toString().trim();
		final String contact = etContact.getText().toString().trim();
		final String address = etAddress.getText().toString().trim();
		
		if (TextUtils.isEmpty(content)) {
			BToast.show(mActivity, "请添写任务描述");
			return;
		}else if (TextUtils.isEmpty(money)) {
			BToast.show(mActivity, "请添写预算费用");
			return;
		}else if (TextUtils.isEmpty(endDate)) {
			BToast.show(mActivity, "请添写任务停止招标时间");
			return;
		}else if (TextUtils.isEmpty(finishData)) {
			BToast.show(mActivity, "请添写任务预计完成时间");
			return;
		}else if (TextUtils.isEmpty(contact)) {
			BToast.show(mActivity, "请添写联系人");
			return;
		}else if (TextUtils.isEmpty(phone) || phone.length()!=11) {
			BToast.show(mActivity, "请添写正确的手机号");
			return;
		}else {
			bean = new TaskBean();
			bean.userId = pUitl.getUserID();
			bean.position = popWindow.mWorkPosition;
			bean.content = content;
			bean.contact = contact;
			bean.charges = Integer.valueOf(money);
			bean.telephone = phone;
			bean.address = address;
			bean.deadline = (new StringBuffer(endDate)).append(" 23:59:59").toString();
			bean.finishTime = (new StringBuffer(finishData)).append(" 23:59:59").toString();
			TaskRequest.getInstance().requestTaskADD(this, mActivity, bean);
		}
	}
	
	/**
	 * 
	 * @todo:设置时间
	 * @date:2015-9-11 上午11:52:35
	 * @author:hg_liuzl@163.com
	 * @params:@param y
	 * @params:@param m
	 * @params:@param d
	 * @params:@param etTime
	 */
	private void setTextDate(int y,int m,int d,EditText etTime) {
		StringBuffer sb = new StringBuffer();
		sb.append(y).append("-").append((m+1)<10?"0"+(m+1):(m+1)).append("-").append(d<10?"0"+d:d);
		etTime.setText(sb.toString());
	}
	
	/**
	 * 
	 * @todo:清空数据
	 * @date:2015-8-19 下午2:47:59
	 * @author:hg_liuzl@163.com
	 * @params:
	 */
	private void clearData() {
		etDescribe.setText("");
		etMoney.setText("");
		etEndDate.setText("");
		etFinishDate.setText("");
//		setTextDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), etEndDate);
//		setTextDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), etFinishDate);
		etContact.setText("");
		etAddress.setText("");
		scrollView.fullScroll(ScrollView.FOCUS_UP);
	}

	@Override
	public void onTaskOver(HttpRequest request, HttpResponse response) {
		if (response.getState() == HttpTaskState.STATE_OK) {
			if (null != response.result && response.result.getSuccess()) {
				BToast.show(mActivity, "任务发布成功");
				clearData();
				// 发送广播
				Intent intent = new Intent(MainActivity.TASK_CHECK_TAB);
				intent.putExtra(MainActivity.CHECK_TAB_ID, MainActivity.SHOW_OF_FIRST_TAG);
				sendBroadcast(intent);
			}else{
				BToast.show(mActivity, response.result.getErrorMsg());
			}
		}
	}

	@SuppressLint("SimpleDateFormat")
	private void createTime(final EditText etTime){
		
		final int year = calendar.get(Calendar.YEAR);
		final int month = calendar.get(Calendar.MONTH);
		final int day = calendar.get(Calendar.DATE);
		
		DatePickerDialog dialog = new DatePickerDialog(this,AlertDialog.THEME_HOLO_LIGHT, new OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker arg0, int y, int m, int d) {
				setTextDate(y, m, d, etTime);
			}
		}, year, month, day);
		
		DatePicker datePicker = dialog.getDatePicker();
		datePicker.setMinDate(calendar.getTime().getTime());
		dialog.show();
	}

	@Override
	public void onClick(View v) {
		EditText text = null;
		switch (v.getId()) {
		case R.id.btn_right:	//提交
		case R.id.btn_send: // 提交
			doSubmit();
			break;
		case R.id.et_enddate: // 截止时间
			text = (EditText) v.getTag();
			createTime(text);
			break;
		case R.id.et_finishtime:// 完成时间
			text = (EditText) v.getTag();
			createTime(text);
			break;
		default:
			break;
		}
	}
}
