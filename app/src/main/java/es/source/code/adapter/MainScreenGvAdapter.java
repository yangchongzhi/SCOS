package es.source.code.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
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

import es.source.code.activity.FoodOrderView;
import es.source.code.activity.FoodView;
import es.source.code.activity.LoginOrRegister;
import es.source.code.activity.R;
import es.source.code.utils.Final;
import es.source.code.utils.ResourceOperation;


public class MainScreenGvAdapter extends BaseAdapter implements AdapterView.OnItemClickListener{
    private LayoutInflater inflater;
    private Context context;
    // MainScreen 导航栏按钮
    private List<Map<String, Object>> btnList= new ArrayList<>();

    public MainScreenGvAdapter(Context context) {
        this.context = context;
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
        return btnList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        Map<String, Object> btnMap= btnList.get(position);
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
//        holder.btn.setBackgroundResource(R.drawable.ic_account_blue);
        holder.btn.setText(btnMap.get("CNName").toString());
        // 设置图标显示
        Drawable drawable = context.getResources().getDrawable(ResourceOperation.getDrawable(btnMap.get("icon").toString(), "drawable"));
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        holder.btn.setCompoundDrawables(drawable,null, null, null);
        setBtnBackGroundColor(holder.btn, position);
        return convertView;
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


    private void setBtnBackGroundColor(Button btn, int position) {
        String colors[] = { "#DBDBDB", "#CAE1FF", "#EEE685", "#E0FFFF"};
        btn.setBackgroundColor(Color.parseColor(colors[position]));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent;
        switch(btnList.get(position).get("ENName").toString()) {
            case "order":
                intent = new Intent(context, FoodView.class);
                context.startActivity(intent);
                break;
            case "check":
                intent = new Intent(context, FoodOrderView.class);
                context.startActivity(intent);
                break;
            case "loginOrRegister":
                intent = new Intent(context, LoginOrRegister.class);
                (((Activity) context)).startActivityForResult(intent, Final.ActivityRequestCode.LOGIN_OR_REGISTER_CODE);
                break;
            case "help":
                Log.d("help", "help");
                break;
        }
    }


    class Holder {
        Button btn;

        Holder(View view) {
            btn = view.findViewById(R.id.btn);
        }
    }

}
