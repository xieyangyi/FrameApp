package com.example.xieyangyi.framesdk.imageloader;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by xieyangyi on 16/7/30.
 */
public class ImageRequest implements ImageRequestDelegate {

    private static final String TAG = "ImageRequest";

    public static boolean DEBUG = false;
    public static boolean DEBUG_PLACE_HOLDER = false;
    public static boolean IS_USE_WEBP = true;
    public static int DNS_TYPE = -1;

    private Context mContext;
    private Fragment mFragment;
    private ImageRequestImpl IMPL;
    private ImageRequestParams P;

    private ImageRequest() {}

    private ImageRequest(Context context, ImageRequestParams params) {
        mContext = context;
        P = params;
        IMPL = ImageRequestFactory.create(context);
    }

    private ImageRequest(Fragment fragment, ImageRequestParams params) {
        mFragment = fragment;
        P = params;
        IMPL = ImageRequestFactory.create(fragment.getActivity());
    }


    /**
     * in this method,it will append imagesize,set placeholder errorholder
     */
    private void process() {
        if (P.loadType == Integer.MIN_VALUE) {
            throw new IllegalStateException("you must call load method");
        }

        switch (P.imageType) {
            case ImageRequestParams.MICRO_SIZE:
                P.imagesize = ImageSize.Micro;
                break;
            case ImageRequestParams.SMALL_SIZE:
                P.imagesize = ImageSize.Small;
                break;
            case ImageRequestParams.MIDDLE_SIZE:
                P.imagesize = ImageSize.Middle;
                break;
            case ImageRequestParams.BIG_SIZE:
                P.imagesize = ImageSize.Big;
                break;
            case ImageRequestParams.LARGE_SIZE:
                P.imagesize = ImageSize.Large;
                break;
            case ImageRequestParams.LARGER_SIZE:
                P.imagesize = ImageSize.Larger;
                break;
            case ImageRequestParams.FIT_LARGE_SIZE:
                P.imagesize = ImageSize.Fit_Large;
                break;
            case ImageRequestParams.FIT_LARGER_SIZE:
                P.imagesize = ImageSize.Fit_Larger;
                break;
        }

        //如果支持wep,原图也webp输出 先临时这么写,有时间会把upyun的一些特性支持起来 http://docs.upyun.com/cdn/feature/#_8
//upyun的各种特性,我们都不支持,我感觉好坑
//        if (imagesize == null && IS_USE_WEBP && (url != null && !url.endsWith("format/webp"))){
//            url = url+"/format/webp";
//        }

        if (P.loadType == P.LOAD_IMG_URL && P.url != null && !P.url.startsWith("file:")) {
            if (!TextUtils.isEmpty(P.imagesize)) {
                //just use for upyun,if url had append imagesize,i will substring it to append new imagesize.
                if (P.url.contains("!")) {
                    P.url = P.url.substring(0, P.url.lastIndexOf("!"));
                }
                P.url = P.url + P.imagesize;
            }
        }

        if (P.loadType == P.LOAD_IMG_URL && DEBUG) {
            Log.d(TAG, "url : " + P.url);
        }

        if (P.placeholder != Integer.MIN_VALUE && P.error == Integer.MIN_VALUE) {
            P.error = P.placeholder;
        } else if (P.placeholder == Integer.MIN_VALUE) {
            //not set placeholder
        }

        if (DEBUG_PLACE_HOLDER) {
            P.url = P.url + "_debug_place_holder";
        }
    }

