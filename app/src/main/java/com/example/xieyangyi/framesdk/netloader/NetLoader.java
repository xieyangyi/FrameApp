package com.example.xieyangyi.framesdk.netloader;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.example.xieyangyi.framesdk.netloader.lifecycle.LifecyleManager;

/**
 * Created by xieyangyi on 16/8/9.
 */
public class NetLoader {

    public static NetRequestBuilder with(Context context) {
        NetRequestManager manager = LifecyleManager.getInstance().get(context);
        return new NetRequestBuilder(context, manager.getRequestTracker());
    }

    public static NetRequestBuilder with(Activity activity) {
        NetRequestManager manager = LifecyleManager.getInstance().get(activity);
        return new NetRequestBuilder(activity, manager.getRequestTracker());
    }

    public static NetRequestBuilder with(Fragment fragment) {
        NetRequestManager manager = LifecyleManager.getInstance().get(fragment);
        return new NetRequestBuilder(fragment, manager.getRequestTracker());
    }
}
