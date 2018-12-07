package com.example.administrator.sharenebulaproject.model.bean;

/**
 * Created by Administrator on 2018/8/22.
 * BUS 信息载体
 */

public class BusObject {

    int intValue;
    String stringValue;

    public BusObject(int intValue, String stringValue) {
        this.intValue = intValue;
        this.stringValue = stringValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }
}
