package com.cbkj.rrh.net.http;

/**
 * 
 * @todo:网络请求地址
 * @date:2015-4-19 下午5:16:36
 * @author:hg_liuzl@163.com
 */
public class HttpURL {
	
  public static final String BASE_URL = "http://service.lalawitkey.com";	//外网环境
//	public static final String BASE_URL = "http://192.168.1.118:9092";//     	测试环境
	
	/*****************************************************用户模块*************************************************************/
	/**获取手机验证码**/
	public static final String URL_PHONE_VERIFYCODE = BASE_URL + "/account/createVerifyCode";
	
	/**验证验证码**/
	public static final String URL_CHECK_VERIFYCODE = BASE_URL + "/account/checkVerifyCode";
	
	/**检验手机号是否被占用**/
	public static final String URL_CHECK_PHONE = BASE_URL + "/account/checkTelephone";
	
	/**注册操作**/
	public static final String URL_REGISTER = BASE_URL + "/account/register";
	
	/**登录操作**/
	public static final String URL_LOGIN = BASE_URL + "/account/login";
	
	/**用户资料**/
	public static final String URL_USER_PROFILE = BASE_URL + "/account/userProfile";
	
	/**忘记密码(重置密码)**/
	public static final String URL_RESET_PWD = BASE_URL + "/account/resetPwd";
	
	/**职业列表**/
	public static final String URL_POSITION_LIST = BASE_URL + "/positionList";
	
	/**修改用户资料**/
	public static final String URL_MODIFY_USER = BASE_URL + "/account/modifyUserProfile";
	
	/**维护支付宝账号**/
	public static final String URL_AMALIPAY_URL = BASE_URL + "/account/amAlipay";
	
	/**修改密码**/
	public static final String URL_MODIFY_PWD = BASE_URL + "/account/modifyPwd";
	
	/**检查新版本(这里只需要关注安卓升级接口)**/
	public static final String URL_CHECK_UPDATE = BASE_URL + "/upgrade";
	
	/**意见反馈**/
	public static final String URL_FEEDBACK = BASE_URL + "/feedback";
	
	/**文件上传**/
	public static final String URL_UPLOAD_FILE = BASE_URL + "/uploadFile";
	
	/**上传头像**/
	public static final String URL_UPLOAD_HEAD_IMG = BASE_URL + "/upload/uploadHatHead";
	
	/**文件证书**/
	public static final String URL_UPLOAD_CERTIFICATE_IMG = BASE_URL + "/upload/uploadCertificate";
	
	/**添加证书**/
	public static final String URL_ADD_CERTIFICATE_IMG = BASE_URL + "/addCertificate";
	
	/**删除证书**/
	public static final String URL_DEL_CERTIFICATE = BASE_URL + "/delCertificate";
	
	/**证书列表**/
	public static final String URL_CERTIFICATE_LIST = BASE_URL + "/certificateList";
	
	/**是否打开推送**/
	public static final String URL_SWITCH_PUSH = BASE_URL + "/openCommonPush";
	
	
	
	
	
	/*****************************************************业务模块*****************************************************************/
	
	/**提交任务 */
	public static final String URL_TASK_ADD = BASE_URL + "/addTask";
	
	/**取消任务 */
	public static final String URL_TASK_CANCEL = BASE_URL + "/cancelTask";

	/**强行终止任务 */
	public static final String URL_TASK_STOP = BASE_URL + "/stopTask";

	/**修改任务 */
	public static final String URL_TASK_MODIFY = BASE_URL + "/modifyTask";
	
	/**任务列表 */
	public static final String URL_TASK_LIST = BASE_URL + "/taskList";
	
	/**任务详情 */
	public static final String URL_TASK_DETAIL = BASE_URL + "/taskDetail";
	
	/**任务的接单人 */
	public static final String URL_TASK_RECIVES = BASE_URL + "/taskEmployee";
	
	/**发单人名片 */
	public static final String URL_SENTER_NAMECARD = BASE_URL + "/employerProfile";
	
	/**接单人名片 */
	public static final String URL_RECIVER_NAMECARD = BASE_URL + "/employeeProfile";
	
	/**我要接单 */
	public static final String URL_IWANT_RECIVE_TASK = BASE_URL + "/applyTask";
	
	/**修改报价 */
	public static final String URL_CONFIRM_CHARGE = BASE_URL + "/confirmCharges";
	
	/**我接的单 */
	public static final String URL_IRECIVER_TASK = BASE_URL + "/myReceivedTask";
	
	/**我发的单 */
	public static final String URL_ISENT_TASK = BASE_URL + "/mySentTask";
	
	/**选择一个接单人 */
	public static final String URL_SELECT_RECIVER = BASE_URL + "/chooseEmployee";
	
	/**接单人确认任务完成 */
	public static final String URL_AFFIRM_FINISH = BASE_URL + "/handInTask";
	
	/**延长任务时间*/
	public static final String URL_DELAY_TIME = BASE_URL + "/delayTaskTime";
	
	/**完结任务*/
	public static final String URL_TASK_FINISH = BASE_URL + "/finishTask";
	
	/**接单人取消接单*/
	public static final String URL_CANCEL_RECIVED_TASK = BASE_URL + "/cancelAppliedTask";
	
	/**评分规则 */
	public static final String URL_SCORE_RULES = BASE_URL + "/scoresRulesList";
	
	/**对接单人评分 */
	public static final String URL_RECIVER_GRADE = BASE_URL + "/scoreForEmployee";
	
	/**对发单人评分*/
	public static final String URL_SENTER_GRADE = BASE_URL + "/scoreForEmployer";
	
	/**举报接单人*/
	public static final String URL_REPORT_RECIVER = BASE_URL + "/reportEmployee";
	
	/**举报发单人 */
	public static final String URL_REPORT_SENTER = BASE_URL + "/reportEmployer";
	
	/**申请接单人认证*/
	public static final String URL_APPLY_IDENTIFICATION = BASE_URL + "/apply2Employee";
	
	/**消息推送*/
	public static final String URL_PUSH_LOG = BASE_URL + "/pushLogList";
	
	/**清空推送*/
	public static final String URL_PUSH_LOG_CLEAR = BASE_URL + "/clearPushLog";
	
	/**交易记录*/
	public static final String URL_TRADE_LOG = BASE_URL + "/pay/payLog";

	
}
