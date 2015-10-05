package com.cbkj.rrh.net.http;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.cbkj.rrh.R;
import com.cbkj.rrh.others.utils.DeviceUtils;

/**
 * @todo:http请求的基类
 * @date:2015-4-20 上午10:35:36
 * @author:hg_liuzl@163.com
 */
public class HttpRequest {
	
	private String requestUrl;
	
	private JSONObject body;
	
	private int bodyLength = 0;
	
	private File file;	//需要上传的文件
	
	private String success;	//获取服务器返回的状态，成功或者失败
	
	private String errorMsg;	//获取服务器返回的返回错误信息
	
	private String strBodyJson;	//获取body中的
	
	private static HttpRequest instance = null;
	
	public static synchronized HttpRequest getInstance(){
		if (null == instance) {
			instance = new HttpRequest();
		}
		return instance;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getStrBodyJson() {
		return strBodyJson;
	}

	public void setStrBodyJson(String strBodyJson) {
		this.strBodyJson = strBodyJson;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public boolean getSuccess() {
		return success.equalsIgnoreCase("true");
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public int getBodyLength() {
		return bodyLength;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public JSONObject getBody() {
		return body;
	}

	public void setBody(JSONObject body,Context context) {
		this.body = body;
		setCommonData(context);
	}

	/**
	 * 
	 * @todo:设置通用参数,设备信息，设备系统，设备渠道
	 * @date:2015-4-20 上午10:44:43
	 * @author:hg_liuzl@163.com
	 * @params:
	 */
	private void setCommonData(Context context) {
	//	device_info	设备信息		Y	Android：imei或Ios: udid
	//	os	终端系统		Y	Android或Ios
	//	app_channel	APP所属渠道		Y	Ios渠道一般是appstore，Android渠道:360，baidu,tencent,xiaomi
		String deviceInfo = DeviceUtils.getImei(context);
		String os = "Android";
		String appChannel = context.getString(R.string.app_channel);
		try {
			body.put("appDeviceInfo", deviceInfo);
			body.put("appSystem", os);
			body.put("appChannel", appChannel);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	

	
	/**
	 * 
	 * @todo:获取参数的总长度
	 * @date:2015-4-20 下午2:53:36
	 * @author:hg_liuzl@163.com
	 * @params:@param dos
	 * @params:@throws IOException
	 */
	public int getMeasureLength()
	{
		this.bodyLength = body.toString().getBytes().length;
		return bodyLength;
	}
	
	/**
	 * 把请求的数据转换成字节流
	 * @return
	 */
	public byte[] toBytes()
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try
		{
			
			if (getBody() != null)
				dos.write(getBody().toString().getBytes());
			byte[] bs = bos.toByteArray();
			return bs;
		} catch (IOException e)
		{
			e.printStackTrace();
			return null;
		} finally
		{
			if (dos != null)
				try
				{
					dos.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				} finally
				{
					dos = null;
				}
			if (bos != null)
				try
				{
					bos.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				} finally
				{
					bos = null;
				}
		}

	}

	/**
	 * 发送给服务端
	 * @param dos
	 * @throws IOException
	 */
	public void send(DataOutputStream dos) throws IOException
	{
		if (dos == null)
			return;
		byte[] bs = toBytes();
		dos.write(bs);
		dos.flush();
	}
	
	
	/**
	 * 
	 * 解析服务器返回的数据
	 * @param bs
	 * @param length
	 * @return
	 * @throws JSONException
	 */
	public void parseData(byte[] bs,int length) throws JSONException
	{
		ByteArrayInputStream bis = new ByteArrayInputStream(bs);
		DataInputStream dis = new DataInputStream(bis);
		try
		{
			byte[] bsbody = new byte[length];
			dis.read(bsbody, 0, bsbody.length);
			String strbody = new String(bsbody, "utf-8");
			JSONObject object = new JSONObject(strbody);
			setErrorMsg(object.optString("msg"));
			setSuccess(object.optString("success"));
			setStrBodyJson(object.optString("body"));
		} catch (IOException e)
		{
			e.printStackTrace();
			return;
		} finally
		{
			try
			{
				dis.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			try
			{
				bis.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return;
	}
}
