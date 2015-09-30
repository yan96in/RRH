package com.cbkj.rrh.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cbkj.rrh.R;
import com.cbkj.rrh.adapter.TaskAdapter.TaskType;
import com.cbkj.rrh.bean.TaskBean;
import com.cbkj.rrh.bean.TaskBean.DataType;
import com.cbkj.rrh.bean.TaskReciverBean;
import com.cbkj.rrh.db.PreferenceUtil;
import com.cbkj.rrh.system.BGApp;
import com.cbkj.rrh.ui.TaskUtil;
import com.cbkj.rrh.ui.help.ShowNameCardListener;
import com.cbkj.rrh.ui.me.user.ScoreEvaluateActivity;
import com.cbkj.rrh.view.RoundImageView;
import com.cbkj.rrh.view.dialog.DialogFactory;


/**
 * @todo:订单列表适配器
 * @date:2015-7-23 上午9:31:14
 * @author:hg_liuzl@163.com
 */
public class OrderDetailAdapter extends KBaseAdapter implements OnClickListener {
	
	private int mType;
	private TaskBean mTaskBean;
	private PreferenceUtil pUtils = null;

	public OrderDetailAdapter(List<?> mList,TaskBean bean, Activity mActivity,int type,PreferenceUtil mPUtils) {
		super(mList, mActivity);
		this.mType = type;
		this.mTaskBean = bean;
		this.pUtils = mPUtils;
	}
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null)
		{
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_task_detail, parent, false);
			
			//top
			holder.ivTaskStatus = (ImageView) convertView.findViewById(R.id.iv_task_status);
			holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
			holder.rvImg = (RoundImageView) convertView.findViewById(R.id.riv_user);
			holder.tvPublishDate = (TextView) convertView.findViewById(R.id.tv_date);
			holder.tvMoney = (TextView) convertView.findViewById(R.id.tv_money);
			
			//bottom
			holder.vDivide = convertView.findViewById(R.id.v_divide);			
			
			holder.btnContact = (Button) convertView.findViewById(R.id.btn_contact);
			holder.btnSelectOne = (Button) convertView.findViewById(R.id.btn_select_one);
			holder.btnModifyOffer = (Button) convertView.findViewById(R.id.btn_modify_offer);
			
			holder.btnAffirmFinish = (Button) convertView.findViewById(R.id.btn_affirm_finish);
			holder.btnDelayPay = (Button) convertView.findViewById(R.id.btn_delay_pay);
			
			holder.btnHold = (Button) convertView.findViewById(R.id.btn_hold_one);

			holder.btnGrade = (Button) convertView.findViewById(R.id.btn_grade_score);
			
			holder.vDivide2 = convertView.findViewById(R.id.v2_divide);			
			holder.tvLeaveMessage = (TextView) convertView.findViewById(R.id.tv_leave_message);
			
			
			convertView.setTag(holder);
		} else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		final TaskReciverBean reciverBean = (TaskReciverBean) mList.get(position);
		
		BGApp.getInstance().setImageSqure(reciverBean.smallImg, holder.rvImg);
		
		holder.rvImg.setOnClickListener(new ShowNameCardListener(reciverBean, mActivity));
		holder.tvName.setText(reciverBean.nickName);
		holder.tvName.setOnClickListener(new ShowNameCardListener(reciverBean, mActivity));
		
		holder.btnContact.setBackgroundResource(mType == TaskBean.TASK_SENT_TAG?R.drawable.btn_contact_employee:R.drawable.btn_contact_employer);
		
		reciverBean.setMoney(mActivity,mType,holder.tvMoney);
		reciverBean.setTime(mActivity, holder.tvPublishDate);
		holder.vDivide.setVisibility(View.GONE);
		holder.vDivide2.setVisibility(View.GONE);
		holder.tvLeaveMessage.setVisibility(View.GONE);
		if(mType == TaskBean.TASK_SENT_TAG){	//我发的订单
			setSentTask(holder,reciverBean);
		}else if(mType == TaskBean.TASK_RECIVED_TAG){	//我接的单
			setReicveTask(holder,reciverBean);
		}
		return convertView;
	}
	
	/**
	 * 
	 * @todo:我发的单
	 * @date:2015-7-24 上午10:57:42
	 * @author:hg_liuzl@163.com
	 * @params:@param ivStatus
	 * @params:@param status
	 */
	private void setSentTask(ViewHolder holder,TaskReciverBean reciverBean) {
		holder.ivTaskStatus.setVisibility(View.GONE);
		switch (mTaskBean.status) {
		case TaskBean.SENT_TASK_HOLD:	//待定的
			holder.btnContact.setVisibility(View.VISIBLE);
			holder.btnContact.setOnClickListener(this);
			holder.btnContact.setTag(reciverBean);
			holder.btnSelectOne.setVisibility(View.VISIBLE);
			holder.btnSelectOne.setOnClickListener(this);
			holder.btnSelectOne.setTag(reciverBean);
			holder.btnModifyOffer.setVisibility(View.GONE);
			holder.btnHold.setVisibility(View.GONE);
			holder.btnAffirmFinish.setVisibility(View.GONE);
			holder.btnDelayPay.setVisibility(View.GONE);
			holder.btnGrade.setVisibility(View.GONE);
			break;
		case  TaskBean.SENT_TASK_FAIL://过期的
			holder.btnAffirmFinish.setVisibility(View.GONE);
			holder.btnSelectOne.setVisibility(View.GONE);
			holder.btnModifyOffer.setVisibility(View.GONE);
			holder.btnContact.setVisibility(View.GONE);
			holder.btnDelayPay.setVisibility(View.GONE);
			holder.btnGrade.setVisibility(View.GONE);
			break;
		case  TaskBean.SENT_TASK_WORKING://进行中的
			holder.btnAffirmFinish.setVisibility(View.GONE);
			holder.btnContact.setVisibility(View.VISIBLE);
			holder.btnContact.setOnClickListener(this);
			holder.btnContact.setTag(reciverBean);
			holder.btnModifyOffer.setVisibility(View.GONE);
			holder.btnDelayPay.setVisibility(View.GONE);
			holder.btnGrade.setVisibility(View.GONE);
			break;
		case  TaskBean.SENT_TASK_CHECK://验收的
			holder.btnAffirmFinish.setVisibility(View.VISIBLE);
			holder.btnAffirmFinish.setOnClickListener(this);
			holder.btnAffirmFinish.setTag(reciverBean);
			holder.btnContact.setVisibility(View.VISIBLE);
			holder.btnContact.setOnClickListener(this);
			holder.btnContact.setTag(reciverBean);
			holder.btnModifyOffer.setVisibility(View.GONE);
			holder.btnDelayPay.setVisibility(View.VISIBLE);
			holder.btnDelayPay.setOnClickListener(this);
			holder.btnDelayPay.setTag(reciverBean);
			holder.btnGrade.setVisibility(View.GONE);
			break;
		case  TaskBean.SENT_TASK_FINISH:	//完成
			holder.btnAffirmFinish.setVisibility(View.GONE);
			holder.btnContact.setVisibility(View.GONE);
			holder.btnModifyOffer.setVisibility(View.GONE);
			holder.btnDelayPay.setVisibility(View.GONE);
			holder.btnGrade.setVisibility(View.GONE);
			break;
		}
	}
	
	/**
	 * 
	 * @todo:我接的单
	 * @date:2015-8-5 上午10:21:02
	 * @author:hg_liuzl@163.com
	 * @params:@param holder
	 * @params:@param bean
	 */
	@SuppressLint("ResourceAsColor")
	private void setReicveTask(ViewHolder holder,TaskReciverBean bean) {
		
		switch (mTaskBean.status) {
		case  TaskBean.RECEIVE_TASK_HOLD:	//待定的
			holder.ivTaskStatus.setBackgroundResource(R.drawable.bg_task_status_hold);
			holder.btnAffirmFinish.setVisibility(View.GONE);
			holder.btnContact.setVisibility(View.VISIBLE);
			holder.btnContact.setOnClickListener(this);
			holder.btnContact.setTag(bean);
			holder.btnModifyOffer.setVisibility(View.VISIBLE);
			holder.btnModifyOffer.setOnClickListener(this);
			holder.btnModifyOffer.setTag(bean);

			holder.btnDelayPay.setVisibility(View.GONE);
			holder.btnGrade.setVisibility(View.GONE);
			break;
		case TaskBean.RECEIVE_TASK_SUCCESS:	//成功的
			holder.ivTaskStatus.setBackgroundResource(R.drawable.bg_task_status_success);
			holder.btnAffirmFinish.setVisibility(View.VISIBLE);
			holder.btnAffirmFinish.setOnClickListener(this);
			holder.btnAffirmFinish.setTag(bean);
			holder.btnAffirmFinish.setText("任务完成确认");
			holder.btnContact.setOnClickListener(this);
			holder.btnContact.setTag(bean);
			holder.btnModifyOffer.setVisibility(View.GONE);
			holder.btnDelayPay.setVisibility(View.GONE);
			holder.btnGrade.setVisibility(View.GONE);
			break;
		case TaskBean.RECEIVE_TASK_CHECK:	//验收中
			holder.ivTaskStatus.setBackgroundResource(R.drawable.bg_task_status_success);
			holder.btnAffirmFinish.setVisibility(View.GONE);
			holder.btnContact.setVisibility(View.VISIBLE);
			holder.btnContact.setOnClickListener(this);
			holder.btnContact.setTag(bean);
			holder.btnModifyOffer.setVisibility(View.GONE);
			holder.btnDelayPay.setVisibility(View.GONE);
			holder.btnGrade.setVisibility(View.GONE);
			break;
		case TaskBean.RECEIVE_TASK_FAIL://失败的
			holder.ivTaskStatus.setBackgroundResource(R.drawable.bg_task_status_fail);
			holder.btnAffirmFinish.setVisibility(View.GONE);
			holder.btnContact.setVisibility(View.GONE);
			holder.btnModifyOffer.setVisibility(View.GONE);
			holder.btnDelayPay.setVisibility(View.GONE);
			holder.btnGrade.setVisibility(View.GONE);
			break;
		case TaskBean.RECEIVE_TASK_FINISH://完成的
			holder.ivTaskStatus.setBackgroundResource(R.drawable.bg_task_status_finish);
			holder.btnAffirmFinish.setVisibility(View.GONE);
			holder.btnContact.setVisibility(View.GONE);
			holder.btnModifyOffer.setVisibility(View.GONE);
			holder.btnDelayPay.setVisibility(View.GONE);
			holder.btnGrade.setVisibility(mTaskBean.hasScore == 1?View.GONE:View.VISIBLE);
			holder.btnGrade.setOnClickListener(this);
			break;
		}

	}
	
	
	final class ViewHolder{
		RoundImageView rvImg;
		ImageView ivTaskStatus;
		TextView tvName,tvPublishDate,tvMoney,tvLeaveMessage;
		Button btnContact,btnSelectOne,btnAffirmFinish,btnDelayPay,btnHold,btnGrade,btnModifyOffer;
		View vDivide,vDivide2;
	}

	@Override
	public void onClick(View v) {
		final TaskReciverBean reciverBean = (TaskReciverBean) v.getTag();
		switch (v.getId()) {
		case R.id.btn_affirm_finish:	

			if (mType == TaskBean.TASK_SENT_TAG) {	//发单人确认完成任务
				TaskUtil.getInstance().affirmFinishTask(mActivity,TaskType.SENT_TASK,DataType.DATA_DETAIL,mTaskBean.userId,mTaskBean,this,reciverBean.userId);
			} else {	//接单人确认完成任务
				TaskUtil.getInstance().affirmFinishTask(mActivity,TaskType.RECIVE_TASK,DataType.DATA_DETAIL,reciverBean.userId,mTaskBean,this,mTaskBean.userId);
			}
			
			break;
		case R.id.btn_contact:	//联系人
			TaskUtil.getInstance().callContact(mActivity,mType != TaskBean.TASK_SENT_TAG?mTaskBean.telephone : reciverBean.telephone);
			break;
		case R.id.btn_grade_score:	//评价
			if (mType == TaskBean.TASK_SENT_TAG) {	//我发的单，去评论接单人
				
				ScoreEvaluateActivity.doScoreAction(mActivity, ScoreEvaluateActivity.KEY_SCORE_EMPLOYEE, mTaskBean.taskId, reciverBean.userId);
			} else {	//我接单的单，去评论雇主

				ScoreEvaluateActivity.doScoreAction(mActivity, ScoreEvaluateActivity.KEY_SCORE_EMPLOYER, mTaskBean.taskId, mTaskBean.userId);
			}
			break;
		case R.id.btn_select_one:	//选择一个接单人
//			TaskUtil.getInstance().affirmSelectOne(mActivity,mTaskBean,reciverBean);
			doSelectRecivedOne(reciverBean);
			break;
		case R.id.btn_modify_offer:	//修改价格
			TaskUtil.getInstance().modifyOffer(mActivity,reciverBean,this);
			break;
		case R.id.btn_delay_pay:	//订单延期支付
			TaskUtil.getInstance().doDelayPay(mActivity,DataType.DATA_DETAIL,mTaskBean,this);
			break;
		default:
			break;
		}
	}
	
	/**
	 * 
	 * @todo:选择接单人
	 * @date:2015-9-19 下午3:17:36
	 * @author:hg_liuzl@163.com
	 * @params:@param taskbean
	 * @params:@param reciverBean
	 */
	Dialog dialog = null;
	private void doSelectRecivedOne(final TaskReciverBean reciverBean) {
		if (pUtils.hasReadEmployerTerms()) {	//已经阅读了雇主条款
			TaskUtil.getInstance().affirmSelectOne(mActivity,mTaskBean,reciverBean);
		} else {
			dialog = DialogFactory.serviceTermDialog(mActivity, "雇主条款", mActivity.getResources().getString(R.string.employer_service_terms), "同意并选TA", new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dialog.dismiss();
					pUtils.setReadEmployerTerms(true);
					TaskUtil.getInstance().affirmSelectOne(mActivity,mTaskBean,reciverBean);
				}
			});
		}
	}
}
