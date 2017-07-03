package com.huida.zsxs.bean;

import java.util.List;

/**
 * Created by zhang on 2017/7/2.
 */

public class GetCourseInfo {
    /**
     * shoucang : 0
     * kc_img : http:
     * teacher : Kyrie
     * hot : 148
     * teacher_id : 12328
     * files : [{"files_id":"179729","files_title":"[第1节]C语言变量.","orderid":"1","shiting":"1","files_url":"http://121.22.11.84:8008/12328/A/20160709/2016070910183892818.mp3","size":""},{"files_id":"179730","files_title":"[第2节]C语言程序结构 二号解析.","orderid":"2","shiting":"0","files_url":"","size":"未知"},{"files_id":"179731","files_title":"[第3节]C语言程序结构.","orderid":"3","shiting":"0","files_url":"","size":"未知"},{"files_id":"179732","files_title":"[第4节]C语言的基本语法.","orderid":"4","shiting":"0","files_url":"","size":"未知"},{"files_id":"179733","files_title":"[第5节]C语言环境设置.","orderid":"5","shiting":"0","files_url":"","size":"未知"},{"files_id":"179734","files_title":"[第6节]if if else while的表现方式.","orderid":"6","shiting":"0","files_url":"","size":"未知"},{"files_id":"179735","files_title":"[第7节]常量和文字.","orderid":"7","shiting":"0","files_url":"","size":"未知"}]
     * keshi : 8
     */

    private String shoucang;
    private String kc_img;
    private String teacher;
    private int hot;
    private int teacher_id;
    private int keshi;
    private List<FilesBean> files;

    public String getShoucang() {
        return shoucang;
    }

    public void setShoucang(String shoucang) {
        this.shoucang = shoucang;
    }

    public String getKc_img() {
        return kc_img;
    }

    public void setKc_img(String kc_img) {
        this.kc_img = kc_img;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getHot() {
        return hot;
    }

    public void setHot(int hot) {
        this.hot = hot;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public int getKeshi() {
        return keshi;
    }

    public void setKeshi(int keshi) {
        this.keshi = keshi;
    }

    public List<FilesBean> getFiles() {
        return files;
    }

    public void setFiles(List<FilesBean> files) {
        this.files = files;
    }

    public static class FilesBean {
        /**
         * files_id : 179729
         * files_title : [第1节]C语言变量.
         * orderid : 1
         * shiting : 1
         * files_url : http://121.22.11.84:8008/12328/A/20160709/2016070910183892818.mp3
         * size :
         */

        private String files_id;
        private String files_title;
        private String orderid;
        private String shiting;
        private String files_url;
        private String size;

        public String getFiles_id() {
            return files_id;
        }

        public void setFiles_id(String files_id) {
            this.files_id = files_id;
        }

        public String getFiles_title() {
            return files_title;
        }

        public void setFiles_title(String files_title) {
            this.files_title = files_title;
        }

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getShiting() {
            return shiting;
        }

        public void setShiting(String shiting) {
            this.shiting = shiting;
        }

        public String getFiles_url() {
            return files_url;
        }

        public void setFiles_url(String files_url) {
            this.files_url = files_url;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }
    }
}
