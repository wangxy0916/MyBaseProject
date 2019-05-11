package com.smartwang.charge.entity;

public class HomeBean extends BaseBean {


    /**
     * code : 1
     * msg : ok
     * data : {"page_name":"关于我们","content":"1.介绍\n2.客服联系方式\n3.官网地址"}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * page_name : 关于我们
         * content : 1.介绍
         2.客服联系方式
         3.官网地址
         */

        private String page_name;
        private String content;

        public String getPage_name() {
            return page_name;
        }

        public void setPage_name(String page_name) {
            this.page_name = page_name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
