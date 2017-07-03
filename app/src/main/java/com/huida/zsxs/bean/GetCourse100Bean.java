package com.huida.zsxs.bean;

import java.util.List;

/**
 * Created by xiaojiu on 2017/6/24.
 */

public class GetCourse100Bean {

    public List<CourseBean> Course;

    public static class CourseBean {
        /**
         * kc_id : 140478
         * title : 响应式网页设计
         * img : http://www.chinaplat.com/CourseImg/IMG-20161224/20161224095173197319.png
         * info : 
         * kc_money : 100
         */

        public String kc_id;
        public String title;
        public String img;
        public String info;
        public int kc_money;
    }
}
