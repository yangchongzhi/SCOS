package es.source.code.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.source.code.model.User;
import es.source.code.utils.Final;
import es.source.code.utils.MyApplication;

public class LoginOrRegister extends AppCompatActivity {
    private NetHandler handler;

    private ProgressDialog pDialog = null;
    private Button loginBtn;
    private Button returnBtn;
    private Button registerBtn;

    private EditText loginNameEt;
    private EditText loginPasswordEt;

    private EditText loginNameTipEt;
    private EditText loginPasswordTipEt;
    private EditText loginTipEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);

        loginBtn = findViewById(R.id.btn_login);
        returnBtn = findViewById(R.id.btn_return);
        registerBtn = findViewById(R.id.btn_register);

        loginNameEt = findViewById(R.id.login_name);
        loginPasswordEt = findViewById(R.id.login_password);
        loginNameTipEt = findViewById(R.id.login_name_tip);
        loginPasswordTipEt = findViewById(R.id.login_password_tip);
        loginTipEt = findViewById(R.id.login_tip);
        handler = new NetHandler(this, loginTipEt);
        // 绑定按钮点击事件
        loginBtn.setOnClickListener(setBtnClick("login"));
        returnBtn.setOnClickListener(setBtnClick("return"));
        registerBtn.setOnClickListener(setBtnClick("register"));
        // 输入框focus事件
        loginPasswordEt.setOnFocusChangeListener(setEditTextFocus("password"));
        loginNameEt.setOnFocusChangeListener(setEditTextFocus("name"));

        // 设置按钮和输入框状态
        setWidgetState();
    }

    private View.OnClickListener setBtnClick(final String flag) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginTipEt.setVisibility(View.INVISIBLE);
                switch (flag) {
                    case "login":
                        handleLoginOrRegister(true);
                        break;
                    case "register":
                        handleLoginOrRegister(false);
                        break;
                    case "return":
                        backToMainScreen();
                        break;

                }
            }
        };

    }

    private void handleLoginOrRegister(boolean flag) {
        String userName = loginNameEt.getText().toString();
        // 检测输入是否合法
        if (!checkInput(userName)) {
            setError("name");
            return;
        }
        String password = loginPasswordEt.getText().toString();
        if (!checkInput(password)) {
            setError("password");
            return;
        }
        // 记录用户信息
        recordUserInfo(userName, password, flag);
        // 将信息写入SharedPreferences
        Map<String, Object> map = new HashMap<>();
        map.put("userName", userName);
        map.put("loginState", 1);
        setLoginInfoToSharedPreferences(map);
        // 显示进度条
        showPressDialogAndToMainScreen();
    }

    private void backToMainScreen() {
        if(!getLoginInfoFromSharedPreferences().get("userName").equals("")) {
            // 将信息写入SharedPreferences
            Map<String, Object> map = new HashMap<>();
            map.put("loginState", 0);
            setLoginInfoToSharedPreferences(map);
        }
        finish();
    }

    private View.OnFocusChangeListener setEditTextFocus(final String flag) {
        return new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                switch (flag) {
                    case "name":
                        if (hasFocus && !loginNameTipEt.getText().toString().equals("")) {
                            loginNameTipEt.setText("");
                            loginNameEt.setText("");
                        }
                        break;
                    case "password":
                        if (hasFocus && !loginPasswordTipEt.getText().toString().equals("")) {
                            loginPasswordTipEt.setText("");
                            loginPasswordEt.setText("");
                        }
                        break;
                }

            }
        };
    }

    private void showPressDialogAndToMainScreen() {
        pDialog = new ProgressDialog(LoginOrRegister.this);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        pDialog.setTitle("登录");

        pDialog.setMessage("正在登录……");
        // 设置ProgressDialog 标题图标
        pDialog.setIcon(R.drawable.logo1);
        pDialog.setProgress(100);
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
        login();
        // 启动一个线程负责关闭对话框
        new Thread(){
            @Override
            public void run() {
                try {

                    Thread.sleep(2000);
                    pDialog.cancel();


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }.start();

    }

    private boolean checkInput(String str) {
        String regEx = "^[A-Za-z0-9]+$";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        // 忽略大小写的写法
        Matcher matcher = pattern.matcher(str);
        // 字符串是否与正则表达式相匹配
        return matcher.matches();
    }

    private void setError(String type) {
        if(type.equals("name")) {
            loginNameTipEt.setText(Final.AppTip.INPUT_ERROR_TIP);
            loginNameEt.clearFocus();
        }
        if(type.equals("password")) {
            loginPasswordTipEt.setText(Final.AppTip.INPUT_ERROR_TIP);
            loginPasswordEt.clearFocus();
        }

    }

    private void recordUserInfo(String userName, String password, boolean oldUser) {
        User loginUser = new User();
        loginUser.setUserName(userName);
        loginUser.setPassword(password);
        loginUser.setOldUser(oldUser);
        MyApplication.getApp().setUser(loginUser);
    }

    private Map<String, Object> getLoginInfoFromSharedPreferences() {
        Map<String, Object> map = new HashMap<>();
        SharedPreferences sharedPreferences = getSharedPreferences(Final.SCOS_SP_Name, Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("userName", "");
        map.put("userName", userName);
        return map;
    }

    private void setLoginInfoToSharedPreferences(Map<String, Object> map) {
        SharedPreferences sharedPreferences = getSharedPreferences(Final.SCOS_SP_Name, Context.MODE_PRIVATE);
        //获取editor对象
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        //存储键值对
        Object userName = map.get("userName");
        if (userName != null) {
            editor.putString("userName", (String) userName);
        }
        Object loginState = map.get("loginState");
        if(loginState != null) {
            editor.putInt("loginState", (Integer) loginState);
        }
        //提交
        editor.apply();
    }

    private void setWidgetState() {
        Map<String, Object> map = getLoginInfoFromSharedPreferences();
        String userName = map.get("userName").toString();
        if (userName.equals("")) {
            loginBtn.setVisibility(View.GONE);
        } else {
            registerBtn.setVisibility(View.GONE);
            loginNameEt.setText(userName);
        }
    }

    private void login() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection;
                try {
                    URL url = new URL("http://10.0.2.2:8080/login");
                    connection = (HttpURLConnection) url.openConnection();

                    // 设置请求方式
                    connection.setRequestMethod("POST");
                    // 设置编码格式
                    connection.setRequestProperty("Charset", "UTF-8");
                    connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    // 传参数
                    DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                    StringBuilder param=new StringBuilder();
                    User user = MyApplication.getApp().getUser();
                    param.append("user=").append(user.getUserName()).append("&password=").append(user.getPassword());
                    outputStream.writeBytes(param.toString());
                    outputStream.flush();
                    outputStream.close();
                    // 连接
                    connection.connect();
                    // 成功请求
                    if(connection.getResponseCode() == 200) {
                        InputStream is = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
                        // 获取返回参数
                        String strRead;
                        StringBuilder sb = new StringBuilder();
                        while ((strRead = reader.readLine()) != null) {
                            sb.append(strRead);
                        }
                        reader.close();
                        // 把输出返回给主线程
                        Message message = handler.obtainMessage();
                        Bundle bundle = new Bundle();
                        bundle.putString("data", sb.toString());
                        message.setData(bundle);
                        handler.sendMessage(message);
                    }
                    pDialog.cancel();
                } catch (IOException e) {
                    pDialog.cancel();
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static class NetHandler extends Handler {
        private Context context;
        private EditText loginTip;

        NetHandler(Context context, EditText loginTip) {
            this.context = context;
            this.loginTip = loginTip;
        }

        @Override
        public void handleMessage(Message msg) {
            JSONObject jsonObject = JSON.parseObject(msg.getData().getString("data"));
            boolean loginSuccess = jsonObject.getString("RESULT_CODE").equals("1") ;
            if (loginSuccess) {
                // 跳转到MainScreen
                Intent intent = new Intent(context, MainScreen.class);
                Bundle bundle = new Bundle();
                bundle.putString(Final.ActivityTransferInfo.FROM_LOR, MyApplication.getApp().getUser().getOldUser() ? Final.ActivityTransferInfo.LOR_LOGIN_TO_MAIN : Final.ActivityTransferInfo.LOR_REGISTER_TO_MAIN);
                intent.putExtras(bundle);
                ((Activity) context).setResult(Final.ActivityRequestCode.LOGIN_OR_REGISTER_CODE, intent);
                ((Activity) context).finish();
            } else {
                loginTip.setVisibility(View.VISIBLE);
            }


        }
    }
}

