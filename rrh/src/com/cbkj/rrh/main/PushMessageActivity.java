//package com.cbkj.rrh.main;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.ImageView;
//
//import com.alibaba.fastjson.JSON;
//import com.cbkj.rrh.R;
//import com.cbkj.rrh.adapter.MessageAdapter;
//import com.cbkj.rrh.bean.MessageBean;
//import com.cbkj.rrh.bean.TaskBean;
//import com.cbkj.rrh.discover.view.OrderDetailActivity;
//import com.cbkj.rrh.home.view.TaskRecivedActivity;
//import com.cbkj.rrh.main.base.BaseBackShowDataActivity;
//import com.cbkj.rrh.net.http.HttpRequest;
//import com.cbkj.rrh.net.http.HttpRequestAsyncTask.TaskListenerWithState;
//import com.cbkj.rrh.net.http.HttpResponse;
//import com.cbkj.rrh.net.http.HttpResponse.HttpTaskState;
//import com.cbkj.rrh.net.http.HttpURL;
//import com.cbkj.rrh.net.request.TaskRequest;
//import com.cbkj.rrh.others.utils.ToolUtils;
//import com.cbkj.rrh.view.BToast;
//import com.cbkj.rrh.view.widget.TitleBar;
//import com.cbkj.rrh.view.xlistview.XListView;
//import com.cbkj.rrh.view.xlistview.XListView.IXListViewListener;
//
///**
// * @todo:消息提醒
// * @date:2015-8-25 上午10:08:19
// * @author:hg_liuzl@163.com
// */
//public class PushMessageActivity extends BaseBackShowDataActivity implements OnItemClickListener,TaskListenerWithState,IXListViewListener {
//	
//	private ImageView ivNoData;
//	private XListView xlv;
//	private MessageAdapter adapter;
//	private List<MessageBean> messages = new ArrayList<MessageBean>();
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.a_message_layout);
//		initView();
//		loadLocalData();
//		doRequestData();
//	}
//	
//	private void initView() {
//		TitleBar title = new TitleBar(mActivity);
//		title.initAllBar("消息", "清空");
//		title.rightBtn.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				doClearMsg();
//			}
//		});
//		
//		xlv = (XListView) findViewById(R.id.xlv_spaceless);
//		xlv.setPullLoadEnable(true);
//		xlv.setPullRefreshEnable(true);
//		xlv.setXListViewListener(this);
//		xlv.setBackgroundResource(R.drawable.bg_task_content);
//		xlv.setOnItemClickListener(this);
//		
//		adapter = new MessageAdapter(messages, mActivity);
//		xlv.setAdapter(adapter);
//		
//		ivNoData = (ImageView) findViewById(R.id.iv_no_data);
//		
//	}
//	
//	/**
//	 * 
//	 * @todo:TODO
//	 * @date:2015-9-23 下午3:10:40
//	 * @author:hg_liuzl@163.com
//	 * @params:
//	 */
//	private void loadLocalData() {
//		List<MessageBean> resultlist = pUitl.getPushLogData();
//		setDataAdapter(xlv, adapter, messages, resultlist, isRefreshAction);
//		showVisible();
//	}
//	
////	/**
////	 * 
////	 * @todo:获取数据
////	 * @date:2015-8-25 下午6:31:18
////	 * @author:hg_liuzl@163.com
////	 * @params:
////	 */
////	private void getData() {
////		new MyAsyncTask(dbHelper, pUitl.getUserID()).execute();
////	}
////	
////
////
////	public class MyAsyncTask extends AsyncTask<Void, Void, List<MessageBean>>
////	{
////
////		private DBHelper mDBHelper;
////		
////		private String mUserId;
////		
////		public MyAsyncTask(DBHelper db,String userId){
////			this.mDBHelper = db;
////			this.mUserId = userId;
////		}
////		
////		@Override
////		protected void onPreExecute() {
////			super.onPreExecute();
////			LoadingProgress.getInstance().show(mActivity);
////		}
////
////		@Override
////		protected List<MessageBean> doInBackground(Void... arg0) {
////			return MessageBean.queryMessages(mDBHelper, mUserId);
////		}
////		
////		@Override
////		protected void onPostExecute(List<MessageBean> result) {
////			super.onPostExecute(result);
////			LoadingProgress.getInstance().dismiss();
////			
////		}
////	}
//
//	/**
//	 * 
//	 * @todo:清空消息
//	 * @date:2015-8-26 下午3:22:12
//	 * @author:hg_liuzl@163.com
//	 * @params:
//	 */
//	private void doClearMsg() {
//		TaskRequest.getInstance().requestClearPushLog(this, mActivity, pUitl.getUserID());
//	}
//
//	@Override
//	public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
//		
//		final MessageBean bean = (MessageBean) adapter.getAdapter().getItem(position);
//		//MessageBean.updateMessageHasRead(dbHelper, bean.id);
//
//		switch (bean.type) {
//		case MessageBean.TYPE_MSG_SENT:
//			OrderDetailActivity.goToDetail(mActivity, TaskBean.TASK_SENT_TAG, bean.taskId);
//			finish();
//			break;
//
//		case MessageBean.TYPE_MSG_RECIVE:
//			OrderDetailActivity.goToDetail(mActivity, TaskBean.TASK_RECIVED_TAG, bean.taskId);
//			finish();
//			break;
//
//		case MessageBean.TYPE_MSG_POSITION:
//			TaskRecivedActivity.goTaskDetail(mActivity, bean.taskId);
//			finish();
//			break;
//		}
//	}
//	
//	/**
//	 * 
//	 * @todo:设置数据
//	 * @date:2015-7-23 下午2:47:01
//	 * @author:hg_liuzl@163.com
//	 * @params:@param strJson
//	 */
//	private void setData(String strJson) {
//		if (!TextUtils.isEmpty(strJson)) {
//			pUitl.storePushLogData(strJson,isRefreshAction); // 数据保存到本地
//			List<MessageBean> resultlist = JSON.parseArray(strJson, MessageBean.class);
//			setDataAdapter(xlv, adapter, messages, resultlist, isRefreshAction);
//			showVisible();
//		}
//	}
//	
//	private void showVisible() {
//		if (messages.size() > 0) {
//			xlv.setVisibility(View.VISIBLE);
//			ivNoData.setVisibility(View.GONE);
//		} else {
//			xlv.setVisibility(View.GONE);
//			ivNoData.setVisibility(View.VISIBLE);
//		}
//
//	}
//
//	@Override
//	public void onTaskOver(HttpRequest request, HttpResponse response) {
//		if (response.getState() == HttpTaskState.STATE_OK) {
//
//			if (request.getRequestUrl() == HttpURL.URL_PUSH_LOG  && response.result != null) { // 获取推送的log
//				if (response.result.getSuccess()) {
//					m_start_page += PAGE_SIZE_ADD;
//					mRefreshTime = ToolUtils.getNowTime();
//					xlv.setRefreshTime(mRefreshTime);
//					setData(response.result.getStrBody());
//				} else {
//					BToast.show(mActivity, response.result.getErrorMsg());
//				}
//			} else if (request.getRequestUrl() == HttpURL.URL_PUSH_LOG_CLEAR  && response.result != null) { // 清空推送的log
//				if (response.result.getSuccess()) {
//					messages.clear();
//					adapter.notifyDataSetChanged();
//					showVisible();
//				} else {
//					BToast.show(mActivity, response.result.getErrorMsg());
//				}
//			}
//		} 
//	}
//
//	@Override
//	public void onRefresh() {
//		reFreshData();
//	}
//
//	@Override
//	public void onLoadMore() {
//		isRefreshAction = false;
//		doRequestData();
//	}
//	
//	
//	private void reFreshData() {
//		isRefreshAction = true;
//		m_start_page = 0;
//		doRequestData();
//	}
//	
//	/**
//	 * 
//	 * @todo:请求数据
//	 * @date:2015-5-11 上午10:12:57
//	 * 
//	 * @author:hg_liuzl@163.com
//	 * @params:
//	 */
//	private void doRequestData() {
//		TaskRequest.getInstance().requestPushLog(this, mActivity, pUitl.getUserID(),m_start_page+1, m_start_page+PAGE_SIZE_ADD);
//	}
//	
//	
//}
//
