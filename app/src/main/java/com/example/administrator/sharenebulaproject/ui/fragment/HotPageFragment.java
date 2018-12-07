package com.example.administrator.sharenebulaproject.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.base.BaseFragment;
import com.example.administrator.sharenebulaproject.base.EndlessRecyclerOnScrollListener;
import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.global.MyApplication;
import com.example.administrator.sharenebulaproject.model.bean.DailyNetBean;
import com.example.administrator.sharenebulaproject.model.bean.HotAllDataBean;
import com.example.administrator.sharenebulaproject.model.bean.UpLoadStatusNetBean;
import com.example.administrator.sharenebulaproject.model.bean.UserInfoBean;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.model.event.EventCode;
import com.example.administrator.sharenebulaproject.rxtools.RxBus;
import com.example.administrator.sharenebulaproject.rxtools.RxUtil;
import com.example.administrator.sharenebulaproject.ui.activity.about.DailyActivity;
import com.example.administrator.sharenebulaproject.ui.activity.about.GeneralVersionActivity;
import com.example.administrator.sharenebulaproject.ui.activity.about.PublicWebActivity;
import com.example.administrator.sharenebulaproject.ui.activity.certification.GeneralActivity;
import com.example.administrator.sharenebulaproject.ui.adapter.DailyRecyclerViewAdapter;
import com.example.administrator.sharenebulaproject.ui.adapter.HotRecyclerViewAdapter;
import com.example.administrator.sharenebulaproject.ui.dialog.ProgressDialog;
import com.example.administrator.sharenebulaproject.ui.dialog.ShowDialog;
import com.example.administrator.sharenebulaproject.ui.view.CustomPopupWindow;
import com.example.administrator.sharenebulaproject.ui.view.ImageNetSlideshow;
import com.example.administrator.sharenebulaproject.utils.LocationUtils;
import com.example.administrator.sharenebulaproject.utils.LogUtil;
import com.example.administrator.sharenebulaproject.utils.SortingUtils;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;
import com.example.administrator.sharenebulaproject.widget.CommonSubscriber;
import com.example.administrator.sharenebulaproject.widget.FullyLinearLayoutManager;
import com.example.administrator.sharenebulaproject.widget.UmShareListenerBuilder;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * Created by Administrator on 2018/8/11.
 * 榜单 需求变动，当前页面不使用
 */

public class HotPageFragment extends BaseFragment implements View.OnClickListener, ImageNetSlideshow.OnItemClickListener, HotRecyclerViewAdapter.onCheckListener, RadioGroup.OnCheckedChangeListener, CustomPopupWindow.OnItemClickListener, PopupWindow.OnDismissListener, AppBarLayout.OnOffsetChangedListener, SwipeRefreshLayout.OnRefreshListener, HotRecyclerViewAdapter.onItemCheckListener, UmShareListenerBuilder.onShareListener {

    @BindView(R.id.hotpage_slide_show)
    ImageNetSlideshow slide_show;
    @BindView(R.id.hot_recyclerview)
    RecyclerView hot_list;
    @BindView(R.id.group_indicator_view)
    RadioGroup group_indicator_view;
    @BindView(R.id.registered_all_people)
    TextView registered_all_people;
    @BindView(R.id.online_all_people)
    TextView online_all_people;
    @BindView(R.id.select_today)
    TextView select_today;
    @BindView(R.id.select_promote)
    TextView select_promote;
    @BindView(R.id.select_awards_show)
    TextView select_awards_show;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipe_layout;
    @BindView(R.id.bar_layout)
    AppBarLayout bar_layout;
    @BindView(R.id.title_silence_view)
    View title_silence_view;
    @BindView(R.id.layout_silence_blank)
    LinearLayout layout_silence_blank;

    private List<HotAllDataBean.Result.newsBean> AllSortClassList = new ArrayList<>();
    private List<HotAllDataBean.Result.newsBean> news;
    private DateFormat format;
    private CustomPopupWindow customPopupWindow;
    private LinearLayoutManager fullyLinearLayoutManager;
    private HotRecyclerViewAdapter hotRecyclerViewAdapter;
    private int pageNumber = 1;
    private int orderType = 1;
    private int oldVerticalOffset = 0;
    private int newIndex = 0;
    private boolean refreshStatus;
    private boolean shareRefresh;
    private ProgressDialog progressDialog;
    int[] location = new int[2];
    private int newsid;
    private String shareImgUrl = "";
    private String shareNewsUrl = "";
    private String shareTitle = "";
    private UmShareListenerBuilder umShareListenerBuilder;
    private Bitmap bitmap;
    private int selectRefresh = 0;
    private List<HotAllDataBean.Result.BannerBean> banner;
    private String cnBylocation = "";
    private ShowDialog instance;


