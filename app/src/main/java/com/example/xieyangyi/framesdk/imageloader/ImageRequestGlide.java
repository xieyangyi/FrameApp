package com.example.xieyangyi.framesdk.imageloader;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.example.xieyangyi.framesdk.R;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by xieyangyi on 16/7/30.
 */
public class ImageRequestGlide implements ImageRequestImpl {

    private static ImageRequestGlide sInstance;

    private ImageRequestGlide() {}

    public static ImageRequestGlide getInstance() {
        if (sInstance == null) {
            synchronized (ImageRequestGlide.class) {
                if (sInstance == null) {
                    sInstance = new ImageRequestGlide();
                }
            }
        }
        return sInstance;
    }

    @Override
    public void into(final ImageRequest request) {
        Context context = getContext(request);
        if (context != null && context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }

        if (request.getLoaderListener() != null) {
            request.getLoaderListener().onLoadStarted(request.getImageView());
        }


        if (!request.isPlaceholderScaleTypeAsView()) {
            ImageView.ScaleType scaleType = (ImageView.ScaleType) request.getImageView().getTag(R.id.image_scale_type);
            if (scaleType == null) {
                request.getImageView().setTag(R.id.image_scale_type, request.getImageView().getScaleType());
            }
            request.getImageView().setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }

        if (request.getBackground() != Integer.MIN_VALUE) {
            request.getImageView().setBackgroundColor(request.getBackground());
        }

        createRequestBuilder(request).listener(new RequestListener() {
            @Override
            public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {

                if (!request.isPlaceholderScaleTypeAsView()) {
                    request.getImageView().setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                }
                if (request.getLoaderListener() != null) {
                    request.getLoaderListener().onLoadFailed(request.getImageView(), request.getUrl(), e == null ? "" : e.getMessage());
                }

                Map<String, String> stringMap = new HashMap<>();
                stringMap.put("url", request.getUrl());
                String msg = (e == null ? "" : e.getMessage());
                if (!TextUtils.isEmpty(msg)) {
                    stringMap.put("message", msg);
                }

//                Utils.reportError(request.getImageView().getContext(),"kGlideImageLoadFailed",stringMap);
                return false;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
                if (!request.isPlaceholderScaleTypeAsView()) {
                    request.getImageView().setScaleType((ImageView.ScaleType) request.getImageView().getTag(R.id.image_scale_type));
                }
                if (request.getBackground() != Integer.MIN_VALUE) {
                    request.getImageView().setBackgroundDrawable(null);
                }

                if (request.getLoaderListener() != null) {
                    request.getLoaderListener().onLoadSuccessed(request.getImageView(), request.getUrl(), resource);
                }
                return false;
            }
        }).into(request.getImageView());
    }

    @Override
    public void cache(final ImageRequest request) {
        Context context = getContext(request);
        if (context != null && context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }

        createRequestBuilder(request).into(new SimpleTarget() {

            @Override
            public void onLoadStarted(Drawable placeholder) {
                super.onLoadStarted(placeholder);
                if (request.getLoaderListener() != null) {
                    request.getLoaderListener().onLoadStarted(request.getImageView());
                }
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                if (request.getLoaderListener() != null) {
                    request.getLoaderListener().onLoadFailed(request.getImageView(), request.getUrl(), e == null ? "" : e.getMessage());
                }
                if (getContext(request) != null) {

                    Map<String, String> stringMap = new HashMap<>();
                    stringMap.put("url", request.getUrl());
                    String msg = (e == null ? "" : e.getMessage());
                    if (!TextUtils.isEmpty(msg)) {
                        stringMap.put("message", msg);
                    }

//                    Utils.reportError(getContext(request),"kGlideImageLoadFailed", stringMap);
                }
            }

            @Override
            public void onResourceReady(Object resource, GlideAnimation glideAnimation) {
                if (request.getLoaderListener() != null) {
                    request.getLoaderListener().onLoadSuccessed(request.getImageView(), request.getUrl(), resource);
                }
            }
        });
    }

