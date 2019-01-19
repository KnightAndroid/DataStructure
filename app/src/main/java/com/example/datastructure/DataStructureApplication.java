package com.example.datastructure;

import android.app.Application;
import android.content.Context;
import androidx.multidex.MultiDex;

import com.example.datastructure.manage.ActivityManage;

/**
 * Describe :
 * Created by Knight on 2019/1/3
 * 点滴之行,看世界
 **/
public class DataStructureApplication extends Application {
    //全局唯一的context
    private static DataStructureApplication application;
    //Activity管理器
    private ActivityManage activityManage;


    /**
     * 获取全局唯一上下文
     *
     * @return DataStructureApplication
     */
    public static DataStructureApplication getApplication(){
        return application;
    }
    @Override
    protected void attachBaseContext(Context base){
        super.attachBaseContext(base);
        application = this;
        //MultiDex分包方法 必须先初始化
        MultiDex.install(this);
    }

    @Override
    public void onCreate(){
        super.onCreate();
        activityManage = new ActivityManage();
    }


    /**
     * 程序终止的时候执行
     *
     */
    @Override
    public void onTerminate(){
        super.onTerminate();
        exitApp();
    }




    public void exitApp(){
        activityManage.finishAll();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);

    }

    /**
     * 返回Activity管理器
     *
     * @return
     */
    public ActivityManage getActivityManage(){
        if(activityManage == null){
           activityManage = new ActivityManage();
        }
        return activityManage;
    }
















}
