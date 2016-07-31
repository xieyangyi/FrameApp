package com.example.xieyangyi.framesdk;

import com.example.xieyangyi.framesdk.imageloader.ImageCfgObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by xieyangyi on 16/7/31.
 */
public class SdkCfgObject {

    @SerializedName("image_loader")
    @Expose
    public ImageCfgObject imageCfg;
}
