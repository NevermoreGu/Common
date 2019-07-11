package com.batman.baselibrary.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.batman.baselibrary.delegate.ApplicationDispatcher;
import com.network.Resource;


/**
 * Created by hzxuwen on 2015/4/13.
 */
public class UserPres implements SharedPreferences.OnSharedPreferenceChangeListener {


    public static final String KEY_account = "KEY_account";
    public static final String KEY_token = "KEY_token";
    public static final String KEY_id = "KEY_id";
    public static final String KEY_createtime = "KEY_createtime";
    public static final String KEY_updatetime = "KEY_updatetime";
    public static final String KEY_phone = "KEY_phone";
    public static final String KEY_tluserid = "KEY_tluserid";
    public static final String KEY_pwd = "KEY_pwd";
    public static final String KEY_paypwd = "KEY_paypwd";
    public static final String KEY_attemp = "KEY_attemp";
    public static final String KEY_lastattemptime = "KEY_lastattemptime";
    public static final String KEY_islock = "KEY_islock";
    public static final String KEY_salt = "KEY_salt";
    public static final String KEY_props = "KEY_props";
    public static final String KEY_icon = "KEY_icon";
    public static final String KEY_sign = "KEY_sign";
    public static final String KEY_email = "KEY_email";
    public static final String KEY_birth = "KEY_birth";
    public static final String KEY_gender = "KEY_gender";
    public static final String KEY_ex = "KEY_ex";
    public static final String KEY_category = "KEY_category";
    public static final String KEY_needkick = "KEY_needkick";
    public static final String KEY_status = "KEY_status";
    public static final String KEY_donnopOpen = "KEY_donnopOpen";
    public static final String KEY_isSearchFlag = "KEY_isSearchFlag";
    public static final String KEY_yx_no = "KEY_yx_no";
    public static final String KEY_isCxnoSearchFlag = "KEY_isCxnoSearchFlag";
    public static final String KEY_app_id = "KEY_app_id";
    public static final String KEY_wx_openid = "KEY_wx_openid";
    public static final String KEY_isSetPayPwd = "KEY_isSetPayPwd";
    public static final String KEY_balance = "KEY_balance";

    public static final String KEY_isAuth = "KEY_isAuth";
    public static final String KEY_isTongSign = "KEY_isTongSign";

    public static final String OPEN = "0";
    public static final String OFF = "1";

    public String account;
    public String token;
    public String id;
    public String createtime;
    public String updatetime;
    public String phone;
    public String tluserid;
    public String pwd;
    public String paypwd;
    public String attemp;
    public String lastattemptime;
    public String islock;
    public String salt;
    public String name;
    public String props;
    public String icon;
    public String sign;
    public String email;
    public String birth;
    public String gender;
    public String ex;
    public String needkick;
    public String category;
    public String status;
    public String donnopOpen;
    public String isSearchFlag;
    public String yx_no;
    public String isCxnoSearchFlag;
    public String app_id;
    public String wx_openid;
    public String isSetPayPwd;
    public String balance;
    public String isAuth;
    public String isTongSign;

    private static UserPres mUserPres;

    private UserPres() {
        getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        loadPrefs(getSharedPreferences());
    }

    public static UserPres getInstance() {

        if (mUserPres == null) {
            mUserPres = new UserPres();
            mEditor = getSharedPreferences().edit();
        }
        return mUserPres;
    }