    @Override
    protected int getLayoutId() {
        return R.layout.layout_cooding;
    }

    @Override
    protected void initClass() {
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        customPopupWindow = new CustomPopupWindow(getActivity());
        instance = ShowDialog.getInstance();
        progressDialog = instance.showProgressStatus(getActivity(), getString(R.string.progress));
        umShareListenerBuilder = new UmShareListenerBuilder(getActivity(), toastUtil);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        hotRecyclerViewAdapter = new HotRecyclerViewAdapter(getActivity(), AllSortClassList);

        hot_list.setItemViewCacheSize(10);
        hot_list.setDrawingCacheEnabled(true);
        hot_list.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        hot_list.setAdapter(hotRecyclerViewAdapter);
    }

    @Override
    protected void initData() {
        try {
            cnBylocation = LocationUtils.getCNBylocation(getActivity());
        } catch (Exception e) {
            toastUtil.showToast("发生错误，无法获取当前坐标");
        }
    }

    public void getData() {
        progressDialog.show();
        initNetDataWork(pageNumber, orderType);
    }

    @Override
    protected void initView() {
        //设置样式刷新显示的位置
        swipe_layout.setProgressViewOffset(true, -20, 100);
        swipe_layout.setColorSchemeResources(R.color.blue);
        fullyLinearLayoutManager = new LinearLayoutManager(getActivity());
        fullyLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        hot_list.setLayoutManager(fullyLinearLayoutManager);
        slide_show.setOverStatus(false);
        slide_show.setDotSpace(24);
        slide_show.setDotSize(12);
        slide_show.setDelay(3000);
    }

    @Override
    protected void initListener() {
        slide_show.setOnItemClickListener(this);
        group_indicator_view.setOnCheckedChangeListener(this);
        select_today.setOnClickListener(this);
        select_promote.setOnClickListener(this);
        select_awards_show.setOnClickListener(this);
        customPopupWindow.setOnItemClickListener(this);
        customPopupWindow.setOnDismissListener(this);
        bar_layout.addOnOffsetChangedListener(this);
        swipe_layout.setOnRefreshListener(this);
        hot_list.addOnScrollListener(scrollListener);
        hot_list.setFocusableInTouchMode(false);
        umShareListenerBuilder.setOnShareListener(this);
        hotRecyclerViewAdapter.setOnItemCheckListener(this);

    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.USERID_REFRESH:
                shareNewsUrl = shareNewsUrl + commonevent.getTemp_str();
                DataClass.USERID_SHARE = "&userid_share=" + commonevent.getTemp_str();
                break;
        }
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        swipe_layout.setEnabled(verticalOffset == 0 ? true : false);
        layout_silence_blank.getLocationOnScreen(location);
        if (location[1] != 0) {
            title_silence_view.setBackgroundColor(getResources().getColor(R.color.gray_light));
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else {
            title_silence_view.setBackgroundColor(getResources().getColor(R.color.white));
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    @Override
    public void onRefresh() {
        pageNumber = 1;
        initNetDataWork(pageNumber, orderType);
    }

    private RecyclerView.OnScrollListener scrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadMore() {
            if (hotRecyclerViewAdapter != null) {
                hotRecyclerViewAdapter.setLoadState(hotRecyclerViewAdapter.LOADING);
                if (AllSortClassList.size() > 2) {
                    pageNumber = pageNumber + 1;
                    LogUtil.e(TAG, "pageNumber : " + pageNumber);
                    initNetDataWork(pageNumber, orderType);
                } else {
                    // 显示加载到底的提示
                    hotRecyclerViewAdapter.setLoadState(hotRecyclerViewAdapter.LOADING_END);
                }
                if (newIndex == 0) {
                    hotRecyclerViewAdapter.setLoadState(hotRecyclerViewAdapter.LOADING_END);
                }
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView) {
        }
    };

    @SuppressLint("WrongConstant")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.select_today:
                startActivity(new Intent(getActivity(), DailyActivity.class));
                break;
            case R.id.select_promote:
                Intent promoteIntent = new Intent(getActivity(), GeneralVersionActivity.class);
                promoteIntent.setFlags(1);
                getActivity().startActivity(promoteIntent);
                break;
            case R.id.select_awards_show:
                Intent awardsShow = new Intent(getActivity(), PublicWebActivity.class);
                awardsShow.setFlags(EventCode.TEXT_WEB);
                awardsShow.putExtra("value", String.valueOf(DataClass.AWARDS_SHOW));
                startActivity(awardsShow);
                break;
        }
    }

