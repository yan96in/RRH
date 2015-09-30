package com.cbkj.rrh.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cbkj.rrh.R;

/**
 * @todo:自定义选项控件
 * @date:2015-4-21 上午10:16:51
 * @author:hg_liuzl@163.com
 */
public class MySelectItemView extends LinearLayout {

	public TextView tvTitle,tvContent;
	public ImageView ivTitle,ivNext;
	
	public MySelectItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context,attrs);
	}
	
	private void initView(Context context,AttributeSet attrs) {
		TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.my_select_view); 
		int imgTitle  = a.getResourceId(R.styleable.my_select_view_img_title, -1);
		int imgNext  = a.getResourceId(R.styleable.my_select_view_img_next, -1);
		int title  = a.getResourceId(R.styleable.my_select_view_text_title, -1);
		
		
		LayoutInflater.from(context).inflate(R.layout.my_model_select_view, this);
		
		tvTitle = (TextView) findViewById(R.id.tv_title_text);
		tvTitle.setText(title);
		
		tvContent = (TextView) findViewById(R.id.tv_content_tag);
		
		ivTitle = (ImageView) findViewById(R.id.iv_title_img);
		ivTitle.setBackgroundResource(imgTitle);

		ivNext = (ImageView) findViewById(R.id.iv_next_img);
		ivNext.setImageResource(imgNext);
		
		a.recycle();
	}
	
	/**
	 * 
	 * @todo:设置控件隐藏
	 * @date:2015-8-7 下午4:53:16
	 * @author:hg_liuzl@163.com
	 * @params:
	 */
	public void setControlHidden(View v) {
		v.setVisibility(View.GONE);
	}
	
	/**
	 * 
	 * @todo:设置正文内容
	 * @date:2015-8-7 下午4:55:09
	 * @author:hg_liuzl@163.com
	 * @params:@param str
	 */
	public void setContent(String str){
		tvContent.setVisibility(View.VISIBLE);
		tvContent.setText(str);
	}
	
	/**
	 * 
	 * @todo:设置图片
	 * @date:2015-9-6 下午3:32:54
	 * @author:hg_liuzl@163.com
	 * @params:@param resource
	 */
	public void setIvTag(int resource) {
		ivNext.setVisibility(View.VISIBLE);
		ivNext.setImageResource(resource);
	}
	
}
