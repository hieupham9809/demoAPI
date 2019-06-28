package com.example.zingdemoapi.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.zingdemoapi.R;
import com.example.zingdemoapi.adapter.ProgramInfoDataAdapter;
import com.example.zingdemoapi.datamodel.Home;
import com.example.zingdemoapi.datamodel.ProgramInfo;
import com.example.zingdemoapi.request.RequestInterface;
import com.example.zingdemoapi.typeadapter.ProgramAdapter;
import com.example.zingdemoapi.typeadapter.ProgramInfoAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProgramInfoActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ProgramInfoDataAdapter programInfoDataAdapter;
    private CompositeDisposable mCompositeDisposable;
    final public String BASE = "http://dev.api.tv.zing.vn/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_info);
        mCompositeDisposable = new CompositeDisposable();
        Intent intent = getIntent();
        int id = intent.getIntExtra("IDPROGRAM", 0);
        initRecyclerView();

        loadJSON(id);
    }
    private void initRecyclerView(){
        mRecyclerView = findViewById(R.id.program_info_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
    }
    private Gson getGSONConvertFactory(){
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ProgramInfo.class, new ProgramInfoAdapter());
        return builder.create();
    }
    private void loadJSON(int id){
        RequestInterface requestInterface = new Retrofit.Builder()
                .baseUrl(BASE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(getGSONConvertFactory()))
                .build().create(RequestInterface.class);

        Disposable disposable = requestInterface.getProgramInfo(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<ProgramInfo>() {
                    @Override
                    public void accept(ProgramInfo response) throws Exception {

                        ProgramInfoActivity.this.handleResponse(response);


                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable error) throws Exception {
                        handleError(error);
                    }
                });
        mCompositeDisposable.add(disposable);
    }
    private void handleResponse(ProgramInfo programInfo){
        programInfoDataAdapter = new ProgramInfoDataAdapter(programInfo, this );
        mRecyclerView.setAdapter(programInfoDataAdapter);
    }

    private void handleError(Throwable error){
        //Toast.makeText(this, "Error" + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        Log.d("PROGRAMINFO", "Error" + error.getLocalizedMessage());
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        mCompositeDisposable.clear();
    }
}
