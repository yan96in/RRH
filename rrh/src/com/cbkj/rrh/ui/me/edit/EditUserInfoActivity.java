package com.cbkj.rrh.ui.me.edit;

import java.io.File;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.cbkj.rrh.R;
import com.cbkj.rrh.bean.UserBean;
import com.cbkj.rrh.net.http.HttpRequest;
import com.cbkj.rrh.net.http.HttpRequestAsyncTask.TaskListenerWithState;
import com.cbkj.rrh.net.http.HttpResponse;
import com.cbkj.rrh.net.http.HttpResponse.HttpTaskState;
import com.cbkj.rrh.net.http.HttpURL;
import com.cbkj.rrh.net.request.UserRequest;
import com.cbkj.rrh.system.BGApp;
import com.cbkj.rrh.ui.base.BaseBackActivity;
import com.cbkj.rrh.utils.DeviceUtils;
import com.cbkj.rrh.utils.ImgUtils;
import com.cbkj.rrh.utils.pic.CropImageActivity;
import com.cbkj.rrh.view.BToast;
import com.cbkj.rrh.view.MyEditTextView;
import com.cbkj.rrh.view.RoundImageView;
import com.cbkj.rrh.view.dialog.BottomDialog;
import com.cbkj.rrh.view.dialog.DialogFactory;
import com.cbkj.rrh.widget.TitleBar;

/**
 * @todo:编辑个人资料
 * @date:2015-4-23 下午3:03:08
 * @author:hg_liuzl@163.com
 */
public class EditUserInfoActivity extends BaseBackActivity implements TaskListenerWithState,OnClickListener {
	
	/**如果是注册**/
	public static final String IS_REGISTER = "is_register";
	/** 从相册选择照片 **/
	private static final int FLAG_CHOOSE_FROM_IMGS = 100;
	/** 从手机获取照片 **/
	private static final int FLAG_CHOOSE_FROM_CAMERA = FLAG_CHOOSE_FROM_IMGS + 1;
	/** 选择完过后 **/
	private static final int FLAG_MODIFY_FINISH = FLAG_CHOOSE_FROM_CAMERA + 1;

	private File tempFile = null; // 文件
	
