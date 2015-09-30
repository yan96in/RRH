package com.cbkj.rrh.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cbkj.rrh.R;
/**
 * @todo:图片与文字的View
 * @author:hg_liuzl@163.com
 */
public class ImgWordView extends LinearLayout {
	private ImageView ivIcon;
	private TextView tvName;
	
	
	public ImgWordView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context,attrs);
	}
	
	private void initView(Context context,AttributeSet attrs) {
		
		
		TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.thirdbar); 
		int text  = a.getResourceId(R.styleable.thirdbar_t_text, -1);
		int img = a.getResourceId(R.styleable.thirdbar_t_img, -1);
		
		LayoutInflater.from(context).inflate(R.layout.item_img_word, this);
		ivIcon = (ImageView) findViewById(R.id.iv_img);
		ivIcon.setBackgroundResource(img);
		tvName = (TextView) findViewById(R.id.tv_name);
		tvName.setText(text);
		
		a.recycle();
	}
	
	
	/**
	 * @todo:设置图片logo与文字
	 * @author:hg_liuzl@163.com
	 * @params:
	 */
	public void initLogoAndName(int drawIcon,int logoName) {
		ivIcon.setBackgroundResource(drawIcon);
		tvName.setText(logoName);
	}
	
	/**
	 * @todo:设置字体颜色
	 * @author:hg_liuzl@163.com
	 * @params:
	 */
	public void setTextColor(Context context,int color) {
		tvName.setTextColor(context.getResources().getColor(color));
	}
	
}
