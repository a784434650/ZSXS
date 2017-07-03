package com.huida.zsxs.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhang on 2017/6/25.
 */

public class CourseType implements Serializable  {
    /**
     * kc_types : 0
     * t_list : [{"id":"4","name":"考试"},{"id":"5","name":"工作"},{"id":"10","name":"生活"}]
     * 4.	获取课程分类信息
     */

    private String kc_types;
    private List<TListBean> t_list;

    public String getKc_types() {
        return kc_types;
    }

    public void setKc_types(String kc_types) {
        this.kc_types = kc_types;
    }

    public List<TListBean> getT_list() {
        return t_list;
    }

    public void setT_list(List<TListBean> t_list) {
        this.t_list = t_list;
    }

    public static class TListBean implements Serializable {
        /**
         * id : 4
         * name : 考试
         */

        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
