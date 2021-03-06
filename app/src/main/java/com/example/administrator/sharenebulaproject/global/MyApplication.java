package com.example.administrator.sharenebulaproject.global;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.example.administrator.sharenebulaproject.di.component.AppComponent;
import com.example.administrator.sharenebulaproject.di.component.DaggerAppComponent;
import com.example.administrator.sharenebulaproject.di.module.AppModule;
import com.example.administrator.sharenebulaproject.di.module.HttpModule;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.rxtools.RxBus;
import com.example.administrator.sharenebulaproject.server.InitializeService;
import com.example.administrator.sharenebulaproject.ui.activity.AdvertisingActivity;
import com.example.administrator.sharenebulaproject.ui.activity.HomeActivity;
import com.example.administrator.sharenebulaproject.ui.activity.LoginActivity;
import com.example.administrator.sharenebulaproject.ui.activity.MainActivity;
import com.example.administrator.sharenebulaproject.ui.activity.WelComeActivity;
import com.example.administrator.sharenebulaproject.ui.activity.about.AnecdotesActivity;
import com.example.administrator.sharenebulaproject.ui.activity.about.DailyActivity;
import com.example.administrator.sharenebulaproject.ui.activity.about.PublicWebActivity;
import com.example.administrator.sharenebulaproject.ui.activity.about.SearchActivity;
import com.example.administrator.sharenebulaproject.utils.LogUtil;
import com.example.administrator.sharenebulaproject.widget.TTAdManagerHolder;
import com.example.administrator.sharenebulaproject.widget.UmPushBuilder;
import com.luoxudong.app.threadpool.ThreadPoolHelp;
import com.tencent.stat.StatConfig;
import com.tencent.stat.StatService;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;


/**
 * Created by Administrator on 2017/10/27.
 */

public class MyApplication extends Application {

    public static AppComponent appComponent;
    public static MyApplication instance;
    public static Set<Activity> allActivities;
    public static ExecutorService executorService;
    public static int flag = -1;

    public static synchronized MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        disableAPIDialog();
        instance = this;
        LogUtil.isDebug = true;
        initThreadTools();
        InitializeService.start(this);
        //友盟初始化
        initUm();
        new UmPushBuilder(this).initPushSetting();
        TTAdManagerHolder.getInstance(this);
        //统计初始化
        initMTA();
    }

    private void initMTA() {
        StatConfig.setDebugEnable(true);
        StatService.registerActivityLifecycleCallbacks(this);
    }

    private void initUm() {
        UMConfigure.setLogEnabled(true);

        UMConfigure.init(this, AppKeyConfig.UMID, "umeng", UMConfigure.DEVICE_TYPE_PHONE, AppKeyConfig.UMPUSHID);

        PlatformConfig.setWeixin(AppKeyConfig.WXID, AppKeyConfig.WXID_ABOUT);

        PlatformConfig.setQQZone(AppKeyConfig.QQID, AppKeyConfig.QQID_ABOUT);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    private void initThreadTools() {
        executorService = ThreadPoolHelp.Builder.fixed(6)
                .name("globalTask")
                .builder();
    }

    /**
     * Android P API 监测禁止弹窗
     */
    private void disableAPIDialog() {
        if (Build.VERSION.SDK_INT < 28) return;
        try {
            Class clazz = Class.forName("android.app.ActivityThread");
            Method currentActivityThread = clazz.getDeclaredMethod("currentActivityThread");
            currentActivityThread.setAccessible(true);
            Object activityThread = currentActivityThread.invoke(null);
            Field mHiddenApiWarningShown = clazz.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static AppComponent getAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(instance))
                .httpModule(new HttpModule())
                .build();
        return appComponent;
    }

    public void addActivity(Activity act) {
        if (act instanceof LoginActivity || act instanceof MainActivity || act instanceof HomeActivity || act instanceof WelComeActivity || act instanceof PublicWebActivity || act instanceof DailyActivity|| act instanceof AdvertisingActivity|| act instanceof SearchActivity|| act instanceof AnecdotesActivity) {
            if (allActivities == null) {
                allActivities = new HashSet<>();
            }
            allActivities.add(act);
        } else {
            if (DataClass.USERID.isEmpty()) {
                act.finish();
                startActivity(new Intent(this, LoginActivity.class));
            }else {
                if (allActivities == null) {
                    allActivities = new HashSet<>();
                }
                allActivities.add(act);
            }
        }

    }

    public void removeActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }

    //自杀
    public static void exitApp() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    act.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

}
