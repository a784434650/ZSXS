package com.huida.zsxs.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huida.zsxs.R;
import com.huida.zsxs.pager.SearchPager;
import com.huida.zsxs.utils.DensityUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

@ContentView(R.layout.activity_search)
public class SearchActivity extends Activity implements View.OnClickListener{
    @ViewInject(R.id.bt_search_back)
    Button bt_search_back;
    @ViewInject(R.id.et_search_list)
    EditText et_search_list;
    @ViewInject(R.id.tv_search)
    TextView tv_search;
    @ViewInject(R.id.search_shipin_tv)
    TextView search_shipin_tv;
    @ViewInject(R.id.search_yinpin_tv)
    TextView search_yinpin_tv;
    @ViewInject(R.id.search_dushu_tv)
    TextView search_dushu_tv;
    @ViewInject(R.id.search_wenzhang_tv)
    TextView search_wenzhang_tv;
    @ViewInject(R.id.search_view_line)
    View search_view_line;
    @ViewInject(R.id.vp_search)
    ViewPager vp_search;
    @ViewInject(R.id.ib_clear_edit)
    ImageButton ib_clear_edit;
    private ArrayList<SearchPager> pagerList;
    private TextView[] tvList;
    private int screenWidth;
    private RelativeLayout.LayoutParams params;
    public static boolean is_init = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        bt_search_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initData();
        initListener();

    }

    private void initData() {
        tvList = new TextView[]{search_shipin_tv,search_yinpin_tv,search_dushu_tv,search_wenzhang_tv};
        pagerList = new ArrayList<SearchPager>();
        for (int i = 0;i<4;i++){
            pagerList.add(new SearchPager(SearchActivity.this,i,et_search_list));
        }
        vp_search.setAdapter(new contentAdapter());
        //默认初始化第一页
        pagerList.get(0).setInitData();
        //指示器的宽度的初始化
        screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        params = (RelativeLayout.LayoutParams) search_view_line.getLayoutParams();
        params.width = screenWidth /pagerList.size();
        search_view_line.setLayoutParams(params);

        //四个页签的单击事件
       search_shipin_tv.setOnClickListener(this);
       search_yinpin_tv.setOnClickListener(this);
       search_dushu_tv.setOnClickListener(this);
       search_wenzhang_tv.setOnClickListener(this);
        vp_search.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0;i<tvList.length;i++){
                    if (i==position){
                        tvList[i].setTextColor(getResources().getColor(R.color.pager_focus_textcolor));
                    }else {
                        tvList[i].setTextColor(getResources().getColor(R.color.pager_nomal_textcolor));
                    }
                }
                //移动指示器
                int i = DensityUtil.dip2px(SearchActivity.this, 10);
                // 指示器移动的距离 = 一个指示器的宽度+position*一个指示器的宽度
                int offsetX = (int) (screenWidth /pagerList.size()-i+(position-1)*(screenWidth /pagerList.size()));

                params.leftMargin = offsetX+i ;//设置左边距
                search_view_line.setLayoutParams(params);
                //pagerList.get(position).setInitData();
                if (!is_init){
                    pagerList.get(position).setInitData();
                }else{
                    String et_list = et_search_list.getText().toString().trim();
                    pagerList.get(position).setSearchData(et_list,position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_shipin_tv:
                vp_search.setCurrentItem(0);
                break;
            case R.id.search_yinpin_tv:
                vp_search.setCurrentItem(1);
                break;
            case R.id.search_dushu_tv:
                vp_search.setCurrentItem(2);
                break;
            case R.id.search_wenzhang_tv:
                vp_search.setCurrentItem(3);
                break;
        }
    }

    public class contentAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return pagerList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(pagerList.get(position).rootView);
            return pagerList.get(position).rootView;
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
    private void initListener() {
        //搜索的点击事件
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String et_list = et_search_list.getText().toString().trim();
                if (TextUtils.isEmpty(et_list)){
                    Toast.makeText(SearchActivity.this, "请输入您要搜索的内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                //搜索的内容
                pagerList.get(vp_search.getCurrentItem()).setSearchData(et_list,vp_search.getCurrentItem());
                is_init = true;
            }
        });
        et_search_list.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //显示小X号
                ib_clear_edit.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                String et_list = et_search_list.getText().toString().trim();
                if (TextUtils.isEmpty(et_list)){
                    //隐藏小X号
                    ib_clear_edit.setVisibility(View.INVISIBLE);
                    //最近搜索
                    is_init = false;
                    pagerList.get(vp_search.getCurrentItem()).setInitData();


                }
            }
        });
        ib_clear_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_search_list.setText("");
            }
        });
    }
}
