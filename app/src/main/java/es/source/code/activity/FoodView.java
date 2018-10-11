package es.source.code.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import es.source.code.adapter.FoodViewPagerAdapter;
import es.source.code.model.User;
import es.source.code.utils.Final;
import es.source.code.utils.MyApplication;

public class FoodView extends AppCompatActivity {

    private ViewPager vp;
    private TabLayout tl;
    private User user;
    private Button btnOrderedFood;
    private Button btnOrderCheck;
    private Button btnCallService;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_view);

        user = MyApplication.getApp().getUser();
        // 初始化ViewPager
        vp = findViewById(R.id.vp);
        tl = findViewById(R.id.tl);
        tl.setupWithViewPager(vp);
        vp.setAdapter(new FoodViewPagerAdapter(getSupportFragmentManager(),this));

        // 绑定toolBar按钮事件
        btnOrderedFood = findViewById(R.id.btn_ordered_food);
        btnOrderCheck = findViewById(R.id.btn_order_check);
        btnCallService = findViewById(R.id.btn_call_service);
        bindBtnClick();

        toolbar =  findViewById(R.id.toolbar);


//        toolbar.setTitle(R.string.app_name);
//        toolbar.setNavigationIcon(R.mipmap.ic_launcher);

//        setSupportActionBar(toolbar);//如果需要给toolbar设置事件监听，需要将toolbar设置支持actionbar


    }

    private void bindBtnClick() {

        View.OnClickListener onClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (v.getId()) {
                    case R.id.btn_ordered_food:
                        intent = new Intent(FoodView.this, FoodOrderView.class);
                        intent.putExtra(Final.ActivityTransferInfo.FROM_FV, Final.ActivityTransferInfo.FV_TO_WAIT_ORDER);
                        startActivity(intent);
                        break;
                    case R.id.btn_order_check:
                        intent = new Intent(FoodView.this, FoodOrderView.class);
                        intent.putExtra(Final.ActivityTransferInfo.FROM_FV, Final.ActivityTransferInfo.FV_TO_ORDER);
                        startActivity(intent);
                        break;
                    case R.id.btn_call_service:
                        break;
                }
            }
        };

        btnOrderedFood.setOnClickListener(onClick);
        btnOrderCheck.setOnClickListener(onClick);
        btnCallService.setOnClickListener(onClick);
    }


}
