package es.source.code.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import es.source.code.fragment.OrderFragment;
import es.source.code.fragment.WaitOrderFragment;
import es.source.code.utils.Final;

public class FoodOrderViewPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private String[] list;

    public FoodOrderViewPagerAdapter(FragmentManager fm, Context context){
        super(fm);
        this.context = context;
        list = Final.FoodArgs.FOOD_ORDERED_STATE;
    }


    @Override
    public Fragment getItem(int i) {
        switch(i)
        {
            case 0:
                return new WaitOrderFragment();
            case 1:
                return new OrderFragment();
        }
        return null;

    }

    @Override
    public int getCount() {
        return list.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return list[position];
    }
}
