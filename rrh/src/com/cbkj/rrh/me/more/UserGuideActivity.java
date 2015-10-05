package com.cbkj.rrh.me.more;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.cbkj.rrh.R;
import com.cbkj.rrh.main.base.BaseBackActivity;
import com.cbkj.rrh.view.widget.TitleBar;


/**
 * 
 * @todo:我的消息
 * @date:2015-7-23 下午3:12:38
 * @author:hg_liuzl@163.com
 */
public class UserGuideActivity extends BaseBackActivity {

	
	private ViewPager mTabPager;//页卡内容
    private RadioGroup radio_group;
    private ImageView img0,img1;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_user_help_layout);
		initView();
	}
	
	
	
	private void initView() {
		
		TitleBar title = new TitleBar(mActivity);
		title.initAllBar("使用指南");
		
		radio_group = (RadioGroup) findViewById(R.id.radio_group);
		radio_group.setOnCheckedChangeListener(mOnCheckedChangeListener);
		
		mTabPager = (ViewPager) findViewById(R.id.vp_publish_type_select);
		//设置ViewPager的页面翻滚监听
		mTabPager.setOnPageChangeListener(new myOnPageChangeListener());
		
		img0 = (ImageView) findViewById(R.id.img_01);
		img1 = (ImageView) findViewById(R.id.img_02);
		
		View view1 = inflater.inflate(R.layout.show_userguide_model, null);

		ImageView ivEmployee = (ImageView) view1.findViewById(R.id.iv_img);
		ivEmployee.setImageResource(R.drawable.img_employee_guide);
		
		View view2 = inflater.inflate(R.layout.show_userguide_model, null);
		ImageView ivEmployer = (ImageView) view2.findViewById(R.id.iv_img);
		ivEmployer.setImageResource(R.drawable.img_employer_guide);
		
		
		//将布局放入集合
		final ArrayList<View> views = new ArrayList<View>();
		views.add(view1);
		views.add(view2);
		
		//放入adapter
		PagerAdapter adapter = new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return views.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager)container).removeView(views.get(position));
			}

			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager)container).addView(views.get(position));
				return views.get(position);
			}
		};
		mTabPager.setAdapter(adapter);
		mTabPager.setCurrentItem(0, true);

	}
	

	/**
	 * 
	 */
	private OnCheckedChangeListener mOnCheckedChangeListener = new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			int radioButtonId = group.getCheckedRadioButtonId();
			switch (radioButtonId) {
			case R.id.radio_01:
				mTabPager.setCurrentItem(0);
				showTabTag(0);
				break;
			case R.id.radio_02:
				mTabPager.setCurrentItem(1);
				showTabTag(1);
				break;
			default:
				break;
			}
		}
	};
	
	private void showTabTag(int index) {
		switch (index) {
		case 0:
			img0.setVisibility(View.VISIBLE);
			img1.setVisibility(View.INVISIBLE);
			break;
		case 1:
			img0.setVisibility(View.INVISIBLE);
			img1.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
	}
	
	public class myOnPageChangeListener implements OnPageChangeListener{

		@Override
		public void onPageScrollStateChanged(int arg0) {
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
		}

		@Override
		public void onPageSelected(int i) {
			RadioButton child = (RadioButton) radio_group.getChildAt(i);
			child.setChecked(true);
		}
	}
}
