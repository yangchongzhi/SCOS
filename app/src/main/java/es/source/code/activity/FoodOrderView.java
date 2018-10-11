package es.source.code.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import es.source.code.adapter.FoodOrderViewPagerAdapter;
import es.source.code.model.User;
import es.source.code.utils.Final;
import es.source.code.utils.MyApplication;

public class FoodOrderView extends AppCompatActivity {

    private ViewPager vp;
    private TabLayout tl;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_order_view);

        user = MyApplication.getApp().getUser();
        vp = findViewById(R.id.vp);
        tl = findViewById(R.id.tl);
        tl.setupWithViewPager(vp);
        vp.setAdapter(new FoodOrderViewPagerAdapter(getSupportFragmentManager(),this));

        // 设置viewPager显示
        setViewPager();

    }

    private void setViewPager() {
        Intent intent = getIntent();
        try {
            String message = intent.getStringExtra(Final.ActivityTransferInfo.FROM_FV);
            vp.setCurrentItem((message.equals(Final.ActivityTransferInfo.FV_TO_ORDER)) ? 1 : 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