    @Override
    public Object sync(final ImageRequest request) {
        Context context = getContext(request);
        if (context != null && context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return null;
            }
        }
        try {
            return createRequestBuilder(request).into(request.getWidth(), request.getHeight()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void clearMemoryCache(Context context) {
        try {
            Glide.get(context).clearMemory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearDiskCache(Context context) {
        try {
            Glide.get(context).clearDiskCache();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    final Context getContext(ImageRequest request) {
        if (request.getFragment() != null) {
            return request.getFragment().getActivity();
        }
        if (request.getContext() != null) {
            return request.getContext();
        }
        return null;
    }

    private GenericRequestBuilder createRequestBuilder(final ImageRequest request) {
        RequestManager requestManager = null;

        try {
            if (request.getFragment() != null) {
                requestManager = Glide.with(request.getFragment());
            }

            if (request.getContext() != null) {
                if (request.getContext() instanceof Activity) {
                    requestManager = Glide.with((Activity) request.getContext());
                } else {
                    requestManager = Glide.with(request.getContext());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (requestManager == null) {
            Log.d("GlideUtil","imageload with par was not right");
            Context context = Utils.getApplicationContext();
            requestManager = Glide.with(context);
        }

        if (requestManager == null) {
            throw new IllegalStateException("requestManager was null");
        }

        DrawableTypeRequest drawableTypeRequest = null;

        switch (request.getLoadType()) {
            case ImageRequestParams.LOAD_IMG_URL:
                drawableTypeRequest = requestManager.load(request.getUrl());
                break;
            case ImageRequestParams.LOAD_IMG_FILE:
                drawableTypeRequest = requestManager.load(request.getFile());
                break;
            case ImageRequestParams.LOAD_IMG_RES:
                drawableTypeRequest = requestManager.load(request.getResourceId());
                break;
            case ImageRequestParams.LOAD_IMG_URI:
                drawableTypeRequest = requestManager.load(request.getUri());
                break;
        }

        if (drawableTypeRequest == null) {
            throw new IllegalStateException("drawableTypeRequest == null");
        }

        GenericRequestBuilder requestBuilder;

        if (request.getRequestType() == ImageRequestParams.GET_BITMAP) {
            requestBuilder = drawableTypeRequest.asBitmap();
        } else if (request.getRequestType() == ImageRequestParams.GET_GIF) {
            requestBuilder = drawableTypeRequest.asGif();
        } else {
            throw new IllegalStateException("what's the fuck requestType = " + request.getRequestType());
        }

        if (request.getPlaceholder() != Integer.MIN_VALUE) {
            requestBuilder = requestBuilder.placeholder(request.getPlaceholder());
        }

        if (request.getError() != Integer.MIN_VALUE) {
            requestBuilder = requestBuilder.error(request.getError());
        }

        Priority priority = null;

        switch (request.getPriority()) {
            case ImageRequestParams.IMMEDIATE_PRIORITY:
                priority = Priority.IMMEDIATE;
                break;
            case ImageRequestParams.HIGH_PRIORITY:
                priority = Priority.HIGH;
                break;
            case ImageRequestParams.NORMAL_PRIORITY:
                priority = Priority.NORMAL;
                break;
            case ImageRequestParams.LOW_PRIORITY:
                priority = Priority.LOW;
                break;
        }

        if (priority != null) {
            requestBuilder = requestBuilder.priority(priority);
        }


        if (request.getWidth() != Integer.MIN_VALUE && request.getHeight() != Integer.MIN_VALUE && request.getWidth() > 0 && request.getHeight() > 0) {
            requestBuilder = requestBuilder.override(request.getWidth(), request.getHeight());
        }

        if (requestBuilder instanceof BitmapTypeRequest) {
            if (request.getScaleType() == ImageRequestParams.SCALE_TYPE_CENTER_CROP) {
                requestBuilder = ((BitmapTypeRequest) requestBuilder).centerCrop();
            } else if (request.getScaleType() == ImageRequestParams.SCALE_TYPE_FIT_CENTER) {
                requestBuilder = ((BitmapTypeRequest) requestBuilder).fitCenter();
            }

            requestBuilder = ((BitmapTypeRequest) requestBuilder).atMost();
        }

        requestBuilder = requestBuilder.diskCacheStrategy(DiskCacheStrategy.ALL);

        return requestBuilder;
    }

}
