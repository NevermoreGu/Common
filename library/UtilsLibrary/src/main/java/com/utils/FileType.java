package com.utils;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Created by guqian on 2017/5/23.
 */


@IntDef({FileType.IMG, FileType.VID})
@Retention(RetentionPolicy.SOURCE)
public @interface FileType {

    int IMG = 0;
    int VID = 1;
}
