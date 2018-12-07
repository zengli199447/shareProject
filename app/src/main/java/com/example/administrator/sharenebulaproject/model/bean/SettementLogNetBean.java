package com.example.administrator.sharenebulaproject.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/8/22.
 * 结算记录
 */

public class SettementLogNetBean {

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
            private String createdate;
            private String moneydetailid;
            private String optmoney;
            private String remark;

            public UsersBean(String createdate, String moneydetailid, String optmoney, String remark) {
                this.createdate = createdate;
                this.moneydetailid = moneydetailid;
                this.optmoney = optmoney;
                this.remark = remark;
            }

            public String getCreatedate() {
                return createdate;
            }

            public void setCreatedate(String createdate) {
                this.createdate = createdate;
            }

            public String getMoneydetailid() {
                return moneydetailid;
            }

            public void setMoneydetailid(String moneydetailid) {
                this.moneydetailid = moneydetailid;
            }

            public String getOptmoney() {
                return optmoney;
            }

            public void setOptmoney(String optmoney) {
                this.optmoney = optmoney;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }
        }
    }
}
