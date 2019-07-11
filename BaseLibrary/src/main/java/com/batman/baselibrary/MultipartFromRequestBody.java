package com.batman.baselibrary;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Synopsis     ${SYNOPSIS}
 * Author		Mosr
 * Version		${VERSION}
 * Create 	    2019/3/18 20:46
 * Email  		intimatestranger@sina.cn
 */
public class MultipartFromRequestBody implements Serializable {
    private static final long serialVersionUID = 877933051155742086L;
    //文件上传的keyname
    private String mFilesKeyName;
    private String mFilesType;
    private List<File> mFiles;
    private List<String> mFileUrls;
    private Map<String, String> mBodyMaps;

    public String getmFilesKeyName() {
        return mFilesKeyName;
    }

    public MultipartFromRequestBody setmFilesKeyName(String mFilesKeyName) {
        this.mFilesKeyName = mFilesKeyName;
        return this;
    }

    public String getmFilesType() {
        return mFilesType;
    }

    public MultipartFromRequestBody setmFilesType(String mFilesType) {
        this.mFilesType = mFilesType;
        return this;
    }

    public List<File> getmFiles() {
        return mFiles;
    }

    public List<String> getmFileUrls() {
        return mFileUrls;
    }

    public MultipartFromRequestBody setmFileUrls(List<String> mFileUrls) {
        this.mFileUrls = mFileUrls;
        return this;
    }

    public MultipartFromRequestBody setmFiles(List<File> mFiles) {
        this.mFiles = mFiles;
        return this;
    }

    public Map<String, String> getmBodyMaps() {
        return mBodyMaps;
    }

    public MultipartFromRequestBody setmBodyMaps(Map<String, String> mBodyMaps) {
        this.mBodyMaps = mBodyMaps;
        return this;
    }
}
