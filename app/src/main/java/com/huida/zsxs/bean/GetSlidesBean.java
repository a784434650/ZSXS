package com.huida.zsxs.bean;

import java.util.List;

/**
 * Created by xiaojiu on 2017/6/23.
 */

public class GetSlidesBean {

    public List<SlidesBean> Slides;

    public static class SlidesBean {
        /**
         * pic : http://www.chinaplat.com/imgzt/IMG-20170124/20170124140783108310.png
         * title : 名校课程
         * picURL : 112
         * pictype : app
         */

        public String pic;
        public String title;
        public String picURL;
        public String pictype;
    }
}
