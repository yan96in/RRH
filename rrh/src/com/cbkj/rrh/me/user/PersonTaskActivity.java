package com.cbkj.rrh.me.user;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.cbkj.rrh.R;
import com.cbkj.rrh.adapter.TaskAdapter;
import com.cbkj.rrh.adapter.TaskAdapter.TaskType;
import com.cbkj.rrh.bean.NameCardBean;
import com.cbkj.rrh.bean.TaskBean;
import com.cbkj.rrh.main.base.BaseShowDataActivity;
import com.cbkj.rrh.net.http.HttpRequest;
import com.cbkj.rrh.net.http.HttpRequestAsyncTask.TaskListenerWithState;
import com.cbkj.rrh.net.http.HttpResponse;
import com.cbkj.rrh.net.http.HttpResponse.HttpTaskState;
import com.cbkj.rrh.net.http.HttpURL;
import com.cbkj.rrh.net.request.TaskRequest;
import com.cbkj.rrh.others.utils.ToolUtils;
import com.cbkj.rrh.view.BToast;
import com.cbkj.rrh.view.widget.TitleBar;
import com.cbkj.rrh.view.xlistview.XListView;
import com.cbkj.rrh.view.xlistview.XListView.IXListViewListener;

/**
 * 
 * @todo:雇主的单
 * @date:2015-9-4 下午5:34:55
 * @author:hg_liuzl@163.com
 */
public class PersonTaskActivity extends BaseShowDataActivity implements IXListViewListener,TaskListenerWithState {

	private XListView mXListView;
	private TaskAdapter mTaskAdapter;
	private List<TaskBean> mList = new ArrayList<TaskBean>();
	private String mRefreshTime = "";
	//当前选中的Bean
	private TaskBean mCurrentBean;
	
	private ImageView ivNoData;
	
	private String userId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_listshow_layout);
		userId = getIntent().getStringExtra(NameCardBean.KEY_USER_ID);
		initView();
		doRequestData();
	}
	
	private void initView() {
		TitleBar title = new TitleBar(mActivity);
		title.initAllBar("TA发的单");
		//数据列表
		mXListView = (XListView) findViewById(R.id.xlv_sapce);
		mXListView.setPullLoadEnable(true);
		mXListView.setPullRefreshEnable(true);
		mXListView.setXListViewListener(this);
		mTaskAdapter = new TaskAdapter(mList, mActivity,TaskType.EMPLOYER_TASK,share,pUitl);
		mXListView.setAdapter(mTaskAdapter);
		ivNoData = (ImageView) findViewById(R.id.iv_no_data);
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
	 * @author:hg_liuzl@163.com
	 * @params:
	 */
	private void doRequestData() {
		TaskRequest.getInstance().requestMySentTasks(this, mActivity,userId,m_start_page+1, m_start_page+PAGE_SIZE_ADD,true);
	}

	@Override
	public void onTaskOver(HttpRequest request, HttpResponse response) {
		if (response.getState() == HttpTaskState.STATE_OK) {
			if (request.getRequestUrl()== HttpURL.URL_ISENT_TASK && null!=response.result ) {	//获取发单列表
				if (response.result.getSuccess()) {
					m_start_page += PAGE_SIZE_ADD;
					mRefreshTime = ToolUtils.getNowTime();
					mXListView.setRefreshTime(mRefreshTime);
					setData(response.result.getStrBody());
				}else{
					BToast.show(mActivity, response.result.getErrorMsg());
				}
			}else{	//系统出错
				BToast.show(mActivity,R.string.system_error);
			}
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
			pUitl.storeTaskData(strJson,isRefreshAction);	//数据保存到本地
			List<TaskBean> resultlist = JSON.parseArray(strJson,TaskBean.class);
			setDataAdapter(mXListView, mTaskAdapter, mList, resultlist, isRefreshAction);
			if (mList.size()>0) {
				mXListView.setVisibility(View.VISIBLE);
				ivNoData.setVisibility(View.GONE);
			} else {
				mXListView.setVisibility(View.GONE);
				ivNoData.setVisibility(View.VISIBLE);
			}
		}else{
			mXListView.setVisibility(View.GONE);
			ivNoData.setVisibility(View.VISIBLE);
		}
	}
	
}