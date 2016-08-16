package com.example.xieyangyi.framesdk.netloader.lifecycle;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.example.xieyangyi.framesdk.netloader.NetRequestManager;

/**
 * Created by xieyangyi on 16/8/16.
 */
public class LifecyleManager {

    private static final String TAG_FRAGMENT = "com.example.xieyangyi.framesdk.netloader.lifecycle";
    private static LifecyleManager sManager;
    private NetRequestManager mApplicationRequestManager;

    private LifecyleManager() {}

    public static LifecyleManager getInstance() {
        if (sManager == null) {
            synchronized (LifecyleManager.class) {
                if (sManager == null) {
                    sManager = new LifecyleManager();
                }
            }
        }

        return sManager;
    }

    public NetRequestManager get(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context is null");
        } else if (context instanceof FragmentActivity) {
            return get((FragmentActivity) context);
        } else if (context instanceof Activity) {
            return get((Activity) context);
        }

        return getAppManager(context);

    }

    private NetRequestManager getAppManager(Context context) {
        if (mApplicationRequestManager == null) {
            synchronized (LifecyleManager.class) {
                if (mApplicationRequestManager == null) {
                    mApplicationRequestManager = new NetRequestManager(context);
                }
            }
        }
        return mApplicationRequestManager;
    }

    public NetRequestManager get(FragmentActivity activity) {
        return getSupportRequestManager(activity, activity.getSupportFragmentManager());
    }
    
    public NetRequestManager get(Fragment fragment) {
        if (fragment.getActivity() == null) {
            throw new IllegalArgumentException("you can't use this API before fragment attached to activity");
        }
        
        return getSupportRequestManager(fragment.getActivity(), fragment.getChildFragmentManager()); 
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public NetRequestManager get(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return get(activity.getApplicationContext());
        } else {
            return getRequestManager(activity, activity.getFragmentManager());
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public NetRequestManager get(android.app.Fragment fragment) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return get(fragment.getActivity().getApplicationContext());
        } else {
            return getRequestManager(fragment.getActivity(), fragment.getChildFragmentManager());
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private NetRequestManager getRequestManager(Context context, android.app.FragmentManager fm) {
        LifecycleFragment fragment = getFragment(fm);
        NetRequestManager manager = fragment.getRequestManager();
        if (manager == null) {
            manager = new NetRequestManager(context);
            fragment.setRequestManager(manager);
        }
        return manager;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private LifecycleFragment getFragment(android.app.FragmentManager fm) {
        LifecycleFragment fragment = (LifecycleFragment) fm.findFragmentByTag(TAG_FRAGMENT);
        if (fragment == null) {
            fragment = new LifecycleFragment();
            fm.beginTransaction().add(fragment, TAG_FRAGMENT).commitAllowingStateLoss();
        }
        return fragment;
    }
    
    private NetRequestManager getSupportRequestManager(Context context, FragmentManager fm) {
        SupportLifecycleFragment fragment = getSupportFragment(fm);
        NetRequestManager manager = fragment.getRequestManager();
        if (manager == null) {
            manager = new NetRequestManager(context);
            fragment.setRequestManager(manager);
        }
        return manager;
    }

    private SupportLifecycleFragment getSupportFragment(FragmentManager fm) {
        SupportLifecycleFragment fragment = (SupportLifecycleFragment) fm.findFragmentByTag(TAG_FRAGMENT);
        if (fragment == null) {
            fragment = new SupportLifecycleFragment();
            fm.beginTransaction().add(fragment, TAG_FRAGMENT).commitAllowingStateLoss();
        }
        return fragment;
    }
    
    
}
