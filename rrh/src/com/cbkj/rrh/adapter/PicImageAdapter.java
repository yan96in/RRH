package com.cbkj.rrh.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.cbkj.rrh.R;
import com.cbkj.rrh.bean.FileBean;
import com.cbkj.rrh.main.BGApp;
import com.cbkj.rrh.main.base.KBaseAdapter;
import com.cbkj.rrh.others.utils.LogUtils;

/***
 * @todo:图片处理界面
 * @date:2014-11-10 下午6:00:55
 * @author:hg_liuzl@163.com
 */
public class PicImageAdapter extends KBaseAdapter {

	private boolean showDelete = false;
	private OnClickListener mListener;
	private boolean mCurrentUser = false;

	public PicImageAdapter(List<?> mList, Activity mActivity,OnClickListener listener,boolean isCurrentUser) {
		super(mList, mActivity);
		this.mListener = listener;
		this.mCurrentUser = isCurrentUser;
	}

	@Override
	public int getCount() {
		return mCurrentUser?(mList.size() + 1):mList.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if (convertView == null) {
			holder = new Holder();
			convertView = mInflater.inflate(R.layout.item_img, parent, false);
			holder.imageImgV = (ImageView) convertView.findViewById(R.id.iv_img);
			holder.ivDelete = (ImageView) convertView.findViewById(R.id.iv_delete);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		
		if (mCurrentUser && position == mList.size()) {
			holder.imageImgV.setImageResource(R.drawable.btn_addpic_selector);
			holder.ivDelete.setVisibility(View.GONE);
		} else {
			LogUtils.i("----------------------"+position);
			final FileBean fileBean = (FileBean) mList.get(position);
			if (!fileBean.small.equals(holder.imageImgV.getTag())) {
				BGApp.getInstance().setImageSqure(fileBean.small, holder.imageImgV);
				holder.imageImgV.setTag(fileBean.small);
			}
			holder.ivDelete.setVisibility(showDelete ? View.VISIBLE:View.GONE);
			holder.ivDelete.setOnClickListener(mListener);
			holder.ivDelete.setTag(fileBean);
		}
		return convertView;
	}

	final class Holder {
		ImageView imageImgV, ivDelete;
	}
	
	/**
	 * 
	 * @todo:显示删除或编辑按钮
	 * @date:2015-5-25 下午3:17:07
	 * @author:hg_liuzl@163.com
	 * @params:@param isDelete
	 * @params:@param btnAction
	 */
	public void setDeleVisible(Button btnAction) {
		if (showDelete) {
			this.showDelete = false;
			btnAction.setText("编辑");
		}else{
			this.showDelete = true;
			btnAction.setText("完成");
		}
		notifyDataSetChanged();
	}
}
