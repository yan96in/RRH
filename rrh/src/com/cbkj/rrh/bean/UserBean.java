package com.cbkj.rrh.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @todo:用户实体类
 * @date:2015-4-24 上午9:53:02
 * @author:hg_liuzl@163.com
 */
public class UserBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public static final String USER_BEAN = "userbean";
	
	/**用户编号*/
	public static final String USER_ID = "user_id";
	
	/**用户认证状态*/
	public static final String USER_IS_EMPLOYEE = "user_is_employee";
	
	/**用户手机号**/
	public static final String USER_TELEPHONE = "user_telephone";
	
	
	
	public String userId = "";//	用户Id	string	Y	用户Id
	public String telephone = "";//		手机号码	string	Y	手机号码
	public String nickName = "";//		昵称	string	Y	昵称
	public int gender = 0;//		性别	int	Y	0：女性；1男性；3：未知
	public int position = 0;//		职业	int	N	职业Id
	public String smallImg = "";//		小头像	string	Y	小头像后缀
	public String bigImg = "";//		大头像	string	Y	大头像后缀
	public String created = "";//		注册时间	datetime	Y	注册时间
	public String lastLogin = "";//		最后一次登录	datetime	Y	用户最后一次登录时间
	public String aliPayNo = "";			//支付宝账号
	public String intro = "";		//自我评价
	public List<CertificationBean> certificates = null; //证书图片
	public List<ScoreBean> scores = null;	//接单人对我的评分
	public List<ScoreBean> scores2 = null;	//发单人对我的评分
	public int openPush; //0：不开启；1：开启
	/***
	 * 未通过接单人认证：0；已通过接单人认证：1
	 */
//	public int isEmployee = 0;
	
	/***（申请接单人验证状态） 1：没有申请过；2：申请过正在审核中；3：申请过并通过审核了 **/
	public int employeeStatus = 1;

	
	/***
	 * 
	 * @todo:获取评分
	 * @date:2015-8-12 下午7:49:36
	 * @author:hg_liuzl@163.com
	 * @params:@return
	 */
	public String getScores(List<ScoreBean> listScore){
		StringBuffer sb = new StringBuffer();
		for (int i = 1; i <= listScore.size();i++) {
			final ScoreBean sBean = listScore.get(i-1);
			sb.append(i).append(".").append(sBean.rulesName).append(sBean.count).append("次").append("\n");
		}
		return sb.toString();
	}
	
	
	
	

}
