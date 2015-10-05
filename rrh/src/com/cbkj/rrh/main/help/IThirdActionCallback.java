package com.cbkj.rrh.main.help;

import java.util.Map;

import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * @todo:第三方动作回调
 * @date:2015-5-6 下午5:31:14
 * @author:hg_liuzl@163.com
 */
public interface IThirdActionCallback {
	
	/**获取第三方的数据*/
	void setInfo(Map<String,Object> map);
	
	/**第三方操作，绑定或者取消绑定**/
	void actionType(final SHARE_MEDIA platform);
	
}
