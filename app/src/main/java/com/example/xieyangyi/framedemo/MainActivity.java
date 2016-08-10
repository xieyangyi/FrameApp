package com.example.xieyangyi.framedemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.xieyangyi.framesdk.netloader.NetLoader;

/**
 * Created by xieyangyi on 16/8/10.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NetLoader.with(this).url("https://www.baidu.com/").load();
    }
}
