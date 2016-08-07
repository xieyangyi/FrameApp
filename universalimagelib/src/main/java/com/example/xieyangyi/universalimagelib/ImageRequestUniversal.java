package com.example.xieyangyi.universalimagelib;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import com.example.xieyangyi.framesdk.imageloader.ImageRequest;
import com.example.xieyangyi.framesdk.imageloader.ImageRequestImpl;
import com.example.xieyangyi.framesdk.imageloader.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by xieyangyi on 16/7/30.
 */
public class ImageRequestUniversal implements ImageRequestImpl {

    private static ImageRequestUniversal sInstance;
    private static boolean isInited = false;

    private ImageRequestUniversal() {}

    public static ImageRequestUniversal getInstance() {
        if (sInstance == null) {
            synchronized (ImageRequestUniversal.class) {
                if (sInstance == null) {
                    init();
                    sInstance = new ImageRequestUniversal();
                }
            }
        }
        return sInstance;
    }

    private static void init() {
        if (!isInited) {
            ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(Utils.getApplicationContext());
            ImageLoader.getInstance().init(configuration);
            isInited = true;
        }
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


//        if (!request.isPlaceholderScaleTypeAsView()) {
//            ImageView.ScaleType scaleType = (ImageView.ScaleType) request.getImageView().getTag(R.id.image_scale_type);
//            if (scaleType == null) {
//                request.getImageView().setTag(R.id.image_scale_type, request.getImageView().getScaleType());
//            }
//            request.getImageView().setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//        }

        if (request.getBackground() != Integer.MIN_VALUE) {
            request.getImageView().setBackgroundColor(request.getBackground());
        }

        DisplayImageOptions options = createImageOptions(request);
        ImageSize imageSize = createImageSize(request);

        ImageLoader.getInstance().displayImage(
                request.getUrl(),
                new ImageViewAware(request.getImageView()),
                options,
                imageSize,
                new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String s, View view) {
                        if (request.getLoaderListener() != null) {
                            request.getLoaderListener().onLoadStarted(request.getImageView());
                        }
                    }

                    @Override
                    public void onLoadingFailed(String s, View view, FailReason failReason) {
                        if (request.getLoaderListener() != null) {
                            request.getLoaderListener().onLoadFailed(request.getImageView(), request.getUrl(), s);
                        }
                    }

                    @Override
                    public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                        if (request.getLoaderListener() != null) {
                            request.getLoaderListener().onLoadSuccessed(request.getImageView(), request.getUrl(), bitmap);
                        }
                    }

                    @Override
                    public void onLoadingCancelled(String s, View view) {

                    }
                },
                null
        );
    }


    @Override
    public void cache(final ImageRequest request) {
        Context context = getContext(request);
        if (context != null && context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        DisplayImageOptions options = createImageOptions(request);
        ImageSize imageSize = createImageSize(request);

        ImageLoader.getInstance().loadImage(
                request.getUrl(),
                imageSize,
                options,
                new ImageLoadingListener() {
                    public void onLoadingStarted(String s, View view) {
                        if (request.getLoaderListener() != null) {
                            request.getLoaderListener().onLoadStarted(request.getImageView());
                        }
                    }

                    @Override
                    public void onLoadingFailed(String s, View view, FailReason failReason) {
                        if (request.getLoaderListener() != null) {
                            request.getLoaderListener().onLoadFailed(request.getImageView(), request.getUrl(), s);
                        }
                    }

                    @Override
                    public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                        if (request.getLoaderListener() != null) {
                            request.getLoaderListener().onLoadSuccessed(request.getImageView(), request.getUrl(), bitmap);
                        }
                    }

                    @Override
                    public void onLoadingCancelled(String s, View view) {

                    }
                }
        );

    }

    @Override
    public Object sync(final ImageRequest request) {

        return null;
    }

    @Override
    public void clearMemoryCache(Context context) {
        try {
            ImageLoader.getInstance().clearMemoryCache();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearDiskCache(Context context) {
        try {
            ImageLoader.getInstance().clearDiskCache();
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

    private DisplayImageOptions createImageOptions(ImageRequest request) {
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true);

        if (request.getPlaceholder() != Integer.MIN_VALUE) {
            builder = builder
                    .showImageOnLoading(request.getPlaceholder())
                    .showImageForEmptyUri(request.getPlaceholder())
                    .showImageOnFail(request.getPlaceholder());
        }

        if (request.getError() != Integer.MIN_VALUE) {
            builder = builder.showImageOnFail(request.getError());
        }

        return builder.build();
    }

    private ImageSize createImageSize(ImageRequest request) {
        if (request.getWidth() != Integer.MIN_VALUE && request.getHeight() != Integer.MIN_VALUE && request.getWidth() > 0 && request.getHeight() > 0) {
            return new ImageSize(request.getWidth(), request.getHeight());
        }

        return null;
    }

}
