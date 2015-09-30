package com.cbkj.rrh.pay.wxpay;

import java.io.StringReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Xml;

import com.cbkj.rrh.R;
import com.cbkj.rrh.utils.LogUtils;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;


/**
 * 
 * @todo:微信支付帮助类，只需调用doSubmitOrder()
 * @date:2015-9-7 下午12:03:48
 * @author:hg_liuzl@163.com
 */
public class WXPayUtil {
	private PayReq req = null;
	private IWXAPI msgApi = null;
	private Map<String,String> resultunifiedorder = null;
	private StringBuffer sb = null;
	private Activity mActivity = null;
	private String mProductInfo = null;
	private String mTotalMoney = null;
	
	public WXPayUtil(final Activity activity,String productInfo,String money){
		this.mActivity = activity;
		this.req = new PayReq();
		this.msgApi = WXAPIFactory.createWXAPI(mActivity, null);
		this.msgApi.registerApp(Constants.APP_ID);
		this.sb = new StringBuffer();
		this.mProductInfo = productInfo;
		this.mTotalMoney = money;
	}
	
	/**
	 * 
	 * @todo:生成预支付订单
	 * @date:2015-9-7 上午11:52:40
	 * @author:hg_liuzl@163.com
	 * @params:
	 */
	public void doSubmitOrder() {
		GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
		getPrepayId.execute();
	}
	
