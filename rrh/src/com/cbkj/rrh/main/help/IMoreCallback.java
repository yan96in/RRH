package com.cbkj.rrh.main.help;
/**
 * 
 * @todo:更多操作
 * @date:2015-4-28 上午10:31:41
 * @author:hg_liuzl@163.com
 */
public interface IMoreCallback {
	
	/**关注**/
	void attentionAction(Object object);
	
	/**收藏*/
	void collectAction(Object object);
	
	/**举报**/
	void reportAction(Object object);
}
