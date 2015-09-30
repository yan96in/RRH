package com.cbkj.rrh.ui.me.edit;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.cbkj.rrh.R;
import com.cbkj.rrh.bean.UserBean;
import com.cbkj.rrh.net.http.HttpRequest;
import com.cbkj.rrh.net.http.HttpRequestAsyncTask.TaskListenerWithState;
import com.cbkj.rrh.net.http.HttpResponse;
import com.cbkj.rrh.net.http.HttpResponse.HttpTaskState;
import com.cbkj.rrh.net.request.UserRequest;
import com.cbkj.rrh.ui.base.BaseBackActivity;
import com.cbkj.rrh.view.BToast;
import com.cbkj.rrh.widget.TitleBar;
/**
 * @todo:编辑支付宝账号
 * @date:2015-8-10 上午11:41:30
 * @author:hg_liuzl@163.com
 */
public class EditAlipayActivity extends BaseBackActivity implements TaskListenerWithState
{
    private UserBean mUserBean;
    private EditText etAliNo;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_alipay_layout);
        TitleBar mTitleBar = new TitleBar(mActivity);
        mTitleBar.initAllBar("编辑支付宝账号");
        mUserBean = pUitl.getUserBean();
        findView();
    }

	/**
	 * 控件初始化方法
	 */
    private void findView()
    {
    	etAliNo = (EditText) findViewById(R.id.etv_alipay_no);
    	if (!TextUtils.isEmpty(mUserBean.aliPayNo))
        {
    		etAliNo.setText(mUserBean.aliPayNo);
    		etAliNo.setSelection(mUserBean.aliPayNo.length());
        }
   }

    public  void submitAlipayNo(View v) {
    	String alipayNo = etAliNo.getText().toString();
          if (TextUtils.isEmpty(alipayNo)){
              BToast.show(mActivity, "请填写支付宝账号");
              return;
          }else{
        	  mUserBean.aliPayNo = alipayNo;
        	  UserRequest.getInstance().requestModifyAlipay(this, mActivity, alipayNo, pUitl.getUserID(),pUitl.getAccountPassword());
          }
	}
    


	@Override
	public void onTaskOver(HttpRequest request, HttpResponse response) {
		if (response.getState() == HttpTaskState.STATE_OK && null!=response.result ) {
				if (response.result.getSuccess()) {
					pUitl.setUserBean(mUserBean);
					finish();
				}else{
					BToast.show(mActivity, response.result.getErrorMsg());
				}
			}else{	//系统出错
				BToast.show(mActivity,R.string.system_error);
			}
		} 
}
