package com.example.xieyangyi.okhttplib;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.xieyangyi.framesdk.Utils;
import com.example.xieyangyi.framesdk.netloader.NetRequstParams;
import com.example.xieyangyi.framesdk.netloader.engine.NetEngineImpl;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by xieyangyi on 16/8/10.
 */
public class NetRequestOkhttp implements NetEngineImpl {

    private static NetRequestOkhttp sInstance;
    Handler handler = new Handler(Looper.getMainLooper());

    public static NetRequestOkhttp getInstance() {
        if (sInstance == null) {
            synchronized (NetRequestOkhttp.class) {
                if (sInstance == null) {
                    sInstance = new NetRequestOkhttp();
                }
            }
        }
        return sInstance;
    }

    @Override
    public void load(NetRequstParams params) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(params.getUrl())
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String res = response.body().string();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Utils.getApplicationContext(), res, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
