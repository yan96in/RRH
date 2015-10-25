package com.cbkj.rrh.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cbkj.rrh.R;
import com.cbkj.rrh.bean.TaskBean;
import com.cbkj.rrh.db.PreferenceUtil;
import com.cbkj.rrh.main.CBApp;
import com.cbkj.rrh.main.base.KBaseAdapter;
import com.cbkj.rrh.main.help.ShowNameCardListener;
import com.cbkj.rrh.others.utils.SharePostUtils;
import com.cbkj.rrh.view.RoundImageView;


/**
 * @todo:订单列表适配器
 * @date:2015-7-23 上午9:31:14
 * @author:hg_liuzl@163.com
 */
public class TaskAdapter extends KBaseAdapter implements OnClickListener {
	
	private TaskType mType;
	private SharePostUtils mShare;
	private PreferenceUtil pUtil;
	

	
	/***订单类型*/
	public enum TaskType{
		COMMON_TASK,	//普通的订单列表
		SENT_TASK, //我发出的订单
		RECIVE_TASK, //我收到的订单
		EMPLOYER_TASK	//雇主的单
	}

	public TaskAdapter(List<?> mList, Activity mActivity,TaskType type,PreferenceUtil p) {
		super(mList, mActivity);
		this.mType = type;
		this.pUtil = p;
	}
	
	public TaskAdapter(List<?> mList, Activity mActivity,TaskType type,SharePostUtils share,PreferenceUtil p) {
		super(mList, mActivity);
		this.mType = type;
		this.mShare = share;
		this.pUtil = p;
	}
	
	public TaskAdapter(List<?> mList, Activity mActivity,TaskType type) {
		super(mList, mActivity);
		this.mType = type;
	}

	public void removeData(TaskBean bean) {
		this.mList.remove(bean);
		notifyDataSetChanged();

	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null)
		{
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_task, parent, false);
			
			//top
			holder.ivTaskStatus = (ImageView) convertView.findViewById(R.id.iv_task_status);
			holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
			holder.rvImg = (RoundImageView) convertView.findViewById(R.id.riv_user);
			holder.tvPublishDate = (TextView) convertView.findViewById(R.id.tv_date);
			holder.tvMoney = (TextView) convertView.findViewById(R.id.tv_money);
			
			//center
			holder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
			holder.btnRecive = (Button) convertView.findViewById(R.id.btn_reciver);
			
			//bottom
			holder.vDivide = convertView.findViewById(R.id.v_divide);			
			
			holder.tvEndDate = (TextView) convertView.findViewById(R.id.tv_enddate);
			holder.tvShare = (TextView) convertView.findViewById(R.id.tv_share);
			
			holder.btnSelectReciver = (Button) convertView.findViewById(R.id.btn_select_reciver);
			
			holder.btnRedit = (Button) convertView.findViewById(R.id.btn_redit);
			
			holder.btnContact = (Button) convertView.findViewById(R.id.btn_contact);
			holder.btnCancleOrder = (Button) convertView.findViewById(R.id.btn_cancel_order);
			holder.btnDelayPay = (Button) convertView.findViewById(R.id.btn_delay_pay);
			holder.btnAffirmFinish = (Button) convertView.findViewById(R.id.btn_affirm_finish);
			holder.btnGrade = (Button) convertView.findViewById(R.id.btn_grade_score);

			
			holder.vDivide2 = convertView.findViewById(R.id.v2_divide);			
			holder.tvLeaveMessage = (TextView) convertView.findViewById(R.id.tv_leave_message);
			
