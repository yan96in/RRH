package com.cbkj.rrh.net.http;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

import com.cbkj.rrh.net.http.HttpResponse.HttpTaskState;
/**
 * 
 * @todo:Http网络请求
 * @date:2015-4-20 下午3:23:38
 * @author:hg_liuzl@163.com
 */
public class HttpManager {

	public static final String STR_ENCODE = "utf-8";
	//超时时间
	public static final int CONN_TIMEOUT = 30 * 1000;	
	
	@SuppressWarnings("static-access")
	public static HttpResponse postHttpRequest(HttpRequest request)throws IOException {
		HttpResponse response = null;
		URL url = new URL(request.getRequestUrl());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Content-Length", String.valueOf(request.getMeasureLength()));
		conn.setReadTimeout(CONN_TIMEOUT);
		conn.setConnectTimeout(CONN_TIMEOUT);
		conn.setRequestMethod("POST");
		conn.setDoInput(true);
		conn.setDoOutput(true);
		
		DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
		request.send(dos);
		conn.connect();
		
		
		
		if(conn.getResponseCode() == 200){
			response = parseData(conn.getInputStream());
		}
		
		if(null != dos){
			try {
				dos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				dos = null;
			}
		}
		return response;
	}
	
	
	
	
	public static HttpResponse getHttpRequest(HttpRequest request)throws IOException {
		HttpResponse response = null;
		FileInputStream stream = null;
		InputStream is = null;
		OutputStream ot = null;
		File f = request.getFile();
		if(!f.exists()){
			return response;
		}
		try
		{
			URL url = new URL(request.getRequestUrl());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			
			stream = new FileInputStream(f);
			byte[] file_arr = new byte[(int) f.length()];
			stream.read(file_arr);
			ot = conn.getOutputStream();
			ot.write(file_arr);
			ot.flush();
			conn.connect();
			if (200 == conn.getResponseCode())
			{
				response = parseData(conn.getInputStream());
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			if (stream != null)
			{
				try
				{
					stream.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
				stream = null;
			}
			if (is != null)
			{
				try
				{
					is.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
				is = null;
			}
			if (ot != null)
			{
				try
				{
					ot.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
				ot = null;
			}
		}
		return response;
	}
	
	// Reads an InputStream and converts it to a String.
	private static String readData(InputStream stream){
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(stream,Charset.forName(STR_ENCODE)));
		String str;
		try {
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String(sb.toString());
	}
	
	
	/**
	 * 
	 * 解析服务器返回的数据
	 * @param bs
	 * @param length
	 * @return
	 * @throws JSONException
	 */
	private static HttpResponse parseData(InputStream is)
	{
		String strbody = readData(is);
		HttpResponse response = new HttpResponse(HttpTaskState.STATE_OK);
		try {
			JSONObject object = new JSONObject(strbody);
			response.success = object.optString("success");
			response.resultCode = object.optString("resultCode");
			response.errorMsg = object.optString("errorMsg");
			response.strBody = object.optString("body");
			response.jsonBody = new JSONObject(response.strBody);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return response;
	}
	
}
