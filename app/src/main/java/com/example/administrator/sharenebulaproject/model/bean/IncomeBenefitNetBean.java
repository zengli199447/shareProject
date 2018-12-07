package com.example.administrator.sharenebulaproject.model.bean;

/**
 * Created by Administrator on 2018/8/24.
 * 收入权益
 */

public class IncomeBenefitNetBean {

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

        private String rights1;
        private String rights2;
        private String rights3;
        private String rights4;
        private String rights5;
        private String rights6;

        public String getRights1() {
            return rights1;
        }

        public void setRights1(String rights1) {
            this.rights1 = rights1;
        }

        public String getRights2() {
            return rights2;
        }

        public void setRights2(String rights2) {
            this.rights2 = rights2;
        }

        public String getRights3() {
            return rights3;
        }

        public void setRights3(String rights3) {
            this.rights3 = rights3;
        }

        public String getRights4() {
            return rights4;
        }

        public void setRights4(String rights4) {
            this.rights4 = rights4;
        }

        public String getRights5() {
            return rights5;
        }

        public void setRights5(String rights5) {
            this.rights5 = rights5;
        }

        public String getRights6() {
            return rights6;
        }

        public void setRights6(String rights6) {
            this.rights6 = rights6;
        }
    }
}
