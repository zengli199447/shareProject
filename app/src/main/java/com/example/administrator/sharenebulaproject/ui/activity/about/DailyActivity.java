package com.example.administrator.sharenebulaproject.ui.activity.about;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.base.BaseActivity;
import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.model.bean.DailyNetBean;
import com.example.administrator.sharenebulaproject.model.bean.UpLoadStatusNetBean;
import com.example.administrator.sharenebulaproject.model.bean.UserInfoBean;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.model.event.EventCode;
import com.example.administrator.sharenebulaproject.rxtools.RxBus;
import com.example.administrator.sharenebulaproject.rxtools.RxUtil;
import com.example.administrator.sharenebulaproject.ui.adapter.DailyRecyclerViewAdapter;
import com.example.administrator.sharenebulaproject.ui.dialog.ProgressDialog;
import com.example.administrator.sharenebulaproject.ui.dialog.ShowDialog;
import com.example.administrator.sharenebulaproject.ui.view.CustomPopupWindow;
import com.example.administrator.sharenebulaproject.ui.view.ImageNetSlideshow;
import com.example.administrator.sharenebulaproject.utils.LogUtil;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;
import com.example.administrator.sharenebulaproject.widget.CommonSubscriber;
import com.example.administrator.sharenebulaproject.widget.FullyLinearLayoutManager;
import com.example.administrator.sharenebulaproject.widget.UmShareListenerBuilder;
import com.example.administrator.sharenebulaproject.widget.WebViewBuilder;
import com.google.gson.Gson;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/8/24.
 * 每日精选 需求变动，当前页面不使用
 */

public class DailyActivity extends BaseActivity implements View.OnClickListener, ImageNetSlideshow.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, DailyRecyclerViewAdapter.onCheckListener, AppBarLayout.OnOffsetChangedListener, CustomPopupWindow.OnItemClickListener, PopupWindow.OnDismissListener, DailyRecyclerViewAdapter.onItemCheckListener, UmShareListenerBuilder.onShareListener {

    @BindView(R.id.hotpage_slide_show)
    ImageNetSlideshow slide_show;
    @BindView(R.id.hot_recyclerview)
    RecyclerView hot_list;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipe_layout;
    @BindView(R.id.bar_layout)
    AppBarLayout bar_layout;
    @BindView(R.id.img_btn_black)
    ImageButton img_btn_black;
    @BindView(R.id.title_about_img_)
    View title_about_img;
    @BindView(R.id.hotpage_slide_show_layout)
    LinearLayout hotpage_slide_show_layout;

