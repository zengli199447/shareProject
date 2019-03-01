package com.example.administrator.sharenebulaproject.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobads.SplashAd;
import com.baidu.mobads.SplashAdListener;
import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.base.BaseActivity;
import com.example.administrator.sharenebulaproject.global.AppKeyConfig;
import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.global.MyApplication;
import com.example.administrator.sharenebulaproject.model.bean.TheNewTypeNetBean;
import com.example.administrator.sharenebulaproject.model.bean.ValidationCodeNet;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.rxtools.RxUtil;
import com.example.administrator.sharenebulaproject.utils.LocationUtils;
import com.example.administrator.sharenebulaproject.utils.LogUtil;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;
import com.example.administrator.sharenebulaproject.widget.CommonSubscriber;
import com.google.gson.Gson;
import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;
import com.qq.e.comm.util.AdError;

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
public class AdvertisingActivity extends BaseActivity implements View.OnClickListener, SplashADListener {

    @BindView(R.id.advertising)
    ImageView advertising;
    @BindView(R.id.action)
    TextView action;
    @BindView(R.id.advertsing_text)
    TextView advertsing_text;
    @BindView(R.id.splash_container)
    ViewGroup splash_container;
    public boolean canJumpImmediately = false;
    private int time;

    private int minSplashTimeWhenNoAD = 5000;
    private boolean actionStatus;

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
    private long fetchSplashADTime;
    private SplashAD splashAD;

    private void seconds() {
        if (time > 0 && action != null) {
            if (actionStatus)
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
        MyApplication.executorService.submit(new Runnable() {
            @Override
            public void run() {
                LocationUtils.getCNBylocation(AdvertisingActivity.this);
            }
        });
    }

    @Override
    protected void initData() {
        time = getResources().getInteger(R.integer.action_time);
        TheNewsTypeNetWork();
    }

    @Override
    protected void initView() {
        SystemUtil.textMagicTool(this, advertsing_text, getString(R.string.app_name), getString(R.string.read_advertising),
                R.dimen.dp25, R.dimen.dp12, R.color.black_overlay, R.color.gray_light_text, "\n");
        fetchSplashADTime = System.currentTimeMillis();
        splashAD = new SplashAD(this, splash_container, advertsing_text, AppKeyConfig.ADVERTISING_QQID, AppKeyConfig.ADVERTISING_QQ_SPLASH_ADVERTISING_ID, this, 0);
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

    private void jumpWhenCanClick() {
        if (canJumpImmediately && !actionStatus) {
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
        if (actionStatus) {
            this.startActivity(new Intent(this, HomeActivity.class));
            this.finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        canJumpImmediately = false;
        LogUtil.e(TAG, "actionStatus : " + actionStatus);
    }

    @Override
    public void onADDismissed() {
        LogUtil.e(TAG, "onADDismissed");
    }

    @Override
    public void onNoAD(AdError adError) {
        LogUtil.e(TAG, "onNoAD");
    }

    @Override
    public void onADPresent() {
        advertising.setVisibility(View.INVISIBLE); // 广告展示后一定要把预设的开屏图片隐藏起来
    }

    @Override
    public void onADClicked() {
        LogUtil.e(TAG, "onADClicked");
        actionStatus = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    public void onADTick(long millisUntilFinished) {
        action.setText(String.format(getString(R.string.action), Math.round(millisUntilFinished / 1000f)));
        LogUtil.e(TAG, "onADTick");
    }

    @Override
    public void onADExposure() {
        long alreadyDelayMills = System.currentTimeMillis() - fetchSplashADTime;//从拉广告开始到onNoAD已经消耗了多少时间
        long shouldDelayMills = alreadyDelayMills > minSplashTimeWhenNoAD ? 0 : minSplashTimeWhenNoAD
                - alreadyDelayMills;//为防止加载广告失败后立刻跳离开屏可能造成的视觉上类似于"闪退"的情况，根据设置的minSplashTimeWhenNoAD
        // 计算出还需要延时多久
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!actionStatus) {
                    LogUtil.e(TAG, "actionStatus : " + actionStatus);
                    startActivity(new Intent(AdvertisingActivity.this, HomeActivity.class));
                    finish();
                }
            }
        }, shouldDelayMills);
    }

    /**
     * 开屏页一定要禁止用户对返回按钮的控制，否则将可能导致用户手动退出了App而广告无法正常曝光和计费
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //新闻类型获取
    private void TheNewsTypeNetWork() {
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
