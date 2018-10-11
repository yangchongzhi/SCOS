package es.source.code.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import es.source.code.adapter.FoodDetailViewPagerAdapter;
import es.source.code.model.Food;

public class FoodDetailed extends AppCompatActivity {
    private ViewPager vp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detailed);

        vp = findViewById(R.id.vp);
        vp.setAdapter(new FoodDetailViewPagerAdapter(getSupportFragmentManager(),this, getData()));


//        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();
//        Toast.makeText(this, bundle.getInt("position") + "", Toast.LENGTH_SHORT).show();
    }

    private List<Food> getData() {
        List<Food> list = new ArrayList<>();
        list.add(new Food("hmjmf", "黄焖鸡米饭", 22.0, "好吃", "@drawable/logo1"));
        list.add(new Food("hcd1", "好吃的1", 22.0, "好吃", "@drawable/logo1"));
        list.add(new Food("hcd2", "好吃的2", 22.0, "好吃", "@drawable/logo1"));
        list.add(new Food("hcd3", "好吃的3", 22.0, "好吃", "@drawable/logo1"));
        list.add(new Food("hcd4", "好吃的4", 22.0, "好吃", "@drawable/logo1"));
        list.add(new Food("hcd5", "好吃的5", 22.0, "好吃", "@drawable/logo1"));
        list.add(new Food("hcd6", "好吃的6", 22.0, "好吃", "@drawable/logo1"));
        list.add(new Food("hcd7", "好吃的7", 22.0, "好吃", "@drawable/logo1"));
        list.add(new Food("hcd8", "好吃的8", 22.0, "好吃", "@drawable/logo1"));

        return list;
    }

}
