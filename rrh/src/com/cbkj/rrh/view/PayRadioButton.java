package com.cbkj.rrh.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cbkj.rrh.R;

/**
 * 
 * @todo:自定义关于支付选择的按钮
 * @date:2015-9-2 上午10:14:49
 * @author:hg_liuzl@163.com
 */
public class PayRadioButton extends RelativeLayout {

	private RadioButton rb;
	
	public PayRadioButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context,attrs);
	}
	
	private void initView(Context context,AttributeSet attrs) {
		
		TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.radio_pay_view); 
		
		int icon  = a.getResourceId(R.styleable.radio_pay_view_p_img, -1);
		int text  = a.getResourceId(R.styleable.radio_pay_view_p_title, -1);
		int text_sub = a.getResourceId(R.styleable.radio_pay_view_p_sub_title, -1);
		
		
		LayoutInflater.from(context).inflate(R.layout.model_pay_item, this);
		
		ImageView ivIcon = (ImageView) findViewById(R.id.iv_pay_icon);
		ivIcon.setBackgroundResource(icon);
		
		TextView tvName = (TextView) findViewById(R.id.tv_pay_title);
		tvName.setText(text);
		
		TextView tvSubName = (TextView) findViewById(R.id.tv_pay_sub_title);
		tvSubName.setText(text_sub);
		
		rb = (RadioButton) findViewById(R.id.rb_select);
		//rb.setEnabled(false);
		
		a.recycle();
	}
	
	public void setPayCheck(boolean check) {
		rb.setChecked(check);
	}

	public boolean isPayChecked() {
		return rb.isChecked();
	}

}
