package es.source.code.activity.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import es.source.code.activity.R;
import es.source.code.activity.utils.Icon;


public class MainScreenGvAdapter extends BaseAdapter implements AdapterView.OnItemClickListener{
    private LayoutInflater inflater;
    private Context context;
    private OnNavigatorItemClick onNavigatorItemClick;
    // MainScreen 导航栏按钮
    private List<Map<String, Object>> btnList= new ArrayList<>();

    public MainScreenGvAdapter(Context context, OnNavigatorItemClick onNavigatorItemClick) {
        this.context = context;
        this.onNavigatorItemClick = onNavigatorItemClick;
        inflater = LayoutInflater.from(context);
        // 初始化btnList
        initBtnList();
    }

    @Override
    public int getCount() {
        return btnList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;

        if (convertView == null){
            convertView = inflater.inflate(R.layout.layout_main_screen_gv_item,null);
            holder = new Holder(convertView);
//            // 设置icon
//            holder.btn.setTypeface(IconFont.getTypeFace(context));
//            holder.btn.setText(IconFont.getIcon(btnList.get(position).get("icon").toString()));
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        Map<String, Object> btnMap= btnList.get(position);
//        holder.btn.setBackgroundResource(R.drawable.ic_account_blue);
        holder.btn.setText(btnMap.get("CNName").toString());
        // 设置图标显示
        Drawable drawable = context.getResources().getDrawable(Icon.getDrawable(btnMap.get("icon").toString(), "drawable"));
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        holder.btn.setCompoundDrawables(drawable,null, null, null);
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        onNavigatorItemClick.onClick(btnList.get(position));
    }


    private void initBtnList() {
        // 点菜按钮信息
        Map<String, Object> orderMap = new HashMap<>();
        orderMap.put("CNName", "点菜");
        orderMap.put("ENName", "order");
        orderMap.put("icon", "ic_order_blue");
        btnList.add(orderMap);

        // 点菜按钮信息
        Map<String, Object> checkMap = new HashMap<>();
        checkMap.put("CNName", "查看订单");
        checkMap.put("ENName", "check");
        checkMap.put("icon", "ic_form_blue");
        btnList.add(checkMap);

        // 点菜按钮信息
        Map<String, Object> loginOrRegisterMap = new HashMap<>();
        loginOrRegisterMap.put("CNName", "登录/注册");
        loginOrRegisterMap.put("ENName", "loginOrRegister");
        loginOrRegisterMap.put("icon", "ic_account_blue");
        btnList.add(loginOrRegisterMap);

        // 点菜按钮信息
        Map<String, Object> helpMap = new HashMap<>();
        helpMap.put("CNName", "系统帮助");
        helpMap.put("ENName", "help");
        helpMap.put("icon", "ic_help_blue");
        btnList.add(helpMap);
    }

    class Holder {
        Button btn;

        Holder(View view) {
            btn = view.findViewById(R.id.btn);
        }
    }

    public interface OnNavigatorItemClick {
        void onClick(Map<String, Object> btnMap);
    }
}
