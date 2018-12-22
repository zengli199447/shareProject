package com.example.administrator.sharenebulaproject.widget;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.tencent.smtt.sdk.DownloadListener;


/**
 * 作者：真理 Created by Administrator on 2018/12/21.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class MyDownLoadListener implements DownloadListener {

    Context context;

    public MyDownLoadListener(Context context) {
        this.context = context;
    }

    @Override
    public void onDownloadStart(String s, String s1, String s2, String s3, long l) {
        Uri uri = Uri.parse(s);

        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        context.startActivity(intent);
    }


}
