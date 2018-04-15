package com.apicloud.moduleVideo.bean;

import java.util.List;

/**
 * Created by tx on 2018/4/15.
 */

public class DataBean {
    private List<Data> data;
    private int code;
    private String message;


    public class Data{
        private String url;
        private int type;
        private String title;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "url='" + url + '\'' +
                    ", type=" + type +
                    '}';
        }
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
