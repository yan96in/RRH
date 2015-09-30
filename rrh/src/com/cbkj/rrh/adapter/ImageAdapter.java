package com.cbkj.rrh.adapter;

import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cbkj.rrh.R;
import com.cbkj.rrh.bean.FileBean;
import com.cbkj.rrh.bean.ImageBean;
import com.cbkj.rrh.system.BGApp;
import com.cbkj.rrh.system.Const;
/***
 * @todo:图片适配器
 * @date:2014-11-10 下午6:00:55
 * @author:hg_liuzl@163.com
 */
public class ImageAdapter extends KBaseAdapter {
	
	private int mMediaType = Const.TYPE_PIC;

	public ImageAdapter(List<?> mList, Activity mActivity,OnClickListener listener,int type) {
		super(mList, mActivity, listener);
		this.mMediaType = type;
	}
	
	public ImageAdapter(List<?> mList, Activity mActivity,OnClickListener listener) {
		super(mList, mActivity, listener);
	}
	
	public ImageAdapter(List<?> mList, Activity mActivity) {
		super(mList, mActivity);
	}
	
	@Override
	public int getCount() {
		return super.getCount();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if (convertView == null) {
			holder = new Holder();
			convertView = mInflater.inflate(R.layout.item_img,parent, false);
			holder.imageImgV = (ImageView) convertView.findViewById(R.id.iv_img);
			holder.deleteImgV = (ImageView) convertView.findViewById(R.id.iv_delete);
			convertView.setTag(holder);
		} 
		else 
		{
			holder = (Holder) convertView.getTag();
		}
		
		final Object object = mList.get(position);
		
		if(object instanceof ImageBean){
			ImageBean bean = (ImageBean) object;
			imgType(position,bean,holder.imageImgV);
		}if(object instanceof FileBean){
			FileBean bean = (FileBean) object;
			if (null != mListener) {	//没有删除事件的图片浏览
				fileType(position,bean, holder.deleteImgV, holder.imageImgV);
			}else{
				fileType(position,bean,holder.imageImgV);
			}
		}else{
			Bitmap bitmap = (Bitmap) object;
			bitmapType(position,bitmap, holder.deleteImgV, holder.imageImgV);
		}
		return convertView;
	}
	
	/**文件方式展示图片*/
	private void bitmapType(int position,Bitmap bitmap,ImageView ivDelete,ImageView ivImg){
		ivDelete.setOnClickListener(mListener);
		ivDelete.setTag(position);
		if (null != bitmap) {
			ivImg.setImageBitmap(bitmap);
			ivDelete.setVisibility(View.VISIBLE);
		} 
		else
		{
			ivImg.setImageResource(R.drawable.btn_addpic_selector);
			ivDelete.setVisibility(View.GONE);
		}
		
		if (mMediaType == Const.TYPE_PIC) {	//图片类型
			if(position >= 9){
				ivImg.setVisibility(View.GONE);
			}else{
				ivImg.setVisibility(View.VISIBLE);
			}
		}else{	//视频类型
			if(position >= 1){
				ivImg.setVisibility(View.GONE);
			}else{
				ivImg.setVisibility(View.VISIBLE);
			}
		}
	}
	
	
	/**文件方式展示图片*/
	private void fileType(int position,FileBean fileBean,ImageView ivDelete,ImageView ivImg){
		ivDelete.setOnClickListener(mListener);
		ivDelete.setTag(position);
		if (null != fileBean) {
			BGApp.getInstance().setImageSqure(fileBean.small, ivImg);
			ivDelete.setVisibility(View.VISIBLE);
		} 
		else
		{
			ivImg.setImageResource(R.drawable.btn_addpic_selector);
			ivDelete.setVisibility(View.GONE);
		}
		
		if (mMediaType == Const.TYPE_PIC) {	//图片类型
			if(position >= 9){
				ivImg.setVisibility(View.GONE);
			}else{
				ivImg.setVisibility(View.VISIBLE);
			}
		}else{	//视频类型
			if(position >= 1){
				ivImg.setVisibility(View.GONE);
			}else{
				ivImg.setVisibility(View.VISIBLE);
			}
		}
	}
	
	/**图片的url方式展示图片*/
	private void imgType(int position,ImageBean bean,final ImageView ivImg){
		BGApp.getInstance().setImageSqure(bean.img_thum, ivImg);
	}
	
	/**图片的url方式展示图片*/
	private void fileType(int position,FileBean bean,final ImageView ivImg){
		BGApp.getInstance().setImageSqure(bean.small, ivImg);
	}

	class Holder {
		ImageView imageImgV;
		ImageView deleteImgV;
	}
}
