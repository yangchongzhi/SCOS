package es.source.code.adapter;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.source.code.activity.FoodOrderView;
import es.source.code.activity.FoodView;
import es.source.code.activity.R;
import es.source.code.utils.Final;

public class HelperAdapter extends BaseAdapter  implements AdapterView.OnItemClickListener{
    private Context context;
    private LayoutInflater inflater;

    // 帮助列表
    private List<Map<String, Object>> helperList= new ArrayList<>();

    public HelperAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        // 初始化btnList
        initHelperList();
    }
    @Override
    public int getCount() {
        return helperList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        Map<String, Object> map= helperList.get(position);
        if (convertView == null){
            convertView = inflater.inflate(R.layout.layout_helper_gv_item,null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.btn.setText(map.get("helperOption").toString());
        return convertView;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent;
        switch(helperList.get(position).get("helpType").toString()) {
            case "userProtocol":
//                intent = new Intent(context, FoodView.class);
//                context.startActivity(intent);
                break;
            case "system":
//                intent = new Intent(context, FoodOrderView.class);
//                context.startActivity(intent);
                break;
            case "phone":
                callPhone();
                break;
            case "message":
                sendMessage();
                break;
            case "mail":
                Toast.makeText(context, "求助邮件已发送成功", Toast.LENGTH_SHORT).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendMail();
                    }
                }).start();
                break;
        }
    }

    private void initHelperList() {
        // 用户使用协议
        Map<String, Object> userProtocolMap = new HashMap<>();
        userProtocolMap.put("helperOption", "用户使用协议");
        userProtocolMap.put("helpType", "userProtocol");
        helperList.add(userProtocolMap);

        // 关于系统
        Map<String, Object> systemMap = new HashMap<>();
        systemMap.put("helperOption", "关于系统");
        systemMap.put("helpType", "system");
        helperList.add(systemMap);

        // 电话人工帮助
        Map<String, Object> phoneMap = new HashMap<>();
        phoneMap.put("helperOption", "电话人工帮助");
        phoneMap.put("helpType", "phone");
        helperList.add(phoneMap);

        // 短信帮助
        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("helperOption", "短信帮助");
        messageMap.put("helpType", "message");
        helperList.add(messageMap);

        // 邮件帮助
        Map<String, Object> mailMap = new HashMap<>();
        mailMap.put("helperOption", "邮件帮助");
        mailMap.put("helpType", "mail");
        helperList.add(mailMap);
    }

    private class Holder {
        Button btn;

        Holder(View view) {
            btn = view.findViewById(R.id.btn);
        }
    }

    private void callPhone() {
        // 打电话
        Uri uri = Uri.parse("tel:"+ Final.HelperInfo.PHONE);
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(uri);
        context.startActivity(intent);
    }

    private void sendMessage() {
        // 发短信
        SmsManager smsManager = SmsManager.getDefault();
        PendingIntent pi = PendingIntent.getActivity(context,0,new Intent(),0);
        smsManager.sendTextMessage(Final.HelperInfo.PHONE,null,Final.HelperInfo.MESSAGE,
                pi,null);
        Toast.makeText(context, "短信发送完成", Toast.LENGTH_SHORT).show();
    }

    private void sendMail() {
        try {
            HtmlEmail email = new HtmlEmail();
            // SMTP发送服务器的名字
            email.setHostName("smtp.qq.com");
            // SMTP发送服务器端口
            email.setSmtpPort(587);
            // 发件人在邮件服务器上的注册名称和密码
            email.setAuthentication("xxx", "xxx");
            // 字符编码集的设置
            email.setCharset("gbk");
            // 收件人的邮箱
            email.addTo("xxx@qq.com");
            // 发送人的邮箱
            email.setFrom("xxxx@qq.com", "xxx");
            // 邮件标题
            email.setSubject("SCOS求助邮件");
            // 要发送的信息
            email.setMsg("我遇到了一些问题，请帮我解决一下。");
            // 发送
            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }
}
