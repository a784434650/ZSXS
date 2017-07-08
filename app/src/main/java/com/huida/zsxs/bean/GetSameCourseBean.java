package com.huida.zsxs.bean;

import java.util.List;

/**
 * Created by xiaojiu on 2017/7/6.
 */

public class GetSameCourseBean {

    /**
     * kc_types : 3
     * sameCourse : [{"kc_id":"108169","title":"英语六级听力的五大诀窍","img":"","info":"专业术语之类难词、生僻词对于绝大部分同学来说不需要掌握，因此练习时可以忽略掉。而如果选择的材料中这类...","money":"5","hot":812,"keshi":0,"teacher":"库塔哇"},{"kc_id":"117519","title":"备战英语六级，听力从固定搭配开启","img":"http://www.chinaplat.com/CourseImg/IMG-20150701/20150701163921022102.png","info":"备考大学英语六级听力，除了掌握发音现象、记忆场景词汇，还有必要识别一些固定搭配的含义，这些固定搭配涉...","money":"0","hot":151,"keshi":0,"teacher":"思"},{"kc_id":"108162","title":"英语四级翻译技巧","img":"","info":"从中国人进行的翻译定位上看，无论是英译汉还是汉译英，其根本问题都在译者的英语水平或造诣上。英译汉的题...","money":"5","hot":856,"keshi":0,"teacher":"库塔哇"},{"kc_id":"116889","title":"2015年6月英语四级答案完整版","img":"","info":"2015年6月英语四级答案完整版，赶紧对一对，看看你能考多少，寒冬十二月，你与四级是否还有约！...","money":"0","hot":129,"keshi":0,"teacher":"思"},{"kc_id":"119226","title":"2015年12月英语六级翻译预测：传承中国古典文化","img":"http://www.chinaplat.com/CourseImg/IMG-20151210/20151210135978767876.png","info":"2015年12月的英语四六级考试越来越近了，本文提供了翻译预测题和答案供考生参考和练习，希望对考生有...","money":"0","hot":206,"keshi":0,"teacher":"halen"},{"kc_id":"119162","title":"英语四级听力满分兵法","img":"http://www.chinaplat.com/CourseImg/IMG-20151202/20151202110713671367.jpg","info":"听力部分历来是中国考生的薄弱环节，也是考生最紧张、最容易丢分的环节，如何在最后有限的时间内提高听力成...","money":"0","hot":829,"keshi":0,"teacher":"追梦蝶"}]
     */

    public String kc_types;
    public List<SameCourseBean> sameCourse;

    public static class SameCourseBean {
        /**
         * kc_id : 108169
         * title : 英语六级听力的五大诀窍
         * img : 
         * info : 专业术语之类难词、生僻词对于绝大部分同学来说不需要掌握，因此练习时可以忽略掉。而如果选择的材料中这类...
         * money : 5
         * hot : 812
         * keshi : 0
         * teacher : 库塔哇
         */

        public String kc_id;
        public String title;
        public String img;
        public String info;
        public String money;
        public int hot;
        public int keshi;
        public String teacher;
        
    }
}
