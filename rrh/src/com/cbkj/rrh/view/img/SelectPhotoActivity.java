package com.cbkj.rrh.view.img;


import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.Toast;

import com.cbkj.rrh.R;
import com.cbkj.rrh.bean.FileBean;
import com.cbkj.rrh.main.base.BaseActivity;
import com.cbkj.rrh.others.utils.ImgUtils;
import com.cbkj.rrh.view.img.GridItemAdapter.OnPhotoSelectedListener;
import com.cbkj.rrh.view.widget.TitleBar;
/**
 * 
 * @todo:图片选择
 * @date:2015-7-9 下午5:45:14
 * @author:hg_liuzl@163.com
 */
public class SelectPhotoActivity extends BaseActivity implements OnClickListener  {


	public static final String SELECT_PHOTO_SIZE = "select_photo_size";
	public static final String SELECT_PHOTO_LIST = "select_photo_list";
	
	/**最多可选多少张图片 **/
	public static final int SELECT_MAX_SIZE = 9;
	
	private GridView photoGrid;//图片列表

	private Button btnPhoto,btnFinish;//底部下一步按钮

	private ProgressDialog mProgressDialog;

	private HashSet<String> mDirPaths = new HashSet<String>();// 临时的辅助类，用于防止同一个文件夹的多次扫描

	private List<ImageFloder> mImageFloders = new ArrayList<ImageFloder>();// 扫描拿到所有的图片文件夹

	int totalCount = 0;

	private File mImgDir=new File("");// 图片数量最多的文件夹

	private int mPicsSize;//存储文件夹中的图片数量

	private List<String> mImgs=new ArrayList<String>();//所有的图片

	private int mScreenHeight;

	private ListView dirListView;


	private ImageFloaderAdapter floderAdapter;

	private GridItemAdapter girdItemAdapter;

	private PopupWindow popupWindow;

	private View dirview;


	private String path;

	private File dir;
	
