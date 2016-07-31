package com.example.xieyangyi.framesdk.imageloader;

/**
 * Created by xieyangyi on 16/7/30.
 */
public class ImageSize {
    public static final String Micro = (ImageRequest.IS_USE_WEBP ? "!100x100.webp" : "!100x100.jpg");
    public static final String Small = (ImageRequest.IS_USE_WEBP ? "!160x160.webp" : "!160x160.jpg");
    public static final String Middle = (ImageRequest.IS_USE_WEBP ? "!320x320.webp" : "!320x320.jpg");
    public static final String Big = (ImageRequest.IS_USE_WEBP ? "!450x450.webp" : "!450x450.jpg");
    public static final String Large = (ImageRequest.IS_USE_WEBP ? "!640x640.webp" : "!640x640.jpg");
    public static final String Larger = (ImageRequest.IS_USE_WEBP ? "!960x960.webp" : "!960x960.jpg");

    /**
     * just request width,the height will auto scale.
     */
    public static final String Fit_Larger = (ImageRequest.IS_USE_WEBP ? "!1080.webp" : "!1080.jpg");
    public static final String Fit_Large = (ImageRequest.IS_USE_WEBP ? "!640.webp" : "!640.jpg");
}
