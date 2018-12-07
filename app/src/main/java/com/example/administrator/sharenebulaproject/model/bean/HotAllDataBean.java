package com.example.administrator.sharenebulaproject.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/8/13.
 * HotAllDataBean  首页数据集
 */

public class HotAllDataBean {


    /**
     * message :
     * result : {"RootPath":"http://xfx.027perfect.com/","banner":[{"content":"","createdate":"","infotype":"1","intamount":"","jianjie":"","numamount":"","photo":"./upload/2018/08/12/bd2d0c6bd713e20a2e21b4e6264c2c3e.jpg","textInfoid":"4","title":"1111111"},{"content":"","createdate":"","infotype":"1","intamount":"","jianjie":"","numamount":"","photo":"./upload/2018/08/12/77e566256dce4659233352f78d947360.jpg","textInfoid":"5","title":"22"}],"ifhavemessage":"1","news":[{"amount_read":"0","amount_share":"0","createdate":"2018-08-13 10:25:05","ifcanmoney":"0","listimg":"./upload/2018/08/13/50d6ade499251586988640c6cd0e7db2.jpg,./upload/2018/08/13/ac175cacab2166a0d5cdd140b5ee251d.jpg,./upload/2018/08/13/ac175cacab2166a0d5cdd140b5ee251d.jpg","newsid":"8","newstype":"2","starbean":"333","title":"文章8","viewtype":"1"},{"amount_read":"0","amount_share":"0","createdate":"2018-08-13 10:25:05","ifcanmoney":"1","listimg":"./upload/2018/08/13/50d6ade499251586988640c6cd0e7db2.jpg,./upload/2018/08/13/ac175cacab2166a0d5cdd140b5ee251d.jpg,./upload/2018/08/13/ac175cacab2166a0d5cdd140b5ee251d.jpg","newsid":"9","newstype":"2","starbean":"333","title":"文章9","viewtype":"2"},{"amount_read":"0","amount_share":"0","createdate":"2018-08-13 10:25:05","ifcanmoney":"1","listimg":"./upload/2018/08/13/50d6ade499251586988640c6cd0e7db2.jpg,./upload/2018/08/13/ac175cacab2166a0d5cdd140b5ee251d.jpg,./upload/2018/08/13/ac175cacab2166a0d5cdd140b5ee251d.jpg","newsid":"11","newstype":"2","starbean":"333","title":"文章11","viewtype":"1"}],"newversion":[],"totaluser_online":"1076","totaluser_regist":"10078"}
     * status : 1
     */

