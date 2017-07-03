package com.huida.zsxs.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.huida.zsxs.R;
import com.huida.zsxs.activity.CourseActivity;
import com.huida.zsxs.bean.CourseType;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;


/**
 * Created by zhang on 2017/6/24.
 */

public class CourseFragment extends BaseFragment implements View.OnClickListener {

    private String kc_types = "0";
    private String examtid = "&tid=4";
    private String worktid = "&tid=5";
    private String lifetid = "&tid=10";
    private String typeurl = "http://api.chinaplat.com/getval_2017?Action=GetCourseTypesList&kc_types=";
    private ImageView right_top_search;
    private ImageView right_top_more;
    private RadioButton ib_exam;
    private RadioButton ib_work;
    private RadioButton ib_life;
    private GridView gv_course;
    private ListView lv_course;
    private List<CourseType.TListBean> t_list;
    private List<CourseType.TListBean> courseList;
    private PopupWindow mPopWindow;
    private TextView pop_video;
    private TextView pop_audio;
    private TextView pop_book;
    private TextView pop_article;
    private TextView tv_center_name;
    private CourseType getCourseList;
    private int oldListPosition=0;
    private int changePosition=0;
    private MyAdapter myAdapter;

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.coursefraement, null);
        right_top_search = (ImageView) view.findViewById(R.id.right_top_search);
        right_top_more = (ImageView) view.findViewById(R.id.right_top_more);
        ib_exam = (RadioButton) view.findViewById(R.id.ib_exam);
        ib_work = (RadioButton) view.findViewById(R.id.ib_work);
        ib_life = (RadioButton) view.findViewById(R.id.ib_life);
        gv_course = (GridView) view.findViewById(R.id.gv_course);
        lv_course = (ListView) view.findViewById(R.id.lv_course);
        tv_center_name = (TextView) view.findViewById(R.id.tv_center_name);
        return view;
    }

    @Override
    protected void initData() {
        ib_exam.setSelected(true);
        setCourseType(typeurl + kc_types);

        showPopupWindow();
    }

    @Override
    protected void initListener() {
        ib_exam.setOnClickListener(this);
        ib_life.setOnClickListener(this);
        ib_work.setOnClickListener(this);
        right_top_more.setOnClickListener(this);
        pop_article.setOnClickListener(this);
        pop_video.setOnClickListener(this);
        pop_audio.setOnClickListener(this);
        pop_book.setOnClickListener(this);
        lv_course.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                oldListPosition=position;
                myAdapter.notifyDataSetChanged();
                String tid = t_list.get(position).getId();
                setCourseCourseGridViewList(typeurl + kc_types + "&tid=" + tid);
            }
        });
        gv_course.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent course = new Intent(mActivity, CourseActivity.class);
                course.putExtra("getCourseList", getCourseList);
                course.putExtra("position",position);
                startActivity(course);


            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_exam:
                ib_exam.setSelected(true);
                ib_work.setSelected(false);
                ib_life.setSelected(false);
                setCourseListViewList(typeurl + kc_types + examtid);
                break;
            case R.id.ib_work:
                ib_exam.setSelected(false);
                ib_work.setSelected(true);
                ib_life.setSelected(false);
                setCourseListViewList(typeurl + kc_types + worktid);
                break;
            case R.id.ib_life:
                ib_exam.setSelected(false);
                ib_work.setSelected(false);
                ib_life.setSelected(true);
                setCourseListViewList(typeurl + kc_types + lifetid);
                break;
            case R.id.right_top_more:
                if (mPopWindow != null) {
                    mPopWindow.showAsDropDown(v);
                }
                break;
            case R.id.pop_video:
                kc_types = "0";
                tv_center_name.setText("视频中心");
                ib_exam.setSelected(true);
                ib_work.setSelected(false);
                ib_life.setSelected(false);
                setCourseType(typeurl + kc_types);
                if (mPopWindow != null) {
                    mPopWindow.dismiss();
                }
                break;
            case R.id.pop_audio:
                kc_types = "1";
                tv_center_name.setText("音频中心");
                ib_exam.setSelected(true);
                ib_work.setSelected(false);
                ib_life.setSelected(false);
                setCourseType(typeurl + kc_types);
                if (mPopWindow != null) {
                    mPopWindow.dismiss();
                }
                break;
            case R.id.pop_book:
                kc_types = "2";
                tv_center_name.setText("读书中心");
                ib_exam.setSelected(true);
                ib_work.setSelected(false);
                ib_life.setSelected(false);
                setCourseType(typeurl + kc_types);
                if (mPopWindow != null) {
                    mPopWindow.dismiss();
                }
                break;
            case R.id.pop_article:
                tv_center_name.setText("文章中心");
                ib_exam.setSelected(true);
                ib_work.setSelected(false);
                ib_life.setSelected(false);
                kc_types = "3";
                setCourseType(typeurl + kc_types);
                if (mPopWindow != null) {
                    mPopWindow.dismiss();
                }
                break;
        }

    }

    private void showPopupWindow() {

        View contentView = LayoutInflater.from(mActivity).inflate(R.layout.popuplayout, null);
        mPopWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setTouchable(true);
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        pop_video = (TextView) contentView.findViewById(R.id.pop_video);
        pop_audio = (TextView) contentView.findViewById(R.id.pop_audio);
        pop_book = (TextView) contentView.findViewById(R.id.pop_book);
        pop_article = (TextView) contentView.findViewById(R.id.pop_article);

    }

    public void setCourseType(final String url) {
        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                CourseType courseTypesList = gson.fromJson(result, CourseType.class);
                List<CourseType.TListBean> t_list = courseTypesList.getT_list();
                for (int i = 0; i <= t_list.size() - 1; i++) {
                    switch (t_list.get(i).getName()) {
                        case "考试":
                            examtid = "&tid=" + t_list.get(i).getId();
                            break;
                        case "工作":
                            worktid = "&tid=" + t_list.get(i).getId();
                            break;
                        case "生活":
                            lifetid = "&tid=" + t_list.get(i).getId();
                            break;
                    }
                }
                setCourseListViewList(typeurl + courseTypesList.getKc_types() + examtid);
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

    public void setCourseListViewList(final String url) {
        RequestParams params = new RequestParams(url);

        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                CourseType courseTypesList = gson.fromJson(result, CourseType.class);
                t_list = courseTypesList.getT_list();
                myAdapter = new MyAdapter();
                lv_course.setAdapter(myAdapter);
                setCourseCourseGridViewList(typeurl + courseTypesList.getKc_types() + "&tid=" + t_list.get(0).getId());
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

    public void setCourseCourseGridViewList(String url) {
        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                getCourseList = gson.fromJson(result, CourseType.class);
                courseList = getCourseList.getT_list();
                gv_course.setAdapter(new MyCourseAdapter());
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
            return t_list.size();
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
                convertView = View.inflate(mActivity, R.layout.gv_course_item, null);
                holder.course_name = (TextView) convertView.findViewById(R.id.course_name);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            holder.course_name.setText(t_list.get(position).getName());
            if (oldListPosition==position){
                holder.course_name.setTextColor(Color.GREEN);
            }else {
                holder.course_name.setTextColor(Color.BLACK);
            }
            return convertView;
        }
    }

    class MyCourseAdapter extends BaseAdapter {
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
                convertView = View.inflate(mActivity, R.layout.gv_course_item, null);
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
