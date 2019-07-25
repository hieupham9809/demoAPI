package com.example.zingdemoapi.request;

import com.example.zingdemoapi.datamodel.Constant;
import com.example.zingdemoapi.datamodel.DataComment;
import com.example.zingdemoapi.datamodel.Home;
import com.example.zingdemoapi.datamodel.ProgramInfo;
import com.example.zingdemoapi.typeadapter.DataCommentAdapter;
import com.example.zingdemoapi.typeadapter.HomeAdapter;
import com.example.zingdemoapi.typeadapter.ProgramInfoAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApi {

    private static RestApi instance;
    private RequestInterface requestInterface;

    private RestApi() {
        requestInterface = new Retrofit.Builder()
                .baseUrl(Constant.BASE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(getGSONConvertFactory()))
                .build().create(RequestInterface.class);
    }

    private Gson getGSONConvertFactory() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Home.class, new HomeAdapter());
        builder.registerTypeAdapter(DataComment.class, new DataCommentAdapter());
        builder.registerTypeAdapter(ProgramInfo.class, new ProgramInfoAdapter());
        return builder.create();
    }

    public Observable<Home> getHome() {
        return requestInterface.register();
    }

    public Observable<ProgramInfo> getProgramInfo(int programId) {
        return requestInterface.getProgramInfo(programId);
    }
    public Observable<ProgramInfo> getProgramInfo(String programName) {
        return requestInterface.getProgramInfo(programName);
    }
    public Observable<DataComment> getDataComment(int programId, int page){
        return requestInterface.getDataComment(programId, page);
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
