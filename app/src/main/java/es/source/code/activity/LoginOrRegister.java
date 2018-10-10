package es.source.code.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.source.code.activity.utils.Final;

public class LoginOrRegister extends AppCompatActivity {
    private ProgressDialog pDialog = null;
    private Button btn_login;
    private EditText et_login_name;
    private EditText et_login_password;
    private final String ERROR_TIP = "输入不符合规则";
    private  Button btn_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);
        btn_login = findViewById(R.id.btn_login);
        btn_return = findViewById(R.id.btn_return);
        et_login_name = findViewById(R.id.login_name);
        et_login_password = findViewById(R.id.login_password);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 检测输入是否合法
                if (!checkInput(et_login_name.getText().toString())) {
                    setError(et_login_name, false);
                    et_login_name.clearFocus();
                    return;
                }

                if (!checkInput(et_login_password.getText().toString())) {
                    setError(et_login_password, true);
                    et_login_password.clearFocus();
                    return;
                }

                // 显示进度条
                showPressDialogAndToMainScreen();
            }
        });

        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginOrRegister.this, MainScreen.class);
                intent.putExtra("msg_from_LoginOrRegister","Return");
                setResult(Final.ActivityRequestCode.LOGIN_OR_REGISTER_CODE, intent);
                finish();
            }
        });
        et_login_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && et_login_password.getText().toString().contains(ERROR_TIP)) {
                    et_login_password.setText("");
                } else {
                    // 此处为失去焦点时的处理内容
                }
            }
        });

        et_login_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && et_login_name.getText().toString().contains(ERROR_TIP)) {
                    et_login_name.setText("");
                } else {
                    // 此处为失去焦点时的处理内容
                }
            }
        });
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

        // 启动一个线程负责关闭对话框
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    pDialog.cancel();
                    // 跳转到MainScreen
                    Intent intent = new Intent(LoginOrRegister.this, MainScreen.class);
                    intent.putExtra("msg_from_LoginOrRegister","LoginSuccess");
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

    private void setError(EditText et,boolean flag) {
        et.setText(ERROR_TIP);
//        if (flag) {
//            et.setInputType(InputType.TYPE_CLASS_TEXT);
//        }
    }
}

