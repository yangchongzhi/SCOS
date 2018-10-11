package es.source.code.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import es.source.code.activity.R;
import es.source.code.model.Food;

public class WaitOrderRvAdapter extends RecyclerView.Adapter<WaitOrderRvAdapter.Holder>  {

    private Context context;
    private WaitOrderRvAdapter.ItemClickListener fragment;
    private List<Food> list;
    public WaitOrderRvAdapter(Context context, WaitOrderRvAdapter.ItemClickListener fragment, List<Food> list) {
        this.context = context;
        this.fragment = fragment;
        this.list = list;
    }

    @NonNull
    @Override
    public WaitOrderRvAdapter.Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_wait_order_list_item, viewGroup, false);
        return new WaitOrderRvAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WaitOrderRvAdapter.Holder holder, int i) {
        Food food = list.get(i);
        holder.nameTextView.setText(food.getName());
        holder.priceTextView.setText(food.getPrice().toString());
        holder.numberTextView.setText(food.getCount() + "");
        holder.remarkTextView.setText(food.getRemark());
        holder.itemView.setTag(i);
        holder.cancelBtn.setTag(i);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        private TextView nameTextView;
        private TextView priceTextView;
        private TextView numberTextView;
        private TextView remarkTextView;
        private Button cancelBtn;
        private LinearLayout linearLayout;
        Holder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name);
            priceTextView = itemView.findViewById(R.id.price);
            numberTextView = itemView.findViewById(R.id.count);
            remarkTextView = itemView.findViewById(R.id.remark);
            cancelBtn = itemView.findViewById(R.id.cancel_btn);
            linearLayout = itemView.findViewById(R.id.list_item_container);
            // 添加点击事件
            linearLayout.setOnClickListener(this);
            cancelBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.list_item_container:
                    fragment.onItemClick(view);
                    break;
                case R.id.list_btn_order:
                    fragment.onItemBtnClick(view);
                    break;
            }
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view);
        void onItemBtnClick(View view);
    }
}

