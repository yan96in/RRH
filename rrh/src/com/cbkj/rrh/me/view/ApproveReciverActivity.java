package com.cbkj.rrh.me.view;

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
import android.widget.EditText;
import android.widget.ImageView;

import com.cbkj.rrh.R;
import com.cbkj.rrh.bean.UserBean;
import com.cbkj.rrh.main.base.BaseActivity;
import com.cbkj.rrh.net.http.HttpRequest;
import com.cbkj.rrh.net.http.HttpRequestAsyncTask.TaskListenerWithState;
import com.cbkj.rrh.net.http.HttpResponse;
import com.cbkj.rrh.net.http.HttpResponse.HttpTaskState;
import com.cbkj.rrh.net.http.HttpURL;
import com.cbkj.rrh.net.request.UserRequest;
import com.cbkj.rrh.others.utils.ImgUtils;
import com.cbkj.rrh.others.utils.ToolUtils;
import com.cbkj.rrh.view.BToast;
import com.cbkj.rrh.view.dialog.BottomDialog;
import com.cbkj.rrh.view.dialog.DialogFactory;
import com.cbkj.rrh.view.widget.TitleBar;

/**
 * @todo:申请接单人认证
 * @date:2015-8-10 下午3:07:23
 * @author:hg_liuzl@163.com
 */
public class ApproveReciverActivity extends BaseActivity implements TaskListenerWithState,OnClickListener {

	private enum UploadImgType
	{
		CID_FONT_IMG,
		CID_BACK_IMG
	}
	
	/** 从相册选择照片 **/
	private static final int FLAG_CHOOSE_FROM_IMGS = 100;
	/** 从手机获取照片 **/
	private static final int FLAG_CHOOSE_FROM_CAMERA = FLAG_CHOOSE_FROM_IMGS + 1;
	
	private File tempFile = null; // 文件
	
	private EditText etRealName,etCID;
	private ImageView ivCIDFont,ivCIDBack;
	private String fontCID,backCID;
	private UserBean mUserBean;
	private BottomDialog dialog = null;
	private UploadImgType mUploadType = UploadImgType.CID_FONT_IMG;
	/**提交认证状态    :（申请接单人验证状态） 1：没有申请过；2：申请过正在审核中；3：申请过并通过审核了 **/
	private int isEmployee = -1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		isEmployee = getIntent().getIntExtra(UserBean.USER_IS_EMPLOYEE, -1);
		setContentView(R.layout.a_approve_reciver_layout);
		mUserBean = pUitl.getUserBean();
		initView();
	}

	private void initView() {
		TitleBar mTitleBar = new TitleBar(mActivity);
		mTitleBar.initAllBar("申请接单人认证","提交");
		mTitleBar.rightBtn.setOnClickListener(this);
		
		ImageView ivVerifyEmployee = (ImageView) findViewById(R.id.iv_verify_employee);
		View vInput = findViewById(R.id.sv_input_verify);
		
		if (isEmployee == 2) {
			ivVerifyEmployee.setVisibility(View.VISIBLE);
			vInput.setVisibility(View.GONE);
		} else {
			ivVerifyEmployee.setVisibility(View.GONE);
			vInput.setVisibility(View.VISIBLE);

		}
		
		
		etRealName = (EditText) findViewById(R.id.et_real_name);
		etCID = (EditText) findViewById(R.id.et_cid);
		
		ivCIDFont = (ImageView) findViewById(R.id.iv_cid_font);
		ivCIDFont.setOnClickListener(this);
		
		ivCIDBack = (ImageView) findViewById(R.id.iv_cid_back);
		ivCIDBack.setOnClickListener(this);
		
		findViewById(R.id.btn_submit).setOnClickListener(this);
	}
	
	
	@Override
	public void onTaskOver(HttpRequest request, HttpResponse response) {
		if (response.getState() == HttpTaskState.STATE_OK&& null != response.result) {
			if (request.getRequestUrl() == HttpURL.URL_APPLY_IDENTIFICATION) {

				if (response.result.getSuccess()) {
					pUitl.setUserBean(mUserBean);
					BToast.show(mActivity, "认证材料提交成功");
					finish();
				} else {
					BToast.show(mActivity, response.result.getErrorMsg());
				}
			} else if (request.getRequestUrl() == HttpURL.URL_UPLOAD_CERTIFICATE_IMG) {
				if (response.result.getSuccess()) {
					JSONObject json = response.result.getJsonBody();
					String bigImg = json.optString("bigImg");
					if (mUploadType == UploadImgType.CID_FONT_IMG) {
						fontCID = bigImg;
					} else if(mUploadType == UploadImgType.CID_BACK_IMG) {
						backCID = bigImg;
					}
				} else {
					BToast.show(mActivity, response.result.getErrorMsg());
				}
			}
		} else { // 系统出错
			BToast.show(mActivity, R.string.system_error);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_right:
		case R.id.btn_submit:
			submitInfo();
			break;
		case R.id.iv_cid_font:
			mUploadType = UploadImgType.CID_FONT_IMG;
			dialog = DialogFactory.uploadFileDialog(mActivity,R.drawable.btn_camera_selector,R.string.publish_take_pic,R.drawable.btn_upload_file_selector,R.string.publish_local_upload, this, this, this);
			break;
		case R.id.iv_cid_back:
			mUploadType = UploadImgType.CID_BACK_IMG;
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
	
	
	private void afterGetPic(String path) {
		tempFile = new File(path);
		Bitmap mBitmapUploaded = ImgUtils.compressImageFromFile(path);
		if (mUploadType == UploadImgType.CID_FONT_IMG) {
			ivCIDFont.setImageBitmap(mBitmapUploaded);

		} else if(mUploadType == UploadImgType.CID_BACK_IMG) {
			ivCIDBack.setImageBitmap(mBitmapUploaded);
		}
		ImgUtils.compressBmpToFile(mBitmapUploaded, tempFile);
		UserRequest.getInstance().requestUploadCertificateImg(this, mActivity, pUitl.getUserID(), pUitl.getAccountPassword(), tempFile,true);
	}
	
	private void submitInfo() {
		String realName = etRealName.getText().toString().trim();
		String IDCard = etCID.getText().toString().trim();
		
		if (TextUtils.isEmpty(realName)) {
			BToast.show(mActivity, "请填写你的真实姓名");
			return;
			
		} else if(!ToolUtils.checkCID(IDCard)){
			BToast.show(mActivity, "请填写正确的身份证号");
			return;
		} else if(TextUtils.isEmpty(fontCID)){
			BToast.show(mActivity, "请上传手持身份证照片");
			return;
		}else if(TextUtils.isEmpty(backCID)){
			BToast.show(mActivity, "请上传身份证正面照片");
			return;
		}else {
			UserRequest.getInstance().requestApplay2Employee(this, mActivity, pUitl.getUserID(), realName, IDCard, fontCID, backCID);
		}
	}
}
