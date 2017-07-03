package com.huida.zsxs.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huida.zsxs.R;
import com.huida.zsxs.adapter.MyCourseAdapter;
import com.huida.zsxs.bean.CourseType;
import com.huida.zsxs.bean.GetCourseList;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static android.content.ContentValues.TAG;


public class CourseActivity extends Activity implements View.OnClickListener {
    @InjectView(R.id.lv_courselist)
    PullToRefreshListView lv_courselist;
    @InjectView(R.id.ib_back)
    ImageButton ibBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.ib_search)
    ImageButton ibSearch;
    @InjectView(R.id.tv_kc_types)
    TextView tvKcTypesLeft;
    @InjectView(R.id.tv_types)
    TextView tvKcTypesRight;
    @InjectView(R.id.activity_book)
    LinearLayout activityBook;

    private String typesurl = "http://api.chinaplat.com/getval_2017?Action=GetCourseList&types=";
    private List<GetCourseList.CourseBean> course;
    private List<GetCourseList.CourseBean> oldCourse =new ArrayList<>();
    private PopupWindow mPopWindow;
    private PopupWindow mOrdePopWindow;
    private GridView gv_course_types;
    private CourseType getCourseTypeList;
    private List<CourseType.TListBean> courseList;
    private TextView pop_zonghe;
    private TextView pop_hot;
    private TextView pop_fastnew;
    private TextView pop_pingfen;
    private TextView pop_add;
    private TextView pop_minus;
    private String kc_tid;
    private String orderTag = "0";
    private String refreshUrl;

    private int page_all;
    private int page_now;
    private MyCourseAdapter myCourseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        ButterKnife.inject(this);

        initData();
        initListener();
    }


    private void initData() {
        //设置是否允许刷新的时候可以滑动
        lv_courselist.setScrollingWhileRefreshingEnabled(true);

        getCourseTypeList = (CourseType) getIntent().getSerializableExtra("getCourseList");
        int position = getIntent().getIntExtra("position", 0);
        courseList = getCourseTypeList.getT_list();
        kc_tid = "&tid=" + courseList.get(position).getId();
        String name = courseList.get(position).getName();


        switch (getCourseTypeList.getKc_types()) {
            case "0":
                tvTitle.setText("视频中心");
                break;
            case "1":
                tvTitle.setText("音频中心");
                break;
            case "2":
                tvTitle.setText("读书中心");
                break;
            case "3":
                tvTitle.setText("文章中心");
                break;
        }
        showPopupWindow();
        showOrdePopupWindow();
        tvKcTypesLeft.setText(name);
        getCourseList(typesurl + getCourseTypeList.getKc_types() + kc_tid);
    }

    private void initListener() {
        ibBack.setOnClickListener(this);
        tvKcTypesLeft.setOnClickListener(this);
        tvKcTypesRight.setOnClickListener(this);
        pop_zonghe.setOnClickListener(this);
        pop_hot.setOnClickListener(this);
        pop_fastnew.setOnClickListener(this);
        pop_pingfen.setOnClickListener(this);
        pop_add.setOnClickListener(this);
        pop_minus.setOnClickListener(this);
        gv_course_types.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                kc_tid = "&tid=" + courseList.get(position).getId();
                String name = courseList.get(position).getName();
                tvKcTypesLeft.setText(name);
                mPopWindow.dismiss();
                getCourseListUrl();
                oldCourse.removeAll(oldCourse);

            }
        });
        lv_courselist.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                oldCourse.removeAll(oldCourse);
                getCourseListUrl();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (page_all > page_now) {
                    getRefreshUrl(page_now + 1);
                }
            }
        });
        lv_courselist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent(CourseActivity.this, AudioActivity.class);
                intent.putExtra("kc_types",getCourseTypeList.getKc_types());
                intent.putExtra("kc_id",oldCourse.get(position-1).getKc_id());
                startActivity(intent);
            }
        });
    }

    private void getRefreshUrl(int pageNext) {
        switch (orderTag) {
            case "0":
                getCourseList(typesurl + getCourseTypeList.getKc_types() + kc_tid + "&page=" + pageNext);
                break;
            case "1":
                getCourseList(typesurl + getCourseTypeList.getKc_types() + kc_tid + "&order=Hot" + "&page=" + pageNext);
                break;
            case "2":
                getCourseList(typesurl + getCourseTypeList.getKc_types() + kc_tid + "&order=time" + "&page=" + pageNext);
                break;
            case "3":
                getCourseList(typesurl + getCourseTypeList.getKc_types() + kc_tid + "&page=" + pageNext);
                break;
            case "4":
                getCourseList(typesurl + getCourseTypeList.getKc_types() + kc_tid + "&page=" + pageNext);
                break;
            case "5":
                getCourseList(typesurl + getCourseTypeList.getKc_types() + kc_tid + "&order=moneyUP" + "&page=" + pageNext);
                break;
            case "6":
                getCourseList(typesurl + getCourseTypeList.getKc_types() + kc_tid + "&order=moneyDown" + "&page=" + pageNext);
                break;
        }
    }

    private void getCourseListUrl() {
        switch (orderTag) {
            case "0":
                getCourseList(typesurl + getCourseTypeList.getKc_types() + kc_tid);
                break;
            case "1":
                getCourseList(typesurl + getCourseTypeList.getKc_types() + kc_tid + "&order=Hot");
                break;
            case "2":
                getCourseList(typesurl + getCourseTypeList.getKc_types() + kc_tid + "&order=time");
                break;
            case "3":
                getCourseList(typesurl + getCourseTypeList.getKc_types() + kc_tid);
                break;
            case "4":
                getCourseList(typesurl + getCourseTypeList.getKc_types() + kc_tid);
                break;
            case "5":
                getCourseList(typesurl + getCourseTypeList.getKc_types() + kc_tid + "&order=moneyUP");
                break;
            case "6":
                getCourseList(typesurl + getCourseTypeList.getKc_types() + kc_tid + "&order=moneyDown");
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.tv_kc_types:
                tvKcTypesLeft.setSelected(true);
                mPopWindow.showAsDropDown(v);
                break;
            case R.id.tv_types:
                tvKcTypesRight.setSelected(true);
                mOrdePopWindow.showAsDropDown(v);
                break;
            case R.id.pop_zonghe:
                getCourseList(typesurl + getCourseTypeList.getKc_types() + kc_tid);
                tvKcTypesRight.setText("综合");
                orderTag = "0";
                mOrdePopWindow.dismiss();
                oldCourse.removeAll(oldCourse);
                break;
            case R.id.pop_hot:
                getCourseList(typesurl + getCourseTypeList.getKc_types() + kc_tid + "&order=Hot");
                tvKcTypesRight.setText("热门");
                orderTag = "1";
                mOrdePopWindow.dismiss();
                oldCourse.removeAll(oldCourse);
                break;
            case R.id.pop_fastnew:
                getCourseList(typesurl + getCourseTypeList.getKc_types() + kc_tid + "&order=time");
                tvKcTypesRight.setText("最新");
                orderTag = "2";
                mOrdePopWindow.dismiss();
                oldCourse.removeAll(oldCourse);
                break;
            case R.id.pop_pingfen:
                tvKcTypesRight.setText("评分");
                orderTag = "4";
                oldCourse.removeAll(oldCourse);
                break;
            case R.id.pop_add:
                getCourseList(typesurl + getCourseTypeList.getKc_types() + kc_tid + "&order=moneyUP");
                tvKcTypesRight.setText("价格递增");
                orderTag = "5";
                mOrdePopWindow.dismiss();
                oldCourse.removeAll(oldCourse);
                break;
            case R.id.pop_minus:
                getCourseList(typesurl + getCourseTypeList.getKc_types() + kc_tid + "&order=moneyDown");
                tvKcTypesRight.setText("价格递减");
                orderTag = "6";
                mOrdePopWindow.dismiss();
                oldCourse.removeAll(oldCourse);
                break;
        }
    }

    private void showPopupWindow() {

        View contentView = LayoutInflater.from(CourseActivity.this).inflate(R.layout.popup_types, null);
        mPopWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setTouchable(true);
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                tvKcTypesLeft.setSelected(false);
            }
        });
        gv_course_types = (GridView) contentView.findViewById(R.id.gv_course_types);
        gv_course_types.setAdapter(new MyAdapter());

    }

    private void showOrdePopupWindow() {

        View contentView = LayoutInflater.from(CourseActivity.this).inflate(R.layout.popup_orde, null);
        mOrdePopWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        mOrdePopWindow.setTouchable(true);
        mOrdePopWindow.setOutsideTouchable(true);
        mOrdePopWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        mOrdePopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                tvKcTypesRight.setSelected(false);
            }
        });
        pop_zonghe = (TextView) contentView.findViewById(R.id.pop_zonghe);
        pop_hot = (TextView) contentView.findViewById(R.id.pop_hot);
        pop_fastnew = (TextView) contentView.findViewById(R.id.pop_fastnew);
        pop_pingfen = (TextView) contentView.findViewById(R.id.pop_pingfen);
        pop_add = (TextView) contentView.findViewById(R.id.pop_add);
        pop_minus = (TextView) contentView.findViewById(R.id.pop_minus);
    }

    public void getCourseList(final String url) {
        Log.e(TAG, "URL:" + url);
        RequestParams requestParams = new RequestParams(url);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                GetCourseList getCourseList = gson.fromJson(result, GetCourseList.class);
                page_all = Integer.parseInt(getCourseList.getPage_all());
                page_now = Integer.parseInt(getCourseList.getPage_now());
                course = getCourseList.getCourse();
                oldCourse.addAll(course);
                if (myCourseAdapter==null){
                    myCourseAdapter = new MyCourseAdapter();
                    myCourseAdapter.setKc_types(getCourseTypeList.getKc_types());
                    myCourseAdapter.setCourse(course);
                    lv_courselist.setAdapter(myCourseAdapter);
                }else {
                    myCourseAdapter.setKc_types(getCourseTypeList.getKc_types());
                    myCourseAdapter.setCourse(oldCourse);
                    myCourseAdapter.notifyDataSetChanged();
                    lv_courselist.onRefreshComplete();
                }
                if (page_all > page_now) {
                    lv_courselist.setMode(PullToRefreshBase.Mode.BOTH);
                } else {
                    lv_courselist.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
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


    class MyAdapter extends BaseAdapter {
        public int getCount() {
            return courseList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView == null) {
                holder = new Holder();
                convertView = View.inflate(CourseActivity.this, R.layout.gv_course_item, null);
                holder.course_name = (TextView) convertView.findViewById(R.id.course_name);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }

            holder.course_name.setText(courseList.get(position).getName());
            return convertView;
        }
    }


    class Holder {
        TextView course_name;
    }
}
