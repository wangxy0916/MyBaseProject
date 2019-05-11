package com.smartwang.charge.entity;

import java.util.List;

public class HierarchyBean extends BaseBean {


    /**
     * code : 12
     * msg : 请求成功
     * data : [{"time":"01:00","data":[{"name":"王"},{"name":"张"}]},{"time":"02:00","data":[{"name":"李"},{"name":"赵"},{"name":"李"}]},{"time":"03:00","data":[{"name":"周"},{"name":"马"}]}]
     */

    private int code;
    private String msg;
    private List<DataBeanX> data;

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

    public List<DataBeanX> getData() {
        return data;
    }

    public void setData(List<DataBeanX> data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * time : 01:00
         * data : [{"name":"王"},{"name":"张"}]
         */

        private String time;
        private List<DataBean> data;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * name : 王
             */

            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