    private void loadPrefs(SharedPreferences prefs) {

        this.account = prefs.getString(KEY_account, "");
        this.token = prefs.getString(KEY_token, "");
        this.id = prefs.getString(KEY_id, "");
        this.createtime = prefs.getString(KEY_createtime, "");
        this.updatetime = prefs.getString(KEY_updatetime, "");
        this.phone = prefs.getString(KEY_phone, "");
        this.tluserid = prefs.getString(KEY_tluserid, "");
        this.pwd = prefs.getString(KEY_pwd, "");
        this.paypwd = prefs.getString(KEY_paypwd, "");
        this.attemp = prefs.getString(KEY_attemp, "");
        this.lastattemptime = prefs.getString(KEY_lastattemptime, "");
        this.islock = prefs.getString(KEY_islock, "");
        this.salt = prefs.getString(KEY_salt, "");
        this.props = prefs.getString(KEY_props, "");
        this.icon = prefs.getString(KEY_icon, "");
        this.sign = prefs.getString(KEY_sign, "");
        this.email = prefs.getString(KEY_email, "");
        this.birth = prefs.getString(KEY_birth, "");
        this.gender = prefs.getString(KEY_gender, "");
        this.ex = prefs.getString(KEY_ex, "");
        this.needkick = prefs.getString(KEY_needkick, "");
        this.category = prefs.getString(KEY_category, "");
        this.status = prefs.getString(KEY_status, "");
        this.donnopOpen = prefs.getString(KEY_donnopOpen, "");
        this.isSearchFlag = prefs.getString(KEY_isSearchFlag, "");
        this.yx_no = prefs.getString(KEY_yx_no, "");
        this.isCxnoSearchFlag = prefs.getString(KEY_isCxnoSearchFlag, "");
        this.app_id = prefs.getString(KEY_app_id, "");
        this.wx_openid = prefs.getString(KEY_wx_openid, "");
        this.isSetPayPwd = prefs.getString(KEY_isSetPayPwd, "");
        this.isAuth = prefs.getString(KEY_isAuth, "");
        this.isTongSign = prefs.getString(KEY_isTongSign, "");
        this.balance = prefs.getString(KEY_balance, "");
    }

    static SharedPreferences getSharedPreferences() {
        return ApplicationDispatcher.get().getApplicationContext().getSharedPreferences("IM_USER", Context.MODE_PRIVATE);
    }

    public static void save(Resource<UserResult> resource) {
        //保存数据
        UserPres.save(KEY_account, resource.data.yxuser.accid, false);
        UserPres.save(KEY_token, resource.data.yxuser.token, false);
        UserPres.save(KEY_id, resource.data.yxuser.id, false);
        UserPres.save(KEY_createtime, resource.data.yxuser.createtime, false);
        UserPres.save(KEY_updatetime, resource.data.yxuser.updatetime, false);
        UserPres.save(KEY_phone, resource.data.yxuser.phone, false);
        UserPres.save(KEY_tluserid, resource.data.yxuser.tluserid, false);
        UserPres.save(KEY_pwd, resource.data.yxuser.pwd, false);
        UserPres.save(KEY_paypwd, resource.data.yxuser.paypwd, false);
        UserPres.save(KEY_attemp, resource.data.yxuser.attemp, false);
        UserPres.save(KEY_lastattemptime, resource.data.yxuser.lastattemptime, false);
        UserPres.save(KEY_islock, resource.data.yxuser.islock, false);
        UserPres.save(KEY_salt, resource.data.yxuser.salt, false);
        UserPres.save(KEY_props, resource.data.yxuser.props, false);
        UserPres.save(KEY_icon, resource.data.yxuser.icon, false);
        UserPres.save(KEY_sign, resource.data.yxuser.sign, false);
        UserPres.save(KEY_email, resource.data.yxuser.email, false);
        UserPres.save(KEY_birth, resource.data.yxuser.birth, false);
        UserPres.save(KEY_gender, resource.data.yxuser.gender, false);
        UserPres.save(KEY_ex, resource.data.yxuser.ex, false);
        UserPres.save(KEY_needkick, resource.data.yxuser.needkick, false);
        UserPres.save(KEY_category, resource.data.yxuser.category, false);
        UserPres.save(KEY_status, resource.data.yxuser.status, false);
        UserPres.save(KEY_donnopOpen, resource.data.yxuser.donnopOpen, false);
        UserPres.save(KEY_isSearchFlag, resource.data.yxuser.isSearchFlag, false);
        UserPres.save(KEY_yx_no, resource.data.yxuser.yx_no, false);
        UserPres.save(KEY_isCxnoSearchFlag, resource.data.yxuser.isCxnoSearchFlag, false);
        UserPres.save(KEY_app_id, resource.data.yxuser.app_id, false);
        UserPres.save(KEY_wx_openid, resource.data.yxuser.wx_openid, false);
        UserPres.save(KEY_isSetPayPwd, resource.data.yxuser.isSetPayPwd, false);
        UserPres.save(KEY_balance, resource.data.yxuser.balance, false);
        UserPres.save(KEY_wx_openid, resource.data.yxuser.wx_openid, false);

        mEditor.commit();
    }

    public static void save(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.commit();
    }

    private static SharedPreferences.Editor mEditor;

    public static void save(String key, String value, boolean isSave) {
        if (mEditor == null) {
            mEditor = getSharedPreferences().edit();
        }
        mEditor.putString(key, value);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        loadPrefs(getSharedPreferences());
    }

    @Override
    protected void finalize() {
        getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
