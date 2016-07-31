package com.example.xieyangyi.framesdk.imageloader;

/**
 * Created by xieyangyi on 16/7/30.
 */
public class ImageRequestFactory {

    public static ImageRequestImpl create() {
        //
        return ImageRequestGlide.getInstance();
    }
}
