package com.example.xieyangyi.framesdk.netloader;

import android.content.Context;
import android.support.v4.app.Fragment;

import java.util.Map;

/**
 * Created by xieyangyi on 16/8/9.
 */
public class NetRequestBuilder {

    private NetRequstParams P;

    private Context mContext;
    private Fragment mFragment;
    private NetRequest mRequest;
    private NetRequestTracker mRequestTracker;


    public NetRequestBuilder(Context context, NetRequestTracker requestTracker) {
        mContext = context;
        mRequestTracker = requestTracker;
        P = new NetRequstParams();
    }

    public NetRequestBuilder(Fragment fragment, NetRequestTracker requestTracker) {
        mFragment = fragment;
        mRequestTracker = requestTracker;
        P = new NetRequstParams();
    }

    // 参数获取
    public NetRequestBuilder url(String url) {
        P.url = url;
        return this;
    }

    public NetRequestBuilder body(Map<String, Object> params) {
        P.params = params;
        return this;
    }

    public NetRequestBuilder body(byte[] body) {
        P.body = body;
        return this;
    }

    public NetRequestBuilder tag(String tag) {
        P.tag = tag;
        return this;
    }

    public NetRequestBuilder type(NetRequstParams.Type type) {
        P.type = type;
        return this;
    }

    public NetRequestBuilder cacheTime(int cacheTime) {
        P.cacheTime = cacheTime;
        return this;
    }

    public NetRequestBuilder addHeader(String key, String value) {
        P.header.put(key, value);
        return this;
    }

    public NetRequestBuilder listener(NetRequestListener listener) {
        P.listener = listener;
        return this;
    }

    public NetRequestBuilder emptyView(EmptyView view) {
        P.emptyView = view;
        return this;
    }

    public NetRequestBuilder json(Class jsonClass) {
        P.jsonClass = jsonClass;
        return this;
    }

    // 创建
    public NetRequest build() {
        if (mRequest == null) {
            mRequest = NetRequest.obtain(mContext, P);
        }
        return mRequest;
    }

    // API
    public void load() {

        Request request = build();
        mRequestTracker.runRequest(request);

    }
}
