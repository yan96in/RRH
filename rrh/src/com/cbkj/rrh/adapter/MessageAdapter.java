package com.cbkj.rrh.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cbkj.rrh.R;
import com.cbkj.rrh.bean.MessageBean;
import com.cbkj.rrh.main.BGApp;
import com.cbkj.rrh.main.base.KBaseAdapter;
import com.cbkj.rrh.others.utils.ToolUtils;


/**
 * 
 * @todo:消息适配器
 * @date:2015-8-25 上午11:06:22
 * @author:hg_liuzl@163.com
 */
public class MessageAdapter extends KBaseAdapter 
{
	public MessageAdapter(List<?> mList, Activity mActivity) {
		super(mList, mActivity);
	}

	@Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
       final ViewHolder holder;
        if (convertView == null)
        {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_message_layout, null);
            holder.ivHeader = (ImageView) convertView.findViewById(R.id.iv_img);
            holder.tvNick = (TextView) convertView.findViewById(R.id.tv_nick);
            holder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)convertView.getTag();
        }
        
        final MessageBean message = (MessageBean) mList.get(position);
		
        if (message.type==3) {
        	holder.ivHeader.setBackgroundResource(R.drawable.icon_message_bg);
        	holder.tvNick.setText("啦啦私活");
		} else {
			BGApp.getInstance().setImageSqure(message.smallImg, holder.ivHeader);
			holder.tvNick.setText(message.nickName);
		}
        
        
		holder.tvTime.setText(ToolUtils.getFormatDate(message.created));
		
		holder.tvContent.setText(message.content);
        
        return convertView;
    }
	
    private final class ViewHolder
    {
        TextView tvNick,tvTime,tvContent;           // 用户名
        ImageView ivHeader;
    }
}
