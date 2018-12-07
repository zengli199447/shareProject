package com.example.administrator.sharenebulaproject.model.manager;

import android.text.TextUtils;

/**
 * Created by Administrator on 2017/10/30.
 */

public class InvalidUrlException extends RuntimeException {

    public InvalidUrlException(String url) {
        super("You've configured an invalid url : " + (TextUtils.isEmpty(url) ? "EMPTY_OR_NULL_URL" : url));
    }
}
