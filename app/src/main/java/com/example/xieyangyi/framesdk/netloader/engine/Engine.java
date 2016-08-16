package com.example.xieyangyi.framesdk.netloader.engine;

import android.content.Context;
import android.view.View;

import com.example.xieyangyi.framesdk.netloader.NetRequest;

/**
 * Created by xieyangyi on 16/8/10.
 */
public class Engine {

    private NetEngineImpl IMPL;

    public Engine(Context context) {
        IMPL = NetEngineFactory.create(context);
    }

    public void load(NetRequest request) {
        if (IMPL == null) return;

        if (request.getListener() != null) {
            request.getListener().onStarted();
        }
        if (request.getEmptyView() != null) {
            request.getEmptyView().setVisibility(View.VISIBLE);
            request.getEmptyView().resetAsFetching();
        }

        IMPL.load(request);
    }
}
