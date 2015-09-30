package com.cbkj.rrh.ui.me.user;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;

import com.alibaba.fastjson.JSON;
import com.cbkj.rrh.R;
import com.cbkj.rrh.bean.ScoreRuleBean;
import com.cbkj.rrh.net.http.HttpRequest;
import com.cbkj.rrh.net.http.HttpRequestAsyncTask.TaskListenerWithState;
import com.cbkj.rrh.net.http.HttpResponse;
import com.cbkj.rrh.net.http.HttpResponse.HttpTaskState;
import com.cbkj.rrh.net.http.HttpURL;
import com.cbkj.rrh.net.request.TaskRequest;
import com.cbkj.rrh.ui.base.BaseBackActivity;
import com.cbkj.rrh.view.BToast;
import com.cbkj.rrh.widget.TitleBar;

/**
 * @todo:评分界面
 * @date:2015-5-19 上午9:34:18
 * @author:hg_liuzl@163.com
 */
public class ScoreEvaluateActivity extends BaseBackActivity implements OnClickListener,TaskListenerWithState {

	/***评分类型，给雇主，还是接单人评分*/
	public static final String KEY_SCORE_TYPE = "key_score_type";
	
	/**雇主为1，接单为2**/
	public static final int KEY_SCORE_EMPLOYER = 1;
	public static final int KEY_SCORE_EMPLOYEE = 2;
	
	/**任务编号**/
	public static final String KEY_TASK_ID = "key_task_id";
	/**被评论者的ID,**/
	public static final String KEY_COMMENT_ID = "key_comment_id";
	
	private RadioButton[] rbs = null;
	
	private int scoreType = -1;
	private String taskID = "";
	private String commentID = "";
	private TitleBar mTitleBar = null;
	private int score = -1;	//评分，默认是非常满意
	
	//评分规则
	private List<ScoreRuleBean> scoreRules = new ArrayList<ScoreRuleBean>();

	/**
	 * 
	 * @todo:TODO
	 * @date:2015-8-21 上午10:35:40
	 * @author:hg_liuzl@163.com
	 * @params:@param mActivity
	 * @params:@param scoreType 评分类型，给雇主，还是接单人评分
	 * @params:@param taskId 任务编号
	 * @params:@param commentID 被评论者的ID
	 */
	public static void doScoreAction(Activity mActivity,int scoreType,String taskId,String commentID) {
		Intent intent = new Intent(mActivity,ScoreEvaluateActivity.class);
		intent.putExtra(KEY_SCORE_TYPE, scoreType);
		intent.putExtra(KEY_TASK_ID, taskId);
		intent.putExtra(KEY_COMMENT_ID, commentID);
		mActivity.startActivity(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		scoreType = getIntent().getIntExtra(KEY_SCORE_TYPE, -1);
		taskID = getIntent().getStringExtra(KEY_TASK_ID);
		commentID = getIntent().getStringExtra(KEY_COMMENT_ID);
		setContentView(R.layout.a_evaluate_layout);
		
		mTitleBar = new TitleBar(mActivity);
		String title = (scoreType == KEY_SCORE_EMPLOYER ? "给雇主评分":"给接单人评分");
//		mTitleBar.initAllBar(title, "举报");
//		mTitleBar.rightBtn.setOnClickListener(this);
		mTitleBar.initTitleBar(title);
		
		TaskRequest.getInstance().requestScoreRules(this, this, scoreType == KEY_SCORE_EMPLOYER?"employer":"employee");
		
		
	}
	
	private void initView() {
		


		score = scoreRules.get(0).rulesId;
		
		rbs = new RadioButton[4];
		for (int i = 0; i < scoreRules.size(); i++) {
			final ScoreRuleBean bean = scoreRules.get(i);
			switch (i) {
			case 0:
				rbs[0] = (RadioButton) findViewById(R.id.rb_1);
				rbs[0].setOnClickListener(this);
				break;
			case 1:
				rbs[1] = (RadioButton) findViewById(R.id.rb_2);
				rbs[1].setOnClickListener(this);
				break;
			case 2:
				rbs[2] = (RadioButton) findViewById(R.id.rb_3);
				rbs[2].setOnClickListener(this);
				break;
			case 3:
				rbs[3] = (RadioButton) findViewById(R.id.rb_4);
				rbs[3].setOnClickListener(this);
				break;
				
			default:
				break;
			}
			rbs[i].setText("  "+bean.name);
		}
		
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
	}
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_submit:
			doSubmit();
			break;
		case R.id.rb_1:
			score = scoreRules.get(0).rulesId;
			selectReportType(1);
			break;
		case R.id.rb_2:
			score = scoreRules.get(1).rulesId;
			selectReportType(2);
			break;
		case R.id.rb_3:
			score = scoreRules.get(2).rulesId;
			selectReportType(3);
			break;
		case R.id.rb_4:
			score = scoreRules.get(3).rulesId;
			selectReportType(4);
			break;
		default:
			break;
		}
	}
	
	private void doSubmit() {
		if (scoreType == KEY_SCORE_EMPLOYER) {	//对雇主评价
			TaskRequest.getInstance().requestSenterGrade(this, mActivity, taskID, mUserId, commentID, score);
		} else {	//对接单人评价
			TaskRequest.getInstance().requestReciverGrade(this, mActivity, taskID, mUserId, commentID, score);
		}
	}
	
	@Override
	public void onTaskOver(HttpRequest request, HttpResponse response) {
		if (response.getState() == HttpTaskState.STATE_OK && null != response.result) {
			
			if (HttpURL.URL_SCORE_RULES == request.getRequestUrl()) {	//获取评分规则
				
				if (response.result.getSuccess()) {
					List<ScoreRuleBean> result = JSON.parseArray(response.result.getStrBody(), ScoreRuleBean.class);
					scoreRules.addAll(result);
					initView();
				} else {
					BToast.show(mActivity, response.result.getErrorMsg());
				}
				
			} else if(HttpURL.URL_SENTER_GRADE == request.getRequestUrl()) {	//对雇主评价
				if (response.result.getSuccess()) {
					BToast.show(mActivity, "评分成功!");
					finish();
				} else {
					BToast.show(mActivity, response.result.getErrorMsg());
				}
			}else if(HttpURL.URL_RECIVER_GRADE == request.getRequestUrl()) {	//对雇主评价
				if (response.result.getSuccess()) {
					BToast.show(mActivity, "评分成功!");
					finish();
				} else {
					BToast.show(mActivity, response.result.getErrorMsg());
				}
			}
		}
	}
}
