package com.cbkj.rrh.widget.wheel;


import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.cbkj.rrh.R;
import com.cbkj.rrh.view.BToast;


/**
 * @auther:summer 时间： 2012-7-19 下午2:59:56
 */
public class MyDateTimePickerDialog extends AlertDialog implements
		OnClickListener{
	private static int START_YEAR = 1949,END_YEAR=2100;
	private final OnDateTimeSetListener mCallBack;
	private Calendar mCalendar;
	private int curr_year, curr_month, curr_day;//, curr_hour, curr_minute;
	// 添加大小月月份并将其转换为list,方便之后的判断
	String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
	String[] months_little = { "4", "6", "9", "11" };
	final WheelView wv_year, wv_month, wv_day;//, wv_hours, wv_mins;
	final List<String> list_big, list_little;

	public MyDateTimePickerDialog(Context context,OnDateTimeSetListener callBack) {
		
		this(context, START_YEAR,END_YEAR,callBack);
	}

	public MyDateTimePickerDialog(Context context, final int START_YEAR, final int END_YEAR,OnDateTimeSetListener callBack) {
		super(context);
		this.START_YEAR = START_YEAR;
		mCalendar = Calendar.getInstance();
		int year = mCalendar.get(Calendar.YEAR);
		int month = mCalendar.get(Calendar.MONTH);
		int day = mCalendar.get(Calendar.DATE);
		this.END_YEAR = year;
		mCallBack = callBack;

		list_big = Arrays.asList(months_big);
		list_little = Arrays.asList(months_little);
		setCancelable(false);
	     setButton("确定",this);
	     setButton2("取消", (OnClickListener) null);
	     
		setTitle("选择日期");
		// 找到dialog的布局文件
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.time_layout, null);
      
		int textSize = 0;
		textSize = adjustFontSize(getWindow().getWindowManager()); 
		// 年
		wv_year = (WheelView) view.findViewById(R.id.year);
		wv_year.setAdapter(new NumericWheelAdapter(START_YEAR, END_YEAR));// 设置"年"的显示数据
		wv_year.setCyclic(true);// 可循环滚动
		wv_year.setLabel("年");// 添加文字
		wv_year.setCurrentItem(year - START_YEAR);// 初始化时显示的数据

		// 月
		wv_month = (WheelView) view.findViewById(R.id.month);
		wv_month.setAdapter(new NumericWheelAdapter(1, 12));
		wv_month.setCyclic(true);
		wv_month.setLabel("月");
		wv_month.setCurrentItem(month);

		// 日
		wv_day = (WheelView) view.findViewById(R.id.day);
		wv_day.setCyclic(true);
		// 判断大小月及是否闰年,用来确定"日"的数据
		if (list_big.contains(String.valueOf(month + 1))) {
			wv_day.setAdapter(new NumericWheelAdapter(1, 31));
		} else if (list_little.contains(String.valueOf(month + 1))) {
			wv_day.setAdapter(new NumericWheelAdapter(1, 30));
		} else {
			// 闰年
			if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
				wv_day.setAdapter(new NumericWheelAdapter(1, 29));
			else
				wv_day.setAdapter(new NumericWheelAdapter(1, 28));
		}
		wv_day.setLabel("日");
		wv_day.setCurrentItem(day - 1);

		// 添加"年"监听
		OnWheelChangedListener wheelListener_year = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int year_num = newValue + START_YEAR;
				// 判断大小月及是否闰年,用来确定"日"的数据
				if (list_big
						.contains(String.valueOf(wv_month.getCurrentItem() + 1))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 31));
				} else if (list_little.contains(String.valueOf(wv_month
						.getCurrentItem() + 1))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 30));
				} else {
					if ((year_num % 4 == 0 && year_num % 100 != 0)
							|| year_num % 400 == 0)
						wv_day.setAdapter(new NumericWheelAdapter(1, 29));
					else
						wv_day.setAdapter(new NumericWheelAdapter(1, 28));
				}
			}
		};
		// 添加"月"监听
		OnWheelChangedListener wheelListener_month = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int month_num = newValue + 1;
				// 判断大小月及是否闰年,用来确定"日"的数据
				if (list_big.contains(String.valueOf(month_num))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 31));
				} else if (list_little.contains(String.valueOf(month_num))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 30));
				} else {
					if (((wv_year.getCurrentItem() + START_YEAR) % 4 == 0 && (wv_year
							.getCurrentItem() + START_YEAR) % 100 != 0)
							|| (wv_year.getCurrentItem() + START_YEAR) % 400 == 0)
						wv_day.setAdapter(new NumericWheelAdapter(1, 29));
					else
						wv_day.setAdapter(new NumericWheelAdapter(1, 28));
				}
			}
		};
		wv_year.addChangingListener(wheelListener_year);
		wv_month.addChangingListener(wheelListener_month);
		wv_day.TEXT_SIZE = textSize;
