package com.huida.zsxs.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huida.zsxs.R;
import com.huida.zsxs.bean.GetCourseList;

import org.xutils.x;

import java.util.List;

/**
 * Created by zhang on 2017/7/1.
 */

public class MyCourseAdapter extends BaseAdapter {
    public List<GetCourseList.CourseBean> course;
    public String kc_types;

    public List<GetCourseList.CourseBean> getCourse() {
        return course;
    }

    public void setCourse(List<GetCourseList.CourseBean> course) {
        this.course = course;
    }

    public String getKc_types() {
        return kc_types;
    }

    public void setKc_types(String kc_types) {
        this.kc_types = kc_types;
    }


    public int getCount() {
        return course.size();
    }


    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BookHolder holder;
        if (convertView == null) {
            holder = new BookHolder();
            convertView = View.inflate(parent.getContext(), R.layout.course_item, null);
            holder.iv_image = (ImageView) convertView.findViewById(R.id.iv_course_image);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_course_title);
            holder.tv_info = (TextView) convertView.findViewById(R.id.tv_course_info);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_course_time);
            holder.tv_money = (TextView) convertView.findViewById(R.id.tv_course_money);
            holder.tv_hot = (TextView) convertView.findViewById(R.id.tv_course_hot);
            convertView.setTag(holder);
        } else {
            holder = (BookHolder) convertView.getTag();
        }

        switch (kc_types){
            case "0":
                x.image().bind(holder.iv_image, course.get(position).getImg());
                holder.tv_time.setText(course.get(position).getKeshi() + "课时");
                break;
            case "1":
                x.image().bind(holder.iv_image, course.get(position).getImg());
                holder.tv_time.setText(course.get(position).getKeshi() + "课时");
                break;
            case "2":
                x.image().bind(holder.iv_image, course.get(position).getImg());
                holder.tv_time.setVisibility(View.GONE);
                break;
            case "3":
                holder.iv_image.setVisibility(View.GONE);
                holder.tv_time.setVisibility(View.GONE);
                break;
        }
        holder.tv_title.setText(course.get(position).getTitle());
        holder.tv_info.setText(course.get(position).getInfo());
        holder.tv_money.setText(course.get(position).getMoney());
        holder.tv_hot.setText(course.get(position).getHot() + "");
        return convertView;
    }



    public class BookHolder {
        ImageView iv_image;
        TextView tv_title;
        TextView tv_info;
        TextView tv_money;
        TextView tv_hot;
        TextView tv_time;
    }
}
