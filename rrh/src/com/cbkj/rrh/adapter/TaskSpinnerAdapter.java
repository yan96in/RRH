package com.cbkj.rrh.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.SpinnerAdapter;

import com.cbkj.rrh.R;


/**
 * 
 * @todo:任务啊
 * @date:2015-7-20 上午10:18:41
 * @author:hg_liuzl@163.com
 */

public class TaskSpinnerAdapter extends KBaseAdapter implements SpinnerAdapter {

	public TaskSpinnerAdapter(List<?> mList, Activity mActivity) {
		super(mList, mActivity);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView=mInflater.inflate(R.layout.item_check_worktype, null);
		}
		CheckedTextView view = (CheckedTextView) convertView.findViewById(R.id.check_work_type);
		
		final Object object = mList.get(position);
		String str = (String) object;
		view.setText(str);
	
		return convertView;
	}
	
	@Override
	public View getDropDownView(int position, View convertView,
			ViewGroup parent) {
		if(convertView==null){
			convertView=mInflater.inflate(R.layout.item_check_worktype, null);
		}
		CheckedTextView view=(CheckedTextView) convertView.findViewById(R.id.check_work_type);
		
		final Object object = mList.get(position);
		String str = (String) object;
		view.setText(str);
		return convertView;
	}

}