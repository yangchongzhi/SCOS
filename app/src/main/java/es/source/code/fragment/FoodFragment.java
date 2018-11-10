package es.source.code.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import es.source.code.activity.FoodDetailed;
import es.source.code.activity.R;
import es.source.code.adapter.FoodRvAdapter;
import es.source.code.utils.Final;
import es.source.code.model.Food;

public class FoodFragment extends Fragment implements FoodRvAdapter.ItemClickListener{
    private RecyclerView recyclerView;
    private static EventBus eventBus;
    private int position;
    private ArrayList<Food> list;
    private Handler updateHandler;

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        list = getData();
        View view = inflater.inflate(R.layout.layout_food_fragment, container, false);
        recyclerView = view.findViewById(R.id.rv);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity (), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new FoodRvAdapter(getActivity(),this, list));
        updateHandler = new UpdateHandler(recyclerView);

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

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        super.onDestroy();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        eventBus = EventBus.getDefault();
        eventBus.register(this);
        super.onCreate(savedInstanceState);
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void handleUpdateStatus(Food food) {
        String foodID = food.getId();
        for(int i = 0; i< list.size(); i++) {
            if(list.get(i).getId().equals(foodID)) {
                Message message = updateHandler.obtainMessage();
                message.arg1 = i;
                message.arg2 = food.getCount();
                updateHandler.sendMessage(message);
                return;
            }
        }

    }


    private ArrayList<Food> getData() {
        ArrayList<Food> list = new ArrayList<>();
        list.add(new Food("000001","麻辣香锅", 22.0, 19));
        list.add(new Food("000002","黄焖鸡米饭", 22.0, 19));
        list.add(new Food("000003","黄焖鸡米饭", 22.0, 19));
        list.add(new Food("000004","黄焖鸡米饭", 22.0, 19));
        list.add(new Food("000005","黄焖鸡米饭", 22.0, 19));
        list.add(new Food("000006","黄焖鸡米饭", 22.0, 19));
        list.add(new Food("hmjmf","黄焖鸡米饭", 22.0, 19));
        list.add(new Food("hmjmf","黄焖鸡米饭", 22.0, 19));
        list.add(new Food("hmjmf","黄焖鸡米饭", 22.0, 19));
        list.add(new Food("hmjmf","黄焖鸡米饭", 22.0, 19));
        list.add(new Food("hmjmf","黄焖鸡米饭", 22.0, 19));
        list.add(new Food("hmjmf","黄焖鸡米饭", 22.0, 19));
        list.add(new Food("hmjmf","黄焖鸡米饭", 22.0, 19));
        list.add(new Food("hmjmf","黄焖鸡米饭", 22.0, 19));
        list.add(new Food("hmjmf","黄焖鸡米饭", 22.0, 19));
        list.add(new Food("hmjmf","黄焖鸡米饭", 22.0, 19));
        list.add(new Food("hmjmf","黄焖鸡米饭", 22.0, 19));


        return list;
    }

    private static class UpdateHandler extends Handler {
        private RecyclerView recyclerView;

        UpdateHandler(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }

        @Override
        public void handleMessage(Message msg) {
            View itemView;
            itemView = recyclerView.getLayoutManager().findViewByPosition(msg.arg1);
            if (itemView != null) {
                TextView a = itemView.findViewById(R.id.count);
                a.setText(msg.arg2 + "");
            }

        }
    }

}
