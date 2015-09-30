package com.cbkj.rrh.ui.help;
//package com.bgood.task.ui.help;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.view.View;
//import android.view.View.OnClickListener;
//
//import com.bgood.task.R;
//import com.bgood.task.bean.CollectBean;
//import com.bgood.task.bean.ContentBean;
//import com.bgood.task.bean.ReplyBean;
//import com.bgood.task.net.http.HttpRequest;
//import com.bgood.task.net.http.HttpResponse;
//import com.bgood.task.net.http.HttpURL;
//import com.bgood.task.net.http.HttpRequestAsyncTask.TaskListenerWithState;
//import com.bgood.task.net.http.HttpResponse.HttpTaskState;
//import com.bgood.task.net.request.CommonRequest;
//import com.bgood.task.view.BToast;
//import com.bgood.task.view.dialog.DialogFactory;
//
///**
// * @todo:删除
// * @date:2015-2-4 上午11:17:00
// * @author:hg_liuzl@163.com
// */
//public class DeleteListener implements OnClickListener,TaskListenerWithState {
//
//	private Object object;
//	private Activity mActivity;
//	private IDeleteCallback mCallback;
//	private Dialog diallog;
//	
//	public DeleteListener(Object object,Activity activity,IDeleteCallback callback){
//		this.object = object;
//		this.mActivity = activity;
//		this.mCallback = callback;
//	}
//	
//	@Override
//	public void onClick(View v) {
//		diallog = DialogFactory.doubleDialog(mActivity, "确定删除这条记录？", "确定", "取消", okListener, cancelListener);
//	}
//	
//	private OnClickListener okListener = new OnClickListener() {
//		
//		@Override
//		public void onClick(View v) {
//			deleteAction();
//			diallog.dismiss();
//		}
//	};
//	
//	private OnClickListener cancelListener = new OnClickListener() {
//		
//		@Override
//		public void onClick(View v) {
//			diallog.dismiss();
//		}
//	};
//	
//	
//	private void deleteAction() {
//		if(object instanceof CollectBean){	//收藏
//			CollectBean collectBean = (CollectBean) object;
//			CommonRequest.getInstance().requestDelConllect(DeleteListener.this, mActivity, collectBean.collectionId);
//		}else if(object instanceof ContentBean){ //图片或者视频
//			ContentBean content = (ContentBean) object;
//			CommonRequest.getInstance().requestDelPicAndVideo(DeleteListener.this, mActivity,content.type,content.mediaId);
//		}else if(object instanceof ReplyBean){	//回复
////			ReplyBean reply = (ReplyBean) object;
////			CommonRequest.getInstance().request(DeleteListener.this, mActivity,content.type,content.mediaId);
//			
//		}
//	}
//
//	@Override
//	public void onTaskOver(HttpRequest request, HttpResponse response) {
//		if (response.getState() == HttpTaskState.STATE_OK) {
//			if (request.getRequestUrl() == HttpURL.URL_DEL_COLLECT && null != response.result) { //删除收藏
//				mCallback.deleteAction(object);
//			} else if(request.getRequestUrl() == HttpURL.URL_DEL_PIC_VIDEO  && null != response.result) {	//删除图片或者视频
//				mCallback.deleteAction(object);
//			}else{
//				BToast.show(mActivity, R.string.system_error);
//			}
//		}
//	}
//}