	private RoundImageView riv;
	private MyEditTextView metNick;
	private RadioGroup rgSex;
	private RadioButton rbMan,rbWoman;
	private UserBean userBean;
	private BottomDialog dialog = null;
	private String imageUrl = "";
	private String bigImageUrl = "";
	private String nick = "";
	private TitleBar mTitleBar = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		userBean = pUitl.getUserBean();
		setContentView(R.layout.a_editinfo_layout);
		initView();
		initData();
	}
	
	
	private void initView() {
		mTitleBar = new TitleBar(mActivity);
		mTitleBar.initAllBar("账号管理");
		riv = (RoundImageView) findViewById(R.id.riv_user);
		metNick = (MyEditTextView) findViewById(R.id.etv_nick);
		metNick.setSingLine();
		rbMan = (RadioButton) findViewById(R.id.rb_male);
		rbWoman = (RadioButton) findViewById(R.id.rb_female);
		
		rgSex = (RadioGroup) findViewById(R.id.rg_sex);
		rgSex.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup rg, int checkedId) {
				if(checkedId == rbMan.getId()){
					userBean.gender = 1;
				}else if(checkedId == rbWoman.getId()){
					userBean.gender = 0;
				}else{
					userBean.gender = 0;
				}

			}
		});
		
		
		findViewById(R.id.iv_photo).setOnClickListener(this);
		findViewById(R.id.btn_save).setOnClickListener(this);
		
	}
	
	private void initData() {
		BGApp.getInstance().setImageSqure(userBean.smallImg, riv);
		metNick.setContent(userBean.nickName);
		RadioButton rg = (RadioButton) rgSex.getChildAt(userBean.gender == 0 ?1 : 0);
		rg.setChecked(true);
		
		if (userBean.gender == 0) {
			rbWoman.setChecked(true);
		} else {
			rbMan.setChecked(true);

		}
	}


	@Override
	public void onClick(View v) {
		DeviceUtils.hiddenKeybroad(v, mActivity);
		switch (v.getId()) {
		case R.id.iv_photo:
			dialog = DialogFactory.uploadFileDialog(mActivity,R.drawable.btn_camera_selector,R.string.publish_take_pic,R.drawable.btn_upload_file_selector,R.string.publish_local_upload, this, this, this);
			break;
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
		case R.id.btn_save:
			save();
			break;
		default:
			break;
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
					Intent intent = new Intent(this, CropImageActivity.class);
					intent.putExtra("path", path);
					startActivityForResult(intent, FLAG_MODIFY_FINISH);
				} else {
					Intent intent = new Intent(this, CropImageActivity.class);
					intent.putExtra("path", uri.getPath());
					startActivityForResult(intent, FLAG_MODIFY_FINISH);
				}
			}
		} else if (requestCode == FLAG_CHOOSE_FROM_CAMERA) {
			if (tempFile == null) {
				BToast.show(mActivity, "拍照出错，请重拍");
				return;
			}
			Intent intent = new Intent(this, CropImageActivity.class);
			intent.putExtra("path", tempFile.getAbsolutePath());
			startActivityForResult(intent, FLAG_MODIFY_FINISH);
		} else if (requestCode == FLAG_MODIFY_FINISH) {
			if (data != null) {
				final String path = data.getStringExtra("path");
				tempFile = new File(path);
				Bitmap mBitmapUploaded = ImgUtils.compressImageFromFile(path);
				riv.setImageBitmap(mBitmapUploaded);
				ImgUtils.compressBmpToFile(mBitmapUploaded, tempFile);
				UserRequest.getInstance().requestUploadHeadImg(EditUserInfoActivity.this, mActivity, userBean.userId, pUitl.getAccountPassword(), tempFile);
			}
		}
	}
	
	
	/**用户资料保存**/
	private void save() {
		nick = metNick.getContent();
		if (TextUtils.isEmpty(nick)) {
			BToast.show(mActivity, "请设置您的昵称");
			return;
		}
		userBean.nickName = nick ;
		//如果没有修改图片，就不改了
		userBean.smallImg = TextUtils.isEmpty(imageUrl)?userBean.smallImg:imageUrl;
		userBean.bigImg = TextUtils.isEmpty(bigImageUrl)?userBean.bigImg:bigImageUrl;
		UserRequest.getInstance().requestModifyUserInfo(this, this,userBean);
	}

	@Override
	public void onTaskOver(HttpRequest request, HttpResponse response) {
		
		if (response.getState() == HttpTaskState.STATE_OK) {
			
			if (HttpURL.URL_UPLOAD_HEAD_IMG == request.getRequestUrl() && null != response.result) { //文件上传
				if (response.result.getSuccess()) {
					JSONObject object = response.result.getJsonBody();
					imageUrl = object.optString("smallImg");
					bigImageUrl = object.optString("bigImg");
				}else{
					BToast.show(mActivity, response.result.getErrorMsg());
				}
			}else if(HttpURL.URL_MODIFY_USER == request.getRequestUrl() && null != response.result ){	//保存资料
				if (response.result.getSuccess()) {
					JSONObject object = response.result.getJsonBody();
					imageUrl = object.optString("smallImg");
					bigImageUrl = object.optString("bigImg");
					nick = object.optString("nickName");
					
					userBean.nickName = TextUtils.isEmpty(nick)?userBean.nickName:nick ;
					//如果没有修改图片，就不改了
					userBean.smallImg = TextUtils.isEmpty(imageUrl)?userBean.smallImg:imageUrl;
					userBean.bigImg = TextUtils.isEmpty(bigImageUrl)?userBean.bigImg:bigImageUrl;;
					pUitl.setUserBean(userBean);
					BToast.show(mActivity, "修改成功");
					finish();
				}else{
					BToast.show(mActivity,response.result.getErrorMsg());
				}
			}else{
				BToast.show(mActivity, R.string.system_error);
			}
		}
	}
}
