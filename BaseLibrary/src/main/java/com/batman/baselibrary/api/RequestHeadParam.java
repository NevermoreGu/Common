package com.batman.baselibrary.api;

import android.text.TextUtils;

import com.batman.baselibrary.Constant;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.network.utils.CxSecureInnerUtil;

import java.net.URLEncoder;
import java.util.HashMap;


public class RequestHeadParam {

    private static volatile RequestHeadParam mRemoteParametersManager;
    private static volatile String model;
    private static volatile String network;
    private static volatile String macAddress;
    private static volatile String sysVersion;
    private static volatile String sysVersionCode;
    private static volatile String isDeviceRooted;
    private static volatile String networkOperator;
    private static volatile String uuid;
    private static volatile String version;
    private static volatile String package_id;
    private static volatile String accid;

    private String mAccount;

    public void setAccount(String account) {
        this.mAccount = account;
    }

    public String getAccount() {
        return mAccount;
    }

    public static RequestHeadParam getInstance() {
        if (null == mRemoteParametersManager) {
            synchronized (RequestHeadParam.class) {
                if (null == mRemoteParametersManager) {
                    mRemoteParametersManager = new RequestHeadParam();
                }
            }
        }
        return mRemoteParametersManager;
    }

    public String getModel() {
        if (TextUtils.isEmpty(model)) {
            synchronized (this) {
                if (TextUtils.isEmpty(model)) {
                    model = DeviceUtils.getModel();
                }
            }
        }
        return model;
    }

    public String getSysVersion() {
        if (TextUtils.isEmpty(sysVersion)) {
            synchronized (this) {
                if (TextUtils.isEmpty(sysVersion)) {
                    sysVersion = DeviceUtils.getSDKVersionName();
                }
            }
        }
        return sysVersion;
    }

    public String getSysVersionCode() {
        if (TextUtils.isEmpty(sysVersionCode)) {
            synchronized (this) {
                if (TextUtils.isEmpty(sysVersionCode)) {
                    sysVersionCode = DeviceUtils.getSDKVersionCode() + "";
                }
            }
        }
        return sysVersionCode;
    }

    public String getIsDeviceRooted() {
        if (TextUtils.isEmpty(isDeviceRooted)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    isDeviceRooted = DeviceUtils.isDeviceRooted() + "";
                }
            }).start();
        }
        return isDeviceRooted;
    }

    public String getNetworkOperator() {
        if (TextUtils.isEmpty(networkOperator)) {
            synchronized (this) {
                if (TextUtils.isEmpty(networkOperator)) {
                    networkOperator = URLEncoder.encode(NetworkUtils.getNetworkOperatorName());
                }
            }
        }
        return networkOperator;
    }

    public String getUuid() {
        if (TextUtils.isEmpty(uuid)) {
            synchronized (this) {
                if (TextUtils.isEmpty(uuid)) {
                    uuid = !TextUtils.isEmpty(DeviceUtils.getAndroidID()) ? DeviceUtils.getAndroidID() : DeviceUtils.getMacAddress();
                }
            }
        }
        return uuid;
    }

    public String getVersion() {
        if (TextUtils.isEmpty(version)) {
            synchronized (this) {
                if (TextUtils.isEmpty(version)) {
                    version = Constant.VERSON_NAME;
                }
            }
        }
        return version;
    }

    public String getPackage_id() {
//        if (TextUtils.isEmpty(package_id)) {
//            synchronized (this) {
//                if (TextUtils.isEmpty(package_id)) {
//                    package_id = Constant.PACKAGE_ID;
//                }
//            }
//        }
        return "com.youxinchat.talk";
    }

    private String getAccid() {
        if (TextUtils.isEmpty(accid)) {
            synchronized (this) {
                if (TextUtils.isEmpty(accid)) {
                    try {
                        accid = !TextUtils.isEmpty(getAccount()) ? CxSecureInnerUtil.encryptTradeInfo(getAccount()) : "";
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return accid;
    }

    public HashMap<String, String> getBaseParameters() {
//        HashMap<String, String> mOtherParametersMap = new HashMap<>();
//        mOtherParametersMap.put("model", getModel());
//        mOtherParametersMap.put("network", NetworkUtils.getNetworkType().name().replace("NETWORK_", ""));
//        mOtherParametersMap.put("macAddress", DeviceUtils.getMacAddress());
//        mOtherParametersMap.put("sysVersion", getSysVersion());
//        mOtherParametersMap.put("sysVersionCode", getSysVersionCode());
//        mOtherParametersMap.put("isDeviceRooted", getIsDeviceRooted());
//        mOtherParametersMap.put("networkOperator", getNetworkOperator());
//        mBaseParametersMap.put("other", new Gson().toJson(mOtherParametersMap));

        HashMap<String, String> mBaseParametersMap = new HashMap<>();

        mBaseParametersMap.put("charset", "UTF-8");
        mBaseParametersMap.put("uuid", getUuid());
        mBaseParametersMap.put("version", getVersion());
        mBaseParametersMap.put("package_id", getPackage_id());
        if (!TextUtils.isEmpty(getAccid())) {
            mBaseParametersMap.put("accid", getAccid());
        }

        return mBaseParametersMap;
    }
}