    //banner 点击
    @SuppressLint("WrongConstant")
    @Override
    public void onItemClick(View view, int position) {
        Intent instructionsIntent = new Intent(getActivity(), PublicWebActivity.class);
        instructionsIntent.setFlags(EventCode.CONTENT_WEB);
        HotAllDataBean.Result.BannerBean bannerBean = banner.get(position);
        instructionsIntent.putExtra("value", bannerBean.getContent());
        instructionsIntent.putExtra("shareTitle", bannerBean.getTitle());
        instructionsIntent.putExtra("shareImgUrl", new StringBuffer().append(DataClass.FileUrl).append(bannerBean.getPhoto()).toString());
        instructionsIntent.putExtra("shareNewsUrl", new StringBuffer().append(DataClass.DAILY_URL).append(bannerBean.getTextInfoid()).append(DataClass.USERID_SHARE).toString());
        startActivity(instructionsIntent);
    }

    //排序类型
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        progressDialog.show();
        pageNumber = 1;
        switch (i) {
            case R.id.comprehensive:
                orderType = 1;
                initNetDataWork(pageNumber, orderType);
                break;
            case R.id.news:
                orderType = 2;
                initNetDataWork(pageNumber, orderType);
                break;
            case R.id.bonus_pools:
                orderType = 3;
                initNetDataWork(pageNumber, orderType);
                break;
            case R.id.reading:
                orderType = 4;
                initNetDataWork(pageNumber, orderType);
                break;
            case R.id.shares:
                orderType = 5;
                initNetDataWork(pageNumber, orderType);
                break;
        }
        group_indicator_view.check(i);
    }

    //分页新闻分享点击
    @Override
    public void onClick(int i) {
        selectRefresh = i;
        initShareData();
    }

    //新闻详情查看点击
    @SuppressLint("WrongConstant")
    @Override
    public void onItemClick(int i) {
        HotAllDataBean.Result.newsBean newsBean = AllSortClassList.get(i);
        Intent dailyIntent = new Intent(getActivity(), PublicWebActivity.class);
        dailyIntent.setFlags(EventCode.GENERAL_WEB);
        dailyIntent.putExtra("value", String.valueOf(newsBean.getNewsid()));
        dailyIntent.putExtra("shareTitle", newsBean.getTitle());
        dailyIntent.putExtra("shareImgUrl", new StringBuffer().append(DataClass.FileUrl).append(newsBean.getListimg().split(",")[0]).toString());
        dailyIntent.putExtra("shareNewsUrl", new StringBuffer().append(DataClass.DAILY_URL).append(newsBean.getNewsid()).append(DataClass.USERID_SHARE).toString());
        dailyIntent.putExtra("total", String.valueOf(newsBean.getStarbean()));
        dailyIntent.putExtra("ifcanmoney", String.valueOf(newsBean.getIfcanmoney()));
        startActivity(dailyIntent);
    }

    @Override
    public void refreshItemClick() {

    }

    //分享
    @Override
    public void setOnItemClick(View v) {
        switch (v.getId()) {
            case R.id.share_wechat:
                umShareListenerBuilder.initUmUrlShare(1, bitmap, shareImgUrl, new StringBuffer().append(shareNewsUrl).append("&type=1").toString(), shareTitle, shareTitle);
                break;
            case R.id.wechat_friends:
                umShareListenerBuilder.initUmUrlShare(2, bitmap, shareImgUrl, new StringBuffer().append(shareNewsUrl).append("&type=1").toString(), shareTitle, shareTitle);
                break;
            case R.id.qq:
                umShareListenerBuilder.initUmUrlShare(0, bitmap, shareImgUrl, new StringBuffer().append(shareNewsUrl).append("&type=2").toString(), shareTitle, shareTitle);
                break;
        }
        customPopupWindow.dismiss();
    }

    @Override
    public void successful() {
        initShareDataWork();
    }

    private void initShareData() {
        HotAllDataBean.Result.newsBean newsBean = AllSortClassList.get(selectRefresh);
        newsid = newsBean.getNewsid();
        shareTitle = newsBean.getTitle();
        shareImgUrl = new StringBuffer().append(DataClass.FileUrl).append(newsBean.getListimg().split(",")[0]).toString();
        shareNewsUrl = new StringBuffer().append(DataClass.DAILY_URL).append(newsBean.getNewsid()).append(DataClass.USERID_SHARE).toString();
        if (Integer.valueOf(newsBean.getStarbean()) > 0) {
            customPopupWindow.showAtLocation(hot_list, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 100);
            SystemUtil.windowToDark(getActivity());
        }
    }

    @Override
    public void onDismiss() {
        SystemUtil.windowToLight(getActivity());
    }

    //数据更新
    private void setHotPageView() {
        if (shareRefresh) {
            hotRecyclerViewAdapter.notifyItemChanged(selectRefresh);
        } else {
            hotRecyclerViewAdapter.notifyDataSetChanged();
        }
        shareRefresh = false;
    }

    //banner 初始化
    private void initBannerView() {
        slide_show.ClearList();
        for (int i = 0; i < banner.size(); i++) {
            slide_show.addImageUrl(new StringBuffer().append(DataClass.FileUrl).append(banner.get(i).getPhoto()).toString());
        }
        slide_show.commit();
        refreshStatus = true;
    }

    //获取首页数据
    private void initNetDataWork(final int pageNumber, final int orderType) {
        LogUtil.e(TAG, "pageNumber : " + pageNumber);
        LogUtil.e(TAG, "orderType : " + orderType);
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.HOMEPAGE_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("versionvalue", SystemUtil.packageCode(getActivity()));
        linkedHashMap.put("systemtype", "1");
        linkedHashMap.put("pagenum", pageNumber);
        linkedHashMap.put("ordertype", orderType);
        linkedHashMap.put("city", cnBylocation);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.HotPageData(map)
                .compose(RxUtil.<HotAllDataBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<HotAllDataBean>(toastUtil) {
                    @Override
                    public void onNext(HotAllDataBean hotAllDataBean) {
                        progressDialog.dismiss();
                        HotAllDataBean.Result result = hotAllDataBean.getResult();
                        if (hotAllDataBean.getStatus() == 1) {
                            if (result != null) {
                                if (result.getAllowlogin() == 0) {
                                    clearUserInfo();
                                }
                                if (!result.getNewversion().isEmpty() && !refreshStatus) {
                                    DataClass.APK_URL = result.getRootPath();
                                    if ("0".equals(result.getIfmust())) {
                                        instance.showPromptDialog(getActivity(), result.getNewversion(), false);
                                    } else {
                                        instance.showPromptDialog(getActivity(), result.getNewversion(), true);
                                    }
                                }
                                if (pageNumber == 1 && orderType == 1) {
                                    DataClass.FileUrl = result.getRootPath();
                                    registered_all_people.setText(new StringBuffer().append(getString(R.string.registered_all_people)).append(result.getTotaluser_regist()));
                                    online_all_people.setText(new StringBuffer().append(getString(R.string.online_all_people)).append(result.getTotaluser_online()));
                                }
                                news = result.getNews();
                                newIndex = news.size();
                                if (result.getBanner().size() > 0)
                                    banner = result.getBanner();
                                if (pageNumber == 1)
                                    AllSortClassList.clear();
                                AllSortClassList.addAll(news);
                                setHotPageView();
                                if (banner != null && !refreshStatus && banner.size() > 0)
                                    initBannerView();
                                swipe_layout.setRefreshing(false);
                                LogUtil.e(TAG, "AllSortClassList.size() : " + AllSortClassList.size());
                            }
                        } else {
                            toastUtil.showToast(hotAllDataBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        progressDialog.dismiss();
                        swipe_layout.setRefreshing(false);
                        super.onError(e);
                    }
                }));
    }

    //分享回调
    private void initShareDataWork() {
        progressDialog.show();
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.AFTERSHARE_SET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("reftype", 1);
        linkedHashMap.put("refid", newsid);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.UpLoadStatus(map)
                .compose(RxUtil.<UpLoadStatusNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<UpLoadStatusNetBean>(toastUtil) {
                    @Override
                    public void onNext(UpLoadStatusNetBean upLoadStatusNetBean) {
                        if (upLoadStatusNetBean.getStatus() == 1) {
                            toastUtil.showToast("分享成功");
                            progressDialog.dismiss();
                            RxBus.getDefault().post(new CommonEvent(EventCode.REFRESH_BRANCH));
                        } else {
                            toastUtil.showToast(upLoadStatusNetBean.getMessage());
                        }
                    }
                }));
    }

    private void clearUserInfo() {
        //需要清空本地登陆信息，以游客方式登陆
        DataClass.USERID = "";
        DataClass.SELECT = new StringBuffer().append(DataClass.URL_).append("pf_wx.php?act=oneday&do=display&userid_share=").toString();
        if (DataClass.LOGINTYPE != 0)
            umShareListenerBuilder.deleteOauth(DataClass.LOGINTYPE);
        dataManager.deleteLoginUserInfo("admin");
        RxBus.getDefault().post(new CommonEvent(EventCode.LOGIN_OUT));
    }

}
