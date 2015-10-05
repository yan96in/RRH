package com.cbkj.rrh.me.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;

import com.cbkj.rrh.R;
import com.cbkj.rrh.main.base.BaseBackActivity;
import com.cbkj.rrh.net.http.HttpRequest;
import com.cbkj.rrh.net.http.HttpRequestAsyncTask.TaskListenerWithState;
import com.cbkj.rrh.net.http.HttpResponse;
import com.cbkj.rrh.net.http.HttpResponse.HttpTaskState;
import com.cbkj.rrh.net.request.TaskRequest;
import com.cbkj.rrh.view.BToast;
import com.cbkj.rrh.view.widget.TitleBar;

/**
 * @todo:举报界面
 * @date:2015-5-19 上午9:34:18
 * @author:hg_liuzl@163.com
 */
public class ReportActivity extends BaseBackActivity implements OnClickListener,TaskListenerWithState {
	
	/***评分类型，给雇主，还是接单人评分*/
	public static final String KEY_REPORT_TYPE = "key_report_type";
	
	/**雇主为1，接单为2**/
	public static final int KEY_EMPLOYER = 1;
	public static final int KEY_EMPLOYEE = 2;
	
	/**任务编号**/
	public static final String KEY_TASK_ID = "key_task_id";
	/**被举报者的ID,**/
	public static final String KEY_REPORT_ID = "key_report_id";
	
	private RadioButton[] rbs = null;
	
	private EditText etContent;
	private int reportType = -1;
	private String taskID = "";
	private String reportID = "";
	private int mCategoryType = -1;	//分类
	
	/**
	 * 
	 * @todo:TODO
	 * @date:2015-7-30 下午4:29:47
	 * @author:hg_liuzl@163.com
	 * @params:@param mActivity
	 * @params:@param scoreType
	 * @params:@param taskId
	 * @params:@param otherId
	 */
	public static void doReportAction(Activity mActivity,int reportType,String taskId,String reportId) {
		Intent intent = new Intent(mActivity,ReportActivity.class);
		intent.putExtra(KEY_REPORT_TYPE, reportType);
		intent.putExtra(KEY_TASK_ID, taskId);
		intent.putExtra(KEY_REPORT_ID, reportId);
		mActivity.startActivity(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		reportType = getIntent().getIntExtra(KEY_REPORT_TYPE, -1);
		taskID = getIntent().getStringExtra(KEY_TASK_ID);
		reportID = getIntent().getStringExtra(KEY_REPORT_ID);
		setContentView(R.layout.a_report_layout);
		initView();
	}
	
	private void initView() {
		
		String title = (reportType == KEY_EMPLOYER?"举报雇主":"举报接单人");
		
		(new TitleBar(mActivity)).initAllBar(title);

		rbs = new RadioButton[7];
		rbs[0] = (RadioButton) findViewById(R.id.rb_1);
		rbs[0].setOnClickListener(this);
		
		rbs[1] = (RadioButton) findViewById(R.id.rb_2);
		rbs[1].setOnClickListener(this);
		
		rbs[2] = (RadioButton) findViewById(R.id.rb_3);
		rbs[2].setOnClickListener(this);
		
		rbs[3] = (RadioButton) findViewById(R.id.rb_4);
		rbs[3].setOnClickListener(this);
		
		rbs[4] = (RadioButton) findViewById(R.id.rb_5);
		rbs[4].setOnClickListener(this);
		
		rbs[5] = (RadioButton) findViewById(R.id.rb_6);
		rbs[5].setOnClickListener(this);
		
		rbs[6] = (RadioButton) findViewById(R.id.rb_7);
		rbs[6].setOnClickListener(this);
		
		etContent = (EditText) findViewById(R.id.et_content);
		
		findViewById(R.id.btn_submit).setOnClickListener(this);
	}

	/**
	 * 
	 * @todo:选择举报类型
	 * @date:2015-5-19 下午2:43:21
	 * @author:hg_liuzl@163.com
	 * @params:@param type
	 */
	private void selectReportType(int type) {
		
		for (int i = 0; i < rbs.length; i++) {
			RadioButton rb = rbs[i];
			if (type == i+1) {
				rb.setChecked(true);
			}else{
				rb.setChecked(false);
			}
		}
		mCategoryType = type;
	}
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_submit:
			doSubmit();
			break;
		case R.id.rb_1:
			selectReportType(1);
			break;
		case R.id.rb_2:
			selectReportType(2);
			break;
		case R.id.rb_3:
			selectReportType(3);
			break;
		case R.id.rb_4:
			selectReportType(4);
			break;
		case R.id.rb_5:
			selectReportType(5);
			break;
		case R.id.rb_6:
			selectReportType(6);
			break;
		case R.id.rb_7:
			selectReportType(7);
			break;
		default:
			break;
		}
	}
	
	private void doSubmit() {
		String mContent = etContent.getText().toString().trim();
		if (reportType == KEY_EMPLOYER) {
			TaskRequest.getInstance().requestReportSenter(this, mActivity, taskID, mUserId, reportID, mCategoryType, mContent);
		} else {
			TaskRequest.getInstance().requestReportReciver(this, mActivity, taskID, mUserId, reportID, mCategoryType, mContent);
		}
	}
	
	@Override
	public void onTaskOver(HttpRequest request, HttpResponse response) {
		if (response.getState() == HttpTaskState.STATE_OK) {
			if (null != response.result  && response.result.getSuccess()) {
				BToast.show(mActivity, "举报成功!");
				finish();
			} else {
				BToast.show(mActivity, response.result.getErrorMsg());
			}
		}
	}
}
