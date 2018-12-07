package com.example.administrator.sharenebulaproject.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/8/22.
 * 粉丝列表
 */

public class FansContentNetBean {

    private String message;
    private ResultBean result;
    private int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class ResultBean {
        private List<UsersBean> users;

        public List<UsersBean> getUsers() {
            return users;
        }

        public void setUsers(List<UsersBean> users) {
            this.users = users;
        }

        public static class UsersBean {

            public UsersBean(String createdate, String photo, String secondname, String userid) {
                this.createdate = createdate;
                this.photo = photo;
                this.secondname = secondname;
                this.userid = userid;
            }

            private String createdate;
            private String photo;
            private String secondname;
            private String userid;

            public String getCreatedate() {
                return createdate;
            }

            public void setCreatedate(String createdate) {
                this.createdate = createdate;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public String getSecondname() {
                return secondname;
            }

            public void setSecondname(String secondname) {
                this.secondname = secondname;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }
        }
    }
}
