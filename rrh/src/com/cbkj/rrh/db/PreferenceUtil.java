//package com.bgood.task.db;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.SharedPreferences;
//
//public class PreferenceUtil {
//	
//	public static final  String PREFERENCE_FILE = "tb_preference";
//	
//	private final SharedPreferences sp;
//	private final SharedPreferences.Editor editor;
//
//	@SuppressLint("CommitPrefEdits")
//	public PreferenceUtil(Context context, String file) {
//		sp = context.getSharedPreferences(file, Context.MODE_PRIVATE);
//		editor = sp.edit();
//	}
//
//	/**设置是否已经登录*/
//	public void setHasLogin(boolean isLogin){
//		editor.putBoolean("isLogin", isLogin);
//		editor.commit();
//	}
//	
//	public boolean isLogin(){
//		return sp.getBoolean("isLogin", false);
//	}
//	
//	
//	
//	/**设置幽默秀刷新时间**/
//	public void setPicRefreshTime(String refreshTime) {
//		editor.putString("joke_detail", refreshTime);
//		editor.commit();
//	}
//
//	public String getPicRefreshTime() {
//		return sp.getString("weiqiang_detail", "");
//	}
//	
//	/**设置幽默秀排序刷新时间**/
//	public void setJokeOrderRefreshTime(String refreshTime) {
//		editor.putString("joke_order", refreshTime);
//		editor.commit();
//	}
//
//	public String getJokeOrderRefreshTime() {
//		return sp.getString("joke_order", "");
//	}
//	
//	/**设置幽默秀排序刷新时间**/
//	public void setJokeRandomRefreshTime(String refreshTime) {
//		editor.putString("joke_random", refreshTime);
//		editor.commit();
//	}
//
//	public String getJokeRandomRefreshTime() {
//		return sp.getString("joke_random", "");
//	}
//	
//	/**是否有查看幽默秀协议**/
//	public void setInitJokeProtocol(boolean hasInit) {
//		editor.putBoolean("joke_protocol_init", hasInit);
//		editor.commit();
//	}
//
//	public boolean hasInitJokeProtocol() {
//		return sp.getBoolean("joke_protocol_init", false);
//	}
//	
//	/**************************************长内容缓存********************************************************/
//	
//	/***缓存所有的微墙***/
//	public void storeWeiqiangAll(String strJson) {
//		editor.putString("store_weiqiang_all", strJson);
//		editor.commit();
//	}
//	
//	public String getStoreWeiqiangAll() {
//		return sp.getString("store_weiqiang_all", "");
//	}
//	/***缓存我关注的微墙***/
//	public void storeWeiqiangAttention(String strJson) {
//		editor.putString("store_weiqiang_attention", strJson);
//		editor.commit();
//	}
//	
//	public String getStoreWeiqiangAttention() {
//		return sp.getString("store_weiqiang_attention", "");
//	}
//	
//	
//	/***缓存排序的幽默秀***/
//	public void storeJokeOrder(String strJson) {
//		editor.putString("store_joke_order", strJson);
//		editor.commit();
//	}
//	
//	public String getStoreJokeOrder() {
//		return sp.getString("store_joke_order", "");
//	}
//	
//	/***缓存随机的幽默秀***/
//	public void storeJokeRandom(String strJson) {
//		editor.putString("store_joke_random", strJson);
//		editor.commit();
//	}
//	
//	public String getStoreJokeRandom() {
//		return sp.getString("store_joke_random", "");
//	}
//	
//	/***缓存日排行的幽默秀***/
//	public void storeJokeRankDay(String strJson) {
//		editor.putString("storeJokeRankDay", strJson);
//		editor.commit();
//	}
//	
//	public String getStoreJokeRankDay() {
//		return sp.getString("storeJokeRankDay", "");
//	}
//	/***缓存周排行的幽默秀***/
//	public void storeJokeRankWeek(String strJson) {
//		editor.putString("storeJokeRankWeek", strJson);
//		editor.commit();
//	}
//	
//	public String getStoreJokeRankWeek() {
//		return sp.getString("storeJokeRankWeek", "");
//	}
//	/***缓存月排行的幽默秀***/
//	public void storeJokeRankMonth(String strJson) {
//		editor.putString("storeJokeRankMonth", strJson);
//		editor.commit();
//	}
//	
//	public String getStoreJokeRankMonth() {
//		return sp.getString("storeJokeRankMonth", "");
//	}
//	
//	/***缓存日榜单的幽默秀***/
//	public void storeJokeRecordDay(String strJson) {
//		editor.putString("storeJokeRecordDay", strJson);
//		editor.commit();
//	}
//	
//	public String getStoreJokeRecordDay() {
//		return sp.getString("storeJokeRecordDay", "");
//	}
//	/***缓存周榜单的幽默秀***/
//	public void storeJokeRecordWeek(String strJson) {
//		editor.putString("storeJokeRecordWeek", strJson);
//		editor.commit();
//	}
//	
//	public String getStoreJokeRecordWeek() {
//		return sp.getString("storeJokeRecordWeek", "");
//	}
//	/***缓存月榜单的幽默秀***/
//	public void storeJokeRecordMonth(String strJson) {
//		editor.putString("storeJokeRecordMonth", strJson);
//		editor.commit();
//	}
//	
//	public String getStoreJokeRecordMonth() {
//		return sp.getString("storeJokeRecordMonth", "");
//	}
//	
//}

