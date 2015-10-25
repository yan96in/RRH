package com.cbkj.rrh.me.view;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.cbkj.rrh.R;
import com.cbkj.rrh.bean.ScoreBean;
import com.cbkj.rrh.bean.UserBean;
import com.cbkj.rrh.main.CBApp;
import com.cbkj.rrh.main.MessageReceiver;
import com.cbkj.rrh.main.base.BaseActivity;
import com.cbkj.rrh.me.more.SettingActivity;
import com.cbkj.rrh.net.http.HttpRequest;
import com.cbkj.rrh.net.http.HttpRequestAsyncTask.TaskListenerWithState;
import com.cbkj.rrh.net.http.HttpResponse;
import com.cbkj.rrh.net.http.HttpResponse.HttpTaskState;
import com.cbkj.rrh.net.http.HttpURL;
import com.cbkj.rrh.net.request.UserRequest;
import com.cbkj.rrh.others.utils.ImgUtils;
import com.cbkj.rrh.view.BToast;
import com.cbkj.rrh.view.CertificateView;
import com.cbkj.rrh.view.CommentView;
import com.cbkj.rrh.view.MyPopWindow;
import com.cbkj.rrh.view.MyPopWindow.IChangePositionCallback;
import com.cbkj.rrh.view.widget.MySelectItemView;
import com.cbkj.rrh.view.widget.SelectItemView;
import com.cbkj.rrh.view.RoundImageView;

/**
 * @todo:我的中心
 * @date:2015-4-13 下午3:24:14
 * @author:hg_liuzl@163.com
 */
