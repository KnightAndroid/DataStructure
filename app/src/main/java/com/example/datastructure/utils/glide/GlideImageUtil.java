package com.example.datastructure.utils.glide;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/**
 * Describe :
 * Created by Knight on 2019/1/19
 * 点滴之行,看世界
 **/
public class GlideImageUtil {

    /**
     * 加载本地圆形图片
     * @param imageView 指定的View
     * @param resourceId 本地指定图片的路径
     */
    public static void loadLocalCircleImage(ImageView imageView,int resourceId){
          GlideApp.with(imageView.getContext()).load(resourceId).diskCacheStrategy(DiskCacheStrategy.RESOURCE).apply(RequestOptions.circleCropTransform())
                  .error(new ColorDrawable(Color.WHITE)).into(imageView);
    }

    /**
     * 加载本地图片
     *
     * @param imageView 指定的View
     * @param resourceId 本地图片路径
     *
     */
    public static void loadLocalImage(ImageView imageView,int resourceId){
        GlideApp.with(imageView.getContext()).load(resourceId).diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .error(new ColorDrawable(Color.WHITE)).into(imageView);
    }

    /**
     * 加载网络圆形图片
     * @param imageView 指定的View
     * @param uri 网络地址
     */
    public static void loadNetWorkCircleImage(ImageView imageView,String uri){
        GlideApp.with(imageView.getContext()).load(uri).diskCacheStrategy(DiskCacheStrategy.RESOURCE).apply(RequestOptions.circleCropTransform())
                .error(new ColorDrawable(Color.WHITE)).into(imageView);
    }

    /**
     * 加载网络图片
     * @param imageView
     * @param uri
     */
    public static void loadNetWorkImage(ImageView imageView,String uri){
        GlideApp.with(imageView.getContext()).load(uri).diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .error(new ColorDrawable(Color.WHITE)).into(imageView);
    }

    /**
     * 加载高斯本地图片
     * @param imageView
     * @param resourceId
     */
    public static void loadLocalImageBlur(ImageView imageView,int resourceId){
        GlideApp.with(imageView).load(resourceId).apply(RequestOptions.bitmapTransform(new BlurTransformation(imageView.getContext())))
                .into(imageView);
    }

    /**
     * 加载高斯网络图片
     *
     * @param imageView
     * @param uri
     */
    public static void loadNetImageBlur(ImageView imageView,String uri){
        GlideApp.with(imageView).load(uri).apply(RequestOptions.bitmapTransform(new BlurTransformation(imageView.getContext())))
                .into(imageView);


    }


}

