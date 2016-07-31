package com.example.xieyangyi.framesdk.imageloader;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by xieyangyi on 16/7/30.
 * This class is not need, just to simplify using ImageRequest
 */
public class ImageLoader {
    /**
     * set ImageLoader in debug mode
     *
     * @param debug
     */
    public static void setDebug(boolean debug) {
        ImageRequest.DEBUG = debug;
    }

    /**
     * set ImageLoader placeholder in debug mode.in this mode,it will show placeholder only.
     *
     * @param debug
     */
    public static void debugPlaceHolder(boolean debug) {
        ImageRequest.DEBUG_PLACE_HOLDER = debug;
    }

    /**
     * @param supportWebP support webp
     */
    public static void supportWebP(boolean supportWebP) {
        ImageRequest.IS_USE_WEBP = supportWebP;
    }

    /**
     * @param dnsType support dns
     */
    public static void setDnsType(int dnsType) {
        ImageRequest.DNS_TYPE = dnsType;
    }

    /**
     * Begin a load with ImageLoader  that will be used outside of the normal fragment or activity lifecycle
     *
     * @param context
     * @return
     * @see #with(Fragment)
     * @see #with(Activity)
     */
    public static ImageRequest.Builder with(Context context) {
        return new ImageRequest.Builder(context);
    }

    /**
     * Begin a load with ImageLoader that will be tied to the given activity's lifecycle
     *
     * @param activity
     * @return
     * @see #with(Context)
     * @see #with(Fragment)
     */
    public static ImageRequest.Builder with(Activity activity) {
        return new ImageRequest.Builder(activity);
    }

    /**
     * Begin a load with ImageLoader that will be tied to the given fragment's lifecycle
     *
     * @param fragment
     * @return
     * @see #with(Activity)
     * @see #with(Context)
     */
    public static ImageRequest.Builder with(Fragment fragment) {
        return new ImageRequest.Builder(fragment);
    }

    /**
     * clear memory cache,you must use it in ui thread
     *
     * @param context
     */
    public static void clearMemoryCache(Context context) {
        new ImageRequest.Builder(context).clearMemoryCache();
    }

    /**
     * clear disk cache,you must use it in ui thread.
     *
     * @param context
     */
    public static void clearDiskCache(Context context) {
        new ImageRequest.Builder(context).clearDiskCache();
    }
}
