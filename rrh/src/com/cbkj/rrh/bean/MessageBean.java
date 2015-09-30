package com.cbkj.rrh.bean;


/**
 * @todo:消息类型
 * @date:2015-8-25 下午4:51:21
 * @author:hg_liuzl@163.com
 */
public class MessageBean {
	
	/***我发的单*/
	public static final int TYPE_MSG_SENT = 1;
	/***我接的单*/
	public static final int TYPE_MSG_RECIVE = 2;
	/***群推的行业单*/
	public static final int TYPE_MSG_POSITION = 3;
	
	
	public String status;//	消息状态	int	Y	消息状态:  0：未读；1：已读；
	public int type;//		消息类型	string	Y	推送消息类型 1：我接的单，2 ：我发的单；3：公共的单
	public String taskId;//		任务Id	int	Y	任务Id
	public String content;//		消息内容	string	Y	消息内容
	public String created;//		推送时间	date	Y	推送时间
	public String smallImg;	//头像
	public String nickName;	//昵称
	
//	
//	/**
//	 * @todo:从数据库查询群组
//	 * @date:2014-12-22 上午10:25:18
//	 * @author:hg_liuzl@163.com
//	 * @params:@param mDBHelper
//	 * @params:@return
//	 */
//	public static List<MessageBean> queryMessages(DBHelper mDBHelper,String userId){
//		   LogUtils.i("------from database----");
//	       Cursor c = mDBHelper.queryAndAll(DBHelper.TB_MESSAGE,DBHelper.CURRENT_USER_ID,userId);
//	       List<MessageBean>list = new ArrayList<MessageBean>();
//	       do {
//	    	   if(c != null && c.getCount()>0){
//	    		   int id = c.getInt(c.getColumnIndex(DBHelper.Message.M_ID));
//	    		   int type = c.getInt(c.getColumnIndex(DBHelper.Message.M_TYPE));
//	    		   String taskId = c.getString(c.getColumnIndex(DBHelper.Message.M_TASKID));
//	    		   String context = c.getString(c.getColumnIndex(DBHelper.Message.M_CONTEXT));
//	    		   String time = c.getString(c.getColumnIndex(DBHelper.Message.M_TIME));
//
//		           MessageBean message = new MessageBean();
//		           message.id = id;
//		           message.type = type;
//		           message.taskId = taskId;
//		           message.context = context;
//		           message.time = time;
//		           
//	           list.add(message);
//	           }
//		} while (c.moveToNext());
//	       
//	       if(null!=c)
//				c.close();
//	       
//	     return list;
//	}
//	
//	/**
//	 * 
//	 * @todo:将数据批量存储到数据库
//	 * @date:2014-12-22 上午10:36:29
//	 * @author:hg_liuzl@163.com
//	 * @params:
//	 */
//	public static void storeMessageBean(DBHelper dbHelper,ArrayList<MessageBean> messages) {
//			if(null==messages)
//				return;
//			
//			ArrayList<ContentValues> list = new ArrayList<ContentValues>();
//			for(MessageBean message:messages){
//				ContentValues cv = new ContentValues();
//				cv.put(DBHelper.Message.M_TYPE, message.type);
//				cv.put(DBHelper.Message.M_TASKID, message.taskId);
//				cv.put(DBHelper.Message.M_CONTEXT, message.context);
//				cv.put(DBHelper.Message.M_TIME, message.time);
//				cv.put(DBHelper.Message.M_READ, message.hasRead);
//				cv.put(DBHelper.CURRENT_USER_ID, message.currentUserId);
//				list.add(cv);
//			}
//			int count = dbHelper.insert(DBHelper.TB_MESSAGE, list);
//			LogUtils.i("-----------批量插入----"+count);
//	}
//	
//	
//	/**
//	 * 
//	 * @todo:插入一条数据
//	 * @date:2014-12-22 上午10:55:07
//	 * @author:hg_liuzl@163.com
//	 * @params:@param dbHelper
//	 * @params:@param MessageBean
//	 */
//	public static void insertMessageBean(DBHelper db,MessageBean message) {
//		ContentValues cv = new ContentValues();
//		cv.put(DBHelper.Message.M_TYPE, message.type);
//		cv.put(DBHelper.Message.M_TASKID, message.taskId);
//		cv.put(DBHelper.Message.M_CONTEXT, message.context);
//		cv.put(DBHelper.Message.M_TIME, message.time);
//		cv.put(DBHelper.Message.M_READ, message.hasRead);
//		cv.put(DBHelper.CURRENT_USER_ID, message.currentUserId);
//		db.insert(DBHelper.TB_MESSAGE, cv);
//		long count = db.insert(DBHelper.TB_MESSAGE, cv);
//		LogUtils.i("--------插入数据-------"+count);
//	}
//	
//	/**
//	 * 
//	 * @todo:删除一条数据
//	 * @date:2014-12-22 上午10:55:51
//	 * @author:hg_liuzl@163.com
//	 * @params:@param dbHelper
//	 * @params:@param MessageBean
//	 */
//	public static void deleteMessageBean(DBHelper dbHelper,String id) {
//		int count = dbHelper.deleteAll(DBHelper.TB_MESSAGE, new String[]{DBHelper.Message.M_ID},new String[]{id});
//		LogUtils.i("-------------删除数据------------"+count);
//	}
//	
//	/**
//	 * 
//	 * @todo:删除一条数据
//	 * @date:2014-12-22 上午10:55:51
//	 * @author:hg_liuzl@163.com
//	 * @params:@param dbHelper
//	 * @params:@param MessageBean
//	 */
//	public static void deleteMessageBeanByUserId(DBHelper dbHelper,String userId) {
//		int count = dbHelper.deleteAll(DBHelper.TB_MESSAGE, new String[]{DBHelper.CURRENT_USER_ID},new String[]{userId});
//		LogUtils.i("-------------删除数据------------"+count);
//	}
//	
//	/**
//	 * 
//	 * @todo:更改一条数据已读状态
//	 * @date:2015-8-25 下午6:57:12
//	 * @author:hg_liuzl@163.com
//	 * @params:@param dbHelper
//	 * @params:@param userid
//	 * @params:@param id
//	 */
//	public static void updateMessageHasRead(DBHelper dbHelper,int id){
//		ContentValues values = new ContentValues();
//		values.put(DBHelper.Message.M_READ, 1);
//		int count = dbHelper.update(DBHelper.TB_MESSAGE, values, DBHelper.Message.M_ID,new String[]{String.valueOf(id)});
//		LogUtils.i("-------------更新数据------------"+count);
//		
//	}
//	
}
