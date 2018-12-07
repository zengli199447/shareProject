package com.example.administrator.sharenebulaproject.model.bean;

/**
 * Created by Administrator on 2018/9/5.
 * 邀请海报信息
 */

public class PosterGetNetBean {

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

        private String invitationcode;
        private PosterBean poster;
        private String ewm_url;

        public String getInvitationcode() {
            return invitationcode;
        }

        public void setInvitationcode(String invitationcode) {
            this.invitationcode = invitationcode;
        }

        public PosterBean getPoster() {
            return poster;
        }

        public void setPoster(PosterBean poster) {
            this.poster = poster;
        }

        public String getEwm_url() {
            return ewm_url;
        }

        public void setEwm_url(String ewm_url) {
            this.ewm_url = ewm_url;
        }

        public static class PosterBean {

            private String posterid;
            private String photo;
            private String x_ewm;
            private String y_ewm;
            private String x_yqm;
            private String y_yqm;

            public String getPosterid() {
                return posterid;
            }

            public void setPosterid(String posterid) {
                this.posterid = posterid;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public String getX_ewm() {
                return x_ewm;
            }

            public void setX_ewm(String x_ewm) {
                this.x_ewm = x_ewm;
            }

            public String getY_ewm() {
                return y_ewm;
            }

            public void setY_ewm(String y_ewm) {
                this.y_ewm = y_ewm;
            }

            public String getX_yqm() {
                return x_yqm;
            }

            public void setX_yqm(String x_yqm) {
                this.x_yqm = x_yqm;
            }

            public String getY_yqm() {
                return y_yqm;
            }

            public void setY_yqm(String y_yqm) {
                this.y_yqm = y_yqm;
            }
        }
    }
}
