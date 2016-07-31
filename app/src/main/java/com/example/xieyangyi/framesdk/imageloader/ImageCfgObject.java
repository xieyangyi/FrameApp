package com.example.xieyangyi.framesdk.imageloader;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by xieyangyi on 16/7/31.
 */
public class ImageCfgObject {

    @SerializedName("sdk")
    @Expose
    public String sdk;

    @SerializedName("place_holder_micro")
    @Expose
    public String placeHolderMicro;

    @SerializedName("place_holder_small")
    @Expose
    public String placeHolderSmall;

    @SerializedName("place_holder_middle")
    @Expose
    public String placeHolderMiddle;

    @SerializedName("place_holder_large")
    @Expose
    public String placeHolderLarge;
}
