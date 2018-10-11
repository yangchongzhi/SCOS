package es.source.code.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import es.source.code.activity.R;
import es.source.code.model.Food;

public class FoodDetailFragment extends Fragment {
    private Food food;

    private TextView tvName;
    private TextView tvPrice;
    private ImageView imageView;

    private EditText etRemark;

    private Button btnOrder;
    private Button btnCancel;

    public void setFood(Food food) {
        this.food = food;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_food_detail, container, false);
        tvName = view.findViewById(R.id.name);
        tvPrice = view.findViewById(R.id.price);
        imageView = view.findViewById(R.id.image);
        etRemark = view.findViewById(R.id.et_remark);
        btnOrder = view.findViewById(R.id.list_btn_order);
        btnCancel = view.findViewById(R.id.list_btn_cancel);

        tvName.setText(food.getName());
        tvPrice.setText(food.getPrice().toString());
        imageView.setImageResource(R.drawable.logo1);
        etRemark.setText("");

        if (foodIsOrdered(food)) {
            btnCancel.setVisibility(View.VISIBLE);
            btnOrder.setVisibility(View.GONE);
        } else {
            btnCancel.setVisibility(View.GONE);
            btnOrder.setVisibility(View.VISIBLE);
        }

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;

    }

    private boolean foodIsOrdered(Food food) {
       return food.getId().equals("hmjmf");
    }
}

