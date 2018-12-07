package com.example.administrator.sharenebulaproject.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/8/17.
 */

public class SettlementAccountNetBean {


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
        private List<MoneyaccountBean> moneyaccount;

        public List<MoneyaccountBean> getMoneyaccount() {
            return moneyaccount;
        }

        public void setMoneyaccount(List<MoneyaccountBean> moneyaccount) {
            this.moneyaccount = moneyaccount;
        }

        public static class MoneyaccountBean {
            private String moneyaccountid;
            private String userid;
            private String account_type;
            private String account_main;
            private String account_code;
            private String account_bank;

            public String getMoneyaccountid() {
                return moneyaccountid;
            }

            public void setMoneyaccountid(String moneyaccountid) {
                this.moneyaccountid = moneyaccountid;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public String getAccount_type() {
                return account_type;
            }

            public void setAccount_type(String account_type) {
                this.account_type = account_type;
            }

            public String getAccount_main() {
                return account_main;
            }

            public void setAccount_main(String account_main) {
                this.account_main = account_main;
            }

            public String getAccount_code() {
                return account_code;
            }

            public void setAccount_code(String account_code) {
                this.account_code = account_code;
            }

            public String getAccount_bank() {
                return account_bank;
            }

            public void setAccount_bank(String account_bank) {
                this.account_bank = account_bank;
            }
        }
    }
}
