package com.cbkj.rrh.view;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.cbkj.rrh.R;
import com.cbkj.rrh.adapter.WorkPositionAdapter;
import com.cbkj.rrh.bean.WorkBean;
import com.cbkj.rrh.db.PreferenceUtil;
import com.cbkj.rrh.net.http.HttpRequest;
import com.cbkj.rrh.net.http.HttpRequestAsyncTask.TaskListenerWithState;
import com.cbkj.rrh.net.http.HttpResponse;
import com.cbkj.rrh.net.http.HttpResponse.HttpTaskState;
import com.cbkj.rrh.net.http.HttpURL;
import com.cbkj.rrh.net.request.UserRequest;
import com.cbkj.rrh.system.BGApp;
import com.cbkj.rrh.utils.ConfigUtil;
import com.cbkj.rrh.utils.DeviceUtils;

/**
 * 
 * @todo:标题栏组件
 * @date:2015-4-13 下午8:03:56
 * @author:hg_liuzl@163.com
 */
public class MyPopWindow implements TaskListenerWithState,OnItemClickListener {
	public int mWorkPosition = -1;	//职业选择
	private TextView mTvPosition;
	private PopupWindow mPopupWindow;
	private Activity mActivity;
	private List<WorkBean> mWorkBeans = new ArrayList<WorkBean>();
	private int mSelectWorktype = 0;	//用于控制宽度
	private IChangePositionCallback callback;
	private PreferenceUtil pUtils;
	
	public MyPopWindow(Activity context,PreferenceUtil p) {
		this.mActivity = context;
		this.pUtils = p;
		showData();
	}
	
	
	
	/**
	 * 
	 * @todo:展示数据
	 * @date:2015-3-12 下午8:23:51
	 * @author:hg_liuzl@163.com
	 * @params:@param index
	 */
	private void showData() {
		if (!ConfigUtil.isConnect(mActivity)) { // 获取本地数据
			
			if (pUtils.getWorkBeans() != null && pUtils.getWorkBeans().size() > 0) {
				mWorkBeans.clear();
				mWorkBeans.addAll(pUtils.getWorkBeans());
			}
		} else {// 获取网络数据
			if (BGApp.workbeans == null) {
				UserRequest.getInstance().requestPositonList(this, mActivity);
			}else{
				mWorkBeans.clear();
				mWorkBeans.addAll(BGApp.workbeans);
			}
		}
	}
	
	public void setCallback(IChangePositionCallback callback) {
		this.callback = callback;
	}
	
	public void setPosition(int position){
		this.mWorkPosition = position;
	}
	
	/**
	 * 
	 * @todo:全图片模式的标题栏,按默认的图片
	 * @date:2015-4-13 下午8:06:21
	 * @author:hg_liuzl@163.com
	 * @params:
	 */
	public void initWorkPosition() {
		initWorkPosition(true);
	}
	
