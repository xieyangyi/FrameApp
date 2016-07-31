package com.example.xieyangyi.framesdk.imageloader;

import android.content.Context;

/**
 * Created by xieyangyi on 16/7/30.
 */
public interface ImageRequestImpl {

    void into(ImageRequest request);

    void cache(ImageRequest request);

    Object sync(ImageRequest request);

    void clearMemoryCache(Context context);

    void clearDiskCache(Context context);

}