package com.cbkj.rrh.db;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cbkj.rrh.bean.MessageBean;
import com.cbkj.rrh.bean.TaskBean;
import com.cbkj.rrh.bean.TradeBean;
import com.cbkj.rrh.bean.UserBean;
import com.cbkj.rrh.bean.WorkBean;

public class PreferenceUtil {
	
	public static final  String PREFERENCE_FILE = "tb_preference";
	
	private final SharedPreferences sp;
	private final SharedPreferences.Editor editor;

	private static  PreferenceUtil instance = null;
	
	public static synchronized PreferenceUtil getInstance(Context context){
		if (null == instance) {
			instance = new PreferenceUtil(context, PREFERENCE_FILE);
		}
		return instance;
	}
	
	@SuppressLint("CommitPrefEdits")
	public PreferenceUtil(Context context, String file) {
		sp = context.getSharedPreferences(file, Context.MODE_PRIVATE);
		editor = sp.edit();
	}
	
	/**清除数据**/
	public void doClear() {
		editor.clear().commit();
	}
	
	
	
	/**获取用户UserID*/
	private void setUserID(String userId){
		editor.putString("user_id", userId);
		editor.commit();
	}
	
	public String getUserID(){
		return sp.getString("user_id","");
	}
	
	
	/**接单人条款*/
	public void setReadEmployeeTerms(boolean hasRead){
		editor.putBoolean("read_employee_terms", hasRead);
		editor.commit();
	}
	
	public boolean hasReadEmployeeTerms(){
		return sp.getBoolean("read_employee_terms", false);
	}
	
	/**雇主条款*/
	public void setReadEmployerTerms(boolean hasRead){
		editor.putBoolean("read_employer_terms", hasRead);
		editor.commit();
	}
	
	public boolean hasReadEmployerTerms(){
		return sp.getBoolean("read_employer_terms", false);
	}
	
	/**用户是否有读Guide*/
	public void setReadGuide(boolean hasRead){
		editor.putBoolean("read_guide", hasRead);
		editor.commit();
	}
	
	public boolean hasReadGuide(){
		return sp.getBoolean("read_guide", false);
	}
	
	/**用户是否有读认证*/
	public void setReadApprove(boolean hasRead){
		editor.putBoolean("read_approve", hasRead);
		editor.commit();
	}
	
	public boolean hasReadApprove(){
		return sp.getBoolean("read_approve", false);
	}
	
	/**用户是否有读服务条款*/
	public void setReadServiceTerms(boolean hasRead){
		editor.putBoolean("read_service_terms", hasRead);
		editor.commit();
	}
	
	public boolean hasReadServiceTerms(){
		return sp.getBoolean("read_service_terms", false);
	}
	
	
	
	public boolean hasReadAllSetting(){
		return hasReadGuide() && hasReadServiceTerms();
	}
	
