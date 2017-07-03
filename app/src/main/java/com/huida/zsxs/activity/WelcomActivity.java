package com.huida.zsxs.activity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.huida.zsxs.R;
import com.huida.zsxs.utils.ConstantUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by xiaojiu on 2017/6/22.
 */

public class WelcomActivity extends Activity {
    public static final String IS_OPENMAIN = "is_openmain" ;
    private ImageView iv_welcome;
    private String path =  Environment.getExternalStorageDirectory().getPath()+File.separator+"zhongshixueshe"+File.separator+"logo";;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置没有标题的页面
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcom);
        iv_welcome = (ImageView) findViewById(R.id.iv_welcome);
        if(new File(path).exists()){
            x.image().bind(iv_welcome,new File(path+"/guanggao_logo.jpg").toURI().toString());
        }
        getWelcomPicFromNet();
        initAnimation();
    }
//从网络获取图片
    private void getWelcomPicFromNet() {
        RequestParams params = new RequestParams(ConstantUtil.PATH);
        params.addBodyParameter("Action","getADpic");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String pic = jsonObject.getString("pic");
                    ImageOptions.Builder builder = new ImageOptions.Builder();
                    ImageOptions build = builder.setImageScaleType(ImageView.ScaleType.FIT_XY).build();
                    //加载文件（边读边写）
                    x.image().loadFile(pic, build, new CacheCallback<File>() {
                        @Override
                        public boolean onCache(File result) {
                            FileInputStream fis=null;
                            FileOutputStream fos=null;
                            try {
                                 fis = new FileInputStream(result);

                                File file =new File(path);
                                if(!file.exists()){
                                   file.mkdirs();
                                }
                                 fos = new FileOutputStream(file.getPath()+"/guanggao_logo.jpg");
                                byte[] bytes = new byte[1024];
                                int len = 0;
                                while((len=fis.read(bytes))!=0){
                                    fos.write(bytes,0,len);
                                }

                                fis.close();
                                fos.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            return true;
                        }

                        @Override
                        public void onSuccess(File result) {

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

                } catch (JSONException e) {
                    e.printStackTrace();
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

    private void enterHome() {
        //显示意图跳转界面（主界面）
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    //初始化动画（由暗到明）
    private void initAnimation() {
        //获取窗体对象
        final Window window = getWindow();
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.2f, 1f);
        valueAnimator.setDuration(3500);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                WindowManager.LayoutParams params = window.getAttributes();
                params.alpha= (float) animation.getAnimatedValue();
                window.setAttributes(params);
            }
        });
        valueAnimator.start();

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                enterHome();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
