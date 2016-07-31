package com.example.xieyangyi.framesdk.imageloader;

import android.view.View;

/**
 * Created by xieyangyi on 16/7/30.
 */
public interface ImageLoaderListener {
    /**
     * @param view view the view was you load image into.
     */
    void onLoadStarted(View view);

    /**
     * @param view       the view was you load image into.
     * @param url        if you load image with url,you can get url from here,otherwise it will be null.
     * @param failReason it return maybe null.
     */
    void onLoadFailed(View view, String url, String failReason);

    /**
     * @param view   the view was you load image into.
     * @param url    if you load image with url,you can get url from here,otherwise it will be null.
     * @param result now result maybe bitmap or gif,it dependant {@link ImageRequest},default was bitmap.
     * @see ImageRequest
     */
    void onLoadSuccessed(View view, String url, Object result);
}