	/**
	 * 
	 * @todo:生成签名参数
	 * @date:2015-9-7 上午11:48:04
	 * @author:hg_liuzl@163.com
	 * @params:
	 */
	private void genPayReq() {
		req.appId = Constants.APP_ID;
		req.partnerId = Constants.MCH_ID;
		req.prepayId = resultunifiedorder.get("prepay_id");
		req.packageValue = "Sign=WXPay";
		req.nonceStr = genNonceStr();
		req.timeStamp = String.valueOf(genTimeStamp());

		List<NameValuePair> signParams = new LinkedList<NameValuePair>();
		signParams.add(new BasicNameValuePair("appid", req.appId));
		signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
		signParams.add(new BasicNameValuePair("package", req.packageValue));
		signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
		signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
		signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
		req.sign = genAppSign(signParams);
		sb.append("sign\n"+req.sign+"\n\n");
		
		sendPayReq();
	}
	
	
	/**
	 * @todo:启动支付
	 * @date:2015-9-7 上午11:49:51
	 * @author:hg_liuzl@163.com
	 */
	private void sendPayReq() {
		msgApi.registerApp(Constants.APP_ID);
		msgApi.sendReq(req);
	}

	
	/**
	 * 
	 * @todo:生成签名包
	 * @date:2015-9-7 上午11:57:15
	 * @author:hg_liuzl@163.com
	 * @params:@param params
	 * @params:@return
	 */
	private String genPackageSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(Constants.API_KEY);
		String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
		return packageSign;
	}
	
	
	
	private String genAppSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(Constants.API_KEY);

        this.sb.append("sign str\n"+sb.toString()+"\n\n");
		String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
		return appSign;
	}
	
	
	private String toXml(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		for (int i = 0; i < params.size(); i++) {
			sb.append("<"+params.get(i).getName()+">");
			sb.append(params.get(i).getValue());
			sb.append("</"+params.get(i).getName()+">");
		}
		sb.append("</xml>");
		return sb.toString();
	}

	/**
	 * 
	 * @todo:异步生成预订单
	 * @date:2015-9-7 上午11:54:13
	 * @author:hg_liuzl@163.com
	 */
	private class GetPrepayIdTask extends AsyncTask<Void, Void, Map<String,String>> {
		private ProgressDialog dialog;
		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(mActivity, mActivity.getString(R.string.app_tip), mActivity.getString(R.string.getting_prepayid));
		
		}
		@Override
		protected void onPostExecute(Map<String,String> result) {
			if (dialog != null) {
				dialog.dismiss();
			}
			sb.append("prepay_id\n"+result.get("prepay_id")+"\n\n");
			resultunifiedorder=result;
			genPayReq();
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected Map<String,String>  doInBackground(Void... params) {
			String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
			String entity = genProductArgs();
			byte[] buf = Util.httpPost(url, entity);
			String content = new String(buf);
			Map<String,String> xml=decodeXml(content);
			return xml;
		}
	}



	public Map<String,String> decodeXml(String content) {

		try {
			Map<String, String> xml = new HashMap<String, String>();
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(new StringReader(content));
			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {

				String nodeName=parser.getName();
				switch (event) {
					case XmlPullParser.START_DOCUMENT:

						break;
					case XmlPullParser.START_TAG:

						if("xml".equals(nodeName)==false){
							//实例化student对象
							xml.put(nodeName,parser.nextText());
						}
						break;
					case XmlPullParser.END_TAG:
						break;
				}
				event = parser.next();
			}

			return xml;
		} catch (Exception e) {
		}
		return null;

	}


	private String genNonceStr() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
	}
	
	private long genTimeStamp() {
		return System.currentTimeMillis() / 1000;
	}
	


	private String genOutTradNo() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
	}
	

   /**
    * 
    * @todo:生成订单
    * @date:2015-9-7 下午12:07:00
    * @author:hg_liuzl@163.com
    * @params:@return
    * 
    *   公众账号ID	appid	是	String(32)	wx8888888888888888	微信分配的公众账号ID（企业号corpid即为此appId）
		
		商户号	mch_id	是	String(32)	1900000109	微信支付分配的商户号
		
		设备号	device_info	否	String(32)	013467007045764	终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB"
		
		随机字符串	nonce_str	是	String(32)	5K8264ILTKCH16CQ2502SI8ZNMTM67VS	随机字符串，不长于32位。推荐随机数生成算法
	
		签名	sign	是	String(32)	C380BEC2BFD727A4B6845133519F3AD6	签名，详见签名生成算法
		
		商品描述	body	是	String(32)	Ipad mini  16G  白色	商品或支付单简要描述
		
		商品详情	detail	否	String(8192)	Ipad mini  16G  白色	商品名称明细列表
		
		附加数据	attach	否	String(127)	说明	附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
		
		商户订单号	out_trade_no	是	String(32)	1217752501201407033233368018	商户系统内部的订单号,32个字符内、可包含字母, 其他说明见商户订单号
		
		货币类型	fee_type	否	String(16)	CNY	符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
	
		总金额	total_fee	是	Int	888	订单总金额，只能为整数，详见支付金额
		
		终端IP	spbill_create_ip	是	String(16)	8.8.8.8	APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
		
		交易起始时间	time_start	否	String(14)	20091225091010	订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
		
		交易结束时间	time_expire	否	String(14)	20091227091010	
		
		订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则
	
		注意：最短失效时间间隔必须大于5分钟
		
		商品标记	goods_tag	否	String(32)	WXG	商品标记，代金券或立减优惠功能的参数，说明详见代金券或立减优惠
		
		通知地址	notify_url	是	String(256)	http://www.baidu.com	接收微信支付异步通知回调地址
		
		交易类型	trade_type	是	String(16)	JSAPI	取值如下：JSAPI，NATIVE，APP，WAP,详细说明见参数规定
	
		商品ID	product_id	否	String(32)	12235413214070356458058	trade_type=NATIVE，此参数必传。此id为二维码中包含的商品ID，商户自行定义。
		
		指定支付方式	limit_pay	否	String(32)	no_credit	no_credit--指定不能使用信用卡支付
		
		用户标识	openid	否	String(128)	oUpF8uMuAJO_M2pxb1Q9zNjWeS6o	trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识。openid如何获取，可参考【获取openid】。企业号请使用【企业号OAuth2.0接口】获取企业号内成员userid，再调用【企业号userid转openid接口】进行转换
    */
	
	
	private String genProductArgs() {
		StringBuffer xml = new StringBuffer();
		
		try{
			String	nonceStr = genNonceStr();
			xml.append("</xml>");
            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
            
			packageParams.add(new BasicNameValuePair("appid", Constants.APP_ID));
			packageParams.add(new BasicNameValuePair("body", mProductInfo));
			packageParams.add(new BasicNameValuePair("mch_id", Constants.MCH_ID));
			packageParams.add(new BasicNameValuePair("nonce_str", nonceStr)); //随机字符串
			packageParams.add(new BasicNameValuePair("notify_url", "http://121.40.35.3/test"));//通知地址	notify_url
			packageParams.add(new BasicNameValuePair("out_trade_no",genOutTradNo()));
			packageParams.add(new BasicNameValuePair("spbill_create_ip","127.0.0.1"/*getIPAddress()*/));
			packageParams.add(new BasicNameValuePair("total_fee", mTotalMoney));	//总金额
			packageParams.add(new BasicNameValuePair("trade_type", "APP"));	//终端交易类型
			
			String sign = genPackageSign(packageParams);
			packageParams.add(new BasicNameValuePair("sign", sign));

		    String xmlstring = toXml(packageParams);

		   return new String(xmlstring.toString().getBytes(), "ISO8859-1");//必须转码，不然无法生成签名
		   
	} catch (Exception e) {
		return null;
	}
		
	}
	
	/**
	 * 获取本机的ip地址
	 * @return
	 */
	private String getIPAddress(){
		try {
			for(Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();){
				NetworkInterface intf = (NetworkInterface)en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = (InetAddress)enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException e) {
			LogUtils.w("getLocalIpAddress",e);
		}
		return null;
	}

}

