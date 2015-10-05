package com.cbkj.rrh.view.img;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

import com.cbkj.rrh.R;
import com.cbkj.rrh.main.BGApp;
import com.cbkj.rrh.main.base.BaseActivity;
import com.cbkj.rrh.view.photoview.PhotoView;
import com.cbkj.rrh.view.widget.TitleBar;

/**
 * @todo:用于展示图片
 * @date:2015-7-10 下午3:02:09
 * @author:hg_liuzl@163.com
 */
public class GalleryPhotoActivity extends BaseActivity implements OnClickListener {
	
	public static final String  SELECT_HIDDEN_DELECT ="select_hidden_delect";
	
	public static final String  SELECT_CURRENT_PHOTO ="select_current_photo";
	//当前的位置
	private int location = 0;
	private ArrayList<View> listViews = null;
	private ViewPagerFixed pager;
	private MyPageAdapter adapter;
	private TitleBar mTitleBar;
	private ArrayList<String> photos = new ArrayList<String>();
	private Button btnFinish;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.plugin_camera_gallery);// 切屏到主界面
		
		boolean hidden  = getIntent().getBooleanExtra(SELECT_HIDDEN_DELECT, false);
		location = getIntent().getIntExtra(SELECT_CURRENT_PHOTO, 0);
		photos = getIntent().getStringArrayListExtra(SelectPhotoActivity.SELECT_PHOTO_LIST);
		
		mTitleBar = new TitleBar(mActivity);
		mTitleBar.initAllBar("", "");
		mTitleBar.backBtn.setOnClickListener(this);
		mTitleBar.rightBtn.setBackgroundResource(R.drawable.plugin_camera_del_state);
		mTitleBar.rightBtn.setOnClickListener(this);
		mTitleBar.rightBtn.setVisibility(hidden?View.VISIBLE:View.GONE);
		
		
		// 为发送按钮设置文字
		pager = (ViewPagerFixed) findViewById(R.id.gallery01);
		pager.setOnPageChangeListener(pageChangeListener);
		for (int i = 0; i < photos.size(); i++) {
			initListViews(photos.get(i));
		}
		
		adapter = new MyPageAdapter(listViews);
		pager.setAdapter(adapter);
		pager.setCurrentItem(location);
		
		btnFinish = (Button) findViewById(R.id.btn_finish);
		btnFinish.setOnClickListener(this);
		
		showTitle();
	}
	
	/**
	 * 
	 * @todo:展示浏览到的图片
	 * @date:2015-7-13 上午10:59:08
	 * @author:hg_liuzl@163.com
	 * @params:
	 */
	private void showTitle(){
		mTitleBar.titleTV.setText(getResources().getString(R.string.show_pic, location+1,photos.size()));
	}
	
	private void initListViews(String path) {
		if (listViews == null)
			listViews = new ArrayList<View>();
		PhotoView img = new PhotoView(this);
		img.setBackgroundColor(0xff000000);
		BGApp.getInstance().setImageSqure(path, img);
		img.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		listViews.add(img);
	}
	
	private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

		public void onPageSelected(int arg0) {
			location = arg0;
			showTitle();
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		public void onPageScrollStateChanged(int arg0) {

		}
	};


	/**
	 * 监听返回按钮
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		finish();
		return true;
	}
	
	
	class MyPageAdapter extends PagerAdapter {

		private ArrayList<View> listViews;

		private int size;
		public MyPageAdapter(ArrayList<View> listViews) {
			this.listViews = listViews;
			size = listViews == null ? 0 : listViews.size();
		}

		public void setListViews(ArrayList<View> listViews) {
			this.listViews = listViews;
			size = listViews == null ? 0 : listViews.size();
		}

		public int getCount() {
			return size;
		}

		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPagerFixed) arg0).removeView(listViews.get(arg1 % size));
		}

		public void finishUpdate(View arg0) {
		}

		public Object instantiateItem(View arg0, int arg1) {
			try {
				((ViewPagerFixed) arg0).addView(listViews.get(arg1 % size), 0);

			} catch (Exception e) {
			}
			return listViews.get(arg1 % size);
		}

		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			dealBack();
			break;
		case R.id.btn_right:
			deletePhoto();
			break;
		case R.id.btn_finish:
			break;

		default:
			break;
		}
	}
	
	private void dealBack() {
		Intent intent = new Intent();
		intent.putStringArrayListExtra(SelectPhotoActivity.SELECT_PHOTO_LIST, photos);
		setResult(RESULT_OK,intent);
		finish();

	}
	
	private void deletePhoto() {
		if (listViews.size() == 0) {
			dealBack();
		} else {
			photos.remove(location);
			pager.removeAllViews();
			listViews.remove(location);
			adapter.setListViews(listViews);
			showTitle();
			adapter.notifyDataSetChanged();
		}

	}
}
