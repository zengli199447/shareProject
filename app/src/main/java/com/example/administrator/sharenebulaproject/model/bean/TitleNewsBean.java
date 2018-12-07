package com.example.administrator.sharenebulaproject.model.bean;

/**
 * 作者：真理 Created by Administrator on 2018/11/27.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class TitleNewsBean {

    String content;
    int id;
    boolean status;

    public TitleNewsBean(String content, int id, boolean status) {
        this.content = content;
        this.status = status;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
