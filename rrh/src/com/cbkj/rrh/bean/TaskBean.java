package com.cbkj.rrh.bean;

import java.io.Serializable;

import android.app.Activity;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cbkj.rrh.R;
import com.cbkj.rrh.adapter.TaskAdapter.TaskType;
import com.cbkj.rrh.system.Const;
import com.cbkj.rrh.utils.DeviceUtils;
import com.cbkj.rrh.utils.SharePostUtils;
import com.cbkj.rrh.utils.ToolUtils;

/**
 * @todo:任务类
 * @date:2015-7-21 下午3:03:26
 * @author:hg_liuzl@163.com
 */
public class TaskBean implements Serializable {
	
	
	
	/**数据模型*/
	public enum DataType{
		DATA_LIST,	//订单列表的数据
		DATA_DETAIL	//订单详情的数据
	}
	
	/** 
	 * 我接的单   1：待定中；2：成功；3：失败；4：完成
	 * 
	 * 待定 1， **/
	public static final int RECEIVE_TASK_HOLD = 1;
	/**成功 2 **/
	public static final int RECEIVE_TASK_SUCCESS = 2;
	/**失败 3 **/
	public static final int RECEIVE_TASK_FAIL = 3;
	/**完成 4**/
	public static final int RECEIVE_TASK_FINISH = 4;
	/**验收任务5**/
	public static final int RECEIVE_TASK_CHECK = 5;
	
	/** 我发的单
	 * 任务的当前状态，1：待定中；2：失败（过期或毁约）；3：进行中；4：完成
	 * 
	 * 待定中 1， **/
	public static final int SENT_TASK_HOLD = 1;
	/**失败，过期或毁约2 **/
	public static final int SENT_TASK_FAIL = 2;
	/**进行中3 **/
	public static final int SENT_TASK_WORKING = 3;
	/**完成 4**/
	public static final int SENT_TASK_FINISH = 4;
	/**验收任务 5**/
	public static final int SENT_TASK_CHECK = 5;
	
	
	/** 公共的单
	 * 任务的主要状态：1:任务有效，但未选择接单人；2：任务有效，已选择接单人；3：任务过期；5：任务完成；6：任务无效（可能被终止，被取消）
	 * 
	 * 1:任务有效，但未选择接单人； **/
	public static final int COMMON_TASK_HOLD = 1;
	
	/**2：任务有效，已选择接单人； **/
	public static final int COMMON_TASK_WORKING = 2;
	
	/**3：任务过期；**/
	public static final int COMMON_TASK_DEAD = 3;
	
	/**5：任务完成；**/
	public static final int COMMON_TASK_FINISH = 5;
	
	/**6：任务无效（可能被终止，被取消）**/
	public static final int COMMON_TASK_INVALID = 6;
	
	
	
	/**订单详情里面的
	 * **/
	/**0自己的单；**/
	public static final int DETAIL_TASK_OF_ME = 0;
	/**1:任务未敲定谁来做，userId 也没有报过价的时候；**/
	public static final int DETAIL_TASK_HOLD_NO_OFFER = 1;
	
	/**2：任务未敲定谁来做，userId 有报过价的时候； **/
	public static final int DETAIL_TASK_HOLD_OFFER = 2;
	
	/**3:任务已敲定谁来做，userId 并没报过价的时候；**/
	public static final int DETAIL_TASK_DECIDE_NO_OFFER = 3;
	
	/**4：任务已敲定谁来做，userId 也报过价的时候；**/
	public static final int DETAIL_TASK_DECIDE_OFFER = 4;
	
	/**5：验收任务；**/
	public static final int DETAIL_TASK_CHECK_OFFER = 5;
	
	
	
	
	/**
	 * @todo:TODO
	 * @date:2015-7-23 上午11:17:38
	 * @author:hg_liuzl@163.com
	 */
	private static final long serialVersionUID = 1L;

	/**任务实体类**/
	public static final String KEY_TASK_BEAN = "key_task_bean";
	
	/**接单TaskID**/
	public static final String KEY_TASK_ID = "key_task_id";
	
	/**接单消息类型**/
	public static final String KEY_MESSAGE_TYPE = "key_message_type";
	
	/**我发的单**/
	public static final int TASK_SENT_TAG = 1;
	
	/**我收的单**/
	public static final int TASK_RECIVED_TAG = 2;
	
	
	
	public String taskId;                   // 任务ID
	public String userId;		//	用户Id	int	Y	发单人的Id
	public String nickName;		//	用户昵称	int	Y	发单人的Id
	public String smallImg;		//	用户头像	int	Y	发单人的Id
	public int position;		//	职位分类Id	int	Y	职位分类Id
	public String content;		//	任务内容	string	Y	任务内容
	public String telephone;	//	联系电话	string	Y	联系电话
	public String contact;		//	联系人	string	N	联系人
	public long charges;			//	佣金	float	Y	预估的佣金数量（可能不准确）
	public String address;		//	联系地址	string	N	联系地址
	public String deadline;		//	剩余时间，一个时间段
	public String deadline2;		//	结束时间	datetime	Y	结束时间(有效期)
	public String finishTime;	//	结束时间	datetime	Y	结束时间(有效期)
	public String created;		//	注册时间	datetime	Y	新建时间
	public String updated;		//	更新时间	datetime	Y	新建时间
	public int commonStatus;			//	公共的status 任务的主要状态：1:任务有效，但未选择接单人；2：任务有效，已选择接单人；
	public int status;			//	当期状态	int	Y	任务的当前状态，1：待定；2：成功；3：失败；4：完成
	public String remark;	//评价，或者失败原因
	public String telPartner;	//选中的接单人电话号码
	public String partnerId;    //接单人用户Id	string	N	接单人（被发单人选中的人）的用户Id
	//订单详情里面会用到
	public int received;		//	登录用户已接单	int	N	当前登录用户已接过此任务0：未接过；1：已接过；
	public int myCharges;		//	登录用户的报价	float	N	当前登录用户,TA当时的报价
	public int hasScore;//	是否评分	int	Y	接单人是否对过此任务的发单人评分过？ 1：是；0：否
	
