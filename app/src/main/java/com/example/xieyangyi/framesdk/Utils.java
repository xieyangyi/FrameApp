package com.example.xieyangyi.framesdk;

import android.content.Context;

import java.lang.reflect.Method;

/**
 * Created by xieyangyi on 16/7/30.
 */
public class Utils {

    public static Context getApplicationContext() {
        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Method method = activityThreadClass.getMethod("currentApplication");
            return (Context) method.invoke(null, (Object[]) null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
