package com.example.xieyangyi.framedemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.xieyangyi.framesdk.netloader.NetLoader;
import com.example.xieyangyi.framesdk.netloader.NetRequestListener;

/**
 * Created by xieyangyi on 16/8/10.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NetLoader.with(this).url("https://www.baidu.com/").listener(new NetRequestListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onSucceed(Object result) {
                Toast.makeText(MainActivity.this, (String) result, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(Exception e) {

            }
        }).load();
    }
}
