package com.cbkj.rrh.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.cbkj.rrh.R;
import com.cbkj.rrh.bean.FileBean;
import com.cbkj.rrh.main.BGApp;
import com.cbkj.rrh.main.base.KBaseAdapter;
import com.cbkj.rrh.others.utils.DeviceUtils;


/**
 * 九宫格展示小图片
 */
public class FileGridAdapter extends KBaseAdapter {
	private int mColumn = 0;
	private int mScreenWidth =  0;
	
	/** 图片Url集合 */
	private ArrayList<String> imageSmallUrls = new ArrayList<String>();
	
	public FileGridAdapter(List<FileBean> mList, Activity mActivity,int numberColums,final GridView gv) {
		super(mList, mActivity);
		this.mColumn = numberColums;
		this.mScreenWidth = DeviceUtils.getDeviceWidth(mActivity) - gv.getPaddingLeft()-gv.getPaddingRight();	//减去左右边距
		for(FileBean img:mList){
			imageSmallUrls.add(img.small);
		}
	}
	
	public FileGridAdapter(List<FileBean> mList, Activity mActivity,int numberColums,final GridView gv,final ListView listView) {
		super(mList, mActivity);
		this.mColumn = numberColums;
		this.mScreenWidth = DeviceUtils.getDeviceWidth(mActivity) - gv.getPaddingLeft()-gv.getPaddingRight()-listView.getPaddingLeft()-listView.getPaddingRight();	//减去左右边距
		for(FileBean img:mList){
			imageSmallUrls.add(img.small);
		}
	}
	
	//说明，如果图片只有一张的话，就按原图来展示，否则，要指定图片大小

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = mInflater.inflate(R.layout.item_gridview, null);
		ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_image);
		RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams(); // 取控件mGrid当前的布局参数   
		
		if (mColumn == 1) {
			linearParams.width = LayoutParams.WRAP_CONTENT;
			linearParams.height = LayoutParams.WRAP_CONTENT;
		}else {
			linearParams.width = mScreenWidth/mColumn;
			linearParams.height = mScreenWidth/mColumn;
		}
		imageView.setLayoutParams(linearParams);
//		imageView.setScaleType(ScaleType.CENTER_CROP);
//		imageView.setAdjustViewBounds(true);
		BGApp.getInstance().setImageSqure(imageSmallUrls.get(position), imageView);
		notifyDataSetChanged();
		return convertView;
	}

}
