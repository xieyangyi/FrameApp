package com.example.xieyangyi.framesdk.netloader;

import android.content.Context;

/**
 * Created by xieyangyi on 16/8/9.
 */
public class NetRequestManager {

    private Context mContext;
    private NetRequestTracker mRequestTracker;

    public NetRequestManager(Context context) {
        mContext = context;
        mRequestTracker = new NetRequestTracker();
    }

    public NetRequestTracker getRequestTracker() {
        return mRequestTracker;
    }
}
