package com.cbkj.rrh.widget;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cbkj.rrh.R;
import com.cbkj.rrh.net.http.HttpURL;

/**
 * @todo:提示的小组件
 * @date:2015-4-13 下午8:34:56
 * @author:hg_liuzl@163.com
 */
public class TipBar {
	
	private TextView tvTip;
	private RelativeLayout llTip;
	
	public TipBar(Activity activity){
		initView(activity);
	}
	
	private void initView(Activity activity) {
		llTip = (RelativeLayout) activity.findViewById(R.id.ll_tip);
		tvTip = (TextView) activity.findViewById(R.id.tv_tip);
		tvTip.setText(HttpURL.BASE_URL.equals("http://192.168.1.108:9090/bbcwm")?"内网":"外网");
		activity.findViewById(R.id.tv_close).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				llTip.setVisibility(View.GONE);
			}
		});
	}
	
	public void setTipWord(String str) {
		tvTip.setText(str);
		tvTip.setVisibility(View.VISIBLE);
	}
	
	public void setVisible(int value){
		llTip.setVisibility(value);
	}
}
