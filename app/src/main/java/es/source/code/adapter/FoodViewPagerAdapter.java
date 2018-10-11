package es.source.code.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import es.source.code.fragment.FoodFragment;
import es.source.code.utils.Final;

public class FoodViewPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private String[] category;

    public FoodViewPagerAdapter(FragmentManager fm, Context context){
        super(fm);
        this.context = context;
        this.category = Final.FoodArgs.CATEGORY_LIST;
    }


    @Override
    public Fragment getItem(int i) {
        FoodFragment foodFragment = new FoodFragment();
        foodFragment.setPosition(i);
        return foodFragment;
    }

    @Override
    public int getCount() {
        return category.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return category[position];
    }
}
