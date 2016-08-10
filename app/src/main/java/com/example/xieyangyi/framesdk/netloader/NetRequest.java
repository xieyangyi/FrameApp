package com.example.xieyangyi.framesdk.netloader;

import android.content.Context;

import com.example.xieyangyi.framesdk.netloader.engine.Engine;

/**
 * Created by xieyangyi on 16/8/9.
 */
public class NetRequest implements Request {

    public final static String TAG = "net_request";

    private Context mContext;
    private NetRequstParams P;
    private Engine mEngine;

    private NetRequest() {
    }

    public static NetRequest obtain(Context context, NetRequstParams params) {
        NetRequest request = NetRequestPool.getInstance().obtain();
        if (request == null) {
            request = new NetRequest();
        }

        request.init(context, params);

        return request;
    }

    private void init(Context context, NetRequstParams params) {
        P = params;
        mContext = context;
        mEngine = new Engine(mContext);
    }

    @Override
    public void begin() {
        mEngine.load(P);
    }

    @Override
    public void pause() {

    }

    @Override
    public void clear() {

    }

    @Override
    public void recycle() {
        P.recycle();
        P = null;
        NetRequestPool.getInstance().recycle(this);
    }

    @Override
    public boolean isPaused() {
        return false;
    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public boolean isComplete() {
        return false;
    }

    @Override
    public boolean isResourceSet() {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isFailed() {
        return false;
    }

}
