package com.batman.baselibrary.image;

import android.content.Context;

/**
 * 代理模式
 */
public class ImageLoadProxy implements IImageLoader {

    private static ImageLoadProxy mImageLoadProxy;

    private IImageLoader mIImageLoader;

    private ImageLoadProxy() {
        mIImageLoader = new GlideImageLoader();
    }

    public static ImageLoadProxy getInstance() {
        synchronized (ImageLoadProxy.class) {
            if (mImageLoadProxy == null) {
                mImageLoadProxy = new ImageLoadProxy();
            }
        }
        return mImageLoadProxy;
    }

    @Override
    public void loadImage(Context context, ImageLoader imageLoader) {
        mIImageLoader.loadImage(context,imageLoader);
    }

    @Override
    public void clearMemoryCache() {
        mIImageLoader.clearMemoryCache();
    }
}
