package es.source.code.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import es.source.code.activity.FoodDetailed;
import es.source.code.activity.R;
import es.source.code.adapter.FoodRvAdapter;
import es.source.code.utils.Final;
import es.source.code.model.Food;

public class FoodFragment extends Fragment implements FoodRvAdapter.ItemClickListener{
    private RecyclerView recyclerView;
    private int position;

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_food_fragment, container, false);
        recyclerView = view.findViewById(R.id.rv);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity (), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new FoodRvAdapter(getActivity(),this, getData()));
        return view;
    }

    @Override
    public void onItemClick(View view) {
        Intent intent = new Intent(getContext(), FoodDetailed.class);
        Bundle bundle = new Bundle();
        bundle.putInt("position", recyclerView.getChildAdapterPosition(view));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onItemBtnClick(View view) {
        Toast.makeText(getContext(), Final.AppTip.ORDER_SUCCESS_TIP, Toast.LENGTH_SHORT).show();
        View itemView = recyclerView.getLayoutManager().findViewByPosition((Integer) view.getTag());
        // 设置button状态
        Button cancelBtn = itemView.findViewById(R.id.list_btn_cancel_order);
        Button orderBtn = itemView.findViewById(R.id.list_btn_order);
        cancelBtn.setVisibility(View.VISIBLE);
        orderBtn.setVisibility(View.GONE);
    }

    private List<Food> getData() {
        List<Food> list = new ArrayList<>();
        list.add(new Food("hmjmf","黄焖鸡米饭", 22.0));
        list.add(new Food("hmjmf","黄焖鸡米饭", 22.0));
        list.add(new Food("hmjmf","黄焖鸡米饭", 22.0));
        list.add(new Food("hmjmf","黄焖鸡米饭", 22.0));
        list.add(new Food("hmjmf","黄焖鸡米饭", 22.0));
        list.add(new Food("hmjmf","黄焖鸡米饭", 22.0));
        list.add(new Food("hmjmf","黄焖鸡米饭", 22.0));
        list.add(new Food("hmjmf","黄焖鸡米饭", 22.0));
        list.add(new Food("hmjmf","黄焖鸡米饭", 22.0));
        list.add(new Food("hmjmf","黄焖鸡米饭", 22.0));
        list.add(new Food("hmjmf","黄焖鸡米饭", 22.0));
        list.add(new Food("hmjmf","黄焖鸡米饭", 22.0));
        list.add(new Food("hmjmf","黄焖鸡米饭", 22.0));
        list.add(new Food("hmjmf","黄焖鸡米饭", 22.0));
        list.add(new Food("hmjmf","黄焖鸡米饭", 22.0));
        list.add(new Food("hmjmf","黄焖鸡米饭", 22.0));
        list.add(new Food("hmjmf","黄焖鸡米饭", 22.0));
        list.add(new Food("hmjmf","黄焖鸡米饭", 22.0));
        list.add(new Food("hmjmf","黄焖鸡米饭", 22.0));
        list.add(new Food("hmjmf","黄焖鸡米饭", 22.0));
        list.add(new Food("hmjmf","黄焖鸡米饭", 22.0));
        list.add(new Food("hmjmf","黄焖鸡米饭", 22.0));

        return list;
    }
}
