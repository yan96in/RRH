package com.cbkj.rrh.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cbkj.rrh.R;
import com.cbkj.rrh.bean.WorkBean;

/**
 * 
 * @todo:工作类型适配器
 * @date:2015-8-13 上午11:19:06
 * @author:hg_liuzl@163.com
 */
public class WorkPositionAdapter extends KBaseAdapter {

	public WorkPositionAdapter(List<?> mList, Activity mActivity) {
		super(mList, mActivity);
	}

	ViewHolder viewHolder;
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView==null){
			viewHolder = new ViewHolder();
			convertView=mInflater.inflate(R.layout.item_work_position, null);
			viewHolder.tvPosition = (TextView) convertView.findViewById(R.id.tv_position);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		final WorkBean bean = (WorkBean) mList.get(position);
		viewHolder.tvPosition.setText(bean.name);
		return convertView;

	}
	
	final class ViewHolder
	{
		TextView tvPosition;
	}
	
}