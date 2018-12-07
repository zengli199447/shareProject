package com.example.administrator.sharenebulaproject.widget;

import android.content.Context;

import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdManagerFactory;
import com.example.administrator.sharenebulaproject.global.AppKeyConfig;
import com.example.administrator.sharenebulaproject.widget.csj.AppDownloadStatusListener;

/**
 * 作者：真理 Created by Administrator on 2018/12/4.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class TTAdManagerHolder {

    private static boolean sInit;

    public static TTAdManager getInstance(Context context) {
        TTAdManager ttAdManager = TTAdManagerFactory.getInstance(context);
        if (!sInit) {
            synchronized (TTAdManagerHolder.class) {
                if (!sInit) {
                    doInit(ttAdManager,context);
                    sInit = true;
                }
            }
        }
        return ttAdManager;
    }
    //step1:接入网盟广告sdk的初始化操作，详情见接入文档和穿山甲平台说明
    private static void doInit(TTAdManager ttAdManager,Context context) {
        ttAdManager.setAppId(AppKeyConfig.ADVERTISING_CSJID)
                .isUseTextureView(true) //使用TextureView控件播放视频,默认为SurfaceView,当有SurfaceView冲突的场景，可以使用TextureView
                .setName("APP测试媒体").setTitleBarTheme(TTAdConstant.TITLE_BAR_THEME_DARK)
                .setAllowShowNotifiFromSDK(true) //是否允许sdk展示通知栏提示
                .setAllowLandingPageShowWhenScreenLock(true) //是否在锁屏场景支持展示广告落地页
                .openDebugMode() //测试阶段打开，可以通过日志排查问题，上线时去除该调用
                .setGlobalAppDownloadListener(new AppDownloadStatusListener(context)) //下载任务的全局监听
                .setDirectDownloadNetworkType(TTAdConstant.NETWORK_STATE_WIFI, TTAdConstant.NETWORK_STATE_3G); //允许直接下载的网络状态集合
    }

}
