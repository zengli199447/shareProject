package com.example.administrator.sharenebulaproject.model.bean;

/**
 * Created by Administrator on 2018/8/20.
 * 个人中心
 */

public class MineInfoNetBean {

    private int status;
    private ResultBean result;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class ResultBean {

        private String messageamount;
        private UserBean user;
        private String starbeantotal;

        public String getMessageamount() {
            return messageamount;
        }

        public void setMessageamount(String messageamount) {
            this.messageamount = messageamount;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public String getStarbeantotal() {
            return starbeantotal;
        }

        public void setStarbeantotal(String starbeantotal) {
            this.starbeantotal = starbeantotal;
        }

        public static class UserBean {

            private String userid;
            private String secondname;
            private String photo;
            private String levelname;
            private String Invitationcode;
            private String sex;
            private String province;
            private String city;
            private String job;
            private String moneyin;

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public String getSecondname() {
                return secondname;
            }

            public void setSecondname(String secondname) {
                this.secondname = secondname;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public String getLevelname() {
                return levelname;
            }

            public void setLevelname(String levelname) {
                this.levelname = levelname;
            }

            public String getInvitationcode() {
                return Invitationcode;
            }

            public void setInvitationcode(String Invitationcode) {
                this.Invitationcode = Invitationcode;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getJob() {
                return job;
            }

            public void setJob(String job) {
                this.job = job;
            }

            public String getMoneyin() {
                return moneyin;
            }

            public void setMoneyin(String moneyin) {
                this.moneyin = moneyin;
            }
        }
    }
}
