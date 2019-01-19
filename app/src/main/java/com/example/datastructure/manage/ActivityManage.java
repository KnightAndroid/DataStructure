package com.example.datastructure.manage;

import com.example.datastructure.base.BaseActivity;

import java.util.HashSet;
import java.util.Set;

/**
 * Describe :
 * Created by Knight on 2019/1/19
 * 点滴之行,看世界
 **/
public class ActivityManage {



    //保存所有创建的Activity
    private Set<BaseActivity> allActivityes = new HashSet<>();


    /**
     * 从管理器添加
     * 添加Activity
     *
     * @param baseActivity
     */
    public void addActivity(BaseActivity baseActivity){
        if(baseActivity != null){
            allActivityes.add(baseActivity);
        }
    }

    /**
     * 从管理器移除Activity
     *
     */
    public void removeActivity(BaseActivity baseActivity){
        if(baseActivity != null){
            allActivityes.remove(baseActivity);
        }
    }

    /**
     * 关闭所有Activity
     *
     */
    public void finishAll(){
        for(BaseActivity activity : allActivityes){
            activity.finish();
        }

    }

}
