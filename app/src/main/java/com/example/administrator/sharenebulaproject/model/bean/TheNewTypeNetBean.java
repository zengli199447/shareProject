package com.example.administrator.sharenebulaproject.model.bean;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/11/28.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class TheNewTypeNetBean {

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
        private List<SelectBean> select;
        private List<AllBean> all;

        public List<SelectBean> getSelect() {
            return select;
        }

        public void setSelect(List<SelectBean> select) {
            this.select = select;
        }

        public List<AllBean> getAll() {
            return all;
        }

        public void setAll(List<AllBean> all) {
            this.all = all;
        }

        public static class SelectBean {

            private String ucid;
            private String userid;
            private int catid;
            private String sort;
            private String catename;
            private String url;

            public String getUcid() {
                return ucid;
            }

            public void setUcid(String ucid) {
                this.ucid = ucid;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public int getCatid() {
                return catid;
            }

            public void setCatid(int catid) {
                this.catid = catid;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public String getCatename() {
                return catename;
            }

            public void setCatename(String catename) {
                this.catename = catename;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class AllBean {

            private int cateid;
            private String catename;
            private String url;
            private String iftop;
            private String listorder;

            public int getCateid() {
                return cateid;
            }

            public void setCateid(int cateid) {
                this.cateid = cateid;
            }

            public String getCatename() {
                return catename;
            }

            public void setCatename(String catename) {
                this.catename = catename;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getIftop() {
                return iftop;
            }

            public void setIftop(String iftop) {
                this.iftop = iftop;
            }

            public String getListorder() {
                return listorder;
            }

            public void setListorder(String listorder) {
                this.listorder = listorder;
            }
        }
    }
}
