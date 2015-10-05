package com.cbkj.rrh.me.more;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.cbkj.rrh.R;
import com.cbkj.rrh.main.base.BaseBackActivity;
import com.cbkj.rrh.net.http.HttpRequest;
import com.cbkj.rrh.net.http.HttpRequestAsyncTask.TaskListenerWithState;
import com.cbkj.rrh.net.http.HttpResponse;
import com.cbkj.rrh.net.http.HttpResponse.HttpTaskState;
import com.cbkj.rrh.net.request.UserRequest;
import com.cbkj.rrh.others.utils.DeviceUtils;
import com.cbkj.rrh.view.BToast;
import com.cbkj.rrh.view.widget.TitleBar;
import com.umeng.analytics.MobclickAgent;


/**
 * 
 * @todo:意见反馈
 * @date:2015-8-10 下午2:28:41
 * @author:hg_liuzl@163.com
 */
public class FeedbackActivity extends BaseBackActivity implements TaskListenerWithState
{
    private EditText m_contentEt = null;  // 内容
    private EditText m_commact;//联系方式
    private TextView m_wordcountTv = null;  // 字数显示
    private String mContent;
    private String mCommact;
    
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
        setContentView(R.layout.user_layout_feedback);
        (new TitleBar(mActivity)).initTitleBar("意见反馈");
        findView();
    }
	/**
	 * 控件初始化方法
	 */
    private void findView()
    {
    	m_contentEt = (EditText) findViewById(R.id.feedback_edit_content);
    	m_commact = (EditText) findViewById(R.id.feedback_edit_commact);
        m_wordcountTv = (TextView) findViewById(R.id.feedback_tv_wordcount);
        
        m_contentEt.addTextChangedListener(new TextWatcher()
        {
            public void afterTextChanged(Editable paramEditable)
            {
            }

            public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
            {
            }

            public void onTextChanged(CharSequence s, int paramInt1, int paramInt2, int paramInt3)
            {
            	m_wordcountTv.setText(s.length()+"/200");
            }
        });
        
        // 确定按钮
        findViewById(R.id.feedback_btn_done).setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
            	checkContent();

            }
        });
        
        findViewById(R.id.tv_copy).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			//	DeviceUtils.copyText(FeedbackActivity.this, getResources().getString(R.string.app_qq));
			}
		});
    }
    
    /**
     * 
     * @todo:提交内容
     * @date:2015-5-19 上午10:59:14
     * @author:hg_liuzl@163.com
     * @params:
     */
    private void checkContent() {
    	mContent = m_contentEt.getText().toString().trim();
    	mCommact = m_commact.getText().toString().trim();
    	if(TextUtils.isEmpty(mContent)){
    		 BToast.show(mActivity, "请输入您的反馈意见!");
             return;
    	}else if(TextUtils.isEmpty(mCommact)){
    		BToast.show(mActivity, "请输入您的联系方式!");
    		return;
    	}
    	UserRequest.getInstance().requestFeedback(this, mActivity, pUitl.getUserID(), 0, mCommact, mContent);
	}

	@Override
	public void onTaskOver(HttpRequest request, HttpResponse response) {
		if(response.getState() == HttpTaskState.STATE_OK){
			if (null != response.result && response.result.getSuccess()) {
				BToast.show(mActivity, "谢谢您的反馈");
				finish();
			}else{
				BToast.show(mActivity, response.result.getErrorMsg());
			}
		}
	}
}
