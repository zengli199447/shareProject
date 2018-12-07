package com.example.administrator.sharenebulaproject.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobads.SplashAd;
import com.baidu.mobads.SplashAdListener;
import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.base.BaseActivity;
import com.example.administrator.sharenebulaproject.global.AppKeyConfig;
import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.model.bean.TheNewTypeNetBean;
import com.example.administrator.sharenebulaproject.model.bean.ValidationCodeNet;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.rxtools.RxUtil;
import com.example.administrator.sharenebulaproject.utils.LogUtil;
import com.example.administrator.sharenebulaproject.widget.CommonSubscriber;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/11/28.
 * 邮箱：229017464@qq.com
 * remark: 引导页、广告
 */
public class AdvertisingActivity extends BaseActivity implements SplashAdListener, View.OnClickListener {

    @BindView(R.id.advertising)
    RelativeLayout advertising;
    @BindView(R.id.action)
    TextView action;
    public boolean canJumpImmediately = false;
    private int time;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case 0:
                    startActivity(new Intent(AdvertisingActivity.this, HomeActivity.class));
                    finish();
                    break;
                case 1:
                    seconds();
                    break;
            }

        }
    };

    private void seconds() {
        if (time > 0 && action != null) {
            time = time - 1;
            action.setText(new StringBuffer().append("(").append(time).append(")").append("  ").append(getString(R.string.action)).toString());
            handler.sendEmptyMessageDelayed(1, 999);
        }
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_advertising;
    }

    @Override
    protected void initClass() {

    }

    @Override
    protected void initData() {
        time = getResources().getInteger(R.integer.action_time);
        TheNewsTypeNetWork();
    }

    @Override
    protected void initView() {
        new SplashAd(this, advertising, this, AppKeyConfig.ADVERTISING_BAIDU_ADVERTISING_SPLASH_ID, true);
    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.action})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action:
                handler.sendEmptyMessage(0);
                break;
        }
    }

    @Override
    public void onAdPresent() {
        LogUtil.e(TAG, "onAdPresent");
    }

    @Override
    public void onAdDismissed() {
        LogUtil.e(TAG, "onAdDismissed");
        jumpWhenCanClick(); // 跳转至您的应用主界面
    }

    @Override
    public void onAdFailed(String s) {
        LogUtil.e(TAG, "onAdFailed");
        this.startActivity(new Intent(this, HomeActivity.class));
        this.finish();
    }

    @Override
    public void onAdClick() {
        LogUtil.e(TAG, "onAdClick");
    }

    private void jumpWhenCanClick() {
        Log.d("test", "this.hasWindowFocus():" + this.hasWindowFocus());
        if (canJumpImmediately) {
            this.startActivity(new Intent(this, HomeActivity.class));
            this.finish();
        } else {
            canJumpImmediately = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (canJumpImmediately) {
            jumpWhenCanClick();
        }
        canJumpImmediately = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        canJumpImmediately = false;
    }

    //新闻类型获取
    private void TheNewsTypeNetWork() {
        handler.sendEmptyMessageDelayed(1, 999);
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.CATE_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.getTheNewTypeNetBean(map)
                .compose(RxUtil.<TheNewTypeNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<TheNewTypeNetBean>(toastUtil) {
                    @Override
                    public void onNext(TheNewTypeNetBean theNewTypeNetBean) {
                        if (theNewTypeNetBean.getStatus() == 1) {
                            List<TheNewTypeNetBean.ResultBean.AllBean> all = theNewTypeNetBean.getResult().getAll();
                            List<TheNewTypeNetBean.ResultBean.SelectBean> select = theNewTypeNetBean.getResult().getSelect();
                            DataClass.ALL_TYPE_TITLE.addAll(all);
                            if (select.size() == 0) {
                                DataClass.TYPE_TITLE.addAll(all);
                            } else {
                                DataClass.TYPE_TITLE.addAll(select);
                            }
                        } else {
                            toastUtil.showToast(theNewTypeNetBean.getMessage());
                        }
                    }
                }));
    }

}
