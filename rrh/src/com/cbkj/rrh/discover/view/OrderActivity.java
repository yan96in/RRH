//package com.cbkj.rrh.discover.view;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.os.Bundle;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.support.v4.view.ViewPager.OnPageChangeListener;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.ImageView;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.RadioGroup.OnCheckedChangeListener;
//
//import com.alibaba.fastjson.JSON;
//import com.cbkj.rrh.R;
//import com.cbkj.rrh.adapter.TaskAdapter;
//import com.cbkj.rrh.adapter.TaskAdapter.TaskType;
//import com.cbkj.rrh.bean.TaskBean;
//import com.cbkj.rrh.main.MainActivity;
//import com.cbkj.rrh.main.MessageReceiver;
//import com.cbkj.rrh.main.PushMessageActivity;
//import com.cbkj.rrh.main.base.BaseShowDataActivity;
//import com.cbkj.rrh.net.http.HttpRequest;
//import com.cbkj.rrh.net.http.HttpRequestAsyncTask.TaskListenerWithState;
//import com.cbkj.rrh.net.http.HttpResponse;
//import com.cbkj.rrh.net.http.HttpResponse.HttpTaskState;
//import com.cbkj.rrh.net.http.HttpURL;
//import com.cbkj.rrh.net.request.TaskRequest;
//import com.cbkj.rrh.view.BToast;
//import com.cbkj.rrh.view.xlistview.XListView;
//import com.cbkj.rrh.view.xlistview.XListView.IXListViewListener;
//import com.umeng.analytics.MobclickAgent;
//
//
///**
// * 
// * @todo:我的消息
// * @date:2015-7-23 下午3:12:38
// * @author:hg_liuzl@163.com
// */
//public class OrderActivity extends BaseShowDataActivity implements OnItemClickListener,TaskListenerWithState,IXListViewListener{
//
//	
//	private ViewPager mTabPager;//页卡内容
//	
//	private int REQUEST_FLAG = TaskBean.TASK_SENT_TAG;
//
//    private RadioGroup radio_group;
//    private ImageView img0,img1,ivNodata,ivRemberTip;
//	
//	private TaskAdapter mSentAdapter = null;
//	private TaskAdapter mReciveAdapter = null;
//	
//	private XListView mSentXLv = null;   
//	private XListView mReciveXLv = null;    
//	
//	private ArrayList<TaskBean> mSentBeans = new ArrayList<TaskBean>();
//	private ArrayList<TaskBean> mReciveBeans = new ArrayList<TaskBean>();
//
//	private int m_SentStart = 0;
//	private int m_ReciveStart = 0;
//	private String userId = "";	
//	
//	private boolean mKeep = false;	//是否切换tab的开关
//	
//	private boolean isNeedShowDialog = true;
//	
//	@Override
//	protected void onResume() {
//		super.onResume();
//		MobclickAgent.onResume(this);
//		int tab = 0;
//		if (MainActivity.mGoToReciverTask) {	//判断是否是从我要接单的地方进来
//			tab = 1;
//			mKeep = false;
//		} else {
//			tab = 0;
//			mKeep = true;
//		}
//		
//		if (!mKeep) {	//防止切换回来乱跳tab
//			mTabPager.setCurrentItem(tab);
//			showTabTag(tab);
//			MainActivity.mGoToReciverTask = false;	//重新改回原值
//			REQUEST_FLAG = tab == 0?TaskBean.TASK_SENT_TAG:TaskBean.TASK_RECIVED_TAG;
//			onRefresh();
//		}else{
//			boolean isNeedRefresh = false;
//			
//			if (REQUEST_FLAG == TaskBean.TASK_SENT_TAG) {
//				isNeedRefresh =  mSentBeans.size()>0 ? false:true;
//			} else {
//				isNeedRefresh =  mReciveBeans.size()>0 ? false:true;
//			}
//			
//			onRefresh();
//		}
//	}
//	
//	@Override
//	protected void onPause() {
//		super.onPause();
//		mKeep = true;
//		MobclickAgent.onPause(this);
//	}
//	
//	/**加载本地数据**/
//	private void loadLocalData() {
//			
//		List<TaskBean> sentTasks = pUitl.getSentTaskData();
//		
//		if (null != sentTasks) {
//			mSentBeans.addAll(sentTasks);
//		}
//		List<TaskBean> recivedTasks = pUitl.getRecivedTaskData();
//
//		if (null != recivedTasks) {
//			mReciveBeans.addAll(recivedTasks);
//		}
//		
//		setLocalData(REQUEST_FLAG);
//		
//
//	}
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.a_order_layout);
//		userId = pUitl.getUserID();
//		initTitle();
//		initView();
//		loadLocalData();
//		registerPushBoradcastReceiver();
//		setMentionTips();
//	}
//	
//	/**
//	 * 
//	 * @todo:进入Message
//	 * @date:2015-8-25 上午10:54:51
//	 * @author:hg_liuzl@163.com
//	 * @params:
//	 */
//	private void enterMessage() {
//		
//		pUitl.setNoReadMsg(0,pUitl.getUserID()); //清空与我相关提示
//		sendBroadcast(new Intent(MessageReceiver.MSG_RECEIVER_TAG));
//		
//		Intent intent = new Intent(mActivity,PushMessageActivity.class);
//		startActivity(intent);
//	}
//	
//	private void initTitle() {
//		
//		findViewById(R.id.fl_rignt).setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				enterMessage();
//			}
//		});
//		
//		ivRemberTip = (ImageView) findViewById(R.id.iv_rember_tip);
//		
//		findViewById(R.id.tv_trade_log).setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(mActivity, PayTradeActivity.class);
//				mActivity.startActivity(intent);
//			}
//		});
//	}
//	
//	private void initView() {
//		radio_group = (RadioGroup) findViewById(R.id.radio_group);
//		radio_group.setOnCheckedChangeListener(mOnCheckedChangeListener);
//		
//		mTabPager = (ViewPager) findViewById(R.id.vp_publish_type_select);
//		//设置ViewPager的页面翻滚监听
//		mTabPager.setOnPageChangeListener(new myOnPageChangeListener());
//		
//		img0 = (ImageView) findViewById(R.id.img_01);
//		img1 = (ImageView) findViewById(R.id.img_02);
//		ivNodata = (ImageView) findViewById(R.id.iv_no_data);
//		
//		View view1 = inflater.inflate(R.layout.listview_space_bar, null);
//		mSentXLv = (XListView) view1.findViewById(R.id.xlv_sapce);
//		mSentXLv.setPullLoadEnable(true);
//		mSentXLv.setPullRefreshEnable(true);
//		mSentXLv.setXListViewListener(this);
//		mSentXLv.setOnItemClickListener(this);
//		mSentAdapter = new TaskAdapter(mSentBeans, mActivity,TaskType.SENT_TASK,pUitl);
//		mSentXLv.setAdapter(mSentAdapter);
//		
//		View view2 = inflater.inflate(R.layout.listview_space_bar, null);
//		mReciveXLv = (XListView) view2.findViewById(R.id.xlv_sapce);
//		mReciveXLv.setPullLoadEnable(true);
//		mReciveXLv.setPullRefreshEnable(true);
//		mReciveXLv.setXListViewListener(this);
//		mReciveXLv.setOnItemClickListener(this);
//		mReciveAdapter = new TaskAdapter(mReciveBeans, mActivity,TaskType.RECIVE_TASK,pUitl);
//		mReciveXLv.setAdapter(mReciveAdapter);
//		
//		
//		//将布局放入集合
//		final ArrayList<View> views = new ArrayList<View>();
//		views.add(view1);
//		views.add(view2);
//		
//		//放入adapter
//		PagerAdapter adapter = new PagerAdapter() {
//
//			@Override
//			public boolean isViewFromObject(View arg0, Object arg1) {
//				return arg0 == arg1;
//			}
//
//			@Override
//			public int getCount() {
//				return views.size();
//			}
//
//			@Override
//			public void destroyItem(View container, int position, Object object) {
//				((ViewPager)container).removeView(views.get(position));
//			}
//
//			@Override
//			public Object instantiateItem(View container, int position) {
//				((ViewPager)container).addView(views.get(position));
//				return views.get(position);
//			}
//		};
//		mTabPager.setAdapter(adapter);
//
//	}
//	
//
//	/**
//	 * 
//	 */
//	private OnCheckedChangeListener mOnCheckedChangeListener = new OnCheckedChangeListener() {
//		@Override
//		public void onCheckedChanged(RadioGroup group, int checkedId) {
//			int radioButtonId = group.getCheckedRadioButtonId();
//			boolean isNeedLoadData = true;	//如果没有数据，就需要加载数据，如果有数据就不需要加载数据
//			switch (radioButtonId) {
//			case R.id.radio_01:
//				REQUEST_FLAG = TaskBean.TASK_SENT_TAG;
//				mTabPager.setCurrentItem(0);
//				showTabTag(0);
//				isNeedLoadData = mSentBeans.size()>0 ? false:true;
//				break;
//			case R.id.radio_02:
//				REQUEST_FLAG = TaskBean.TASK_RECIVED_TAG;
//				mTabPager.setCurrentItem(1);
//				showTabTag(1);
//				isNeedLoadData = mReciveBeans.size()>0 ? false:true;
//				break;
//			default:
//				break;
//			}
//			ivNodata.setVisibility(!isNeedLoadData?View.GONE:View.VISIBLE);
//			setLocalData(REQUEST_FLAG);
//			//showData(isNeedLoadData);
//			onRefresh();
//		}
//	};
//	
//	private void showTabTag(int index) {
//		switch (index) {
//		case 0:
//			img0.setVisibility(View.VISIBLE);
//			img1.setVisibility(View.INVISIBLE);
//			break;
//		case 1:
//			img0.setVisibility(View.INVISIBLE);
//			img1.setVisibility(View.VISIBLE);
//			break;
//		default:
//			break;
//		}
//	}
//	
//	public class myOnPageChangeListener implements OnPageChangeListener{
//
//		@Override
//		public void onPageScrollStateChanged(int arg0) {
//			
//		}
//
//		@Override
//		public void onPageScrolled(int arg0, float arg1, int arg2) {
//			
//		}
//
//		@Override
//		public void onPageSelected(int i) {
//			RadioButton child = (RadioButton) radio_group.getChildAt(i);
//			child.setChecked(true);
//		}
//	}
//
//	@Override
//	public void onRefresh() {
//		isRefreshAction = true;
//		if (REQUEST_FLAG == TaskBean.TASK_SENT_TAG) {
//			m_SentStart = 0;
//		} else if (REQUEST_FLAG == TaskBean.TASK_RECIVED_TAG) {
//			m_ReciveStart = 0;
//		} 
//		showData(true);
//	}
//
//	@Override
//	public void onLoadMore() {
//		isRefreshAction = false;
//		showData(true);
//	}
//
//	@Override
//	public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
//		final TaskBean bean = (TaskBean) adapter.getAdapter().getItem(position);
//		if (null!=bean) {
//			OrderDetailActivity.goToDetail(mActivity, REQUEST_FLAG, bean);
//		}
//	}
//
//	/**
//	 * 
//	 * @todo:是否需要刷新数据
//	 * @date:2015-2-3 下午6:46:05
//	 * @author:hg_liuzl@163.com
//	 * @params:@param isNeedRefresh
//	 */
//	private void doRequest(boolean isNeedRefresh) {
////		if(!isNeedRefresh){
////			return;
////		}
//		if(REQUEST_FLAG == TaskBean.TASK_SENT_TAG){
//			TaskRequest.getInstance().requestMySentTasks(this, mActivity.getParent(), userId, m_SentStart+1, m_SentStart+PAGE_SIZE_ADD,isNeedShowDialog);
//			isNeedShowDialog = true;
//		}else if(REQUEST_FLAG == TaskBean.TASK_RECIVED_TAG){
//			TaskRequest.getInstance().requestMyReciveTasks(this, mActivity.getParent(), userId, m_ReciveStart+1, m_ReciveStart+PAGE_SIZE_ADD,isNeedShowDialog);
//			isNeedShowDialog = true;
//		}
//	}
//	
//	
//	/**
//	 * 
//	 * @todo:展示数据
//	 * @date:2015-3-12 下午8:23:51
//	 * @author:hg_liuzl@163.com
//	 * @params:@param index
//	 */
//	private void showData(boolean isNeedLoadData) {
////		if (!ConfigUtil.isConnect(mActivity)) {	//获取本地数据
////			String strJson = "";
////			if (REQUEST_FLAG == TaskBean.TASK_SENT_TAG) {
////				if (mSentBeans.size() == 0) {
////					strJson = pUitl.getSentTaskData();
////				}
////			} else if (REQUEST_FLAG == TaskBean.TASK_RECIVED_TAG) {
////				strJson = pUitl.getRecivedTaskData();
////			}
////			setData(strJson, REQUEST_FLAG);
////			
////		} else {//获取网络数据
////			doRequest(isNeedLoadData);
////		}
//		doRequest(isNeedLoadData);
//		
//		
////		String strJson = "";
////		if (REQUEST_FLAG == TaskBean.TASK_SENT_TAG) {
////			if (mSentBeans.size() == 0) {
////				strJson = pUitl.getSentTaskData();
////			}
////		} else if (REQUEST_FLAG == TaskBean.TASK_RECIVED_TAG) {
////			strJson = pUitl.getRecivedTaskData();
////		}
////		setData(strJson, REQUEST_FLAG);
////		
////		if (ConfigUtil.isConnect(mActivity)) { // 获取本地数据
////			doRequest(isNeedLoadData);
////		}
//			
//	}
//	
//	/**
//	 * 
//	 * @todo:设置数据
//	 * @date:2015-7-23 下午2:47:01
//	 * @author:hg_liuzl@163.com
//	 * @params:@param strJson
//	 */
//	private void setData(List<TaskBean> resultlist,int type) {
//			if (REQUEST_FLAG == TaskBean.TASK_SENT_TAG) {
//				setDataAdapter(mSentXLv, mSentAdapter,mSentBeans, resultlist,isRefreshAction);
//				ivNodata.setVisibility(mSentBeans.size() > 0 ? View.GONE:View.VISIBLE);
//			} else if (REQUEST_FLAG == TaskBean.TASK_RECIVED_TAG) {
//				setDataAdapter(mReciveXLv, mReciveAdapter,mReciveBeans, resultlist,isRefreshAction);
//				ivNodata.setVisibility(mReciveBeans.size() > 0 ? View.GONE:View.VISIBLE);
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
//	private void setLocalData(int type) {
//			if (REQUEST_FLAG == TaskBean.TASK_SENT_TAG) {
//				mSentAdapter.notifyDataSetChanged();
//				ivNodata.setVisibility(mSentBeans.size() > 0 ? View.GONE:View.VISIBLE);
//			} else if (REQUEST_FLAG == TaskBean.TASK_RECIVED_TAG) {
//				mReciveAdapter.notifyDataSetChanged();
//				ivNodata.setVisibility(mReciveBeans.size() > 0 ? View.GONE:View.VISIBLE);
//		}
//	}
//
//	@Override
//	public void onTaskOver(HttpRequest request, HttpResponse response) {
//		if (response.getState() == HttpTaskState.STATE_OK) {
//			if (request.getRequestUrl() == HttpURL.URL_ISENT_TASK  && null!= response.result) {	//我发的单
//				if (response.result.getSuccess()) {	
//					m_SentStart += PAGE_SIZE_ADD;
//					
//					String strJson = response.result.getStrBody();
//					
//					pUitl.storeSentTaskData(strJson,isRefreshAction);	//数据保存到本地
//					
//					List<TaskBean> resultlist = JSON.parseArray(strJson,TaskBean.class);
//					
//					setData(resultlist,TaskBean.TASK_SENT_TAG);
//				}else{
//					BToast.show(mActivity, response.result.getErrorMsg());
//				} 
//			}else if (request.getRequestUrl() == HttpURL.URL_IRECIVER_TASK  && null!= response.result) {	//我接的单
//				if (response.result.getSuccess()) {	
//					m_ReciveStart += PAGE_SIZE_ADD;
//					
//					String strJson = response.result.getStrBody();
//					
//					pUitl.storeRecivedTaskData(strJson,isRefreshAction);	//数据保存到本地
//
//					List<TaskBean> resultlist = JSON.parseArray(strJson,TaskBean.class);
//					
//					setData(resultlist,TaskBean.TASK_RECIVED_TAG);
//				}else{
//					BToast.show(mActivity, response.result.getErrorMsg());
//				} 
//			}
//		}else{
//			mSentXLv.stopRefresh();
//			mReciveXLv.stopRefresh();
//		}
//	}
//	
//	
//	/***
//	 * 
//	 * @todo:注册推送数量广播
//	 * @date:2015-6-15 下午5:24:09
//	 * @author:hg_liuzl@163.com
//	 * @params:
//	 */
//    public void registerPushBoradcastReceiver(){  
//        IntentFilter myIntentFilter = new IntentFilter();  
//        myIntentFilter.addAction(MessageReceiver.MSG_RECEIVER_TAG);  
//        //注册广播        
//        registerReceiver(mBroadcastReceiver, myIntentFilter);  
//    }
//    
//    private  BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			String action = intent.getAction();
//			if(action.equals(MessageReceiver.MSG_RECEIVER_TAG)){	//收到广播
//				setMentionTips();
//				isNeedShowDialog = false;
//				onRefresh();	//收到通知，刷新数据
//			}
//		}
//	};
//	
//	
//	/**
//	 * 
//	 * @todo:设置与我相关的提示数
//	 * @date:2015-6-15 下午7:13:00
//	 * @author:hg_liuzl@163.com
//	 * @params:
//	 */
//	private void setMentionTips(){
//		if (pUitl.isLogin()) {
//			boolean hasNoRead = pUitl.hasNoReadMsg(pUitl.getUserID());
//			ivRemberTip.setVisibility(hasNoRead?View.VISIBLE:View.GONE);
//		}
//	}
//}
