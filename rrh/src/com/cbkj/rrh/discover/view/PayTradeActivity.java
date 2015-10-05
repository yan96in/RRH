package com.cbkj.rrh.discover.view;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.cbkj.rrh.R;
import com.cbkj.rrh.adapter.TradeAdapter;
import com.cbkj.rrh.bean.TradeBean;
import com.cbkj.rrh.main.base.BaseBackShowDataActivity;
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
 * @todo:交易流水
 * @date:2015-8-25 上午10:08:19
 * @author:hg_liuzl@163.com
 */
public class PayTradeActivity extends BaseBackShowDataActivity implements TaskListenerWithState,IXListViewListener {
	
	private ImageView ivNoData;
	private XListView xlv;
	private TradeAdapter adapter;
	private List<TradeBean> Trades = new ArrayList<TradeBean>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_pay_trade_layout);
		initView();
		loadLoacalDate();
		doRequestData();
	}
	
	private void initView() {
		TitleBar title = new TitleBar(mActivity);
		title.initAllBar("交易记录");

		xlv = (XListView) findViewById(R.id.xlv_sapce);
		xlv.setPullLoadEnable(true);
		xlv.setPullRefreshEnable(true);
		xlv.setXListViewListener(this);
		xlv.setBackgroundResource(R.drawable.bg_task_content);
		
		adapter = new TradeAdapter(Trades, mActivity);
		xlv.setAdapter(adapter);
		
		ivNoData = (ImageView) findViewById(R.id.iv_no_data);
		
	}
	
	private void loadLoacalDate() {
		List<TradeBean> resultlist = pUitl.getPayTradeMsg();
		if (null != resultlist) {
			setDataAdapter(xlv, adapter, Trades, resultlist, isRefreshAction);
			showVisible();
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
			pUitl.storePayTradeMsg(strJson,isRefreshAction); // 数据保存到本地
			List<TradeBean> resultlist = JSON.parseArray(strJson, TradeBean.class);
			setDataAdapter(xlv, adapter, Trades, resultlist, isRefreshAction);
			showVisible();
		}
	}
	
	private void showVisible() {
		if (Trades.size() > 0) {
			xlv.setVisibility(View.VISIBLE);
			ivNoData.setVisibility(View.GONE);
		} else {
			xlv.setVisibility(View.GONE);
			ivNoData.setVisibility(View.VISIBLE);
		}

	}

	@Override
	public void onTaskOver(HttpRequest request, HttpResponse response) {
		if (response.getState() == HttpTaskState.STATE_OK && response.result != null) {

			if (request.getRequestUrl() == HttpURL.URL_TRADE_LOG) { // 获取推送的log
				if (response.result.getSuccess()) {
					m_start_page += PAGE_SIZE_ADD;
					mRefreshTime = ToolUtils.getNowTime();
					xlv.setRefreshTime(mRefreshTime);
					setData(response.result.getStrBody());
				} else {
					BToast.show(mActivity, response.result.getErrorMsg());
				}
			} else if (request.getRequestUrl() == HttpURL.URL_PUSH_LOG_CLEAR) { // 清空推送的log
				if (response.result.getSuccess()) {
					Trades.clear();
					adapter.notifyDataSetChanged();
					showVisible();
				} else {
					BToast.show(mActivity, response.result.getErrorMsg());
				}
			}
		} else {
			BToast.show(mActivity, R.string.system_error);
		}
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
		TaskRequest.getInstance().requestTradeLog(this, mActivity, pUitl.getUserID(),m_start_page+1, m_start_page+PAGE_SIZE_ADD);
	}
	
}

