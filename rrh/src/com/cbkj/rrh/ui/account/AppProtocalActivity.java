package com.cbkj.rrh.ui.account;

import android.os.Bundle;

import com.cbkj.rrh.R;
import com.cbkj.rrh.ui.base.BaseBackActivity;
import com.cbkj.rrh.widget.TitleBar;


/**
 * 
 * @todo:用户协议
 * @date:2015-8-11 上午9:13:33
 * @author:hg_liuzl@163.com
 */
public class AppProtocalActivity extends BaseBackActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_protocal_layout);
		(new TitleBar(mActivity)).initAllBar("用户协议");
	}

}
