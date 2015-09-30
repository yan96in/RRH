package com.cbkj.rrh.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cbkj.rrh.R;
import com.cbkj.rrh.view.ImgWordView;


/**
 * @todo:对话框工厂类
 * @date:2015-5-5 下午8:07:04
 * @author:hg_liuzl@163.com
 */
public class DialogFactory {
	
	/**
	 * 
	 * @todo:提示对话框
	 * @date:2015-5-5 下午8:36:30
	 * @author:hg_liuzl@163.com
	 * @params:@param mActivity
	 * @params:@param msg
	 * @params:@param ok
	 * @params:@param listener
	 * @params:@return
	 */
	public static Dialog singleDialog(Activity mActivity,String msg,String ok,OnClickListener listener){
		View v = LinearLayout.inflate(mActivity, R.layout.layout_alert_single, null);

		TextView tvTitle = (TextView) v.findViewById(R.id.tv_title);
		tvTitle.setText(msg);
		
		TextView tvOK = (TextView) v.findViewById(R.id.tv_know);
		tvOK.setText(ok);
		tvOK.setOnClickListener(listener);
		
		Dialog dialog = new Dialog(mActivity,R.style.dialog_alert);
		dialog.setContentView(v);
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
		
		return dialog;
	}
	
	/**
	 * 
	 * @todo:带确认与取消的对话框
	 * @date:2015-5-5 下午8:29:25
	 * @author:hg_liuzl@163.com
	 * @params:@param mActivity
	 * @params:@param msg
	 * @params:@param strOk
	 * @params:@param strCancel
	 * @params:@param okListener
	 * @params:@param cancleListener
	 * @params:@return
	 */
	public static Dialog doubleDialog(Activity mActivity,String msg,String strOk,String strCancel,OnClickListener okListener,OnClickListener cancleListener){
		View v = LinearLayout.inflate(mActivity, R.layout.layout_alert, null);

		TextView tvTitle = (TextView) v.findViewById(R.id.tv_title);
		tvTitle.setText(msg);
		
		TextView tvOK = (TextView) v.findViewById(R.id.tv_ok);
		tvOK.setText(strOk);
		tvOK.setOnClickListener(okListener);
		
		TextView tvCancel = (TextView) v.findViewById(R.id.tv_cancel);
		tvCancel.setText(strCancel);
		tvCancel.setOnClickListener(cancleListener);
		
		Dialog dialog = new Dialog(mActivity,R.style.dialog_alert);
		dialog.setContentView(v);
		dialog.show();
		
		return dialog;
	}
	
	/**
	 * 
	 * @todo:带确定与取消的按钮
	 * @date:2015-5-5 下午8:31:26
	 * @author:hg_liuzl@163.com
	 * @params:@param mActivity
	 * @params:@param msg
	 * @params:@param okListener
	 * @params:@param cancleListener
	 * @params:@return
	 */
	public static Dialog doubleDialog(Activity mActivity,String msg,OnClickListener okListener,OnClickListener cancleListener) {
		return doubleDialog(mActivity, msg, "确定", "取消", okListener, cancleListener);
	}
	
