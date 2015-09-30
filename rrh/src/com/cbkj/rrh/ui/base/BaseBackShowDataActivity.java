package com.cbkj.rrh.ui.base;

import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.cbkj.rrh.adapter.KBaseAdapter;
import com.cbkj.rrh.utils.ToolUtils;
import com.cbkj.rrh.view.BToast;
import com.cbkj.rrh.view.xlistview.XListView;

/**
 * @todo:需要列表展示数据的activity 基类,可以右滑返回的Activity
 * @date:2014-12-5 上午10:56:12
 * @author:hg_liuzl@163.com
 */
public class BaseBackShowDataActivity extends BaseBackShareActivity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	/*******通用的数据刷新与设置数据方法***************************************************/
	public int m_start_page = 0;
	
	public boolean isRefreshAction = true;
	
	public String mRefreshTime = "";
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	/**
	 * 
	 * @todo:数据适配器
	 * @date:2015-6-11 上午9:50:56
	 * @author:hg_liuzl@163.com
	 * @params:@param xListView
	 * @params:@param adapter
	 * @params:@param showList
	 * @params:@param resultlist
	 * @params:@param isRefresh
	 * @params:@param needTip	//是否需要数据加载完成的提示
	 */
	public void setDataAdapter(XListView xListView,KBaseAdapter adapter,List<?> showList,List resultlist,boolean isRefresh){
		setDataAdapter(xListView, adapter, showList, resultlist, isRefresh,true);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	/**
	 * 
	 * @todo:数据适配器
	 * @date:2015-6-11 上午9:50:56
	 * @author:hg_liuzl@163.com
	 * @params:@param xListView
	 * @params:@param adapter
	 * @params:@param showList
	 * @params:@param resultlist
	 * @params:@param isRefresh
	 * @params:@param needTip	//是否需要数据加载完成的提示
	 */
	public void setDataAdapter(XListView xListView,KBaseAdapter adapter,List<?> showList,List resultlist,boolean isRefresh,boolean needTip)
    {
    	mRefreshTime = ToolUtils.getNowTime();
    	xListView.setRefreshTime(mRefreshTime);
    	stopLoad(xListView);
    	
	   	 if(isRefresh){
			 showList.clear();
			 isRefresh = false;
		 }
    	
    	if(null == resultlist || resultlist.size() ==0)
    	{
    		xListView.setPullLoadEnable(false);
    		// BToast.show(mActivity, "数据加载完毕");
    		return;
    	}else{
    		 if (resultlist.size() < PAGE_SIZE_ADD)
             {
        		 xListView.setPullLoadEnable(false);
//        		 if (needTip) {
//        			 BToast.show(mActivity, "数据加载完毕");
//				}
             }else
             {
            	 xListView.setPullLoadEnable(true);
             }
        	 showList.addAll(resultlist);
    	}
    	adapter.notifyDataSetChanged();
    }
    /**
     * 加载完成之后进行时间保存等方法
     */
    @SuppressLint("SimpleDateFormat")
	private void stopLoad(XListView xListView) {
        xListView.stopRefresh();
        xListView.stopLoadMore();
    }
}
