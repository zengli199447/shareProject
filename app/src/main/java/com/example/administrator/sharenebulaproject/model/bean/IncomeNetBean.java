package com.example.administrator.sharenebulaproject.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/8/23.
 */

public class IncomeNetBean {

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

        private DayChangeBean day_change;
        private String moneyin_all;
        private MonthChangeBean month_change;
        private List<MoneyindetailBean> moneyindetail;
        private String month_current_total;
        private String today_total;
        private int is_inmoney;

        public int getIs_inmoney() {
            return is_inmoney;
        }

        public void setIs_inmoney(int is_inmoney) {
            this.is_inmoney = is_inmoney;
        }

        public String getMonth_current_total() {
            return month_current_total;
        }

        public void setMonth_current_total(String month_current_total) {
            this.month_current_total = month_current_total;
        }

        public String getToday_total() {
            return today_total;
        }

        public void setToday_total(String today_total) {
            this.today_total = today_total;
        }

        public DayChangeBean getDay_change() {
            return day_change;
        }

        public void setDay_change(DayChangeBean day_change) {
            this.day_change = day_change;
        }

        public String getMoneyin_all() {
            return moneyin_all;
        }

        public void setMoneyin_all(String moneyin_all) {
            this.moneyin_all = moneyin_all;
        }

        public MonthChangeBean getMonth_change() {
            return month_change;
        }

        public void setMonth_change(MonthChangeBean month_change) {
            this.month_change = month_change;
        }

        public List<MoneyindetailBean> getMoneyindetail() {
            return moneyindetail;
        }

        public void setMoneyindetail(List<MoneyindetailBean> moneyindetail) {
            this.moneyindetail = moneyindetail;
        }

        public static class DayChangeBean {

            private int key;
            private String value;

            public int getKey() {
                return key;
            }

            public void setKey(int key) {
                this.key = key;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public static class MonthChangeBean {

            private int key;
            private String value;

            public int getKey() {
                return key;
            }

            public void setKey(int key) {
                this.key = key;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public static class MoneyindetailBean {

            private String moneyintypeid;
            private String optmoneytotal;
            private String title;
            private List<DetailBean> detail;

            public String getMoneyintypeid() {
                return moneyintypeid;
            }

            public void setMoneyintypeid(String moneyintypeid) {
                this.moneyintypeid = moneyintypeid;
            }

            public String getOptmoneytotal() {
                return optmoneytotal;
            }

            public void setOptmoneytotal(String optmoneytotal) {
                this.optmoneytotal = optmoneytotal;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public List<DetailBean> getDetail() {
                return detail;
            }

            public void setDetail(List<DetailBean> detail) {
                this.detail = detail;
            }

            public static class DetailBean {

                private String createdate;
                private String optmoney;
                private String remark;

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

                public String getRemark() {
                    return remark;
                }

                public void setRemark(String remark) {
                    this.remark = remark;
                }
            }
        }
    }
}
