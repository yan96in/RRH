
package com.cbkj.rrh.others.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

@SuppressLint("SimpleDateFormat")
public class ToolUtils {
	
	/**
	 * 
	 * @todo:检验18位身份证号
	 * @date:2015-8-11 上午11:52:03
	 * @author:hg_liuzl@163.com
	 * @params:@param CID
	 * @params:@return
	 */
	public static boolean checkCID(String CID) {
		if (TextUtils.isEmpty(CID)) {
			return false;
		}
		//18位身份证检测
		String cidFormat = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}[([0-9]|X|x)]$";
		
		//定义判别用户身份证号的正则表达式（要么是15位，要么是18位，最后一位可以为字母）
		Pattern idNumPattern = Pattern.compile(cidFormat);
		//通过Pattern获得Matcher
		Matcher idNumMatcher = idNumPattern.matcher(CID);
		//判断用户输入是否为身份证号
		return idNumMatcher.matches();
	}
	
	/**
	 * 时间转换成字符串
	 * @param seconds
	 * @return
	 */
	public static String millsecondsToStr(int seconds){
		seconds = seconds / 1000;
		String result = "";
		int hour = 0, min = 0, second = 0;
		hour = seconds / 3600;
		min = (seconds - hour * 3600) / 60;
		second = seconds - hour * 3600 - min * 60;
		if (hour < 10) {
			result += "0" + hour + ":";
		} else {
			result += hour + ":";
		}
		if (min < 10) {
			result += "0" + min + ":";
		} else {
			result += min + ":";
		}
		if (second < 10) {
			result += "0" + second;
		} else {
			result += second;
		}
		return result;
	}
	
	/**
	 * 把秒转换成时间格式，如101s 转换成00:01:41
	 * @param seconds
	 * @return,这里我只考虑分，秒
	 */
	public static String secFormateTime(long time) {
		long hour = 0;
		long mins = 0;
		long sec = 0;
		
//		if (time >= 3600) {
//			hour = time/3600;
//			mins = (time-3600*hour)/60;
//			sec = time-3600*hour-mins*60;
//		}else
		if(time< 3600 && time >=60){
			mins = time/60;
			sec = time - mins * 60;
		}else{
			sec = time;
		}
		
		StringBuffer sb = new StringBuffer();
		//sb.append(hour >10?hour:"0"+hour).append(":");
		sb.append(mins >10?mins:"0"+mins).append(":");
		sb.append(sec >10?sec:"0"+sec);
		return sb.toString();
		
	}
	
	
	
	/**
	 * 
	 * @todo:距离换算
	 * @date:2015-3-17 下午5:32:40
	 * @author:hg_liuzl@163.com
	 * @params:@param distance
	 * @params:@return
	 */
	public static String formatDistance(String mDistance) {
		
		if(TextUtils.isEmpty(mDistance)){
			return "火星";
		}
		
		double distance = Double.valueOf(mDistance);
		StringBuilder sb = new StringBuilder();
		if(distance>=1000){
			sb.append(Math.round(distance/100d)/10d);
			sb.append("km");
		}else{
			sb.append((int)(distance+0.5));
			sb.append("m");
		}
		return sb.toString();
	}

	/**
	 * 
	 * 获取版本名称
	 * */
	public static String getVersionName(Context app) {
		PackageInfo packageInfo = null;
		try {
			packageInfo = app.getPackageManager().getPackageInfo(
					app.getPackageName(), 0);
			return packageInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取版本号
	 * 
	 * */
	public static int getVersionCode(Context app) {
		PackageInfo packageInfo = null;
		try {
			packageInfo = app.getPackageManager().getPackageInfo(
					app.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return 1;
	}

	
	// 用当前时间给取得的图片命名
	public static String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}
	
	/**
	 * 
	 * @todo 隐藏键盘
	 * @param v
	 */
	public static void hiddenKeybroad(View v, Context context) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
	}
	
	/**
	 * 判断网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkinfo = manager.getActiveNetworkInfo();
		if (networkinfo != null && networkinfo.isAvailable()) {
			return true;
		}
		return false;
	}
	
	public static SpannableStringBuilder setTextColor(Resources re,String text,int start,int color){
        SpannableStringBuilder style=new SpannableStringBuilder(text); 
        if(!TextUtils.isEmpty(text) && start<text.length()){
            style.setSpan(new ForegroundColorSpan(re.getColor(color)),start,text.length(),Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        }
        return style;
    }

	/**
	 * 判断wifi是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isWifi(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null
				&& activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否为移动的号码
	 * 
	 * @return
	 */
	public static boolean isMobile(String imsi) {
		if (imsi.startsWith("46000") || imsi.startsWith("46002")) {
			return true;
		}
		return false;
	}

	/**
	 * 获取Mac地址
	 * 
	 * @param context
	 * @return
	 */
	public static String getMacAddress(Context context) {
//		WifiManager wifi = (WifiManager) context
//				.getSystemService(Context.WIFI_SERVICE);
//		WifiInfo info = wifi.getConnectionInfo();
//		return info.getMacAddress();
		return "00:22:F4:10:61:7E";
	}

	/**
	 * 判断service是否启动
	 * 
	 * @param contex
	 * @return
	 */
	public static boolean isServiceStart(Context context) {
		ActivityManager myManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> myServiceList = myManager
				.getRunningServices(50);
		for (ActivityManager.RunningServiceInfo info : myServiceList) {
			if (context.getPackageName().equals(info.service.getPackageName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断activity是否启动
	 * 
	 * @param contex
	 * @return
	 */
	public static boolean isAcvitityStart(Context context) {
		ActivityManager myManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> myActivityList = myManager
				.getRunningTasks(1000);
		for (ActivityManager.RunningTaskInfo info : myActivityList) {
			if (context.getPackageName().equals(
					info.baseActivity.getPackageName())) {
				return true;
			}
		}
		return false;
	}

	
	/**data yyyy-mm-dd HH:mm:ss*/
	public static SimpleDateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * @return
	 */
	public static String formatDate(Date date) {
		if (null == date) return "";
		return dateFormate.format(date);
	}
	/**
	 * @return
	 */
	public static String formatDate(String date) {
		if (null == date) return "";
		try {
			Pattern pp = Pattern.compile("^[\\d]{4}[-]{1}[\\d]{1,2}[-]{1}[\\d]{1,2}");
			Matcher match1 = pp.matcher(date);
			if (match1.find()) {
				return match1.group();
			}
			match1 = null;
			return "";
		} catch (Exception e) {
			return "";
		}
	}
	
	/**
	 * @return
	 */
	public static String formatDate() {
		Date date = new Date();
		return dateFormate.format(date);
	}
	
	/**
	 * 比较时间是为今天
	 * @param date
	 * @return
	 */
	@SuppressWarnings("deprecation")
    public static boolean compareDate(String date){
	    try {
            return android.text.format.DateUtils.isToday(new Date(date).getTime());
        } catch (Exception e) {
        }
	    return false;
	}
		
	/**
	 * judge the Email style
	 * @param str
	 * @return
	 */
	public static boolean isEmail(String str) {
		if (null == str) return false;
		boolean cf = isContainChr(str);
		if (cf) return false;
		Pattern pp = Pattern.compile("^[\\w|_]+[\\w|.]*@{1}[A-Za-z0-9_.]+\\.{1}\\w+");
		Matcher match1 = pp.matcher(str);
		boolean ff = match1.matches();
		pp = null;
		match1 = null;
		return ff;
	}
	
	/**
	 * judge the Email style
	 * @param str
	 * @return
	 */
	public static boolean isAllNumber(String str) {
		if (null == str) return false;
		Pattern pp = Pattern.compile("^[0-9]+");
		Matcher match1 = pp.matcher(str);
		boolean ff = match1.matches();
		pp = null;
		match1 = null;
		return ff;
	}
	
	
	/**
	 * 
	 * @todo:判断电话格式
	 * @date:2015-5-5 下午4:36:59
	 * @author:hg_liuzl@163.com
	 * @params:@param str
	 * @params:@return
	 */
	public static boolean isTelephone(String str) {
		if (null == str) return false;
		Pattern pp = Pattern.compile("^\\d+$");
		Matcher match1 = pp.matcher(str);
		boolean ff = match1.matches();
		pp = null;
		match1 = null;
		return ff;
	}
	
	/**
	 * judge the telephone style
	 * @param str
	 * @return
	 */
	public static boolean isTWTelephone(String str) {
		if (null == str) return false;
		Pattern pp = Pattern.compile("09[0-9]{2}[0-9]{6}");
		Matcher match1 = pp.matcher(str);
		boolean ff = match1.matches();
		pp = null;
		match1 = null;
		return ff;
	}
	
	
	
	/**
	 * validate the userCode
	 * @param name
	 * @return
	 */
	public static boolean isValidName(String name) {
		
		if (null == name) return false;
		
		Pattern pp1 = Pattern.compile("^[\\w|_]{1}[@|\\.|\\w]{5,29}");
		Matcher match = pp1.matcher(name);
		boolean cf = isContainChr(name);
		boolean ff = match.find();
		pp1 = null;
		match = null;
		return !cf && ff;
	}
	/**
	 * 是否为中文
	 * @param c
	 * @return
	 */
	private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
             || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
            || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
            || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
            || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
            || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }
	
	/**
	 * 获取字符串长度,两个英文字母为一个汉字 
	 * @param s
	 * @return
	 */
	public static int lenChar(String s) {
		if (null == s || "".equals(s.trim()))
			return 0;
		int len = s.length();
		int chinese = 0;
		int engs = 0;
		for (int i = 0; i<len; i++) {
			boolean cf = isChinese(s.charAt(i));
			if (cf) 
				chinese ++;
			else {
				engs ++;
			}
		}
		
		return chinese = chinese + engs/2;
	}
	/**
	 * 是否包含了汉字
	 * @param str
	 * @return
	 */
	public static boolean isContainChr(String str) {
		boolean cf = false;
		for (int i = 0; i<str.length(); i++) {
			cf = isChinese(str.charAt(i));
			if (cf) break;
		}
		return cf;
	}
	
	/**
	 * 获取本机的ip地址
	 * @return
	 */
	public static String getIp(){
		try {
			for(Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();){
				NetworkInterface intf = (NetworkInterface)en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = (InetAddress)enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException e) {
			LogUtils.w("getLocalIpAddress",e);
		}
		return null;
	}
	
	/**
	 * 把arraylist转换成string
	 * @param lists
	 * @return
	 */
	public static String arrayListToString(List<? extends Object> lists){
		StringBuffer buffer = new StringBuffer();
		if(lists!=null && lists.size()>0){
			for(Object o : lists){
				buffer.append(o.toString());
				buffer.append("\n");
			}
		}
		return buffer.toString();
	}
	
	/**
	 * 
	 * @target 获取字符串
	 * @author Andy.Liu
	 * @createTime 2012-8-30  上午11:51:54
	 * @param str
	 * @param len  从什么位置开始截取
	 * @param isEnd 是否从头部截取(false),还是从尾部截取(true)
	 * @return
	 */
	public static String splitString(String str,int len,boolean isEnd){
		String spliteStr = "";
		if(TextUtils.isEmpty(str))
			return spliteStr;
		else{
			int length = str.length();
			if(length>len)
				spliteStr = !isEnd ? str.substring(len) : str.substring(length-len);
			else
				spliteStr = str;	
				
			return spliteStr;
		}
	}
	
	/**
	 * 
	 * @target 获取字符串
	 * @author Andy.Liu
	 * @createTime 2012-8-30  上午11:51:54
	 * @param str
	 * @param begin  从倒数什么位置开始截取
	 * @param end    从倒数什么位置结束
	 * @return
	 */
	public static String splitString(String str,int begin,int end){
		String spliteStr = "";
		if(TextUtils.isEmpty(str))
			return spliteStr;
		else{
			int length = str.length();
			if(end < begin && begin < length)
				spliteStr = str.substring(length - begin,length - end);
			else
				spliteStr = str;	
				
			return spliteStr;
		}
	}
	
//	/**
//	 * 获取版本号
//	 * @return 当前应用的版本号
//	 */
//	public static String getVersion(Context context) {
//	    try {
//	        PackageManager manager = context.getPackageManager();
//	        PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
//	        String version = info.versionName;
//	        return context.getString(R.string.app_version,version);
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        return context.getString(R.string.app_get_fail);
//	    }
//	}
	
	/**
	 * 判断当前环境是否为台湾地址
	 */
    public static boolean isTaiwan() {
        String country = Locale.getDefault().getCountry();
        if("TW".equalsIgnoreCase(country)){
            return true;
        }else{
            return false;
        }
    } 
    
 

	
	/**
	 * 手机号验证
	 * 
	 * @param  str
	 * @return 验证通过返回true
	 */
	public static boolean judgeTelephone(String str) { 
		Pattern p = null;
		Matcher m = null;
		boolean b = false; 
		p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches(); 
		return b;
	}
	/**
	 * 电话号码验证
	 * 
	 * @param  str
	 * @return 验证通过返回true
	 */
	public static boolean isPhone(String str) { 
		Pattern p1 = null,p2 = null;
		Matcher m = null;
		boolean b = false;  
		p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的
		p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的
		if(str.length() >9)
		{	m = p1.matcher(str);
 		    b = m.matches();  
		}else{
			m = p2.matcher(str);
 			b = m.matches(); 
		}  
		return b;
	}
	
	/**
	 * 时间格式
	 * date (MM-dd HH:mm:ss)
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String getFormatDate(String date){
		if (TextUtils.isEmpty(date)) {
			return "";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currentDate = new Date();
		Date fromDate = null;
		StringBuffer sb = new StringBuffer();
		try {
			fromDate = dateFormat.parse(date);
			
			if(fromDate.getYear() == currentDate.getYear() && fromDate.getMonth() == currentDate.getMonth()){	//如果是同年同月
				if(fromDate.getDate() == currentDate.getDate()){
					sb.append("今天");
				}else if(fromDate.getDate() == currentDate.getDate()-1){
					sb.append("昨天");
				}else if(fromDate.getDate() == currentDate.getDate()-2){
					sb.append("前天");
				}else{
					sb.append(fromDate.getMonth()+1).append("月").append(fromDate.getDate()).append("日");
				}
				sb.append(" ").append(fromDate.getHours()).append(":").append(formateTenNum(fromDate.getMinutes()));
//				sb.append(" ").append(fromDate.getHours()>12?fromDate.getHours()-12:fromDate.getHours()).append(":").append(formateTenNum(fromDate.getMinutes()));
//				sb.append(fromDate.getHours()>12 ? "PM":"AM");
				return sb.toString();
				
			}else if(fromDate.getYear() == currentDate.getYear() && fromDate.getMonth() != currentDate.getMonth()){//同年不同月
				return new SimpleDateFormat("MM-dd HH:mm").format(fromDate);
			}else if(fromDate.getYear() == currentDate.getYear() && fromDate.getMonth() == currentDate.getMonth()){//不同年且不同月
				return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(fromDate);
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 
	 * @todo:获取当前时间
	 * @date:2014-10-27 下午4:33:32
	 * @author:hg_liuzl@163.com
	 * @params:
	 */
	public static String getNowTime() {
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowTime = sdf.format(date);
		
		return getFormatDate(nowTime);
	}
	
	
	/**
	 * 
	 * @todo:获取当前时间
	 * @date:2014-10-27 下午4:33:32
	 * @author:hg_liuzl@163.com
	 * @params:
	 */
	public static String getStandardTime() {
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowTime = sdf.format(date);
		return nowTime;
		
	}
	
	
	/**
	 * 
	 * @author lzlong@zwmob.com
	 * @time 2014-3-24 下午4:28:40
	 * @todo  格式化10以下的数据
	 *  @param num
	 *  @return
	 */
	public static String formateTenNum(int num){
		return num < 10 ? "0"+num:""+num;
	}
	
	/**
	 * 获取yyyy-MM-dd格式的日期字符串
	 * @param date
	 * @return
	 */
	public static String getDate(Date date){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String str = dateFormat.format( date ); 
		return str;
	}
	

	/**
	 * 时间格式
	 * date (MM-dd HH:mm:ss)
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String getFormatTime(String dateStr,String formatData){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = dateFormat.parse(dateStr);
			return new SimpleDateFormat(formatData).format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	/**
	 * 前面，或者后面多少天
	 * date (MM-dd HH:mm:ss)
	 * @return
	 * String result = getFormatTime("2014-10-20 00:00:00", "MM月dd日", 1000*3600*24L*365);
	 */
	@SuppressWarnings("deprecation")
	public static String getFormatTime(String dateStr,String formatData,long time){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = dateFormat.parse(dateStr);
			return new SimpleDateFormat(formatData).format(new Date(date.getTime()-time));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
	/**
	 * 
	 * @todo:比较一下是否有新版本
	 * @date:2014-12-1 下午3:43:29
	 * @author:hg_liuzl@163.com
	 * @params:@param version
	 * @params:@return
	 * 说明，远程服务器升级包版本格式必须与本地应用版本格式一致 如 1.1.1.1和1.1.1.2
	 */
	public static boolean isNeedUpdate(String version,Context mContext) {
		
		if(TextUtils.isEmpty(version))
			return false;
		
		
		/**远程app,版本*/
		String[] netVersion = version.split("\\.");
		/**当前应用的app,版本*/
		String[] curVersion = ConfigUtil.getVersionName(mContext).split("\\.");

		for(int i = 0;i<netVersion.length;i++){
			int curV = Integer.valueOf(curVersion[i]);
			int netV = Integer.valueOf(netVersion[i]);
			if(curV > netV){
				break;
			}else if(curV < netV){
				return true;
			}
		}
		return false;
	}
	
	
//	/**远程app,版本*/
//	String[] netVersion = version.split("\\.");
//	/**当前应用的app,版本*/
//	String[] curVersion = ConfigUtil.getVersionName(mContext).split("\\.");
//
//	for(int i = 0;i<netVersion.length;i++){
//		int netV = Integer.valueOf(netVersion[i]);
//		int curV = Integer.valueOf(curVersion[i]);
//		if(netV > curV){
//			return true;
//		}
//	}
//	return false;
	

	/**
	 * 
	 * @todo:是否已经更新到新版本
	 * @date:2015-3-20 下午6:27:19
	 * @author:hg_liuzl@163.com
	 * @params:@param version  历史版本的app
	 * @params:@param mContext
	 * @params:@return
	 */
	public static boolean hasUpdate(String version,Context mContext) {
		if(TextUtils.isEmpty(version))
			return true;
		
		/**旧版app,版本*/
		String[] netVersion = version.split("\\.");
		/**当前应用的app,版本*/
		String[] curVersion = ConfigUtil.getVersionName(mContext).split("\\.");

		for(int i = 0;i<netVersion.length;i++){
			int netV = Integer.valueOf(netVersion[i]);
			int curV = Integer.valueOf(curVersion[i]);
			if(netV < curV){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @todo:格式化隐藏手机号
	 * @date:2015-9-23 下午5:37:21
	 * @author:hg_liuzl@163.com
	 * @params:@param telephone
	 * @params:@return
	 */
	public static String formatHiddenTelephone(String telephone) {
		String newTelephone = "";
		if (!TextUtils.isEmpty(telephone) && telephone.length() >= 11) {
			
			String oldChar = telephone.substring(3, 7);
			newTelephone = telephone.replace(oldChar, "****");
		}
		
		return newTelephone;
		
	}
	
}
