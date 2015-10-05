//package com.cbkj.rrh.discover.view;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.alibaba.fastjson.JSON;
//import com.cbkj.rrh.R;
//import com.cbkj.rrh.adapter.OrderDetailAdapter;
//import com.cbkj.rrh.adapter.TaskAdapter.TaskType;
//import com.cbkj.rrh.bean.TaskBean;
//import com.cbkj.rrh.bean.TaskReciverBean;
//import com.cbkj.rrh.main.BGApp;
//import com.cbkj.rrh.main.base.BaseBackShowDataActivity;
//import com.cbkj.rrh.main.help.ShowNameCardListener;
//import com.cbkj.rrh.me.user.ReportActivity;
//import com.cbkj.rrh.net.http.HttpRequest;
//import com.cbkj.rrh.net.http.HttpRequestAsyncTask.TaskListenerWithState;
//import com.cbkj.rrh.net.http.HttpResponse;
//import com.cbkj.rrh.net.http.HttpResponse.HttpTaskState;
//import com.cbkj.rrh.net.http.HttpURL;
//import com.cbkj.rrh.net.request.TaskRequest;
//import com.cbkj.rrh.ui.TaskUtil;
//import com.cbkj.rrh.view.BToast;
//import com.cbkj.rrh.view.RoundImageView;
//import com.cbkj.rrh.view.widget.TitleBar;
//import com.cbkj.rrh.view.xlistview.XListView;
//
///**
// * @todo:订单详情界面
// * @date:2015-7-31 下午2:29:26
// * @author:hg_liuzl@163.com
// */
//public class OrderDetailActivity extends BaseBackShowDataActivity implements TaskListenerWithState,OnClickListener {
//	
//	private TitleBar mTitleBar;
//	private XListView mXListView;
//	private OrderDetailAdapter mTaskDetailAdapter;
//	private List<TaskBean> mList = new ArrayList<TaskBean>();
//	//当前选中的Bean
//	private TaskBean mCurrentBean;
//	
//	//当前选中的taskId
//	private String mTaskId;
//	
//	private int mTaskType = TaskBean.TASK_SENT_TAG;
//	
//	private ImageView ivNodata;
//	
//	/**
//	 * 
//	 * @todo:进入任务详情
//	 * @date:2015-7-31 下午4:38:05
//	 * @author:hg_liuzl@163.com
//	 * @params:@param activity
//	 * @params:@param taskType
//	 * @params:@param taskId
//	 */
//	public static void goToDetail(Activity activity,int taskType,String taskId){
//		Intent intent = new Intent(activity,OrderDetailActivity.class);
//		intent.putExtra(TaskBean.KEY_MESSAGE_TYPE, taskType);
//		intent.putExtra(TaskBean.KEY_TASK_ID, taskId);
//		activity.startActivity(intent);
//	}
//	
//	/**
//	 * 
//	 * @todo:进入任务详情
//	 * @date:2015-7-31 下午4:38:05
//	 * @author:hg_liuzl@163.com
//	 * @params:@param activity
//	 * @params:@param taskType
//	 * @params:@param taskId
//	 */
//	public static void goToDetail(Activity activity,int taskType,TaskBean bean){
//		Intent intent = new Intent(activity,OrderDetailActivity.class);
//		intent.putExtra(TaskBean.KEY_MESSAGE_TYPE, taskType);
//		intent.putExtra(TaskBean.KEY_TASK_BEAN, bean);
//		activity.startActivity(intent);
//	}
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.a_listshow_layout);
//		mTaskType = getIntent().getIntExtra(TaskBean.KEY_MESSAGE_TYPE, TaskBean.TASK_SENT_TAG);
//		
//		mCurrentBean = (TaskBean) getIntent().getSerializableExtra(TaskBean.KEY_TASK_BEAN);
//		mTaskId = getIntent().getStringExtra(TaskBean.KEY_TASK_ID);
//
//		if (null != mCurrentBean) {
//			initView();
//		}else if(!TextUtils.isEmpty(mTaskId)) {
//			TaskRequest.getInstance().requestTaskDetail(this, mActivity, mTaskId, pUitl.getUserID());
//		}else{
//			finish();
//		}
//	}
//	
//	/**
//	 * @todo:任务类型，我发的单，还是我接的单
//	 * @date:2015-8-20 下午2:42:32
//	 * @author:hg_liuzl@163.com
//	 * @params:
//	 */
//	private void switchTaskType() {
//		if (mTaskType == TaskBean.TASK_SENT_TAG) {	//我发的单
//			mTitleBar = new TitleBar(mActivity);
//			mTitleBar.initAllBar("详情", "");
//			mTitleBar.rightBtn.setBackgroundResource(R.drawable.icon_title_more);
//			mTitleBar.rightBtn.setOnClickListener(this);
//			switchSentTaskStatus();
//			if (mCurrentBean.status != TaskBean.SENT_TASK_FAIL) {
//				doRequestReciverData();
//			}
//		}else{//我接的单
//			mTitleBar = new TitleBar(mActivity);
//			
//			if (mCurrentBean.status == TaskBean.RECEIVE_TASK_HOLD) {
//				mTitleBar.initAllBar("详情", "");
//				mTitleBar.rightBtn.setBackgroundResource(R.drawable.icon_title_more);
//			}else{
//				mTitleBar.initAllBar("详情", "举报");
//			}
//			mTitleBar.rightBtn.setOnClickListener(this);
//			doRequestReciverData();
//		}
//	}
//	
//	/** 发单人的状态 **/
//	private void switchSentTaskStatus() {
//		int type = mCurrentBean.status;
//		switch (type) {
//		case TaskBean.SENT_TASK_HOLD:	//待定中
//			mTitleBar.rightBtn.setVisibility(View.VISIBLE);
//			
//			break;
//		case TaskBean.SENT_TASK_FAIL://失败
//			mTitleBar.rightBtn.setVisibility(View.VISIBLE);
//			
//			break;
//		case TaskBean.SENT_TASK_WORKING:	//进行中
//			mTitleBar.rightBtn.setVisibility(View.GONE);
//
//			break;
//		case TaskBean.SENT_TASK_FINISH:	//完成
//			mTitleBar.rightBtn.setVisibility(View.GONE);
//
//			break;
//
//		default:
//			break;
//		}
//	}
//	
//	private void initView() {
//		//数据列表
//		mXListView = (XListView) findViewById(R.id.xlv_sapce);
//		mXListView.setPullLoadEnable(false);
//		mXListView.setPullRefreshEnable(false);
//		mTaskDetailAdapter = new OrderDetailAdapter(mList,mCurrentBean,mActivity,mTaskType,pUitl);
//		mXListView.setAdapter(mTaskDetailAdapter);
//		mXListView.addHeaderView(createHeaderView());
//		switchTaskType();
//
//	}
//	
//	private View createHeaderView() {
//		View v = View.inflate(mActivity, R.layout.item_task_detail_header, null);
//		
//		ImageView ivStatus = (ImageView) v.findViewById(R.id.iv_task_status);
//		mCurrentBean.setImageStatus(ivStatus, mTaskType == TaskBean.TASK_SENT_TAG?TaskType.SENT_TASK:TaskType.RECIVE_TASK);
//		
//		TextView tv = (TextView) v.findViewById(R.id.tv_name);
//		tv.setText(mCurrentBean.nickName);
//		tv.setOnClickListener(new ShowNameCardListener(mCurrentBean, mActivity));
//		
//		RoundImageView rvImage = (RoundImageView) v.findViewById(R.id.riv_user);
//		BGApp.getInstance().setImageSqure(mCurrentBean.smallImg, rvImage);
//		rvImage.setOnClickListener(new ShowNameCardListener(mCurrentBean, mActivity));
//		
//		tv = (TextView) v.findViewById(R.id.tv_money);
//		mCurrentBean.setMoney(mActivity, tv);
//		
//		tv = (TextView) v.findViewById(R.id.tv_date);
//		mCurrentBean.setPublishTime(mActivity, tv);
//		
//		tv = (TextView) v.findViewById(R.id.tv_task_id);
//		tv.setText( mCurrentBean.taskId);
//		
//		tv = (TextView) v.findViewById(R.id.tv_describe);
//		tv.setText(mCurrentBean.content);
//		
//		tv = (TextView) v.findViewById(R.id.tv_contact);
//		tv.setText(mCurrentBean.contact);
//		
//		tv = (TextView) v.findViewById(R.id.tv_phone);
//		tv.setText( mCurrentBean.telephone);
//
//		tv = (TextView) v.findViewById(R.id.tv_address);
//		tv.setText(mCurrentBean.getAddress());
//		
//		tv = (TextView) v.findViewById(R.id.tv_deadline);
//		mCurrentBean.setEndTime(mActivity, tv);
//		//tv.setText(mActivity.getResources().getString(R.string.task_deadline, mCurrentBean.deadline));
//		
//		if (mTaskType == TaskBean.TASK_SENT_TAG) {
//			if (mCurrentBean.status == TaskBean.SENT_TASK_WORKING) {
//				tv.setText("已投标");
//			}
//		} else {
//			if (mCurrentBean.status == TaskBean.RECEIVE_TASK_SUCCESS) {
//				tv.setText("已中标");
//			}
//		}
//		
//		tv = (TextView) v.findViewById(R.id.tv_finishTime);
//		//tv.setText(mActivity.getResources().getString(R.string.task_plan_time, mCurrentBean.getFinishTime()));
//		
//		mCurrentBean.setFinishTime(mActivity, tv);
//		
//		ivNodata = (ImageView) v.findViewById(R.id.iv_no_data);
//		ivNodata.setVisibility(View.GONE);
//		
//		return v;
//	}
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.btn_right:	//右边的按钮
//			doRightAction();
//			break;
//		default:
//			break;
//		}
//	}
//	
//	private void doRightAction() {
//
//		if (mTaskType == TaskBean.TASK_SENT_TAG) {	//我发的单
//			switch (mCurrentBean.status) { //1：待定中；2：成功；3：失败；4：完成
//			case TaskBean.SENT_TASK_HOLD:	//待定中
//				TaskUtil.getInstance().showEmployerMenuMore(mActivity,mCurrentBean);
//				break;
//			case TaskBean.SENT_TASK_FAIL://失败
//				TaskUtil.getInstance().doReditTask(mActivity,mCurrentBean);
//				break;
//			default:
//				break;
//			}
//		} else {	//我接的单
//			if (mCurrentBean.status == TaskBean.RECEIVE_TASK_HOLD) {	//待定中的
//				TaskUtil.getInstance().showEmployeeMenuMore(mActivity,mCurrentBean,pUitl.getUserID());
//			} else {	//举报
//				ReportActivity.doReportAction(mActivity, ReportActivity.KEY_EMPLOYER, mCurrentBean.taskId, mCurrentBean.userId);
//			}
//		}
//	}
//	
//	
//	/**
//	 * 
//	 * @todo:查看接单人
//	 * @date:2015-5-11 上午10:12:57
//	 * @author:hg_liuzl@163.com
//	 * @params:
//	 */
//	private void doRequestReciverData() {
//		String taskType = "";
//		String employeeId = "";
//		if (mTaskType == TaskBean.TASK_RECIVED_TAG) {		//我接的单
//			taskType = "employee";
//			employeeId = pUitl.getUserID();
//		} else {	//我发的单
//			taskType = "employer";
//			employeeId = "";
//		}
//		TaskRequest.getInstance().requestTaskRecivers(this, mActivity, mCurrentBean.taskId, taskType,employeeId);
//	}
//
//	@Override
//	public void onTaskOver(HttpRequest request, HttpResponse response) {
//		if (response.getState() == HttpTaskState.STATE_OK) {
//			if (request.getRequestUrl() == HttpURL.URL_TASK_DETAIL && null!=response.result ) {	//订单详情
//				if (response.result.getSuccess()) {
//					mCurrentBean = JSON.parseObject(response.result.getStrBody(), TaskBean.class);
//					initView();
//				} else {
//					BToast.show(mActivity, response.result.getErrorMsg());
//				}
//			} else if (request.getRequestUrl()== HttpURL.URL_TASK_RECIVES && null!=response.result ) {	//获取接单人列表
//				if (response.result.getSuccess()) {
//					List<TaskReciverBean> reciverBeans = JSON.parseArray(response.result.getStrBody(), TaskReciverBean.class);
//					setDataAdapter(mXListView, mTaskDetailAdapter, mList, reciverBeans, false);
//					ivNodata.setVisibility(mList.size()==0?View.VISIBLE:View.GONE);
//				}else{
//					BToast.show(mActivity, response.result.getErrorMsg());
//				}
//			}
//			}else{	//系统出错
//				BToast.show(mActivity,R.string.system_error);
//			}
//		
//	}
//}
