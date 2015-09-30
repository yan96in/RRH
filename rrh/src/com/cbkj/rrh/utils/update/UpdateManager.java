package com.cbkj.rrh.utils.update;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cbkj.rrh.R;
import com.cbkj.rrh.bean.ApkBean;
import com.cbkj.rrh.utils.ToolUtils;
import com.cbkj.rrh.view.dialog.DialogFactory;


/**
 * 
 * @todo:软件更新检测
 * @date:2014-12-4 下午4:55:55
 * @author:hg_liuzl@163.com
 */
public class UpdateManager {
	private Activity mActivity;
	/**更新提示语*/
	private String apkUpdateMsg;
	/**下载安装包的路径*/
	private String apkUrl;
	
	private String apkVersionCode;
	
	private Dialog downloadDialog;
	
	private Dialog noDownloadDialog;
	
	 /* 下载包安装路径 */
    private static final String savePath = "/sdcard/updatedemo/";
    
    private static final String saveFileName = savePath + "UpdateDemoRelease.apk";

    /* 进度条与通知ui刷新的handler和msg常量 */
    private ProgressBar mProgress;
    
    private static final int DOWN_UPDATE = 1;
    
    private static final int DOWN_OVER = 2;
    
    private int progress;
    private TextView tvProgress;
    
    private Thread downLoadThread;
    
    private boolean interceptFlag = false;
    
    private Handler mHandler = new Handler(){
    	public void handleMessage(Message msg) {
    		switch (msg.what) {
			case DOWN_UPDATE:
				mProgress.setProgress(progress);
				tvProgress.setText(progress+"%");
				break;
			case DOWN_OVER:
				downloadDialog.dismiss();
				installApk();
				break;
			default:
				break;
			}
    	};
    };
    
	public UpdateManager(Activity context,ApkBean bean) {
		this.mActivity = context;
		this.apkUpdateMsg = bean.intro;
		this.apkUrl = bean.downLoadUrl;
		this.apkVersionCode = bean.versionCode;
	}
	
	/**
	 * 
	 * @todo:TODO
	 * @date:2015-1-30 下午7:00:47
	 * @author:hg_liuzl@163.com
	 * @params:@param showTag  没有新版本的时候，是否需要提示,false不展示，true展示
	 */
	public void checkUpdateInfo(boolean showTag){
		boolean isNeedUpdate = ToolUtils.isNeedUpdate(apkVersionCode, mActivity);
		if(isNeedUpdate){
			showNoticeDialog();
		}else{
			doNoNeedUpdate(showTag);
		}
	}

	
	private void doNoNeedUpdate(boolean showDialog){
		if(!showDialog){
			return;
		}
		noDownloadDialog = DialogFactory.singleDialog(mActivity, "已经是最新版本", "确定", new OnClickListener() {
			@Override
			public void onClick(View v) {
				noDownloadDialog.cancel();
			}
		});
	}
	
	Dialog dialog = null;
	private void showNoticeDialog(){
		
//		View v = LayoutInflater.from(mActivity).inflate(R.layout.model_down_item, null);
//		
//		final Dialog dialog = DialogFactory.customDialog(mActivity, v);
//		
//		TextView tv = (TextView) v.findViewById(R.id.tv_title);
//		tv.setText("发现新版本");
//		tv = (TextView) v.findViewById(R.id.tv_info);
//		tv.setText(Html.fromHtml(apkUpdateMsg));
//		
//		v.findViewById(R.id.tv_ok).setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				dialog.dismiss();
//				showDownloadDialog();	
//			}
//		});
//
//		v.findViewById(R.id.tv_cancel).setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				dialog.dismiss();
//			}
//		});
		
	
		dialog = DialogFactory.contentDialog(mActivity, "发现新版本", apkUpdateMsg, "取消", "下载", new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		}, new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				showDownloadDialog();	
			}
		});
		
	}
	
	private void showDownloadDialog(){
		
		final LayoutInflater inflater = LayoutInflater.from(mActivity);
		View v = inflater.inflate(R.layout.layout_update_progress, null);
		
		downloadDialog = DialogFactory.customDialog(mActivity, v);
		mProgress = (ProgressBar)v.findViewById(R.id.progress);
		tvProgress = (TextView) v.findViewById(R.id.tv_progress);
		
		v.findViewById(R.id.tv_cancel).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				downloadDialog.dismiss();
				interceptFlag = true;
			}
		});
		downloadApk();
	}
	
	private Runnable mdownApkRunnable = new Runnable() {	
		@Override
		public void run() {
			try {
				URL url = new URL(apkUrl);
			
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				conn.connect();
				int length = conn.getContentLength();
				InputStream is = conn.getInputStream();
				
				File file = new File(savePath);
				if(!file.exists()){
					file.mkdir();
				}
				String apkFile = saveFileName;
				File ApkFile = new File(apkFile);
				FileOutputStream fos = new FileOutputStream(ApkFile);
				
				int count = 0;
				byte buf[] = new byte[1024];
				
				do{   		   		
		    		int numread = is.read(buf);
		    		count += numread;
		    	    progress =(int)(((float)count / length) * 100);
		    	    //更新进度
		    	    mHandler.sendEmptyMessage(DOWN_UPDATE);
		    		if(numread <= 0){	
		    			//下载完成通知安装
		    			mHandler.sendEmptyMessage(DOWN_OVER);
		    			break;
		    		}
		    		fos.write(buf,0,numread);
		    	}while(!interceptFlag);//点击取消就停止下载.
				
				fos.close();
				is.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch(IOException e){
				e.printStackTrace();
			}
			
		}
	};
	
	 /**
     * 下载apk
     * @param url
     */
	
	private void downloadApk(){
		downLoadThread = new Thread(mdownApkRunnable);
		downLoadThread.start();
	}
	 /**
     * 安装apk
     * @param url
     */
	private void installApk(){
		File apkfile = new File(saveFileName);
        if (!apkfile.exists()) {
            return;
        }    
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive"); 
        mActivity.startActivity(i);
	
	}
}
