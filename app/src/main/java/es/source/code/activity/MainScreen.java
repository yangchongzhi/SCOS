package es.source.code.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import es.source.code.adapter.MainScreenGvAdapter;
import es.source.code.model.User;
import es.source.code.utils.Final;

public class MainScreen extends AppCompatActivity {
    private GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        // 初始化导航栏
        initNavigator();
        // 处理获取的message
        receiveMessage();
    }

    private void initNavigator() {
        gridView = findViewById(R.id.grid_view);
        MainScreenGvAdapter adapter = new MainScreenGvAdapter(MainScreen.this);
        gridView.setAdapter(adapter);
        // 设置点击事件
        gridView.setOnItemClickListener(adapter);
    }


    // 接收startActivity
    private void receiveMessage() {
//        Intent intent = getIntent();
//        String message = intent.getStringExtra(Final.ActivityTransferInfo.FROM_ENTRY);
//        if (!message.equals(Final.ActivityTransferInfo.ENTRY_TO_MAIN)) {
        int loginState = getLoginState();
        if(loginState != 1) {
            // 在进入页面后不能马上获取gridView的元素，要延迟一下
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        gridView.getChildAt(0).findViewById(R.id.btn).setVisibility(View.INVISIBLE);
                        gridView.getChildAt(1).findViewById(R.id.btn).setVisibility(View.INVISIBLE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            },50);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        String message;
        boolean isLogin = false;
        boolean isRegister = false;
        try {
            message = data.getStringExtra(Final.ActivityTransferInfo.FROM_LOR);
            isLogin = message.equals(Final.ActivityTransferInfo.LOR_LOGIN_TO_MAIN);
            isRegister = message.equals(Final.ActivityTransferInfo.LOR_REGISTER_TO_MAIN);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 通过loginState判断状态
        boolean loginState = getLoginState() == 1;
        isLogin = isLogin && loginState;
        isRegister = isRegister && loginState;

        if (isLogin || isRegister) {
            if(isRegister) Toast.makeText(MainScreen.this, Final.AppTip.REGISTER_TIP, Toast.LENGTH_LONG).show();
            Button orderBtn = gridView.getChildAt(0).findViewById(R.id.btn);
            if (orderBtn.getVisibility() == View.INVISIBLE){
                orderBtn.setVisibility(View.VISIBLE);
            }
            Button checkBtn = gridView.getChildAt(1).findViewById(R.id.btn);
            if (checkBtn.getVisibility() == View.INVISIBLE) {
                checkBtn.setVisibility(View.VISIBLE);
            }
        }
    }

    private int getLoginState() {
        SharedPreferences sharedPreferences = getSharedPreferences(Final.SCOS_SP_Name, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("loginState", 0);
    }
}
