package com.batman.baselibrary.image;

import android.content.Context;

public interface IImageLoader {

    void loadImage(Context context, ImageLoader imageLoader);

    void clearMemoryCache();
}
