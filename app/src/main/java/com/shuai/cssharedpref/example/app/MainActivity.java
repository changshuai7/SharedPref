package com.shuai.cssharedpref.example.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.shuai.sharedpref.SharedPref;
import com.shuai.sharedpref.SharedPrefConfig;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_save_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPref.setString("KEY", "我是数据");
            }
        });
        findViewById(R.id.btn_get_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = SharedPref.getString("KEY", "-");

                Toast.makeText(MainActivity.this, key, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