	/**
	 * 
	 * @todo:底部弹出的对话框
	 * @date:2015-5-9 上午10:35:18
	 * @author:hg_liuzl@163.com
	 * @params:@param mActivity
	 * @params:@param title
	 * @params:@param oneMsg
	 * @params:@param twoMsg
	 * @params:@param oneListener
	 * @params:@param twoListener
	 * @params:@param cancelListener
	 * @params:@return
	 */
	public static BottomDialog bottomDialog(Activity mActivity,String title,String oneMsg,String twoMsg,OnClickListener oneListener,OnClickListener twoListener,OnClickListener cancelListener){
		
			View v = LayoutInflater.from(mActivity).inflate(R.layout.model_bottom_dialog, null);
			//标题
			TextView tvTitle = (TextView) v.findViewById(R.id.tv_title);
			tvTitle.setText(title);
			tvTitle.setVisibility(TextUtils.isEmpty(title)?View.GONE:View.VISIBLE);
			
			
			Button btnOne = (Button) v.findViewById(R.id.btn_one);
			btnOne.setText(oneMsg);
			btnOne.setVisibility(TextUtils.isEmpty(oneMsg)?View.GONE:View.VISIBLE);
			btnOne.setOnClickListener(oneListener);
			
			Button btnTwo = (Button) v.findViewById(R.id.btn_two);
			btnTwo.setText(twoMsg);
			btnTwo.setVisibility(TextUtils.isEmpty(twoMsg)?View.GONE:View.VISIBLE);
			btnTwo.setOnClickListener(twoListener);
			
			v.findViewById(R.id.btn_cancel).setOnClickListener(cancelListener);
			
			
			
			BottomDialog dialog = new BottomDialog(mActivity);
			dialog.setvChild(v);
			dialog.show();
			
			return dialog;
	}
	
	
	/**
	 * 
	 * @todo:底部弹出上传文件
	 * @date:2015-5-9 上午10:35:18
	 * @author:hg_liuzl@163.com
	 * @params:@param mActivity
	 * @params:@param title
	 * @params:@param oneMsg
	 * @params:@param twoMsg
	 * @params:@param oneListener
	 * @params:@param twoListener
	 * @params:@param cancelListener
	 * @params:@return
	 */
	public static BottomDialog uploadFileDialog(Activity mActivity,int oneDrawable,int oneName,int twoDrawable,int twoName,OnClickListener oneListener,OnClickListener twoListener,OnClickListener cancelListener){
		
			View v = LayoutInflater.from(mActivity).inflate(R.layout.model_upload_dialog, null);
			
			ImgWordView ivFirst = (ImgWordView) v.findViewById(R.id.iw_first);
			ivFirst.initLogoAndName(oneDrawable, oneName);
			ivFirst.setOnClickListener(oneListener);
			
			
			ImgWordView iwSecond = (ImgWordView) v.findViewById(R.id.iw_second);
			iwSecond.initLogoAndName(twoDrawable, twoName);
			iwSecond.setOnClickListener(oneListener);
			
			v.findViewById(R.id.tv_cancel).setOnClickListener(cancelListener);
			
			BottomDialog dialog = new BottomDialog(mActivity);
			dialog.setvChild(v);
			dialog.show();
			
			return dialog;
	}
	
	
	/**
	 * 
	 * @todo:带确认与取消的对话框
	 * @date:2015-5-5 下午8:29:25
	 * @author:hg_liuzl@163.com
	 * @params:@param mActivity
	 * @params:@param msg
	 * @params:@param strOk
	 * @params:@param strCancel
	 * @params:@param okListener
	 * @params:@param cancleListener
	 * @params:@return
	 */
	public static Dialog customDialog(Activity mActivity,View v){
		Dialog dialog = new Dialog(mActivity,R.style.dialog_alert);
		dialog.setContentView(v);
		dialog.show();
		return dialog;
	}
	
	
	

	/**
	 * 
	 * @todo:不带标题的底部确认框
	 * @date:2015-8-24 上午11:59:25
	 * @author:hg_liuzl@163.com
	 * @params:@param mActivity
	 * @params:@param okMsg
	 * @params:@param okListener
	 * @params:@param cancelListener
	 * @params:@return
	 */
	public static BottomDialog affirmDialog(Activity mActivity,String okMsg,OnClickListener okListener,OnClickListener cancelListener) {
		
		return affirmDialogWithTitle(mActivity, null, okMsg, okListener, cancelListener);
	}

	
	/**
	 * 
	 * @todo:底部弹出确认框
	 * @date:2015-8-24 上午11:59:09
	 * @author:hg_liuzl@163.com
	 * @params:@param mActivity
	 * @params:@param title
	 * @params:@param okMsg
	 * @params:@param okListener
	 * @params:@param cancelListener
	 * @params:@return
	 */
	public static BottomDialog affirmDialogWithTitle(Activity mActivity,String title,String okMsg,OnClickListener okListener,OnClickListener cancelListener){
		
			View v = LayoutInflater.from(mActivity).inflate(R.layout.model_bottom_select_dialog, null);
			//标题
			TextView tvTitle = (TextView) v.findViewById(R.id.tv_select_title);
			tvTitle.setText(title);
			tvTitle.setVisibility(TextUtils.isEmpty(title)?View.GONE:View.VISIBLE);
			
			TextView tvOk = (TextView) v.findViewById(R.id.tv_select_ok);
			tvOk.setText(okMsg);
			tvOk.setOnClickListener(okListener);
			
			v.findViewById(R.id.tv_select_cancel).setOnClickListener(cancelListener);
			
			BottomDialog dialog = new BottomDialog(mActivity);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			v.setLayoutParams(params);
			dialog.setvChild(v);
			dialog.show();
			
			return dialog;
	}
	
