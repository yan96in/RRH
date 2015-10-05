package com.cbkj.rrh.others.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.media.MediaPlayer;
import android.os.Environment;

/**
 * @todo:文件工具类
 * @date:2015-5-11 下午2:53:48
 * @author:hg_liuzl@163.com
 */
public class FileUtils {

	public static final String BASE_BEGIN = Environment.getExternalStorageDirectory().getAbsolutePath() + "/cool";
	public static final String BASE_END = ".jpg";
	public static final String CID_IMG_STRING_PATH = BASE_BEGIN + System.currentTimeMillis() + BASE_END;

	/***存储图像**/
	public static final String IMAGE_PATH = BASE_BEGIN + "/image/";
	
	/***存储视频 **/
	public static final String VIDEO_PATH = BASE_BEGIN+"/video/";
	
	/**用户头像*/
	public static final String HEAD_PATH = BASE_BEGIN + "/head/";
	
	/**存放视频最后一帧文件**/
	public static final String VIDEO_IMG_FRAME_PATH = VIDEO_PATH+"img_frame.jpg";
	
	/**文件转换成字节**/
	public static byte[] file2Bytes(String filePath) {
		byte[] buffer = null;
		try {
			File file = new File(filePath);
			if(null==file || !file.exists()){
				return null;
			}
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
			byte[] b = new byte[2048];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch(OutOfMemoryError e){
			e.printStackTrace();
		}
		return buffer;
	}
	
	
	
	
	
	/***
	 * 
	 * @todo:文件转换成字节
	 * @date:2015-5-11 下午2:55:18
	 * @author:hg_liuzl@163.com
	 * @params:@param file
	 * @params:@return
	 */
	public static byte[] file2Bytes(File file) {
		if(null==file || !file.exists()){
			return null;
		}
		return file2Bytes(file.getAbsolutePath());
	}
	
	
	/**
	 * 文件转换成M
	 * @param file
	 * @return
	 */
	public static float file2M(File file) {

		return formetFileSize(file2Bytes(file).length);
		
	}
	
	// 用当前时间给取得的图片命名
	public static String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}
	
	// 用当前时间给取得的视频命名
	public static String getVideoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'VIDEO'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".mp4";
	}
	
	/**
	 * 
	 * @todo:清除缓存
	 * @date:2015-5-14 下午4:15:56
	 * @author:hg_liuzl@163.com
	 * @params:@return
	 */
    public static boolean ClearCache(){
    	String[] dirs={IMAGE_PATH,VIDEO_PATH,HEAD_PATH};
    	for(String dir:dirs){
    		File file = new File(dir); 
    		if(file!=null&&file.exists()){
        		for(File temp:file.listFiles()){
        			if(temp.isDirectory()){
        				for(File temp1:temp.listFiles()){
        					if(!temp1.delete()){
                    			return false;
                    		}
        				}
        			}else{
        				if(!temp.delete()){
                			return false;
                		}
        			}
            	}
        	}
    	}
    	return true;
    }
	
	/**
	 * 获取文件大小
	 * @param filePath:文件的路径
	 * @return
	 */
	public static String getFileSize(String filePath){
		String fileSize = "";
		File targetFile = new File(filePath);
		if(targetFile.exists() && targetFile.isFile()){
			long size = targetFile.length();
			fileSize =String.valueOf(formetFileSize(size));
		}
		return fileSize;
	}
	
