package com.example.xieyangyi.framedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.xieyangyi.framesdk.imageloader.ImageLoader;

public class ImageLoaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        ImageView ivImg = (ImageView) findViewById(R.id.iv_img);
        String url = "http://b3.hucdn.com/upload/face/1605/11/55695347863284_414x414.jpg";
        ImageLoader.with(this).load(url).into(ivImg);
    }
}
