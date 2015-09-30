package com.cbkj.rrh.bean;

import java.io.Serializable;



/**
 * 
 * @todo:评论的实体类
 * @date:2015-5-9 下午2:19:41
 * @author:hg_liuzl@163.com
 */
public class CommentBean implements Serializable
{

	public static final String KEY_COMMENT_BEAN = "comment_bean";
	
	public static final String KEY_COMMENT_WORD = "comment_bean_word";
	
	private static final long serialVersionUID = 1L;
	public int commentId;			//回复记录ID
    public String userId;//		评论者ID	
    public String nickName;//			评论者昵称
    public String imageUrl;//			评论者小头像
    public String content;//			
    public String created;//		评论时间	
}