public class MeActivity extends BaseActivity implements OnClickListener,TaskListenerWithState {
	/**编辑个人资料**/
	private ArrayList<String> imgList = new ArrayList<String>(); //存储图片查看器的图片地址
	private UserBean mUserBean = null;
	private RoundImageView rivUser;
	private ImageView ivSex,ivRemberTip;
	private TextView tvNick;
	private MySelectItemView sivApprove,sivAlipay;
	private SelectItemView sivScoreSelf;
	private LinearLayout llUserInfo;
	private CertificateView view;
	private MyPopWindow popWindow;
	private CommentView employerComment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_me_layout);
		mUserBean = pUitl.getUserBean();
		initView();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		ivRemberTip.setVisibility(pUitl.hasReadAllSetting()?View.GONE:View.VISIBLE);
		sivApprove.setIvTag(pUitl.hasReadApprove()?R.drawable.icon_next:R.drawable.icon_remenber_tip);
		setData();
		UserRequest.getInstance().requestUserInfo(this, this, pUitl.getUserID());
	}
	
	private void initView() {
		findViewById(R.id.fl_rignt).setOnClickListener(this);
		ivRemberTip = (ImageView) findViewById(R.id.iv_rember_tip);
		
		llUserInfo = (LinearLayout) findViewById(R.id.ll_user_info);
		llUserInfo.setOnClickListener(this);
		
		
		
		rivUser = (RoundImageView) findViewById(R.id.riv_user);
		//rivUser.setOnClickListener(this);
		
		tvNick = (TextView) findViewById(R.id.tv_nick);
		
		ivSex = (ImageView) findViewById(R.id.iv_sex);
		
		ImageView iv = (ImageView) findViewById(R.id.iv_next_img);
		iv.setVisibility(View.VISIBLE);
		
		popWindow = new MyPopWindow(mActivity,pUitl);
		popWindow.initWorkPosition(false);
		popWindow.setCallback(callback);
		
		sivApprove = (MySelectItemView) findViewById(R.id.siv_apply_sign);
		sivApprove.setOnClickListener(this);
		
		sivAlipay = (MySelectItemView) findViewById(R.id.siv_alipay_account);
		sivAlipay.setOnClickListener(this);
		
		view = new CertificateView(mActivity, View.VISIBLE,0,"我的证书");
		
		sivScoreSelf = (SelectItemView) findViewById(R.id.siv_score_self);
		sivScoreSelf.tvContent.setMaxLines(2);
		sivScoreSelf.setOnClickListener(this);
		sivScoreSelf.tvContent.setTextColor(getResources().getColor(R.color.gray));
	}
	
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);

	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.fl_rignt:
			intent = new Intent(mActivity, SettingActivity.class);
			startActivity(intent);
			break;
		case R.id.ll_user_info:
			intent = new Intent(mActivity, EditUserInfoActivity.class);
			startActivity(intent);
			break;
		case R.id.riv_user: // 查看大图
			ImgUtils.imageBrower(0, imgList, mActivity);
			break;
		case R.id.siv_apply_sign:		//接单认证
			if (mUserBean.employeeStatus == 3) { // 未认证
				BToast.show(mActivity, "已经认证接单人！");
			} else { // 已经认证
				intent = new Intent(mActivity, ApproveReciverActivity.class);
				intent.putExtra(UserBean.USER_IS_EMPLOYEE, mUserBean.employeeStatus);
				startActivity(intent);
				pUitl.setReadApprove(true);
				sendBroadcast(new Intent(MessageReceiver.MSG_RECEIVER_TAG));
			}
			break;
		case R.id.siv_alipay_account:	//支付宝账号
			intent = new Intent(mActivity,CheckUserActivity.class);
			startActivity(intent);
			break;	
			
		case R.id.siv_score_self:		//自我评价
			intent = new Intent(mActivity,EditSelfAssessmentActivity.class);
			intent.putExtra(EditSelfAssessmentActivity.SELF_ASSESSMENT_TYPE, EditSelfAssessmentActivity.EDIT_ASSESSMENT_KEY);
			startActivity(intent);
			break;
			
		default:
			break;
		}
	}


	
	/**
	 * 
	 * @todo:设置用户数据
	 * @date:2015-8-7 下午5:27:32
	 * @author:hg_liuzl@163.com
	 * @params:
	 */
	private void setData() {
		
		
		imgList.clear();
		imgList.add(mUserBean.bigImg);

		CBApp.getInstance().setImageSqure(mUserBean.smallImg, rivUser);
		
		tvNick.setText(mUserBean.nickName);
		
		ivSex.setBackgroundResource(mUserBean.gender == 0?R.drawable.icon_female:R.drawable.icon_male);
		
		popWindow.setPosition(mUserBean.position);
		
		switch (mUserBean.employeeStatus) {
		case 1:
			sivApprove.setContent("(待认证)");
			break;
		case 2:
			sivApprove.setContent("(审核中)");
			break;
		case 3:
			sivApprove.setContent("(已认证)");
			
			break;
		default:
			break;
		}
		sivApprove.tvContent.setTextColor(getResources().getColor(R.color.red));

		pUitl.setReadApprove(mUserBean.employeeStatus == 3?true:false);
		sivApprove.setIvTag(pUitl.hasReadApprove()?R.drawable.icon_next:R.drawable.icon_remenber_tip);
		
		sivAlipay.setContent(mUserBean.aliPayNo);
		
		view.setData(mUserBean.certificates, mUserBean.userId);

		sivScoreSelf = (SelectItemView) findViewById(R.id.siv_score_self);
		sivScoreSelf.setTvContent(mUserBean.intro);

		employerComment =  new CommentView(mActivity, "发单者对我的评价:",0, mUserBean.scores2);
		employerComment.setBGVisible(mUserBean.scores2 == null?View.GONE:View.VISIBLE);
//		
//		employeeComment =  new CommentView(mActivity, "接单人对我的评价:", mUserBean.scores);
//		employeeComment.setBGVisible(mUserBean.scores.size() == 0 ?View.GONE:View.VISIBLE);
		initRecivedScore();
	}
	
	/**接单者对我的评价*/
	private void initRecivedScore() {
		LinearLayout llComment = (LinearLayout) findViewById(R.id.ll_comment_recived);
		TextView tv;
		if (mUserBean.scores == null || mUserBean.scores.size() == 0) {
			llComment.setVisibility(View.GONE);
			return;
		}
		llComment.setVisibility(View.VISIBLE);
		for (int i = 0; i < mUserBean.scores.size(); i++) {
			final ScoreBean bean = mUserBean.scores.get(i);
			int location = i+1;
			switch (location) {
			case 1:
				tv = (TextView) mActivity.findViewById(R.id.tv_1);
				tv.setText(location+"."+bean.rulesName);
				tv = (TextView) mActivity.findViewById(R.id.tv_1_score);
				tv.setText(String.valueOf(bean.count)+"次");
				break;
			case 2:
				tv = (TextView) mActivity.findViewById(R.id.tv_2);
				tv.setText(location+"."+bean.rulesName);
				tv = (TextView) mActivity.findViewById(R.id.tv_2_score);
				tv.setText(String.valueOf(bean.count)+"次");
				break;

			case 3:
				tv = (TextView) mActivity.findViewById(R.id.tv_3);
				tv.setText(location+"."+bean.rulesName);
				tv = (TextView) mActivity.findViewById(R.id.tv_3_score);
				tv.setText(String.valueOf(bean.count)+"次");
				break;

			case 4:
				tv = (TextView) mActivity.findViewById(R.id.tv_4);
				tv.setText(location+"."+bean.rulesName);
				tv = (TextView) mActivity.findViewById(R.id.tv_4_score);
				tv.setText(String.valueOf(bean.count)+"次");
				break;

			default:
				break;
			}
		}

	}
	
	private IChangePositionCallback callback = new IChangePositionCallback() {
		@Override
		public void change(int position) {
			mUserBean.position = position;
			pUitl.setUserBean(mUserBean);
			UserRequest.getInstance().requestModifyUserInfo(MeActivity.this, mActivity, mUserBean);
		}
	};
	@Override
	public void onTaskOver(HttpRequest request, HttpResponse response) {

		if (response.getState() == HttpTaskState.STATE_OK) {
			if (request.getRequestUrl() == HttpURL.URL_MODIFY_USER && response.result != null) {	//修改用户的职业类型
				if (response.result.getSuccess()) {
					BToast.show(mActivity, "职业类型修改成功");
				} else {
					BToast.show(mActivity, response.result.errorMsg);
				}
			}else if (request.getRequestUrl() == HttpURL.URL_USER_PROFILE && response.result != null) {	//获取用户资料
				if (response.result.getSuccess()) {
					mUserBean = JSON.parseObject(response.result.getStrBody(), UserBean.class);
					pUitl.setUserBean(mUserBean);
					setData();
				} else {
					BToast.show(mActivity, response.result.errorMsg);
				}
			}
		}
	}
}
