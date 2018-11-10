package es.source.code.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import es.source.code.adapter.FoodViewPagerAdapter;
import es.source.code.model.Food;
import es.source.code.service.ServerObserverService;
import es.source.code.utils.Final;

public class FoodView extends AppCompatActivity {
    private static EventBus eventBus;
    private Intent serviceIntent;
    private ViewPager vp;
    private TabLayout tl;
    private Button btnOrderedFood;
    private Button btnOrderCheck;
    private Button btnCallService;
    private Button btnStartRefresh;
    private Button btnStopRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_view);

        eventBus = EventBus.getDefault();
        serviceIntent = new Intent(this, ServerObserverService.class);
        startService(serviceIntent);

        // 初始化ViewPager
        vp = findViewById(R.id.vp);
        tl = findViewById(R.id.tl);
        tl.setupWithViewPager(vp);
        vp.setAdapter(new FoodViewPagerAdapter(getSupportFragmentManager(),this));

        // 绑定toolBar按钮事件
        btnOrderedFood = findViewById(R.id.btn_ordered_food);
        btnOrderCheck = findViewById(R.id.btn_order_check);
        btnCallService = findViewById(R.id.btn_call_service);
        btnStartRefresh = findViewById(R.id.btn_start_update);
        btnStopRefresh = findViewById(R.id.btn_stop_update);
        bindBtnClick();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(serviceIntent);
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
                    case R.id.btn_start_update:
                    case R.id.btn_stop_update:
                        boolean flag = v.getId() == R.id.btn_start_update;
                        eventBus.post(new NotifyUpdateStatus(flag ? 1 : 0));
                        btnStopRefresh.setVisibility(flag ? View.VISIBLE : View.INVISIBLE);
                        btnStartRefresh.setVisibility(flag ? View.INVISIBLE : View.VISIBLE);
                        break;
                    case R.id.btn_call_service:
                        break;
                }
            }
        };

        btnOrderedFood.setOnClickListener(onClick);
        btnOrderCheck.setOnClickListener(onClick);
        btnCallService.setOnClickListener(onClick);
        btnStartRefresh.setOnClickListener(onClick);
        btnStopRefresh.setOnClickListener(onClick);
    }

    public static class NotifyUpdateStatus {
        private int status;
        public NotifyUpdateStatus(int status) {
            this.status = status;
        }

        public int getStatus() {
            return status;
        }
    }

}
