package com.example.administrator.sharenebulaproject.ui.activity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.base.BaseActivity;
import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.global.MyApplication;
import com.example.administrator.sharenebulaproject.model.bean.LoginInfoBean;
import com.example.administrator.sharenebulaproject.model.bean.TheNewTypeNetBean;
import com.example.administrator.sharenebulaproject.model.bean.UserInfoBean;
import com.example.administrator.sharenebulaproject.model.db.entity.AppDBInfo;
import com.example.administrator.sharenebulaproject.model.db.entity.LoginUserInfo;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.model.event.EventCode;
import com.example.administrator.sharenebulaproject.rxtools.RxBus;
import com.example.administrator.sharenebulaproject.rxtools.RxUtil;
import com.example.administrator.sharenebulaproject.server.DownloadService;
import com.example.administrator.sharenebulaproject.ui.dialog.ProgressDialog;
import com.example.administrator.sharenebulaproject.ui.dialog.ShowDialog;
import com.example.administrator.sharenebulaproject.ui.fragment.HomePageFragment;
import com.example.administrator.sharenebulaproject.ui.fragment.HotPageFragment;
import com.example.administrator.sharenebulaproject.ui.fragment.IncomeFragment;
import com.example.administrator.sharenebulaproject.ui.fragment.MineFragment;
import com.example.administrator.sharenebulaproject.ui.fragment.ToDayPageFragment;
import com.example.administrator.sharenebulaproject.ui.fragment.TopFragment;
import com.example.administrator.sharenebulaproject.utils.LocationUtils;
import com.example.administrator.sharenebulaproject.utils.LogUtil;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;
import com.example.administrator.sharenebulaproject.widget.CommonSubscriber;
import com.example.administrator.sharenebulaproject.widget.Constants;
import com.example.administrator.sharenebulaproject.widget.UmPushBuilder;
import com.example.administrator.sharenebulaproject.widget.UmShareListenerBuilder;
import com.google.gson.Gson;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Administrator on 2018/8/11.
 */

public class HomeActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.group_view)
    RadioGroup group_view;
    @BindView(R.id.layout_home)
    RelativeLayout layout_home;
    @BindView(R.id.guide_layout)
    RelativeLayout guide_layout;
    private int showFragment = Constants.TYPE_HOTPAGE;
    private int hideFragment = Constants.TYPE_HOTPAGE;
    //    private HomePageFragment homePageFragment;
    private HotPageFragment hotPageFragment;
    private IncomeFragment incomeFragment;
    private MineFragment mineFragment;
    private TopFragment topFragment;
    private long exitTime = 0;
    private String[] titleName;
    private ProgressDialog progressDialog;
    private UmShareListenerBuilder umShareListenerBuilder;
    private boolean switchStatus;
    private DownloadService.DownloadBinder mDownloadBinder;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
