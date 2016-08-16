package com.example.xieyangyi.framesdk.netloader;

import android.content.Context;

import com.example.xieyangyi.framesdk.netloader.lifecycle.LifecycleListener;

/**
 * Created by xieyangyi on 16/8/9.
 */
public class NetRequestManager implements LifecycleListener {

    private Context mContext;
    private NetRequestTracker mRequestTracker;

    public NetRequestManager(Context context) {
        mContext = context;
        mRequestTracker = new NetRequestTracker();
    }

    public NetRequestTracker getRequestTracker() {
        return mRequestTracker;
    }

    @Override
    public void onStart() {
        mRequestTracker.resumeRequests();
    }

    @Override
    public void onStop() {
        mRequestTracker.pauseRequests();
    }

    @Override
    public void onDestory() {
        mRequestTracker.clearRequests();
    }
}