	private TitleBar titleBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.photo_select_view);
		GridItemAdapter.mSelectedImage.clear();
		DisplayMetrics outMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		mScreenHeight = outMetrics.heightPixels;
		setView();
		getImages();
		changeBtnStatus();
	}
	/**
	 * 获取控件
	 */
	private void setView() {
		titleBar = new TitleBar(mActivity);
		titleBar.initAllBar("选择照片");
		
		 Drawable drawable = getResources().getDrawable(R.drawable.icon_direction_selector);
		 drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());  
		 titleBar.titleTV.setCompoundDrawables(null, null, drawable, null);
	  	 titleBar.titleTV.setCompoundDrawablePadding(10);
	  	 titleBar.titleTV.setSelected(false);
		
		titleBar.backBtn.setOnClickListener(this);
		
		titleBar.titleTV.setOnClickListener(this);
		
		photoGrid=(GridView)findViewById(R.id.gird_photo_list);
		
		btnPhoto=(Button)findViewById(R.id.selected_photo_btn);
		btnPhoto.setOnClickListener(this);
		
		btnFinish = (Button) findViewById(R.id.btn_finish);
		btnFinish.setOnClickListener(this);
		
		
	//	titleBar.titleTV.setBackgroundResource(R.drawable.navigationbar_arrow_down);
	}
	/**
	 * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中 完成图片的扫描，最终获得jpg最多的那个文件夹
	 */
	private void getImages(){
		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
			return;
		}
		// 显示进度条
		mProgressDialog = ProgressDialog.show(this, null, "正在加载...");
		new Thread(new Runnable(){
			@Override
			public void run(){
				String firstImage = null;
				Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				ContentResolver mContentResolver = SelectPhotoActivity.this.getContentResolver();
				// 只查询jpeg和png的图片
				Cursor mCursor = mContentResolver.query(mImageUri, null,
						MediaStore.Images.Media.MIME_TYPE + "=? or "
								+ MediaStore.Images.Media.MIME_TYPE + "=?",
								new String[] { "image/jpeg", "image/png" },
								MediaStore.Images.Media.DATE_MODIFIED);
				Log.e("TAG", mCursor.getCount() + "");
				while (mCursor.moveToNext()){
					// 获取图片的路径
					String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
					Log.e("TAG", path);
					// 拿到第一张图片的路径
					if (firstImage == null)
						firstImage = path;
					// 获取该图片的父路径名
					File parentFile = new File(path).getParentFile();
					if (parentFile == null)
						continue;
					String dirPath = parentFile.getAbsolutePath();
					ImageFloder imageFloder = null;
					// 利用一个HashSet防止多次扫描同一个文件夹（不加这个判断，图片多起来还是相当恐怖的~~）
					if (mDirPaths.contains(dirPath)){
						continue;
					} else{
						mDirPaths.add(dirPath);
						// 初始化imageFloder
						imageFloder = new ImageFloder();
						imageFloder.setDir(dirPath);
						imageFloder.setFirstImagePath(path);
					}
					if(parentFile.list()==null)continue ;
					int picSize = parentFile.list(new FilenameFilter(){
						@Override
						public boolean accept(File dir, String filename){
							if (filename.endsWith(".jpg")
									|| filename.endsWith(".png")
									|| filename.endsWith(".jpeg"))
								return true;
							return false;
						}
					}).length;
					totalCount += picSize;
					imageFloder.setCount(picSize);
					mImageFloders.add(imageFloder);

					if (picSize > mPicsSize){
						mPicsSize = picSize;
						mImgDir = parentFile;
					}
				}
				mCursor.close();

				// 扫描完成，辅助的HashSet也就可以释放内存了
				mDirPaths = null;

				// 通知Handler扫描图片完成
				mHandler.sendEmptyMessage(0x110);

			}
		}).start();

	}
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg){
			mProgressDialog.dismiss();
			setDataView();// 为View绑定数据
		}
	};


	/**
	 * 为View绑定数据
	 */
	public void setDataView(){
		path=Environment.getExternalStorageDirectory() + "/"+"test/photo/";
		dir=new File(path); 
		if(!dir.exists())dir.mkdirs(); 
		if (mImgDir == null){
			Toast.makeText(getApplicationContext(), "一张图片没扫描到",Toast.LENGTH_SHORT).show();
			return;
		}

		if(mImgDir.exists()){
			mImgs = Arrays.asList(mImgDir.list());
		}
		
		int hasSelectCount = getIntent().getIntExtra(SELECT_PHOTO_SIZE, 0);		//已经选了的图片
		
		girdItemAdapter=new GridItemAdapter(this, mImgs, mImgDir.getAbsolutePath(),hasSelectCount);
		photoGrid.setAdapter(girdItemAdapter);
		girdItemAdapter.setOnPhotoSelectedListener(new OnPhotoSelectedListener() {
			@Override
			public void photoClick(List<String> number) {
				changeBtnStatus();
				
			}
		});
	}
	/**
	 * 初始化展示文件夹的popupWindw
	 */
	private void initListDirPopupWindw(){
		if(popupWindow==null){
			dirview=LayoutInflater.from(SelectPhotoActivity.this).inflate(R.layout.image_select_dirlist, null);
			dirListView=(ListView)dirview.findViewById(R.id.id_list_dirs);
			popupWindow =new PopupWindow(dirview, LayoutParams.MATCH_PARENT, mScreenHeight*7/10);
			floderAdapter=new ImageFloaderAdapter(SelectPhotoActivity.this, mImageFloders);
			dirListView.setAdapter(floderAdapter);
			popupWindow.setFocusable(true);
			popupWindow.setOutsideTouchable(true);
			popupWindow.setBackgroundDrawable(new BitmapDrawable());
		}
		
		popupWindow.showAsDropDown(titleBar.titleTV,0,20);
		popupWindow.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				titleBar.titleTV.setSelected(false);
			}
		});
		
		titleBar.titleTV.setSelected(true);
		
		dirListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				titleBar.titleTV.setText(mImageFloders.get(position).getName());
				mImgDir = new File(mImageFloders.get(position).getDir());
				mImgs = Arrays.asList(mImgDir.list(new FilenameFilter(){
					@Override
					public boolean accept(File dir, String filename)
					{
						if (filename.endsWith(".jpg") || filename.endsWith(".png")
								|| filename.endsWith(".jpeg"))
							return true;
						return false;
					}
				}));
				for (int i = 0; i < mImageFloders.size(); i++) {
					mImageFloders.get(i).setSelected(false);
				}
				mImageFloders.get(position).setSelected(true);
				floderAdapter.changeData(mImageFloders);
				girdItemAdapter.changeData(mImgs, mImageFloders.get(position).getDir());
				dissmissPopupMore();
			}
		});
	}

	public void dissmissPopupMore()
	{
		if (popupWindow != null && popupWindow.isShowing()){
			popupWindow.dismiss();
			titleBar.titleTV.setSelected(false);
		}
	}
	
	/**
	 * 
	 * @todo:切换标题选项
	 * @date:2015-4-13 下午8:54:50
	 * @author:hg_liuzl@163.com
	 * @params:
	 */
	private void doTitle() {
		if (popupWindow != null && popupWindow.isShowing())
		{
			dissmissPopupMore();
		} 
		else
		{
			initListDirPopupWindw();
		}
	}
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			dealBack();
			break;
		case R.id.tv_title:
			doTitle();
			break;
		case R.id.selected_photo_btn:
			List<FileBean> fileList = new ArrayList<FileBean>();
			for (String path:GridItemAdapter.mSelectedImage) {
				fileList.add(new FileBean(path, path));
			}
			ImgUtils.fileBrower(0, fileList, mActivity);
			break;
		case R.id.btn_finish:
			Intent intent = new Intent();
			intent.putStringArrayListExtra(SELECT_PHOTO_LIST, GridItemAdapter.mSelectedImage);
			setResult(RESULT_OK, intent);
			finish();
			break;
		default:
			break;
		}
	}
	
	private void dealBack() {
		finish();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (KeyEvent.KEYCODE_BACK == keyCode) {
			dealBack();
		}
		return super.onKeyDown(keyCode, event);
	}
	
	/**
	 * 
	 * @todo:修改按钮状态 
	 * @date:2015-7-10 下午2:42:40
	 * @author:hg_liuzl@163.com
	 * @params:
	 */
	private void changeBtnStatus() {
		if (GridItemAdapter.mSelectedImage.size()==0) {
			btnFinish.setEnabled(false);
			btnPhoto.setEnabled(false);
			btnPhoto.setText("预览");
		}else{
			btnFinish.setEnabled(true);
			btnPhoto.setEnabled(true);
			btnPhoto.setText("预览("+GridItemAdapter.mSelectedImage.size()+")");
		}
	}
}
