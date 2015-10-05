package com.cbkj.rrh.main.account;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.cbkj.rrh.R;
import com.cbkj.rrh.main.base.BaseBackActivity;
import com.cbkj.rrh.net.http.HttpRequest;
import com.cbkj.rrh.net.http.HttpRequestAsyncTask.TaskListenerWithState;
import com.cbkj.rrh.net.http.HttpResponse;
import com.cbkj.rrh.net.http.HttpResponse.HttpTaskState;
import com.cbkj.rrh.net.request.UserRequest;
import com.cbkj.rrh.view.BToast;
import com.cbkj.rrh.view.dialog.DialogFactory;
import com.cbkj.rrh.view.widget.TitleBar;
import com.umeng.analytics.MobclickAgent;

/**
 * 
 * @todo: 修改密码
 * @date:2015-5-6 下午4:32:40
 * @author:hg_liuzl@163.com
 */
public class ModifyPasswordActivity extends BaseBackActivity implements TaskListenerWithState,OnClickListener
{
    private EditText m_oldPasswordEt     = null; // 原密码
    private EditText m_newPasswordEt     = null; // 新密码
    private EditText m_confirmPasswordEt = null; // 确认新密码
    private TitleBar titleBar = null;
    
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
        setContentView(R.layout.layout_modify_password);
        titleBar = new TitleBar(mActivity);
        titleBar.initAllBar("修改密码", "完成");
        titleBar.rightBtn.setOnClickListener(this);
        findView();
    }
 

    /**
     * 控件初始化方法
     */
    private void findView()
    {
        m_oldPasswordEt = (EditText) findViewById(R.id.modify_password_et_old_password);
        m_newPasswordEt = (EditText) findViewById(R.id.modify_password_et_new_password);
        m_confirmPasswordEt = (EditText) findViewById(R.id.modify_password_et_confirm_password);
    }
    /**
     * 检查用户信息方法
     */
    private void checkInfo()
    {
        // 原密码
    	String oldPassword = m_oldPasswordEt.getText().toString().trim();
    	String newPassword = m_newPasswordEt.getText().toString().trim();
        String confirmPassword = m_confirmPasswordEt.getText().toString().trim();
        if (TextUtils.isEmpty(oldPassword))
        {
            BToast.show(mActivity, "请输入原密码");
            return;
        }else if(TextUtils.isEmpty(newPassword)){
        	 BToast.show(mActivity, "请输入新密码");
             return;
        }else if(newPassword.length() < 6 && newPassword.length() > 10){
        	 BToast.show(mActivity, "新密码长度必须为6-10位");
             return;
        }else if(!newPassword.equals(confirmPassword)){
        	 BToast.show(mActivity, "两次密码输入不一致");
        	 return;
        }else{
        	UserRequest.getInstance().requestModifyPwd(this, this, pUitl.getAccountNumber(), oldPassword, newPassword);
        }
    }

	@Override
	public void onTaskOver(HttpRequest request, HttpResponse response) {
		if (response.getState() == HttpTaskState.STATE_OK) {
			if (response.result != null) {
				if (response.result.getSuccess()) {	//密码重置成功
					DialogFactory.singleDialog(mActivity, "密码修改成功", "点击重新登录", this);
				}else{
					BToast.show(mActivity, response.result.errorMsg);
				}
			}else{
				BToast.show(mActivity, R.string.system_error);
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_right:
			checkInfo();
			break;
		case R.id.tv_know:
			LoginActivity.quitLogin(pUitl, mActivity);
			Intent intent = new Intent(mActivity, LoginActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
			mActivity.startActivity(intent);
			finish();
			break;
		default:
			break;
		}
		
	}


}
