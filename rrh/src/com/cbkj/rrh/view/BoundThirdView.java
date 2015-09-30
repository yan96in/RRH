package com.cbkj.rrh.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cbkj.rrh.R;

/**
 * @todo:绑定控件
 * @date:2015-4-25 下午4:07:29
 * @author:hg_liuzl@163.com
 */
public class BoundThirdView extends RelativeLayout {

	private ImageView iv;
	private TextView tvCount,tvBoundState;
	
	public BoundThirdView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context, attrs);
	}
	
	private void initView(Context context, AttributeSet attrs) {
		TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.boundbar); 
		int img  = a.getResourceId(R.styleable.boundbar_b_img, -1);
		int text = a.getResourceId(R.styleable.boundbar_b_text, -1);
		
		LayoutInflater.from(context).inflate(R.layout.model_band_third, this);

		iv = (ImageView) findViewById(R.id.iv_bound_icon);
		iv.setBackgroundResource(img);
		
		tvCount = (TextView) findViewById(R.id.tv_bound_name);
		tvCount.setText(text);
		
		tvBoundState = (TextView) findViewById(R.id.tv_bound_state);
		
		a.recycle();
		
	}
	
	/**
	 * 
	 * @todo:设置绑定状态
	 * @date:2015-4-25 下午4:52:10
	 * @author:hg_liuzl@163.com
	 * @params:@param isBound
	 */
	public void setBoudState(boolean isBound){
		tvBoundState.setSelected(isBound?true:false);
		tvBoundState.setText(isBound?"取消绑定":"点击绑定");
	}
}
