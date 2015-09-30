package com.cbkj.rrh.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cbkj.rrh.R;

/**
 *自定义控件，操作赞，评论，转发，分享
 */
public class ActionView extends LinearLayout {

	private ImageView iv;
	private TextView tvCount;
	private Animation animation;
	
	public ActionView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context, attrs);
	}
	
	private void initView(Context context, AttributeSet attrs) {
		
		animation = (AnimationSet) AnimationUtils.loadAnimation(context, R.anim.scale_zoom_enter);
		
		TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.actionBar); 
		int text  = a.getResourceId(R.styleable.actionBar_text, -1);
		int img = a.getResourceId(R.styleable.actionBar_img, -1);
		
		LayoutInflater.from(context).inflate(R.layout.layout_action_view, this);
		
		iv = (ImageView) findViewById(R.id.iv_type);
		iv.setBackgroundResource(img);
		iv.setOnClickListener(listener);
		
		tvCount = (TextView) findViewById(R.id.tv_count);
		tvCount.setText(text);
		
		a.recycle();
	}
	
	public void setCount(int count){
		tvCount.setText(String.valueOf(count));
	}
	
	public void setCount(String count){
		tvCount.setText(count);
	}
	
	public void setText(String text){
		tvCount.setText(text);
	}
	
	public String getText(){
		return tvCount.getText().toString();
	}
	
	public void setCountHidden(){
		tvCount.setVisibility(View.GONE);
	}
	
	private OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			iv.setAnimation(animation);
			animation.start();
		}
	};
}
