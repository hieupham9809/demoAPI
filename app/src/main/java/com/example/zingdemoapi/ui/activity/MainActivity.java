package com.example.zingdemoapi.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.zingdemoapi.R;
import com.example.zingdemoapi.adapter.DataAdapter;
import com.example.zingdemoapi.datamodel.Home;
import com.example.zingdemoapi.typeadapter.HomeAdapter;
import com.example.zingdemoapi.request.RequestInterface;
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

public class MainActivity extends AppCompatActivity {

    final public String BASE = "http://dev.api.tv.zing.vn/";
    private Home home;
    private CompositeDisposable mCompositeDisposable;
    private DataAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCompositeDisposable = new CompositeDisposable();
        initRecyclerView();
        loadJSON();
    }
    private void initRecyclerView(){
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);



    }
    private Gson getGSONConvertFactory(){
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Home.class, new HomeAdapter());
        return builder.create();
    }
    private void loadJSON(){
        RequestInterface requestInterface = new Retrofit.Builder()
                .baseUrl(BASE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(getGSONConvertFactory()))
                .build().create(RequestInterface.class);

        Disposable disposable = requestInterface.register()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Home>() {
                    @Override
                    public void accept(Home response) throws Exception {

                        MainActivity.this.handleResponse(response);


                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable error) throws Exception {
                        handleError(error);
                    }
                });
        mCompositeDisposable.add(disposable);
    }
    private void handleResponse(Home home){
//        List<MovieItem> movieItemList = movie.getResults();
//
//
//        movieItemArrayList = new ArrayList<>(movieItemList);

        mAdapter = new DataAdapter(home, this);
        mRecyclerView.setAdapter(mAdapter);
        Log.d("MovieDB", "GET RESPONSE" + home.size());

    }

    private void handleError(Throwable error){
        Toast.makeText(this, "Error" + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        Log.d("MovieDB", "Error" + error.getLocalizedMessage());
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        mCompositeDisposable.clear();
    }
}
