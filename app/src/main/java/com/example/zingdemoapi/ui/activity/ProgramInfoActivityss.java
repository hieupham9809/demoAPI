package com.example.zingdemoapi.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.zingdemoapi.R;
import com.example.zingdemoapi.adapter.ProgramInfoDataAdapter;
import com.example.zingdemoapi.datamodel.ProgramInfo;
import com.example.zingdemoapi.request.RestApi;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ProgramInfoActivityss extends BaseActivity {
    private RecyclerView mRecyclerView;
    private RequestManager requestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityss_program_info);

        //actionBar.setDisplayShowHomeEnabled(true);
        requestManager = Glide.with(this);
        Intent intent = getIntent();
        int id = intent.getIntExtra("IDPROGRAM", 0);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        actionBar.setTitle(intent.getStringExtra("TITLE"));
        initRecyclerView();

        loadJSON(Integer.toString(id));
    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.program_info_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void loadJSON(String id) {

        subscribe(RestApi.getInstance().getProgramInfo(id)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                , new Consumer<ProgramInfo>() {
                    @Override
                    public void accept(ProgramInfo response) throws Exception {
                        ProgramInfoActivityss.this.handleResponse(response);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable error) throws Exception {
                        ProgramInfoActivityss.this.handleError(error);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d("ZingDemoApi", "Completed ProgramInfoActivity API");
                    }
                });

    }

    private void handleResponse(ProgramInfo programInfo) {
        ProgramInfoDataAdapter programInfoDataAdapter = new ProgramInfoDataAdapter(programInfo, this, requestManager);
        mRecyclerView.setAdapter(programInfoDataAdapter);
    }

    private void handleError(Throwable error) {
        //Toast.makeText(this, "Error" + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        Log.d("PROGRAMINFO", "Error" + error.getLocalizedMessage());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
