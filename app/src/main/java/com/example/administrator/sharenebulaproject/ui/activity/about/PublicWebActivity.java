package com.example.administrator.sharenebulaproject.ui.activity.about;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.base.BaseActivity;
import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.model.bean.DailyNetBean;
import com.example.administrator.sharenebulaproject.model.bean.MineInfoNetBean;
import com.example.administrator.sharenebulaproject.model.bean.NewContentNetBean;
import com.example.administrator.sharenebulaproject.model.bean.TextExplainsNetBean;
import com.example.administrator.sharenebulaproject.model.bean.UpLoadStatusNetBean;
import com.example.administrator.sharenebulaproject.model.bean.UserInfoBean;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.model.event.EventCode;
import com.example.administrator.sharenebulaproject.rxtools.RxBus;
import com.example.administrator.sharenebulaproject.rxtools.RxUtil;
import com.example.administrator.sharenebulaproject.ui.dialog.ProgressDialog;
import com.example.administrator.sharenebulaproject.ui.dialog.ShowDialog;
import com.example.administrator.sharenebulaproject.ui.view.CustomPopupWindow;
import com.example.administrator.sharenebulaproject.utils.LogUtil;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;
import com.example.administrator.sharenebulaproject.widget.CommonSubscriber;
import com.example.administrator.sharenebulaproject.widget.UmShareListenerBuilder;
import com.example.administrator.sharenebulaproject.widget.ViewBuilder;
import com.example.administrator.sharenebulaproject.widget.WebViewBuilder;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedHashMap;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/8/25.
 * 公开访问Tools
 */

public class PublicWebActivity extends BaseActivity implements View.OnClickListener, CustomPopupWindow.OnItemClickListener, PopupWindow.OnDismissListener, UmShareListenerBuilder.onShareListener {

