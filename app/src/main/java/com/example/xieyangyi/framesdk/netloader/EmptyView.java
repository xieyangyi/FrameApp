package com.example.xieyangyi.framesdk.netloader;

import android.content.Context;
import android.view.View;

/**
 * Created by xieyangyi on 16/8/9.
 */
public abstract class EmptyView extends View {

    public EmptyView(Context context) {
        super(context);
    }

    public abstract void resetAsFetching();

    public abstract void resetAsFailed();

    public abstract void resetAsEmpty();
}