//	/**
//	 * 格式化文件大小
//	 * 	 * @param fileS，文件的路径
//	 * @return 文件大小的字符串
//	 */
//	public static String formetFileSize(long fileSize){
//		String result="";
//		if((fileSize/1024)>=1){
//			fileSize/=1024;
//			if(fileSize/1024>=1){
//				result=new BigDecimal(fileSize*1.0/1024).setScale(2,BigDecimal.ROUND_HALF_UP).floatValue()+" MB";
//			}
//			else{
//				result=fileSize+" kb";
//			}
//		}
//		else{
//			result=fileSize+" B";
//		}
//		return result;
//	}
	
	/**
	 * 格式化文件大小
	 * @param fileSize
	 * @return
	 */
	public static float formetFileSize(long fileSize){
		float result = 0.0f ;
		if((fileSize/1024)>=1){
			fileSize/=1024;
			result=new BigDecimal(fileSize*1.0/1024).setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
		}
		return result;
	}
	
    /**
     * 计算视频文件内容的播放时间
     * @param mPlayer MediaPlayer类型
     * @param videoPath 文件路径
     * @return 0:0:00样式
     */
    public static String getVideoFileTime(MediaPlayer mPlayer, String videoPath){
    	if(mPlayer ==null){
    		mPlayer = new MediaPlayer();
    		try {
    			mPlayer.setDataSource(videoPath);
    			mPlayer.prepare();
    		} catch (IllegalArgumentException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (IllegalStateException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}

    	int  v_time = mPlayer.getDuration()/1000;
    	if(v_time < 10){
    		return "0:0" + v_time;
    	}else if(v_time <60){
			return "0:" + v_time;
		}else {
			return null;
		}
    }
	
    
	/**
	 * 删除指定路径的文件
	 */
	public static void deleteGeneratedFile(String path) {
		if(path == null || path.equals("")){
			return;
		}
		File videoFile = new File(path);
		if(videoFile.exists() && videoFile.isFile()){
			videoFile.delete();
		}
	}
	

	/**
	 * 将一个文件拷贝到另外一个地方
	 * 
	 * @param sourceFile
	 *            源文件地址
	 * @param destFile
	 *            目的地址
	 * @return
	 */
	public static boolean copyFile(File sourceFile, File destFile)
	{
		return copyFile(sourceFile.getAbsolutePath(), destFile.getAbsolutePath(), true);
	}	
	
	/**
	 * 将一个文件拷贝到另外一个地方
	 * 
	 * @param sourceFile
	 *            源文件地址
	 * @param destFile
	 *            目的地址
	 * @param shouldOverlay
	 *            是否覆盖
	 * @return
	 */
	public static boolean copyFile(String sourceFile, String destFile,
			boolean shouldOverlay)
	{
		try
		{
			if (shouldOverlay)
			{
				deleteFile(destFile);
			}
			FileInputStream fi = new FileInputStream(sourceFile);
			writeFile(destFile, fi);
			return true;
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 删除一个文件
	 * 
	 * @param filePath
	 *            要删除的文件路径名
	 * @return true if this file was deleted, false otherwise
	 */
	public static boolean deleteFile(String filePath)
	{
		try
		{
			File file = new File(filePath);
			if (file.exists())
			{
				return file.delete();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 创建一个文件，创建成功返回true
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean createFile(String filePath)
	{
		try
		{
			File file = new File(filePath);
			if (!file.exists())
			{
				if (!file.getParentFile().exists())
				{
					file.getParentFile().mkdirs();
				}

				return file.createNewFile();
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 从一个输入流里写文件
	 * 
	 * @param destFilePath
	 *            要创建的文件的路径
	 * @param in
	 *            要读取的输入流
	 * @return 写入成功返回true,写入失败返回false
	 */
	public static boolean writeFile(String destFilePath, InputStream in)
	{
		try
		{
			if (!createFile(destFilePath))
			{
				return false;
			}
			FileOutputStream fos = new FileOutputStream(destFilePath);
			int readCount = 0;
			int len = 1024;
			byte[] buffer = new byte[len];
			while ((readCount = in.read(buffer)) != -1)
			{
				fos.write(buffer, 0, readCount);
			}
			fos.flush();
			if (null != fos)
			{
				fos.close();
				fos = null;
			}
			if (null != in)
			{
				in.close();
				in = null;
			}
			return true;
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return false;
	}

	
	
}
