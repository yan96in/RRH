package com.cbkj.rrh.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cbkj.rrh.R;
import com.cbkj.rrh.bean.CommentBean;

/**
 * @todo:与我相关部分的简单评论，微墙与炫能地方可用
 * @date:2014-12-5 下午3:27:01
 * @author:hg_liuzl@163.com
 */
public class CorrelationCommentAdapter extends KBaseAdapter {

	public CorrelationCommentAdapter(List<?> mList, Activity mActivity) {
		super(mList, mActivity);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
	   final Holder holder;
        if (convertView == null)
        {
            holder = new Holder();
            convertView = mInflater.inflate(R.layout.item_correlation_comment, parent, false);
            holder.tvCommentAuthor = (TextView) convertView.findViewById(R.id.tv_nick);
            holder.tvCommentContent = (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(holder);
        } else
        {
            holder = (Holder) convertView.getTag();
        }
        
       final CommentBean wComment = (CommentBean) mList.get(position);
        
        holder.tvCommentAuthor.setText(wComment.nickName+":");
        holder.tvCommentContent.setText(wComment.content);
        
		return convertView;
	}

	final class Holder
    {
        TextView tvCommentAuthor;
        TextView tvCommentContent;
    }
}
