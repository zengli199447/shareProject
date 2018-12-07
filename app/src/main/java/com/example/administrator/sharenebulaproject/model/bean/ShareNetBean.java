package com.example.administrator.sharenebulaproject.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/8/22.
 */

public class ShareNetBean {

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

        private String total;
        private List<DetaillistBean> detaillist;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public List<DetaillistBean> getDetaillist() {
            return detaillist;
        }

        public void setDetaillist(List<DetaillistBean> detaillist) {
            this.detaillist = detaillist;
        }

        public static class DetaillistBean {

            private String levelconfigid;
            private String levelname;
            private String fansamount;

            public String getLevelconfigid() {
                return levelconfigid;
            }

            public void setLevelconfigid(String levelconfigid) {
                this.levelconfigid = levelconfigid;
            }

            public String getLevelname() {
                return levelname;
            }

            public void setLevelname(String levelname) {
                this.levelname = levelname;
            }

            public String getFansamount() {
                return fansamount;
            }

            public void setFansamount(String fansamount) {
                this.fansamount = fansamount;
            }
        }
    }
}
