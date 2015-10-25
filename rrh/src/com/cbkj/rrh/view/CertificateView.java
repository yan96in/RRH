package com.cbkj.rrh.view;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cbkj.rrh.R;
import com.cbkj.rrh.bean.CertificationBean;
import com.cbkj.rrh.bean.UserBean;
import com.cbkj.rrh.main.CBApp;
import com.cbkj.rrh.me.view.MyCerfiticateActivity;

/**
 * @todo:我的证书View
 * @date:2015-8-26 下午7:33:56
 * @author:hg_liuzl@163.com
 */
public class CertificateView implements OnClickListener {
	
	private Activity mActivity;
	private String mUserId;
	private LinearLayout ll_certificate;
	
	public CertificateView(Activity activity,int titleImgVisible,int bgResource,String title) {
		this.mActivity = activity;
		initView(activity,titleImgVisible,bgResource,title);
	}

	public CertificateView(Activity activity,int titleImgVisible,String title) {
		this.mActivity = activity;
		initView(activity,titleImgVisible,title);
	}
	
	private void initView(final Activity mActivity,int visible,String title) {
		initView(mActivity, visible, -1, title);
	}
	
	private void initView(final Activity mActivity,int visible,int bgResource,String title) {
		
		ll_certificate = (LinearLayout) mActivity.findViewById(R.id.ll_certificate);
		ll_certificate.setOnClickListener(this);
		if (bgResource !=-1) {
			ll_certificate.setBackgroundResource(bgResource);
		}
		
		ImageView ivTitleImg = (ImageView) mActivity.findViewById(R.id.iv_certificate_title_img);
		ivTitleImg.setVisibility(visible);
		
		TextView tvTitle = (TextView) mActivity.findViewById(R.id.tv_certificate_title_text);
		tvTitle.setText(title);
	}

	public void setData(final List<CertificationBean> beans,String userId) {
		this.mUserId = userId;
		if (null == beans || beans.size() == 0) {
			return;
		}
		
		ImageView iv = null;
		for (int i = 0; i < beans.size(); i++) {
			final CertificationBean bean = beans.get(i);
			switch (i) {
			case 0:
				iv = (ImageView) mActivity.findViewById(R.id.iv_one);
				CBApp.getInstance().setImageSqure(bean.smallImg, iv);
				iv.setVisibility(TextUtils.isEmpty(bean.smallImg)?View.INVISIBLE:View.VISIBLE);
				break;
			case 1:
				iv = (ImageView) mActivity.findViewById(R.id.iv_two);
				CBApp.getInstance().setImageSqure(bean.smallImg, iv);
				iv.setVisibility(TextUtils.isEmpty(bean.smallImg)?View.INVISIBLE:View.VISIBLE);
				break;

			default:
				break;
			}
		}
	}
	
	
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(mActivity,MyCerfiticateActivity.class);
		intent.putExtra(UserBean.USER_ID, mUserId);
		mActivity.startActivity(intent);
	}
	
	
}
