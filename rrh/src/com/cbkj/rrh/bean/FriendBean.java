package com.cbkj.rrh.bean;

/**
 * 
 * @todo:我的好友类，关注与粉丝
 * @date:2015-4-28 下午12:04:51
 * @author:hg_liuzl@163.com
 */
public class FriendBean {
	
	/**1查看粉丝，2查看关注**/
	public static final int FANS = 1;
	public static final int ATTENTION = 2;
	public static final int NONE = 3;
	
	
	/**related	相互关系	int	0：不是；1：是*/
	public static final int TYPE_RELATED_NO = 0;
	public static final int TYPE_RELATED_YES = 1;
	
	/**type	类型	int		1:建立，2:取消*/
	public static final int TYPE_ACTION_CREATE = 1;
	public static final int TYPE_ACTION_CANCEL = 2;
	
	public String userId;//			
	public String nickName;//				
	public int gender;//				0是女的，1是男的，3其他
	public String signature;//				
	public String imageUrl;//		小头像		需要加地址前缀
	public String bigImageUrl;//		大头像		需要加地址前缀
	public int related;//		相互关系	int	0：不是；1：是
}

