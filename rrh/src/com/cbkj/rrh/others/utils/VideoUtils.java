package com.cbkj.rrh.others.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore.Video.Thumbnails;
import android.widget.ImageView;

/**
 * @todo:视频帮助类
 * @date:2015-5-16 下午2:18:12
 * @author:hg_liuzl@163.com
 */
public class VideoUtils {

	/** 根据视频获取视频最后一帧 */
	public static void initVideoThumbNail(String videoPath, ImageView ivThumb) {
		Bitmap fristFrameOfVideo = ThumbnailUtils.createVideoThumbnail(
				videoPath, Thumbnails.MINI_KIND);// 创建视频缩略图(240*320)
		ivThumb.setScaleType(ImageView.ScaleType.CENTER_CROP);
		ivThumb.setImageBitmap(fristFrameOfVideo);
	}

	/** 根据视频获取视频最后一帧 */
	public static Bitmap getVideoThumbNail(String videoPath) {
		return ThumbnailUtils.createVideoThumbnail(videoPath,
				Thumbnails.MINI_KIND);// 创建视频缩略图(240*320)
	}

	/**
	 * 选择照片
	 */
	public static void selectVideo(Activity mActivity, int requestCode) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_PICK);
		intent.setType("video/*");
		mActivity.startActivityForResult(intent, requestCode);
	}
}
