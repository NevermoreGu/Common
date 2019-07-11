package com.batman.baselibrary.preference;

import java.util.List;

/**
 * 登录接口返回对象
 */
public class UserResult {

    public String code;
    public String desc;
    public String phone;
    public YxuserEntity yxuser;

    public static class YxuserEntity {

        public String id;
        public String createtime;
        public String updatetime;
        public String accid;
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
        public String token;
        public String sign;
        public String email;
        public String birth;
        public String mobile;
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
        public String isBindingWexi;
        public String isBindingAli;
        public String openid;
        public String aliuserid;
        public String balance;
        public List<?> newsList;
    }
}
