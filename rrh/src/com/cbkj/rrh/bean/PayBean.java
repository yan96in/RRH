package com.cbkj.rrh.bean;

import java.io.Serializable;

import android.app.Activity;

import com.cbkj.rrh.pay.IPayCallback;
import com.cbkj.rrh.pay.alipay.AliPayUtil;
import com.cbkj.rrh.pay.wxpay.WXPayUtil;

/**
 * @todo:支付的实体类
 * @date:2015-9-8 下午4:45:28
 * @author:hg_liuzl@163.com
 */
public class PayBean implements Serializable {
	
	/**
	 * @todo:TODO
	 * @date:2015-9-8 下午5:08:15
	 * @author:hg_liuzl@163.com
	 */
	private static final long serialVersionUID = 1L;
	/**支付实体类**/
	public static final String KEY_PAY_BEAN = "key_pay_bean";
	
	public enum PayType{
		ALIPAY,WXPAY
	}
	
	public PayType payWay;//支付方式
	public boolean payResult;				//支付结果 true成功，false,失败
	public String payTitle = "啦啦私活订单";	//支付标题
	public String payInfo = "啦啦私活订单";		//支付详情
	public String payMoney;					//支付金额
	
	public transient IPayCallback payCallback;
	
	public transient Activity mActivity;
	
	public void doPay(Activity activity,IPayCallback callback) {
		this.mActivity = activity;
		this.payCallback = callback;
		if (payWay == PayType.ALIPAY) {
			doAlipay();
		} else {
			doWxPay();
		}
	}
	/**
	 * @todo:阿里支付
	 * @date:2015-9-2 下午3:29:05
	 * @author:hg_liuzl@163.com
	 * @params:
	 */
	private void doAlipay() {
		AliPayUtil payUtil = new AliPayUtil(mActivity,payCallback);
		payUtil.submitPay(payTitle,payInfo,payMoney);
//		payUtil.submitPay(payTitle,payInfo,"0.01");
	}
	
	/**
	 * @todo:微信支付
	 * @date:2015-9-2 下午3:29:14
	 * @author:hg_liuzl@163.com
	 * @params:
	 */
	private void doWxPay() {
		WXPayUtil payUtil = new WXPayUtil(mActivity, payInfo, String.valueOf(Integer.valueOf(payMoney)*100));
//		WXPayUtil payUtil = new WXPayUtil(mActivity, payInfo, "1");		//注意这里金额要*100   微信支付是是以分为单位的
		payUtil.doSubmitOrder();
	}
}
