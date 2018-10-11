package es.source.code.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.source.code.model.User;
import es.source.code.utils.Final;
import es.source.code.utils.MyApplication;

public class LoginOrRegister extends AppCompatActivity {
    private ProgressDialog pDialog = null;
    private Button loginBtn;
    private Button returnBtn;
    private Button registerBtn;

    private EditText loginNameEt;
    private EditText loginPasswordEt;

    private EditText loginNameTipEt;
    private EditText loginPasswordTipEt;

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

        // 绑定按钮点击事件
        loginBtn.setOnClickListener(setBtnClick("login"));
        returnBtn.setOnClickListener(setBtnClick("return"));
        registerBtn.setOnClickListener(setBtnClick("register"));
        // 输入框focus事件
        loginPasswordEt.setOnFocusChangeListener(setEditTextFocus("password"));
        loginNameEt.setOnFocusChangeListener(setEditTextFocus("name"));
    }

    private View.OnClickListener setBtnClick(final String flag) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (flag) {
                    case "login":
                        handleLoginOrRegister(true);
                        break;
                    case "register":
                        handleLoginOrRegister(false);
                        break;
                    case "return":
                        Intent intent = new Intent(LoginOrRegister.this, MainScreen.class);
                        Bundle bundle = new Bundle();
                        bundle.putString(Final.ActivityTransferInfo.FROM_LOR,Final.ActivityTransferInfo.LOR_BACK_TO_MAIN);
                        intent.putExtras(bundle);
                        setResult(Final.ActivityRequestCode.LOGIN_OR_REGISTER_CODE, intent);
                        finish();
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
        // 显示进度条
        showPressDialogAndToMainScreen(flag);
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

    private void showPressDialogAndToMainScreen(final boolean flag) {
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

        // 启动一个线程负责关闭对话框
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    pDialog.cancel();
                    // 跳转到MainScreen
                    Intent intent = new Intent(LoginOrRegister.this, MainScreen.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(Final.ActivityTransferInfo.FROM_LOR,flag ? Final.ActivityTransferInfo.LOR_LOGIN_TO_MAIN : Final.ActivityTransferInfo.LOR_REGISTER_TO_MAIN);
                    intent.putExtras(bundle);
                    setResult(Final.ActivityRequestCode.LOGIN_OR_REGISTER_CODE, intent);
                    finish();
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
}

