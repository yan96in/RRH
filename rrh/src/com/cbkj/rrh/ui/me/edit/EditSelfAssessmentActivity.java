package com.cbkj.rrh.ui.me.edit;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

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
 * @todo:编辑自我评价
 * @date:2015-8-10 上午11:41:30
 * @author:hg_liuzl@163.com
 */
public class EditSelfAssessmentActivity extends BaseBackActivity implements TaskListenerWithState
{
	/** 正文内容**/
	public static final String SELF_ASSESSMENT_KEY = "self_assessment_key";
	/**自我评价**/
	public static final String SELF_ASSESSMENT_TYPE = "self_assessment_type";
	/**编辑自我评价*/
	public static final int EDIT_ASSESSMENT_KEY = 1;
	/**查看自我评价*/
	public static final int LOOK_ASSESSMENT_KEY = 2;
	
	private UserBean userBean;
    private EditText myEditView;
    private TextView tvAssessment;
    private int assessmentType = 1;
    /**自我评价内容**/
    private String asessment = "";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_self_assessment_layout);
       
        userBean = pUitl.getUserBean();
        
        assessmentType = getIntent().getIntExtra(SELF_ASSESSMENT_TYPE, 1);

        asessment = getIntent().getStringExtra(SELF_ASSESSMENT_KEY);

        findView();
    }

	/**
	 * 控件初始化方法
	 */
    private void findView()
    {
    	LinearLayout svETAssmessment = (LinearLayout) findViewById(R.id.sl_et_assessment);
    	ScrollView svTvAssmessment = (ScrollView) findViewById(R.id.sl_tv_assessment);
        TitleBar mTitleBar = new TitleBar(mActivity);
        if (assessmentType == EDIT_ASSESSMENT_KEY) {
        	
        	svETAssmessment.setVisibility(View.VISIBLE);
        	svTvAssmessment.setVisibility(View.GONE);
        	
        	myEditView = (EditText) findViewById(R.id.et_self_assessment);
        	mTitleBar.initAllBar("编辑自我评价", "保存");
        	mTitleBar.rightBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					save();
				}
			});
        	
        	if (!TextUtils.isEmpty(userBean.intro))
        	{
        		myEditView.setText(userBean.intro);
        		myEditView.setSelection(userBean.intro.length());
        	}

		} else {
			mTitleBar.initTitleBar("TA的自我评价");
			
			svETAssmessment.setVisibility(View.GONE);
        	svTvAssmessment.setVisibility(View.VISIBLE);
			
			tvAssessment = (TextView) findViewById(R.id.tv_assessment);
			tvAssessment.setText(asessment);
		}
    	

   }

    /***
     * 
     * @todo:保存内容
     * @date:2015-9-23 下午5:18:01
     * @author:hg_liuzl@163.com
     * @params:
     */
    private  void save() {
    	asessment = myEditView.getText().toString().trim();
          if (TextUtils.isEmpty(asessment)){
        	  
              BToast.show(mActivity, "请填写自我评价");
              return;
              
          }else
          {
        	  userBean.intro = asessment;
        	  UserRequest.getInstance().requestModifyUserInfo(this, mActivity, userBean);
          }
	}
    
    public void submitInfo(View v) {
    	save();
	}


	@Override
	public void onTaskOver(HttpRequest request, HttpResponse response) {
		if (response.getState() == HttpTaskState.STATE_OK && null!=response.result ) {
				if (response.result.getSuccess()) {
					pUitl.setUserBean(userBean);
					BToast.show(mActivity, "保存成功");
					finish();
				}else{
					BToast.show(mActivity, response.result.getErrorMsg());
				}
			}else{	//系统出错
				BToast.show(mActivity,R.string.system_error);
			}
		} 
}
