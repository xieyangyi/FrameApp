package com.example.xieyangyi.framesdk.netloader;

/**
 * Created by xieyangyi on 16/8/9.
 */
public interface NetRequestListener<T> {

    void onStarted();
    void onSucceed(T result);
    void onFailed(Exception e);
}
