package com.example.zingdemoapi.request;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.zingdemoapi.R;

public class GlideRequest {
    private static GlideRequest instance;

    private GlideRequest(){

    }

    public static GlideRequest getInstance(){
        if (instance == null){
            synchronized (GlideRequest.class){
                if (instance == null){
                    instance = new GlideRequest();
                }
            }
        }
        return instance;
    }

    public void loadImage(RequestManager requestManager, String url, ImageView imageView){
        requestManager
                .load(url)
                //.error(R.drawable.null_image)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(imageView);
    }

}