    @BindView(R.id.web_layout)
    FrameLayout web_layout;
    @BindView(R.id.web_view)
    WebView web_view;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.img_btn_black)
    ImageButton img_btn_black;
    @BindView(R.id.title_about_img_)
    View title_about_img_;
    @BindView(R.id.advertising_layout)
    RelativeLayout advertising_layout;
    @BindView(R.id.advertising)
    ImageView advertising;
    @BindView(R.id.advertising_close)
    View advertising_close;
    private WebViewBuilder webViewBuilder;
    private int flags;
    private String intentValue;
    private CustomPopupWindow customPopupWindow;
    private UmShareListenerBuilder umShareListenerBuilder;
    private String shareTitle;
    private String shareImgUrl;
    private String shareNewsUrl;
    private Bitmap bitmap;
    private ProgressDialog progressDialog;

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
        return R.layout.activity_public_web;
    }

    @Override
    protected void initClass() {
        progressDialog = ShowDialog.getInstance().showProgressStatus(this, getString(R.string.progress));
        webViewBuilder = new WebViewBuilder(web_view, progressDialog, toastUtil, this, handler);
        customPopupWindow = new CustomPopupWindow(this);
        umShareListenerBuilder = new UmShareListenerBuilder(this, toastUtil);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        webViewBuilder.initWebView();
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

        LogUtil.e(TAG, "total : " + total);
        LogUtil.e(TAG, "ifcanmoney : " + ifcanmoney);

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
    }

    @Override
    protected void initView() {
        switch (flags) {
            case EventCode.GENERAL_WEB:
                web_layout.setVisibility(View.VISIBLE);
                title_name.setText(getString(R.string.daily_news));
//                initNetDataWork();
//                title_about_img.setBackground(getResources().getDrawable(R.drawable.share_icon));
                webViewBuilder.loadWebView(new StringBuffer().append(DataClass.DAILY_URL).append(intentValue).append("&ifapp=1").toString(), true);
                break;
            case EventCode.TEXT_WEB:
                web_layout.setVisibility(View.VISIBLE);
                switch (Integer.valueOf(intentValue)) {
                    case 2:
                        title_name.setText(new StringBuffer().append(getString(R.string.app_name)).append(getString(R.string.agreement)).toString());
                        break;
                    case 3:
                        title_name.setText(getString(R.string.select_awards_show));
                        break;
                    case 6:
                        title_name.setText(getString(R.string.help_center));
                        break;
                    case 7:
                        title_name.setText(getString(R.string.generalize_content));
                        break;
                    case 8:
                        title_name.setText(getString(R.string.level_modle));
                        break;
                    case 9:
                        title_name.setText(getString(R.string.customer_service));
                        break;
                    case 13:
                        title_name.setText(getString(R.string.settlement_content));
                        break;
                    case 10:
                        title_name.setText(getString(R.string.content));
                        break;
                }
                initNetTextDataWork(Integer.valueOf(intentValue));
                break;
            case EventCode.CONTENT_WEB:
                web_layout.setVisibility(View.VISIBLE);
                title_name.setText(getString(R.string.daily_news));
//                title_about_img.setBackground(getResources().getDrawable(R.drawable.share_icon));
                webViewBuilder.loadWebView(intentValue, false);
                break;

        }
    }

    @Override
    protected void initListener() {
        img_btn_black.setOnClickListener(this);
        title_about_img_.setOnClickListener(this);
        customPopupWindow.setOnItemClickListener(this);
        customPopupWindow.setOnDismissListener(this);
        umShareListenerBuilder.setOnShareListener(this);
        advertising.setOnClickListener(this);
        advertising_close.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.title_about_img_:
                ShowShareSelect();
                break;
            case R.id.advertising:

                break;
            case R.id.advertising_close:
                advertising_layout.setVisibility(View.GONE);
                break;
        }
    }

    public void ShowShareSelect() {
        customPopupWindow.showAtLocation(web_layout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 100);
        SystemUtil.windowToDark(this);
    }

    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case 0:
                    ShowShareSelect();
                    break;
            }

        }
    };

    @Override
    public void onDismiss() {
        SystemUtil.windowToLight(PublicWebActivity.this);
    }

    @Override
    public void setOnItemClick(View v) {
        LogUtil.e(TAG, "shareNewsUrl : " + shareNewsUrl);
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

    @Override
    public void successful() {
        initShareDataWork();
    }

    //获取文章详情
    private void initNetDataWork() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.NEWS_DETAIL_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("newsid", intentValue);
        linkedHashMap.put("ifweb", "");
        LogUtil.e(TAG, "intentValue : " + intentValue);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.getNewContentNetBean(map)
                .compose(RxUtil.<NewContentNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<NewContentNetBean>(toastUtil) {
                    @Override
                    public void onNext(NewContentNetBean newContentNetBean) {
                        LogUtil.e(TAG, "NewContentNetBean : " + newContentNetBean.toString());
                        if (newContentNetBean.getStatus() == 1) {
                            webViewBuilder.loadWebView(newContentNetBean.getResult().getDetail().getContent(), false);
                            if ("1".equals(newContentNetBean.getResult().getDetail().getIfcanmoney())) {
                                title_about_img_.setBackground(getResources().getDrawable(R.drawable.share_icon));
                            }
                        }
                    }

                }));
    }

    //获取文本
    private void initNetTextDataWork(int infotype) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.TEXTINFO_GET);
        linkedHashMap.put("infotype", infotype);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.getTextExplainsNetBean(map)
                .compose(RxUtil.<TextExplainsNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<TextExplainsNetBean>(toastUtil) {
                    @Override
                    public void onNext(TextExplainsNetBean textExplainsNetBean) {
                        if (textExplainsNetBean.getStatus() == 1) {
                            LogUtil.e(TAG, "TextExplainsNetBean : " + textExplainsNetBean.toString());
                            String content = textExplainsNetBean.getResult().getContent();
                            LogUtil.e(TAG, "content : " + content);
                            webViewBuilder.loadWebView(content, false);
                        } else {
                            toastUtil.showToast(textExplainsNetBean.getMessage());
                        }
                    }
                }));
    }

    //获取banner文本
    private void initNetBannerDataWork(int infotype) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.TEXTINFO_DETAIL_GET);
        linkedHashMap.put("textInfoid", infotype);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.getTextExplainsNetBean(map)
                .compose(RxUtil.<TextExplainsNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<TextExplainsNetBean>(toastUtil) {
                    @Override
                    public void onNext(TextExplainsNetBean textExplainsNetBean) {
                        LogUtil.e(TAG, "TextExplainsNetBean : " + textExplainsNetBean.toString());
                        String content = textExplainsNetBean.getResult().getContent();
                        LogUtil.e(TAG, "content : " + content);
                        webViewBuilder.loadWebView(content, false);
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
        linkedHashMap.put("refid", intentValue);
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
                            RxBus.getDefault().post(new CommonEvent(EventCode.REFRESH_BRANCH));
                        } else {
                            toastUtil.showToast(upLoadStatusNetBean.getMessage());
                        }
                        progressDialog.dismiss();
                    }
                }));
    }

}
