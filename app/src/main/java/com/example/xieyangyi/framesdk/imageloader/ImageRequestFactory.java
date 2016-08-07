package com.example.xieyangyi.framesdk.imageloader;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by xieyangyi on 16/7/30.
 */
public class ImageRequestFactory {

    private static final String TAG = "image_request_factory";
    private static final String[] CLASS_NAME = new String[] {
            "com.example.xieyangyi.glidelib.ImageRequestGlide",
            "com.example.xieyangyi.universalimagelib.ImageRequestUniversal",
    };


    public static ImageRequestImpl create(Context context) {

        ImageRequestImpl impl = null;

        for (int i = 0; i < CLASS_NAME.length; i++) {
            if ((impl = getImpl(context, CLASS_NAME[i])) != null) {
                return impl;
            }
        }
        return impl;

    }

    private static ImageRequestImpl getImpl(Context context, String className) {
        ImageRequestImpl impl = null;

        try {
            ClassLoader loader = context.getClassLoader();
            Class<? extends ImageRequestImpl> clazz = loader.loadClass(className).asSubclass(ImageRequestImpl.class);
            impl = (ImageRequestImpl) clazz.getDeclaredMethod("getInstance").invoke(null, (Object[]) null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            Log.d(TAG, className + "not found");
            e.printStackTrace();
        }

        return impl;
    }
}