//            hotPageFragment.getData();
            layout_home.setVisibility(View.VISIBLE);
        }
    };
    private ToDayPageFragment toDayPageFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            showHideFragment(getTargetFragment(showFragment), getTargetFragment(hideFragment));
            hideFragment = showFragment;
        }
        Intent intent = new Intent(this, DownloadService.class);
        startService(intent);
        bindService(intent, mConnection, BIND_AUTO_CREATE);//绑定服务
    }

    private SupportFragment getTargetFragment(int item) {
        switch (item) {
            case Constants.TYPE_HOTPAGE:
                return toDayPageFragment;
            case Constants.TYPE_TOP:
                return topFragment;
            case Constants.TYPE_INCOME:
                return incomeFragment;
            case Constants.TYPE_MINE:
                return mineFragment;
        }
        return toDayPageFragment;
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.REFRESH_USERINFO:
                initNetDataWork();
                break;
            case EventCode.LOGIN_OUT:
                switchStatus = true;
                group_view.check(R.id.hotpage);
                showFragment = Constants.TYPE_HOTPAGE;
                showHideFragment(getTargetFragment(showFragment), getTargetFragment(hideFragment));
                hideFragment = showFragment;
                switchStatus = false;
                break;
            case EventCode.DOWNLOAD_APK:
                if (mDownloadBinder != null) {
                    toastUtil.showToast("后台更新启动");
                    long downloadId = mDownloadBinder.startDownload(DataClass.APK_URL);
                }
                break;
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void initClass() {
        MyApplication.executorService.submit(new Runnable() {
            @Override
            public void run() {
                LocationUtils.getCNBylocation(HomeActivity.this);
            }
        });
        progressDialog = ShowDialog.getInstance().showProgressStatus(this, getString(R.string.progress));
        umShareListenerBuilder = new UmShareListenerBuilder(this, toastUtil);
    }

    @Override
    protected void initData() {
        titleName = new String[]{getString(R.string.home_page_name), getString(R.string.top), getString(R.string.income), getString(R.string.mine)};
        if (!DataClass.USERID.isEmpty())
            initNetDataWork();
        SystemUtil.initWindowsManagerWidth(this);
        try {
            for (int i = 0; i < DataClass.ALL_TYPE_TITLE.size(); i++) {
                TheNewTypeNetBean.ResultBean.AllBean allBean = DataClass.ALL_TYPE_TITLE.get(i);
                if (getString(R.string.local).equals(allBean.getCatename())) {
                    allBean.setCatename(DataClass.CNBYLOCATION);
                }
            }
            for (int i = 0; i < DataClass.TYPE_TITLE.size(); i++) {
                Object object = DataClass.TYPE_TITLE.get(i);
                if (object instanceof TheNewTypeNetBean.ResultBean.AllBean) {
                    if (getString(R.string.local).equals(((TheNewTypeNetBean.ResultBean.AllBean) object).getCatename())) {
                        ((TheNewTypeNetBean.ResultBean.AllBean) object).setCatename(DataClass.CNBYLOCATION);
                    }
                } else {
                    if (getString(R.string.local).equals(((TheNewTypeNetBean.ResultBean.SelectBean) object).getCatename())) {
                        ((TheNewTypeNetBean.ResultBean.SelectBean) object).setCatename(DataClass.CNBYLOCATION);
                    }
                }
            }
        } catch (Exception e) {
            toastUtil.showToast("发生错误，无法获取当前坐标");
        }
    }

    @Override
    protected void initView() {
//        hotPageFragment = new HotPageFragment();
        toDayPageFragment = new ToDayPageFragment();
//        homePageFragment = new HomePageFragment();
        incomeFragment = new IncomeFragment();
        mineFragment = new MineFragment();
        topFragment = new TopFragment();
        loadMultipleRootFragment(R.id.layout_fl, 0, toDayPageFragment, topFragment, incomeFragment, mineFragment);
        MyApplication.executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    LogUtil.e(TAG, "dataManager.loadAppDBInfoDao().size() : " + dataManager.loadAppDBInfoDao().size());
                    if (dataManager.loadAppDBInfoDao().size() > 0) {
                        handler.sendEmptyMessageDelayed(0, 1500);
                    } else {
                        guide_layout.setVisibility(View.VISIBLE);
                        handler.sendEmptyMessage(0);
                        dataManager.insertAppDBInfoDao(new AppDBInfo("", true));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void initListener() {
        group_view.setOnCheckedChangeListener(this);
    }

    @OnClick({R.id.guide_layout})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.guide_layout:
                guide_layout.setVisibility(View.GONE);
                break;
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        int position = 0;
        switch (i) {
            case R.id.hotpage:
                showFragment = Constants.TYPE_HOTPAGE;
                position = 0;
                break;
            case R.id.top:
                showFragment = Constants.TYPE_TOP;
                position = 1;
                break;
            case R.id.income:
                showFragment = Constants.TYPE_INCOME;
                position = 2;
                break;
            case R.id.mine:
                showFragment = Constants.TYPE_MINE;
                position = 3;
                break;
        }
        if (!switchStatus)
            if (DataClass.USERID.isEmpty() && position > 1) {
                startActivity(new Intent(this, LoginActivity.class));
                group_view.check(R.id.hotpage);
                showFragment = Constants.TYPE_HOTPAGE;
//            title_name.setText(titleName[0]);
            } else {
                group_view.check(i);
//            title_name.setText(titleName[position]);
                showHideFragment(getTargetFragment(showFragment), getTargetFragment(hideFragment));
                hideFragment = showFragment;
            }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            toastUtil.showToast(getString(R.string.finish));
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mDownloadBinder = (DownloadService.DownloadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mDownloadBinder = null;
        }
    };

    //获取当前个人信息
    private void initNetDataWork() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.USER_DETAIL_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.fetchLogin(map)
                .compose(RxUtil.<LoginInfoBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<LoginInfoBean>(toastUtil) {
                    @Override
                    public void onNext(LoginInfoBean loginInfoBean) {
                        Log.e(TAG, "loginInfoBean.getStatus() : " + loginInfoBean.getStatus());
                        if (loginInfoBean.getResult() != null) {
                            if (loginInfoBean.getStatus() == 1) {
                                DataClass.initUserInfo(loginInfoBean.getResult());
                                RxBus.getDefault().post(new CommonEvent(EventCode.REFRESH_BRANCH));
                            } else {
                                toastUtil.showToast(loginInfoBean.getMessage());
                            }
                        }
                    }
                }));
    }

}
