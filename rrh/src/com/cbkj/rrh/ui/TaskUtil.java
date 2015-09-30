package com.cbkj.rrh.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.cbkj.rrh.R;
import com.cbkj.rrh.adapter.OrderDetailAdapter;
import com.cbkj.rrh.adapter.TaskAdapter;
import com.cbkj.rrh.adapter.TaskAdapter.TaskType;
import com.cbkj.rrh.bean.TaskBean;
import com.cbkj.rrh.bean.TaskBean.DataType;
import com.cbkj.rrh.bean.TaskReciverBean;
import com.cbkj.rrh.net.http.HttpRequest;
import com.cbkj.rrh.net.http.HttpRequestAsyncTask.TaskListenerWithState;
import com.cbkj.rrh.net.http.HttpResponse;
import com.cbkj.rrh.net.http.HttpResponse.HttpTaskState;
import com.cbkj.rrh.net.http.HttpURL;
import com.cbkj.rrh.net.request.TaskRequest;
import com.cbkj.rrh.ui.me.user.ReportActivity;
import com.cbkj.rrh.ui.order.OrderPayActivity;
import com.cbkj.rrh.ui.order.OrderReditActivity;
import com.cbkj.rrh.view.BToast;
import com.cbkj.rrh.view.dialog.DialogFactory;

/**
 * @todo:任务辅助类
 * @date:2015-8-20 下午3:59:28
 * @author:hg_liuzl@163.com
 */
public class TaskUtil implements TaskListenerWithState {
	
	private static TaskUtil instance = null;
	
	private Activity activity = null;
	
	private String mCommentId = null;
	
	private String mTaskId = null;
	
	private OrderDetailAdapter mOrderDetailAdapter = null;
	
	private TaskAdapter mTaskAdapter = null;
	
	private DataType mDataType = null;
	
	public static  synchronized TaskUtil getInstance(){
		if (instance == null) {
			instance = new TaskUtil();
		}
		return instance;
	}
	
	
	
	public Dialog dialog = null;
	public Dialog dialog2 = null;
	public Dialog dialog3 = null;


	/**
	 * 
	 * @todo:呼叫联系人
	 * @date:2015-8-21 上午10:58:14
	 * @author:hg_liuzl@163.com
	 * @params:@param phone
	 */
	public  void callContact(final Activity mActivity,final String phone){
		 this.activity = mActivity;
		 dialog = DialogFactory.affirmDialogWithTitle(mActivity, phone, "呼叫", new OnClickListener() {
			@Override
			public void onClick(View v) {
				//用intent启动拨打电话  
		        Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phone));  
		        mActivity.startActivity(intent);  
				dialog.dismiss();
			}
		}, new OnClickListener(){
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}});
	}
