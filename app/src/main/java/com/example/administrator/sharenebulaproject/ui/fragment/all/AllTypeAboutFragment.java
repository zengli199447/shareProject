package com.example.administrator.sharenebulaproject.ui.fragment.all;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baidu.mobads.BaiduNativeAdPlacement;
import com.bumptech.glide.Glide;
import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.base.BaseFragment;
import com.example.administrator.sharenebulaproject.global.AppKeyConfig;
import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.global.MyApplication;
import com.example.administrator.sharenebulaproject.model.bean.HotAllDataBean;
import com.example.administrator.sharenebulaproject.model.bean.RefreshDateBean;
import com.example.administrator.sharenebulaproject.model.bean.SelfBuiltAdvertisingBean;
import com.example.administrator.sharenebulaproject.model.db.entity.LoginUserInfo;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.model.event.EventCode;
import com.example.administrator.sharenebulaproject.rxtools.RxBus;
import com.example.administrator.sharenebulaproject.rxtools.RxUtil;
import com.example.administrator.sharenebulaproject.ui.activity.about.AnecdotesActivity;
import com.example.administrator.sharenebulaproject.ui.activity.about.PublicWebActivity;
import com.example.administrator.sharenebulaproject.ui.adapter.DiversifiedAdapter;
import com.example.administrator.sharenebulaproject.ui.adapter.DiversifiedRecyclerViewAboutAdapter;
import com.example.administrator.sharenebulaproject.ui.adapter.DiversifiedRecyclerViewAdapter;
import com.example.administrator.sharenebulaproject.ui.adapter.DiversifiedTopAdapter;
import com.example.administrator.sharenebulaproject.ui.adapter.TopRecyclerViewAdapter;
import com.example.administrator.sharenebulaproject.ui.dialog.ShowDialog;
import com.example.administrator.sharenebulaproject.utils.LogUtil;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;
import com.example.administrator.sharenebulaproject.widget.AdvertisingBuilder;
import com.example.administrator.sharenebulaproject.widget.CommonSubscriber;
import com.example.administrator.sharenebulaproject.widget.UmShareListenerBuilder;
import com.example.administrator.sharenebulaproject.widget.ViewBuilder;
import com.example.administrator.sharenebulaproject.widget.WebViewBuilder;
import com.google.gson.Gson;
import com.qq.e.ads.nativ.NativeExpressADView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 作者：真理 Created by Administrator on 2018/11/28.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class AllTypeAboutFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, DiversifiedAdapter.onItemCheckListener, NestedScrollView.OnScrollChangeListener, AdvertisingBuilder.InitDataListener, DiversifiedTopAdapter.TopItemListener {

    @BindView(R.id.empty_layout)
    TextView empty_layout;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipe_layout;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.top_recycler_view)
    RecyclerView top_recycler_view;

    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.progress_bar)
    RelativeLayout progress_bar;
    @BindView(R.id.footer_layout)
    RelativeLayout footer_layout;

    @BindView(R.id.placeholder)
    View placeholder;
    @BindView(R.id.web_layout)
    FrameLayout web_layout;
    @BindView(R.id.web_view)
    WebView web_view;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    private String title;
    private String typeId;
    private String url;
    int pageNumber = 1;
    int newSize;
    boolean pushStatus;
    //目标项是否在最后一个可见项之后
    private boolean mShouldScroll;
    //记录目标项位置
    private int mToPosition;

    private List<Object> AllSortClassList = new ArrayList<>();
    private List<HotAllDataBean.Result.topNews> TopSortClassList = new ArrayList<>();
    private List<RefreshDateBean> refreshStatusClassList = new ArrayList<>();

    private DiversifiedAdapter diversifiedRecyclerViewAdapter;
    private UmShareListenerBuilder umShareListenerBuilder;
    private ShowDialog instance;
    private DiversifiedTopAdapter topRecyclerViewAdapter;
    private WebViewBuilder webViewBuilder;
    private AdvertisingBuilder advertisingBuilder;

    private int pageNumberMore = 1;

    private List<NativeExpressADView> nativeExpressADViewList = new ArrayList<>();
    private String token = "";

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_all_type;
    }

    @Override
    protected void initClass() {
        recycler_view.setLayoutManager(ViewBuilder.getFullyLinearLayoutManager(getActivity()));
        top_recycler_view.setLayoutManager(ViewBuilder.getFullyLinearLayoutManager(getActivity()));
        diversifiedRecyclerViewAdapter = new DiversifiedAdapter(getActivity(), AllSortClassList, nativeExpressADViewList);
        topRecyclerViewAdapter = new DiversifiedTopAdapter(getActivity(), TopSortClassList);

        recycler_view.setAdapter(diversifiedRecyclerViewAdapter);
        top_recycler_view.setAdapter(topRecyclerViewAdapter);

        umShareListenerBuilder = new UmShareListenerBuilder(getActivity(), toastUtil);
        instance = ShowDialog.getInstance();

        advertisingBuilder = new AdvertisingBuilder(getActivity());

        webViewBuilder = new WebViewBuilder(web_view, null, toastUtil, getActivity(), null);
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        title = bundle != null ? bundle.getString("title") : "";
        typeId = bundle != null ? bundle.getString("typeId") : "";
        url = bundle != null ? bundle.getString("url") : "";
        LoginUserInfo loginUserInfo = dataManager.queryLoginUserInfo("admin");
        if (loginUserInfo != null) {
            token = loginUserInfo.getToken();
        }
        ViewBuilder.ProgressStyleChange(swipe_layout);
        swipe_layout.setRefreshing(true);
        if (DataClass.CNBYLOCATION != null)
            if (!DataClass.CNBYLOCATION.equals(title)) {
                web_layout.setVisibility(View.GONE);
                initAdData();

            } else {
                webViewBuilder.initWebView();
                web_layout.setVisibility(View.VISIBLE);
                webViewBuilder.loadWebView(url, true);
            }
    }

    @Override
    protected void initView() {
        empty_layout.setText(title);
    }

    @Override
    protected void initListener() {
        swipe_layout.setOnRefreshListener(this);
        scrollView.setOnScrollChangeListener(this);
        topRecyclerViewAdapter.setOnTopItemkListener(this);
        diversifiedRecyclerViewAdapter.setOnItemCheckListener(this);
        advertisingBuilder.setInitDataListener(this);
    }

    @Override
    public void onRefresh() {
        pushStatus = true;
        initAdData();
//        initNetDataWork();
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onItemClick(int i) {
        if (AllSortClassList.get(i) instanceof HotAllDataBean.Result.newsBean) {
            HotAllDataBean.Result.newsBean newsBean = (HotAllDataBean.Result.newsBean) AllSortClassList.get(i);
            Intent intent = null;
            switch (newsBean.getType()) {
                case 0:
                    intent = new Intent(getActivity(), AnecdotesActivity.class);
                    break;
                case 1:
                    intent = new Intent(getActivity(), PublicWebActivity.class);
                    break;
            }
            intent.setFlags(EventCode.GENERAL_WEB);
            intent.putExtra("value", String.valueOf(newsBean.getNewsid()));
            intent.putExtra("shareTitle", newsBean.getTitle());
            intent.putExtra("shareImgUrl", new StringBuffer().append(DataClass.FileUrl).append(newsBean.getListimg().split(",")[0]).toString());
            intent.putExtra("shareNewsUrl", new StringBuffer().append(DataClass.DAILY_URL).append(newsBean.getNewsid()).append(DataClass.USERID_SHARE).toString());
            intent.putExtra("total", String.valueOf(newsBean.getStarbean()));
            intent.putExtra("ifcanmoney", String.valueOf(newsBean.getIfcanmoney()));
            startActivity(intent);
        }
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onTopItemClick(int i) {
        HotAllDataBean.Result.topNews topNews = (HotAllDataBean.Result.topNews) TopSortClassList.get(i);
        Intent intent = new Intent(getActivity(), PublicWebActivity.class);
        intent.setFlags(EventCode.GENERAL_WEB);
        intent.putExtra("value", String.valueOf(topNews.getNewsid()));
        intent.putExtra("shareTitle", topNews.getTitle());
        intent.putExtra("shareImgUrl", new StringBuffer().append(DataClass.FileUrl).append(topNews.getListimg().split(",")[0]).toString());
        intent.putExtra("shareNewsUrl", new StringBuffer().append(DataClass.DAILY_URL).append(topNews.getNewsid()).append(DataClass.USERID_SHARE).toString());
        intent.putExtra("total", String.valueOf(topNews.getStarbean()));
        intent.putExtra("ifcanmoney", String.valueOf(topNews.getIfcanmoney()));
        startActivity(intent);
    }

    @Override
    public void onRefreshItemClick() {
        swipe_layout.setRefreshing(true);
        pushStatus = true;
        initAdData();
//        initNetDataWork();
        scrollView.scrollTo(0, 0);
        scrollView.fullScroll(ScrollView.FOCUS_UP);//滑到顶部
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
            pageNumberMore = pageNumberMore + 1;
            initAdData();
//            initNetDataWork();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            LogUtil.e(TAG, "title :" + title);
            LogUtil.e(TAG, "typeId :" + typeId);
        }
    }

    private void clearUserInfo(int status) {
        //需要清空本地登陆信息，以游客方式登陆
        DataClass.USERID = "";
        DataClass.SELECT = new StringBuffer().append(DataClass.URL_).append("pf_wx.php?act=oneday&do=display&userid_share=").toString();
        if (DataClass.LOGINTYPE != 0)
            umShareListenerBuilder.deleteOauth(DataClass.LOGINTYPE);
        dataManager.deleteLoginUserInfo("admin");
        RxBus.getDefault().post(new CommonEvent(EventCode.LOGIN_OUT));
        switch (status) {
            case 0:
                instance.showLoginStatusPromptDialog(getActivity(), getString(R.string.illegal_user));
                break;
            case 2:
                instance.showLoginStatusPromptDialog(getActivity(), getString(R.string.other_equipment_login));
                break;
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case 0:
                    refreshView();
                    break;
            }
        }
    };

    private void refreshView() {
        diversifiedRecyclerViewAdapter.refreshViewStatus(newSize, new Date().getTime());
        if (pageNumber != 1) {
            diversifiedRecyclerViewAdapter.notifyDataSetChanged();
        }
        topRecyclerViewAdapter.notifyDataSetChanged();
//        diversifiedRecyclerViewAdapter.notifyItemRangeChanged(0, newSize);
        if (AllSortClassList.size() == 0) {
            footer_layout.setVisibility(View.GONE);
        } else {
            footer_layout.setVisibility(View.VISIBLE);
        }
        if (newSize == 0) {
            progress_bar.setVisibility(View.GONE);
        } else {
            progress_bar.setVisibility(View.VISIBLE);
        }

    }

    private void initAdData() {
        advertisingBuilder.initNativeExpressAD(pageNumber);

    }

    @Override
    public void onInitDataListener(List<NativeExpressADView> list) {
        nativeExpressADViewList.addAll(list);
        initNetDataWork();
        LogUtil.e(TAG, "nativeExpressADViewList.size() : " + nativeExpressADViewList.size());
    }

    @Override
    public void onRemoverView(NativeExpressADView adView) {
        diversifiedRecyclerViewAdapter.removeADView(adView);
    }

    //获取首页数据
    private void initNetDataWork() {
        if (pushStatus) {
            pageNumber = 1;
        } else {
            pageNumber = pageNumberMore;
        }
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.HOMEPAGE_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("versionvalue", SystemUtil.packageCode(getActivity()));
        linkedHashMap.put("systemtype", "1");
        linkedHashMap.put("pagenum", pageNumber);
        linkedHashMap.put("ordertype", 1);
        linkedHashMap.put("city", DataClass.CNBYLOCATION);
        linkedHashMap.put("catid", typeId);
        linkedHashMap.put("token", token);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.HotPageData(map)
                .compose(RxUtil.<HotAllDataBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<HotAllDataBean>(toastUtil) {
                    @Override
                    public void onNext(HotAllDataBean hotAllDataBean) {
                        swipe_layout.setRefreshing(false);
                        if (hotAllDataBean.getStatus() == 1) {
                            final HotAllDataBean.Result result = hotAllDataBean.getResult();
                            if (result.getAllowlogin() != 1) {
                                clearUserInfo(result.getAllowlogin());
                            }
                            newSize = result.getNews().size();

                            if (pushStatus) {
                                RxBus.getDefault().post(new CommonEvent(EventCode.REFRESH_NEWS, newSize));
                                pushStatus = false;
                            }

                            if (pageNumber == 1) {
                                if (newSize == 0) {
                                    empty_layout.setVisibility(View.VISIBLE);
                                } else {
                                    empty_layout.setVisibility(View.GONE);

                                    //置顶数据刷新
                                    TopSortClassList.clear();
                                    TopSortClassList.addAll(result.getTopnews());

                                    //上拉刷新位置状态变化
                                    if (refreshStatusClassList.size() > 0) {
                                        for (int i = 0; i < refreshStatusClassList.size(); i++) {
                                            refreshStatusClassList.get(i).setStatus(false);
                                        }
                                    }
                                    for (int i = 0; i < newSize + 1; i++) {
                                        if (i == newSize) {
                                            if (AllSortClassList.size() > newSize) {
                                                RefreshDateBean refreshDateBean = new RefreshDateBean(true, "");
                                                refreshStatusClassList.add(0, refreshDateBean);
                                                diversifiedRecyclerViewAdapter.addData(i, refreshDateBean);
                                            }
                                        } else {
                                            diversifiedRecyclerViewAdapter.addData(i, result.getNews().get(i));
                                        }
                                    }
                                    //整体刷新
                                    refreshView();
                                }
                            } else {
                                //添加下拉加载更多的数据
                                AllSortClassList.addAll(result.getNews());
                                //整体刷新
                                refreshView();
                            }
                            // 应用更新状态
                            if (!result.getNewversion().isEmpty()) {
                                DataClass.APK_URL = result.getRootPath();
                                if ("0".equals(result.getIfmust())) {
                                    instance.showPromptDialog(getActivity(), result.getNewversion(), false);
                                } else {
                                    instance.showPromptDialog(getActivity(), result.getNewversion(), true);
                                }
                            }
                        } else {
                            toastUtil.showToast(hotAllDataBean.getMessage());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        swipe_layout.setRefreshing(false);
                        super.onError(e);
                    }
                }));
    }

}
