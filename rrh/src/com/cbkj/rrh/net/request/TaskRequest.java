package com.cbkj.rrh.net.request;

import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;

import com.cbkj.rrh.bean.TaskBean;
import com.cbkj.rrh.net.http.HttpRequest;
import com.cbkj.rrh.net.http.HttpRequestAsyncTask;
import com.cbkj.rrh.net.http.HttpURL;
import com.cbkj.rrh.net.http.HttpRequestAsyncTask.TaskListenerWithState;
import com.cbkj.rrh.others.utils.MD5;

/**
 * @todo:任务相关业务
 * @date:2015-7-21 下午12:03:55
 * @author:hg_liuzl@163.com
 */
public class TaskRequest extends HttpRequest {
	private static TaskRequest instance = null;
	public  synchronized static TaskRequest getInstance(){
		if(null == instance)
			instance = new TaskRequest();
		
		return instance;
	}


	/**
	 * 
	 * @todo:添加任务
	 * @date:2015-7-21 下午3:29:16
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param bean
	 */
	public void requestTaskADD(TaskListenerWithState mHttpTaskListener,Context context,TaskBean bean) {
		setRequestUrl(HttpURL.URL_TASK_ADD);
		JSONObject body = new JSONObject();
		try {
			body.put("userId", bean.userId);
			body.put("position", bean.position);
			body.put("content", bean.content);
			body.put("telephone", bean.telephone);
			body.put("contact", bean.contact);
			body.put("charges", bean.charges);
			body.put("address", bean.address);
			body.put("deadline", bean.deadline);
			body.put("finishTime", bean.finishTime);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(this, mHttpTaskListener, context).execute();
	}
	
	/**
	 * 
	 * @todo:编辑任务
	 * @date:2015-7-21 下午3:29:16
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param bean
	 */
	public void requestTaskModify(TaskListenerWithState mHttpTaskListener,Context context,TaskBean bean) {
		setRequestUrl(HttpURL.URL_TASK_MODIFY);
		JSONObject body = new JSONObject();
		try {
			body.put("userId", bean.userId);
			body.put("taskId", bean.taskId);
			body.put("position", bean.position);
			body.put("content", bean.content);
			body.put("telephone", bean.telephone);
			body.put("contact", bean.contact);
			body.put("charges", bean.charges);
			body.put("address", bean.address);
			body.put("deadline", bean.deadline);
			body.put("finishTime", bean.finishTime);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(this, mHttpTaskListener, context).execute();
	}
	
	/**
	 * 
	 * @todo:修改报价
	 * @date:2015-7-21 下午3:29:16
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param bean
	 */
	public void requestModifyCharges(TaskListenerWithState mHttpTaskListener,Context context,String taskId,String employeeId,String money) {
		setRequestUrl(HttpURL.URL_CONFIRM_CHARGE);
		JSONObject body = new JSONObject();
		try {
			body.put("taskId", taskId);
			body.put("employeeId", employeeId);
			body.put("charges", Float.valueOf(money));
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(this, mHttpTaskListener, context).execute();
	}
	
	
	/**
	 * 
	 * @todo:取消任务
	 * @date:2015-7-21 下午3:29:16
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param bean
	 */
	public void requestTaskCancel(TaskListenerWithState mHttpTaskListener,Context context,String userId,String taskId) {
		setRequestUrl(HttpURL.URL_TASK_CANCEL);
		JSONObject body = new JSONObject();
		try {
			body.put("userId", userId);
			body.put("taskId", taskId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(this, mHttpTaskListener, context).execute();
	}
	
	
	/**
	 * 
	 * @todo:强行终止任务
	 * @date:2015-7-21 下午3:29:16
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param bean
	 */
	public void requestTaskStop(TaskListenerWithState mHttpTaskListener,Context context,String userId,String taskId) {
		setRequestUrl(HttpURL.URL_TASK_STOP);
		JSONObject body = new JSONObject();
		try {
			body.put("userId", userId);
			body.put("taskId", taskId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(this, mHttpTaskListener, context).execute();
	}
	/**
	 * 
	 * @todo:任务列表
	 * @date:2015-7-21 下午3:29:16
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param bean
	 */
	public void requestTaskList(TaskListenerWithState mHttpTaskListener,Context context,int position,int start,int end) {
		setRequestUrl(HttpURL.URL_TASK_LIST);
		JSONObject body = new JSONObject();
		try {
			body.put("position", position);
			body.put("start", start);
			body.put("end", end);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(true,this, mHttpTaskListener, context).execute();
	}
	
	/**
	 * 
	 * @todo:获取任务详情
	 * @date:2015-7-21 下午3:29:16
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param bean
	 */
	public void requestTaskDetail(TaskListenerWithState mHttpTaskListener,Context context,String taskId,String loginUserId) {
		setRequestUrl(HttpURL.URL_TASK_DETAIL);
		JSONObject body = new JSONObject();
		try {
			body.put("taskId", taskId);
			body.put("userId", loginUserId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(this, mHttpTaskListener, context).execute();
	}
	
	/**
	 * 
	 * @todo:发单人名片
	 * @date:2015-7-21 下午3:29:16
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param bean
	 */
	public void requestSenterNameCard(TaskListenerWithState mHttpTaskListener,Context context,String userId) {
		setRequestUrl(HttpURL.URL_SENTER_NAMECARD);
		JSONObject body = new JSONObject();
		try {
			body.put("userId", userId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(this, mHttpTaskListener, context).execute();
	}
	
	/**
	 * 
	 * @todo:发单人名片
	 * @date:2015-7-21 下午3:29:16
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param bean
	 */
	public void requestReciverNameCard(TaskListenerWithState mHttpTaskListener,Context context,String userId) {
		setRequestUrl(HttpURL.URL_RECIVER_NAMECARD);
		JSONObject body = new JSONObject();
		try {
			body.put("userId", userId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(this, mHttpTaskListener, context).execute();
	}
	
	/**
	 * 
	 * @todo:接单人列表
	 * @date:2015-7-21 下午3:29:16
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param bean
	 */
	public void requestTaskRecivers(TaskListenerWithState mHttpTaskListener,Context context,String taskId,String whose,String employeeId) {
		setRequestUrl(HttpURL.URL_TASK_RECIVES);
		JSONObject body = new JSONObject();
		try {
			body.put("taskId", taskId);
			body.put("whose", whose);
			body.put("employeeId", employeeId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(this, mHttpTaskListener, context).execute();
	}
	
	/**
	 * 
	 * @todo:我要接单
	 * @date:2015-7-21 下午3:29:16
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param bean
	 */
	public void requestWantReciveTask(TaskListenerWithState mHttpTaskListener,Context context,String taskId,String userId,float charge) {
		setRequestUrl(HttpURL.URL_IWANT_RECIVE_TASK);
		JSONObject body = new JSONObject();
		try {
			body.put("taskId", taskId);
			body.put("userId", userId);
			body.put("charges", charge);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(this, mHttpTaskListener, context).execute();
	}
	
	/**
	 * 
	 * @todo:我接的单
	 * @date:2015-7-21 下午3:29:16
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param bean
	 */
	public void requestMyReciveTasks(TaskListenerWithState mHttpTaskListener,Context context,String userId,int start,int end,boolean showDialog) {
		setRequestUrl(HttpURL.URL_IRECIVER_TASK);
		JSONObject body = new JSONObject();
		try {
			body.put("userId", userId);
			body.put("start", start);
			body.put("end", end);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(showDialog,this, mHttpTaskListener, context).execute();
	}
	
	/**
	 * 
	 * @todo:我发的单
	 * @date:2015-7-21 下午3:29:16
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param bean
	 */
	public void requestMySentTasks(TaskListenerWithState mHttpTaskListener,Context context,String userId,int start,int end,boolean showDialog) {
		setRequestUrl(HttpURL.URL_ISENT_TASK);
		JSONObject body = new JSONObject();
		try {
			body.put("userId", userId);
			body.put("start", start);
			body.put("end", end);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(showDialog,this, mHttpTaskListener, context).execute();
	}
	
	/**
	 * 
	 * @todo:选择一个接单人
	 * @date:2015-7-21 下午3:29:16
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param bean
	 * @param payment	支付方式	int	Y	支付方式； 1：阿里支付宝；2：腾讯微信支付；
	 */
	public void requestChooseTask(TaskListenerWithState mHttpTaskListener,Context context,String taskId,String userId,String employeeId,String password,int payment) {
		setRequestUrl(HttpURL.URL_SELECT_RECIVER);
		JSONObject body = new JSONObject();
		try {
			body.put("userId", userId);
			body.put("taskId", taskId);
			body.put("employeeId", employeeId);
			body.put("password", MD5.GetMD5Code(password));
			body.put("payment", payment);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(this, mHttpTaskListener, context).execute();
	}
	
	/**
	 * 
	 * @todo:接单人确认完成任务
	 * @date:2015-7-21 下午3:29:16
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param bean
	 */
	public void requestAffirmFinishTasksByEmployee(TaskListenerWithState mHttpTaskListener,Context context,String taskId,String userId) {
		setRequestUrl(HttpURL.URL_AFFIRM_FINISH);
		JSONObject body = new JSONObject();
		try {
			body.put("userId", userId);
			body.put("taskId", taskId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(this, mHttpTaskListener, context).execute();
	}
	
	/**
	 * 
	 * @todo:延长接单完成时间
	 * @date:2015-7-21 下午3:29:16
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param bean
	 */
	public void requestDelayTaskTime(TaskListenerWithState mHttpTaskListener,Context context,String taskId,String userId) {
		setRequestUrl(HttpURL.URL_DELAY_TIME);
		JSONObject body = new JSONObject();
		try {
			body.put("userId", userId);
			body.put("taskId", taskId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(this, mHttpTaskListener, context).execute();
	}
	
	/**
	 * 
	 * @todo:雇主完结任务
	 * @date:2015-7-21 下午3:29:16
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param bean
	 */
	public void requestFinishTaskByEmployer(TaskListenerWithState mHttpTaskListener,Context context,String taskId,String userId) {
		setRequestUrl(HttpURL.URL_TASK_FINISH);
		JSONObject body = new JSONObject();
		try {
			body.put("userId", userId);
			body.put("taskId", taskId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(this, mHttpTaskListener, context).execute();
	}
	
	/**
	 * 
	 * @todo:对接单人评分
	 * @date:2015-7-21 下午3:29:16
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param bean
	 */
	public void requestReciverGrade(TaskListenerWithState mHttpTaskListener,Context context,String taskId,String userId,String employeeId,int rulesId) {
		setRequestUrl(HttpURL.URL_RECIVER_GRADE);
		JSONObject body = new JSONObject();
		try {
			body.put("taskId", taskId);
			body.put("userId", userId);
			body.put("employeeId", employeeId);
			body.put("rulesId", rulesId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(this, mHttpTaskListener, context).execute();
	}
	
	
	/**
	 * 
	 * @todo:评分规则
	 * @date:2015-7-21 下午3:29:16
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param bean
	 */
	public void requestScoreRules(TaskListenerWithState mHttpTaskListener,Context context,String whose) {
		setRequestUrl(HttpURL.URL_SCORE_RULES);
		JSONObject body = new JSONObject();
		try {
			body.put("whose", whose);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(this, mHttpTaskListener, context).execute();
	}
	
	/**
	 * 
	 * @todo:对发单人评分
	 * @date:2015-7-21 下午3:29:16
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param bean
	 */
	public void requestSenterGrade(TaskListenerWithState mHttpTaskListener,Context context,String taskId,String userId,String employeeId,int rulesId) {
		setRequestUrl(HttpURL.URL_SENTER_GRADE);
		JSONObject body = new JSONObject();
		try {
			body.put("taskId", taskId);
			body.put("userId", userId);
			body.put("employerId", employeeId);
			body.put("rulesId", rulesId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(this, mHttpTaskListener, context).execute();
	}
	
	
	/**
	 * 
	 * @todo:举报接单人
	 * @date:2015-7-21 下午3:29:16
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param bean
	 */
	public void requestReportReciver(TaskListenerWithState mHttpTaskListener,Context context,String taskId,String userId,String employee,int category,String content) {
		setRequestUrl(HttpURL.URL_REPORT_RECIVER);
		JSONObject body = new JSONObject();
		try {
			body.put("userId", userId);
			body.put("taskId", taskId);
			body.put("employeeId", employee);
			body.put("category", category);
			body.put("content", content);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(this, mHttpTaskListener, context).execute();
	}
	
	
	/**
	 * 
	 * @todo:举报发单人
	 * @date:2015-7-21 下午3:29:16
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param bean
	 */
	public void requestReportSenter(TaskListenerWithState mHttpTaskListener,Context context,String taskId,String userId,String employer,int category,String content) {
		setRequestUrl(HttpURL.URL_REPORT_SENTER);
		JSONObject body = new JSONObject();
		try {
			body.put("userId", userId);
			body.put("taskId", taskId);
			body.put("employerId", employer);
			body.put("category", category);
			body.put("content", content);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(this, mHttpTaskListener, context).execute();
	}
	
	/**
	 * 
	 * @todo:申请接单人认证
	 * @date:2015-7-21 下午3:29:16
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param bean
	 */
	public void requestApplyIdentification(TaskListenerWithState mHttpTaskListener,Context context,String userId,String fullName,String IDCard,String telephone,String IDCardPhoto1,String IDCardPhoto2,String alipayNo) {
		setRequestUrl(HttpURL.URL_APPLY_IDENTIFICATION);
		JSONObject body = new JSONObject();
		try {
			body.put("userId", userId);
			body.put("fullName", fullName);
			body.put("IDCard", IDCard);
			body.put("telephone", telephone);
			body.put("IDCardPhoto1", IDCardPhoto1);
			body.put("IDCardPhoto2", IDCardPhoto2);
			body.put("alipayNo", alipayNo);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(this, mHttpTaskListener, context).execute();
	}
	
	/**
	 * 
	 * @todo:消息推送
	 * @date:2015-7-21 下午3:29:16
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param bean
	 */
	public void requestPushLog(TaskListenerWithState mHttpTaskListener,Context context,String userId,int start,int end) {
		setRequestUrl(HttpURL.URL_PUSH_LOG);
		JSONObject body = new JSONObject();
		try {
			body.put("userId", userId);
			body.put("start", start);
			body.put("end", end);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(true,this, mHttpTaskListener, context).execute();
	}
	
	/**
	 * 
	 * @todo:清除消息
	 * @date:2015-7-21 下午3:29:16
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param bean
	 */
	public void requestClearPushLog(TaskListenerWithState mHttpTaskListener,Context context,String userId) {
		setRequestUrl(HttpURL.URL_PUSH_LOG_CLEAR);
		JSONObject body = new JSONObject();
		try {
			body.put("userId", userId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(true,this, mHttpTaskListener, context).execute();
	}
	
	/**
	 * 
	 * @todo:获取交易记录
	 * @date:2015-7-21 下午3:29:16
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param bean
	 */
	public void requestTradeLog(TaskListenerWithState mHttpTaskListener,Context context,String userId,int start,int end) {
		setRequestUrl(HttpURL.URL_TRADE_LOG);
		JSONObject body = new JSONObject();
		try {
			body.put("userId", userId);
			body.put("start", start);
			body.put("end", end);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(true,this, mHttpTaskListener, context).execute();
	}
	
	/**
	 * 
	 * @todo:接单人取消接单
	 * @date:2015-7-21 下午3:29:16
	 * @author:hg_liuzl@163.com
	 * @params:@param mHttpTaskListener
	 * @params:@param context
	 * @params:@param bean
	 */
	public void requestCancleRecivedTask(TaskListenerWithState mHttpTaskListener,Context context,String taskId,String userId) {
		setRequestUrl(HttpURL.URL_CANCEL_RECIVED_TASK);
		JSONObject body = new JSONObject();
		try {
			body.put("userId", userId);
			body.put("taskId", taskId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setBody(body, context);
		new HttpRequestAsyncTask(false,this, mHttpTaskListener, context).execute();
	}
	
}