	/**
	 * 
	 * @todo:内容分享
	 * @date:2015-3-20 上午10:34:23
	 * @author:hg_liuzl@163.com
	 * @params:@param share
	 */
	public  void doShare(SharePostUtils share){
		share.setShareContent(content, "", getShareUrl(),charges);
	}
	
	public String getAddress() {
		if (TextUtils.isEmpty(address)) {
			return "---";
		}
		return address;
	}
	
	private String getShareUrl(){
		return String.format(Const.SHARE_URL,taskId);
	}
	
	public void setMoney(Activity mActivity,TextView tvMoney){
		tvMoney.setText(getFormatStr(mActivity, R.string.task_offer_money, String.valueOf(charges)));
	}
	
	public void setEndTime(Activity mActivity,TextView tvEndTime) {
		tvEndTime.setText(getFormatStr(mActivity, R.string.task_deadline, TextUtils.isEmpty(deadline)?"已截止":deadline));
	}
	
	public void setFinishTime(Activity mActivity,TextView tvFinishTime) {
		tvFinishTime.setText(getFormatStr(mActivity, R.string.task_plan_time, getFinishTime()));
	}
	
	public void setPublishTime(Activity mActivity,TextView tvPublishTime) {
		tvPublishTime.setText(ToolUtils.getFormatDate(created)+"发布");
	}
	
	public void setUpdateTime(Activity mActivity,TextView tvPublishTime) {
		tvPublishTime.setText(ToolUtils.getFormatDate(updated)+"更新");
	}
	
	private SpannableStringBuilder getFormatStr(Activity mActivity,int resString,String strWord){
		int start = splitLength(mActivity, resString);
		SpannableStringBuilder builder = new SpannableStringBuilder(mActivity.getResources().getString(resString, strWord));
		ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED); 
		int width = DeviceUtils.getDeviceWidth(mActivity);
		int size = 0;
		if (width < 600) {
			size = 25;
		} else if (width < 1000) {
			size = 35;
		} else {
			size = 45;
		}
		AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(size);
		builder.setSpan(redSpan, start, String.valueOf(strWord).length()+start, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
		builder.setSpan(sizeSpan, start, String.valueOf(strWord).length()+start, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
		return builder;
	}
	
	
	
	private int splitLength(Activity activity,int strWord) {
		String str = activity.getResources().getString(strWord);
		return str.indexOf("%s");
	}
	
	public String getFinishTime(){
		String format = "yyyy-MM-dd";
		return finishTime.substring(0, format.length());
	}
	
	public void setImageStatus(ImageView ivStatus,TaskType type) {
		
		if (type == TaskType.RECIVE_TASK) {		//，1：待定中；2：成功；3：失败；4：完成
			ivStatus.setVisibility(View.VISIBLE);
			switch (status) {
			case RECEIVE_TASK_HOLD:	//待定的
				ivStatus.setBackgroundResource(R.drawable.bg_task_status_hold);
				break;
			case RECEIVE_TASK_SUCCESS://过期的
				ivStatus.setBackgroundResource(R.drawable.bg_task_status_success);
				break;
			case RECEIVE_TASK_FAIL://进行中的
				ivStatus.setBackgroundResource(R.drawable.bg_task_status_fail);
				break;
			case RECEIVE_TASK_FINISH:	//完成
				ivStatus.setBackgroundResource(R.drawable.bg_task_status_finish);
				break;
			case RECEIVE_TASK_CHECK:	//验收
				ivStatus.setBackgroundResource(R.drawable.bg_task_status_check);
				break;
			default://待定的
				ivStatus.setBackgroundResource(R.drawable.bg_task_status_hold);
				break;
			}
		} else if(type == TaskType.SENT_TASK || type == TaskType.EMPLOYER_TASK) {	//发单
			ivStatus.setVisibility(View.VISIBLE);
			switch (status) { //1：待定中；2：失败（过期或毁约）；3：进行中；4：完成
			case SENT_TASK_HOLD:	//待定的
				ivStatus.setBackgroundResource(R.drawable.bg_task_status_hold);
				break;
			case SENT_TASK_FAIL://过期的,也放在失败里去
				ivStatus.setBackgroundResource(R.drawable.bg_task_status_fail);
				break;
			case SENT_TASK_WORKING://进行中的
				ivStatus.setBackgroundResource(R.drawable.bg_task_status_process);
				break;
			case SENT_TASK_FINISH:	//完成
				ivStatus.setBackgroundResource(R.drawable.bg_task_status_finish);
				break;
			case SENT_TASK_CHECK:	//验收
				ivStatus.setBackgroundResource(R.drawable.bg_task_status_check);
				break;
				
			default://待定的
				ivStatus.setBackgroundResource(R.drawable.bg_task_status_hold);
				break;
			}
		}
	}
}

