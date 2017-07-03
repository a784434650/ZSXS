package com.huida.zsxs.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huida.zsxs.R;
import com.huida.zsxs.bean.GetTJCourseBean;
import com.huida.zsxs.utils.ConstantUtil;
import com.huida.zsxs.view.ProgressLayout;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

@ContentView(R.layout.activity_more_tj)
public class MoreTJActivity extends Activity {
    @ViewInject(R.id.bt_moretj_back)
    Button bt_moretj_back;
    @ViewInject(R.id.ib_moretj_search)
    ImageButton ib_moretj_search;
    @ViewInject(R.id.lv_more_tj)
    PullToRefreshListView lv_more_tj;
    @ViewInject(R.id.pl_more_tj)
    ProgressLayout pl_more_tj;
    private List<GetTJCourseBean.CourseBean> courseBeanList;
    private int page_all;
    private int page_now;
    private MoreTJAdapter moreTJAdapter;
    private boolean is_freshing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        bt_moretj_back.setText("推荐课程");
        bt_moretj_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ib_moretj_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //搜索的点击事件


            }
        });
        pl_more_tj.showProgress();
        getMoreTJFromNet();
        initRefresh();
    }

    private void initRefresh() {
        final ILoadingLayout proxy = lv_more_tj.getLoadingLayoutProxy();
        lv_more_tj.setMode(PullToRefreshBase.Mode.BOTH);
        lv_more_tj.setScrollingWhileRefreshingEnabled(true);
        lv_more_tj.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                is_freshing = true;
                lv_more_tj.setMode(PullToRefreshBase.Mode.BOTH);
                getMoreTJFromNet();
                proxy.setPullLabel("下拉刷新");
                proxy.setLastUpdatedLabel("上次刷新时间：");
                proxy.setRefreshingLabel("正在加载...");
                proxy.setReleaseLabel("松开刷新数据");
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                proxy.setLastUpdatedLabel("上次刷新时间："+label);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                proxy.setPullLabel("加载更多");
                if(page_now<page_all){
                    page_now +=1;
                    getMoreData();
                }else{
                    lv_more_tj.onRefreshComplete();
                    lv_more_tj.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                    Toast.makeText(MoreTJActivity.this, "没有更多数据", Toast.LENGTH_SHORT).show();
                }
            }
        });

        lv_more_tj.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void getMoreData() {
        RequestParams params = new RequestParams(ConstantUtil.PATH);
        params.addBodyParameter("Action","GetTJCourseList");
        params.addBodyParameter("Page",page_now+"");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("huida",result);
                Gson gson = new Gson();
                GetTJCourseBean bean = gson.fromJson(result, GetTJCourseBean.class);
                List<GetTJCourseBean.CourseBean> moreList = bean.Course;
                page_now = Integer.parseInt(bean.page_now);
                courseBeanList.addAll(moreList);
                //刷新
                lv_more_tj.onRefreshComplete();
                moreTJAdapter.notifyDataSetChanged();

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

    private void getMoreTJFromNet() {
        RequestParams params = new RequestParams(ConstantUtil.PATH);
        params.addBodyParameter("Action","GetTJCourseList");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                pl_more_tj.showContent();
                Gson gson = new Gson();
                GetTJCourseBean tjCourseBean = gson.fromJson(result, GetTJCourseBean.class);
                courseBeanList = tjCourseBean.Course;
                page_all = Integer.parseInt(tjCourseBean.page_all);
                page_now = Integer.parseInt(tjCourseBean.page_now);
                if (is_freshing){
                    is_freshing = false;
                    lv_more_tj.onRefreshComplete();
                }
                moreTJAdapter = new MoreTJAdapter();
                lv_more_tj.setAdapter(moreTJAdapter);
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
    class MoreTJAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return courseBeanList.size();
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
                convertView = View.inflate(MoreTJActivity.this,R.layout.more_tj_list_item_layout,null);
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
            GetTJCourseBean.CourseBean courseBean = courseBeanList.get(position);
            holder.tv_item_more_tj.setText(courseBean.title);
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
}
