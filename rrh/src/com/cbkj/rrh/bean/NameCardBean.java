package com.cbkj.rrh.bean;

import java.util.List;

/**
 * @todo:发单人与接单人的名片
 * @date:2015-7-30 上午10:39:24
 * @author:hg_liuzl@163.com
 */
public class NameCardBean {
	
	/**User ID**/
	public static final String KEY_USER_ID = "key_user_id";
	
	public String userId;//	用户Id	string	Y	用户Id
	public String nickName;//		昵称	string	Y	昵称
	public int gender;//		性别	int	Y	0：女性；1男性；3：未知
	public String smallImg;//		小头像	string	Y	小头像后缀
	public String telephone;//		联系电话	string	Y	发单人留下的联系电话
	public String fullName;//		姓名	string	N	真实姓名
	public String address;//		联系地址	string	N	联系地址
	public String intro;//		自我介绍	string	N	自我介绍 自我评价
	public List<ScoreBean> scores;//		评分次数	json list	Y	雇主给接单人评分的次数,数组形式：[{"rulesName":"非常满意","count":2},{"rulesName":"很满意","count":23},{"rulesName ":"满意","count":45},{"rulesName":"不满意","count":5}]
    public List<CertificationBean> certificates;//		证书图片	json list	N	数组形式：[{"bigImg":"http://d.com/12323.jpg","smallImg":"http://d.com/12323_s.jpg"},{"bigImg":"http://d.com/12323.jpg","smallImg":"http://d.com/12323_s.jpg"}]
	public int successLTimes;//		成功接单数	int	Y	成功发单次数
	public int allLTimes;//		总接单数	int	Y	总发单数
	public boolean certified;//		已认证	bool	N	接单人是否已认证？
	
	public String created;//		注册时间	datetime	Y	注册时间
	public int successRTimes;//	成功接单数	int	Y	成功接单次数
	public int allRTimes;//	总接单数	int	Y	总接单数
	
	
	/***
	 * 
	 * @todo:获取评价
	 * @date:2015-8-12 下午7:49:36
	 * @author:hg_liuzl@163.com
	 * @params:@return
	 */
	public String getScores(){
		StringBuffer sb = new StringBuffer();
		for (int i = 1; i <= scores.size();i++) {
			final ScoreBean sBean = scores.get(i-1);
			sb.append(i).append(".").append(sBean.rulesName).append(sBean.count).append("次").append("\n");
		}
		return sb.toString();
	}
	
}
