package com.example.xieyangyi.framesdk.imageloader;

import android.content.Context;

/**
 * Created by xieyangyi on 16/7/30.
 */
public class ImageRequestFactory {

    private static final String TAG = "image_request_factory";

    public static ImageRequestImpl create(Context context) {

        switch (ImageCfg.getInstance(context).getSdkType()) {

            case ImageCfg.LOADER_TYPE_GLIDE:
                return ImageRequestGlide.getInstance();
            case ImageCfg.LOADER_TYPE_FRESCO:
            case ImageCfg.LOADER_TYPE_PICASSO:
            default:
                return ImageRequestGlide.getInstance();
        }
    }
}