//		wv_hours.TEXT_SIZE = textSize;
//		wv_mins.TEXT_SIZE = textSize;
		wv_month.TEXT_SIZE = textSize;
		wv_year.TEXT_SIZE = textSize;
		setView(view);
	}
	
	
	
/**
 * 
 * @author lzlong@zwmob.com
 * @time 2014-3-24 上午11:10:53
 * @todo  TODO  endTime时间格式必须为yyyy-MM-dd
 */
	public MyDateTimePickerDialog(Context context, final String endTime,OnDateTimeSetListener callBack) {
		super(context);
		mCalendar = Calendar.getInstance();
		int currentYear = Integer.valueOf(endTime.substring(0, 4));
		int currentMonth = Integer.valueOf(endTime.substring(5, 7));
		int currentDay = Integer.valueOf(endTime.substring(8, 10));
		
		
		this.END_YEAR = mCalendar.get(Calendar.YEAR);
		int year = mCalendar.get(Calendar.YEAR);
		final int month = mCalendar.get(Calendar.MONTH);
		int day = mCalendar.get(Calendar.DATE);
		
		mCallBack = callBack;

		list_big = Arrays.asList(months_big);
		list_little = Arrays.asList(months_little);
		setCancelable(false);
	     setButton("确定",this);
	     setButton2("取消", (OnClickListener) null);
	     
		setTitle("选择出生日期");
		// 找到dialog的布局文件
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.time_layout, null);
      
		int textSize = 0;
		textSize = adjustFontSize(getWindow().getWindowManager()); 
		// 年
		wv_year = (WheelView) view.findViewById(R.id.year);
		wv_year.setAdapter(new NumericWheelAdapter(START_YEAR, END_YEAR));// 设置"年"的显示数据
		wv_year.setCyclic(false);// 可循环滚动
		wv_year.setLabel("年");// 添加文字
		wv_year.setCurrentItem(currentYear - START_YEAR);// 初始化时显示的数据

		// 月
		wv_month = (WheelView) view.findViewById(R.id.month);
		wv_month.setAdapter(new NumericWheelAdapter(1, wv_year.getCurrentItem()== END_YEAR-START_YEAR ? currentMonth:12)); //如果选中的是当年的年份，就只能到那个月。
		wv_month.setCyclic(false);
		wv_month.setLabel("月");
		wv_month.setCurrentItem(currentMonth-1);

		// 日
		wv_day = (WheelView) view.findViewById(R.id.day);
		wv_day.setCyclic(false);
		// 判断大小月及是否闰年,用来确定"日"的数据
		if (list_big.contains(String.valueOf(month))) {
			wv_day.setAdapter(new NumericWheelAdapter(1, wv_year.getCurrentItem()==END_YEAR-START_YEAR&&wv_month.getCurrentItem()==month?day:31));
		} else if (list_little.contains(String.valueOf(month))) {
			wv_day.setAdapter(new NumericWheelAdapter(1, wv_year.getCurrentItem()==END_YEAR-START_YEAR&&wv_month.getCurrentItem()==month?day:30));
		} else {
			// 闰年
			if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
				wv_day.setAdapter(new NumericWheelAdapter(1, wv_year.getCurrentItem()==END_YEAR-START_YEAR&&wv_month.getCurrentItem()==month?day:29));
			else
				wv_day.setAdapter(new NumericWheelAdapter(1, wv_year.getCurrentItem()==END_YEAR-START_YEAR&&wv_month.getCurrentItem()==month?day:28));
		}
		wv_day.setLabel("日");
		wv_day.setCurrentItem(currentDay-1);

		// 添加"年"监听
		OnWheelChangedListener wheelListener_year = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int year_num = newValue + START_YEAR;
				
				wv_month.setAdapter(new NumericWheelAdapter(1, year_num==END_YEAR?mCalendar.get(Calendar.MONTH)+1:12));
				
				// 判断大小月及是否闰年,用来确定"日"的数据
				if (list_big.contains(String.valueOf(wv_month.getCurrentItem() + 1))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 31));
				} else if (list_little.contains(String.valueOf(wv_month.getCurrentItem() + 1))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 30));
				} else {
					if ((year_num % 4 == 0 && year_num % 100 != 0)|| year_num % 400 == 0)
						wv_day.setAdapter(new NumericWheelAdapter(1, 29));
					else
						wv_day.setAdapter(new NumericWheelAdapter(1, 28));
				}
				
				if(wv_year.getCurrentItem() == END_YEAR-START_YEAR&& wv_month.getCurrentItem() == mCalendar.get(Calendar.MONTH)+1){
					wv_day.setAdapter(new NumericWheelAdapter(1, mCalendar.get(Calendar.DATE)));
				}
			}
		};
		// 添加"月"监听
		OnWheelChangedListener wheelListener_month = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int month_num = newValue + 1;
				// 判断大小月及是否闰年,用来确定"日"的数据
				if (list_big.contains(String.valueOf(month_num))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 31));
				} else if (list_little.contains(String.valueOf(month_num))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 30));
				} else {
					if (((wv_year.getCurrentItem() + START_YEAR) % 4 == 0 && (wv_year.getCurrentItem() + START_YEAR) % 100 != 0)|| (wv_year.getCurrentItem() + START_YEAR) % 400 == 0)
						wv_day.setAdapter(new NumericWheelAdapter(1, 29));
					else
						wv_day.setAdapter(new NumericWheelAdapter(1, 28));
				}
				
				if(wv_year.getCurrentItem() == END_YEAR-START_YEAR&&month_num == mCalendar.get(Calendar.MONTH)+1){
					wv_day.setAdapter(new NumericWheelAdapter(1, mCalendar.get(Calendar.DATE)));
				}
			}
		};
		wv_year.addChangingListener(wheelListener_year);
		wv_month.addChangingListener(wheelListener_month);
		wv_day.TEXT_SIZE = textSize;
		wv_month.TEXT_SIZE = textSize;
		wv_year.TEXT_SIZE = textSize;
		setView(view);
	}
	
	public void onClick(DialogInterface dialog, int which) {

		mCalendar = Calendar.getInstance();
		
		int year = mCalendar.get(Calendar.YEAR);
		int month = mCalendar.get(Calendar.MONTH)+1;
		int day = mCalendar.get(Calendar.DATE);
		
		curr_year = wv_year.getCurrentItem() + START_YEAR;
		curr_month = wv_month.getCurrentItem() + 1;
		curr_day = wv_day.getCurrentItem() + 1;
		
		
		if(curr_year ==year && (curr_month > month || (curr_month == month && curr_day > day))){
			BToast.show(getContext(), "出生不能大于当前日期");
			return;
		}
		
		
		if (mCallBack != null) {
			mCallBack.onDateTimeSet(curr_year, curr_month, curr_day/**curr_hour,	curr_minute*/);
		}
	}
	 public void show() {
	        super.show();
	 }
	public  interface OnDateTimeSetListener {
			void onDateTimeSet(int year, int monthOfYear, int dayOfMonth/**, int hour,int minute*/);
		}
	public static int adjustFontSize(WindowManager windowmanager) {

		 int screenWidth = windowmanager.getDefaultDisplay().getWidth();
	     int screenHeight = windowmanager.getDefaultDisplay().getHeight();
	     /*  DisplayMetrics dm = new DisplayMetrics();
	      dm = windowmanager.getApplicationContext().getResources().getDisplayMetrics();
	     int widthPixels = dm.widthPixels;
	     int heightPixels = dm.heightPixels;
	     float density = dm.density;
	     fullScreenWidth = (int)(widthPixels * density);
	     fullScreenHeight = (int)(heightPixels * density);*/
		if (screenWidth <= 240) { // 240X320 屏幕
			return (int) (1.5*10);
		} else if (screenWidth <= 320) { // 320X480 屏幕
			return (int) (1.5*14);
		} else if (screenWidth <= 480) { // 480X800 或 480X854 屏幕
			return (int) (1.5*24);
		} else if (screenWidth <= 540) { // 540X960 屏幕
			return (int) (1.5*26);
		} else if (screenWidth <= 800) { // 800X1280 屏幕
			return (int) (1.5*30);
		} else { // 大于 800X1280
			return (int) (1.5*30);
		}
	}
	
	/**
	 * 
	 * @author lzlong@zwmob.com
	 * @time 2014-4-10 上午11:41:12
	 * @todo  获取上次时间
	 *  @param year
	 *  @param monthOfYear
	 *  @param dayOfMonth
	 */
	public void setLastTime(int year, int monthOfYear, int dayOfMonth){
		wv_year.setCurrentItem(year - START_YEAR);// 初始化时显示的数据
		wv_month.setCurrentItem(monthOfYear - 1);// 初始化时显示的数据
		wv_day.setCurrentItem(dayOfMonth -1);// 初始化时显示的数据
	}
}

