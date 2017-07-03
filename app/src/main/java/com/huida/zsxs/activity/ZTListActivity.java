package com.huida.zsxs.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huida.zsxs.R;
import com.huida.zsxs.bean.GetZTListBean;
import com.huida.zsxs.utils.ConstantUtil;
import com.huida.zsxs.view.ProgressLayout;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

public class ZTListActivity extends Activity {

    private ProgressLayout pl_zt;
    private PullToRefreshListView lv_zt_list;
    private int type;
    private int page_all;
    private int page_now;
    private List<GetZTListBean.ListBean> list;
    private boolean is_freshing = false;
    private ZtAdapter ztAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ztlist);
        Intent intent = getIntent();
        type = Integer.parseInt(intent.getStringExtra("type"));
        String title = "";
        switch (type){
            case 1:
                title = "考证达人";
                break;
            case 2:
                title = "职场精英";
                break;
            case 3:
                title = "生活易趣";
                break;
            case 4:
                title = "企业培训";
                break;
        }
        Button bt_zt_listback = (Button) findViewById(R.id.bt_zt_listback);
        bt_zt_listback.setText(title);
        bt_zt_listback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //正在加载中的动画（圈圈）
        pl_zt = (ProgressLayout) findViewById(R.id.pl_zt);
        pl_zt.showProgress();
        lv_zt_list = (PullToRefreshListView) findViewById(R.id.lv_zt_list);
        getZTFromNet();
        initRefresh();
    }

    private void initRefresh() {
        final ILoadingLayout proxy = lv_zt_list.getLoadingLayoutProxy();
        lv_zt_list.setMode(PullToRefreshBase.Mode.BOTH);
        lv_zt_list.setScrollingWhileRefreshingEnabled(true);
        lv_zt_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                is_freshing = true;
                lv_zt_list.setMode(PullToRefreshBase.Mode.BOTH);
                getZTFromNet();
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
                    lv_zt_list.onRefreshComplete();
                    lv_zt_list.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                    Toast.makeText(ZTListActivity.this, "没有更多数据", Toast.LENGTH_SHORT).show();
                }
            }
        });

        lv_zt_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //单个条目的单击事件，跳转到ZTSHOWactivity
                Intent intent = new Intent(ZTListActivity.this, ZTShowActivity.class);
                intent.putExtra("ztid",list.get(position-1).ztid+"");
                startActivity(intent);
            }
        });
    }

    private void getMoreData() {
        RequestParams params = new RequestParams(ConstantUtil.PATH);
        params.addBodyParameter("Action","GetZT");
        params.addBodyParameter("types",type+"");
        params.addBodyParameter("Page",page_now+"");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("huida",result);
                Gson gson = new Gson();
                GetZTListBean ztListBean = gson.fromJson(result, GetZTListBean.class);
                List<GetZTListBean.ListBean> moreList = ztListBean.list;
                page_now = ztListBean.page_now;
                list.addAll(moreList);
                //刷新
                lv_zt_list.onRefreshComplete();
                ztAdapter.notifyDataSetChanged();

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

    private void getZTFromNet() {
        RequestParams params = new RequestParams(ConstantUtil.PATH);
        params.addBodyParameter("Action","GetZT");
        params.addBodyParameter("types",type+"");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //显示内容
                pl_zt.showContent();
                Gson gson = new Gson();
                GetZTListBean ztListBean = gson.fromJson(result, GetZTListBean.class);
                page_all = ztListBean.page_all;
                page_now = ztListBean.page_now;
                list = ztListBean.list;

                if (is_freshing){
                    is_freshing = false;
                    lv_zt_list.onRefreshComplete();
                }
                ztAdapter = new ZtAdapter();
                lv_zt_list.setAdapter(ztAdapter);
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
    public class ZtAdapter extends BaseAdapter{

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
            if (convertView==null){
                convertView = View.inflate(ZTListActivity.this,R.layout.zt_list_item,null);
            }
            ImageView iv_zt_list_item = (ImageView) convertView.findViewById(R.id.iv_zt_list_item);
            x.image().bind(iv_zt_list_item,list.get(position).imgURL);
            return convertView;
        }
    }
}
