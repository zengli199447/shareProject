package com.example.administrator.sharenebulaproject.model.bean;

/**
 * Created by Administrator on 2018/8/20.
 */

public class UpLoadStatusNetBean {

    private String message;
    private Object result;
    private int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
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
