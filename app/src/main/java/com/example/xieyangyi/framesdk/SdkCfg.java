package com.example.xieyangyi.framesdk;

import android.content.Context;
import android.util.Log;

import com.example.xieyangyi.framesdk.Json.JsonUtil;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by xieyangyi on 16/7/31.
 */
public class SdkCfg {

    private static final String TAG = "sdk_cfg";

    private static final String CFG_File = "cfg.json";

    private static SdkCfgObject sCfg;

    public static SdkCfgObject getCfg(Context context) {
        if (sCfg == null) {
            sCfg = JsonUtil.fromJson(getJson(context), SdkCfgObject.class);
        }

        return  sCfg;
    }

    private static String getJson(Context context) {
        String json = "";

        try {
            InputStream is = context.getResources().getAssets().open(CFG_File);
            int length = is.available();
            byte[] buffer = new byte[length];
            is.read(buffer);
            json = new String(buffer, "utf-8");

        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d(TAG, json);
        return json;
    }
}
