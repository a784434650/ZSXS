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

public class SearchShiPinAdapter extends BaseAdapter {
    private final Activity activity;
    private final List<GetSearchCourseBean.CourseListBean> list;
    private final String string;

    public SearchShiPinAdapter(Activity activity, List<GetSearchCourseBean.CourseListBean> list,String string){
        this.activity = activity;
        this.list = list;
        this.string = string;
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
        if (convertView==null){
            holder = new ViewHolder();
            convertView = View.inflate(activity, R.layout.more_tj_list_item_layout,null);
            holder.tv_item_more_dianjiliang = (TextView) convertView.findViewById(R.id.tv_item_more_dianjiliang);
            holder.tv_item_more_keshi = (TextView) convertView.findViewById(R.id.tv_item_more_keshi);
            holder.tv_item_more_tj = (TextView) convertView.findViewById(R.id.tv_item_more_tj);
            holder.tv_info_item_more_tj = (TextView) convertView.findViewById(R.id.tv_info_item_more_tj);
            holder.tv_item_more_jifen = (TextView) convertView.findViewById(R.id.tv_item_more_jifen);
            holder.iv_item_more_tj = (ImageView) convertView.findViewById(R.id.iv_item_more_tj);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        //数据填充
        GetSearchCourseBean.CourseListBean courseBean = list.get(position);
        holder.tv_item_more_tj.setText(FormatUtil.getSearchIndex(courseBean.title,string));
        holder.tv_info_item_more_tj.setText(courseBean.info);
        holder.tv_item_more_keshi.setText(courseBean.keshi+"课时");
        holder.tv_item_more_jifen.setText(courseBean.money+"");
        holder.tv_item_more_dianjiliang.setText(courseBean.hot+"");
        //自动调整图片大小
        ImageOptions build = new ImageOptions.Builder().setImageScaleType(ImageView.ScaleType.FIT_XY).build();
        //获取imageView
        x.image().bind(holder.iv_item_more_tj,courseBean.img,build);
        return convertView;
    }
    class ViewHolder {
        TextView tv_item_more_dianjiliang;
        TextView tv_item_more_keshi;
        TextView tv_item_more_tj;
        TextView tv_info_item_more_tj;
        ImageView iv_item_more_tj;
        TextView tv_item_more_jifen;
    }
}
