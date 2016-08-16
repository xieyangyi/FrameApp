package com.example.xieyangyi.framesdk.netloader;

import android.content.Context;

import com.example.xieyangyi.framesdk.netloader.engine.Engine;

import java.util.Map;

/**
 * Created by xieyangyi on 16/8/9.
 */
public class NetRequest implements Request {

    public final static String TAG = "net_request";

    private Context mContext;
    private NetRequstParams P;
    private Engine mEngine;
    private Status mStatus;

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
        mStatus = Status.PENDING;
    }

    @Override
    public void begin() {
        mStatus = Status.RUNNING;
        mEngine.load(this);
    }

    @Override
    public void pause() {
        if (mStatus == Status.PAUSED) {
            return;
        }
        clear();
        mStatus = Status.PAUSED;
    }

    @Override
    public void clear() {
        if (mStatus == Status.CLEARD) {
            return;
        }
        recycle();
        mStatus = Status.CLEARD;
    }

    @Override
    public void recycle() {
        P.recycle();
        P = null;
        NetRequestPool.getInstance().recycle(this);
    }

    @Override
    public boolean isPaused() {
        return mStatus == Status.PAUSED;
    }

    @Override
    public boolean isRunning() {
        return mStatus == Status.RUNNING;
    }

    @Override
    public boolean isComplete() {
        return mStatus == Status.COMPLETE;
    }

    @Override
    public boolean isCancelled() {
        return mStatus == Status.CANCELLED;
    }

    @Override
    public boolean isFailed() {
        return mStatus == Status.FAILED;
    }


    // getter
    public String getUrl() {
        return P.url;
    }

    public Map<String, Object> getParams() {
        return P.params;
    }

    public byte[] getBody() {
        return P.body;
    }

    public String getTag() {
        return P.tag;
    }

    public int getCacheTime() {
        return P.cacheTime;
    }

    public Map<String, String> getHeader() {
        return P.header;
    }

    public NetRequstParams.Type getType() {
        return P.type;
    }

    public boolean isLoadCacheIfNetError() {
        return P.isLoadCacheIfNetError;
    }

    public NetRequestListener getListener() {
        return P.listener;
    }

    public EmptyView getEmptyView() {
        return P.emptyView;
    }

    public void setStatus(Status status) {
        mStatus = status;
    }

    public enum Status {
        PENDING,
        RUNNING,
        COMPLETE,
        FAILED,
        CANCELLED,
        CLEARD,
        PAUSED
    }
}