	/**
	 * 
	 * @todo:全图片模式的标题栏,按默认的图片
	 * @date:2015-4-13 下午8:06:21
	 * @author:hg_liuzl@163.com
	 * @params:
	 */
	public void initWorkPosition(boolean titleTagVisible) {
		mSelectWorktype = 0;
		TextView tvPostionTag = (TextView) mActivity.findViewById(R.id.tv_postion_tag);
		tvPostionTag.setVisibility(titleTagVisible?View.VISIBLE:View.GONE);
		
		mTvPosition=  (TextView) mActivity.findViewById(R.id.tv_position);
		mTvPosition.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				doTitle();
			}
		});
		setDefault();
	}
	
	
	
	/**
	 * 
	 * @todo:全图片模式的标题栏,按默认的图片
	 * @date:2015-4-13 下午8:06:21
	 * @author:hg_liuzl@163.com
	 * @params:
	 */
	public void initWorkPositionForTask() {
		mSelectWorktype = 1;
		LinearLayout llTop = (LinearLayout) mActivity.findViewById(R.id.ll_top);
		llTop.setGravity(Gravity.CENTER);
		
		TextView tvPostionTag = (TextView) mActivity.findViewById(R.id.tv_postion_tag);
		tvPostionTag.setVisibility(View.GONE);
		
		mTvPosition=  (TextView) mActivity.findViewById(R.id.tv_position);
		mTvPosition.setTextColor(mActivity.getResources().getColor(R.color.white));
		mTvPosition.setBackgroundResource(R.drawable.bg_current_work_position);
		mTvPosition.setPadding(15, 5, 15, 5);
		
		
		 Drawable drawable = mActivity.getResources().getDrawable(R.drawable.icon_direction_home_selector);
		 drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());  
		 mTvPosition.setCompoundDrawables(null, null, drawable, null);
		
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT); 
		mTvPosition.setLayoutParams(params);
		mTvPosition.setGravity(Gravity.CENTER);
		
		mTvPosition.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				doTitle();
			}
		});
		
			WorkBean work = new WorkBean("全部", 0);
			mWorkBeans.add(0, work);
		
		
		setDefault();
	}
	
	/**
	 * 
	 * @todo:给Text文本设置一个默认值
	 * @date:2015-8-14 上午10:02:16
	 * @author:hg_liuzl@163.com
	 * @params:
	 */
	private void setDefault() {
		if (mWorkBeans.size() > 0) {
			final WorkBean bean = mWorkBeans.get(mWorkPosition!=-1?mWorkPosition:0);
			mWorkPosition = bean.position; // 默认是第一个数据
			mTvPosition.setText(bean.name);
		}
	}


	@SuppressLint("NewApi")
	public void showPopupMore() {
		int width = DeviceUtils.getDeviceWidth(mActivity);
		int contentWidth = width * 6/10;	//下拉条的宽度
		if (mWorkBeans.size() == 0) {
			return;
		}
		
		if (mPopupWindow == null) {
			View contentView = LayoutInflater.from(mActivity).inflate(R.layout.model_work_position, null);
			ListView listview = (ListView) contentView.findViewById(R.id.listview);
			WorkPositionAdapter adapter = new WorkPositionAdapter(mWorkBeans, mActivity);
			listview.setAdapter(adapter);
			listview.setOnItemClickListener(this);
			
			mPopupWindow = new PopupWindow(contentView,mSelectWorktype == 1?contentWidth:mTvPosition.getWidth(), DeviceUtils.getDeviceHeight(mActivity)/2);
			mPopupWindow.setFocusable(true);
			mPopupWindow.setOutsideTouchable(true);
			mPopupWindow.setBackgroundDrawable(mActivity.getResources().getDrawable(R.color.transparent));
			mPopupWindow.setOnDismissListener(new OnDismissListener() {
				
				@Override
				public void onDismiss() {
					mTvPosition.setSelected(false);
				}
			});
		}
		
		int contentX = (width - contentWidth)/2;	//下拉条的X坐标
		
		mPopupWindow.showAsDropDown(mTvPosition, 0,mSelectWorktype == 1?25:0);
		
		mTvPosition.setSelected(true);
	}

	public void dissmissPopupMore() {
		if (mPopupWindow != null && mPopupWindow.isShowing()) {
			mPopupWindow.dismiss();
			mTvPosition.setSelected(false);
		}
	}

	/**
	 * 
	 * @todo:切换标题选项
	 * @date:2015-4-13 下午8:54:50
	 * @author:hg_liuzl@163.com
	 * @params:
	 */
	private void doTitle() {
		if (mPopupWindow != null && mPopupWindow.isShowing()) {
			dissmissPopupMore();
		} else {
			showPopupMore();
		}
	}


	@Override
	public void onTaskOver(HttpRequest request, HttpResponse response) {
		if (response.getState() == HttpTaskState.STATE_OK) {
			if (request.getRequestUrl() == HttpURL.URL_POSITION_LIST && response.result != null) {
				if (response.result.getSuccess()) { // 获取成功
					List<WorkBean> results = JSON.parseArray(response.result.getStrBody(), WorkBean.class);
					mWorkBeans.addAll(results);
					BGApp.workbeans = results;
					pUtils.storeWorkbeans(response.result.getStrBody());
					setDefault();
				}else{
					BToast.show(mActivity, response.result.getErrorMsg());
				}
			}
		}
	}
	

	@Override
	public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
		final WorkBean work = mWorkBeans.get(position);
		mTvPosition.setText(work.name);
		mWorkPosition = work.position;
		dissmissPopupMore();
		if (callback != null) {
			callback.change(mWorkPosition);
		}
	}
	
	/**
	 * 
	 * @todo:行业变化后的回调
	 * @date:2015-8-18 下午2:24:04
	 * @author:hg_liuzl@163.com
	 */
	public interface IChangePositionCallback{
		void change(int position);
	}
}
