package es.source.code.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import java.util.Map;
import es.source.code.activity.adapter.MainScreenGvAdapter;
import es.source.code.activity.utils.Final;

public class MainScreen extends AppCompatActivity {

//    private Button btn_order;
//    private Button btn_check;
//    private Button btn_login_register;
    private GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
//        btn_order = findViewById(R.id.btn_order);
//        btn_check = findViewById(R.id.btn_check);
//        btn_login_register = findViewById(R.id.btn_login_register);
//        btn_login_register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivityForResult(new Intent(MainScreen.this, LoginOrRegister.class), 1);
//            }
//        });

        // 初始化导航栏
        initNavigator();

//        setBtnVisibility();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        String result = data.getStringExtra("msg_from_LoginOrRegister");
        Log.d("#######################", result);
//        if (result.equals("LoginSuccess")){
//            if (btn_order.getVisibility() == View.INVISIBLE){
//                btn_order.setVisibility(View.VISIBLE);
//            }
//            if (btn_check.getVisibility() == View.INVISIBLE){
//                btn_check.setVisibility(View.VISIBLE);
//            }
//        }
    }

    private void initNavigator() {
        gridView = findViewById(R.id.grid_view);
        MainScreenGvAdapter adapter = new MainScreenGvAdapter(MainScreen.this, new MainScreenGvAdapter.OnNavigatorItemClick(){
            @Override
            public void onClick(Map<String, Object> btnMap) {
                OnNavigatorItemClick(btnMap);
            }
        });
        gridView.setAdapter(adapter);
        // 设置点击事件
        gridView.setOnItemClickListener(adapter);
    }

    private void OnNavigatorItemClick(Map<String, Object> btnMap) {
        switch(btnMap.get("ENName").toString()) {
            case "order":
                Log.d("order", "order");
                break;
            case "check":
                Log.d("check", "check");
                break;
            case "loginOrRegister":
                Log.d("loginOrRegister", "loginOrRegister");
                startActivityForResult(new Intent(MainScreen.this, LoginOrRegister.class), Final.ActivityRequestCode.LOGIN_OR_REGISTER_CODE);
                break;
            case "help":
                Log.d("help", "help");
                break;
        }
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        Log.d("#########", data.getStringExtra("msg_from_LoginOrRegister"));
//        super.onActivityReenter(resultCode, data);
    }

    //    private void setBtnVisibility() {
//        Intent intent = getIntent();
//        String data = intent.getStringExtra("msg_from_SCOSEntry");
//        Log.d("MainScreenActivity", data);
//        if (!data.equals("FromEntry")) {
//            btn_order.setVisibility(View.INVISIBLE);
//            btn_check.setVisibility(View.INVISIBLE);
//        }
//    }

}
