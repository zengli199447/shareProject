package com.example.administrator.sharenebulaproject.model.bean;

/**
 * Created by Administrator on 2018/8/30.
 * 支付宝Pay
 */

public class AlPayNetBean {

    private String message;
    private PrepayinfoBean prepayinfo;
    private String result;
    private int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PrepayinfoBean getPrepayinfo() {
        return prepayinfo;
    }

    public void setPrepayinfo(PrepayinfoBean prepayinfo) {
        this.prepayinfo = prepayinfo;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class PrepayinfoBean {

        private String OrderCode;
        private String partner;
        private String private_key;
        private String seller;
        private String totalmoney;
        private String url_notify;

        public String getOrderCode() {
            return OrderCode;
        }

        public void setOrderCode(String OrderCode) {
            this.OrderCode = OrderCode;
        }

        public String getPartner() {
            return partner;
        }

        public void setPartner(String partner) {
            this.partner = partner;
        }

        public String getPrivate_key() {
            return private_key;
        }

        public void setPrivate_key(String private_key) {
            this.private_key = private_key;
        }

        public String getSeller() {
            return seller;
        }

        public void setSeller(String seller) {
            this.seller = seller;
        }

        public String getTotalmoney() {
            return totalmoney;
        }

        public void setTotalmoney(String totalmoney) {
            this.totalmoney = totalmoney;
        }

        public String getUrl_notify() {
            return url_notify;
        }

        public void setUrl_notify(String url_notify) {
            this.url_notify = url_notify;
        }
    }
}
