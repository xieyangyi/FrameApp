package com.example.xieyangyi.framesdk.netloader.engine;

import android.content.Context;

import com.example.xieyangyi.framesdk.netloader.NetRequstParams;

/**
 * Created by xieyangyi on 16/8/10.
 */
public class Engine {

    private NetEngineImpl IMPL;

    public Engine(Context context) {
        IMPL = NetEngineFactory.create(context);
    }

    public void load(NetRequstParams params) {
        if (IMPL == null) return;

        IMPL.load(params);
    }
}
