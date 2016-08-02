package com.example.xieyangyi.framesdk.imageloader;

import android.content.Context;

import com.example.xieyangyi.framesdk.R;
import com.example.xieyangyi.framesdk.SdkCfg;

/**
 * Created by xieyangyi on 16/7/31.
 */
public class ImageCfg {

    private static final String TAG = "image_cfg";

    public static final int LOADER_TYPE_GLIDE = 1;
    public static final int LOADER_TYPE_FRESCO = 2;
    public static final int LOADER_TYPE_PICASSO = 3;
    public static final int LOADER_TYPE_DEFAULT = LOADER_TYPE_GLIDE;

    private static ImageCfg sCfg;
    private Context mContext;
    private ImageCfgObject mCfgObject;

    private int mMicroHolder;
    private int mSmallHolder;
    private int mMiddleHolder;
    private int mLargeHolder;

    private ImageCfg(Context context) {
        mContext = context;
        if (SdkCfg.getCfg(mContext) != null) {
            mCfgObject = SdkCfg.getCfg(mContext).imageCfg;
            setMicroPlaceHolder();
            setSmallPlaceHolder();
            setMiddlePlaceHolder();
            setLargePlaceHolder();
        }
    }

    public static ImageCfg getInstance(Context context) {
        if (sCfg == null) {
            synchronized (ImageCfg.class) {
                if (sCfg == null) {
                    sCfg = new ImageCfg(context);
                }
            }
        }
        return sCfg;
    }

    public int getMicroPlaceHolder() {
        return mMicroHolder;
    }

    public int getSmallPlaceHolder() {
        return mSmallHolder;
    }

    public int getMiddlePlaceHolder() {
        return mMiddleHolder;
    }

    public int getLargePlaceHolder() {
        return mLargeHolder;
    }

    private int readRes(String res) {

        int id = 0;

        try {
            id = R.drawable.class.getDeclaredField(res).getInt(R.drawable.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return id;
    }

    private void setMicroPlaceHolder() {

        if (mCfgObject == null) {
            mMicroHolder = R.mipmap.ic_launcher;
        }

        String holder = mCfgObject.placeHolderMicro;
        int id = readRes(holder);

        if (id == 0) {
            mMicroHolder = R.mipmap.ic_launcher;
        } else {
            mMicroHolder = id;
        }
    }

    private void setSmallPlaceHolder() {

        if (mCfgObject == null) {
            mSmallHolder = R.mipmap.ic_launcher;
        }

        String holder = mCfgObject.placeHolderSmall;
        int id = readRes(holder);

        if (id == 0) {
            mSmallHolder = R.mipmap.ic_launcher;
        } else {
            mSmallHolder = id;
        }
    }

    private void setMiddlePlaceHolder() {

        if (mCfgObject == null) {
            mMiddleHolder = R.mipmap.ic_launcher;
        }

        String holder = mCfgObject.placeHolderMiddle;
        int id = readRes(holder);

        if (id == 0) {
            mMiddleHolder = R.mipmap.ic_launcher;
        } else {
            mMiddleHolder = id;
        }
    }

    private void setLargePlaceHolder() {

        if (mCfgObject == null) {
            mLargeHolder = R.mipmap.ic_launcher;
        }

        String holder = mCfgObject.placeHolderLarge;
        int id = readRes(holder);

        if (id == 0) {
            mLargeHolder = R.mipmap.ic_launcher;
        } else {
            mLargeHolder = id;
        }
    }
}
