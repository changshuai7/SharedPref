package com.shuai.cssharedpref.example.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.shuai.sharedpref.SharedPref;

public class MainActivity extends AppCompatActivity {

    public static final String KEY = "mykey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 存储数据
        findViewById(R.id.btn_save_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPref.setString(KEY, "我是数据");
            }
        });

        // 获取数据
        findViewById(R.id.btn_get_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = SharedPref.getString(KEY, "-");

                Toast.makeText(MainActivity.this, key, Toast.LENGTH_SHORT).show();
            }
        });

        // 删除数据
        findViewById(R.id.btn_remove_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPref.removeKey(KEY);
            }
        });

        // 判断数据是否存在
        findViewById(R.id.btn_judge_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, SharedPref.contains(KEY)+"", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