    @Override
    public void into(ImageView imageView) {
        if (IMPL == null) {
            return;
        }

        P.imageView = imageView;
        process();
        if (DEBUG) {
            IMPL.into(this);
        } else {
            try {
                IMPL.into(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void cache() {
        if (IMPL == null) {
            return;
        }

        process();
        if (DEBUG) {
            IMPL.cache(this);
        } else {
            try {
                IMPL.cache(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Object sync() {
        if (IMPL == null) {
            return null;
        }

        if (P.width < 0 || P.height < 0) {
            throw new IllegalStateException("you must call size(int, int) before sync");
        }
        P.loaderListener = null;
        process();

        if (DEBUG) {
            return IMPL.sync(this);
        } else {
            try {
                return IMPL.sync(this);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    @Override
    public Object sync4Web() {
        if (IMPL == null) {
            return null;
        }

        P.loaderListener = null;
        process();

        if (DEBUG) {
            return IMPL.sync(this);
        } else {
            try {
                return IMPL.sync(this);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    @Override
    public void clearMemoryCache() {
        if (IMPL == null) {
            return;
        }
        IMPL.clearMemoryCache(mContext);
    }

    @Override
    public void clearDiskCache() {
        if (IMPL == null) {
            return;
        }
        IMPL.clearDiskCache(mContext);
    }

    /*  get method start*/
    public Context getContext() {
        return mContext;
    }

    public Fragment getFragment() {
        return mFragment;
    }

    public int getLoadType() {
        return P.loadType;
    }

    public String getUrl() {
        return P.url;
    }

    public String getImagesize() {
        return P.imagesize;
    }

    public int getResourceId() {
        return P.resourceId;
    }

    public File getFile() {
        return P.file;
    }

    public Uri getUri() {
        return P.uri;
    }

    public int getWidth() {
        return P.width;
    }

    public int getHeight() {
        return P.height;
    }

    public int getRequestType() {
        return P.requestType;
    }

    public int getPlaceholder() {
        return P.placeholder;
    }

    public int getError() {
        return P.error;
    }

    public boolean isSkipMemoryCache() {
        return P.skipMemoryCache;
    }

    public int getBackground() {
        return P.background;
    }

    public int getPriority() {
        return P.priority;
    }

    public int getScaleType() {
        return P.scaleType;
    }

    public ImageView getImageView() {
        return P.imageView;
    }

    public int getImageType() {
        return P.imageType;
    }

    public boolean isPlaceholderScaleTypeAsView() {
        return P.placeholderScaleTypeAsView;
    }

    public ImageLoaderListener getLoaderListener() {
        return P.loaderListener;
    }

    public boolean isOptimizeNetwork() {
        return P.optimizeNetwork;
    }
    /*  get method end*/

    @Override
    public String toString() {
        return "ImageRequest{" +
                "imageType=" + P.imageType +
                ", mContext=" + mContext +
                ", fragment=" + mFragment +
                ", url='" + P.url + '\'' +
                ", imagesize='" + P.imagesize + '\'' +
                ", resourceId=" + P.resourceId +
                ", file=" + P.file +
                ", uri=" + P.uri +
                ", width=" + P.width +
                ", height=" + P.height +
                ", requestType=" + P.requestType +
                ", placeholder=" + P.placeholder +
                ", error=" + P.error +
                ", skipMemoryCache=" + P.skipMemoryCache +
                ", background=" + P.background +
                ", priority=" + P.priority +
                ", scaleType=" + P.scaleType +
                ", optimizeNetwork=" + P.optimizeNetwork +
                ", loaderListener=" + P.loaderListener +
                ", imageView=" + P.imageView +
                '}';
    }

    public static class Builder {

        private Context mContext;
        private Fragment mFragment;
        private ImageRequest mRequest;
        private ImageRequestParams P;

        public Builder(Context context) {
            mContext = context;
            P = new ImageRequestParams(context);
        }

        public Builder(Fragment fragment) {
            mFragment = fragment;
            P = new ImageRequestParams(fragment.getActivity());
        }

        /**
         * load resource with url.
         *
         * @param url
         * @return
         */
        public Builder load(String url) {
            P.loadType = P.LOAD_IMG_URL;
            P.url = url;
            return this;
        }

        /**
         * load resource with resourceId.
         *
         * @param resourceId
         * @return
         */
        public Builder load(int resourceId) {
            P.loadType = P.LOAD_IMG_RES;
            P.resourceId = resourceId;
            return this;
        }

        /**
         * load resource with file.
         *
         * @param file
         * @return
         */
        public Builder load(File file) {
            P.loadType = P.LOAD_IMG_FILE;
            P.file = file;
            return this;
        }

        /**
         * load resource with file.
         *
         * @param uri
         * @return
         */
        public Builder load(Uri uri) {
            P.loadType = P.LOAD_IMG_URI;
            P.uri = uri;
            return this;
        }


        /**
         * url append with {@link ImageSize}
         *
         * @param imageSize
         * @return
         * @see #append(String)
         * @see #micro()
         * @see #small()
         * @see #middle()
         * @see #large()
         * @see #larger()
         * @see #fitlarge()
         * @see #fitlarger()
         */
        public Builder append(String imageSize) {
            P.imagesize = imageSize;
            return this;
        }


        /**
         * url append with {@link ImageSize#Micro}
         *
         * @return
         * @see #append(String)
         * @see #micro()
         * @see #small()
         * @see #middle()
         * @see #large()
         * @see #larger()
         * @see #fitlarge()
         * @see #fitlarger()
         */
        public Builder micro() {
            P.imageType = P.MICRO_SIZE;
            return this;
        }

        /**
         * url append with {@link ImageSize#Small}
         *
         * @return
         * @see #append(String)
         * @see #micro()
         * @see #small()
         * @see #middle()
         * @see #large()
         * @see #larger()
         * @see #fitlarge()
         * @see #fitlarger()
         */
        public Builder small() {
            P.imageType = P.SMALL_SIZE;
            return this;
        }

        /**
         * url append with {@link ImageSize#Middle}
         *
         * @return
         * @see #append(String)
         * @see #micro()
         * @see #small()
         * @see #middle()
         * @see #large()
         * @see #larger()
         * @see #fitlarge()
         * @see #fitlarger()
         */
        public Builder middle() {
            P.imageType = P.MIDDLE_SIZE;
            return this;
        }

        /**
         * url append with {@link ImageSize#Big}
         *
         * @return
         * @see #append(String)
         * @see #micro()
         * @see #small()
         * @see #middle()
         * @see #large()
         * @see #larger()
         * @see #fitlarge()
         * @see #fitlarger()
         */
        public Builder big() {
            P.imageType = P.BIG_SIZE;
            return this;
        }

        /**
         * url append with {@link ImageSize#Large}
         *
         * @return
         * @see #append(String)
         * @see #micro()
         * @see #small()
         * @see #middle()
         * @see #large()
         * @see #larger()
         * @see #fitlarge()
         * @see #fitlarger()
         */
        public Builder large() {
            P.imageType = P.LARGE_SIZE;
            return this;
        }

        /**
         * url append with {@link ImageSize#Larger}
         *
         * @return
         * @see #append(String)
         * @see #micro()
         * @see #small()
         * @see #middle()
         * @see #large()
         * @see #larger()
         * @see #fitlarge()
         * @see #fitlarger()
         */
        public Builder larger() {
            P.imageType = P.LARGER_SIZE;
            return this;
        }

        /**
         * url append with {@link ImageSize#Fit_Large}
         *
         * @return
         * @see #append(String)
         * @see #micro()
         * @see #small()
         * @see #middle()
         * @see #large()
         * @see #larger()
         * @see #fitlarge()
         * @see #fitlarger()
         */
        public Builder fitlarge() {
            P.imageType = P.FIT_LARGE_SIZE;
            return this;
        }

        /**
         * url append with {@link ImageSize#Fit_Larger}
         *
         * @return
         * @see #append(String)
         * @see #micro()
         * @see #small()
         * @see #middle()
         * @see #large()
         * @see #larger()
         * @see #fitlarge()
         * @see #fitlarger()
         */
        public Builder fitlarger() {
            P.imageType = P.FIT_LARGER_SIZE;
            return this;
        }


        /**
         * not use memory cache.
         *
         * @return
         */
        public Builder skipMemoryCache() {
            P.skipMemoryCache = true;
            return this;
        }

        /**
         * set use background with imageview.the default color was {}
         *
         * @return
         * @see #background(int)
         */
        public Builder background() {
            P.background = Color.parseColor("#ffffff");
            return this;
        }


        /**
         * @param colorId background color with imageview.
         * @return
         * @see #background()
         */
        public Builder background(@ColorRes int colorId) {
            if (mContext != null) {
                P.background = mContext.getResources().getColor(colorId);
            } else if (mFragment != null) {
                P.background = mFragment.getActivity().getResources().getColor(colorId);
            }
            return this;
        }

        /**
         * priority with imageloader. immediate load.
         *
         * @return
         */
        public Builder immediate() {
            P.priority = P.IMMEDIATE_PRIORITY;
            return this;
        }

        /**
         * priority with imageloader.
         *
         * @return
         */
        public Builder high() {
            P.priority = P.HIGH_PRIORITY;
            return this;
        }

        /**
         * priority with imageloader.
         *
         * @return
         */
        public Builder normal() {
            P.priority = P.NORMAL_PRIORITY;
            return this;
        }

        /**
         * priority with imageloader.
         *
         * @return
         */
        public Builder low() {
            P.priority = P.LOW_PRIORITY;
            return this;
        }

        /**
         * it will resize bitmap when the bitmap was loaded.
         *
         * @param width
         * @param height
         * @return
         */
        public Builder size(int width, int height) {
            P.width = width;
            P.height = height;
            return this;
        }

        /**
         * set the scale type with imageview
         *
         * @return
         * @see #centerCrop()
         */
        public Builder fitCenter() {
            P.scaleType = P.SCALE_TYPE_FIT_CENTER;
            return this;
        }

        /**
         * set the scale type with imageview
         *
         * @return
         * @see #fitCenter()
         */
        public Builder centerCrop() {
            P.scaleType = P.SCALE_TYPE_CENTER_CROP;
            return this;
        }

        /**
         * @param placeholder
         * @return
         * @see #placeholder
         * @see #placeholder(int)
         * @see #placeholderMicro()
         * @see #placeholderSmall()
         * @see #placeholderMiddle()
         * @see #placeholderLarge()
         * @see #noplaceholder()
         */
        public Builder placeholder(int placeholder) {
            P.placeholder = placeholder;
            return this;
        }

        /**
         * not use placeholder.
         *
         * @return
         * @see #placeholder
         * @see #placeholder(int)
         * @see #placeholderMicro()
         * @see #placeholderSmall()
         * @see #placeholderMiddle()
         * @see #placeholderLarge()
         * @see #noplaceholder()
         */
        public Builder noplaceholder() {
            P.placeholder = Integer.MIN_VALUE;
            return this;
        }

        /**
         * set the placeholder image scale type as same with load image.
         * the placeholder's default scale type was fitcenter.
         *
         * @return
         */
        public Builder placeholderScaleTypeAsView() {
            P.placeholderScaleTypeAsView = true;
            return this;
        }

        /**
         * set placeholder.
         *
         * @return
         * @see #placeholder
         * @see #placeholder(int)
         * @see #placeholderMicro()
         * @see #placeholderSmall()
         * @see #placeholderMiddle()
         * @see #placeholderLarge()
         * @see #noplaceholder()
         */
        public Builder placeholderMicro() {
            P.placeholder = P.placeHolderMicro;
            return this;
        }

        /**
         * set placeholder.
         *
         * @return
         * @see #placeholder
         * @see #placeholder(int)
         * @see #placeholderMicro()
         * @see #placeholderSmall()
         * @see #placeholderMiddle()
         * @see #placeholderLarge()
         * @see #noplaceholder()
         */
        public Builder placeholderSmall() {
            P.placeholder = P.placeHolderSmall;
            return this;
        }

        /**
         * set placeholder
         *
         * @return
         * @see #placeholder
         * @see #placeholder(int)
         * @see #placeholderMicro()
         * @see #placeholderSmall()
         * @see #placeholderMiddle()
         * @see #placeholderLarge()
         * @see #noplaceholder()
         */
        public Builder placeholderMiddle() {
            P.placeholder = P.placeHolderMiddle;
            return this;
        }

        /**
         * set placeholder
         *
         * @return
         * @see #placeholder
         * @see #placeholder(int)
         * @see #placeholderMicro()
         * @see #placeholderSmall()
         * @see #placeholderMiddle()
         * @see #placeholderLarge()
         * @see #noplaceholder()
         */
        public Builder placeholderLarge() {
            P.placeholder = P.placeHolderLarge;
            return this;
        }

        /**
         * the default error placeholder is placeholder
         *
         * @param error error placeholder
         * @return
         * @see #process().
         */
        public Builder error(int error) {
            P.error = error;
            return this;
        }

        /**
         * this method will optimize network by reduce imagesize.
         * Not achieved.
         *
         * @return
         */
        public Builder optimizeNetwork() {
            P.optimizeNetwork = true;
            return this;
        }

        /**
         * request image as git,
         * has not test this method.
         *
         * @return
         */
        public Builder asGif() {
            P.requestType = P.GET_GIF;
            return this;
        }

        /**
         * @param loaderListener callback
         * @return
         */
        public Builder listener(ImageLoaderListener loaderListener) {
            P.loaderListener = loaderListener;
            return this;
        }


        private ImageRequest build() {
            ImageRequest request;
            if (mContext != null) {
                request = new ImageRequest(mContext, P);
            } else {
                request = new ImageRequest(mFragment, P);
            }
            return request;
        }

        public void into(ImageView imageView) {
            if (mRequest == null) {
                mRequest = build();
            }
            mRequest.into(imageView);
        }

        public void cache() {
            if (mRequest == null) {
                mRequest = build();
            }
            mRequest.cache();
        }

        public Object sync() {
            if (mRequest == null) {
                mRequest = build();
            }
            return mRequest.sync();
        }

        public Object sync4Web() {
            if (mRequest == null) {
                mRequest = build();
            }
            return mRequest.sync4Web();
        }

        public void clearMemoryCache() {
            if (mRequest == null) {
                mRequest = build();
            }
            mRequest.clearMemoryCache();
        }

        public void clearDiskCache() {
            if (mRequest == null) {
                mRequest = build();
            }
            mRequest.clearDiskCache();
        }
    }

}
