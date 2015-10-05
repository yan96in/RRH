package com.cbkj.rrh.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cbkj.rrh.R;
import com.cbkj.rrh.bean.TradeBean;
import com.cbkj.rrh.main.base.KBaseAdapter;
import com.cbkj.rrh.others.utils.ToolUtils;

/**
 * @todo:交易适配
 * @date:2015-9-10 上午11:11:53
 * @author:hg_liuzl@163.com
 */
public class TradeAdapter extends KBaseAdapter {

	public TradeAdapter(List<?> mList, Activity mActivity) {
		super(mList, mActivity);
	}

	ViewHolder vHolder;
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if (null == convertView) {
			vHolder = new ViewHolder();
//			convertView = mInflater.inflate(R.layout.item_trade_layout, parent, false);
//			vHolder.tvID = (TextView) convertView.findViewById(R.id.tv_trade_id);
//			vHolder.tvWay = (TextView) convertView.findViewById(R.id.tv_trade_way);
//			vHolder.tvMoney = (TextView) convertView.findViewById(R.id.tv_trade_money);
//			vHolder.tvTime = (TextView) convertView.findViewById(R.id.tv_trade_time);
//			vHolder.tvReason = (TextView) convertView.findViewById(R.id.tv_trade_reason);
//			vHolder.tvStatus = (TextView) convertView.findViewById(R.id.tv_trade_status);
			convertView.setTag(vHolder);
		} else {
			vHolder = (ViewHolder) convertView.getTag();
		}
		
		final TradeBean trade = (TradeBean) mList.get(position);
		
		vHolder.tvID.setText(trade.orderId);
		vHolder.tvWay.setText(trade.payment == 1?"支付宝支付":"微信支付");
		vHolder.tvMoney.setText(String.valueOf(trade.charges)+"元");
		vHolder.tvTime.setText(ToolUtils.getFormatTime(trade.time, "yyyy-MM-dd HH:mm:ss"));
		vHolder.tvReason.setText(trade.remark);
		
		vHolder.tvStatus.setBackgroundResource(trade.type == 1?R.drawable.icon_trade_money_enter:R.drawable.icon_trade_money_out);
		vHolder.tvStatus.setPadding(20, 5, 20, 5);
		StringBuffer sb = new StringBuffer();
		sb.append(trade.type == 1?"+":"-");
		sb.append(trade.charges);
		sb.append("元");
		vHolder.tvStatus.setText(sb.toString());
		
		
		
		
		
		return convertView;
	}

	
	final class ViewHolder{
		TextView tvID,tvWay,tvReason,tvMoney,tvTime,tvStatus;
	}
	
	
	
	
	
	
	
	
}
