package es.source.code.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import es.source.code.activity.R;
import es.source.code.model.Food;

public class OrderRvAdapter extends RecyclerView.Adapter<OrderRvAdapter.Holder> {

    private Context context;
    private OrderRvAdapter.ItemClickListener fragment;
    private List<Food> list;
    public OrderRvAdapter(Context context, OrderRvAdapter.ItemClickListener fragment, List<Food> list) {
        this.context = context;
        this.fragment = fragment;
        this.list = list;
    }

    @NonNull
    @Override
    public OrderRvAdapter.Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_order_list_item, viewGroup, false);
        return new OrderRvAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderRvAdapter.Holder holder, int i) {
        Food food = list.get(i);
        holder.nameTextView.setText(food.getName());
        holder.priceTextView.setText(food.getPrice().toString());
        holder.numberTextView.setText(food.getCount() + "");
        holder.remarkTextView.setText(food.getRemark());
        holder.itemView.setTag(i);
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
        private LinearLayout linearLayout;
        Holder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name);
            priceTextView = itemView.findViewById(R.id.price);
            numberTextView = itemView.findViewById(R.id.count);
            remarkTextView = itemView.findViewById(R.id.remark);
            linearLayout = itemView.findViewById(R.id.list_item_container);
            // 添加点击事件
            linearLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            fragment.onItemClick(view);
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view);
    }
}
