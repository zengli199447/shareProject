package com.example.administrator.sharenebulaproject.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/8/21.
 * 会员升级
 */

public class UpgradeNetBean {

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

        private ConfigBean config;
        private List<LevelBean> level;

        public ConfigBean getConfig() {
            return config;
        }

        public void setConfig(ConfigBean config) {
            this.config = config;
        }

        public List<LevelBean> getLevel() {
            return level;
        }

        public void setLevel(List<LevelBean> level) {
            this.level = level;
        }

        public static class ConfigBean {

            private String photo;
            private String content;

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }

        public static class LevelBean {

            private String levelconfigid;
            private String levelname;
            private String icon;
            private String levelprice;
            private String btntxt;
            private String ifcanclick;

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

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getLevelprice() {
                return levelprice;
            }

            public void setLevelprice(String levelprice) {
                this.levelprice = levelprice;
            }

            public String getBtntxt() {
                return btntxt;
            }

            public void setBtntxt(String btntxt) {
                this.btntxt = btntxt;
            }

            public String getIfcanclick() {
                return ifcanclick;
            }

            public void setIfcanclick(String ifcanclick) {
                this.ifcanclick = ifcanclick;
            }
        }
    }
}
