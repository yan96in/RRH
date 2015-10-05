//package com.cbkj.rrh.discover.view;
//
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.TextView;
//
//import com.cbkj.rrh.R;
//import com.cbkj.rrh.bean.TaskBean;
//import com.cbkj.rrh.bean.TaskReciverBean;
//import com.cbkj.rrh.main.base.BaseBackActivity;
//import com.cbkj.rrh.net.http.HttpRequest;
//import com.cbkj.rrh.net.http.HttpRequestAsyncTask.TaskListenerWithState;
//import com.cbkj.rrh.net.http.HttpResponse;
//import com.cbkj.rrh.net.http.HttpResponse.HttpTaskState;
//import com.cbkj.rrh.net.http.HttpURL;
//import com.cbkj.rrh.net.request.TaskRequest;
//import com.cbkj.rrh.pay.IPayCallback;
//import com.cbkj.rrh.pay.PayBean;
//import com.cbkj.rrh.pay.PayBean.PayType;
//import com.cbkj.rrh.view.BToast;
//import com.cbkj.rrh.view.PayRadioButton;
//import com.cbkj.rrh.view.widget.TitleBar;
//import com.cbkj.rrh.wxapi.WXPayEntryActivity;
//
///**
// * @todo:确定给接单人
// * @date:2015-8-20 下午4:56:27
// * @author:hg_liuzl@163.com
// */
//public class OrderPayActivity extends BaseBackActivity implements OnClickListener,TaskListenerWithState {
//	
//	private TaskBean mTaskBean;
//	private TaskReciverBean mTaskReciverBean;
//	private PayRadioButton mPayAli,mPayWx;
//	private PayType mPayType = PayType.ALIPAY;
//	private PayBean mPayBean = new PayBean();
//	
//	/**是否从支付界面回来的*/
//	private boolean fromPay = false;
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.a_affirm_reciver_layout);
//		
//		mTaskBean = (TaskBean) getIntent().getSerializableExtra(TaskBean.KEY_TASK_BEAN);
//		mTaskReciverBean = (TaskReciverBean) getIntent().getSerializableExtra(TaskReciverBean.KEY_RECIVER_BEAN);
//		
//		initView();
//	}
//	
//	
//	@Override
//	protected void onResume() {
//		super.onResume();
//		doPayResult();
//	}
//	
//	/**支付状态*/
//	private void doPayResult() {	//只关注微信支付的
//		if (fromPay) {
////			if (mPayType == PayType.ALIPAY) {	//支付宝支付
////				mPayBean.payResult = AliPayUtil.mAliPay;
////			} else {//如果是微信支付
////				mPayBean.payResult = WXPayEntryActivity.mWXPayResult == 0 ? true:false;
////			}
//			if (mPayType == PayType.WXPAY) {
//				mPayBean.payResult = WXPayEntryActivity.mWXPayResult == 0 ? true:false;
//				PayDetailActivity.enterPayDetail(mActivity, mPayBean, mTaskBean, mTaskReciverBean);
//			}
//		}
//	}
//
//	@Override
//	protected void onPause() {
//		super.onPause();
//		this.fromPay = true;
//	}
//	
//	private void initView() {
//		TitleBar bar = new TitleBar(mActivity);
//		bar.initAllBar("确定接单人");
//		
//		TextView tv = (TextView) findViewById(R.id.tv_describe);
//		tv.setText(mTaskBean.content);
//		
//		
//		tv = (TextView) findViewById(R.id.tv_reciver);
//		tv.setText(mTaskReciverBean.nickName);
//		
//		
//		tv = (TextView) findViewById(R.id.tv_money);
//		tv.setText(mTaskReciverBean.charges+"元");
//		
//		mPayAli = (PayRadioButton) findViewById(R.id.pay_alipay);
//		mPayAli.setPayCheck(true);
//		mPayAli.setOnClickListener(this);
//		
//		mPayWx = (PayRadioButton) findViewById(R.id.pay_wxpay);
//		mPayWx.setOnClickListener(this);
//		
//		findViewById(R.id.btn_submit).setOnClickListener(this);
//	}
//
//	private void doSubmit() {
//		TaskRequest.getInstance().requestChooseTask(this, mActivity, mTaskBean.taskId, mTaskBean.userId, mTaskReciverBean.userId,pUitl.getAccountPassword(),mPayBean.payWay == PayType.ALIPAY?1:2);
//	}
//
//
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.btn_submit:
//			mPayBean.payWay = mPayType;
//			mPayBean.payMoney = mTaskReciverBean.charges;
//			mPayBean.doPay(mActivity,payCallback);
//			break;
//		case R.id.pay_alipay:
//			if (!mPayAli.isPayChecked()) {
//				mPayAli.setPayCheck(true);
//				mPayWx.setPayCheck(false);
//				mPayType = PayType.ALIPAY;
//			}
//			break;
//		case R.id.pay_wxpay:
//			if (!mPayWx.isPayChecked()) {
//				mPayAli.setPayCheck(false);
//				mPayWx.setPayCheck(true);
//				mPayType = PayType.WXPAY;
//			}
//			break;
//
//		default:
//			break;
//		}
//	}
//	
//	private IPayCallback payCallback = new IPayCallback() {
//		@Override
//		public void paySuccess() {
//			BToast.show(mActivity, "支付成功！");
//			mPayBean.payResult = true;
//			doSubmit();
//			//PayDetailActivity.enterPayDetail(mActivity, mPayBean, mTaskBean, mTaskReciverBean);
//		}
//		
//		@Override
//		public void payFail() {
//			BToast.show(mActivity, "支付失败！");
//			mPayBean.payResult = false;
//			PayDetailActivity.enterPayDetail(mActivity, mPayBean, mTaskBean, mTaskReciverBean);
//		}
//	};
//	
//
//	@Override
//	public void onTaskOver(HttpRequest request, HttpResponse response) {
//		if (response.getState() == HttpTaskState.STATE_OK) {
//			if (HttpURL.URL_SELECT_RECIVER == request.getRequestUrl() && response.result!=null) {	//确定完成任务
//				if (response.result.getSuccess()) {
//					BToast.show(mActivity, "确定接单人成功！");
//					PayDetailActivity.enterPayDetail(mActivity, mPayBean, mTaskBean, mTaskReciverBean);
//				} else {
//					BToast.show(mActivity, response.result.getErrorMsg());
//				}
//			} else {
//				BToast.show(mActivity, R.string.system_error);
//			}
//		}
//	}
//}
