package com.huida.zsxs.pager;

import android.app.Activity;
import android.content.Intent;
import android.text.format.DateUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huida.zsxs.R;
import com.huida.zsxs.activity.ArticleActivity;
import com.huida.zsxs.activity.MainActivity;
import com.huida.zsxs.activity.MoreTJActivity;
import com.huida.zsxs.activity.SearchActivity;
import com.huida.zsxs.adapter.SearchDuShuAdapter;
import com.huida.zsxs.adapter.SearchShiPinAdapter;
import com.huida.zsxs.adapter.SearchWenZhangAdapter;
import com.huida.zsxs.bean.GetSearchCourseBean;
import com.huida.zsxs.bean.GetTJCourseBean;
import com.huida.zsxs.fragment.BaseFragment;
import com.huida.zsxs.utils.ConstantUtil;
import com.huida.zsxs.utils.DensityUtil;
import com.huida.zsxs.utils.SpUtil;
import com.huida.zsxs.view.FlowLayout;
import com.huida.zsxs.view.ProgressLayout;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by xiaojiu on 2017/7/2.
 */

public class SearchPager {

    private static final String SAVE_FILE = "save_file";
    private final Activity activity;
    private final int tabs;
    private final EditText et_search_list;
    public View rootView;
    private FrameLayout fl_pager_search;
    private ImageView iv_delete_zuijin;
    private GridView gv_zuijin;
    private ProgressLayout pl_shipin_base_page;
    private FlowLayout flow;
    private PullToRefreshListView lv_search_data_page;
    private ProgressLayout pl_shipin_data_page;
    private LinearLayout ll_no_data;
    private List<GetSearchCourseBean.CourseListBean> courseList;
    private int page_now;
    private int page_all;
    private int kc_types;
    private String strings;
    private boolean is_freshing = false;
    private BaseAdapter baseAdapter;
    private int types;
    private String[] split;
    private TextView tv_zhengdin;


    public SearchPager(Activity activity, int tabs, EditText et_search_list){
        this.activity = activity;
        this.tabs = tabs;
        this.et_search_list = et_search_list;
        rootView=initView();
    }