	public boolean hasReadAll(){
		return hasReadApprove() && hasReadGuide() && hasReadServiceTerms();
	}
	
	
	/**获取未读信息*/
	public void setNoReadMsg(int count,String userId){
		editor.putInt("un_read", count);
		editor.putString("user_id", userId);
		editor.commit();
	}
	
	public int getNoReadMsg(String userId){
		String uid = sp.getString("user_id", "");
		if (userId.equals(uid)) {
			return sp.getInt("un_read", 0);
		}else{
			return 0;
		}
	}
	/**是否有未读消息 */
	public boolean hasNoReadMsg(String userId){
		String uid = sp.getString("user_id", "");
		if (userId.equals(uid)) {
			return sp.getInt("un_read", 0)>0?true:false;
		}else{
			return false;
		}
	}
	
//	
//	/**获取所有信息**/
//	public int getAllMsg(String userId){
//		return getFansTip(userId)+getNoReadMsg(userId);
//	}
//	
	
	/**
	 * 
	 * @todo:设置签到时间
	 * @date:2015-5-25 下午5:32:51
	 * @author:hg_liuzl@163.com
	 * @params:@param signData
	 */
	public void setSignTime(String signData,String userId){
		editor.putString("signDate", signData);
		editor.putString("userId", userId);
		editor.commit();
	}
	
