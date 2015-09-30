package com.cbkj.rrh.ui.home;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.cbkj.rrh.R;
import com.cbkj.rrh.adapter.TaskAdapter;
import com.cbkj.rrh.adapter.TaskAdapter.TaskType;
import com.cbkj.rrh.bean.TaskBean;
import com.cbkj.rrh.net.http.HttpRequest;
import com.cbkj.rrh.net.http.HttpRequestAsyncTask.TaskListenerWithState;
import com.cbkj.rrh.net.http.HttpResponse;
import com.cbkj.rrh.net.http.HttpResponse.HttpTaskState;
import com.cbkj.rrh.net.http.HttpURL;
import com.cbkj.rrh.net.request.TaskRequest;
import com.cbkj.rrh.ui.MainActivity;
import com.cbkj.rrh.ui.base.BaseShowDataActivity;
import com.cbkj.rrh.ui.order.OrderDetailActivity;
import com.cbkj.rrh.utils.ConfigUtil;
import com.cbkj.rrh.utils.ToolUtils;
import com.cbkj.rrh.view.BToast;
import com.cbkj.rrh.view.MyPopWindow;
import com.cbkj.rrh.view.MyPopWindow.IChangePositionCallback;
import com.cbkj.rrh.view.xlistview.XListView;
import com.cbkj.rrh.view.xlistview.XListView.IXListViewListener;

/**
 * 
 * @todo:任务展示列表
 * @date:2015-4-13 下午3:23:41
 * @author:hg_liuzl@163.com
 */
public class TaskShowActivity extends BaseShowDataActivity implements OnItemClickListener,IXListViewListener,TaskListenerWithState {
	
	private MyPopWindow popWindow;
	private int mPosition = 0;
	private ImageView ivNodata;
	private XListView mXListView;
	private TaskAdapter mTaskAdapter;
	private List<TaskBean> mList = new ArrayList<TaskBean>();
	private String mRefreshTime = "";
	//当前选中的Bean
	private TaskBean mCurrentBean;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_taskshow_layout);
		initView();
		loadLocalData();
		showData();
	}
	
	private void loadLocalData() {
		List<TaskBean> resultlist = pUitl.getTaskData();
		if (null != resultlist) {
			setDataAdapter(mXListView, mTaskAdapter, mList, resultlist, isRefreshAction);
			ivNodata.setVisibility(mList.size() > 0 ? View.GONE:View.VISIBLE);
		}
	}
	
	private void initView() {
		
		popWindow = new MyPopWindow(mActivity,pUitl);
		popWindow.initWorkPositionForTask();
		popWindow.setCallback(callback);
//		mPosition = pUitl.getUserBean().position;
//		popWindow.setPosition(mPosition);
		
		//数据列表
		mXListView = (XListView) findViewById(R.id.xlv_sapce);
		mXListView.setPullLoadEnable(true);
		mXListView.setPullRefreshEnable(true);
		mXListView.setXListViewListener(this);
		mXListView.setOnItemClickListener(this);
		mTaskAdapter = new TaskAdapter(mList, mActivity,TaskType.COMMON_TASK,share,pUitl);
		mXListView.setAdapter(mTaskAdapter);
		
		ivNodata = (ImageView) findViewById(R.id.iv_no_data);
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if (MainActivity.mNeedRefreshData) {
			MainActivity.mNeedRefreshData = false;
			reFreshData();
		}
	}
	
	/**
	 * 
	 * @todo:选择获取数据类型
	 * @date:2015-5-21 上午10:03:54
	 * @author:hg_liuzl@163.com
	 * @params:@param type
	 * @params:@param strTitle
	 */
	private void selectDataType(int type) {
		this.mPosition = type;
		reFreshData();
	}
	
	@Override
	public void onRefresh() {
		reFreshData();
	}

	@Override
	public void onLoadMore() {
		isRefreshAction = false;
		doRequestData();
	}
	
	
	private void reFreshData() {
		isRefreshAction = true;
		m_start_page = 0;
		doRequestData();
	}
	
	/**
	 * 
	 * @todo:请求数据
	 * @date:2015-5-11 上午10:12:57
	 * 
	 * @author:hg_liuzl@163.comdfs
	 * @params:
	 */
	private void doRequestData() {
		TaskRequest.getInstance().requestTaskList(this, mActivity,mPosition,m_start_page+1, m_start_page+PAGE_SIZE_ADD);
	}

	@Override
	public void onTaskOver(HttpRequest request, HttpResponse response) {
		if (response.getState() == HttpTaskState.STATE_OK) {
			if (request.getRequestUrl()== HttpURL.URL_TASK_LIST && null!=response.result ) {	//获取列表
				if (response.result.getSuccess()) {
					m_start_page += PAGE_SIZE_ADD;
					mRefreshTime = ToolUtils.getNowTime();
					mXListView.setRefreshTime(mRefreshTime);
					setData(response.result.getStrBody());
				}else{
					BToast.show(mActivity, response.result.getErrorMsg());
				}
			}
		} 
	}


	@Override
	public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
			mCurrentBean = (TaskBean) adapter.getAdapter().getItem(position);
			if (null!=mCurrentBean) {
				if (mCurrentBean.userId.equals(pUitl.getUserID())) {	//如果ID一样，就是自己发的单
					OrderDetailActivity.goToDetail(mActivity, TaskBean.TASK_SENT_TAG, mCurrentBean.taskId);
				} else {
					TaskRecivedActivity.goTaskDetail(mActivity, mCurrentBean.taskId);
				}
		}
	}
	
	/**
	 * 
	 * @todo:展示数据
	 * @date:2015-3-12 下午8:23:51
	 * @author:hg_liuzl@163.com
	 * @params:@param index
	 */
	private void showData() {
//		if (!ConfigUtil.isConnect(mActivity)) {	//获取本地数据
//			String strJson = "";
//			if (mList.size() == 0) {
//				strJson = pUitl.getTaskData();
//				setData(strJson);
//			}
//			
//		} else {//获取网络数据
//			if (mList.size() == 0) {
//				doRequestData();
//			}
//		}
		if (ConfigUtil.isConnect(mActivity)) { // 获取本地数据
			doRequestData();
		}
		
	}
	
	/**
	 * 
	 * @todo:设置数据
	 * @date:2015-7-23 下午2:47:01
	 * @author:hg_liuzl@163.com
	 * @params:@param strJson
	 */
	private void setData(String strJson) {
		if (!TextUtils.isEmpty(strJson)) {
			List<TaskBean> resultlist = JSON.parseArray(strJson,TaskBean.class);
			pUitl.storeTaskData(strJson,isRefreshAction);	//数据保存到本地
			setDataAdapter(mXListView, mTaskAdapter, mList, resultlist, isRefreshAction);
			ivNodata.setVisibility(mList.size() > 0 ? View.GONE:View.VISIBLE);
		}
	}
	
	private IChangePositionCallback callback = new IChangePositionCallback() {
		@Override
		public void change(int position) {
			selectDataType(position);
		}
	};
	
}