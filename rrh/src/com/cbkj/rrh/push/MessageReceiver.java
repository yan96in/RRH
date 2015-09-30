package com.cbkj.rrh.push;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.cbkj.rrh.db.PreferenceUtil;
import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

/**
 * 
 * @todo:消息推送通知
 * @date:2015-8-25 下午2:58:43
 * @author:hg_liuzl@163.com
 */
public class MessageReceiver extends XGPushBaseReceiver {
	
	public static final String MSG_RECEIVER_TAG = "com.bgood.task.RECEIVE_PUSH";
	private Intent intent = new Intent(MSG_RECEIVER_TAG);

	// 通知展示
	@Override
	public void onNotifactionShowedResult(Context context,XGPushShowedResult notifiShowedRlt) {
		if (context == null || notifiShowedRlt == null) {
			return;
		}
		PreferenceUtil pUtil = PreferenceUtil.getInstance(context);
		
		if (!TextUtils.isEmpty(pUtil.getUserID())) {
			String userId = pUtil.getUserID();
			//未读消息
			int countMention = pUtil.getNoReadMsg(userId);
			countMention++;
			pUtil.setNoReadMsg(countMention, userId);
			context.sendBroadcast(intent);
			}
	}

	// 通知点击回调 actionType=1为该消息被清除，actionType=0为该消息被点击
	@Override
	public void onNotifactionClickedResult(Context context,XGPushClickedResult message) {
		if (context == null || message == null) {
			return;
		}
//		String text = "";
//		if (message.getActionType() == XGPushClickedResult.NOTIFACTION_CLICKED_TYPE) {
//			// 通知在通知栏被点击啦。。。。。
//			// APP自己处理点击的相关动作
//			// 这个动作可以在activity的onResume也能监听，请看第3点相关内容
//			
//			Intent intent = new Intent(context, MyMentionActivity.class);
//			context.startActivity(intent);
//			
//		}
//		BToast.show(context, text);
	}

	@Override
	public void onDeleteTagResult(Context arg0, int arg1, String arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegisterResult(Context arg0, int arg1,
			XGPushRegisterResult arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSetTagResult(Context arg0, int arg1, String arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextMessage(Context context, XGPushTextMessage message) {
		if (context == null || message == null) {
			return;
		}
		
	}

	@Override
	public void onUnregisterResult(Context arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}


}
