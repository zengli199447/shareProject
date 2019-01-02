package com.example.administrator.sharenebulaproject.provider;

import android.content.Context;
import android.support.v4.content.FileProvider;

import com.example.administrator.sharenebulaproject.utils.LogUtil;

/**
 * 作者：真理 Created by Administrator on 2019/1/2.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class MyFileProvider extends FileProvider {

    public String TAG = getClass().getSimpleName();

    public static String getFileProviderName(Context context) {
        return context.getPackageName() + ".sharenebulaproject.provider";
    }
}
