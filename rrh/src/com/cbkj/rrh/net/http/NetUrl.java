package com.cbkj.rrh.net.http;

/**
 * 
 * @todo:网络请求地址
 * @date:2015-4-19 下午5:16:36
 * @author:hg_liuzl@163.com
 */
public class NetUrl {
	
	public static final String BASE_URL = "http://bbcwm.showneng.com:9090/bbcwm";//     生产环境
//	public static final String BASE_URL = "http://192.168.1.121:9090/bbcwm";//     	测试环境
//	public static final String BASE_URL = "http://192.168.1.108:8080/bbcwm";//     	测试环境
	
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
	
	/**忘记密码**/
	public static final String URL_RESET_PWD = BASE_URL + "/account/resetPwd";
	
	/**修改用户资料**/
	public static final String URL_MODIFY_USER = BASE_URL + "/account/modifyUserProfile";
	
	/**修改密码**/
	public static final String URL_MODIFY_PWD = BASE_URL + "/account/modifyPwd";
	
	/**检查新版本(这里只需要关注安卓升级接口)**/
	public static final String URL_CHECK_UPDATE = BASE_URL + "/upgrade";
	
	/**意见反馈**/
	public static final String URL_FEEDBACK = BASE_URL + "/feedback";
	
	/**文件上传**/
	public static final String URL_UPLOAD_FILE = BASE_URL + "/uploadFile";
	/******************************************************业务模块******************************************************************/
	
	/**提交任务 */
	public static final String URL_TASK_ADD = BASE_URL + "/addTask";
	/**修改任务 */
	public static final String URL_TASK_MODIFY = BASE_URL + "/modifyTask";
	/**任务列表 */
	public static final String URL_TASK_LIST = BASE_URL + "/taskList";
	/**任务详情 */
	public static final String URL_TASK_DETAIL = BASE_URL + "/taskDetail";
	
	
//	
//	/**发单人名片 */
//	public static final String URL_MY_PUBLISH = BASE_URL + "/myPublish";
//	/**接单人名片 */
//	public static final String URL_MY_PUBLISH = BASE_URL + "/myPublish";
//	/**我要接单 */
//	public static final String URL_MY_PUBLISH = BASE_URL + "/myPublish";
//	/**我接的任务 */
//	public static final String URL_MY_PUBLISH = BASE_URL + "/myPublish";
//	/**我发的任务 */
//	public static final String URL_MY_PUBLISH = BASE_URL + "/myPublish";
//	/**确定接单人 */
//	public static final String URL_MY_PUBLISH = BASE_URL + "/myPublish";
//	/**通知任务验收 */
//	public static final String URL_MY_PUBLISH = BASE_URL + "/myPublish";
//	/**延长任务时间 */
//	public static final String URL_MY_PUBLISH = BASE_URL + "/myPublish";
//	/**确定任务完成 */
//	public static final String URL_MY_PUBLISH = BASE_URL + "/myPublish";
//	/**对接单人评分 */
//	public static final String URL_MY_PUBLISH = BASE_URL + "/myPublish";
//	/**举报接单人 */
//	public static final String URL_MY_PUBLISH = BASE_URL + "/myPublish";
//	/**举报发单人 */
//	public static final String URL_MY_PUBLISH = BASE_URL + "/myPublish";
//	/**举报任务 */
//	public static final String URL_MY_PUBLISH = BASE_URL + "/myPublish";
//	
//	
}
