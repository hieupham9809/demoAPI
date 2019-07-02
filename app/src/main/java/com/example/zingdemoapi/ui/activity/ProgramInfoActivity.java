package com.example.zingdemoapi.ui.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.zingdemoapi.R;
import com.example.zingdemoapi.adapter.ProgramInfoDataAdapter;
import com.example.zingdemoapi.datamodel.ProgramInfo;
import com.example.zingdemoapi.request.RestApi;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ProgramInfoActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ProgramInfoDataAdapter programInfoDataAdapter;
    private CompositeDisposable mCompositeDisposable;
    private RequestManager requestManager;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_info);

        //actionBar.setDisplayShowHomeEnabled(true);
        mCompositeDisposable = new CompositeDisposable();
        requestManager = Glide.with(this);
        Intent intent = getIntent();
        int id = intent.getIntExtra("IDPROGRAM", 0);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
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
        Disposable disposable = RestApi.getInstance().getProgramInfo(id)
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

    private void handleResponse(ProgramInfo programInfo) {
        programInfoDataAdapter = new ProgramInfoDataAdapter(programInfo, this, requestManager);
        mRecyclerView.setAdapter(programInfoDataAdapter);
    }

    private void handleError(Throwable error) {
        //Toast.makeText(this, "Error" + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        Log.d("PROGRAMINFO", "Error" + error.getLocalizedMessage());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }
//    public boolean onOptionsItemSelected(MenuItem item){
//        Intent returnIntent = new Intent(getApplicationContext(), MainActivity.class);
//        startActivityForResult(returnIntent, 0);
//        return true;
//    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
