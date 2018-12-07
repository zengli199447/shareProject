package com.example.administrator.sharenebulaproject.model.bean;

/**
 * 作者：真理 Created by Administrator on 2018/12/4.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class SelfBuiltAdvertisingBean {

    String img;
    String id;
    String title;

    public SelfBuiltAdvertisingBean(String img, String id, String title) {
        this.img = img;
        this.id = id;
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
