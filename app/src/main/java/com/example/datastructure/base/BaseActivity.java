package com.example.datastructure.base;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import android.view.WindowManager;

import com.example.datastructure.DataStructureApplication;
import com.example.datastructure.R;
import com.example.datastructure.utils.CleanLeakUtils;
import com.example.datastructure.utils.glide.GlideApp;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Describe :
 * Created by Knight on 2019/1/3
 * 点滴之行,看世界
 **/
public abstract class BaseActivity extends FragmentActivity {

    //黄油刀实例
    private Unbinder unbinder;
    //沉浸栏
    protected ImmersionBar immersionBar;
    protected Context context;

   @Override
    protected void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       context = this;
       getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
       //初始化ButterKnife
       unbinder = ButterKnife.bind(this);
       //沉浸式状态栏
       initImmersionBar(R.color.colorPrimary);
       //加入Activity管理器
       DataStructureApplication.getApplication().getActivityManage().addActivity(this);

   }


   //设置沉浸式状态栏颜色
   protected void initImmersionBar(int color){
       immersionBar = ImmersionBar.with(this);
       if(color != 0){
           immersionBar.statusBarColor(color);
       }
       immersionBar.init();
   }


    /**
     *
     * Activity被销毁的时候做一些移除 关闭操作
     */
    protected void onDestroy(){
       super.onDestroy();
       if(unbinder != null){
           unbinder.unbind();
       }

       //必须调用该方法,防止内存泄漏
       if(immersionBar != null){
          immersionBar.destroy();
       }
       //将Activity从管理器移除
       DataStructureApplication.getApplication().getActivityManage().removeActivity(this);
       CleanLeakUtils.fixInputMethodManagerLeak(BaseActivity.this);
       GlideApp.get(this).clearMemory();
       GlideApp.get(this).getBitmapPool().clearMemory();

   }


   @Override
    public void onAttachedToWindow(){
        super.onAttachedToWindow();
   }

}
