package es.source.code.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import es.source.code.activity.R;
import es.source.code.adapter.WaitOrderRvAdapter;
import es.source.code.model.Food;

public class WaitOrderFragment extends Fragment implements WaitOrderRvAdapter.ItemClickListener{
    private RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_food_view_frament2, container, false);
        recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity (), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new WaitOrderRvAdapter(getActivity(),this, getData()));
        return view;
    }

    @Override
    public void onItemClick(View view) {
//        Intent intent = new Intent(getContext(), FoodDetailed.class);
//        Bundle bundle = new Bundle();
//        bundle.putInt("position", recyclerView.getChildAdapterPosition(view));
//        intent.putExtras(bundle);
//        startActivity(intent);
    }

    @Override
    public void onItemBtnClick(View view) {}

    private List<Food> getData() {
        List<Food> list = new ArrayList<>();
        list.add(new Food("hmjmf","黄猛击米饭", 22.0, 2, "好吃"));
        list.add(new Food("hmjmf","黄猛击米饭", 22.0, 2, "好吃"));
        list.add(new Food("hmjmf","黄猛击米饭", 22.0, 2, "好吃"));
        list.add(new Food("hmjmf","黄猛击米饭", 22.0, 2, "好吃"));
        list.add(new Food("hmjmf","黄猛击米饭", 22.0, 2, "好吃"));
        list.add(new Food("hmjmf","黄猛击米饭", 22.0, 2, "好吃"));
        list.add(new Food("hmjmf","黄猛击米饭", 22.0, 2, "好吃"));
        list.add(new Food("hmjmf","黄猛击米饭", 22.0, 2, "好吃"));
        list.add(new Food("hmjmf","黄猛击米饭", 22.0, 2, "好吃"));
        list.add(new Food("hmjmf","黄猛击米饭", 22.0, 2, "好吃"));
        list.add(new Food("hmjmf","黄猛击米饭", 22.0, 2, "好吃"));
        list.add(new Food("hmjmf","黄猛击米饭", 22.0, 2, "好吃"));
        list.add(new Food("hmjmf","黄猛击米饭", 22.0, 2, "好吃"));
        list.add(new Food("hmjmf","黄猛击米饭", 22.0, 2, "好吃"));
        list.add(new Food("hmjmf","黄猛击米饭", 22.0, 2, "好吃"));

        return list;
    }

}

