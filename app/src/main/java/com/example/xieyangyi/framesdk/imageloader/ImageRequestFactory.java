package com.example.xieyangyi.framesdk.imageloader;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by xieyangyi on 16/7/30.
 */
public class ImageRequestFactory {

    private static final String TAG = "image_request_factory";
    private static final String GLIDE_CLASS_NAME = "com.example.xieyangyi.glidelib.ImageRequestGlide";

    public static ImageRequestImpl create(Context context) {

        switch (ImageCfg.getInstance(context).getSdkType()) {

            case ImageCfg.LOADER_TYPE_GLIDE:
                return getImpl(context, GLIDE_CLASS_NAME);

            case ImageCfg.LOADER_TYPE_FRESCO:
            case ImageCfg.LOADER_TYPE_PICASSO:
            default:
                return getImpl(context, GLIDE_CLASS_NAME);
        }
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
            e.printStackTrace();
            Log.d(TAG, className + "not found");
        }

        return impl;
    }
}
