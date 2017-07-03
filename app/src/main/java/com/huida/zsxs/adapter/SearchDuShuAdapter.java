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

public class SearchDuShuAdapter extends BaseAdapter {
    private final Activity activity;
    private final List<GetSearchCourseBean.CourseListBean> list;
    private  String string = "";

    public SearchDuShuAdapter(Activity activity, List<GetSearchCourseBean.CourseListBean> list,String string){
        this.activity = activity;
        this.list = list;
        this.string = string;
    }
    public SearchDuShuAdapter(Activity activity, List<GetSearchCourseBean.CourseListBean> list){
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
            convertView = View.inflate(activity, R.layout.item_dushu_detail, null);
            holder.dushu_image = (ImageView) convertView.findViewById(R.id.dushu_image);
            holder.tv_item_title = (TextView)  convertView.findViewById(R.id.tv_item_title);
            holder.tv_article_item_info = (TextView) convertView.findViewById(R.id.tv_article_item_info);
            holder.tv_jifen = (TextView) convertView.findViewById(R.id.tv_jifen);
            holder.tv_dianjiliang = (TextView) convertView.findViewById(R.id.tv_dianjiliang);
            holder.xiazai_no_gouxuan = (ImageView) convertView.findViewById(R.id.xiazai_no_gouxuan);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        //数据填充
        GetSearchCourseBean.CourseListBean listBean = list.get(position);
        holder.tv_item_title.setText(FormatUtil.getSearchIndex(listBean.title,string));
        holder.tv_article_item_info.setText(listBean.info);
        holder.tv_jifen.setText(listBean.money+"");
        holder.tv_dianjiliang.setText(listBean.hot+"");
        //自动调整图片大小
        ImageOptions build = new ImageOptions.Builder().setImageScaleType(ImageView.ScaleType.FIT_XY).build();
        //获取imageView
        x.image().bind(holder.dushu_image,listBean.img,build);
        return convertView;
    }
    class ViewHolder {
        ImageView dushu_image;
        TextView tv_item_title;
        TextView tv_article_item_info;
        TextView tv_jifen;
        TextView tv_dianjiliang;
        ImageView xiazai_no_gouxuan;
    }
}