			convertView.setTag(holder);
		} else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		final TaskBean bean = (TaskBean) mList.get(position);
		
		CBApp.getInstance().setImageSqure(bean.smallImg, holder.rvImg);
		
		holder.rvImg.setOnClickListener(new ShowNameCardListener(bean, mActivity));
		
		holder.tvName.setText(bean.nickName);
		holder.tvName.setOnClickListener(new ShowNameCardListener(bean, mActivity));
		
		bean.setPublishTime(mActivity, holder.tvPublishDate);
		
		bean.setMoney(mActivity, holder.tvMoney);
		
		holder.tvContent.setText(bean.content);
		
		bean.setImageStatus(holder.ivTaskStatus, mType);
		
		holder.tvLeaveMessage.setText(bean.remark);
		holder.tvLeaveMessage.setVisibility(View.VISIBLE);
		holder.tvLeaveMessage.setTextColor(mActivity.getResources().getColor(R.color.gray));
		
		if(mType == TaskType.COMMON_TASK){	//普通的订单
			setListTask(holder, bean);
		}else if(mType == TaskType.SENT_TASK){	//我发的订单
			bean.setUpdateTime(mActivity, holder.tvPublishDate);
			setSentTask(holder,bean);
		}else if(mType == TaskType.RECIVE_TASK){	//我接的单
			bean.setUpdateTime(mActivity, holder.tvPublishDate);
			holder.btnCancleOrder.setVisibility(View.GONE);
			setReicveTask(holder,bean);
		}else if(mType == TaskType.EMPLOYER_TASK){	//雇主的单
			holder.rvImg.setEnabled(false);
			holder.tvName.setEnabled(false);
			setEmployerTask(holder,bean);
		}
		return convertView;
	}
	
	
	/**
	 * 
	 * @todo:普通任务列表
	 * @date:2015-8-5 上午10:21:32
	 * @author:hg_liuzl@163.com
	 * @params:
	 */
	private void setListTask(ViewHolder holder,TaskBean bean) {
		holder.btnRecive.setTag(bean);
		holder.btnRecive.setOnClickListener(this);
		holder.btnRecive.setVisibility(View.VISIBLE);
		bean.setEndTime(mActivity, holder.tvEndDate);
		if (bean.userId.equals(pUtil.getUserID())) {	//如果是当前用户
			holder.btnRecive.setText("选择接单人");
			holder.tvEndDate.setVisibility(View.VISIBLE);
			holder.btnRecive.setBackgroundResource(R.drawable.btn_task_hold_selector);
		}else{
			if (bean.commonStatus == TaskBean.COMMON_TASK_HOLD) {
				holder.btnRecive.setText("我要接单");
				holder.btnRecive.setBackgroundResource(R.drawable.btn_has_order_selector);
				holder.tvEndDate.setVisibility(View.VISIBLE);
			} else{
				holder.btnRecive.setText("已被接单");
				holder.btnRecive.setBackgroundResource(R.drawable.btn_order_recived);
				holder.tvEndDate.setVisibility(View.GONE);
			}
		}
		holder.btnRecive.setPadding(15, 7, 15, 7);
		holder.tvShare.setTag(bean);
		holder.tvShare.setOnClickListener(this);
		holder.tvShare.setVisibility(View.VISIBLE);
		holder.vDivide.setVisibility(View.GONE);
		holder.vDivide2.setVisibility(View.GONE);
		holder.tvLeaveMessage.setVisibility(View.GONE);
	}
	
	
	/**
	 * 
	 * @todo:我发的单
	 * @date:2015-7-24 上午10:57:42
	 * @author:hg_liuzl@163.com
	 * @params:@param ivStatus
	 * @params:@param status
	 */
	private void setSentTask(ViewHolder holder,TaskBean bean) {
		holder.btnGrade.setVisibility(View.GONE);
		holder.btnGrade.setText("评论接单人");
		
		switch (bean.status) {
		case TaskBean.SENT_TASK_HOLD:	//待定的
			holder.btnSelectReciver.setVisibility(View.VISIBLE);
			holder.btnSelectReciver.setOnClickListener(this);
			holder.btnSelectReciver.setTag(bean);
			
			holder.btnRedit.setVisibility(View.GONE);
			holder.btnAffirmFinish.setVisibility(View.GONE);
			holder.btnContact.setVisibility(View.GONE);
			holder.btnDelayPay.setVisibility(View.GONE);
			holder.vDivide.setVisibility(View.VISIBLE);
//			holder.tvLeaveMessage.setVisibility(View.GONE);
			break;
		case TaskBean.SENT_TASK_FAIL://过期的
			holder.btnSelectReciver.setVisibility(View.GONE);
			
			holder.btnRedit.setVisibility(View.VISIBLE);
			holder.btnRedit.setOnClickListener(this);
			holder.btnRedit.setTag(bean);
			
			holder.btnAffirmFinish.setVisibility(View.GONE);
			holder.btnContact.setVisibility(View.GONE);
			holder.btnDelayPay.setVisibility(View.GONE);
			holder.vDivide.setVisibility(View.VISIBLE);
			holder.tvLeaveMessage.setTextColor(mActivity.getResources().getColor(R.color.red));
			break;
		case TaskBean.SENT_TASK_WORKING://进行中的
			holder.btnSelectReciver.setVisibility(View.GONE);
			holder.btnRedit.setVisibility(View.GONE);
			holder.btnAffirmFinish.setVisibility(View.GONE);
			holder.btnContact.setVisibility(View.VISIBLE);
			holder.btnContact.setOnClickListener(this);
			holder.btnContact.setTag(bean);
			holder.btnContact.setBackgroundResource(R.drawable.btn_contact_employee);
			holder.btnDelayPay.setVisibility(View.GONE);
			holder.vDivide.setVisibility(View.VISIBLE);
			break;
		case TaskBean.SENT_TASK_CHECK://验收中
			holder.btnSelectReciver.setVisibility(View.GONE);
			holder.btnRedit.setVisibility(View.GONE);
			
			holder.btnAffirmFinish.setVisibility(View.VISIBLE);
			holder.btnAffirmFinish.setOnClickListener(this);
			holder.btnAffirmFinish.setTag(bean);
			
			holder.btnContact.setVisibility(View.VISIBLE);
			holder.btnContact.setOnClickListener(this);
			holder.btnContact.setTag(bean);
			holder.btnContact.setBackgroundResource(R.drawable.btn_contact_employee);
			holder.btnDelayPay.setVisibility(View.VISIBLE);
			holder.btnDelayPay.setOnClickListener(this);
			holder.btnDelayPay.setTag(bean);
			holder.vDivide.setVisibility(View.VISIBLE);
			break;
		case TaskBean.SENT_TASK_FINISH:	//完成
			holder.btnSelectReciver.setVisibility(View.GONE);
			holder.btnRedit.setVisibility(View.GONE);
			holder.btnAffirmFinish.setVisibility(View.GONE);
			holder.btnContact.setVisibility(View.GONE);
			holder.btnDelayPay.setVisibility(View.GONE);
			holder.vDivide.setVisibility(View.GONE);
			holder.tvLeaveMessage.setText(bean.remark);
			if (bean.hasScore == 1) {
				holder.btnGrade.setVisibility(View.GONE);
				holder.vDivide.setVisibility(View.GONE);
			} else {
				holder.btnGrade.setVisibility(View.VISIBLE);
				holder.vDivide.setVisibility(View.VISIBLE);
				holder.btnGrade.setOnClickListener(this);
				holder.btnGrade.setTag(bean);
			}
			break;
		default:	//待定的
			holder.btnSelectReciver.setVisibility(View.VISIBLE);
			holder.btnSelectReciver.setOnClickListener(this);
			holder.btnRedit.setVisibility(View.GONE);
			holder.btnAffirmFinish.setVisibility(View.GONE);
			holder.btnContact.setVisibility(View.GONE);
			holder.btnDelayPay.setVisibility(View.GONE);
			holder.vDivide.setVisibility(View.VISIBLE);
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
	private void setReicveTask(ViewHolder holder,TaskBean bean) {
		switch (bean.status) {
		case TaskBean.RECEIVE_TASK_HOLD:	//待定的
			holder.btnSelectReciver.setVisibility(View.GONE);
			holder.btnRedit.setVisibility(View.GONE);
			holder.btnAffirmFinish.setVisibility(View.GONE);
			holder.btnContact.setVisibility(View.VISIBLE);
			holder.btnContact.setOnClickListener(this);
			holder.btnContact.setTag(bean);
			holder.btnCancleOrder.setVisibility(View.VISIBLE);
			holder.btnCancleOrder.setOnClickListener(this);
			holder.btnCancleOrder.setTag(bean);
			holder.btnContact.setBackgroundResource(R.drawable.btn_contact_employer);
			holder.btnDelayPay.setVisibility(View.GONE);
			holder.vDivide.setVisibility(View.VISIBLE);
			holder.btnGrade.setVisibility(View.GONE);
			break;
		case TaskBean.RECEIVE_TASK_SUCCESS:	//成功的
			holder.btnSelectReciver.setVisibility(View.GONE);
			holder.btnRedit.setVisibility(View.GONE);
			holder.btnAffirmFinish.setVisibility(View.VISIBLE);
			holder.btnAffirmFinish.setOnClickListener(this);
			holder.btnAffirmFinish.setText("任务完成确认");
			holder.btnAffirmFinish.setTag(bean);
			
			holder.btnContact.setVisibility(View.VISIBLE);
			holder.btnContact.setOnClickListener(this);
			holder.btnContact.setTag(bean);
			holder.btnContact.setBackgroundResource(R.drawable.btn_contact_employer);
			holder.btnDelayPay.setVisibility(View.GONE);
			holder.btnGrade.setVisibility(View.GONE);
			holder.vDivide.setVisibility(View.VISIBLE);
			break;
		case TaskBean.RECEIVE_TASK_CHECK:	//验收中
			holder.btnSelectReciver.setVisibility(View.GONE);
			holder.btnRedit.setVisibility(View.GONE);
			
			holder.btnAffirmFinish.setVisibility(View.GONE);
			
			holder.btnContact.setVisibility(View.VISIBLE);
			holder.btnContact.setOnClickListener(this);
			holder.btnContact.setTag(bean);
			holder.btnContact.setBackgroundResource(R.drawable.btn_contact_employer);
			holder.btnDelayPay.setVisibility(View.GONE);
			holder.btnGrade.setVisibility(View.GONE);
			holder.vDivide.setVisibility(View.VISIBLE);
			break;
		case TaskBean.RECEIVE_TASK_FAIL://失败的
			holder.btnSelectReciver.setVisibility(View.GONE);
			holder.btnRedit.setVisibility(View.GONE);
			holder.btnAffirmFinish.setVisibility(View.GONE);
			holder.btnContact.setVisibility(View.GONE);
			holder.btnDelayPay.setVisibility(View.GONE);
			holder.btnGrade.setVisibility(View.GONE);
			holder.tvLeaveMessage.setVisibility(View.VISIBLE);
			holder.tvLeaveMessage.setText(bean.remark);
			holder.tvLeaveMessage.setTextColor(mActivity.getResources().getColor(R.color.red));
			holder.vDivide.setVisibility(View.GONE);
			break;
		case TaskBean.RECEIVE_TASK_FINISH://完成的
			holder.btnSelectReciver.setVisibility(View.GONE);
			holder.btnRedit.setVisibility(View.GONE);
			holder.btnAffirmFinish.setVisibility(View.GONE);
			holder.btnContact.setVisibility(View.GONE);
			holder.btnDelayPay.setVisibility(View.GONE);
			holder.btnGrade.setOnClickListener(this);
			holder.btnGrade.setText("评论雇主");
			holder.btnGrade.setTag(bean);
			holder.tvLeaveMessage.setText(bean.remark);
			if (bean.hasScore == 1) {
				holder.btnGrade.setVisibility(View.GONE);
				holder.vDivide.setVisibility(View.GONE);
			} else {
				holder.btnGrade.setVisibility(View.VISIBLE);
				holder.vDivide.setVisibility(View.VISIBLE);
			}
			break;
		default:
			holder.btnSelectReciver.setVisibility(View.GONE);
			holder.btnRedit.setVisibility(View.GONE);
			holder.btnAffirmFinish.setVisibility(View.GONE);
			holder.btnContact.setVisibility(View.VISIBLE);
			holder.btnSelectReciver.setOnClickListener(this);
			holder.btnDelayPay.setVisibility(View.GONE);
			holder.btnGrade.setVisibility(View.GONE);
			holder.vDivide.setVisibility(View.VISIBLE);
			break;
		}
	}
	
	/**
	 * 
	 * @todo:雇主的单
	 * @date:2015-9-4 下午5:33:08
	 * @author:hg_liuzl@163.com
	 * @params:@param holder
	 * @params:@param bean
	 */
	private void setEmployerTask(ViewHolder holder,TaskBean bean) {
			holder.vDivide.setVisibility(View.GONE);
	}
	
	final class ViewHolder{
		RoundImageView rvImg;
		ImageView ivTaskStatus;
		TextView tvName,tvPublishDate,tvMoney,tvContent,tvEndDate,tvShare,tvLeaveMessage;
		Button btnRecive,btnSelectReciver,btnRedit,btnAffirmFinish,btnContact,btnDelayPay,btnGrade,btnCancleOrder;
		View vDivide,vDivide2;
	}

	@Override
	public void onClick(View v) {
//		TaskBean currentBean = (TaskBean) v.getTag();
//		switch (v.getId()) {
//		case R.id.btn_reciver:	//我要接单
//			if (currentBean.userId.equals(pUtil.getUserID())) {	//如果ID一样，就是自己发的单
//				OrderDetailActivity.goToDetail(mActivity, TaskBean.TASK_SENT_TAG, currentBean.taskId);
//			} else {
//				TaskRecivedActivity.goTaskDetail(mActivity, currentBean.taskId);
//			}
//			break;
//			
//		case R.id.tv_share:	//分享
//			currentBean.doShare(mShare);
//			break;
//			
//		case R.id.btn_affirm_finish://任务完成确认
//			if (mType == TaskType.SENT_TASK) {	//发单人确认完成任务
//				TaskUtil.getInstance().affirmFinishTask(mActivity,TaskType.SENT_TASK,DataType.DATA_LIST,pUtil.getUserID(),currentBean,this,currentBean.partnerId);
//			} else {	//接单人确认完成任务
//				TaskUtil.getInstance().affirmFinishTask(mActivity,TaskType.RECIVE_TASK,DataType.DATA_LIST,pUtil.getUserID(),currentBean,this,currentBean.userId);
//			}
//			break;
//		case R.id.btn_select_reciver: //选择接单人
//				OrderDetailActivity.goToDetail(mActivity, TaskBean.TASK_SENT_TAG, currentBean);
//			break;
//
//		case R.id.btn_contact:	//联系人//如果是我是发单人的情况，要打接单人的电话号码，如果我是接单人的情况，要拨打发单人的电话
//			TaskUtil.getInstance().callContact(mActivity,mType == TaskType.SENT_TASK?currentBean.telPartner:currentBean.telephone);
//			break;
//			
//		case R.id.btn_grade_score://评分
//			if (mType == TaskType.SENT_TASK) {
//				ScoreEvaluateActivity.doScoreAction(mActivity, ScoreEvaluateActivity.KEY_SCORE_EMPLOYEE, currentBean.taskId, currentBean.partnerId);
//			} else {
//				ScoreEvaluateActivity.doScoreAction(mActivity, ScoreEvaluateActivity.KEY_SCORE_EMPLOYER, currentBean.taskId, currentBean.userId);
//			}
//			currentBean.hasScore = 1;
//			notifyDataSetChanged();
//			break;
//		case R.id.btn_redit:	    //重新编辑
//			TaskUtil.getInstance().doReditTask(mActivity, currentBean);
//			break;
//		case R.id.btn_delay_pay:	//延期付款
//			TaskUtil.getInstance().doDelayPay(mActivity,DataType.DATA_LIST,currentBean,this);
//			break;
//		case R.id.btn_cancel_order://取消接单
//			TaskUtil.getInstance().doCancelTaskRecived(mActivity, DataType.DATA_LIST, currentBean, pUtil.getUserID(), this);
//		default:
//			break;
//		}
		
	}
}
