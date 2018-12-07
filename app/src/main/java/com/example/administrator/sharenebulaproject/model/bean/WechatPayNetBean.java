package com.example.administrator.sharenebulaproject.model.bean;

/**
 * Created by Administrator on 2018/8/30.
 * 微信支付
 */

public class WechatPayNetBean {

    private String message;
    private String prepayinfo;
    private String result;
    private int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPrepayinfo() {
        return prepayinfo;
    }

    public void setPrepayinfo(String prepayinfo) {
        this.prepayinfo = prepayinfo;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