    private View initView() {
        View view = View.inflate(activity, R.layout.search_pager_layout, null);
        fl_pager_search = (FrameLayout) view.findViewById(R.id.fl_pager_search);
        return view;
    }
    public void setInitData(int temp) {
        types = temp;
        fl_pager_search.removeAllViews();
        View view = View.inflate(activity, R.layout.search_shipin_init_page, null);
        iv_delete_zuijin = (ImageView) view.findViewById(R.id.iv_delete_zuijin);
        gv_zuijin = (GridView) view.findViewById(R.id.gv_zuijin);
        pl_shipin_base_page = (ProgressLayout) view.findViewById(R.id.pl_shipin_base_page);
        flow = (FlowLayout) view.findViewById(R.id.flow);
        pl_shipin_base_page.showProgress();
        //删除图标的单击事件
        iv_delete_zuijin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpUtil.putString(SAVE_FILE,"",activity);
                getSearchFileData();
            }
        });
        getSearchFileData();//获取保存最近搜索内容的方法
        getInitData();//获取初始化的数据
        fl_pager_search.addView(view,0);

    }
    //获取保存最近搜索内容的方法
    private void getSearchFileData() {
        String file = SpUtil.getString(SAVE_FILE, activity);
        split = file.split(",");
        gv_zuijin.setAdapter(new SaveFileAdapter());
    }
    public class SaveFileAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return split.length;
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
            final TextView tv = new TextView(activity);
            tv.setTextColor(0xff747474);
            tv.setText(split[position]);
            //最近搜索的单击事件
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    et_search_list.setText(tv.getText());
                    setSearchData(tv.getText().toString(),types);
                    et_search_list.setSelection(tv.getText().length());
                    SearchActivity.is_init = true;
                }
            });
            return tv;
        }
    }
    //获取初始化的数据（热门搜索）
    private void getInitData() {
        RequestParams params = new RequestParams(ConstantUtil.PATH);
        params.addBodyParameter("Action", "getKeywords");
        params.addBodyParameter("kc_types", tabs + "");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                pl_shipin_base_page.showContent();
                Gson gson = new Gson();
                String[] jsonList = gson.fromJson(result, String[].class);
                if (flow.getChildCount() == 0) {
                    for (int i = 0; i < jsonList.length; i++) {
                        //控件之间的间距
                        int ranHeight = DensityUtil.dip2px(activity, 40);
                        final TextView tv = new TextView(activity);
                        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ranHeight);
                        lp.setMargins(DensityUtil.dip2px(activity, 7), 0, DensityUtil.dip2px(activity, 7), 0);
                        //调整控件中字的位置
                        tv.setPadding(DensityUtil.dip2px(activity, 15), DensityUtil.dip2px(activity, 5), DensityUtil.dip2px(activity, 15), DensityUtil.dip2px(activity, 5));
                        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                        tv.setTextColor(0xff747474);
                        tv.setText(jsonList[i]);
                        tv.setGravity(Gravity.CENTER_VERTICAL);
                        tv.setLines(1);
                        tv.setBackgroundResource(R.drawable.search_item_shape);
                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //单个小控件的单击事件
                                setSearchData(tv.getText().toString(),types);//获取布局
                                et_search_list.setText(tv.getText());//点击拿到字
                                et_search_list.setSelection(tv.getText().length());//光标定位在最后
                                SearchActivity.is_init = true;
                            }
                        });
                        flow.addView(tv, lp);

                    }
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
    public void setSearchData(String str,int type){
        this.strings = str;
        this.types = type;
        fl_pager_search.removeAllViews();
        View view = View.inflate(activity, R.layout.search_shipin_data_page, null);
        lv_search_data_page = (PullToRefreshListView) view.findViewById(R.id.lv_search_data_page);
        pl_shipin_data_page = (ProgressLayout) view.findViewById(R.id.pl_shipin_data_page);
        ll_no_data = (LinearLayout) view.findViewById(R.id.ll_no_data);
        tv_zhengdin = (TextView) view.findViewById(R.id.tv_zhengdin);
        pl_shipin_data_page.showProgress();
        getSearchData();
        initRefresh();
        fl_pager_search.addView(view,0);

    }
    private void initRefresh() {
        final ILoadingLayout proxy = lv_search_data_page.getLoadingLayoutProxy();
        lv_search_data_page.setMode(PullToRefreshBase.Mode.BOTH);
        lv_search_data_page.setScrollingWhileRefreshingEnabled(true);
        lv_search_data_page.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                is_freshing = true;
                lv_search_data_page.setMode(PullToRefreshBase.Mode.BOTH);
                getSearchData();
                proxy.setPullLabel("下拉刷新");
                proxy.setLastUpdatedLabel("上次刷新时间：");
                proxy.setRefreshingLabel("正在加载...");
                proxy.setReleaseLabel("松开刷新数据");
                String label = DateUtils.formatDateTime(activity, System.currentTimeMillis(),
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
                    lv_search_data_page.onRefreshComplete();
                    lv_search_data_page.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                    Toast.makeText(activity, "没有更多数据", Toast.LENGTH_SHORT).show();
                }
            }
        });

        lv_search_data_page.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
    //获取更多数据
    private void getMoreData() {
        RequestParams params = new RequestParams(ConstantUtil.PATH);
        params.addBodyParameter("Action", "searchCourse");
        params.addBodyParameter("kc_types", tabs + "");
        params.addBodyParameter("keywords", strings);
        params.addBodyParameter("Page", page_now+"");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                GetSearchCourseBean bean = gson.fromJson(result, GetSearchCourseBean.class);
                List<GetSearchCourseBean.CourseListBean> moreList = bean.courseList;
                page_now = bean.page_now;
                courseList.addAll(moreList);
                //刷新
                lv_search_data_page.onRefreshComplete();
                baseAdapter.notifyDataSetChanged();

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
    //获取搜索内容（搜索按钮）
    private void getSearchData() {
        RequestParams params = new RequestParams(ConstantUtil.PATH);
        params.addBodyParameter("Action", "searchCourse");
        params.addBodyParameter("kc_types", types + "");
        params.addBodyParameter("keywords", strings);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //Log.e("jdr",result);
                pl_shipin_data_page.showContent();
                result = result.replace("\\","/");
                Gson gson = new Gson();
                GetSearchCourseBean searchCourseBean = gson.fromJson(result, GetSearchCourseBean.class);
                courseList = searchCourseBean.courseList;
                if (courseList.size()==0){
                    lv_search_data_page.setVisibility(View.GONE);
                    ll_no_data.setVisibility(View.VISIBLE);

                    //无搜索内容，单击跳转到征订页面（个人中心）
                    tv_zhengdin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    return;
                }
                lv_search_data_page.setVisibility(View.VISIBLE);
                ll_no_data.setVisibility(View.GONE);
                page_now = searchCourseBean.page_now;
                page_all = searchCourseBean.page_all;
                kc_types = searchCourseBean.kc_types;

                if (is_freshing){
                    lv_search_data_page.onRefreshComplete();
                    is_freshing = false;
                }
                switch (kc_types){
                    case 0:

                    case 1:
                        baseAdapter = new SearchShiPinAdapter(activity,courseList,strings);
                        break;
                    case 2:
                        baseAdapter = new SearchDuShuAdapter(activity,courseList,strings);
                        break;
                    case 3:
                        baseAdapter = new SearchWenZhangAdapter(activity,courseList,strings);
                        break;
                }
                lv_search_data_page.setAdapter(baseAdapter);
                lv_search_data_page.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //条目单击事件（播放等）
                switch (kc_types){
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        //跳转到文章页面
                        Intent intent = new Intent(activity, ArticleActivity.class);
                        intent.putExtra("id",courseList.get(position-1).kc_id);
                        activity.startActivity(intent);
                        break;
                }

                    }
                });
                //保存最近搜索的内容
                saveSearchList();
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
    //保存最近搜索的内容
    private void saveSearchList() {
        String save_file = SpUtil.getString(SAVE_FILE, activity);
        if (save_file.contains(strings)){
            return;
        }
        SpUtil.putString(SAVE_FILE,strings+","+save_file,activity);
    }
}
