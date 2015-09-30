package com.cbkj.rrh.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView.OnEditorActionListener;

import com.cbkj.rrh.R;

/**
 * @todo:自定义清除按钮
 * @date:2015-4-23 下午3:39:56
 * @author:hg_liuzl@163.com
 */
public class MyEditTextView extends LinearLayout {
	public EditText etContent;
	public ImageView ivClear;

	public MyEditTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context,attrs);
	}
	
	private void initView(Context context, AttributeSet attrs) {

		 LayoutInflater.from(context).inflate(R.layout.model_edit_layout, this);
		
		 etContent = (EditText) findViewById(R.id.et_content);
		 
		 etContent.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,int arg3) {
				
			}
			
			@Override
			public void afterTextChanged(Editable e) {
				ivClear.setVisibility(TextUtils.isEmpty(etContent.getText().toString())?View.INVISIBLE:View.VISIBLE);
			}
		});
		 
		 ivClear = (ImageView) findViewById(R.id.iv_do_delete);
		 
		 ivClear.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				etContent.setText("");
			}
		});
	}
	
	public void setContent(String content){
		etContent.setText(content);
		etContent.setSelection(etContent.getText().length());
	}
	
	public void setOnEditorActionListener(OnEditorActionListener listener){
		etContent.setOnEditorActionListener(listener);
	}
	
	public void setHint(String content){
		etContent.setHint(content);
	}
	
	public String getContent(){
		return etContent.getText().toString().trim();
	}
	
	public void setSingLine(){
		etContent.setSingleLine(true);
	}
	
	public void setTextSize(int size){
		etContent.setTextSize(size);
	}
}
