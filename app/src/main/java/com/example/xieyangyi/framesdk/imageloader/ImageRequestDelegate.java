package com.example.xieyangyi.framesdk.imageloader;

import android.widget.ImageView;

/**
 * Created by xieyangyi on 16/7/31.
 */
public interface ImageRequestDelegate {

    void into(ImageView imageView);
    void cache();
    Object sync();
    Object sync4Web();
    void clearMemoryCache();
    void clearDiskCache();
}
