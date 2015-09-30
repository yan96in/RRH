package com.cbkj.rrh.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.cbkj.rrh.R;

/**
 * @todo:自定义选项控件
 * @date:2015-4-21 上午10:16:51
 * @author:hg_liuzl@163.com
 */
public class SelectItemView extends RelativeLayout {

	public TextView tvTitle,tvContent,tvTag;
	public ImageView iv;
	public ToggleButton tbn;
	
	public SelectItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context,attrs);
	}
	
	private void initView(Context context,AttributeSet attrs) {
		TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.selectView); 
		int title  = a.getResourceId(R.styleable.selectView_select_title, -1);
		int img  = a.getResourceId(R.styleable.selectView_select_img, -1);
		int tag  = a.getResourceId(R.styleable.selectView_select_tag, -1);
		
		LayoutInflater.from(context).inflate(R.layout.model_select_view, this);
		
		tvTitle = (TextView) findViewById(R.id.tv_title_tag);
		tvTitle.setText(title);
		
		tvContent = (TextView) findViewById(R.id.tv_content_tag);
		
		iv = (ImageView) findViewById(R.id.iv_tag);
		iv.setImageResource(img);

		tvTag = (TextView) findViewById(R.id.tv_tag);
		tvTag.setText(tag);
		
		
		tbn = (ToggleButton) findViewById(R.id.toogle_btn);
		
		a.recycle();
	}
	
	/**
	 * 
	 * @todo:展示图片
	 * @date:2015-4-21 下午3:20:21
	 * @author:hg_liuzl@163.com
	 * @params:
	 */
	public void showIV(int visible) {
		iv.setVisibility(visible);
	}
	
	/**
	 * 
	 * @todo:展示文本
	 * @date:2015-4-21 下午3:20:40
	 * @author:hg_liuzl@163.com
	 * @params:
	 */
	public void showText(String text) {
		tvTag.setText(text);
		tvTag.setVisibility(View.VISIBLE);
	}
	
	
	/**
	 * 
	 * @todo:展示开关按钮
	 * @date:2015-4-21 下午3:22:19
	 * @author:hg_liuzl@163.com
	 * @params:
	 */
	public void showToogle() {
		tbn.setVisibility(View.VISIBLE);
	}
	
	/**
	 * 
	 * @todo:设置内容
	 * @date:2015-7-28 上午9:58:17
	 * @author:hg_liuzl@163.com
	 * @params:@param content
	 */
	public void setTvContent(String content){
		tvContent.setVisibility(View.VISIBLE);
		tvContent.setText(content);
	}
	
	/**
	 * 
	 * @todo:设置标签是否可见
	 * 
	 * @date:2015-7-28 上午10:51:30
	 * @author:hg_liuzl@163.com
	 * @params:@param visible
	 */
	public void setTagVisible(int visible){
		iv.setVisibility(visible);
	}
	
	/**
	 * 
	 * @todo:设置图片
	 * @date:2015-9-6 下午3:32:54
	 * @author:hg_liuzl@163.com
	 * @params:@param resource
	 */
	public void setIvTag(int resource) {
		iv.setVisibility(View.VISIBLE);
		iv.setImageResource(resource);

	}
	
}
