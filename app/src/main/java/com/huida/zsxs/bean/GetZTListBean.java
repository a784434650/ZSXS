package com.huida.zsxs.bean;

import java.util.List;

/**
 * Created by xiaojiu on 2017/6/24.
 */

public class GetZTListBean {

    /**
     * page_all : 4
     * page_now : 1
     * types : 1
     * list : [{"imgURL":"http://www.chinaplat.com/imgzt/IMG-20170124/20170124140783108310.png","ztid":112},{"imgURL":"http://www.chinaplat.com/imgzt/IMG-20170517/20170517091695859585.jpg","ztid":110},{"imgURL":"http://www.chinaplat.com/imgzt/IMG-20170223/20170223114681028102.jpg","ztid":108},{"imgURL":"http://www.chinaplat.com/imgzt/IMG-20170223/20170223113815821582.jpg","ztid":107},{"imgURL":"http://www.chinaplat.com/imgzt/IMG-20170517/20170517092561946194.jpg","ztid":104},{"imgURL":"http://www.chinaplat.com/imgzt/IMG-20160216/20160216113634253425.png","ztid":39},{"imgURL":"http://www.chinaplat.com/imgzt/IMG-20161104/20161104095221012101.jpg","ztid":24},{"imgURL":"http://www.chinaplat.com/imgzt/IMG-20161007/20161007164324962496.jpg","ztid":109},{"imgURL":"http://www.chinaplat.com/imgzt/IMG-20170517/20170517091256145614.jpg","ztid":106},{"imgURL":"http://www.chinaplat.com/imgzt/IMG-20170517/20170517091091509150.png","ztid":103}]
     */

    public int page_all;
    public int page_now;
    public int types;
    public List<ListBean> list;
    
    public static class ListBean {
        /**
         * imgURL : http://www.chinaplat.com/imgzt/IMG-20170124/20170124140783108310.png
         * ztid : 112
         */

        public String imgURL;
        public int ztid;
        
    }
}
