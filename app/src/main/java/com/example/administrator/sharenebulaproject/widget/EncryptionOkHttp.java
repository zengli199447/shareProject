package com.example.administrator.sharenebulaproject.widget;

import com.example.administrator.sharenebulaproject.utils.LogUtil;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 作者：真理 Created by Administrator on 2019/1/15.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class EncryptionOkHttp extends OkHttpClient {
    String TAG = getClass().getSimpleName();


    @Override
    public Call newCall(Request request) {
        LogUtil.e(TAG, request.toString());
        return super.newCall(request);
    }
}
