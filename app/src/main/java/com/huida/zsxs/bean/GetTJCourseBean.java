package com.huida.zsxs.bean;

import java.util.List;

/**
 * Created by xiaojiu on 2017/6/24.
 */

public class GetTJCourseBean {

    /**
     * page_all : 17
     * page_now : 1
     * Course : [{"kc_id":"140725","title":"2017年导游考试【大纲分析】讲座","img":"http://www.chinaplat.com/CourseImg/IMG-20170509/20170509142596339633.png","info":"2017年导游考试【大纲分析】讲座","money":"2900","keshi":4,"hot":666},{"kc_id":"140729","title":"TTT-培训师的修炼","img":"http://www.chinaplat.com/CourseImg/IMG-20170517/20170517095147194719.jpg","info":"TTT-培训师的修炼","money":"9900","keshi":20,"hot":605},{"kc_id":"140657","title":"2017导游从业资格--全国导游基础知识（考点精讲）","img":"http://www.chinaplat.com/CourseImg/IMG-20170218/20170218110555975597.jpg","info":"2017导游从业资格--全国导游基础知识（考点精讲）","money":"10900","keshi":63,"hot":5520},{"kc_id":"140655","title":"2017导游从业资格--政策与法律法规（考点精讲）","img":"http://www.chinaplat.com/CourseImg/IMG-20170218/20170218091855445544.jpg","info":"2017导游从业资格--政策与法律法规（考点精讲）","money":"11900","keshi":61,"hot":4482},{"kc_id":"141139","title":"2017地方导游基础知识（习题提升）","img":"http://www.chinaplat.com/CourseImg/IMG-20170526/20170526161678437843.jpg","info":"2017地方导游基础知识（习题提升）","money":"13900","keshi":1,"hot":325},{"kc_id":"141138","title":"2017地方导游基础知识（考点精讲）","img":"http://www.chinaplat.com/CourseImg/IMG-20170526/20170526161650955095.jpg","info":"2017地方导游基础知识（考点精讲）","money":"12900","keshi":1,"hot":272},{"kc_id":"140767","title":"国家普通话水平测试过关速成训练","img":"http://www.chinaplat.com/CourseImg/IMG-20170515/20170515145758375837.png","info":"国家普通话水平测试过关速成训练","money":"5900","keshi":12,"hot":121},{"kc_id":"140660","title":"2017导游从业资格--导游业务（习题提升）","img":"http://www.chinaplat.com/CourseImg/IMG-20170218/20170218113481938193.jpg","info":"2017导游从业资格--导游业务（习题提升）","money":"11900","keshi":1,"hot":1475},{"kc_id":"140658","title":"2017导游从业资格--全国导游基础知识（习题冲刺）","img":"http://www.chinaplat.com/CourseImg/IMG-20170218/20170218110552435243.jpg","info":"2017导游从业资格--全国导游基础知识（习题冲刺）","money":"10900","keshi":51,"hot":1855},{"kc_id":"140656","title":"2017导游从业资格--政策与法律法规（习题冲刺）","img":"http://www.chinaplat.com/CourseImg/IMG-20170218/20170218093183718371.jpg","info":"2017导游从业资格--政策与法律法规（习题冲刺）","money":"12900","keshi":43,"hot":1681}]
     */

    public String page_all;
    public String page_now;
    public List<CourseBean> Course;

  
    public static class CourseBean {
        /**
         * kc_id : 140725
         * title : 2017年导游考试【大纲分析】讲座
         * img : http://www.chinaplat.com/CourseImg/IMG-20170509/20170509142596339633.png
         * info : 2017年导游考试【大纲分析】讲座
         * money : 2900
         * keshi : 4
         * hot : 666
         */
        public String kc_id;
        public String title;
        public String img;
        public String info;
        public String money;
        public int keshi;
        public int hot;
    }
}
