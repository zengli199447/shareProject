package com.example.administrator.sharenebulaproject.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

//import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.app.PayTask;
import com.example.administrator.sharenebulaproject.global.AppKeyConfig;
import com.example.administrator.sharenebulaproject.global.MyApplication;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.model.event.EventCode;
import com.example.administrator.sharenebulaproject.rxtools.RxBus;
import com.example.administrator.sharenebulaproject.utils.LogUtil;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;
import com.example.administrator.sharenebulaproject.utils.ToastUtil;
import com.example.administrator.sharenebulaproject.widget.pay.OrderInfoUtil2_0;
import com.example.administrator.sharenebulaproject.widget.pay.PayResult;
import com.example.administrator.sharenebulaproject.widget.pay.PayUitl;
import com.google.gson.Gson;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.umeng.commonsdk.stateless.UMSLEnvelopeBuild.mContext;

/**
 * Created by Administrator on 2018/8/30.
 */

public class PayBuilder {

    private Activity activity;
    private ToastUtil toastUtil;
    private String TAG = "PayBuilder";

    public PayBuilder(Activity activity, ToastUtil toastUtil) {
        this.activity = activity;
        this.toastUtil = toastUtil;
    }

//    public void alPayFilter() {
//        if (TextUtils.isEmpty(AppKeyConfig.APPID) || (TextUtils.isEmpty(AppKeyConfig.RSA2_PRIVATE) && TextUtils.isEmpty(AppKeyConfig.RSA_PRIVATE))) {
//            new AlertDialog.Builder(activity).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
//                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialoginterface, int i) {
//                            activity.finish();
//                        }
//                    }).show();
//            return;
//        } else {
//            alPay();
//        }
//    }

//    public void alPay() {
//        boolean rsa2 = (AppKeyConfig.RSA2_PRIVATE.length() > 0);
//        Map<String, String> keyValues = new HashMap<>();
//        LinkedHashMap<String, String> value = new LinkedHashMap();
//        value.put("timeout_express","30");
//        value.put("product_code","1535593583126");
//        value.put("total_amount","0.01");
//        value.put("subject","1");
//        value.put("body","会员升级");
//        value.put("out_trade_no","1535593583126");
//        String json = new Gson().toJson(value);
//        keyValues.put("app_id", AppKeyConfig.APPID);
//        keyValues.put("biz_content", json);
//        keyValues.put("charset", "utf-8");
//        keyValues.put("method", "alipay.trade.app.pay");
//        keyValues.put("sign_type", AppKeyConfig.RSA2_PRIVATE);
//        keyValues.put("timestamp", SystemUtil.getCurrentTime());
//        keyValues.put("version", "1.0");
//
////        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(AppKeyConfig.APPID, rsa2);
//        String orderParam = OrderInfoUtil2_0.buildOrderParam(keyValues);
//
//        String privateKey = rsa2 ? AppKeyConfig.RSA2_PRIVATE : AppKeyConfig.RSA_PRIVATE;
//
//        String sign = OrderInfoUtil2_0.getSign(keyValues, privateKey, rsa2); //加签
//        final String orderInfo = orderParam + "&" + sign;
//
//        MyApplication.executorService.submit(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    PayTask alipay = new PayTask(activity);
//                    Map<String, String> result = alipay.payV2(orderInfo, true);
//                    Log.e("msp", result.toString());
//                    String resultStatus = result.get("resultStatus");
//                    String resultInfo = result.get("result");
//                    String memo = result.get("memo");
//                    LogUtil.e(TAG,"resultStatus : " + resultStatus);
//                    LogUtil.e(TAG,"resultInfo : " + resultInfo);
//                    LogUtil.e(TAG,"memo : " + memo);
//                } catch (Exception e) {
//                    LogUtil.e(TAG, "Exception : " + e.toString());
//                    e.printStackTrace();
//                }
//            }
//        });
//
//    }

    public void doAlipay(String subject, String body, String out_trade_no, String notify_url, String partner, String seller, String private_key, String totalmoney) {
        // 订单
        String orderInfo = PayUitl.getOrderInfo(subject, body, out_trade_no, notify_url, totalmoney, partner, seller);
        // 对订单做RSA 签名
        String sign = PayUitl.sign(orderInfo, private_key);
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 完整的符合支付宝参数规范的订单信息
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + PayUitl.getSignType();

        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                int status = 0;
                // 构造PayTask 对象
                PayTask alipay = new PayTask(activity);
                // 调用支付接口，获取支付结果
                final String result = alipay.pay(payInfo, true);
                PayResult payResult = new PayResult(result);
                String resultStatus = payResult.getResultStatus();
                String resultInfo = payResult.getResult();
                String memo = payResult.getMemo();
                LogUtil.e(TAG, "resultStatus : " + resultStatus);
                LogUtil.e(TAG, "resultInfo : " + resultInfo);
                LogUtil.e(TAG, "memo : " + memo);
                if (memo.contains("取消")) {
                    return;
                }
                if (TextUtils.equals(resultStatus, "9000")) {
                    status = 0;
                } else if (TextUtils.equals(resultStatus, "8000")) {
                    status = 2;
                } else {
                    status = 1;
                }
                RxBus.getDefault().post(new CommonEvent(EventCode.PAY_RETURN, status));
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    public interface onPayStatusListener {
        void payStatus(int status);
    }

    public onPayStatusListener payStatusListener;

    public void setOnPayStatusListener(onPayStatusListener payStatusListener) {
        this.payStatusListener = payStatusListener;
    }

    //微信支付
    public void formatWxPayContent(String content) {
        JSONObject payObj = null;
        try {
            payObj = new JSONObject(content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        doWeiXinPay(payObj.optString("appid"), payObj.optString("partnerid"), payObj.optString("prepayid"), payObj.optString("noncestr"),
                payObj.optString("timestamp"), payObj.optString("package"), payObj.optString("sign"));
    }

    public void doWeiXinPay(String appId, String partnerId, String prepayId, String nonceStr, String timeStamp, String packageValue, String sign) {
        IWXAPI api = WXAPIFactory.createWXAPI(activity, null);
        api.registerApp(appId);
        PayReq req = new PayReq();
        req.appId = appId;
        req.partnerId = partnerId;
        req.prepayId = prepayId;
        req.nonceStr = nonceStr;
        req.timeStamp = timeStamp;
        req.packageValue = packageValue;
        req.sign = sign;
        boolean sendReq = api.sendReq(req);
        LogUtil.e(TAG, "sendReq : " + sendReq);
    }

}
