package es.source.code.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import es.source.code.activity.FoodDetailed;
import es.source.code.activity.MainScreen;
import es.source.code.activity.R;
import es.source.code.model.Food;

public class UpdateService extends IntentService {
    public String reqType = "json";
    public UpdateService() {
        super("UpdateService");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public UpdateService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        getFoods();
    }

    private void getFoods() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection;
                try {
                    URL url = new URL("http://10.0.2.2:8080/foodUpdate/" + reqType);
                    connection = (HttpURLConnection) url.openConnection();

                    // 设置请求方式
                    connection.setRequestMethod("GET");
                    // 设置编码格式
                    connection.setRequestProperty("Charset", "UTF-8");
                    connection.setRequestProperty("Content-Type","application/json");
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    // 连接
                    connection.connect();
                    // 成功
                    if(connection.getResponseCode() == 200) {
                        InputStream is = connection.getInputStream();
                        if(reqType.equals("json")) {
                            try {
                                getJSON(is);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                getXML(is);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        is.close();


                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // 发送通知
    private void sendNotification(Food food) {


        String id = "my_channel_01";
        String name="我是渠道名字";

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification;

        //获取PendingIntent
        Intent MainScreenIntent = new Intent(this, MainScreen.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, MainScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 创建chanel
            NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(mChannel);

            notification = new Notification.Builder(this, "default")
                    .setChannelId(id)
                    .setContentTitle("新品上架")
                    .setContentText("菜名:" + food.getName() + ",价格:" + food.getPrice())
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.iv_logo).build();
        } else {
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "default")
                    .setContentTitle("新品上架")
                    .setContentText("菜名:" + food.getName() + ",价格:" + food.getPrice())
                    .setSmallIcon(R.drawable.iv_logo)
                    .setOngoing(true);
            notification = notificationBuilder.build();
        }

        // 通过builder.build()方法生成Notification对象,并发送通知
        notificationManager.notify(0, notification);
    }

    private void playNotification() {
        Uri ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        MediaPlayer mediaPlayer = MediaPlayer.create(this, ringtone);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
    }

    public void getXML(InputStream inStream) throws Exception {
        long startTime = System.currentTimeMillis();//记录开始时间
        List<Food> foods = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(inStream);
        Element root = document.getDocumentElement();
        NodeList personNodes = root.getElementsByTagName("item");
        for (int i = 0; i < personNodes.getLength(); i++) {
            Food food = new Food();
            Element foodElement = (Element) personNodes.item(i);
            NodeList childNodes = foodElement.getChildNodes();
            for (int j = 0; j < childNodes.getLength(); j++) {
                Node node = childNodes.item(j);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    if ("name".equals(node.getNodeName())) {
                        String name = node.getFirstChild().getNodeValue();
                        food.setName(name);
                    }
                    else if ("price".equals(node.getNodeName())) {
                        double price = Double.parseDouble(node.getFirstChild().getNodeValue());
                        food.setPrice(price);
                    }
                }
            }
            foods.add(food);
        }
        long endTime = System.currentTimeMillis();//记录结束时间
        float excTime = (float)(endTime-startTime);
        System.out.println("==============解析xml时间："+excTime+"ms==============");
        if(foods.size() == 0) return;
        // 发送状态栏通知
        sendNotification(foods.get(0));
        // 播放提示音
        playNotification();
    }

    public void getJSON(InputStream inStream) throws Exception {
        long startTime = System.currentTimeMillis();//记录开始时间
        BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
        // 获取返回参数
        String strRead;
        StringBuilder sb = new StringBuilder();
        while ((strRead = reader.readLine()) != null) {
            sb.append(strRead);
        }
        reader.close();
        JSONArray jsonArray = JSON.parseArray(sb.toString());
        long endTime = System.currentTimeMillis();//记录结束时间
        float excTime = (float)(endTime-startTime);
        System.out.println("==============解析json时间："+excTime+"ms==============");
        if(jsonArray.isEmpty()) return;
        JSONObject temp = JSON.parseObject(jsonArray.get(0).toString());
        Food food = new Food(temp.getString("name"), temp.getDouble("price"));
        // 发送状态栏通知
        sendNotification(food);
        // 播放提示音
        playNotification();
    }

}
