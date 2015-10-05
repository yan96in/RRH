package com.cbkj.rrh.me.view;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.cbkj.rrh.R;
import com.cbkj.rrh.adapter.PicImageAdapter;
import com.cbkj.rrh.bean.CertificationBean;
import com.cbkj.rrh.bean.FileBean;
import com.cbkj.rrh.bean.UserBean;
import com.cbkj.rrh.main.base.BaseBackActivity;
import com.cbkj.rrh.net.http.HttpRequest;
import com.cbkj.rrh.net.http.HttpRequestAsyncTask.TaskListenerWithState;
import com.cbkj.rrh.net.http.HttpResponse;
import com.cbkj.rrh.net.http.HttpResponse.HttpTaskState;
import com.cbkj.rrh.net.http.HttpURL;
import com.cbkj.rrh.net.request.UserRequest;
import com.cbkj.rrh.others.utils.DeviceUtils;
import com.cbkj.rrh.others.utils.ImgUtils;
import com.cbkj.rrh.view.BToast;
import com.cbkj.rrh.view.dialog.BottomDialog;
import com.cbkj.rrh.view.dialog.DialogFactory;
import com.cbkj.rrh.view.img.GalleryPhotoActivity;
import com.cbkj.rrh.view.widget.TitleBar;
import com.umeng.analytics.MobclickAgent;

/**
 * 
 * @todo:证书界面
 * @date:2015-4-30 下午3:19:07
 * @author:hg_liuzl@163.com
 */
public class MyCerfiticateActivity extends BaseBackActivity implements OnItemClickListener,OnClickListener,TaskListenerWithState
{
	
	/**选择照片集合**/
	public static final String SELECT_PHOTO_LIST = "select_photo_list";
	
	/**选择照片张数**/
	public static final String SELECT_PHOTO_SIZE = "select_photo_size";
	
	/** 从相册选择照片 **/
	private static final int FLAG_CHOOSE_FROM_IMGS = 100;
	
	/** 从手机获取照片 **/
	private static final int FLAG_CHOOSE_FROM_CAMERA = FLAG_CHOOSE_FROM_IMGS + 1;
	
	/**查看照片**/
	private static final int FLAG_LOOK_PHOTO = FLAG_CHOOSE_FROM_CAMERA + 1;
	
	private BottomDialog dialog = null;
    
	private File tempFile = null; // 临时文件
	
	private GridView gridview_images;
	private PicImageAdapter adapter;
	
    private List<FileBean> fileBeans = new ArrayList<FileBean>();	//图片文件集合
    
    
    private TitleBar mTitleBar;

    private ImageView ivNoData;
    
    private String mUserId;
    
    /**是否是当前用户**/
    private boolean isCurrentUser = false;
    
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
	
    
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		mUserId = getIntent().getStringExtra(UserBean.USER_ID);
		
		isCurrentUser = mUserId.equals(pUitl.getUserID());
		
