package com.example.xieyangyi.framesdk.netloader;

/**
 * Created by xieyangyi on 16/8/9.
 */
public interface NetRequestListener {

    void onStarted();
    void onSucceed(Object result);
    void onFailed(Exception e);
}
