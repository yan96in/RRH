package com.cbkj.rrh.view.img;


import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cbkj.rrh.R;
import com.cbkj.rrh.view.img.ImageLoader.Type;

public class GridItemAdapter extends BaseAdapter{
	/**
	 * 用户选择的图片，存储为图片的完整路径
	 */
	public static ArrayList<String> mSelectedImage = new ArrayList<String>();

	/**
	 * 文件夹路径
	 */
	private String mDirPath;

	private Context context;

	private int mHasSelect = 0;
	
	private List<String> mDatas=new ArrayList<String>();//所有的图片


	public GridItemAdapter(Context context, List<String> mDatas,String dirPath,int mSelectSize) {
		super();
		this.context = context;
		this.mDatas = mDatas;
		this.mDirPath=dirPath;
		this.mHasSelect = mSelectSize;
	}

	public void changeData(List<String> mDatas,String dirPath){
		this.mDatas = mDatas;
		this.mDirPath=dirPath;
		notifyDataSetChanged();

	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public String getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView==null){
				convertView=LayoutInflater.from(context).inflate(R.layout.grid_item, null);
				holder=new ViewHolder();
				holder.id_item_image=(ImageView)convertView.findViewById(R.id.id_item_image);
				holder.id_item_select=(ImageButton)convertView.findViewById(R.id.id_item_select);
				convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		
			holder.id_item_select.setBackgroundResource(R.drawable.icon_selected);
			holder.id_item_image.setBackgroundResource(R.drawable.pictures_no);
			ImageLoader.getInstance(3,Type.LIFO).loadImage(mDirPath + "/" + mDatas.get(position), holder.id_item_image);
			holder.id_item_image.setColorFilter(null);
			//设置ImageView的点击事件
			holder.id_item_image.setOnClickListener(new MyOnClickListener(holder, position));
			/**
			 * 已经选择过的图片，显示出选择过的效果
			 */
			if (mSelectedImage.contains(mDirPath + "/" + mDatas.get(position))){
				holder.id_item_select.setImageResource(R.drawable.icon_selected_press);
				holder.id_item_image.setColorFilter(Color.parseColor("#77000000"));
			}else{
				holder.id_item_select.setImageResource(R.drawable.icon_selected);
				holder.id_item_image.setColorFilter(Color.parseColor("#00000000"));
			}
		return convertView;
	}
	class MyOnClickListener implements OnClickListener{
		ViewHolder holder;
		int position;
		MyOnClickListener(ViewHolder holder,int  position){
			this.holder=holder;
			this.position=position;
		}
		@Override
		public void onClick(View v) {
			// 已经选择过该图片
			if (mSelectedImage.contains(mDirPath + "/" + mDatas.get(position))){
				mSelectedImage.remove(mDirPath + "/" + mDatas.get(position));
				holder.id_item_select.setImageResource(R.drawable.icon_selected);
				holder.id_item_image.setColorFilter(null);
			} else{// 未选择该图片
				int count = SelectPhotoActivity.SELECT_MAX_SIZE-mHasSelect;
				if(mSelectedImage.size() >= count){
					Toast.makeText(context, "您只能选"+count+"张", Toast.LENGTH_SHORT).show();
					return ;
				}
				mSelectedImage.add(mDirPath + "/" + mDatas.get(position));
				holder.id_item_select.setImageResource(R.drawable.icon_selected_press);
				holder.id_item_image.setColorFilter(Color.parseColor("#77000000"));
			}
			onPhotoSelectedListener.photoClick(mSelectedImage);
		}
		
	}
	class ViewHolder{
		ImageView id_item_image;
		ImageButton id_item_select;
	}
	class ViewHolder2{
		LinearLayout id_item_image2;
	}
	public OnPhotoSelectedListener onPhotoSelectedListener;
	public void setOnPhotoSelectedListener(OnPhotoSelectedListener onPhotoSelectedListener){
		this.onPhotoSelectedListener=onPhotoSelectedListener;
	}
	public  interface OnPhotoSelectedListener{
		public void photoClick(List<String> number);
	}

}
