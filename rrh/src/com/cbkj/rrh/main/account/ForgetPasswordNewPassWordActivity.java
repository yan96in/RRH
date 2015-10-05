package com.cbkj.rrh.main.account;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.cbkj.rrh.R;
import com.cbkj.rrh.bean.UserBean;
import com.cbkj.rrh.main.base.BaseBackActivity;
import com.cbkj.rrh.net.http.HttpRequest;
import com.cbkj.rrh.net.http.HttpRequestAsyncTask.TaskListenerWithState;
import com.cbkj.rrh.net.http.HttpResponse;
import com.cbkj.rrh.net.http.HttpResponse.HttpTaskState;
import com.cbkj.rrh.net.request.UserRequest;
import com.cbkj.rrh.view.BToast;
import com.cbkj.rrh.view.widget.TitleBar;
import com.umeng.analytics.MobclickAgent;

/**
 * 
 * @todo:重置密码
 * @date:2015-4-29 下午3:20:19
 * @author:hg_liuzl@163.com
 */
public class ForgetPasswordNewPassWordActivity extends BaseBackActivity implements TaskListenerWithState
{

	private String mPhoneNumber;
	private TitleBar mTitleBar;
	private EditText etPassWord;
	
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_forget_password_new_password);
        mPhoneNumber = getIntent().getStringExtra(UserBean.USER_TELEPHONE);
        findView();
    }
	
    /**
     * 控件初始化方法
     */
    private void findView()
    {
    	etPassWord = (EditText) findViewById(R.id.et_pwd);
    	mTitleBar = new TitleBar(mActivity);
    	mTitleBar.initAllBar("重置密码", "完成");
    	mTitleBar.rightBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				checkInfo();
			}
		});
    }
    
   
    
    /**
     * 检查用户信息方法
     */
    private void checkInfo()
    {
        // 用户手机号码查询
        String pwd = etPassWord.getText().toString().trim();
        if (TextUtils.isEmpty(pwd))
        {
            BToast.show(mActivity, "请输入密码");
            return;
        }
        else if (pwd.length() < 6 || pwd.length() > 10)
        {
            BToast.show(mActivity, "请输入6-10位字符密码");
            return;
        }else
        {
          UserRequest.getInstance().requestReSetPwd(ForgetPasswordNewPassWordActivity.this, mActivity, mPhoneNumber, pwd);
        }
    }


	@Override
	public void onTaskOver(HttpRequest request, HttpResponse response) {
		if (response.getState() == HttpTaskState.STATE_OK) {
			if (response.result != null) {
				if (response.result.getSuccess()) {	//密码重置成功
					LoginActivity.quitLogin(pUitl, mActivity);
					Intent intent = new Intent(mActivity, LoginActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
					mActivity.startActivity(intent);
					finish();
				}else{
					BToast.show(mActivity, response.result.errorMsg);
				}
			}else{
				BToast.show(mActivity, R.string.system_error);
			}
		}
	}
}