	/**
	 * 
	 * @todo:底部弹出确认框
	 * @date:2015-8-24 上午11:59:09
	 * @author:hg_liuzl@163.com
	 * @params:@param mActivity
	 * @params:@param title
	 * @params:@param okMsg
	 * @params:@param okListener
	 * @params:@param cancelListener
	 * @params:@return
	 */
	public static BottomDialog menuDialog(Activity mActivity,String oneMsg,String twoMsg,OnClickListener oneLister,OnClickListener twoListener,OnClickListener cancelListener){
		
			View v = LayoutInflater.from(mActivity).inflate(R.layout.model_bottom_select_dialog, null);
			//标题
			TextView tvTitle = (TextView) v.findViewById(R.id.tv_select_title);
			tvTitle.setVisibility(View.GONE);
			
			TextView tvOne =  (TextView) v.findViewById(R.id.tv_select_one);
			tvOne.setText(oneMsg);
			tvOne.setOnClickListener(oneLister);
			tvOne.setVisibility(View.VISIBLE);
			
			v.findViewById(R.id.v_cut_one).setVisibility(View.VISIBLE);
			
			TextView tvTwo =  (TextView) v.findViewById(R.id.tv_select_ok);
			tvTwo.setText(twoMsg);
			tvTwo.setOnClickListener(twoListener);
			tvTwo.setTextColor(mActivity.getResources().getColor(R.color.black));
			
			v.findViewById(R.id.tv_select_cancel).setOnClickListener(cancelListener);
			
			BottomDialog dialog = new BottomDialog(mActivity);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			v.setLayoutParams(params);
			dialog.setvChild(v);
			dialog.show();
			
			return dialog;
	}
	
	/**
	 * 
	 * @todo:带标题与正文以及两个按钮的对话框
	 * 
	 * 正文是用Html
	 * @date:2015-9-19 上午11:19:19
	 * @author:hg_liuzl@163.com
	 * @params:
	 */
	public static Dialog contentDialog(Activity mActivity,String title,String content,String cancle,String ok,OnClickListener cancleListener,OnClickListener okListener) {

		View v = LayoutInflater.from(mActivity).inflate(R.layout.model_down_item, null);

		TextView tv = (TextView) v.findViewById(R.id.tv_title);
		tv.setText(title);
		
		tv = (TextView) v.findViewById(R.id.tv_info);
		tv.setText(Html.fromHtml(content));
		
		tv = (TextView) v.findViewById(R.id.tv_ok);
		if (!TextUtils.isEmpty(ok)) {
			tv.setText(ok);
		}
		tv.setOnClickListener(okListener);

		tv =  (TextView) v.findViewById(R.id.tv_cancel);
		if (!TextUtils.isEmpty(cancle)) {
			tv.setText(cancle);
		}
		tv.setOnClickListener(cancleListener);
		
		Dialog dialog = new Dialog(mActivity,R.style.dialog_alert);
		dialog.setContentView(v);
		dialog.show();
		
		return dialog;
	}
	
	/***
	 * 
	 * @todo:TODO
	 * @date:2015-9-19 下午2:49:43
	 * @author:hg_liuzl@163.com
	 * @params:
	 */
	public static Dialog serviceTermDialog(Activity mActivity,String title,String content,String ok,OnClickListener okListener) {

		View v = LayoutInflater.from(mActivity).inflate(R.layout.service_terms_model, null);
		
		final Dialog dialog = new Dialog(mActivity,R.style.dialog_alert);
		dialog.setContentView(v);
		dialog.show();

		
		TextView tv = (TextView) v.findViewById(R.id.tv_title);
		tv.setText(title);
		
		tv = (TextView) v.findViewById(R.id.tv_info);
		//tv.setText(Html.fromHtml(content));
		tv.setText(content);
		
		tv = (TextView) v.findViewById(R.id.tv_ok);
		if (!TextUtils.isEmpty(ok)) {
			tv.setText(ok);
		}
		tv.setOnClickListener(okListener);

		tv =  (TextView) v.findViewById(R.id.tv_cancel);
		tv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				
			}
		});
		
		
		return dialog;
		
	}
	
}
