package com.cbkj.rrh.view;

import java.util.List;

import android.app.Activity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cbkj.rrh.R;
import com.cbkj.rrh.bean.ScoreBean;

/**
 * @todo:评分部分
 * @date:2015-8-26 下午7:33:56
 * @author:hg_liuzl@163.com
 */
public class CommentView {

	private LinearLayout ll_comment;
	private int mTag = 0;
	
	public CommentView(Activity activity,String title, List<ScoreBean> scores) {
		initView(activity,title, scores);
		this.ll_comment = (LinearLayout) activity.findViewById(R.id.ll_comment);
	}
	
	public CommentView(int tag,Activity activity,String title, List<ScoreBean> scores) {
		initView(activity,title, scores);
		this.mTag = tag;
		this.ll_comment = (LinearLayout) activity.findViewById(R.id.ll_comment);
	}
	
	public CommentView(Activity activity,String title,int bgResource,List<ScoreBean> scores) {
		initView(activity,title, scores);
		this.ll_comment = (LinearLayout) activity.findViewById(R.id.ll_comment);
		this.ll_comment.setBackgroundResource(bgResource);
	}

	private void initView(final Activity mActivity,String title, final List<ScoreBean> scores) {
		TextView tv = (TextView) mActivity.findViewById(R.id.tv_score_title);
		tv.setText(title);
		if (scores == null || scores.size() == 0) {
			return;
		}
		for (int i = 0; i < scores.size(); i++) {
			final ScoreBean bean = scores.get(i);
			int location = i+1;
			switch (location) {
			case 1:
				tv = (TextView) mActivity.findViewById(R.id.tv_one);
				tv.setText(location+"."+bean.rulesName);
				tv = (TextView) mActivity.findViewById(R.id.tv_one_score);
				tv.setText(String.valueOf(bean.count)+"次");
				break;
			case 2:
				tv = (TextView) mActivity.findViewById(R.id.tv_two);
				tv.setText(location+"."+bean.rulesName);
				tv = (TextView) mActivity.findViewById(R.id.tv_two_score);
				tv.setText(String.valueOf(bean.count)+"次");
				break;

			case 3:
				tv = (TextView) mActivity.findViewById(R.id.tv_three);
				tv.setText(location+"."+bean.rulesName);
				tv = (TextView) mActivity.findViewById(R.id.tv_three_score);
				tv.setText(String.valueOf(bean.count)+"次");
				break;

			case 4:
				tv = (TextView) mActivity.findViewById(R.id.tv_four);
				tv.setText(location+"."+bean.rulesName);
				tv = (TextView) mActivity.findViewById(R.id.tv_four_score);
				tv.setText(String.valueOf(bean.count)+"次");
				break;

			default:
				break;
			}
		}
	}
	
	public void setBGVisible(int visible) {
		ll_comment.setVisibility(visible);
	}

}
