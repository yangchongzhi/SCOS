package es.source.code.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.List;

import es.source.code.fragment.FoodDetailFragment;
import es.source.code.model.Food;

public class FoodDetailViewPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private List<Food> data;

    public FoodDetailViewPagerAdapter(FragmentManager fm, Context context, List<Food> data){
        super(fm);
        this.context = context;
        this.data = data;
    }


    @Override
    public Fragment getItem(int i) {
        FoodDetailFragment foodDetailFragment = new FoodDetailFragment();
        foodDetailFragment.setFood(data.get(i));
        return foodDetailFragment;
    }

    @Override
    public int getCount() {
        return data.size();
    }



}