		setContentView(R.layout.a_my_cenfiticate_layout);
		mTitleBar = new TitleBar(mActivity);
		mTitleBar.initAllBar(isCurrentUser?"我的证书":"TA的证书","编辑");
		mTitleBar.rightBtn.setOnClickListener(this);
		mTitleBar.rightBtn.setVisibility(isCurrentUser?View.VISIBLE:View.GONE);
		requestData();
	}

	private void initViews()
	{
		gridview_images = (GridView) findViewById(R.id.gridview_images);
		adapter = new PicImageAdapter(fileBeans,this,this,isCurrentUser);
		gridview_images.setAdapter(adapter);
		gridview_images.setOnItemClickListener(this);
		ivNoData = (ImageView) findViewById(R.id.iv_no_data);
	}
	

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		if(position == fileBeans.size() && isCurrentUser){
			dialog = DialogFactory.uploadFileDialog(mActivity,R.drawable.btn_camera_selector,R.string.publish_take_pic,R.drawable.btn_upload_file_selector,R.string.publish_local_upload, MyCerfiticateActivity.this, MyCerfiticateActivity.this, MyCerfiticateActivity.this);
		}else{
			Intent intent = new Intent(mActivity,GalleryPhotoActivity.class);
			intent.putExtra(GalleryPhotoActivity.SELECT_CURRENT_PHOTO, position);
			intent.putExtra(GalleryPhotoActivity.SELECT_HIDDEN_DELECT, false);
			
			ArrayList<String> paths = new ArrayList<String>();
			for (FileBean bean : fileBeans) {
				paths.add(bean.small);
			}
			intent.putStringArrayListExtra(SELECT_PHOTO_LIST, paths);
			startActivityForResult(intent, FLAG_LOOK_PHOTO);
		}
	}
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK)
			return;
		if (requestCode == FLAG_CHOOSE_FROM_IMGS) {
			if (data != null) {
				Uri uri = data.getData();
				if (!TextUtils.isEmpty(uri.getAuthority())) {
					Cursor cursor = getContentResolver().query(uri,new String[] { MediaStore.Images.Media.DATA },null, null, null);
					if (null == cursor) {
						BToast.show(mActivity, "图片没有找到");
						return;
					}
					cursor.moveToFirst();
					String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
					cursor.close();
					afterGetPic(path);
				} else {
					afterGetPic(uri.getPath());
				}
			}
		} else if (requestCode == FLAG_CHOOSE_FROM_CAMERA) {
			if (tempFile == null) {
				BToast.show(mActivity, "拍照出错，请重拍");
				return;
			}
			afterGetPic(tempFile.getAbsolutePath());
		} 
	}
	
	/**
	 * 
	 * @todo:得到图片后
	 * @date:2015-8-12 上午10:23:36
	 * @author:hg_liuzl@163.com
	 * @params:@param path
	 */
	private void afterGetPic(String path) {
		tempFile = new File(path);
		Bitmap mBitmapUploaded = ImgUtils.compressImageFromFile(path);
		ImgUtils.compressBmpToFile(mBitmapUploaded, tempFile);
		
//		FileBean fb = new FileBean(1, path, path);
//		fileBeans.add(fb);
//		adapter.notifyDataSetChanged();	
		
		UserRequest.getInstance().requestUploadCertificateImg(this, mActivity, pUitl.getUserID(), pUitl.getAccountPassword(), tempFile,true);
	}

	@Override
	public void onClick(View v) {
		DeviceUtils.hiddenKeybroad(v, this);
		switch (v.getId()) {
		case R.id.iw_first:
			dialog.dismiss();
        	tempFile = ImgUtils.takePicture(mActivity, tempFile, FLAG_CHOOSE_FROM_CAMERA);
			break;
		case R.id.iw_second:
			dialog.dismiss();
			ImgUtils.selectPicture(mActivity, FLAG_CHOOSE_FROM_IMGS);
			break;
		case R.id.tv_cancel:
			dialog.dismiss();
			break;
		case R.id.iv_delete:
			FileBean bean = (FileBean) v.getTag();
			fileBeans.remove(bean);
			adapter.notifyDataSetChanged();
			UserRequest.getInstance().requestDelCertificateImg(MyCerfiticateActivity.this, mActivity, pUitl.getUserID(), bean.id);
			break;
		case R.id.btn_right:
			adapter.setDeleVisible(mTitleBar.rightBtn);
			break;
		default:
			break;
		}
	}
	
	private void requestData() {
		UserRequest.getInstance().requestMyCertificates(this, this,mUserId);
	}
	
	/**
	 * 
	 * @todo:设置空图
	 * @date:2015-9-4 下午6:03:51
	 * @author:hg_liuzl@163.com
	 * @params:
	 */
	private void setContentImg(){
//		adapter.notifyDataSetChanged();
		initViews();
		if (!isCurrentUser) {	//非当前用户
			if (fileBeans.size()==0) {
				ivNoData.setVisibility(View.VISIBLE);
				gridview_images.setVisibility(View.GONE);
			} else {
				ivNoData.setVisibility(View.GONE);
				gridview_images.setVisibility(View.VISIBLE);
			}
		}
		
	}
	

	@Override
	public void onTaskOver(HttpRequest request, HttpResponse response) {
		if (response.getState() == HttpTaskState.STATE_OK && null != response.result) {
			if (request.getRequestUrl() == HttpURL.URL_CERTIFICATE_LIST) {	//获取证书列表
				if (response.result.getSuccess()) {
					String strJson = response.result.getStrBody();
					if (TextUtils.isEmpty(strJson)) {
						setContentImg();
						return;
					}
					List<CertificationBean> beans = JSON.parseArray(strJson, CertificationBean.class);
					for (CertificationBean certificationBean : beans) {
						FileBean fb = new FileBean(certificationBean.certificateId, certificationBean.smallImg, certificationBean.bigImg);
						fileBeans.add(fb);
					}
					setContentImg();
				} else {
					BToast.show(mActivity, response.result.getErrorMsg());
				}
			} else if (request.getRequestUrl() == HttpURL.URL_UPLOAD_CERTIFICATE_IMG) {	//证书图片上传
			if (response.result.getSuccess()) {
				JSONObject o = response.result.getJsonBody();
				String smallImg = o.optString("smallImg");
				String bigImg = o.optString("bigImg");
				UserRequest.getInstance().requestAddCertificateImg(MyCerfiticateActivity.this, mActivity, pUitl.getUserID(), smallImg, bigImg);
			} else {
				BToast.show(mActivity, response.result.getErrorMsg());
			}
		}else if (request.getRequestUrl() == HttpURL.URL_ADD_CERTIFICATE_IMG) {	//上传图片操作
			if (response.result.getSuccess()) {
				JSONObject o = response.result.getJsonBody();
				int id = o.optInt("certificateId");
				String smallImg = o.optString("smallImg");
				String bigImg = o.optString("bigImg");
				FileBean fb = new FileBean(id, smallImg, bigImg);
				fileBeans.add(fb);
				adapter.notifyDataSetChanged();			
			} else {
				BToast.show(mActivity, response.result.getErrorMsg());
			}
		}
		else if (request.getRequestUrl() == HttpURL.URL_DEL_CERTIFICATE) {	//删除证书
			if (response.result.getSuccess()) {
			} else {
				BToast.show(mActivity, response.result.getErrorMsg());
			}
		}else { // 系统出错
			BToast.show(mActivity, R.string.system_error);
		}}
		
	}
}
