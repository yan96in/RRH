package com.cbkj.rrh.main;

import java.util.List;
import java.util.Stack;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.cbkj.rrh.R;
import com.cbkj.rrh.bean.WorkBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.ImageDownloader.Scheme;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.tencent.bugly.crashreport.CrashReport;

public class CBApp extends Application {
	
	/**职业分类**/
	public static List<WorkBean> workbeans = null;
	
//	public static boolean isUserLogin = false;
//	/**存放用户资料*/
//	public static UserBean userBean;
//	
//	/**存放用户编号*/
//	public static String mUserId = "";
	
	private static CBApp instance = null;
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		
//		/**腾讯 bug监控工具**/
//		CrashReport.initCrashReport(this, Const.TX_BUGLY_ID, false);
		
        instance = this;
        /**图片加载*/
        initImgDisPlay();
	}
	
	public static CBApp getInstance() {
		return instance;
	}
	
	/**
	 * 展示图片
	 */
	private DisplayImageOptions options = null;
	private DisplayImageOptions optionsRound = null;
	private void initImgDisPlay() {
		options = new DisplayImageOptions.Builder() //
		.showImageOnLoading(R.drawable.bg_img_load)
		.showImageForEmptyUri(R.drawable.bg_img_load) //
		.showImageOnFail(R.drawable.bg_img_load) //
		.cacheInMemory(true) //
		.cacheOnDisk(true) //
		.build();//
		
		optionsRound = new DisplayImageOptions.Builder() //
		.showImageOnLoading(R.drawable.bg_img_load)
		.showImageForEmptyUri(R.drawable.bg_img_load) //
		.showImageOnFail(R.drawable.bg_img_load) //
		.cacheInMemory(true) //
		.cacheOnDisk(true) //
		.displayer(new RoundedBitmapDisplayer(10))
		.resetViewBeforeLoading(true)
		.build();//
		
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())//
		.threadPriority(Thread.NORM_PRIORITY - 2)
		.denyCacheImageMultipleSizesInMemory()
		.tasksProcessingOrder(QueueProcessingType.LIFO)
		.diskCacheFileCount(1000)
		.diskCacheSize(100*1024*1024)
		.build();//
		ImageLoader.getInstance().init(config);
	
	}
	
	
	 private static Stack<Activity> m_activityStack; // activity栈

    /**
     * 添加Activity到堆栈
     * @param Activity 需要加入的Activity
     * @return 
     */
    public void addActivity(Activity activity)
    {
        if (m_activityStack == null)
        {
            m_activityStack = new Stack<Activity>();
        }
        m_activityStack.add(activity);
    }

    /**
     * 结束所有Activity
     * @param 
     * @return 
     */
    @SuppressLint("NewApi")
	public static void finishAllActivity()
    {
        for (int i = 0, size = m_activityStack.size(); i < size; i++)
        {
        	Activity acitivty = m_activityStack.get(i);
        	
            if (null != acitivty && !acitivty.isFinishing())
            {
                m_activityStack.get(i).finish();
            }
        }
        m_activityStack.clear();
    }

	/**设置圆角图片*/
    public  void setImage(final String imgUrl,final ImageView iv){
    	String strImgUrl = imgUrl;
    	
    	if (!TextUtils.isEmpty(strImgUrl) && !strImgUrl.startsWith("http")) {	//如果不是http开头的，则是本地的图片
   		 strImgUrl = Scheme.FILE.wrap(strImgUrl);  
		}
   	
    	
    	//ImageLoader.getInstance().displayImage(strImgUrl,iv, optionsRound,new MyImageLoadingListener(iv));
 	    ImageLoader.getInstance().displayImage(strImgUrl,iv, optionsRound);
 	    
    }
    
	/**设置直角图片*/
    public  void setImageSqure(final String imgUrl,final ImageView iv){
    	
    	String strImgUrl = imgUrl;
    	
    	if (!TextUtils.isEmpty(strImgUrl) && !strImgUrl.startsWith("http")) {	//如果不是http开头的，则是本地的图片
    		 strImgUrl = Scheme.FILE.wrap(strImgUrl);  
		}
    	
    	//ImageLoader.getInstance().displayImage(strImgUrl,iv, options,new MyImageLoadingListener(iv));
	    ImageLoader.getInstance().displayImage(strImgUrl,iv, options);
	    
    }
    
   public class  MyImageLoadingListener implements ImageLoadingListener{
		
	   private ImageView iv;
	   public MyImageLoadingListener(ImageView mIv){
		   this.iv = mIv;
	   }
	   
		@Override
		public void onLoadingStarted(String arg0, View arg1) {
			
		}
		
		@Override
		public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
			
		}
		
		@Override
		public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
			Animation anim = AnimationUtils.loadAnimation(CBApp.this, R.anim.pic_show_in);
			iv.setAnimation(anim);
			anim.start();

		}
		
		@Override
		public void onLoadingCancelled(String arg0, View arg1) {
			
		}
	};
}
