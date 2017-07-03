package com.huida.zsxs.bean;

import java.util.List;

/**
 * Created by zhang on 2017/6/25.
 * 5.	获取课程列表信息
 */

public class GetCourseList {

    /**
     * page_all : 1
     * page_now : 1
     * Course : [{"kc_id":"119656","title":"高效能人士的七个习惯","img":"http://www.chinaplat.com/CourseImg/IMG-20160226/20160226174279067906.jpg","info":"企业领导人都知道：只有每一位员工都成为高效能人士，企业才会真正成为高效能企业。 这也同样适用于个人生...","money":"88","keshi":0,"hot":794,"teacher":"润德"},{"kc_id":"117860","title":"《赢在执行》全集","img":"http://www.chinaplat.com/CourseImg/IMG-20150716/20150716143582778277.jpg","info":"\u201c职场上的成功，需要我们通过执行去获得，我们不能做语言的巨人，行动的矮子，而应当不断提高自己的执行力...","money":"0","keshi":0,"hot":296,"teacher":"黄义珊"},{"kc_id":"109606","title":"《知道更要做到》","img":"http://www.chinaplat.com/CourseImg/IMG-20150506/20150506104264246424.jpg","info":"我相信，读完本书后，大家就会相信并努力践行\u201c知道，更要做到\u201d这一理念，就会懂得怎样寓智慧于行动，寓伟...","money":"100","keshi":0,"hot":265,"teacher":"小仕老师"},{"kc_id":"109409","title":"你在为谁工作","img":"http://www.chinaplat.com/CourseImg/IMG-20150426/20150426145897409740.jpg","info":"触动心底的反思 你在为谁工作\u201c我们到底是在为谁工作呢？\u201d如果不尽快弄清这个问题，不调整好自己的心态，...","money":"100","keshi":0,"hot":395,"teacher":"小仕老师"},{"kc_id":"109073","title":"《不要只做我告诉你的事》","img":"http://www.chinaplat.com/CourseImg/IMG-20150406/20150406173615281528.jpg","info":"无论你在哪里工作，无论你的老板是谁，管理层都始终期望你不要坐等指令，而要运用个人的最佳判断和努力，为...","money":"100","keshi":0,"hot":366,"teacher":"孩心"}]
     */

    private String page_all;
    private String page_now;
    private List<CourseBean> Course;

    public String getPage_all() {
        return page_all;
    }

    public void setPage_all(String page_all) {
        this.page_all = page_all;
    }

    public String getPage_now() {
        return page_now;
    }

    public void setPage_now(String page_now) {
        this.page_now = page_now;
    }

    public List<CourseBean> getCourse() {
        return Course;
    }

    public void setCourse(List<CourseBean> Course) {
        this.Course = Course;
    }

    public static class CourseBean {
        /**
         * kc_id : 119656
         * title : 高效能人士的七个习惯
         * img : http://www.chinaplat.com/CourseImg/IMG-20160226/20160226174279067906.jpg
         * info : 企业领导人都知道：只有每一位员工都成为高效能人士，企业才会真正成为高效能企业。 这也同样适用于个人生...
         * money : 88
         * keshi : 0
         * hot : 794
         * teacher : 润德
         */

        private String kc_id;
        private String title;
        private String img;
        private String info;
        private String money;
        private int keshi;
        private int hot;
        private String teacher;

        public String getKc_id() {
            return kc_id;
        }

        public void setKc_id(String kc_id) {
            this.kc_id = kc_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public int getKeshi() {
            return keshi;
        }

        public void setKeshi(int keshi) {
            this.keshi = keshi;
        }

        public int getHot() {
            return hot;
        }

        public void setHot(int hot) {
            this.hot = hot;
        }

        public String getTeacher() {
            return teacher;
        }

        public void setTeacher(String teacher) {
            this.teacher = teacher;
        }
    }
}
