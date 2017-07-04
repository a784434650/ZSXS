package com.huida.zsxs.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.huida.zsxs.R;
import com.huida.zsxs.activity.LoginActivity;
import com.huida.zsxs.activity.MainActivity;
import com.huida.zsxs.activity.MoreTJActivity;
import com.huida.zsxs.activity.SearchActivity;
import com.huida.zsxs.activity.SignActivity;
import com.huida.zsxs.activity.WebViewActivity;
import com.huida.zsxs.activity.ZTListActivity;
import com.huida.zsxs.activity.ZTShowActivity;
import com.huida.zsxs.bean.GetCourse100Bean;
import com.huida.zsxs.bean.GetSlidesBean;
import com.huida.zsxs.bean.GetTJCourseBean;
import com.huida.zsxs.utils.ConstantUtil;
import com.huida.zsxs.utils.DensityUtil;
import com.huida.zsxs.utils.SpUtil;
import com.huida.zsxs.view.NoSrollGridview;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by xiaojiu on 2017/6/23.
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private ImageButton ib_seach;
    private ImageButton ib_sign;
    private ImageButton ib_scan;
    private ViewPager vp_home;
    private LinearLayout ll_image_point;
    private LinearLayout ll_bfk01;
    private LinearLayout ll_bfk02;
    private LinearLayout ll_bfk03;
    private ImageView iv_bfk_pic01;
    private ImageView iv_bfk_pic02;
    private ImageView iv_bfk_pic03;
    private List<GetSlidesBean.SlidesBean> slidesBeanList;
    private int preSelectPoint = 0;//前一个点选中
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
                //切换
                vp_home.setCurrentItem(vp_home.getCurrentItem()+1);
                //继续发消息
                handler.sendEmptyMessageDelayed(0, 3000);
        }
    };
    private List<GetCourse100Bean.CourseBean> courseList;
    private TextView tv_bfk_01;
    private TextView tv_bfk_02;
    private TextView tv_bfk_03;
    private List<GetTJCourseBean.CourseBean> tjCourseList;
    private NoSrollGridview gv_tj;
    private LinearLayout ll_kzdr;
    private LinearLayout ll_zcjy;
    private LinearLayout ll_shyq;
    private LinearLayout ll_qypx;
    private RelativeLayout rl_more_tj;

    @Override
    protected void initListener() {
        //2017按钮的点击事件
        ll_kzdr.setOnClickListener(this);
        ll_zcjy.setOnClickListener(this);
        ll_shyq.setOnClickListener(this);
        ll_qypx.setOnClickListener(this);
        //更多推荐课程的单击事件
        rl_more_tj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, MoreTJActivity.class);
                startActivity(intent);
            }
        });
        //签到的点击事件
        ib_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SpUtil.getBoolean(MainActivity.IS_LOGIN,mActivity)){
                    Intent intent = new Intent(mActivity, SignActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(mActivity, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        //搜索的点击事件
        ib_seach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        preSelectPoint = 0;
        getTurnsPic();
        getBfk();
        getGetTJCourseList();
    }
    //填充推荐课程模块的数据
    private void getGetTJCourseList() {
        RequestParams params = new RequestParams(ConstantUtil.PATH);
        params.addBodyParameter("Action","GetTJCourseList");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                GetTJCourseBean tjCourseBean = gson.fromJson(result, GetTJCourseBean.class);
                tjCourseList = tjCourseBean.Course;
                gv_tj.setAdapter(new TJCourseAdapter());
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
    //2017按钮的跳转
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mActivity, ZTListActivity.class);
        switch (v.getId()){
            case R.id.ll_kzdr:
                intent.putExtra("type","1");
                break;
            case R.id.ll_zcjy:
                intent.putExtra("type","2");
                break;
            case R.id.ll_shyq:
                intent.putExtra("type","3");
                break;
            case R.id.ll_qypx:
                intent.putExtra("type","4");
                break;
        }
        startActivity(intent);
    }

    public class TJCourseAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return tjCourseList.size();
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
                convertView = View.inflate(mActivity,R.layout.tjcourse_item_gv,null);
                holder.ll_gv_item = (LinearLayout) convertView.findViewById(R.id.ll_gv_item);
                holder.tv_tj_dianjiliang = (TextView) convertView.findViewById(R.id.tv_tj_dianjiliang);
                holder.tv_tj_keshi = (TextView) convertView.findViewById(R.id.tv_tj_keshi);
                holder.tv_tj_lession = (TextView) convertView.findViewById(R.id.tv_tj_lession);
                holder.tv_tj_lession_des = (TextView) convertView.findViewById(R.id.tv_tj_lession_des);
                holder.iv_tj_lession = (ImageView) convertView.findViewById(R.id.iv_tj_lession);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            //数据填充
            GetTJCourseBean.CourseBean courseBean = tjCourseList.get(position);
            holder.tv_tj_lession.setText(courseBean.title);
            holder.tv_tj_lession_des.setText(courseBean.info);
            holder.tv_tj_keshi.setText(courseBean.keshi+"课时");
            holder.tv_tj_dianjiliang.setText(courseBean.hot+"");
            //自动调整图片大小
            ImageOptions build = new ImageOptions.Builder().setImageScaleType(ImageView.ScaleType.FIT_XY).build();
            //获取imageView
            x.image().bind(holder.iv_tj_lession,courseBean.img,build);

            holder.ll_gv_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO   视频播放


                }
            });
            return convertView;
        }
        class ViewHolder {
            LinearLayout ll_gv_item;
            TextView tv_tj_dianjiliang;
            TextView tv_tj_keshi;
            TextView tv_tj_lession;
            TextView tv_tj_lession_des;
            ImageView iv_tj_lession;
        }
    }
    //获取百分课模块的数据
    private void getBfk() {
        RequestParams params = new RequestParams(ConstantUtil.PATH);
        params.addBodyParameter("Action","GetCourse100");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                GetCourse100Bean getCourse100Bean = gson.fromJson(result, GetCourse100Bean.class);
                courseList = getCourse100Bean.Course;
                ImageOptions.Builder builder = new ImageOptions.Builder();
                builder.setImageScaleType(ImageView.ScaleType.FIT_XY);
                ImageOptions build = builder.build();
                x.image().bind(iv_bfk_pic01,courseList.get(0).img,build);
                x.image().bind(iv_bfk_pic02,courseList.get(1).img,build);
                x.image().bind(iv_bfk_pic03,courseList.get(2).img,build);
                tv_bfk_01.setText(courseList.get(0).title);
                tv_bfk_02.setText(courseList.get(1).title);
                tv_bfk_03.setText(courseList.get(2).title);
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
    //获取轮播图数据
    private void getTurnsPic() {
        RequestParams params = new RequestParams(ConstantUtil.PATH);
        params.addBodyParameter("Action","GetSlides");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //解析Gson
                Gson gson = new Gson();
                GetSlidesBean getSlidesBean = gson.fromJson(result, GetSlidesBean.class);
                slidesBeanList = getSlidesBean.Slides;
                vp_home.setAdapter(new TurnsPicAdapter());

                //开始轮播图的自动切换
                handler.removeCallbacksAndMessages(null);
                handler.sendEmptyMessageDelayed(0,3000);

                //删除之前添加的点
                ll_image_point.removeAllViews();
                //添加默认的点
                for (int i = 0; i < slidesBeanList.size(); i++) {
                    ImageView normal_point = new ImageView(mActivity);
                    normal_point.setEnabled(false);
                    normal_point.setBackgroundResource(R.drawable.point_selector);
                    int dip2px = DensityUtil.dip2px(mActivity, 7.0f);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dip2px, dip2px);
                    if (i!=0){
                        params.leftMargin =  DensityUtil.dip2px(mActivity, 3.0f);
                        params.bottomMargin = DensityUtil.dip2px(mActivity, 10.0f);
                    }
                    normal_point.setLayoutParams(params);
                    ll_image_point.addView(normal_point);
                }
                //点的默认处理
                ll_image_point.getChildAt(vp_home.getCurrentItem()).setEnabled(true);
                vp_home.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        //将前一个点变为非选中状态

                        ll_image_point.getChildAt(preSelectPoint%slidesBeanList.size()).setEnabled(false);
                        //对应的点变为红色选中状态
                        ll_image_point.getChildAt(position%slidesBeanList.size()).setEnabled(true);

                        preSelectPoint=position;
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
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
    class TurnsPicAdapter extends PagerAdapter{
        //设置轮播图的最大数量
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
        //填充轮播图的数据
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            final ImageView imageView = new ImageView(mActivity);
            imageView.setBackgroundResource(R.mipmap.guodu_icon);
            String picURL = slidesBeanList.get(position%slidesBeanList.size()).pic;
            x.image().bind(imageView,picURL);
            container.addView(imageView);
            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            handler.removeCallbacksAndMessages(null);
                            break;
                        case MotionEvent.ACTION_UP:
                            handler.sendEmptyMessageDelayed(0,3000);
                            //图片点击事件
                            if (slidesBeanList.get(position%slidesBeanList.size()).pictype.equals("app")){
                                Intent intent = new Intent(mActivity, ZTShowActivity.class);
                                intent.putExtra("ztid", slidesBeanList.get(position%slidesBeanList.size()).picURL);
                                startActivity(intent);
                            }else if(slidesBeanList.get(position%slidesBeanList.size()).pictype.equals("web")){
                                // "pictype":"app"（值为APP时候连接APP的专题页，为web时候连接到picUR，
                                // *注：连接到picUR后需要传递两个参数acode=ACODE值&uid=用户帐号）
                                String url = slidesBeanList.get(position % slidesBeanList.size()).picURL;
                                if (SpUtil.getBoolean(MainActivity.IS_LOGIN,mActivity)){
                                    String acode = SpUtil.getString(LoginActivity.ACODE, mActivity);
                                    String username = SpUtil.getString(LoginActivity.USERNAME, mActivity);
                                    url = url+"?acode="+acode+"&uid="+username;

                                }
                                Intent intent = new Intent(mActivity, WebViewActivity.class);
                                intent.putExtra("title","网站");
                                intent.putExtra("url", url);
                                startActivity(intent);
                            }


                            break;
                        case MotionEvent.ACTION_CANCEL:
                            handler.sendEmptyMessageDelayed(0,3000);
                            break;
                    }
                    return true;
                }
            });
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_home_layout, null);
        ib_seach = (ImageButton) view.findViewById(R.id.ib_search);
        ib_sign = (ImageButton) view.findViewById(R.id.ib_sign);
        ib_scan = (ImageButton) view.findViewById(R.id.ib_scan);
        vp_home = (ViewPager) view.findViewById(R.id.vp_home);
        ll_image_point = (LinearLayout) view.findViewById(R.id.ll_image_points);
        ll_bfk01 = (LinearLayout) view.findViewById(R.id.ll_bfk01);
        ll_bfk02 = (LinearLayout) view.findViewById(R.id.ll_bfk02);
        ll_bfk03 = (LinearLayout) view.findViewById(R.id.ll_bfk03);
        iv_bfk_pic01 = (ImageView) view.findViewById(R.id.iv_bfk_pic01);
        iv_bfk_pic02 = (ImageView) view.findViewById(R.id.iv_bfk_pic02);
        iv_bfk_pic03 = (ImageView) view.findViewById(R.id.iv_bfk_pic03);
        tv_bfk_01 = (TextView) view.findViewById(R.id.tv_bfk_01);
        tv_bfk_02 = (TextView) view.findViewById(R.id.tv_bfk_02);
        tv_bfk_03 = (TextView) view.findViewById(R.id.tv_bfk_03);
        gv_tj = (NoSrollGridview) view.findViewById(R.id.gv_tj);
        ll_kzdr = (LinearLayout) view.findViewById(R.id.ll_kzdr);
        ll_zcjy = (LinearLayout) view.findViewById(R.id.ll_zcjy);
        ll_shyq = (LinearLayout) view.findViewById(R.id.ll_shyq);
        ll_qypx = (LinearLayout) view.findViewById(R.id.ll_qypx);
        rl_more_tj = (RelativeLayout) view.findViewById(R.id.rl_more_tj);
        initData();
        return view;
    }
}
