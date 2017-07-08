package com.huida.zsxs.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xiaojiu on 2017/7/6.
 */

public class GetPLBean implements Serializable{

    /**
     * page_all : 1
     * page_now : 1
     * pllist : [{"id":"1505","userimg":"http://www.chinaplat.com/user/UserHeadImg/163004.jpg","nickname":"毒药","pl_time":"22小时前","pc_content":"c语言","zan":"0","zan_flag":"0"}]
     */

    public int page_all;
    public int page_now;
    public List<PllistBean> pllist;

    public static class PllistBean implements Parcelable{
        /**
         * id : 1505
         * userimg : http://www.chinaplat.com/user/UserHeadImg/163004.jpg
         * nickname : 毒药
         * pl_time : 22小时前
         * pc_content : c语言
         * zan : 0
         * zan_flag : 0
         */

        public String id;
        public String userimg;
        public String nickname;
        public String pl_time;
        public String pc_content;
        public String zan;
        public String zan_flag;
        public PllistBean(){

        }

        protected PllistBean(Parcel in) {
            id = in.readString();
            userimg = in.readString();
            nickname = in.readString();
            pl_time = in.readString();
            pc_content = in.readString();
            zan = in.readString();
            zan_flag = in.readString();
        }

        public static final Creator<PllistBean> CREATOR = new Creator<PllistBean>() {
            @Override
            public PllistBean createFromParcel(Parcel in) {
                return new PllistBean(in);
            }

            @Override
            public PllistBean[] newArray(int size) {
                return new PllistBean[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(userimg);
            dest.writeString(nickname);
            dest.writeString(pl_time);
            dest.writeString(pc_content);
            dest.writeString(zan);
            dest.writeString(zan_flag);
        }
    }
}
