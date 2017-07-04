package com.huida.zsxs.bean;

import java.util.List;

/**
 * Created by xiaojiu on 2017/7/4.
 */

public class ArticleDataBean {
    public String shoucang;
    public String buy;
    public String kc_id;
    public String kc_img;
    public String kc_content;
    public String kc_info;
    public String kc_money;
    public String kc_title;
    public String teacher;
    public int hot;
    public int teacher_id;
    public int keshi;
    public List<FilesBean> files;



    public static class FilesBean {
        /**
         * files_id : 196192
         * files_title : [第1节]《 响应式网页设计 》 (1).
         * orderid : 1
         * shiting : 1
         * files_url : http://121.22.11.84:8008/28994/V/20161224/2016122408552987847.mp4

         * size : 11.84M
         */

        public String files_id;
        public String files_title;
        public String orderid;
        public String shiting;
        public String files_url;
        public String size;

    }
}
