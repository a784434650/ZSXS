package com.huida.zsxs.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huida.zsxs.R;
import com.huida.zsxs.bean.GetSearchCourseBean;
import com.huida.zsxs.utils.FormatUtil;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by xiaojiu on 2017/7/2.
 */

public class SearchWenZhangAdapter extends BaseAdapter {
    private final Activity activity;
    private final List<GetSearchCourseBean.CourseListBean> list;
    private  String string = "";

    public SearchWenZhangAdapter(Activity activity, List<GetSearchCourseBean.CourseListBean> list,String string){
        this.activity = activity;
        this.list = list;
        this.string = string;
    }
    public SearchWenZhangAdapter(Activity activity, List<GetSearchCourseBean.CourseListBean> list){
        this.activity = activity;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
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
       ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(activity, R.layout.recent_article_item, null);
            holder.article_image = (ImageView) convertView.findViewById(R.id.article_image);
            holder.tv_recent_title = (TextView)  convertView.findViewById(R.id.tv_recent_title);
            holder.article_item_info_tv = (TextView) convertView.findViewById(R.id.article_item_info_tv);
            holder.tv_shangchuan_name = (TextView) convertView.findViewById(R.id.tv_shangchuan_name);
            holder.yuedu_tv = (TextView) convertView.findViewById(R.id.yuedu_tv);
            holder.xiazai_no_gouxuan = (ImageView) convertView.findViewById(R.id.xiazai_no_gouxuan);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        //数据填充
        GetSearchCourseBean.CourseListBean listBean = list.get(position);
        holder.tv_recent_title.setText(FormatUtil.getSearchIndex(listBean.title,string));
        holder.article_item_info_tv.setText(listBean.info);
        holder.tv_shangchuan_name.setText(listBean.teacher);
        holder.yuedu_tv.setText(listBean.hot+"");
        //自动调整图片大小
        ImageOptions build = new ImageOptions.Builder().setImageScaleType(ImageView.ScaleType.FIT_XY).build();
        //获取imageView
        x.image().bind(holder.article_image,listBean.img,build);
        return convertView;
    }
    class ViewHolder {
        ImageView article_image;
        TextView tv_recent_title;
        TextView article_item_info_tv;
        TextView tv_shangchuan_name;
        TextView yuedu_tv;
        ImageView xiazai_no_gouxuan;
    }
}
