package com.example.administrator.sharenebulaproject.model.bean;

/**
 * 作者：真理 Created by Administrator on 2018/12/4.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class RefreshDateBean {

    boolean status;
    String date;

    public RefreshDateBean(boolean status, String date) {
        this.status = status;
        this.date = date;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
