package es.source.code.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import es.source.code.activity.R;
import es.source.code.adapter.OrderRvAdapter;
import es.source.code.model.Food;
import es.source.code.model.User;
import es.source.code.utils.Final;
import es.source.code.utils.MyApplication;

public class OrderFragment extends Fragment implements OrderRvAdapter.ItemClickListener{
    private RecyclerView recyclerView;
    private Button submitBtn;
    private TextView countTextView;
    private TextView priceTextView;
    private ProgressBar progressBar;
    private User user;
    private Context context;
    List<Food> foodList;
    MyAsyncTask myAsyncTask;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        foodList = getData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        user = MyApplication.getApp().getUser();

        View view = inflater.inflate(R.layout.layout_food_view_fragment, container, false);
        submitBtn = view.findViewById(R.id.submit_btn);
        countTextView = view.findViewById(R.id.count);
        priceTextView = view.findViewById(R.id.price);
        progressBar = view.findViewById(R.id.progress);

        countTextView.setText(getFoodCount() + "");
        priceTextView.setText(getFoodPrice() + "");

        recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity (), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new OrderRvAdapter(getActivity(),this, foodList));

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
                submitBtn.setClickable(false);
                double discountValue = 1;
                if(!user.getUserName().equals("") && user.getOldUser()) {
                    discountValue = 0.8;
                    Toast.makeText(context, Final.AppTip.DISCOUNT_TIP, Toast.LENGTH_SHORT).show();
                }

                myAsyncTask = new MyAsyncTask();
                myAsyncTask.execute(discountValue);
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

    private double getFoodPrice() {
        double count = 0;
        Iterator<Food> it = foodList.iterator();
        while (it.hasNext()){
            count += it.next().getPrice();
        }
        return count;
    }

    private int getFoodCount() {
        return foodList.size();
    }

    class MyAsyncTask extends AsyncTask<Double,Integer,Double> {


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //通过publishProgress方法传过来的值进行进度条的更新.
            progressBar.setProgress(values[0]);
        }

        @Override
        protected Double doInBackground(Double... doubles) {
            //使用for循环来模拟进度条的进度.
            for (int i = 0;i < 100; i ++){
                //调用publishProgress方法将自动触发onProgressUpdate方法来进行进度条的更新.
                publishProgress(i);
                try {
                    //通过线程休眠模拟耗时操作
                    Thread.sleep(60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return doubles[0];
        }

        @Override
        protected void onPostExecute(Double discountValue) {
            super.onPostExecute(discountValue);
            double foodPrice = getFoodPrice();
            Toast.makeText(context, "共支付" + foodPrice*discountValue +"元, 获得" + foodPrice + "积分", Toast.LENGTH_SHORT).show();
        }

    }
}
