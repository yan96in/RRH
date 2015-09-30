package com.cbkj.rrh.bean;
/**
 * @todo:TODO
 * @date:2015-9-10 下午3:13:07
 * @author:hg_liuzl@163.com
 */
public class TradeBean {
	
	
	public String orderId;	// 订单号	
	public int payment;	// 支付方式	1.支付宝支付，2：微信支付
	public float charges;	//	交易金额
	public int type;	//	交易资金的流动方向  1：流入+  2：流出 -
	public String remark;	//	交易标注
	public String time;	//	交易时间
	
	
	
}
