package es.source.code.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Random;

import es.source.code.activity.FoodView;
import es.source.code.model.Food;

public class ServerObserverService extends Service {
    private static EventBus eventBus;
    private boolean flag = false;
    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                while(flag) {
                    eventBus.post(new Food("000001", (int)(1+Math.random()*20)));
                    eventBus.post(new Food("000002", (int)(1+Math.random()*20)));
                    eventBus.post(new Food("000003", (int)(1+Math.random()*20)));
                    eventBus.post(new Food("000004", (int)(1+Math.random()*20)));
                    eventBus.post(new Food("000005", (int)(1+Math.random()*20)));
                    eventBus.post(new Food("000006", (int)(1+Math.random()*20)));
                    Thread.sleep(3000);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void handleUpdateStatus(FoodView.NotifyUpdateStatus updateStatus) {
        if (updateStatus.getStatus() == 1) {
            // open
            Log.d("######","start..........");
            flag = true;
            thread.start();
        } else {
            // close
            Log.d("######","close..........");
            flag = false;
        }
    }



    @Override
    public void onCreate() {
        //注册订阅者
        eventBus = EventBus.getDefault();
        eventBus.register(this);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        //注销订阅者
        eventBus.unregister(this);
        super.onDestroy();
    }

}
