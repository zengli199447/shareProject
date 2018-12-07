package com.example.administrator.sharenebulaproject.ui.activity.about;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.base.BaseActivity;
import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.model.bean.LoginInfoBean;
import com.example.administrator.sharenebulaproject.model.bean.MineInfoNetBean;
import com.example.administrator.sharenebulaproject.model.bean.PosterGetNetBean;
import com.example.administrator.sharenebulaproject.model.bean.UpLoadStatusNetBean;
import com.example.administrator.sharenebulaproject.model.bean.UserInfoBean;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.rxtools.RxUtil;
import com.example.administrator.sharenebulaproject.ui.dialog.ProgressDialog;
import com.example.administrator.sharenebulaproject.ui.dialog.ShowDialog;
import com.example.administrator.sharenebulaproject.ui.view.CustomPopupWindow;
import com.example.administrator.sharenebulaproject.utils.LogUtil;
import com.example.administrator.sharenebulaproject.utils.QrUtils;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;
import com.example.administrator.sharenebulaproject.widget.CommonSubscriber;
import com.example.administrator.sharenebulaproject.widget.QrBuilder;
import com.example.administrator.sharenebulaproject.widget.UmShareListenerBuilder;
import com.example.administrator.sharenebulaproject.widget.WebViewBuilder;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/8/24.
 * 通用Tools 2
 */

public class GeneralVersionActivity extends BaseActivity implements View.OnClickListener, CustomPopupWindow.OnItemClickListener, PopupWindow.OnDismissListener, UmShareListenerBuilder.onShareListener {

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
    View title_about_img;

    @BindView(R.id.layout_posters)
    LinearLayout layout_posters;
    @BindView(R.id.qr_code)
    TextView qr_code;
    @BindView(R.id.qr_img)
    ImageView qr_img;
    @BindView(R.id.posters_bg_img)
    ImageView posters_bg_img;
    @BindView(R.id.layout_poster)
    RelativeLayout layout_poster;

    @BindView(R.id.layout_title_bar)
    RelativeLayout layout_title_bar;

    private WebViewBuilder webViewBuilder;
    private int flags;
    private CustomPopupWindow customPopupWindow;
    private String intentValue;
    private Bitmap bitmap;
    private UmShareListenerBuilder umShareListenerBuilder;
    private ProgressDialog progressDialog;

    private Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case 0:
                    bitmap = SystemUtil.captureScreen(GeneralVersionActivity.this);
                    SystemUtil.hideBottomUIMenu(false,GeneralVersionActivity.this);
                    if (bitmap == null) {
                        toastUtil.showToast(getString(R.string.bitmap_exception));
                        return;
                    }
                    customPopupWindow.showAtLocation(layout_posters, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 100);
                    SystemUtil.windowToDark(GeneralVersionActivity.this);
                    break;
            }
        }
    };

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_generalversion;
    }

    @Override
    protected void initClass() {
        progressDialog = ShowDialog.getInstance().showProgressStatus(this, getString(R.string.progress));
        webViewBuilder = new WebViewBuilder(web_view, progressDialog, toastUtil, this,null);
        customPopupWindow = new CustomPopupWindow(this);
        umShareListenerBuilder = new UmShareListenerBuilder(this, toastUtil);
    }

    @Override
    protected void initData() {
        flags = getIntent().getFlags();
        intentValue = getIntent().getStringExtra("value");
    }

    @Override
    protected void initView() {
        switch (flags) {
            case 0:
                web_layout.setVisibility(View.VISIBLE);
                title_name.setText(getString(R.string.message));
                webViewBuilder.initWebView();
                webViewBuilder.loadWebView(new StringBuffer().append(DataClass.MESSAGE_URL).append(DataClass.USERID).toString(), true);
                break;
            case 1:
                title_name.setText(getString(R.string.posters));
                layout_posters.setVisibility(View.VISIBLE);
                initNetDataWork();
                title_about_img.setBackground(getResources().getDrawable(R.drawable.share_icon));
                break;
            case 2:

                break;
        }
    }

    @Override
    protected void initListener() {
        img_btn_black.setOnClickListener(this);
        customPopupWindow.setOnItemClickListener(this);
        customPopupWindow.setOnDismissListener(this);
        title_about_img.setOnClickListener(this);
        umShareListenerBuilder.setOnShareListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.title_about_img_:
                SystemUtil.hideBottomUIMenu(true,GeneralVersionActivity.this);
                layout_title_bar.setVisibility(View.GONE);
                handler.sendEmptyMessageDelayed(0, 500);
                break;
        }
    }

    @Override
    public void onDismiss() {
        layout_title_bar.setVisibility(View.VISIBLE);
        SystemUtil.windowToLight(this);
    }

    @Override
    public void setOnItemClick(View v) {
        switch (v.getId()) {
            case R.id.share_wechat:
//                toastUtil.showToast("wechat");
                umShareListenerBuilder.initUmImageShare(1, bitmap);
                break;
            case R.id.wechat_friends:
//                toastUtil.showToast("wechat_friends");
                umShareListenerBuilder.initUmImageShare(2, bitmap);
                break;
            case R.id.qq:
//                toastUtil.showToast("qq");
                umShareListenerBuilder.initUmImageShare(0, bitmap);
                break;
        }
        customPopupWindow.dismiss();
    }

    @Override
    public void successful() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        layout_title_bar.setVisibility(View.VISIBLE);
    }

    //获取海报信息
    private void initNetDataWork() {
        progressDialog.show();
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.POSTER_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.getPosterGetNetBean(map)
                .compose(RxUtil.<PosterGetNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<PosterGetNetBean>(toastUtil) {
                    @Override
                    public void onNext(PosterGetNetBean posterGetNetBean) {
                        LogUtil.e(TAG, "UpLoadStatusNetBean : " + posterGetNetBean.toString());
                        if (posterGetNetBean.getStatus() == 1) {
                            PosterGetNetBean.ResultBean result = posterGetNetBean.getResult();
                            String string = new StringBuffer().append(DataClass.FileUrl).append(result.getPoster().getPhoto()).toString();
                            LogUtil.e(TAG, "string : " + string);
                            Glide.with(GeneralVersionActivity.this).load(new StringBuffer().append(DataClass.FileUrl).append(result.getPoster().getPhoto()).toString()).error(R.drawable.banner_off).into(posters_bg_img);
                            if (result.getInvitationcode() != null) {
                                bitmap = QrBuilder.createQRCodeWithLogo(result.getEwm_url(), 200, BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
//                                imgQr.setImageBitmap(bitmap);
//                                bitmap = QrUtils.encodeAsBitmap(result.getEwm_url());
                                qr_img.setImageBitmap(bitmap);
                                qr_code.setText(new StringBuffer().append(getString(R.string.invite_code)).append(result.getInvitationcode()).toString());
                            }
                        } else {
                            toastUtil.showToast(posterGetNetBean.getMessage());
                        }
                        progressDialog.dismiss();
                    }
                }));
    }


}
