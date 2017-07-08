package com.huida.zsxs.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huida.zsxs.R;
import com.huida.zsxs.bean.GetPLBean;
import com.huida.zsxs.utils.ConstantUtil;
import com.huida.zsxs.utils.SpUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by xiaojiu on 2017/7/6.
 */

public class PingLunMoreActivity extends Activity {
    @InjectView(R.id.bt_pl_back)
    Button bt_pl_back;
    @InjectView(R.id.tv_pl_count)
    TextView tv_pl_count;
    @InjectView(R.id.rl_header)
    RelativeLayout rl_header;
    @InjectView(R.id.line_one)
    View line_one;
    @InjectView(R.id.lv_pl_list)
    PullToRefreshListView lv_pl_list;
    @InjectView(R.id.iv_empty)
    ImageView iv_empty;
    @InjectView(R.id.text1)
    TextView text1;
    @InjectView(R.id.rl_empty)
    RelativeLayout rl_empty;
    @InjectView(R.id.et_pl_more)
    EditText et_pl_more;
    @InjectView(R.id.bt_pl_send)
    Button bt_pl_send;
    @InjectView(R.id.ll_input_back)
    LinearLayout ll_input_back;
    private int page_now;
    private int page_all;
    private boolean is_freshing = false;
    private List<GetPLBean.PllistBean> list;
    private String id;
    private PingLunAdapter pingLunAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pl_more);
        ButterKnife.inject(this);
        Intent intent = getIntent();
        Bundle data = intent.getBundleExtra("data");
        list=data.getParcelableArrayList("list");
        id = intent.getStringExtra("id");
        page_all = intent.getIntExtra("page_all",0);
        page_now = intent.getIntExtra("page_now",0);
        tv_pl_count.setText("评论("+ list.size()+")");
        if (list.size()==0){
            rl_empty.setVisibility(View.VISIBLE);
            lv_pl_list.setVisibility(View.GONE);

        }else{
            rl_empty.setVisibility(View.GONE);
            lv_pl_list.setVisibility(View.VISIBLE);
            pingLunAdapter = new PingLunAdapter();
            lv_pl_list.setAdapter(pingLunAdapter);
            initRefresh();
        }
        bt_pl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bt_pl_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!SpUtil.getBoolean(MainActivity.IS_LOGIN,PingLunMoreActivity.this)) {
                    Toast.makeText(PingLunMoreActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                    return;
                }
                String pl = et_pl_more.getText().toString().trim();
                if (TextUtils.isEmpty(pl)){
                    Toast.makeText(PingLunMoreActivity.this, "请输入评论内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                RequestParams params = new RequestParams(ConstantUtil.PATH);
                params.addBodyParameter("Action","SaveCoursePL");
                params.addBodyParameter("cid", id);
                params.addBodyParameter("plcontent", pl);
                String acode = SpUtil.getString(LoginActivity.ACODE, PingLunMoreActivity.this);
                String username = SpUtil.getString(LoginActivity.USERNAME, PingLunMoreActivity.this);
                params.addBodyParameter("acode",acode);
                params.addBodyParameter("Uid",username);
                x.http().get(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Toast.makeText(PingLunMoreActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                        getPingLunFromNet();
                        return;
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
        });



    }
    public class PingLunAdapter extends BaseAdapter{

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
            convertView = View.inflate(PingLunMoreActivity.this,R.layout.list_pinglun_item,null);
            TextView tv_pinglun_name = (TextView) convertView.findViewById(R.id.tv_pinglun_name);
            TextView tv_pinglun_time = (TextView) convertView.findViewById(R.id.tv_pinglun_time);
            TextView tv_pinglun_content = (TextView) convertView.findViewById(R.id.tv_pinglun_content);
            ImageView iv_pinglun_icon = (ImageView) convertView.findViewById(R.id.iv_pinglun_icon);

            GetPLBean.PllistBean pllistBean = list.get(position);
            tv_pinglun_name.setText(pllistBean.nickname);
            tv_pinglun_time.setText(pllistBean.pl_time);
            tv_pinglun_content.setText(pllistBean.pc_content);
            ImageOptions build = new ImageOptions.Builder().setCircular(true).build();
            x.image().bind(iv_pinglun_icon,pllistBean.userimg,build);
            return convertView;
        }
    }
    private void initRefresh() {
        final ILoadingLayout proxy = lv_pl_list.getLoadingLayoutProxy();
        lv_pl_list.setMode(PullToRefreshBase.Mode.BOTH);
        lv_pl_list.setScrollingWhileRefreshingEnabled(true);
        lv_pl_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                is_freshing = true;
                lv_pl_list.setMode(PullToRefreshBase.Mode.BOTH);
                getPingLunFromNet();
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
                    lv_pl_list.onRefreshComplete();
                    lv_pl_list.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                    Toast.makeText(PingLunMoreActivity.this, "没有更多数据", Toast.LENGTH_SHORT).show();
                }
            }
        });

        lv_pl_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void getPingLunFromNet() {
        RequestParams params = new RequestParams(ConstantUtil.PATH);
        params.addBodyParameter("Action","GetCoursePL");
        params.addBodyParameter("cid", id);
        Log.e("jia",id+"");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                GetPLBean plBean = gson.fromJson(result, GetPLBean.class);
                if (is_freshing){
                    is_freshing = false;
                    lv_pl_list.onRefreshComplete();
                }
                page_all=plBean.page_all;
                page_now=plBean.page_now;
                list = plBean.pllist;
                tv_pl_count.setText("评论("+ list.size()+")");
                if (list.size()==0){
                    rl_empty.setVisibility(View.VISIBLE);
                    lv_pl_list.setVisibility(View.GONE);
                    return;
                }else{
                    rl_empty.setVisibility(View.GONE);
                    lv_pl_list.setVisibility(View.VISIBLE);
                }
                if (pingLunAdapter!=null){
                    pingLunAdapter.notifyDataSetChanged();
                }else{
                    lv_pl_list.setAdapter(pingLunAdapter);
                }
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

    private void getMoreData() {
        RequestParams params = new RequestParams(ConstantUtil.PATH);
        params.addBodyParameter("Action","GetPLBean");
        params.addBodyParameter("cid",id+"");
        params.addBodyParameter("Page",page_now+"");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //Log.e("huida",result);
                Gson gson = new Gson();
                GetPLBean bean = gson.fromJson(result, GetPLBean.class);
                List<GetPLBean.PllistBean> moreList = bean.pllist;
                page_now = bean.page_now;
                list.addAll(moreList);
                //刷新
                lv_pl_list.onRefreshComplete();
                pingLunAdapter.notifyDataSetChanged();

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
}
