package com.example.administrator.sharenebulaproject.model.bean;

/**
 * Created by Administrator on 2018/8/25.
 * 文章详情
 */

public class NewContentNetBean {

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

        private DetailBean detail;

        public DetailBean getDetail() {
            return detail;
        }

        public void setDetail(DetailBean detail) {
            this.detail = detail;
        }

        public static class DetailBean {

            private String amount_read;
            private String amount_share;
            private String content;
            private String createdate;
            private String ifcanmoney;
            private String iftop;
            private String listimg;
            private String newsid;
            private String newstype;
            private String ordernum;
            private String starbean;
            private String state;
            private String state_check;
            private String title;
            private String viewtype;

            public String getAmount_read() {
                return amount_read;
            }

            public void setAmount_read(String amount_read) {
                this.amount_read = amount_read;
            }

            public String getAmount_share() {
                return amount_share;
            }

            public void setAmount_share(String amount_share) {
                this.amount_share = amount_share;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getCreatedate() {
                return createdate;
            }

            public void setCreatedate(String createdate) {
                this.createdate = createdate;
            }

            public String getIfcanmoney() {
                return ifcanmoney;
            }

            public void setIfcanmoney(String ifcanmoney) {
                this.ifcanmoney = ifcanmoney;
            }

            public String getIftop() {
                return iftop;
            }

            public void setIftop(String iftop) {
                this.iftop = iftop;
            }

            public String getListimg() {
                return listimg;
            }

            public void setListimg(String listimg) {
                this.listimg = listimg;
            }

            public String getNewsid() {
                return newsid;
            }

            public void setNewsid(String newsid) {
                this.newsid = newsid;
            }

            public String getNewstype() {
                return newstype;
            }

            public void setNewstype(String newstype) {
                this.newstype = newstype;
            }

            public String getOrdernum() {
                return ordernum;
            }

            public void setOrdernum(String ordernum) {
                this.ordernum = ordernum;
            }

            public String getStarbean() {
                return starbean;
            }

            public void setStarbean(String starbean) {
                this.starbean = starbean;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getState_check() {
                return state_check;
            }

            public void setState_check(String state_check) {
                this.state_check = state_check;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getViewtype() {
                return viewtype;
            }

            public void setViewtype(String viewtype) {
                this.viewtype = viewtype;
            }
        }
    }
}
