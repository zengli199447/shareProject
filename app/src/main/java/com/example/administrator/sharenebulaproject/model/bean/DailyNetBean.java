package com.example.administrator.sharenebulaproject.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/8/24.
 */

public class DailyNetBean {

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
        private List<BannerBean> banner;
        private List<NewsBean> news;

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public List<NewsBean> getNews() {
            return news;
        }

        public void setNews(List<NewsBean> news) {
            this.news = news;
        }

        public static class BannerBean {

            private String amount_read;
            private String amount_share;
            private String createdate;
            private String ifcanmoney;
            private String listimg;
            private String newsid;
            private String newstype;
            private String starbean;
            private String title;
            private String viewtype;
            private String topimg;

            public String getTopimg() {
                return topimg;
            }

            public void setTopimg(String topimg) {
                this.topimg = topimg;
            }

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

            public String getStarbean() {
                return starbean;
            }

            public void setStarbean(String starbean) {
                this.starbean = starbean;
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

        public static class NewsBean {

            private String amount_read;
            private String amount_share;
            private String createdate;
            private String ifcanmoney;
            private String listimg;
            private String newsid;
            private String newstype;
            private String starbean;
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

            public String getStarbean() {
                return starbean;
            }

            public void setStarbean(String starbean) {
                this.starbean = starbean;
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
