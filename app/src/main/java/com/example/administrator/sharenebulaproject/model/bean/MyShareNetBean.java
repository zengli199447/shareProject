package com.example.administrator.sharenebulaproject.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/8/24.
 * 我的分享
 */

public class MyShareNetBean {

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
        private List<MyshareBean> myshare;

        public List<MyshareBean> getMyshare() {
            return myshare;
        }

        public void setMyshare(List<MyshareBean> myshare) {
            this.myshare = myshare;
        }

        public static class MyshareBean {

            private String amount_read;
            private String beantotal;
            private String createdate;
            private String ifing;
            private String newsid;
            private String amount_share;
            private String title;
            private String listimg;
            private String ifcanmoney;

            public String getIfcanmoney() {
                return ifcanmoney;
            }

            public void setIfcanmoney(String ifcanmoney) {
                this.ifcanmoney = ifcanmoney;
            }

            public String getListimg() {
                return listimg;
            }

            public void setListimg(String listimg) {
                this.listimg = listimg;
            }

            public String getAmount_read() {
                return amount_read;
            }

            public void setAmount_read(String amount_read) {
                this.amount_read = amount_read;
            }

            public String getBeantotal() {
                return beantotal;
            }

            public void setBeantotal(String beantotal) {
                this.beantotal = beantotal;
            }

            public String getCreatedate() {
                return createdate;
            }

            public void setCreatedate(String createdate) {
                this.createdate = createdate;
            }

            public String getIfing() {
                return ifing;
            }

            public void setIfing(String ifing) {
                this.ifing = ifing;
            }

            public String getNewsid() {
                return newsid;
            }

            public void setNewsid(String newsid) {
                this.newsid = newsid;
            }

            public String getAmount_share() {
                return amount_share;
            }

            public void setAmount_share(String amount_share) {
                this.amount_share = amount_share;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