//	
//	
//	/**
//	 * 
//	 * @todo:确定完成任务
//	 * @date:2015-8-20 下午4:03:12
//	 * @author:hg_liuzl@163.com
//	 * @params:	//如果是发单人确认完成任务，会带上接单人的ID,需要对其评价，
//	 *            如果是接单人确认完成任务，不需要带上发单人的ID
//	 */
//	public void affirmFinishTask(final Activity mActivity,final String taskId, final String userId,final TaskType type,final String commentId) {
//		 this.activity = mActivity;
//		 this.mCommentId = commentId;
//		 this.mTaskId = taskId;
//		 dialog = DialogFactory.affirmDialog(mActivity, "确定完成任务", new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					if (type == TaskType.SENT_TASK) {	//雇主
//						TaskRequest.getInstance().requestFinishTaskByEmployer(TaskUtil.this, mActivity, mTaskId, userId);
//					} else {	//接单人
//						TaskRequest.getInstance().requestAffirmFinishTasksByEmployee(TaskUtil.this, mActivity, taskId, userId);
//					}
//					dialog.dismiss();
//				}
//			}, new OnClickListener(){
//				@Override
//				public void onClick(View v) {
//					dialog.dismiss();
//				}});
//	}
	
	
	/**
	 * 
	 * @todo:确定完成任务
	 * @date:2015-8-20 下午4:03:12
	 * @author:hg_liuzl@163.com
	 * @params:	//如果是发单人确认完成任务，会带上接单人的ID,需要对其评价，
	 *            如果是接单人确认完成任务，不需要带上发单人的ID
	 *            
	 *            commentId,是被评价的ID
	 */
	public void affirmFinishTask(final Activity mActivity,final TaskType type,final DataType dataType,final String userId,final TaskBean task,final Object object,final String commentId) {
		 this.activity = mActivity;
		 this.mCommentId = commentId;
		 this.mTaskId = task.taskId;
		 this.mDataType = dataType;
		 
		 if (mDataType == DataType.DATA_LIST) {
			this.mTaskAdapter = (TaskAdapter) object;
		} else {
			this.mOrderDetailAdapter = (OrderDetailAdapter) object;
		}
		 dialog = DialogFactory.affirmDialog(mActivity, "确定完成任务", new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (type == TaskType.SENT_TASK) {	//雇主
						task.status = TaskBean.SENT_TASK_FINISH;
						task.remark = "您已验收任务，担保的佣金将在1~3个工作日内转给接单人.";
						TaskRequest.getInstance().requestFinishTaskByEmployer(TaskUtil.this, mActivity, mTaskId, userId);
					} else {	//接单人
						task.status = TaskBean.RECEIVE_TASK_CHECK;
						task.remark = "您已提交验收申请，请等待雇主验收.";
						TaskRequest.getInstance().requestAffirmFinishTasksByEmployee(TaskUtil.this, mActivity, mTaskId, userId);
					}
					dialog.dismiss();
				}
			}, new OnClickListener(){
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}});
	}
	
	
	/**
	 * 
	 * @todo:确定一个接单人
	 * @date:2015-8-20 下午4:53:05
	 * @author:hg_liuzl@163.com
	 * @params:@param mTaskBean
	 * @params:@param reciverBean
	 */
	public void affirmSelectOne(final Activity mActivity,final TaskBean mTaskBean,final TaskReciverBean reciverBean) {
		 this.activity = mActivity;
		 dialog = DialogFactory.affirmDialog(mActivity, "确定选TA", new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(mActivity,OrderPayActivity.class);
					intent.putExtra(TaskBean.KEY_TASK_BEAN, mTaskBean);
					intent.putExtra(TaskReciverBean.KEY_RECIVER_BEAN, reciverBean);
					mActivity.startActivity(intent);
					dialog.dismiss();
				}
			}, new OnClickListener(){
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}});

	}
	
	/**
	 * 
	 * @todo: 修改报价
	 * @date:2015-8-20 下午4:53:05
	 * @author:hg_liuzl@163.com
	 * @params:@param mTaskBean
	 * @params:@param reciverBean
	 */
	public void modifyOffer(final Activity mActivity,final TaskReciverBean reciverBean,OrderDetailAdapter adapter){
		this.activity = mActivity;
		this.mOrderDetailAdapter = adapter;
		View v = LinearLayout.inflate(mActivity, R.layout.layout_edit_alert,null);
		final Dialog dialog = new Dialog(mActivity, R.style.dialog_alert);
		dialog.setContentView(v);
		dialog.show();
		
		@SuppressWarnings("unused")
		final EditText etContent = (EditText) v.findViewById(R.id.my_edit);
		

		v.findViewById(R.id.tv_ok).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				final String money = etContent.getText().toString().trim();
				
				if (TextUtils.isEmpty(money)) {
					BToast.show(mActivity, "请填写新报价！");
					return;
				} else {
					dialog.dismiss();
					reciverBean.charges = money;
					TaskRequest.getInstance().requestModifyCharges(TaskUtil.this, mActivity, reciverBean.taskId, reciverBean.userId, money);
				}
			}
		});

		v.findViewById(R.id.tv_cancel).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});


	}
	
	/**
	 * 
	 * @todo:确定延期付款
	 * @date:2015-8-21 上午11:41:34
	 * @author:hg_liuzl@163.com
	 * @params:@param taskId
	 * @params:@param employerId
	 */
	public void doDelayPay(final Activity mActivity,final DataType dataType,final TaskBean task,final Object object) {
		 this.activity = mActivity;
		 this.mDataType = dataType;
		 
		 if (mDataType == DataType.DATA_LIST) {
			this.mTaskAdapter = (TaskAdapter) object;
		} else {
			this.mOrderDetailAdapter = (OrderDetailAdapter) object;
		}
		 
		 dialog = DialogFactory.affirmDialog(mActivity, "确定不合格", new OnClickListener() {
				@Override
				public void onClick(View v) {
					task.status = TaskBean.SENT_TASK_WORKING;	//退回到工作中的状态
					task.remark = "验收不合格，已要求接单人继续完善";
					TaskRequest.getInstance().requestDelayTaskTime(TaskUtil.this, mActivity, task.taskId, task.userId);
					dialog.dismiss();
				}
			}, new OnClickListener(){
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}});
	}
	
	
	/**
	 * 
	 * @todo:确定取消接单
	 * @date:2015-8-21 上午11:41:34
	 * @author:hg_liuzl@163.com
	 * @params:@param taskId
	 * @params:@param employerId
	 */
	public void doCancelTaskRecived(final Activity mActivity,final DataType type,final TaskBean task,final String userId,final TaskAdapter adapter ) {
		 this.activity = mActivity;
		 this.mDataType = type;
		 
		 dialog = DialogFactory.affirmDialog(mActivity, "确定退出接单", new OnClickListener() {
				@Override
				public void onClick(View v) {
					 if (mDataType == DataType.DATA_LIST) {
						 mTaskAdapter = adapter;
						 mTaskAdapter.removeData(task);
					}
					TaskRequest.getInstance().requestCancleRecivedTask(TaskUtil.this, activity, task.taskId, userId);
					dialog.dismiss();
				}
			}, new OnClickListener(){
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}});
	}
	
	/**
	 * 
	 * @todo:订单详情更多操作
	 * @date:2015-8-24 下午3:47:14
	 * @author:hg_liuzl@163.com
	 * @params:
	 */
	public void showEmployerMenuMore(final Activity mActivity,final TaskBean taskBean) {
		 this.activity = mActivity;
		dialog = DialogFactory.menuDialog(mActivity, "取消订单", "重新编辑",
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog2 = DialogFactory.affirmDialog(mActivity, "确定取消订单", new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								TaskRequest.getInstance().requestTaskCancel(TaskUtil.this, mActivity, taskBean.userId, taskBean.taskId);
								dialog2.dismiss();
							}
						}, new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								dialog2.dismiss();
							}
						});
						dialog.dismiss();
					}
				}, new OnClickListener() {
					@Override
					public void onClick(View v) {
						doReditTask(mActivity,taskBean);
						dialog.dismiss();
					}
				}, new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
	}
	
	
	/**
	 * 
	 * @todo:重新编辑订单
	 * @date:2015-8-28 下午2:14:22
	 * @author:hg_liuzl@163.com
	 * @params:@param mActivity
	 * @params:@param taskBean
	 */
	public void doReditTask(final Activity mActivity,final TaskBean taskBean) {
		this.activity = mActivity;
		dialog2 = DialogFactory.affirmDialog(mActivity, "确定重新编辑", new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog2.dismiss();
				Intent intent = new Intent(mActivity, OrderReditActivity.class);
				intent.putExtra(TaskBean.KEY_TASK_BEAN, taskBean);
				mActivity.startActivity(intent);
			}
		}, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog2.dismiss();
			}
		});

	}

	
	/**
	 * 
	 * @todo:接单人的更多操作
	 * @date:2015-8-24 下午3:47:14
	 * @author:hg_liuzl@163.com
	 * @params:
	 */
	public void showEmployeeMenuMore(final Activity mActivity,final TaskBean taskBean,final String userId) {
		 this.activity = mActivity;
		 dialog = DialogFactory.menuDialog(mActivity, "取消接单", "举      报",
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog2 = DialogFactory.affirmDialog(mActivity, "确定取消接单", new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								TaskRequest.getInstance().requestCancleRecivedTask(TaskUtil.this, mActivity, taskBean.taskId, userId);
								dialog2.dismiss();
							}
						}, new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								dialog2.dismiss();
							}
						});
						dialog.dismiss();
					}
				}, new OnClickListener() {
					@Override
					public void onClick(View v) {
						ReportActivity.doReportAction(mActivity, ReportActivity.KEY_EMPLOYER, taskBean.taskId, userId);

						dialog.dismiss();
					}
				}, new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
	}
	
	
	
	
	@Override
	public void onTaskOver(HttpRequest request, HttpResponse response) {
		if (response.getState() == HttpTaskState.STATE_OK) {
			if (HttpURL.URL_AFFIRM_FINISH == request.getRequestUrl() && response.result!=null) {	//确定完成任务
				if (response.result.getSuccess()) {
					if (mDataType == DataType.DATA_DETAIL) {
						this.mOrderDetailAdapter.notifyDataSetChanged();
					} else {
						this.mTaskAdapter.notifyDataSetChanged();
					}
				} else {
					BToast.show(activity, response.result.getErrorMsg());
				}
				
			} else if (HttpURL.URL_TASK_FINISH == request.getRequestUrl() && response.result!=null) {	//确定完成任务
				if (response.result.getSuccess()) {	//雇主确定完成任务后，要去评价
					if (mDataType == DataType.DATA_DETAIL) {
						this.mOrderDetailAdapter.notifyDataSetChanged();
					} else {
						this.mTaskAdapter.notifyDataSetChanged();
					}
//					ScoreEvaluateActivity.doScoreAction(activity, ScoreEvaluateActivity.KEY_SCORE_EMPLOYEE, mTaskId, mCommentId);
				} else {
					BToast.show(activity, response.result.getErrorMsg());
				}
				
			}
			
			else if(HttpURL.URL_DELAY_TIME == request.getRequestUrl() && response.result!=null){	//延期
				if (response.result.getSuccess()) {
					BToast.show(activity, "操作成功！");
					if (mDataType == DataType.DATA_DETAIL) {
						this.mOrderDetailAdapter.notifyDataSetChanged();
					} else {
						this.mTaskAdapter.notifyDataSetChanged();
					}
				} else {
					BToast.show(activity, response.result.getErrorMsg());
				}
			}
			else if(HttpURL.URL_CANCEL_RECIVED_TASK == request.getRequestUrl() && response.result!=null){	//延期
				if (response.result.getSuccess()) {
					BToast.show(activity, "接单取消成功！");
					if (mDataType != DataType.DATA_LIST) {
						activity.finish();
					}
				} else {
					BToast.show(activity, response.result.getErrorMsg());
				}
			}else if(HttpURL.URL_TASK_CANCEL == request.getRequestUrl() && response.result!=null){	//雇主取消任务
				
				if (response.result.getSuccess()) {
					BToast.show(activity, "订单取消成功");
					activity.finish();
				} else {
					BToast.show(activity, response.result.getErrorMsg());
				}
				
			} else if(HttpURL.URL_CONFIRM_CHARGE == request.getRequestUrl() && response.result!=null){	//修改报价
				if (response.result.getSuccess()) {
					mOrderDetailAdapter.notifyDataSetChanged();
					BToast.show(activity, "修改报价成功");
				} else {
					BToast.show(activity, response.result.getErrorMsg());
				}
				
			}
//			else {
//				BToast.show(activity, R.string.system_error);
//			}
		}
	}
	

}
