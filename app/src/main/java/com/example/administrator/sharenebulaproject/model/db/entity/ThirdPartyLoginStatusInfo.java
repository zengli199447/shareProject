package com.example.administrator.sharenebulaproject.model.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by Administrator on 2018/3/3 0003.
 * 邀请码填报状态
 */

@Entity
public class ThirdPartyLoginStatusInfo {
    //主键自增长
    @Id(autoincrement = true)
    private Long id;
    //第三方登录标识
    @Unique
    private String thirdPartyId;
    //邀请码是否填报
    private boolean validationStatus;

    public ThirdPartyLoginStatusInfo(String thirdPartyId, boolean validationStatus) {
        this.thirdPartyId = thirdPartyId;
        this.validationStatus = validationStatus;
    }

    @Generated(hash = 551951613)
    public ThirdPartyLoginStatusInfo(Long id, String thirdPartyId, boolean validationStatus) {
        this.id = id;
        this.thirdPartyId = thirdPartyId;
        this.validationStatus = validationStatus;
    }

    @Generated(hash = 342090142)
    public ThirdPartyLoginStatusInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getThirdPartyId() {
        return this.thirdPartyId;
    }

    public void setThirdPartyId(String thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }

    public boolean getValidationStatus() {
        return this.validationStatus;
    }

    public void setValidationStatus(boolean validationStatus) {
        this.validationStatus = validationStatus;
    }

  




}
