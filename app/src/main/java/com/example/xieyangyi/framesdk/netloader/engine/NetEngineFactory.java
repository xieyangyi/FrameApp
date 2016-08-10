package com.example.xieyangyi.framesdk.netloader.engine;

import android.content.Context;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by xieyangyi on 16/8/10.
 */
public class NetEngineFactory {

    private static final String[] CLASS_NAME = new String[] {
            "com.example.xieyangyi.okhttplib.NetRequestOkhttp",
    };

    public static NetEngineImpl create(Context context) {
        NetEngineImpl impl = null;

        for (String name : CLASS_NAME) {
            if ((impl = getImpl(context, name)) != null) {
                return impl;
            }
        }

        return impl;
    }

    private static NetEngineImpl getImpl(Context context, String className) {
        NetEngineImpl impl = null;

        ClassLoader loader = context.getClassLoader();
        try {
            Class<? extends NetEngineImpl> clazz = loader.loadClass(className).asSubclass(NetEngineImpl.class);
            try {
                impl = (NetEngineImpl) clazz.getDeclaredMethod("getInstance").invoke(null, (Object[]) null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return impl;
    }
}
