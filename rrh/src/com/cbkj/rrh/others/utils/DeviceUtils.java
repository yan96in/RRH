package com.cbkj.rrh.others.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;

import com.cbkj.rrh.view.BToast;
import com.cbkj.rrh.view.dialog.DialogFactory;

/**
 * 
 * @todo:设备信息类
 * @date:2015-1-21 下午3:06:47
 * @author:hg_liuzl@163.com
 */
public class DeviceUtils {

	public static Dialog dialog = null;
	
	/**
	 * 
	 * @todo:拨号
	 * @date:2015-8-4 下午2:24:25
	 * @author:hg_liuzl@163.com
	 * @params:
	 */
	public static void dailPhone(final Activity activity,final String phone){
		
		 dialog = DialogFactory.doubleDialog(activity, phone, "呼叫", "取消", new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//用intent启动拨打电话  
		        Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phone));  
		        activity.startActivity(intent);  
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
	 * @todo:隐藏键盘
	 * @date:2015-6-30 下午3:54:48
	 * @author:hg_liuzl@163.com
	 * @params:@param v
	 * @params:@param context
	 */
	public static void hiddenKeybroad(View v, Context context) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
	}
	
	/**
	 * 复制文本到剪切板
	 * @param context
	 * @param text
	 */
	@SuppressWarnings("deprecation")
	public static void copyText(Activity activity, String text) {
		
		if(text == null) {
			return;
		}
		
		if(android.os.Build.VERSION.SDK_INT>=11)
		{
			android.content.ClipboardManager cm = (android.content.ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
		    cm.setText(text);
		}
		else {
			android.text.ClipboardManager cm = (android.text.ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
		    cm.setText(text);
		}
		
		BToast.show(activity, "复制成功！");
	}
	
	/**
	 * 
	 * @todo:判断是否有安装App
	 * @date:2015-4-25 上午11:39:48
	 * @author:hg_liuzl@163.com
	 * @params:@param context
	 * @params:@param packageName app包名
	 * @params:@return
	 */
	public static boolean hasPackage(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        List<String> pName = new ArrayList<String>();
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);
    }
  

	
	/**
	 * 
	 * @todo:获取设备高度
	 * @date:2015-1-21 下午3:11:58
	 * @author:hg_liuzl@163.com
	 * @params:@param activity
	 * @params:@return
	 */
	public static int getDeviceHeight(Activity activity){
		DisplayMetrics dm = activity.getResources().getDisplayMetrics();
		return dm.heightPixels;
	}
	
	/**
	 * 
	 * @todo:获取设备宽度
	 * @date:2015-1-21 下午3:12:31
	 * @author:hg_liuzl@163.com
	 * @params:@param activity
	 * @params:@return
	 */
	public static int getDeviceWidth(Activity activity) {
		DisplayMetrics dm = activity.getResources().getDisplayMetrics();
		return dm.widthPixels;
	}
	
	/**
	 * 
	 * @todo:获取设备的imei号
	 * @date:2015-4-19 下午4:09:08
	 * @author:hg_liuzl@163.com
	 * @params:@param context
	 * @params:@return
	 */
	public static String getImei(Context context){
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE); 
		return tm.getDeviceId();
	}
	
	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	} 
	


	/**
	 * 检查SD卡是否存在
	 * @return
	 */
	public static boolean chekSDCardExist(){
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}

}
