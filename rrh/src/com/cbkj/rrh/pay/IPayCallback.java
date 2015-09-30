package com.cbkj.rrh.pay;
/**
 * @todo:支付的回调函数
 * @date:2015-9-2 下午3:33:45
 * @author:hg_liuzl@163.com
 */
public interface IPayCallback {
	
	
	/**
	 * 
	 * @todo:支付成功
	 * @date:2015-9-2 下午3:41:12
	 * @author:hg_liuzl@163.com
	 * @params:
	 */
	void paySuccess();
	
	/**
	 * 
	 * @todo:支付失败
	 * @date:2015-9-2 下午3:41:23
	 * @author:hg_liuzl@163.com
	 * @params:
	 */
	void payFail();
	
}
