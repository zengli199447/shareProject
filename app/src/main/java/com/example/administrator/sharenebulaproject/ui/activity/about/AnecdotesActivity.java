package com.example.administrator.sharenebulaproject.ui.activity.about;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobads.BaiduNativeAdPlacement;
import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.base.BaseActivity;
import com.example.administrator.sharenebulaproject.global.AppKeyConfig;
import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.model.bean.FansNetBean;
import com.example.administrator.sharenebulaproject.model.bean.TheArticleDetailsNetBean;
import com.example.administrator.sharenebulaproject.model.bean.UpLoadStatusNetBean;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.model.event.EventCode;
import com.example.administrator.sharenebulaproject.rxtools.RxUtil;
import com.example.administrator.sharenebulaproject.ui.activity.LoginActivity;
import com.example.administrator.sharenebulaproject.ui.adapter.AnecdotesContentAdapter;
import com.example.administrator.sharenebulaproject.ui.adapter.CommentsAdapter;
import com.example.administrator.sharenebulaproject.ui.adapter.RecommendedAdapter;
import com.example.administrator.sharenebulaproject.ui.view.CustomPopupWindow;
import com.example.administrator.sharenebulaproject.utils.AESCryptUtil;
import com.example.administrator.sharenebulaproject.utils.LogUtil;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;
import com.example.administrator.sharenebulaproject.widget.AdvertisingBuilder;
import com.example.administrator.sharenebulaproject.widget.CommonSubscriber;
import com.example.administrator.sharenebulaproject.widget.FullyLinearLayoutManager;
import com.example.administrator.sharenebulaproject.widget.UmShareListenerBuilder;
import com.example.administrator.sharenebulaproject.widget.ViewBuilder;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/11/30.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class AnecdotesActivity extends BaseActivity implements CustomPopupWindow.OnItemClickListener, PopupWindow.OnDismissListener, View.OnClickListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.title_about_img_)
    View title_about_img_;
    @BindView(R.id.qq_advertising_view_top)
    FrameLayout qq_advertising_view_top;
    @BindView(R.id.qq_advertising_view_bottom)
    FrameLayout qq_advertising_view_bottom;
    @BindView(R.id.baidu_advertising_view_top)
    RelativeLayout baidu_advertising_view_top;
    @BindView(R.id.baidu_advertising_view_bottom)
    RelativeLayout baidu_advertising_view_bottom;
    @BindView(R.id.csj_advertising_view_top)
    FrameLayout csj_advertising_view_top;
    @BindView(R.id.csj_advertising_view_bottom)
    FrameLayout csj_advertising_view_bottom;

    @BindView(R.id.the_article_title)
    TextView the_article_title;
    @BindView(R.id.the_article_from_to)
    TextView the_article_from_to;
    @BindView(R.id.content_recycler_view)
    RecyclerView content_recycler_view;
    @BindView(R.id.recommended_recycler_view)
    RecyclerView recommended_recycler_view;
    @BindView(R.id.comments_recycler_view)
    RecyclerView comments_recycler_view;

    @BindView(R.id.link_share)
    ImageView link_share;
    @BindView(R.id.comments_img)
    ImageView comments_img;
    @BindView(R.id.comments_count)
    TextView comments_count;
    @BindView(R.id.leave_a_message_layout)
    RelativeLayout leave_a_message_layout;
    @BindView(R.id.smile)
    ImageView smile;
    @BindView(R.id.like)
    ImageView like;

    @BindView(R.id.input_layout)
    RelativeLayout input_layout;
    @BindView(R.id.edit_input_content)
    EditText edit_input_content;
    @BindView(R.id.post)
    TextView post;

    @BindView(R.id.scrollView)
    NestedScrollView scrollView;

    private int flags;
    private String intentValue;
    private String shareTitle;
    private String shareImgUrl;
    private String shareNewsUrl;
    private CustomPopupWindow customPopupWindow;
    private Bitmap bitmap;
    private UmShareListenerBuilder umShareListenerBuilder;
    private AdvertisingBuilder advertisingBuilder;
    private TheArticleDetailsNetBean.ResultBean result;
    private List<Object> list = new ArrayList<>();
    private AnecdotesContentAdapter anecdotesContentAdapter;
    private List<TheArticleDetailsNetBean.ResultBean.TjlistBean> tjlist = new ArrayList<>();
    private RecommendedAdapter recommendedAdapter;
    private List<TheArticleDetailsNetBean.ResultBean.CommentBean> commentlist = new ArrayList<>();
    private CommentsAdapter commentsAdapter;

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.USERID_REFRESH:
                shareNewsUrl = shareNewsUrl + commonevent.getTemp_str();
                break;
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.anecdotes_activity;
    }

    @Override
    protected void initClass() {
        customPopupWindow = new CustomPopupWindow(this);
        umShareListenerBuilder = new UmShareListenerBuilder(this, toastUtil);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        advertisingBuilder = new AdvertisingBuilder(this);

        content_recycler_view.setLayoutManager(ViewBuilder.getFullyLinearLayoutManager(this));
        recommended_recycler_view.setLayoutManager(ViewBuilder.getFullyLinearLayoutManager(this));
        comments_recycler_view.setLayoutManager(ViewBuilder.getFullyLinearLayoutManager(this));

        content_recycler_view.setFocusable(false);
        recommended_recycler_view.setFocusable(false);
        comments_recycler_view.setFocusable(false);

        anecdotesContentAdapter = new AnecdotesContentAdapter(this, list);
        recommendedAdapter = new RecommendedAdapter(this, tjlist);
        commentsAdapter = new CommentsAdapter(this, commentlist);

        content_recycler_view.setAdapter(anecdotesContentAdapter);
        recommended_recycler_view.setAdapter(recommendedAdapter);
        comments_recycler_view.setAdapter(commentsAdapter);

        View decorView = getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(ViewBuilder.getGlobalLayoutListener(decorView, findViewById(Window.ID_ANDROID_CONTENT)));
        KeyBoardSetting();
    }

    @Override
    protected void initData() {
        flags = getIntent().getFlags();
        intentValue = getIntent().getStringExtra("value");
        shareTitle = getIntent().getStringExtra("shareTitle");
        shareImgUrl = getIntent().getStringExtra("shareImgUrl");
        shareNewsUrl = getIntent().getStringExtra("shareNewsUrl");
        String total = getIntent().getStringExtra("total");
        String ifcanmoney = getIntent().getStringExtra("ifcanmoney");

        if (ifcanmoney != null && "1".equals(ifcanmoney)) {
            title_about_img_.setBackground(getResources().getDrawable(R.drawable.share_money));
            if ("0".equals(total)) {
                title_about_img_.setBackground(getResources().getDrawable(R.drawable.share_money_off));
            } else {
                title_about_img_.setBackground(getResources().getDrawable(R.drawable.share_money));
            }
        } else if (ifcanmoney != null && "0".equals(ifcanmoney)) {
            customPopupWindow.refreshViewStatus(false);
            title_about_img_.setBackground(getResources().getDrawable(R.drawable.share_icon));
        }

        if (total == null && ifcanmoney == null) {
            title_about_img_.setVisibility(View.GONE);
        }
        initContentNetDataWork();
    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.daily_news));
    }

    @Override
    protected void initListener() {
        customPopupWindow.setOnItemClickListener(this);
        customPopupWindow.setOnDismissListener(this);
        like.setOnClickListener(this);
        leave_a_message_layout.setOnClickListener(this);
        post.setOnClickListener(this);
        link_share.setOnClickListener(this);
    }

    @Override
    public void onDismiss() {
        SystemUtil.windowToLight(this);
    }

    @Override
    public void setOnItemClick(View v) {
        switch (v.getId()) {
            case R.id.share_wechat:
                umShareListenerBuilder.initUmUrlShare(1, bitmap, shareImgUrl, new StringBuffer().append(shareNewsUrl).append("&type=1").append("&ifapp=0").toString(), shareTitle, shareTitle);
                break;
            case R.id.wechat_friends:
                umShareListenerBuilder.initUmUrlShare(2, bitmap, shareImgUrl, new StringBuffer().append(shareNewsUrl).append("&type=1").append("&ifapp=0").toString(), shareTitle, shareTitle);
                break;
            case R.id.qq:
                umShareListenerBuilder.initUmUrlShare(0, bitmap, shareImgUrl, new StringBuffer().append(shareNewsUrl).append("&type=2").append("&ifapp=0").toString(), shareTitle, shareTitle);
                break;
        }
        customPopupWindow.dismiss();
    }

    @OnClick({R.id.img_btn_black, R.id.title_about_img_})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.title_about_img_:
                ShowShareSelect();
                break;
            case R.id.like:
                SupportNetDataWork();
                break;
            case R.id.leave_a_message_layout:
                input_layout.setVisibility(View.VISIBLE);
                SystemUtil.openKeybord(edit_input_content, this);
                break;
            case R.id.post:
                if (!DataClass.USERID.isEmpty()) {
                    if (edit_input_content.getText().toString().trim().isEmpty()) {
                        toastUtil.showToast(getString(R.string.empty_exception));
                    } else {
                        postCommentsNetDataWork(edit_input_content.getText().toString().trim());
                        SystemUtil.closeKeybord(edit_input_content, this);
                    }
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
            case R.id.link_share:
                ShowShareSelect();
                break;
        }
    }

    //监听软键盘是否显示或隐藏
    private void KeyBoardSetting() {
        View decorView = getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(getGlobalLayoutListener(decorView, leave_a_message_layout));
    }

    private ViewTreeObserver.OnGlobalLayoutListener getGlobalLayoutListener(final View decorView, final View contentView) {
        return new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                decorView.getWindowVisibleDisplayFrame(r);
                int height = decorView.getContext().getResources().getDisplayMetrics().heightPixels;
                int diff = height - r.bottom;
                if (diff != 0) {
                    if (contentView.getPaddingBottom() != diff) {
                        LogUtil.e(TAG, "开启");
                        contentView.setPadding(0, 0, 0, diff);
                    }
                } else {
                    if (contentView.getPaddingBottom() != 0) {
                        contentView.setPadding(0, 0, 0, 0);
                        input_layout.setVisibility(View.GONE);
                        edit_input_content.setText("");
                    }
                }
            }
        };
    }

    //显示选择分享分类
    public void ShowShareSelect() {
        customPopupWindow.showAtLocation(title_name, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        SystemUtil.windowToDark(this);
    }

    //刷新视图
    private void refreshView() {
        list.clear();
        tjlist.clear();
        commentlist.clear();

        TheArticleDetailsNetBean.ResultBean.DetailBean detail = result.getDetail();

        list.addAll(detail.getContent());
        tjlist.addAll(result.getTjlist());
        commentlist.addAll(result.getComment());

        switch (result.getAdtype()) {
            case 0:
                advertisingBuilder.initQQAdvertising(qq_advertising_view_top);
                advertisingBuilder.initQQAdvertising(qq_advertising_view_bottom);
                break;
            case 1:
                advertisingBuilder.initBaiDuAdvertising(baidu_advertising_view_top);
                advertisingBuilder.initBaiDuAdvertising(baidu_advertising_view_bottom);
                BaiduNativeAdPlacement placement = new BaiduNativeAdPlacement();
                placement.setSessionId(1); // 设置页面id，不同listview不同，从1开始的正整数，可选
                placement.setPositionId(1); // 设置广告在页面的楼层，从1开始的正整数，可选
                placement.setApId(AppKeyConfig.ADVERTISING_BAIDU_ADVERTISING_RECYCLER_ID);
                list.add((detail.getContent().size() / 2), placement);
                break;
            case 2:
                advertisingBuilder.initCsjAdvertising(csj_advertising_view_top);
                advertisingBuilder.initCsjAdvertising(csj_advertising_view_bottom);
                break;
        }

        anecdotesContentAdapter.notifyDataSetChanged();
        recommendedAdapter.notifyDataSetChanged();
        commentsAdapter.notifyDataSetChanged();

        the_article_title.setText(detail.getTitle());
        the_article_from_to.setText(detail.getSource());
        if (result.getComment().size() > 0) {
            comments_count.setText(String.valueOf(result.getComment().size()));
            comments_count.setVisibility(View.VISIBLE);
        } else {
            comments_count.setVisibility(View.GONE);
        }

        LikeStatusRefresh(Integer.valueOf(detail.getIszan()));

    }

    //点赞视图刷新
    private void LikeStatusRefresh(int status) {
        if (1 == status) {
            like.setImageDrawable(getResources().getDrawable(R.drawable.like_icon));
        } else {
            like.setImageDrawable(getResources().getDrawable(R.drawable.like_off_icon));
        }
    }

    //获取详情内容
    private void initContentNetDataWork() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.NEWS_DETAIL_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("newsid", intentValue);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.getTheArticleDetailsNetBean(map)
                .compose(RxUtil.<TheArticleDetailsNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<TheArticleDetailsNetBean>(toastUtil) {
                    @Override
                    public void onNext(TheArticleDetailsNetBean theArticleDetailsNetBean) {
                        Log.e(TAG, "UpLoadStatusNetBean : " + theArticleDetailsNetBean.toString());
                        if (theArticleDetailsNetBean.getStatus() == 1) {
                            result = theArticleDetailsNetBean.getResult();
                            refreshView();
                        } else {
                            toastUtil.showToast(theArticleDetailsNetBean.getMessage());
                        }
                    }
                }));
    }

    //点赞
    private void SupportNetDataWork() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.ZAN_SET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("newsid", intentValue);
        String toJson =  AESCryptUtil.encrypt(new Gson().toJson(linkedHashMap));
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.UpLoadStatus(map)
                .compose(RxUtil.<UpLoadStatusNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<UpLoadStatusNetBean>(toastUtil) {
                    @Override
                    public void onNext(UpLoadStatusNetBean upLoadStatusNetBean) {
                        Log.e(TAG, "UpLoadStatusNetBean : " + upLoadStatusNetBean.toString());
                        if (upLoadStatusNetBean.getStatus() == 1) {
                            LikeStatusRefresh(Integer.valueOf(upLoadStatusNetBean.getResult().toString()));
                        } else {
                            toastUtil.showToast(upLoadStatusNetBean.getMessage());
                        }
                    }
                }));
    }

    //发送评论
    private void postCommentsNetDataWork(String content) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.COMMENT_SET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("newsid", intentValue);
        linkedHashMap.put("content", content);
        String toJson =  AESCryptUtil.encrypt(new Gson().toJson(linkedHashMap));
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.UpLoadStatus(map)
                .compose(RxUtil.<UpLoadStatusNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<UpLoadStatusNetBean>(toastUtil) {
                    @Override
                    public void onNext(UpLoadStatusNetBean upLoadStatusNetBean) {
                        Log.e(TAG, "UpLoadStatusNetBean : " + upLoadStatusNetBean.toString());
                        if (upLoadStatusNetBean.getStatus() == 1) {
                            initContentNetDataWork();
                        } else {
                            toastUtil.showToast(upLoadStatusNetBean.getMessage());
                        }
                    }
                }));
    }


}
