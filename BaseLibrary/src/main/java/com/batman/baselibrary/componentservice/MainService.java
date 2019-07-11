package com.batman.baselibrary.componentservice;

import com.alibaba.android.arouter.facade.template.IProvider;

import java.util.HashMap;

/**
 * @author guqian
 */
public interface MainService extends IProvider {


    void getMobId(MobIdSuccess mobIdSuccess);

    HashMap getMap(String id,String model);

}