	/***
	 * 
	 * @todo:是否签到过
	 * @date:2015-5-25 下午5:39:26
	 * @author:hg_liuzl@163.com
	 * @params:@return
	 */
	@SuppressLint("SimpleDateFormat")
	public boolean isSign(String userId) {
		boolean isSign = false;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String oldUserId = sp.getString("userId", "");
		String old = sp.getString("signDate", "");
		String today = df.format(new Date());
		Date oldDate;
		Date todayDate;
		try {
			oldDate = df.parse(old);
			todayDate = df.parse(today);
			if (todayDate.getTime() <= oldDate.getTime() && oldUserId.equals(userId)) { // 今天时间小于，或者等于历史时间，则说明已经签到
				isSign =  true;
			} else {
				isSign =  false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isSign;
	}
	
	/**设置是否已经登录*/
	public void setHasLogin(boolean isLogin){
		editor.putBoolean("isLogin", isLogin);
		editor.commit();
	}
	
	public boolean isLogin(){
		return sp.getBoolean("isLogin", false);
	}
	
	/**存放用户信息,以对象的形式存放*/
	public void setUserBean(UserBean userBean){
		if (null == userBean) {
			return;
		}
		////把userbean对象转化成字符串，然后保存起来
		setUserID(userBean.userId);
		String userInfo = JSON.toJSONString(userBean);
		editor.putString("userInfo", userInfo);
		editor.commit();
	}

	
	/**获取用户信息*/
	public UserBean getUserBean(){
		String strUserInfo = sp.getString("userInfo", "");
		if (!TextUtils.isEmpty(strUserInfo)) {
			return JSON.parseObject(strUserInfo, UserBean.class);
		}
		return null;
	}
	
	/**设置用户名*/
	public void setAccountNumber(String accountNumber){
		editor.putString("user_account_number", accountNumber);
		editor.commit();
	}
	
	public String getAccountNumber(){
		return sp.getString("user_account_number", "");
	}
	
	/**设置密码*/
	public void setAccountPassword(String accountPassword){
		editor.putString("user_account_password", accountPassword);
		editor.commit();
	}
	
	public String getAccountPassword(){
		return sp.getString("user_account_password", "");
	}

	
	/**未审核的炫能最大ID**/
	public void setUnCheckJokeMaxID(int maxxnid) {
		editor.putInt("maxxnid", maxxnid);
		editor.commit();
	}

	public int getUnCheckJokeMaxID() {
		return sp.getInt("maxxnid", 0);
	}
	
	
	
	/**获取历史版本号**/
	public void setHistoryVersion(String version) {
		editor.putString("app_version", version);
		editor.commit();
	}

	public String getHistoryVersion() {
		return sp.getString("app_version", "");
	}
	
	/**设置是否展示微墙页**/
	public void setShowWelcomePage(boolean isShow) {
		editor.putBoolean("welcome_show", isShow);
		editor.commit();
	}

	public boolean getShowWelcomePage() {
		return sp.getBoolean("welcome_show", false);
	}

	
	
	
	/*************************************缓存数据******************************************/


	/**存放私活类型数据 */
	public void storeWorkbeans(String workbeans) {
		if (!TextUtils.isEmpty(workbeans)) {
			editor.putString("workbeans", workbeans);
			editor.commit();
		}
	}

	/**获取私活类型数据 */
	public List<WorkBean> getWorkBeans() {
		String strWorkBeans = sp.getString("workbeans", "");
		if (!TextUtils.isEmpty(strWorkBeans)) {
			return JSON.parseArray(strWorkBeans, WorkBean.class);
		}
		return null;
	}

	/**存放交易记录 只取最新的数据 **/
	public void storePayTradeMsg(String strJson, boolean isRefresh) {
		if (isRefresh && !TextUtils.isEmpty(strJson)) {
			editor.putString("pay_trade_msg", strJson);
			editor.commit();
		}
	}
	/**获取交易记录 数据 **/
	public List<TradeBean> getPayTradeMsg() {
		String str = sp.getString("pay_trade_msg", "");
		if (!TextUtils.isEmpty(str)) {
			return JSON.parseArray(str, TradeBean.class);
		}
		return null;
	}
	
	/**存放推送日志只取最新的数据 **/
	public void storePushLogData(String strJson, boolean isRefresh) {
		if (isRefresh && !TextUtils.isEmpty(strJson)) {
			editor.putString("store_push_log", strJson);
			editor.commit();
		}
	}

	/**获取推送日志**/
	public List<MessageBean> getPushLogData() {
		String str = sp.getString("store_push_log", "");
		if (!TextUtils.isEmpty(str)) {
			return JSON.parseArray(str, MessageBean.class);
		}
		return null;
	}

	/**存放我发的单最新数据**/
	public void storeSentTaskData(String strJson, boolean isRefresh) {
		if (isRefresh && !TextUtils.isEmpty(strJson)) {
			editor.putString("store_sent_task", strJson);
			editor.commit();
		}
	}

	/**获取我发的单数据*/
	public List<TaskBean> getSentTaskData() {
		String str = sp.getString("store_sent_task", "");
		if (!TextUtils.isEmpty(str)) {
			return JSON.parseArray(str, TaskBean.class);
		}
		return null;
	}

	/** 存我接的单数据 **/
	public void storeRecivedTaskData(String strJson, boolean isRefresh) {
		if (isRefresh && !TextUtils.isEmpty(strJson)) {
			editor.putString("store_recived_task", strJson);
			editor.commit();
		}
	}
	/**获取 我接的单数据**/
	public List<TaskBean> getRecivedTaskData() {
		String str = sp.getString("store_recived_task", "");
		if (!TextUtils.isEmpty(str)) {
			return JSON.parseArray(str, TaskBean.class);
		}
		return null;

	}

	/**存公共列表的数据 **/
	public void storeTaskData(String strJson, boolean isRefresh) {
		if (isRefresh && !TextUtils.isEmpty(strJson)) {
			editor.putString("task_data", strJson);
			editor.commit();
		}
	}

	/**获取公共列表的数据*/
	public List<TaskBean> getTaskData() {
		String str = sp.getString("task_data", "");
		if (!TextUtils.isEmpty(str)) {
			return JSON.parseArray(str, TaskBean.class);
		}
		return null;
	}
	
	/***************************************************绑定第三方************************************************/
	
	/***绑定新浪***/
	public void setBindSina(boolean isBind) {
		editor.putBoolean("bind_sina", isBind);
		editor.commit();
	}
	
	public boolean isBindSina() {
		return sp.getBoolean("bind_sina", false);
	}
	
	/***绑定QQ***/
	public void setBindQQ(boolean isBind) {
		editor.putBoolean("bind_qq", isBind);
		editor.commit();
	}
	
	public boolean isBindQQ() {
		return sp.getBoolean("bind_qq", false);
	}
	
	/***绑定微信***/
	public void setBindWX(boolean isBind) {
		editor.putBoolean("bind_wx", isBind);
		editor.commit();
	}
	
	public boolean isBindWX() {
		return sp.getBoolean("bind_wx", false);
	}
	
}
