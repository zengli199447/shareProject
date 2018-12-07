package com.example.administrator.sharenebulaproject.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/9/5.
 * 收益明细
 */

public class MoneyAboutNetBean {

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
        private List<MoneyinBean> moneyin;

        public List<MoneyinBean> getMoneyin() {
            return moneyin;
        }

        public void setMoneyin(List<MoneyinBean> moneyin) {
            this.moneyin = moneyin;
        }

        public static class MoneyinBean {

            private String remark;
            private String createdate;
            private String optmoney;

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getCreatedate() {
                return createdate;
            }

            public void setCreatedate(String createdate) {
                this.createdate = createdate;
            }

            public String getOptmoney() {
                return optmoney;
            }

            public void setOptmoney(String optmoney) {
                this.optmoney = optmoney;
            }
        }
    }
}
