package com.example.xieyangyi.framesdk.netloader;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by xieyangyi on 16/8/9.
 */
public class NetLoader {

    public static NetRequestBuilder with(Context context) {
        // manager is related to lifecycle, TBD in next edition
        NetRequestManager manager = new NetRequestManager(context);
        return new NetRequestBuilder(context, manager.getRequestTracker());
    }

    public static NetRequestBuilder with(Activity activity) {
        NetRequestManager manager = new NetRequestManager(activity);
        return new NetRequestBuilder(activity, manager.getRequestTracker());
    }

    public static NetRequestBuilder with(Fragment fragment) {
        NetRequestManager manager = new NetRequestManager(fragment.getActivity());
        return new NetRequestBuilder(fragment, manager.getRequestTracker());
    }
}
