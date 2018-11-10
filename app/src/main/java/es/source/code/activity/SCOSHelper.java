package es.source.code.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import es.source.code.adapter.HelperAdapter;

public class SCOSHelper extends AppCompatActivity {
    private GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoshelper);

        initGridView();
    }

    private void initGridView() {
        gridView = findViewById(R.id.grid_view);
        HelperAdapter adapter = new HelperAdapter(SCOSHelper.this);
        gridView.setAdapter(adapter);
        // 设置点击事件
        gridView.setOnItemClickListener(adapter);
    }

}