    private DailyRecyclerViewAdapter hotRecyclerViewAdapter;
    private FullyLinearLayoutManager fullyLinearLayoutManager;
    private boolean refreshStatus;
    private CustomPopupWindow customPopupWindow;
    private List<DailyNetBean.ResultBean.NewsBean> news = new ArrayList<>();
    private ProgressDialog progressDialog;
    private UmShareListenerBuilder umShareListenerBuilder;
    private Bitmap bitmap;
    private String shareImgUrl = "";
    private String shareNewsUrl = "";
    private String shareTitle = "";
    private String newsid;
    private int selectRefresh = 0;
    private boolean shareRefresh;
    private List<DailyNetBean.ResultBean.BannerBean> banner;
    private int reftype = 1;
    private String shareType = "";

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.USERID_REFRESH:
                shareNewsUrl = shareNewsUrl + commonevent.getTemp_str();
                DataClass.USERID_SHARE = "&userid_share=" + commonevent.getTemp_str();
                shareNewsUrl = shareNewsUrl + commonevent.getTemp_str();
                DataClass.SELECT = shareNewsUrl;
                break;
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_daily;
    }

    @Override
    protected void initClass() {
        swipe_layout.setProgressViewOffset(true, -20, 100);
        swipe_layout.setColorSchemeResources(R.color.blue);
        fullyLinearLayoutManager = new FullyLinearLayoutManager(this);
        fullyLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        hot_list.setLayoutManager(fullyLinearLayoutManager);
        customPopupWindow = new CustomPopupWindow(this);
        progressDialog = ShowDialog.getInstance().showProgressStatus(this, getString(R.string.progress));
        umShareListenerBuilder = new UmShareListenerBuilder(this, toastUtil);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        hotRecyclerViewAdapter = new DailyRecyclerViewAdapter(this, news);
        hot_list.setAdapter(hotRecyclerViewAdapter);
    }

    @Override
    protected void initData() {
        progressDialog.show();
        initNetDataWork();
    }

    @Override
    protected void initView() {
        slide_show.setOverStatus(false);
        slide_show.setDotSpace(24);
        slide_show.setDotSize(12);
        slide_show.setDelay(3000);
        title_about_img.setBackground(getResources().getDrawable(R.drawable.share_icon));
        title_about_img.setVisibility(View.GONE);
    }

    @Override
    protected void initListener() {
        slide_show.setOnItemClickListener(this);
        bar_layout.addOnOffsetChangedListener(this);
        swipe_layout.setOnRefreshListener(this);
        customPopupWindow.setOnItemClickListener(this);
        customPopupWindow.setOnDismissListener(this);
        img_btn_black.setOnClickListener(this);
        title_about_img.setOnClickListener(this);
        umShareListenerBuilder.setOnShareListener(this);
//        hotRecyclerViewAdapter.setOnCheckListener(this);
        hotRecyclerViewAdapter.setOnItemCheckListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.title_about_img_: //每日精选分享点击
                reftype = 2;
                newsid = "";
                shareTitle = getString(R.string.select);
                shareImgUrl = "";
                shareNewsUrl = DataClass.SELECT;
                customPopupWindow.showAtLocation(hot_list, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                SystemUtil.windowToDark(this);
                break;
        }
    }

    //分页新闻分享点击
    @Override
    public void onClick(int i) {
        reftype = 1;
        selectRefresh = i;
        initShareData();
    }

    //新闻详情查看点击
    @SuppressLint("WrongConstant")
    @Override
    public void onItemClick(int i) {
        DailyNetBean.ResultBean.NewsBean newsBean = news.get(i);
        Intent dailyIntent = new Intent(this, PublicWebActivity.class);
        dailyIntent.setFlags(EventCode.GENERAL_WEB);
        dailyIntent.putExtra("value", newsBean.getNewsid());
        dailyIntent.putExtra("shareTitle", newsBean.getTitle());
        dailyIntent.putExtra("shareImgUrl", new StringBuffer().append(DataClass.FileUrl).append(newsBean.getListimg().split(",")[0]).toString());
        dailyIntent.putExtra("shareNewsUrl", new StringBuffer().append(DataClass.DAILY_URL).append(newsBean.getNewsid()).append(DataClass.USERID_SHARE).toString());
        dailyIntent.putExtra("total", newsBean.getStarbean());
        dailyIntent.putExtra("ifcanmoney", newsBean.getIfcanmoney());
        startActivity(dailyIntent);
    }

    @Override
    public void onRefresh() {
        initNetDataWork();
    }

    //banner点击
    @SuppressLint("WrongConstant")
    @Override
    public void onItemClick(View view, int position) {
        Intent dailyIntent = new Intent(this, PublicWebActivity.class);
        dailyIntent.setFlags(EventCode.GENERAL_WEB);
        DailyNetBean.ResultBean.BannerBean bannerBean = banner.get(position);
        dailyIntent.putExtra("value", bannerBean.getNewsid());
        dailyIntent.putExtra("shareTitle", bannerBean.getTitle());
        dailyIntent.putExtra("shareImgUrl", new StringBuffer().append(DataClass.FileUrl).append(bannerBean.getTopimg()).toString());
        dailyIntent.putExtra("shareNewsUrl", new StringBuffer().append(DataClass.DAILY_URL).append(bannerBean.getNewsid()).append(DataClass.USERID_SHARE).toString());
        dailyIntent.putExtra("total", bannerBean.getStarbean());
        dailyIntent.putExtra("ifcanmoney", bannerBean.getIfcanmoney());
        startActivity(dailyIntent);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//        swipe_layout.setEnabled(verticalOffset >= 0 || SystemUtil.isSlideToBottom(hot_list) ? true : false);
        swipe_layout.setEnabled(verticalOffset == 0 ? true : false);
    }

    @Override
    public void onDismiss() {
        SystemUtil.windowToLight(this);
    }

    //分享点击
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

    //分享成功回调
    @Override
    public void successful() {
        initShareDataWork();
    }

    private void initShareData() {
        DailyNetBean.ResultBean.NewsBean newsBean = news.get(selectRefresh);
        newsid = newsBean.getNewsid();
        shareTitle = newsBean.getTitle();
        shareImgUrl = new StringBuffer().append(DataClass.FileUrl).append(newsBean.getListimg().split(",")[0]).toString();
        shareNewsUrl = new StringBuffer().append(DataClass.DAILY_URL).append(newsid).append(DataClass.USERID_SHARE).toString();
        if (Integer.valueOf(newsBean.getStarbean()) > 0) {
            customPopupWindow.showAtLocation(hot_list, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 100);
            SystemUtil.windowToDark(this);
        }
    }

    //banner 初始化
    private void initBannerView() {
        slide_show.ClearList();
        for (int i = 0; i < banner.size(); i++) {
            slide_show.addImageUrl(new StringBuffer().append(DataClass.FileUrl).append(banner.get(i).getTopimg()).toString());
        }
        slide_show.commit();
        refreshStatus = true;
    }

    private void initAdapter() {
        if (shareRefresh) {
            hotRecyclerViewAdapter.notifyItemChanged(selectRefresh);
        } else {

            hotRecyclerViewAdapter.notifyDataSetChanged();
        }
        shareRefresh = false;
    }

    //获取每日精选
    private void initNetDataWork() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.TODAY_NEWS_LIST_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.getDailyNetBean(map)
                .compose(RxUtil.<DailyNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<DailyNetBean>(toastUtil) {
                    @Override
                    public void onNext(DailyNetBean dailyNetBean) {
                        if (dailyNetBean.getStatus() == 1) {
                            DailyNetBean.ResultBean result = dailyNetBean.getResult();
                            banner = result.getBanner();
                            news.clear();
                            news.addAll(result.getNews());
                            if (banner != null && !refreshStatus && banner.size() > 0) {
                                initBannerView();
                                hotpage_slide_show_layout.setVisibility(View.VISIBLE);
                            } else {
                                hotpage_slide_show_layout.setVisibility(View.GONE);
                            }
                            initAdapter();
                            progressDialog.dismiss();
                            swipe_layout.setRefreshing(false);
                        } else {
                            toastUtil.showToast(dailyNetBean.getMessage());
                        }
                    }
                }));
    }

    //分享回调
    private void initShareDataWork() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.AFTERSHARE_SET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("reftype", reftype);
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
                            progressDialog.show();
                            shareRefresh = true;
                            initNetDataWork();
                            RxBus.getDefault().post(new CommonEvent(EventCode.REFRESH_BRANCH));
                        } else {
                            toastUtil.showToast(upLoadStatusNetBean.getMessage());
                        }
                    }
                }));
    }

}
