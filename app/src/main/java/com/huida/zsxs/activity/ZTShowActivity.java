package com.huida.zsxs.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.huida.zsxs.R;
import com.huida.zsxs.bean.GetZTShowBean;
import com.huida.zsxs.utils.ConstantUtil;
import com.huida.zsxs.view.NoSrollGridview;
import com.huida.zsxs.view.ProgressLayout;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

@ContentView(R.layout.activity_ztshow)
public class ZTShowActivity extends Activity {
    @ViewInject(R.id.bt_zt_showback)
    Button bt_zt_showback;
    @ViewInject(R.id.pl_ztshow_detail)
    ProgressLayout pl_ztshow_detail;
    @ViewInject(R.id.iv_ztshow_detaild)
    ImageView iv_ztshow_detaild;
    @ViewInject(R.id.gv_ztshow_detail)
    NoSrollGridview gv_ztshow_detail;
    private int ztid;
    private List<GetZTShowBean.CourseBean> showCourseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent =  getIntent();
        ztid = Integer.parseInt(intent.getStringExtra("ztid"));
        x.view().inject(this);
        pl_ztshow_detail.showProgress();
        getZTShowFromNet();
        bt_zt_showback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //单个条目的单击事件（以后补充）
        gv_ztshow_detail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void getZTShowFromNet() {
        RequestParams params = new RequestParams(ConstantUtil.PATH);
        params.addBodyParameter("Action","getZTShow");
        params.addBodyParameter("ztid",ztid+"");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                pl_ztshow_detail.showContent();
                Gson gson = new Gson();
                GetZTShowBean ztShowBean = gson.fromJson(result, GetZTShowBean.class);
                bt_zt_showback.setText(ztShowBean.ZTtitle);
                ImageOptions build = new ImageOptions.Builder().setImageScaleType(ImageView.ScaleType.FIT_XY).build();
                x.image().bind(iv_ztshow_detaild,ztShowBean.imgURL,build);
                showCourseList = ztShowBean.Course;
                gv_ztshow_detail.setAdapter(new ZTShowAdapter());

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
    public class ZTShowAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return showCourseList.size();
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
            if (convertView == null){
                holder = new ViewHolder();
                convertView = View.inflate(ZTShowActivity.this,R.layout.zt_detail_list_item_layout,null);
                holder.iv_item_detail = (ImageView) convertView.findViewById(R.id.iv_item_detail);
                holder.tv_item_detail_jifen = (TextView) convertView.findViewById(R.id.tv_item_detail_jifen);
                holder.tv_item_detail_dianjiliang = (TextView) convertView.findViewById(R.id.tv_item_detail_dianjiliang);
                holder.tv_item_detail_keshi = (TextView) convertView.findViewById(R.id.tv_item_detail_keshi);
                holder.tv_item_detail = (TextView) convertView.findViewById(R.id.tv_item_detail);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            GetZTShowBean.CourseBean courseBean = showCourseList.get(position);
            holder.tv_item_detail.setText(courseBean.title);
            holder.tv_item_detail_keshi.setText(courseBean.keshi+"课时");
            holder.tv_item_detail_jifen.setText(courseBean.money+"积分");
            holder.tv_item_detail_dianjiliang.setText(courseBean.hot+"");
            ImageOptions build = new ImageOptions.Builder().setImageScaleType(ImageView.ScaleType.FIT_XY).build();
            x.image().bind(holder.iv_item_detail,courseBean.img,build);

            return convertView;
        }
        class ViewHolder{
            ImageView iv_item_detail;
            TextView tv_item_detail_jifen;
            TextView tv_item_detail_dianjiliang;
            TextView tv_item_detail_keshi;
            TextView tv_item_detail;
        }
    }
}
