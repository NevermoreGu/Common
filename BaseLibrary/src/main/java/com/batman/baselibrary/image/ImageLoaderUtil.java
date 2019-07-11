package com.batman.baselibrary.image;

import android.content.Context;

/**
 * 策略模式
 */
@Deprecated
public class ImageLoaderUtil {

    private static ImageLoaderUtil mInstance;
    private IImageLoader mStrategy;

    private ImageLoaderUtil() {
        mStrategy = new GlideImageLoader();
    }

    public static ImageLoaderUtil getInstance() {
        if (mInstance == null) {
            synchronized (ImageLoaderUtil.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoaderUtil();
                    return mInstance;
                }
            }
        }
        return mInstance;
    }

    public void loadImage(Context context, ImageLoader imageLoader) {
        mStrategy.loadImage(context, imageLoader);
    }

    public void setLoadImgStrategy(IImageLoader strategy) {
        mStrategy = strategy;
    }
}
