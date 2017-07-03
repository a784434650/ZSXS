package com.huida.zsxs.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.huida.zsxs.R;
import com.huida.zsxs.bean.LoginInfoBean;
import com.huida.zsxs.bean.User;
import com.huida.zsxs.utils.ConstantUtil;
import com.huida.zsxs.utils.DensityUtil;
import com.huida.zsxs.utils.GetIpUtil;
import com.huida.zsxs.utils.MD5Util;
import com.huida.zsxs.utils.SaveUserUtils;
import com.huida.zsxs.utils.SpUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by xiaojiu on 2017/6/25.
 */

public class LoginActivity extends Activity implements View.OnClickListener{
    private EditText password;
    private EditText username;
    private Button bt_login;
    private Button bt_forget;
    private Button back;
    private TextView register;
    private CheckBox cb_rem_pwd;
    private AlertDialog alertDialog;
    public static final String IS_CHECKED_PWD = "is_checked_pwd";
    public static final String USERNAME = "username";
    public static final String PWD = "pwd";
    public static final String ACODE = "acode";
    private String pwd;
    private ArrayList<User> datas;
    private String user;
    private ImageButton ib_login_showuser;
    private boolean flags = false;
    private PopupWindow popupWindow;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        initView();
        initData();
        initlistener();
    }

    private void initData() {
        boolean is_check = SpUtil.getBoolean(IS_CHECKED_PWD, LoginActivity.this);
        if (is_check){
            String userName = SpUtil.getString(USERNAME, LoginActivity.this);
            String pwd = SpUtil.getString(PWD, LoginActivity.this);
            username.setText(userName);
            password.setText(pwd);
            cb_rem_pwd.setChecked(is_check);
        }
        //获取保存的用户
        getUser();
    }

    private void getUser() {
        ib_login_showuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //下拉三角判断是否弹出下拉框
                if (!flags){
                    showPopUpWindow();
                }else{
                    flags = false;
                }
            }
        });
        //自定义ListView作为pop的内部布局
        listView = new ListView(this);
        datas = SaveUserUtils.getUserList(LoginActivity.this);
        listView.setDivider(null);//设置分割线
        listView.setVerticalScrollBarEnabled(false);//不显示滑动条
        listView.setAdapter(new MyAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                username.setText(datas.get(position).getmId());
                password.setText(datas.get(position).getmPwd());
                popupWindow.dismiss();
            }
        });

    }
    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return datas.size();
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
            TextView textView = new TextView(LoginActivity.this);
            textView.setText(datas.get(position).getmId());
            textView.setTextSize(18);
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(0,8,0,0);
            return textView;
        }
    }


    private void initView() {
        password = (EditText) findViewById(R.id.et_login_password);
        username = (EditText) findViewById(R.id.et_login_username);
        bt_login = (Button) findViewById(R.id.bt_login_login);
        bt_forget = (Button) findViewById(R.id.bt_login_forget);
        back = (Button) findViewById(R.id.bt_login_back);
        register = (TextView) findViewById(R.id.tv_login_register);
        cb_rem_pwd = (CheckBox) findViewById(R.id.cb_rem_pwd);
        ib_login_showuser = (ImageButton) findViewById(R.id.ib_login_showuser);
        Drawable drawable = getResources().getDrawable(R.drawable.checkbox_selector);
        int i = DensityUtil.dip2px(LoginActivity.this, 25);
        drawable.setBounds(0,0,i,i);
        cb_rem_pwd.setCompoundDrawables(drawable,null,null,null);

    }

    private void initlistener() {
        back.setOnClickListener(this);
        register.setOnClickListener(this);
        bt_login.setOnClickListener(this);
        bt_forget.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_login_back:
                finish();
                break;
            case R.id.tv_login_register:
                Intent regintent=new Intent(this,RegisterActivity.class);
                startActivity(regintent);
                break;
            case R.id.bt_login_login:
                //登录对话框
                LoginDialog();
                break;
            case R.id.bt_login_forget:
                //忘记密码,找回
                Intent intent = new Intent(LoginActivity.this, FindPwdActivity.class);
                startActivity(intent);
                break;
        }
    }


    private void showPopUpWindow() {
        flags = true;
        popupWindow = new PopupWindow(this);
        popupWindow.setWidth(username.getWidth());
        popupWindow.setHeight(200);
        //listView填充到pop里
        popupWindow.setContentView(listView);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#fff5f5f5")));
        popupWindow.setOutsideTouchable(true);//点pop外边则收回
        popupWindow.showAsDropDown(username,0,0);//以username为起点弹出
    }

    //登录对话框(自定义对话框)
    private void LoginDialog() {
        /*新主题（5.0以上的app.theme）
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.login_dialog_layout, null);//获取自定义布局
        builder.setView(layout);
        alertDialog = builder.create();
        alertDialog.show();
        Button ib_cancel = (Button) layout.findViewById(R.id.ib_cancel);
        ib_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                return;
            }
        });*/
        //老主题（5.0以下android.theme）
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.show();
        alertDialog.setCancelable(false);
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.login_dialog_layout);
        window.findViewById(R.id.ib_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                return;
            }
        });
        //登录
        Login();
    }

    private void Login() {
        user = username.getText().toString().trim();
        pwd = password.getText().toString().trim();
        if (TextUtils.isEmpty(user)){
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pwd)){
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String md5Pwd = MD5Util.getMd5Value(pwd);
        String psdIp = GetIpUtil.getPsdIp();
        RequestParams params = new RequestParams(ConstantUtil.PATH);
        params.addBodyParameter("Action","Login");
        params.addBodyParameter("user", user);
        params.addBodyParameter("pwd",md5Pwd);
        params.addBodyParameter("ip",psdIp);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                alertDialog.dismiss();
                if ("errorMessage".equals(result)){
                    //发送用户密码错误对话框
                    showErrorDialog();
                    return;
                }
                Gson gson = new Gson();
                LoginInfoBean loginInfo = gson.fromJson(result, LoginInfoBean.class);
                //SpUtil.putBoolean(MainActivity.IS_LOGIN,true,LoginActivity.this);
                //记住密码
                if (cb_rem_pwd.isChecked()){
                    SpUtil.putBoolean(IS_CHECKED_PWD,true,LoginActivity.this);
                }else{
                    SpUtil.putBoolean(IS_CHECKED_PWD,false,LoginActivity.this);
                }
                SpUtil.putString(USERNAME,loginInfo.username,LoginActivity.this);
                SpUtil.putString(PWD,pwd,LoginActivity.this);
                SpUtil.putString(ACODE,loginInfo.acode,LoginActivity.this);
                SpUtil.putBoolean(MainActivity.IS_LOGIN,true,LoginActivity.this);
                //保存登录过的用户
                saveLoginedUser();
                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                finish();
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
    //保存登录过的用户
    private void saveLoginedUser() {
        boolean mIsSave = true;
        //判断此用户是否存过
        for (User user : datas) {
            if (user.getmId().equals(user)&&user.getmPwd().equals(pwd)) {
                mIsSave = false;
                break;
            }
        }
        if (mIsSave){
            User users = new User(user, pwd);
            datas.add(users);
        }

    }
    //优化（activity退出后把数据保存到本地）
    @Override
    protected void onPause() {
        super.onPause();
        try {
            SaveUserUtils.saveUserList(this,datas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //打出密码或用户错误的对话框
    private void showErrorDialog() {
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.show();
        alertDialog.setCancelable(false);
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.loading_error_dialog);
        window.findViewById(R.id.ib_submit_buy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        TextView tv_message = (TextView) window.findViewById(R.id.tv_message);
        tv_message.setText("账号或密码错误");
    }
}
