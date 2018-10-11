package es.source.code.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import es.source.code.activity.R;
import es.source.code.adapter.OrderRvAdapter;
import es.source.code.model.Food;
import es.source.code.model.User;
import es.source.code.utils.Final;
import es.source.code.utils.MyApplication;

public class OrderFragment extends Fragment implements OrderRvAdapter.ItemClickListener{
    private RecyclerView recyclerView;
    private Button submitBtn;
    private User user;
    private Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        user = MyApplication.getApp().getUser();

        View view = inflater.inflate(R.layout.layout_food_view_fragment, container, false);
        submitBtn = view.findViewById(R.id.submit_btn);
        recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity (), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new OrderRvAdapter(getActivity(),this, getData()));

        bindSubmitClick();
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

    private void bindSubmitClick() {
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!user.getUserName().equals("") && user.getOldUser()) {
                    Toast.makeText(context, Final.AppTip.DISCOUNT_TIP, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

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
