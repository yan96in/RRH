//package com.cbkj.rrh.discover.view;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.cbkj.rrh.R;
//import com.cbkj.rrh.bean.TaskBean;
//import com.cbkj.rrh.bean.TaskReciverBean;
//import com.cbkj.rrh.main.MainActivity;
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
//import com.cbkj.rrh.view.widget.TitleBar;
//import com.cbkj.rrh.wxapi.WXPayEntryActivity;
//
///**
// * @todo:支付详情
// * @date:2015-9-8 下午4:19:56
// * @author:hg_liuzl@163.com
// */
//public class PayDetailActivity extends BaseBackActivity implements OnClickListener,TaskListenerWithState {
//	/**支付状态*/
//	public static final String PAY_STATUS = "pay_status";
//	private TaskBean mTaskBean;
//	private TaskReciverBean mTaskReciverBean;
//	private PayBean mPayBean;
//	/**btn1,可以重新支付与完成，btn2取消订单**/
//	private Button btn1,btn2;	
//	
//	/**是否从支付界面回来*/
//	private boolean fromPay = false;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.a_pay_detail_layout);
//		mPayBean = (PayBean) getIntent().getSerializableExtra(PayBean.KEY_PAY_BEAN);
//		mTaskBean = (TaskBean) getIntent().getSerializableExtra(TaskBean.KEY_TASK_BEAN);
//		mTaskReciverBean = (TaskReciverBean) getIntent().getSerializableExtra(TaskReciverBean.KEY_RECIVER_BEAN);
//		initView();
//	}
//
//	private void initView() {
//		TitleBar bar = new TitleBar(mActivity);
//		bar.initAllBar("支付详情");
//		
//		TextView tv = (TextView) findViewById(R.id.tv_describe);
//		tv.setText(mTaskBean.content);
//		
//		tv = (TextView) findViewById(R.id.tv_reciver);
//		tv.setText(mTaskReciverBean.nickName);
//		
//		tv = (TextView) findViewById(R.id.tv_money);
//		tv.setText(mTaskReciverBean.charges + "元");
//		
//	}
//	
//	
//	
//	@Override
//	protected void onResume() {
//		super.onResume();
//		doPayResult();
//		initResult();
//	}
//	
//	/**
//	 * 
//	 * @todo:根据支付结果来展示界面
//	 * @date:2015-9-8 下午5:11:18
//	 * @author:hg_liuzl@163.com
//	 * @params:
//	 */
//	private void initResult() {
//		
//		ImageView ivStatus = (ImageView) findViewById(R.id.iv_pay_status);
//		ivStatus.setBackgroundResource(mPayBean.payResult?R.drawable.icon_pay_success:R.drawable.icon_pay_fail);
//		
//		TextView tv = (TextView) findViewById(R.id.tv_pay_status);
//		tv.setText(mPayBean.payResult?"支付成功":"支付失败");
//		
//
//		btn1 = (Button) findViewById(R.id.btn_pay_again);
//		btn1.setOnClickListener(this);
//		btn1.setText(mPayBean.payResult?"完成":"重新支付");
//		
//		
//		btn2 = (Button) findViewById(R.id.btn_pay_cancel);
//		btn2.setOnClickListener(this);
//		btn2.setVisibility(mPayBean.payResult?View.GONE:View.VISIBLE);
//
//	}
//	
//
//	
//	/**支付状态*/
//	private void doPayResult() {
//		if (mPayBean.payWay == PayType.WXPAY) { // 微信支付
//			mPayBean.payResult = WXPayEntryActivity.mWXPayResult == 0 ? true: false;
//			if (mPayBean.payResult) {
//				doSubmit();
//			}
//		}
//	}
//	
///*	@Override
//	protected void onPause() {	//只关注微信支付的.
//		super.onPause();
//		this.fromPay = true;
//	}*/
//	
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.btn_pay_again:	
//			if (mPayBean.payResult) {	//支付完成
//				goHomePage();
//			} else {	//支付失败，需要重新支付的 
//				mPayBean.doPay(mActivity,payCallback);
//			}
//			break;
//		case R.id.btn_pay_cancel:	//取消支付
//			goHomePage();
//			break;
//
//		default:
//			break;
//		}
//	}
//	
//	/**
//	 * 
//	 * @todo:回去首页
//	 * @date:2015-9-8 下午5:14:53
//	 * @author:hg_liuzl@163.com
//	 * @params:
//	 */
//	private void goHomePage() {
//		Intent intent = new Intent(mActivity,MainActivity.class);
//		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
//		intent.putExtra(MainActivity.SHOW_CURRENT_TAB, MainActivity.SHOW_OF_SECOND_TAG);
//		startActivity(intent);
//		finish();
//	}
//	
//	/**支付宝支付**/
//	private IPayCallback payCallback = new IPayCallback() {
//		@Override
//		public void paySuccess() {
//			BToast.show(mActivity, "支付成功！");
//			mPayBean.payResult = true;
//			initResult();
//			doSubmit();
//		}
//		
//		@Override
//		public void payFail() {
//			BToast.show(mActivity, "支付失败，请稍后再试！");
//			mPayBean.payResult = false;
//			PayDetailActivity.enterPayDetail(mActivity, mPayBean, mTaskBean, mTaskReciverBean);
//		}
//	};
//	
//	
//	
//	/**
//	 * 
//	 * @todo:TODO
//	 * @date:2015-9-8 下午6:32:09
//	 * @author:hg_liuzl@163.com
//	 * @params:@param activity
//	 */
//	public static void enterPayDetail(Activity activity,PayBean paybean,TaskBean taskBean,TaskReciverBean taskReciverBEan){
//		Intent intent = new Intent(activity,PayDetailActivity.class);
//		intent.putExtra(PayBean.KEY_PAY_BEAN, paybean);
//		intent.putExtra(TaskBean.KEY_TASK_BEAN, taskBean);
//		intent.putExtra(TaskReciverBean.KEY_RECIVER_BEAN, taskReciverBEan);
//		activity.startActivity(intent);
//		activity.finish();
//	}
//	
//	private void doSubmit() {
//		TaskRequest.getInstance().requestChooseTask(this, mActivity, mTaskBean.taskId, mTaskBean.userId, mTaskReciverBean.userId,pUitl.getAccountPassword(),mPayBean.payWay == PayType.ALIPAY?1:2);
//	}
//
//	@Override
//	public void onTaskOver(HttpRequest request, HttpResponse response) {
//		if (response.getState() == HttpTaskState.STATE_OK) {
//			if (HttpURL.URL_SELECT_RECIVER == request.getRequestUrl() && response.result!=null) {	//确定完成任务
//				if (response.result.getSuccess()) {
//					BToast.show(mActivity, "确定接单人成功！");
//				} else {
//					BToast.show(mActivity, response.result.getErrorMsg());
//				}
//			} else {
//				BToast.show(mActivity, R.string.system_error);
//			}
//		}
//	}
//}
