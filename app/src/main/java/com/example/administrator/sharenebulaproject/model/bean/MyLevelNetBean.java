package com.example.administrator.sharenebulaproject.model.bean;

/**
 * Created by Administrator on 2018/8/22.
 * 我的等级
 */

public class MyLevelNetBean {

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

        private UserlevelBean userlevel;

        public UserlevelBean getUserlevel() {
            return userlevel;
        }

        public void setUserlevel(UserlevelBean userlevel) {
            this.userlevel = userlevel;
        }

        public static class UserlevelBean {

            private String Invitationcode;
            private String city;
            private String job;
            private String levelname;
            private String moneyin;
            private String photo;
            private String province;
            private String remark;
            private String secondname;
            private String sex;
            private String userid;

            public String getInvitationcode() {
                return Invitationcode;
            }

            public void setInvitationcode(String Invitationcode) {
                this.Invitationcode = Invitationcode;
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

            public String getLevelname() {
                return levelname;
            }

            public void setLevelname(String levelname) {
                this.levelname = levelname;
            }

            public String getMoneyin() {
                return moneyin;
            }

            public void setMoneyin(String moneyin) {
                this.moneyin = moneyin;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getSecondname() {
                return secondname;
            }

            public void setSecondname(String secondname) {
                this.secondname = secondname;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
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
