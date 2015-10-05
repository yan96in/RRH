package com.cbkj.rrh.main.help;
//package com.bgood.task.ui.help;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.view.View;
//import android.view.View.OnClickListener;
//
//import com.bgood.task.R;
//import com.bgood.task.bean.ContentBean;
//import com.bgood.task.net.http.HttpRequest;
//import com.bgood.task.net.http.HttpRequestAsyncTask.TaskListenerWithState;
//import com.bgood.task.net.http.HttpResponse;
//import com.bgood.task.net.http.HttpResponse.HttpTaskState;
//import com.bgood.task.net.http.HttpURL;
//import com.bgood.task.net.request.CommonRequest;
//import com.bgood.task.net.request.UserRequest;
//import com.bgood.task.system.BGApp;
//import com.bgood.task.system.Const;
//import com.bgood.task.view.BToast;
//
///**
// * @date:2015-2-4 上午11:17:00
// * @author:hg_liuzl@163.com
// */
//public class MoreListener implements OnClickListener,TaskListenerWithState {
//
//	private ContentBean mContentBean;
//	private Activity mActivity;
//	
//	public MoreListener(ContentBean bean,Activity activity){
//		this.mContentBean = bean;
//		this.mActivity = activity;
//	}
//	
//	@Override
//	public void onClick(View v) {
//		if (!mContentBean.isSend) {
//			return;
//		}
//		if (BGApp.isUserLogin) {
//			alertDialog();
//		} else {
//			BToast.show(mActivity, "请先登录！");
//		}
//		
//	}
//	
//	AlertDialog dialog = null;
//	private void alertDialog(){
//	  dialog = new AlertDialog.Builder(mActivity, R.style.dialog_alert)
//    	.setCancelable(true)
//    	.setItems(R.array.content_action, new android.content.DialogInterface.OnClickListener () {
//			
//			@Override
//			public void onClick(DialogInterface d, int position) {
//				switch (position) {
//				case 0:
//					//添加关注
//					if (mContentBean.userId.equals(BGApp.mUserId)) {
//						BToast.show(mActivity, "请不要关注自己！");
//					}else{
//						UserRequest.getInstance().requestRelationAction(MoreListener.this, mActivity, BGApp.mUserId, mContentBean.userId, Const.TYPE_ATTENTION_CREATE);
//					}
//					dialog.dismiss();
//					break;
//				case 1:
//					CommonRequest.getInstance().requestAddConllect(MoreListener.this, mActivity, BGApp.mUserId, mContentBean.type, mContentBean.mediaId);
//					dialog.dismiss();
//					break;
//				case 2:
//				//	ReportActivity.doReportAction(mActivity, mContentBean.type == 1?Const.REPORT_TYPE_PIC:Const.REPORT_TYPE_VIDEO, mContentBean.mediaId, -1);
//					dialog.dismiss();
//					break;
//				case 3:
//					dialog.dismiss();
//					break;
//				default:
//					break;
//				}
//				
//			}
//		})
//    	.show();
//	}
//
//	@Override
//	public void onTaskOver(HttpRequest request, HttpResponse response) {
//		if (response.getState() == HttpTaskState.STATE_OK) {
//			if (request.getRequestUrl() == HttpURL.URL_ADD_COLLECT && null != response.result ) {		//添加收藏
//				if (response.result.getSuccess()) {
//					BToast.show(mActivity, "收藏成功");
//				} else {
//					BToast.show(mActivity, response.result.getErrorMsg());
//				}
//			} else if(request.getRequestUrl() == HttpURL.URL_REALATION_ACTION  && null != response.result ) {	//添加关注
//				if (response.result.getSuccess()) {
//					BToast.show(mActivity, "关注成功");
//				} else {
//					BToast.show(mActivity, response.result.getErrorMsg());
//				}
//			}else{
//				BToast.show(mActivity, "系统出错");
//			}
//		}
//	}
//}
