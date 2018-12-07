package com.example.administrator.sharenebulaproject.ui.activity;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.base.BaseActivity;
import com.example.administrator.sharenebulaproject.global.MyApplication;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.ui.dialog.ProgressDialog;
import com.example.administrator.sharenebulaproject.ui.dialog.ShowDialog;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ShowDialog showDialog = ShowDialog.getInstance();
        final ProgressDialog progressDialog = showDialog.showProgressStatus(this, "发生错误，正在关闭应用 ...");
        progressDialog.show();
        MyApplication.executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                    progressDialog.dismiss();
                    MyApplication.exitApp();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
