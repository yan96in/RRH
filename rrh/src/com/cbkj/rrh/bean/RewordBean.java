package com.cbkj.rrh.bean;
/**
 * @todo:打赏人物类
 * @date:2015-5-20 上午9:19:53
 * @author:hg_liuzl@163.com
 */
public class RewordBean {
	
	public String userId;//	用户Id	string	打赏人Id
	public int gender;//	性别	int	打赏人性别，0是女的，1是男的，2未知
	public int followers;//	粉丝数	int	打赏人粉丝数
	public int friends;//	关注数	int	打赏人关注数
	public int favourites;//	收藏数	int	打赏人收藏数
	public int moneys;//	萌币	int	打赏人萌币数
	public String imageUrl;//	头像	string	打赏人小头像
	public String bigImageUrl;//	大头像	string	打赏人大头像
	public String nickName;//	昵称	string	打赏人昵称
	public String signature;//	个性签名	string	打赏人个性签名
	public int giveMoneys;//	打赏的萌币数	int	此用户给媒体（图片、视频）打赏的萌币数量
	
	public RewordBean() {
		super();
	}


	public RewordBean(String userId, String nickName, int giveMoneys) {
		super();
		this.userId = userId;
		this.nickName = nickName;
		this.giveMoneys = giveMoneys;
	}
	
	
	
	
}
