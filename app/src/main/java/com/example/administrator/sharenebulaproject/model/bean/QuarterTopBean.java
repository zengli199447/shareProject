package com.example.administrator.sharenebulaproject.model.bean;

/**
 * Created by Administrator on 2018/8/14.
 * 季度榜单
 */

public class QuarterTopBean {

    int index;
    String user;
    String harvest;

    public QuarterTopBean(int index, String user, String harvest) {
        this.index = index;
        this.user = user;
        this.harvest = harvest;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getHarvest() {
        return harvest;
    }

    public void setHarvest(String harvest) {
        this.harvest = harvest;
    }
}
