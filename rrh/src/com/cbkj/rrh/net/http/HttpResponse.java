package com.cbkj.rrh.net.http;

import org.json.JSONObject;



public class HttpResponse {
	public enum HttpTaskState{
		STATE_OK,
		STATE_NO_NETWORK_CONNECT,
		STATE_TIME_OUT,
		STATE_UNKNOWN,
		STATE_ERROR_SERVER,
	}

	public HttpResponse(HttpTaskState state) {
		this.state=state;
	}
	
	public HttpResponse(HttpResponse response,HttpTaskState state) {
		this.result = response;
		this.state=state;
	}

	public HttpResponse result;

	private HttpTaskState state;
	public HttpTaskState getState() {
		return state;
	}
	
	
	public String success = "false";
	public String resultCode;
	public String errorMsg;
	/**字符串对象*/
	public String strBody;
	/**json对象*/
	public JSONObject jsonBody;

	public boolean getSuccess() {
		if (success.equalsIgnoreCase("true")) {
			return true;
		}else{
			return false;
		}
	}

	public String getResultCode() {
		return resultCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public String getStrBody() {
		return strBody;
	}

	public JSONObject getJsonBody() {
		return jsonBody;
	}
	
}
