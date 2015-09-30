package com.cbkj.rrh.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * 
 * @todo:封装数据库
 * @date:2014-12-20 下午3:52:54
 * @author:hg_liuzl@163.com
 */
public class DBHelper extends CommonDB {

	private final static String DATABASE_NAME = "bg.db";  //数据库名
	private final static int DATABASE_VERSION = 1;         //数据库版本
	/** 消息 表 */
	public final static String TB_MESSAGE = "tab_message";
	
	/**当前用户**/
	public final static String CURRENT_USER_ID = "current_user_id";//当前用户ID
	
//	/** 好友 表 */
//	public final static String TB_FRIEND = "tab_friend";
//	/** 群组表 */
//	public final static String TB_GROUP = "tab_group";
//	/** 群成员表 */
//	public final static String TB_GROUP_MEMBER = "tab_group_member";
	
	public DBHelper(Context context) {
		super(context,DATABASE_NAME,DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(Message.newCreateTableString());  //创建消息表
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(Message.newDeleteTableString());
		onCreate(db);
	}

	/**
	 * 
	 * @todo:消息表
	 * @date:2015-8-25 下午5:57:18
	 * @author:hg_liuzl@163.com
	 */
	public static class Message{
		
		public final static String M_ID = "id";
		public final static String M_TYPE = "type";
		public final static String M_TASKID = "taskId";
		public final static String M_CONTEXT = "context";
		public final static String M_TIME = "time";
		public final static String M_READ = "read";
		
		 public static String newCreateTableString() {
	            StringBuffer buffer = new StringBuffer(512);
	              buffer.append("create table ")
				        .append(TB_MESSAGE).append(" (")
				        .append(M_ID).append(" ").append("integer primary key autoincrement").append(",")
				        .append(CURRENT_USER_ID).append(" ").append("varchar").append(",")
				        .append(M_TYPE).append(" ").append("integer").append(",")
				        .append(M_TASKID).append(" ").append("varchar").append(",")
				        .append(M_CONTEXT).append(" ").append("varchar").append(",")
				        .append(M_READ).append(" ").append("integer").append(",")
				        .append(M_TIME).append(" ").append("varchar").append(")");
	            return buffer.toString();
	        }
	        
	        public static String newDeleteTableString() {
	            StringBuffer buffer = new StringBuffer(64);
	            buffer.append("DROP TABLE IF EXISTS ")
	                .append(TB_MESSAGE);
	            return buffer.toString();
	        }
	}
//	
//	
//	
//	
//    
//    /**
//     * 
//     * @todo:好友表
//     * @date:2014-12-20 下午3:12:04
//     * @author:hg_liuzl@163.com
//     */
//    public static class Friend{
//    	/**用户ID*/
//    	public final static String F_USERID = "userid";
//    	/**用户类型，是普通用户，还是管理员*/
//    	public final static String F_TYPE = "type";
//        /** 用户昵称 */
//        public final static String F_NAME = "name";
//        /** 用户性别*/
//        public final static String F_SEX = "sex";
//        /** 用户等级*/
//        public final static String F_LEVEL = "level";
//        /** 用户图片 */
//        public final static String F_PHOTO = "photo";
//        /** 用户签名*/
//        public final static String F_SIGNATURE = "signature";
//        
//        public static String newCreateTableString() {
//            StringBuffer buffer = new StringBuffer(512);
//              buffer.append("create table ")
//			        .append(TB_FRIEND).append(" (")
//			        .append(FD_ID).append(" ").append("integer primary key autoincrement").append(",")
//			        .append(CLOUMN_CURRENT_USER_ID).append(" ").append("varchar").append(",")
//			        .append(F_USERID).append(" ").append("varchar").append(",")
//			        .append(F_TYPE).append(" ").append("varchar").append(",")
//			        .append(F_NAME).append(" ").append("varchar").append(",")
//			        .append(F_SEX).append(" ").append("varchar").append(",")
//			        .append(F_LEVEL).append(" ").append("varchar").append(",")
//			        .append(F_PHOTO).append(" ").append("varchar").append(",")
//			        .append(F_SIGNATURE).append(" ").append("varchar").append(")");
//            return buffer.toString();
//        }
//        
//        public static String newDeleteTableString() {
//            StringBuffer buffer = new StringBuffer(64);
//            buffer.append("DROP TABLE IF EXISTS ")
//                .append(TB_FRIEND);
//            return buffer.toString();
//        }
//    }
//    
//    /**
//     * 
//     * @todo:群组实体类
//     * @date:2014-12-20 下午3:08:35
//     * @author:hg_liuzl@163.com
//     */
//    public static class Group{
//    	/**环信groupID*/
//    	public final static String G_HX_GROUPID = "hx_groupid";
//    	/**群ID*/
//    	public final static String G_ROOMID = "roomid";
//    	/**群名称*/
//    	public final static String G_NAME = "name";
//    	/** 群简介 */
//    	public final static String G_INTRO = "intro";
//        /** 群公告 */
//        public final static String G_NOTICE = "notice";
//        /** 群图片*/
//        public final static String G_PHOTO = "photo";
//        /** //群类型 0，固定群；1，临时群*/
//        public final static String G_GROUPTYPE = "grouptype";
//        
//        public static String newCreateTableString() {
//            StringBuffer buffer = new StringBuffer(512);
//              buffer.append("create table ")
//			        .append(TB_GROUP).append(" (")
//			        .append(FD_ID).append(" ").append("integer primary key autoincrement").append(",")
//			        .append(CLOUMN_CURRENT_USER_ID).append(" ").append("varchar").append(",")
//			        .append(G_HX_GROUPID).append(" ").append("varchar").append(",")
//			        .append(G_ROOMID).append(" ").append("varchar").append(",")
//			        .append(G_NAME).append(" ").append("varchar").append(",")
//			        .append(G_INTRO).append(" ").append("varchar").append(",")
//			        .append(G_NOTICE).append(" ").append("varchar").append(",")
//			        .append(G_PHOTO).append(" ").append("varchar").append(",")
//			        .append(G_GROUPTYPE).append(" ").append("varchar").append(")");
//            return buffer.toString();
//        }
//        
//        public static String newDeleteTableString() {
//            StringBuffer buffer = new StringBuffer(64);
//            buffer.append("DROP TABLE IF EXISTS ")
//                .append(TB_GROUP);
//            return buffer.toString();
//        }
//    }
//    
//    /**
//     * 
//     * @todo:群成员
//     * @date:2014-12-20 下午3:12:04
//     * @author:hg_liuzl@163.com
//     */
//    public static class GroupMember{
//    	/**环信GroupID*/
//    	public final static String GM_HX_GROUPID = "hx_group_id";
//    	/**群ID*/
//    	public final static String GM_GROUPID = "group_id";
//    	/**用户ID*/
//    	public final static String GM_USERID = "userid";
//    	/**用户类型，是普通用户，还是管理员*/
//    	public final static String GM_TYPE = "type";
//        /** 用户昵称 */
//        public final static String GM_NAME = "name";
//        /** 用户性别*/
//        public final static String GM_SEX = "sex";
//        /** 用户等级*/
//        public final static String GM_LEVEL = "level";
//        /** 用户图片 */
//        public final static String GM_PHOTO = "photo";
//        /** 用户签名*/
//        public final static String GM_SIGNATURE = "signature";
//        
//        public static String newCreateTableString() {
//            StringBuffer buffer = new StringBuffer(512);
//              buffer.append("create table ")
//			        .append(TB_GROUP_MEMBER).append(" (")
//			        .append(FD_ID).append(" ").append("integer primary key autoincrement").append(",")
//			        .append(CLOUMN_CURRENT_USER_ID).append(" ").append("varchar").append(",")
//			        .append(GM_HX_GROUPID).append(" ").append("varchar").append(",")
//			        .append(GM_GROUPID).append(" ").append("varchar").append(",")
//			        .append(GM_USERID).append(" ").append("varchar").append(",")
//			        .append(GM_TYPE).append(" ").append("varchar").append(",")
//			        .append(GM_NAME).append(" ").append("varchar").append(",")
//			        .append(GM_SEX).append(" ").append("varchar").append(",")
//			        .append(GM_LEVEL).append(" ").append("varchar").append(",")
//			        .append(GM_PHOTO).append(" ").append("varchar").append(",")
//			        .append(GM_SIGNATURE).append(" ").append("varchar").append(")");
//            return buffer.toString();
//        }
//        
//        public static String newDeleteTableString() {
//            StringBuffer buffer = new StringBuffer(64);
//            buffer.append("DROP TABLE IF EXISTS ").append(TB_GROUP_MEMBER);
//            return buffer.toString();
//        }
//    }
}

