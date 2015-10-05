package com.cbkj.rrh.main.help;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

import com.cbkj.rrh.R;
import com.cbkj.rrh.bean.TaskBean;
import com.cbkj.rrh.bean.TaskReciverBean;
import com.cbkj.rrh.me.user.ReceiverNameCardActivity;
import com.cbkj.rrh.me.user.SentterNameCardActivity;

/**
 * @todo:名片展示事件
 * @date:2015-1-22 上午11:38:30
 * @author:hg_liuzl@163.com
 */
public class ShowNameCardListener implements OnClickListener {
	private Object object;
	private Activity mActivity;
	public ShowNameCardListener(Object object,Activity activity){
		this.object = object;
		this.mActivity = activity;
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.riv_user:
		case R.id.tv_name:
			if(object instanceof TaskBean){
				TaskBean mTaskBean = (TaskBean) object;
				SentterNameCardActivity.lookNameCard(mActivity, mTaskBean.userId);
			}else if(object instanceof TaskReciverBean){
				TaskReciverBean reciveBean = (TaskReciverBean) object;
				ReceiverNameCardActivity.lookNameCard(mActivity, reciveBean.userId);
			}
			break;
		default:
			break;
		}
	}
}
