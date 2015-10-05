package com.cbkj.rrh.bean;

import java.io.Serializable;

import android.app.Activity;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.cbkj.rrh.R;
import com.cbkj.rrh.others.utils.ToolUtils;

/**
 * @todo:接单人列表
 * @date:2015-8-19 下午5:35:14
 * @author:hg_liuzl@163.com
 */
public class TaskReciverBean implements Serializable{
	
	/**接单人实体类**/
	public static final String KEY_RECIVER_BEAN = "key_reciver_bean";
	
	/**
	 * @todo:TODO
	 * @date:2015-8-19 下午5:46:52
	 * @author:hg_liuzl@163.com
	 */
	private static final long serialVersionUID = 1L;
	
	
	public String taskId; //	任务Id	string	Y	任务Id
	public String userId;//	用户Id	string	Y	用户Id
	public String nickName;//	昵称	string	Y	昵称
	public String smallImg;//	小头像	string	Y	小头像后缀
	public String charges;//	预算	float	Y	发单人出的大概预算
	public String telephone;//	联系电话	string	Y	发单人留下的联系电话
	public String created;	//接单时间
	
	/**
	 * 
	 * @todo:格式化金额
	 * @date:2015-8-20 下午2:58:50
	 * @author:hg_liuzl@163.com
	 * @params:@param mActivity
	 * @params:@param tvMoney
	 */
	public void setMoney(Activity mActivity,int taskType,TextView tvMoney){
//		ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);  
//		if (taskType == TaskBean.TASK_SENT_TAG) {
//			SpannableStringBuilder builder = new SpannableStringBuilder(mActivity.getResources().getString(R.string.task_want_money, charges));
//			builder.setSpan(redSpan, 4, String.valueOf(charges).length()+4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
//			tvMoney.setText(builder);
//		} else {
//			SpannableStringBuilder builder = new SpannableStringBuilder(mActivity.getResources().getString(R.string.i_want_money, charges));
//			builder.setSpan(redSpan, 3, String.valueOf(charges).length()+3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
//			tvMoney.setText(builder);
//		}
	}
	
	/**
	 * 设置接单时间
	 * @todo:TODO
	 * @date:2015-8-20 下午2:58:40
	 * @author:hg_liuzl@163.com
	 * @params:@param mActivity
	 * @params:@param tvMoney
	 */
	public void setTime(Activity mActivity,TextView tvTime){
	//	tvTime.setText(mActivity.getResources().getString(R.string.task_reciver_time, ToolUtils.getFormatDate(created)));
	}
}
