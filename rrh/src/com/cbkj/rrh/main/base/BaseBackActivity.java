package com.cbkj.rrh.main.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.inputmethod.InputMethodManager;

import com.cbkj.rrh.db.DBHelper;
import com.cbkj.rrh.db.PreferenceUtil;
import com.cbkj.rrh.main.CBApp;
import com.cbkj.rrh.others.utils.SharePostUtils;
import com.cbkj.rrh.view.swipeback.app.SwipeBackActivity;
import com.umeng.socialize.sso.UMSsoHandler;
/**
 * 
 * @todo:Activity基类,可以右滑返回的Activity
 * @date:2014-12-25 上午11:05:00
 * @author:hg_liuzl@163.com
 */
public class BaseBackActivity extends SwipeBackActivity {
	public LayoutInflater inflater = null;
	public Activity mActivity = null;
	protected InputMethodManager im = null;
	public static final int PAGE_SIZE_ADD = 10;
	public PreferenceUtil pUitl;
	public DBHelper dbHelper = null;
	/**登录码**/
	public static final int LOGIN_CODE = 100;
	public String mUserId = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mActivity = this;
		CBApp.getInstance().addActivity(this);
		inflater = LayoutInflater.from(this);
		im = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		pUitl = new PreferenceUtil(this, PreferenceUtil.PREFERENCE_FILE);
		mUserId = pUitl.getUserID();
		dbHelper = new DBHelper(mActivity);
	}
	
	
	public void toActivity(Class<?> target, Bundle bundle)
	{
		Intent intent = new Intent(this, target);
		if (bundle != null)
			intent.putExtras(bundle);
		startActivity(intent);
	}

	public void toActivity(String action, Class<?> target, Bundle bundle)
	{
		Intent intent = new Intent(this, target);
		if (bundle != null)
			intent.putExtras(bundle);
		startActivity(intent);
	}

	public void toActivity(Class<?> target)
	{
		Intent intent = new Intent(this, target);
		startActivity(intent);
	}
	
	public void hideSoftInputFromWindow()
	{
		if (getCurrentFocus() == null){
				return;
		}
		
		im.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.SHOW_FORCED);
	}
	
	@Override 
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    /**使用SSO授权必须添加如下代码 */
	    UMSsoHandler ssoHandler = SharePostUtils.mController.getConfig().getSsoHandler(requestCode) ;
	    if(ssoHandler != null){
	       ssoHandler.authorizeCallBack(requestCode, resultCode, data);
	    }
	}


}