    private String message;
    private Result result;
    private int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class Result {

        /**
         * RootPath : http://xfx.027perfect.com/
         * banner : [{"content":"","createdate":"","infotype":"1","intamount":"","jianjie":"","numamount":"","photo":"./upload/2018/08/12/bd2d0c6bd713e20a2e21b4e6264c2c3e.jpg","textInfoid":"4","title":"1111111"},{"content":"","createdate":"","infotype":"1","intamount":"","jianjie":"","numamount":"","photo":"./upload/2018/08/12/77e566256dce4659233352f78d947360.jpg","textInfoid":"5","title":"22"}]
         * ifhavemessage : 1
         * news : [{"amount_read":"0","amount_share":"0","createdate":"2018-08-13 10:25:05","ifcanmoney":"0","listimg":"./upload/2018/08/13/50d6ade499251586988640c6cd0e7db2.jpg,./upload/2018/08/13/ac175cacab2166a0d5cdd140b5ee251d.jpg,./upload/2018/08/13/ac175cacab2166a0d5cdd140b5ee251d.jpg","newsid":"8","newstype":"2","starbean":"333","title":"文章8","viewtype":"1"},{"amount_read":"0","amount_share":"0","createdate":"2018-08-13 10:25:05","ifcanmoney":"1","listimg":"./upload/2018/08/13/50d6ade499251586988640c6cd0e7db2.jpg,./upload/2018/08/13/ac175cacab2166a0d5cdd140b5ee251d.jpg,./upload/2018/08/13/ac175cacab2166a0d5cdd140b5ee251d.jpg","newsid":"9","newstype":"2","starbean":"333","title":"文章9","viewtype":"2"},{"amount_read":"0","amount_share":"0","createdate":"2018-08-13 10:25:05","ifcanmoney":"1","listimg":"./upload/2018/08/13/50d6ade499251586988640c6cd0e7db2.jpg,./upload/2018/08/13/ac175cacab2166a0d5cdd140b5ee251d.jpg,./upload/2018/08/13/ac175cacab2166a0d5cdd140b5ee251d.jpg","newsid":"11","newstype":"2","starbean":"333","title":"文章11","viewtype":"1"}]
         * newversion : []
         * totaluser_online : 1076
         * totaluser_regist : 10078
         */

        private int allowlogin;
        private String RootPath;
        private String ifhavemessage;
        private String totaluser_online;
        private String totaluser_regist;
        private List<BannerBean> banner;
        private List<newsBean> news;
        private String newversion;
        private String ifmust;
        private List<topNews> topnews;

        public List<topNews> getTopnews() {
            return topnews;
        }

        public void setTopnews(List<topNews> topnews) {
            this.topnews = topnews;
        }

        public String getIfmust() {
            return ifmust;
        }

        public void setIfmust(String ifmust) {
            this.ifmust = ifmust;
        }

        public int getAllowlogin() {
            return allowlogin;
        }

        public void setAllowlogin(int allowlogin) {
            this.allowlogin = allowlogin;
        }

        public String getRootPath() {
            return RootPath;
        }

        public void setRootPath(String RootPath) {
            this.RootPath = RootPath;
        }

        public String getIfhavemessage() {
            return ifhavemessage;
        }

        public void setIfhavemessage(String ifhavemessage) {
            this.ifhavemessage = ifhavemessage;
        }

        public String getTotaluser_online() {
            return totaluser_online;
        }

        public void setTotaluser_online(String totaluser_online) {
            this.totaluser_online = totaluser_online;
        }

        public String getTotaluser_regist() {
            return totaluser_regist;
        }

        public void setTotaluser_regist(String totaluser_regist) {
            this.totaluser_regist = totaluser_regist;
        }

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public List<newsBean> getNews() {
            return news;
        }

        public void setNews(List<newsBean> news) {
            this.news = news;
        }

        public String getNewversion() {
            return newversion;
        }

        public void setNewversion(String newversion) {
            this.newversion = newversion;
        }

        public static class BannerBean {
            /**
             * content :
             * createdate :
             * infotype : 1
             * intamount :
             * jianjie :
             * numamount :
             * photo : ./upload/2018/08/12/bd2d0c6bd713e20a2e21b4e6264c2c3e.jpg
             * textInfoid : 4
             * title : 1111111
             */

            private String content;
            private String createdate;
            private String infotype;
            private String intamount;
            private String jianjie;
            private String numamount;
            private String photo;
            private String textInfoid;
            private String title;

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

            public String getInfotype() {
                return infotype;
            }

            public void setInfotype(String infotype) {
                this.infotype = infotype;
            }

            public String getIntamount() {
                return intamount;
            }

            public void setIntamount(String intamount) {
                this.intamount = intamount;
            }

            public String getJianjie() {
                return jianjie;
            }

            public void setJianjie(String jianjie) {
                this.jianjie = jianjie;
            }

            public String getNumamount() {
                return numamount;
            }

            public void setNumamount(String numamount) {
                this.numamount = numamount;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public String getTextInfoid() {
                return textInfoid;
            }

            public void setTextInfoid(String textInfoid) {
                this.textInfoid = textInfoid;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }

        public static class topNews{

            private int amount_read;
            private int amount_share;
            private String createdate;
            private int ifcanmoney;
            private String listimg;
            private int newsid;
            private String newstype;
            private int starbean;
            private String title;
            private int viewtype;
            private boolean status;

            public int getAmount_read() {
                return amount_read;
            }

            public void setAmount_read(int amount_read) {
                this.amount_read = amount_read;
            }

            public int getAmount_share() {
                return amount_share;
            }

            public void setAmount_share(int amount_share) {
                this.amount_share = amount_share;
            }

            public String getCreatedate() {
                return createdate;
            }

            public void setCreatedate(String createdate) {
                this.createdate = createdate;
            }

            public int getIfcanmoney() {
                return ifcanmoney;
            }

            public void setIfcanmoney(int ifcanmoney) {
                this.ifcanmoney = ifcanmoney;
            }

            public String getListimg() {
                return listimg;
            }

            public void setListimg(String listimg) {
                this.listimg = listimg;
            }

            public int getNewsid() {
                return newsid;
            }

            public void setNewsid(int newsid) {
                this.newsid = newsid;
            }

            public String getNewstype() {
                return newstype;
            }

            public void setNewstype(String newstype) {
                this.newstype = newstype;
            }

            public int getStarbean() {
                return starbean;
            }

            public void setStarbean(int starbean) {
                this.starbean = starbean;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getViewtype() {
                return viewtype;
            }

            public void setViewtype(int viewtype) {
                this.viewtype = viewtype;
            }

            public boolean isStatus() {
                return status;
            }

            public void setStatus(boolean status) {
                this.status = status;
            }
        }

        public static class newsBean {

            private int amount_read;
            private int amount_share;
            private String createdate;
            private int ifcanmoney;
            private String listimg;
            private int newsid;
            private String newstype;
            private String ordernum;
            private int starbean;
            private String title;
            private int viewtype;
            private int type;
            private String content;
            private String source;

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getOrdernum() {
                return ordernum;
            }

            public void setOrdernum(String ordernum) {
                this.ordernum = ordernum;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getAmount_read() {
                return amount_read;
            }

            public void setAmount_read(int amount_read) {
                this.amount_read = amount_read;
            }

            public int getAmount_share() {
                return amount_share;
            }

            public void setAmount_share(int amount_share) {
                this.amount_share = amount_share;
            }

            public String getCreatedate() {
                return createdate;
            }

            public void setCreatedate(String createdate) {
                this.createdate = createdate;
            }

            public int getIfcanmoney() {
                return ifcanmoney;
            }

            public void setIfcanmoney(int ifcanmoney) {
                this.ifcanmoney = ifcanmoney;
            }

            public String getListimg() {
                return listimg;
            }

            public void setListimg(String listimg) {
                this.listimg = listimg;
            }

            public int getNewsid() {
                return newsid;
            }

            public void setNewsid(int newsid) {
                this.newsid = newsid;
            }

            public String getNewstype() {
                return newstype;
            }

            public void setNewstype(String newstype) {
                this.newstype = newstype;
            }

            public int getStarbean() {
                return starbean;
            }

            public void setStarbean(int starbean) {
                this.starbean = starbean;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getViewtype() {
                return viewtype;
            }

            public void setViewtype(int viewtype) {
                this.viewtype = viewtype;
            }
        }
    }
}
