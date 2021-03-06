package com.example.administrator.sharenebulaproject.model.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by Administrator on 2018/3/3 0003.
 */

@Entity
public class LoginUserInfo {
    //主键自增长
    @Id(autoincrement = true)
    private Long id;
    //用户名 (设计原因，当前只需要保存userId   用户名则采取统一的命名  即 admin)
    @Unique
    private String username;
    //用户id
    private String userid;
    //在线状态
    private String token;

    public LoginUserInfo(String username, String userid, String token) {
        this.username = username;
        this.userid = userid;
        this.token = token;
    }

    @Generated(hash = 1324356575)
    public LoginUserInfo(Long id, String username, String userid, String token) {
        this.id = id;
        this.username = username;
        this.userid = userid;
        this.token = token;
    }

    @Generated(hash = 436417725)
    public LoginUserInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
