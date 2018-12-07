package com.example.administrator.sharenebulaproject.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/8/25.
 * 达人榜单
 */

public class TopNetBean {

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
        private List<LogsBean> logs;
        private List<TalentMonthBean> talent_month;
        private List<TalentSeasonBean> talent_season;
        private List<TalentTotalBean> talent_total;

        public List<LogsBean> getLogs() {
            return logs;
        }

        public void setLogs(List<LogsBean> logs) {
            this.logs = logs;
        }

        public List<TalentMonthBean> getTalent_month() {
            return talent_month;
        }

        public void setTalent_month(List<TalentMonthBean> talent_month) {
            this.talent_month = talent_month;
        }

        public List<TalentSeasonBean> getTalent_season() {
            return talent_season;
        }

        public void setTalent_season(List<TalentSeasonBean> talent_season) {
            this.talent_season = talent_season;
        }

        public List<TalentTotalBean> getTalent_total() {
            return talent_total;
        }

        public void setTalent_total(List<TalentTotalBean> talent_total) {
            this.talent_total = talent_total;
        }

        public static class LogsBean {

            private String createdate;
            private String remark;

            public String getCreatedate() {
                return createdate;
            }

            public void setCreatedate(String createdate) {
                this.createdate = createdate;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }
        }

        public static class TalentMonthBean {

            private String beantotal_txt;
            private String photo;
            private String secondname;
            private String userid;

            public String getBeantotal_txt() {
                return beantotal_txt;
            }

            public void setBeantotal_txt(String beantotal_txt) {
                this.beantotal_txt = beantotal_txt;
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

        public static class TalentSeasonBean {

            private String beantotal_txt;
            private String photo;
            private String secondname;
            private String userid;

            public String getBeantotal_txt() {
                return beantotal_txt;
            }

            public void setBeantotal_txt(String beantotal_txt) {
                this.beantotal_txt = beantotal_txt;
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

        public static class TalentTotalBean {

            private String beantotal_txt;
            private String photo;
            private String secondname;
            private String userid;

            public String getBeantotal_txt() {
                return beantotal_txt;
            }

            public void setBeantotal_txt(String beantotal_txt) {
                this.beantotal_txt = beantotal_txt;
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
