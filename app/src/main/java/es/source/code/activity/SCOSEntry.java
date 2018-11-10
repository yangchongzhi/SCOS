package es.source.code.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

import es.source.code.service.UpdateService;
import es.source.code.utils.Final;

public class SCOSEntry extends AppCompatActivity {

    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry);

//       参考源码：https://blog.csdn.net/u011733020/article/details/45922843?tdsourcetag=s_pctim_aiomsg
        mGestureDetector = new GestureDetector(this,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                // e1: 第一次按下的位置 e2:当手离开屏幕时的位置  velocityX:沿x轴的速度  velocityY:沿Y轴方向的速度

                if ((e1.getRawX() - e2.getRawX()) >200) {
                    Intent intent = new Intent(SCOSEntry.this, MainScreen.class);
                    intent.setAction("scos.permission.ACCESS_SCOS");
                    intent.putExtra(Final.ActivityTransferInfo.FROM_ENTRY, Final.ActivityTransferInfo.ENTRY_TO_MAIN);
                    startActivity(intent);
                    return true;
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });

        Intent it = new Intent(this, UpdateService.class);
        startService(it);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

}
