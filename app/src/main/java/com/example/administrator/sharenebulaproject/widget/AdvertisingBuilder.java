package com.example.administrator.sharenebulaproject.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baidu.mobad.feeds.RequestParameters;
import com.baidu.mobads.AdView;
import com.baidu.mobads.AdViewListener;
import com.baidu.mobads.BaiduNativeAdPlacement;
import com.baidu.mobads.BaiduNativeH5AdView;
import com.baidu.mobads.BaiduNativeH5AdViewManager;
import com.bumptech.glide.Glide;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdDislike;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTBannerAd;
import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.global.AppKeyConfig;
import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.rxtools.RxBus;
import com.example.administrator.sharenebulaproject.utils.LogUtil;
import com.qq.e.ads.cfg.MultiProcessFlag;
import com.qq.e.ads.cfg.VideoOption;
import com.qq.e.ads.nativ.ADSize;
import com.qq.e.ads.nativ.NativeAD;
import com.qq.e.ads.nativ.NativeADDataRef;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.ads.nativ.NativeMediaAD;
import com.qq.e.ads.nativ.NativeMediaADData;
import com.qq.e.comm.constants.AdPatternType;
import com.qq.e.comm.pi.AdData;
import com.qq.e.comm.util.AdError;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 作者：真理 Created by Administrator on 2018/12/3.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class AdvertisingBuilder implements NativeExpressAD.NativeExpressADListener, AdViewListener, TTAdNative.BannerAdListener {

    String TAG = getClass().getSimpleName();

    Context context;
    ViewGroup advertisingView;
    FrameLayout csjFrameLayout;

    private NativeExpressAD nativeExpressAD;
    private NativeExpressADView nativeExpressADView;
    private AdView adView;
    private TTAdNative mTTAdNative;
    private final Random random;

    public AdvertisingBuilder(Context context) {
        this.context = context;
        random = new Random();
//        MultiProcessFlag.setMultiProcess(true);
    }

    public void initQQAdvertising(ViewGroup view) {
        this.advertisingView = view;
        nativeExpressAD = new NativeExpressAD(context, getMyADSize(), AppKeyConfig.ADVERTISING_QQID, AppKeyConfig.ADVERTISING_QQ_ADVERTISING_ID, this); // 这里的Context必须为Activity
        nativeExpressAD.setVideoOption(new VideoOption.Builder()
                .setAutoPlayPolicy(VideoOption.AutoPlayPolicy.WIFI) // 设置什么网络环境下可以自动播放视频
                .setAutoPlayMuted(true) // 设置自动播放视频时，是否静音
                .build());
        nativeExpressAD.loadAD(1);
    }

    public void initNativeExpressAD(int pageNumber) {
        int i = random.nextInt(3) + 0;
        LogUtil.e(TAG, "i" + i);
        ADSize adSize = new ADSize(ADSize.FULL_WIDTH, ADSize.AUTO_HEIGHT); // 消息流中用AUTO_HEIGHT
        nativeExpressAD = new NativeExpressAD(context, adSize, AppKeyConfig.ADVERTISING_QQID, AppKeyConfig.ADVERTISING_QQ_ADVERTISING_ID, this);
        nativeExpressAD.loadAD(12 * pageNumber);
    }

    @Override
    public void onNoAD(AdError adError) {
        LogUtil.e(TAG, String.format("onNoAD, error code: %d, error msg: %s", adError.getErrorCode(), adError.getErrorMsg()));
    }

    private ADSize getMyADSize() {
        int w = ADSize.FULL_WIDTH;
        int h = ADSize.AUTO_HEIGHT;
        return new ADSize(w, h);
    }

    @Override
    public void onADLoaded(List<NativeExpressADView> list) {
        // 释放前一个展示的NativeExpressADView的资源
//        if (nativeExpressADView != null) {
//            nativeExpressADView.destroy();
//        }
//        if (advertisingView.getChildCount() > 0) {
//            advertisingView.removeAllViews();
//        }
//        nativeExpressADView = list.get(0);
//        LogUtil.e(TAG, "onADLoaded, video info: " + getAdInfo(nativeExpressADView));
//        // 广告可见才会产生曝光，否则将无法产生收益。
//        advertisingView.addView(nativeExpressADView);
//        nativeExpressADView.render();
        if (initDataListener != null)
            initDataListener.onInitDataListener(list);

    }

    @Override
    public void onRenderFail(NativeExpressADView nativeExpressADView) {
        LogUtil.e(TAG, "onRenderFail");
    }

    @Override
    public void onRenderSuccess(NativeExpressADView nativeExpressADView) {
        LogUtil.e(TAG, "onRenderSuccess");
    }

    @Override
    public void onADExposure(NativeExpressADView nativeExpressADView) {
        LogUtil.e(TAG, "onADExposure");
    }

    @Override
    public void onADClicked(NativeExpressADView nativeExpressADView) {
        LogUtil.e(TAG, "onADClicked");
    }

    @Override
    public void onADClosed(NativeExpressADView nativeExpressADView) {
        LogUtil.e(TAG, "onADClosed");
        if (initDataListener != null)
            initDataListener.onRemoverView(nativeExpressADView);
    }

    @Override
    public void onADLeftApplication(NativeExpressADView nativeExpressADView) {
        LogUtil.e(TAG, "onADLeftApplication");
    }

    @Override
    public void onADOpenOverlay(NativeExpressADView nativeExpressADView) {
        LogUtil.e(TAG, "onADOpenOverlay");
    }

    @Override
    public void onADCloseOverlay(NativeExpressADView nativeExpressADView) {
        LogUtil.e(TAG, "onADCloseOverlay");
    }

    private String getAdInfo(NativeExpressADView nativeExpressADView) {
        AdData adData = nativeExpressADView.getBoundData();
        if (adData != null) {
            StringBuilder infoBuilder = new StringBuilder();
            infoBuilder.append("title:").append(adData.getTitle()).append(",")
                    .append("desc:").append(adData.getDesc()).append(",")
                    .append("patternType:").append(adData.getAdPatternType());
            if (adData.getAdPatternType() == AdPatternType.NATIVE_VIDEO) {

            }
            return infoBuilder.toString();
        }
        return null;
    }

    public void initBaiDuAdvertising(RelativeLayout baiDuRelativeLayout) {
        adView = new AdView(context, AppKeyConfig.ADVERTISING_BAIDUID);
        adView.setListener(this);
        // 将adView添加到父控件中(注：该父控件不一定为您的根控件，只要该控件能通过addView能添加广告视图即可)
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(DataClass.PX_WINDOWS_WIDTH, DataClass.PX_WINDOWS_WIDTH * 3 / 7);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        baiDuRelativeLayout.addView(adView, layoutParams);
    }


    @Override
    public void onAdReady(AdView adView) {
        LogUtil.e(TAG, "资源已经缓存完毕，还没有渲染出来 : onAdReady " + adView);
    }

    @Override
    public void onAdShow(JSONObject jsonObject) {
        LogUtil.e(TAG, "广告已经渲染出来 : onAdShow " + jsonObject.toString());
    }

    @Override
    public void onAdClick(JSONObject jsonObject) {
        LogUtil.e(TAG, "onAdSwitch");
    }

    @Override
    public void onAdFailed(String s) {
        LogUtil.e(TAG, "onAdFailed " + s);
    }

    @Override
    public void onAdSwitch() {
        LogUtil.e(TAG, "onAdSwitch");
    }

    @Override
    public void onAdClose(JSONObject jsonObject) {
        LogUtil.e(TAG, "onAdClose");
    }

    public void initCsjAdvertising(FrameLayout csjFrameLayout) {
        this.csjFrameLayout = csjFrameLayout;
        mTTAdNative = TTAdManagerHolder.getInstance(context).createAdNative(context);
        TTAdManagerHolder.getInstance(context).requestPermissionIfNecessary(context);
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(AppKeyConfig.ADVERTISING_CSJ_ADVERTISING_ID) //广告位id
                .setSupportDeepLink(true)
                .setImageAcceptedSize(600, 260)
                .build();
        mTTAdNative.loadBannerAd(adSlot, this);
    }


    @Override
    public void onError(int i, String s) {
        LogUtil.e(TAG, "onError : " + s);
        csjFrameLayout.removeAllViews();
    }

    @Override
    public void onBannerAdLoad(TTBannerAd ttBannerAd) {
        if (ttBannerAd == null) {
            return;
        }
        View bannerView = ttBannerAd.getBannerView();
        if (bannerView == null) {
            return;
        }
        //设置轮播的时间间隔  间隔在30s到120秒之间的值，不设置默认不轮播
        ttBannerAd.setSlideIntervalTime(30 * 1000);
        csjFrameLayout.removeAllViews();
        csjFrameLayout.addView(bannerView);
        //设置广告互动监听回调
        ttBannerAd.setBannerInteractionListener(new TTBannerAd.AdInteractionListener() {
            @Override
            public void onAdClicked(View view, int type) {
                LogUtil.e(TAG, "广告被点击");
            }

            @Override
            public void onAdShow(View view, int type) {
                LogUtil.e(TAG, "广告展示");
            }
        });
        //（可选）设置下载类广告的下载监听
        bindDownloadListener(ttBannerAd);
        //在banner中显示网盟提供的dislike icon，有助于广告投放精准度提升
        ttBannerAd.setShowDislikeIcon(new TTAdDislike.DislikeInteractionCallback() {
            @Override
            public void onSelected(int position, String value) {
                LogUtil.e(TAG, "点击 : " + value);
                //用户选择不喜欢原因后，移除广告展示
                csjFrameLayout.removeAllViews();
            }

            @Override
            public void onCancel() {
                LogUtil.e(TAG, "点击取消");
            }
        });
    }

    private boolean mHasShowDownloadActive = false;

    private void bindDownloadListener(TTBannerAd ad) {
        ad.setDownloadListener(new TTAppDownloadListener() {
            @Override
            public void onIdle() {
                LogUtil.e(TAG, "点击图片开始下载");
            }

            @Override
            public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                if (!mHasShowDownloadActive) {
                    mHasShowDownloadActive = true;
                    LogUtil.e(TAG, "下载中，点击图片暂停");
                }
            }

            @Override
            public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                LogUtil.e(TAG, "下载暂停，点击图片继续");
            }

            @Override
            public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                LogUtil.e(TAG, "下载失败，点击图片重新下载");
            }

            @Override
            public void onInstalled(String fileName, String appName) {
                LogUtil.e(TAG, "安装完成，点击图片打开");
            }

            @Override
            public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                LogUtil.e(TAG, "点击图片安装");
            }
        });
    }

    public static void RecycleAdvertising(Context context, RelativeLayout relativeLayout, Object object) {
        relativeLayout.removeAllViews();
        BaiduNativeH5AdView newAdView = BaiduNativeH5AdViewManager.getInstance().getBaiduNativeH5AdView(context, (BaiduNativeAdPlacement) object, R.drawable.banner_off);
        if (newAdView.getParent() != null) {
            ((ViewGroup) newAdView.getParent()).removeView(newAdView);
        }
        newAdView.setEventListener(new BaiduNativeH5AdView.BaiduNativeH5EventListner() {
            @Override
            public void onAdClick() {
            }

            @Override
            public void onAdFail(String arg0) {
            }

            @Override
            public void onAdShow() {
            }

            @Override
            public void onAdDataLoaded() {
            }
        });
        // 模板宽高比需要和http://mssp.baidu.com/上代码位的设置相同
        double scale = 1.0 * 7 / 5;
        int width = DataClass.PX_WINDOWS_WIDTH;
        int height = (int) (width / scale);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
        newAdView.setLayoutParams(layoutParams);
        relativeLayout.addView(newAdView);
        RequestParameters requestParameters = new RequestParameters.Builder().setWidth(width).setHeight(height).build();
        newAdView.makeRequest(requestParameters);
        newAdView.recordImpression();

    }

    public interface InitDataListener {
        void onInitDataListener(List<NativeExpressADView> list);

        void onRemoverView(NativeExpressADView adView);
    }

    private InitDataListener initDataListener;

    public void setInitDataListener(InitDataListener initDataListener) {
        this.initDataListener = initDataListener;
    }

}

