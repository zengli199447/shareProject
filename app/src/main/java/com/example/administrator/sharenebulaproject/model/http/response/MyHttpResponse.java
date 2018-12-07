package com.example.administrator.sharenebulaproject.model.http.response;

import static android.R.attr.data;

/**
 * Created by Administrator on 2017/10/31.
 */

public class MyHttpResponse<T> {



    private int code;
    private String message;
    private T entity;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    @Override
    public String toString() {
        return "MyHttpResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
