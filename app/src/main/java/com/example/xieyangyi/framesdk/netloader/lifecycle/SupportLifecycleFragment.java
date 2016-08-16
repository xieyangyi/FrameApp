package com.example.xieyangyi.framesdk.netloader.lifecycle;

import android.support.v4.app.Fragment;

import com.example.xieyangyi.framesdk.netloader.NetRequestManager;

/**
 * Created by xieyangyi on 16/8/16.
 */
public class SupportLifecycleFragment extends Fragment {

    private NetRequestManager mRequestManager;

    public NetRequestManager getRequestManager() {
        return mRequestManager;
    }

    public void setRequestManager(NetRequestManager requestManager) {
        mRequestManager = requestManager;
    }


    @Override
    public void onStart() {
        super.onStart();
        if (mRequestManager != null) {
            mRequestManager.onStart();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mRequestManager != null) {
            mRequestManager.onStop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRequestManager != null) {
            mRequestManager.onDestory();
        }
    }
}
