package com.example.administrator.sharenebulaproject.ui.activity.about;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.base.BaseActivity;
import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.ui.dialog.ProgressDialog;
import com.example.administrator.sharenebulaproject.ui.dialog.ShowDialog;
import com.example.administrator.sharenebulaproject.utils.LogUtil;
import com.example.administrator.sharenebulaproject.widget.WebViewBuilder;
import com.tencent.smtt.sdk.WebView;

import java.util.Set;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/9/7.
 */

public class PushMessageActivity extends BaseActivity implements View.OnClickListener {

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
    private ProgressDialog progressDialog;
    private WebViewBuilder webViewBuilder;

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_push_message;
    }

    @Override
    protected void initClass() {
        progressDialog = ShowDialog.getInstance().showProgressStatus(this, getString(R.string.progress));
        webViewBuilder = new WebViewBuilder(web_view, progressDialog, toastUtil, this,null);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        web_layout.setVisibility(View.VISIBLE);
        title_name.setText(getString(R.string.message));
        webViewBuilder.initWebView();
        webViewBuilder.loadWebView(new StringBuffer().append(DataClass.MESSAGE_URL).append(DataClass.USERID).toString(), true);

    }

    @Override
    protected void initListener() {
        img_btn_black.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bun = getIntent().getExtras();
        if (bun != null) {
            Set<String> keySet = bun.keySet();
            for (String key : keySet) {
                String value = bun.getString(key);
                LogUtil.e(TAG,"UmPushValue : " + value);
            }
        }
    }

}
