package com.cbkj.rrh.net.http;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import android.content.Context;
import android.os.AsyncTask;

import com.cbkj.rrh.net.http.HttpResponse.HttpTaskState;
import com.cbkj.rrh.others.utils.ConfigUtil;
import com.cbkj.rrh.others.utils.LogUtils;
import com.cbkj.rrh.view.BToast;
import com.cbkj.rrh.view.LoadingProgress;


public class HttpRequestAsyncTask extends AsyncTask<Void, Void,HttpResponse> {
	private TaskListenerWithState mListenerWithState;
	private Context context;
	private HttpRequest mHttpRequest;
	private boolean mCommonLoading = true;
	
	public HttpRequestAsyncTask(boolean commonLoading,HttpRequest request,TaskListenerWithState listner,Context c) {
		this.mCommonLoading = commonLoading;
		this.mHttpRequest = request;
		this.context=c;
		this.mListenerWithState = listner;
		LogUtils.i("--------------"+request.getRequestUrl());
	}
	public HttpRequestAsyncTask(HttpRequest request,TaskListenerWithState listner,Context c) {
		this.mHttpRequest = request;
		this.context=c;
		this.mListenerWithState = listner;
		LogUtils.i("--------------"+request.getRequestUrl());
	}
	
	@Override
	protected HttpResponse doInBackground(Void... params) {
		if(!ConfigUtil.isConnect(context)){
			return new HttpResponse(HttpTaskState.STATE_NO_NETWORK_CONNECT);
		}
		try {
			if (mHttpRequest != null) {
//				if(type == ServerType.FileServer){	//如果是上传文件，请注意一下。
//					return new HttpResponse(HttpManager.getHttpRequest(mRequest,bNetWork),	HttpTaskState.STATE_OK);		
//				}else{
//					return new HttpResponse(HttpManager.postHttpRequest(mRequest,bNetWork),	HttpTaskState.STATE_OK);		
//				}
				
				return new HttpResponse(HttpManager.postHttpRequest(mHttpRequest),	HttpTaskState.STATE_OK);		
			}
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			return new HttpResponse(HttpTaskState.STATE_TIME_OUT);
		}
		catch (UnknownHostException e) {
			e.printStackTrace();
			return new HttpResponse(HttpTaskState.STATE_UNKNOWN);
		} catch (IOException e) {
			e.printStackTrace();
			return new HttpResponse(HttpTaskState.STATE_ERROR_SERVER);
		}

		return null;
	}
	
	@Override
	protected void onCancelled() {
		super.onCancelled();
	}
	
	@Override
	protected void onPostExecute(HttpResponse response) {
		super.onPostExecute(response);
		if(mCommonLoading){
			LoadingProgress.getInstance().dismiss();
		}
		
		if(null == response){
			return;
		}
		
		if(mListenerWithState!=null){
			mListenerWithState.onTaskOver(mHttpRequest, response);
		}
		
		switch (response.getState()) {
		case STATE_ERROR_SERVER:
			LoadingProgress.getInstance().dismiss();
			//BToast.show(context, "服务器发生故障");
			break;
		case STATE_NO_NETWORK_CONNECT:
			LoadingProgress.getInstance().dismiss();
			BToast.show(context, "网络未连接");
			break;
		case STATE_TIME_OUT:
			LoadingProgress.getInstance().dismiss();
			BToast.show(context, "连接超时");
			break;
		case STATE_UNKNOWN:
			LoadingProgress.getInstance().dismiss();
			BToast.show(context, "未知错误");
			break;
		case STATE_OK:
			break;
		default:
			LoadingProgress.getInstance().dismiss();
			BToast.show(context, "未知错误");
			break;
		}
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if(mCommonLoading){
			LoadingProgress.getInstance().show(context);
		}
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
	}
	
	public interface TaskListener{
		void onTaskOver(HttpRequest request,String response);
	}
	
	public interface TaskListenerWithState{
		void onTaskOver(HttpRequest request,HttpResponse response);
	}
}
