package com.example.zingdemoapi.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.zingdemoapi.R;
import com.example.zingdemoapi.adapter.DataAdapter;
import com.example.zingdemoapi.datamodel.Home;
import com.example.zingdemoapi.request.RestApi;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private CompositeDisposable compositeDisposable;

    private DataAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private RequestManager requestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        compositeDisposable = new CompositeDisposable();
        requestManager = Glide.with(this);
        initRecyclerView();
        loadJSON();
    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);


    }

    private void loadJSON() {

        Disposable disposable = RestApi.getInstance().getHome()
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
        compositeDisposable.add(disposable);
    }

    private void handleResponse(Home home) {
        mAdapter = new DataAdapter(home, this, requestManager, compositeDisposable);
        mRecyclerView.setAdapter(mAdapter);
        Log.d("MovieDB", "GET RESPONSE" + home.size());

    }

    private void handleError(Throwable error) {
        Toast.makeText(this, "Error" + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        Log.d("MovieDB", "Error" + error.getLocalizedMessage());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
