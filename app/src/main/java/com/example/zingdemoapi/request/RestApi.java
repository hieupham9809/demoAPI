package com.example.zingdemoapi.request;

import com.example.zingdemoapi.datamodel.Home;
import com.example.zingdemoapi.datamodel.ProgramInfo;
import com.example.zingdemoapi.typeadapter.HomeAdapter;
import com.example.zingdemoapi.typeadapter.ProgramInfoAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApi {
    final private String BASE = "http://dev.api.tv.zing.vn/";
    private static RestApi instance;

    private RestApi() {

    }

    private RequestInterface requestInterface = new Retrofit.Builder()
            .baseUrl(BASE)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(getGSONConvertFactory()))
            .build().create(RequestInterface.class);

    private Gson getGSONConvertFactory() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Home.class, new HomeAdapter());
        builder.registerTypeAdapter(ProgramInfo.class, new ProgramInfoAdapter());
        return builder.create();
    }

    public Observable<Home> getHome() {
        return requestInterface.register();
    }

    public Observable<ProgramInfo> getProgramInfo(String programId) {
        return requestInterface.getProgramInfo(programId);
    }


    public static RestApi getInstance() {
        if (instance == null) {
            synchronized (RestApi.class) {
                if (instance == null) {
                    instance = new RestApi();
                }
            }
        }
        return instance;
    }
}
