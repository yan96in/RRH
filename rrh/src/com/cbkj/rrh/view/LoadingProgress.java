package com.cbkj.rrh.view;

import android.content.Context;

import com.cbkj.rrh.view.dialog.CustomLoadingDialog;


/**
 * 
 * @todo:自定义Loding
 * @date:2015-1-22 下午5:46:54
 * @author:hg_liuzl@163.com
 */
public class LoadingProgress {

	private static LoadingProgress intance = null;

	private CustomLoadingDialog mDialog;

	private int mShownCount;

	public static synchronized LoadingProgress getInstance() {
		if(null == intance)
		{
			intance = new LoadingProgress();
		}
		return intance;
	}

	private LoadingProgress() {
	}

	public void show(Context context) {
		show(context, "加载中...");
	}

	public void show(Context context, String message) {
		if (haveShown()) {
			mShownCount++;
			return;
		}

		createLoadingDialog(context, message);

		if (!mDialog.isShowing()) {
			mShownCount++;
			mDialog.show();
		}
	}

	private boolean haveShown() {
		return mShownCount != 0 ? true : false;
	}

	private void createLoadingDialog(Context context, String message) {
		mDialog = new CustomLoadingDialog(context);
		mDialog.setCancelable(true);
		mDialog.setCanceledOnTouchOutside(false);
	}

	public void dismiss() {
		if (mDialog != null && mDialog.isShowing()) {
			mShownCount = 0;
			mDialog.cancel();
			mDialog = null;
		}
	}
}



































//
//
//package com.bgood.task.view;
//
//import android.content.Context;
//
//import com.bgood.task.view.dialog.CustomLoadingDialog;
//
//
///**
// * 
// * @todo:自定义Loding
// * @date:2015-1-22 下午5:46:54
// * @author:hg_liuzl@163.com
// */
//public class LoadingProgress {
//
//	private static LoadingProgress intance = null;
//
//	private CustomLoadingDialog mDialog;
//
//	private int mShownCount;
//
//	public static synchronized LoadingProgress getInstance() {
//		if(null == intance)
//		{
//			intance = new LoadingProgress();
//		}
//		return intance;
//	}
//
//	private LoadingProgress() {
//	}
//
//	public void show(Context context) {
//		show(context, "加载中...");
//	}
//
//	public void show(Context context, String message) {
//		if (haveShown()) {
//			mShownCount++;
//			return;
//		}
//
//		createLoadingDialog(context, message);
//
//		if (!mDialog.isShowing()) {
//			mShownCount++;
//			mDialog.show();
//		}
//	}
//
//	private boolean haveShown() {
//		return mShownCount != 0 ? true : false;
//	}
//
//	private void createLoadingDialog(Context context, String message) {
//		mDialog = new CustomLoadingDialog(context);
//		mDialog.setCancelable(true);
//		mDialog.setCanceledOnTouchOutside(false);
//		//mDialog.setMessage(message);
//	}
//
//	public void dismiss() {
//		if (mDialog != null && mDialog.isShowing()) {
//			mShownCount = 0;
//			mDialog.cancel();
//			mDialog = null;
//		}
//	}
//}
