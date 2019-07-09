package com.example.zingdemoapi.ui.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.zingdemoapi.R;
import com.example.zingdemoapi.adapter.DataCommentRecyclerViewAdapter;
import com.example.zingdemoapi.adapter.EndlessRecyclerViewScrollListener;
import com.example.zingdemoapi.datamodel.DataComment;
import com.example.zingdemoapi.request.RestApi;

import io.reactivex.android.schedulers.AndroidSchedulers;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CommentActivity extends BaseActivity {
    private RequestManager requestManager;

    private RecyclerView commentRecyclerView;

    private EndlessRecyclerViewScrollListener scrollListener;
    private DataCommentRecyclerViewAdapter dataCommentRecyclerViewAdapter;
    private LinearLayoutManager linearLayoutManager;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        //actionBar.setDisplayShowHomeEnabled(true);
        requestManager = Glide.with(this);
        Intent intent = getIntent();
        id = intent.getIntExtra("IDPROGRAM", 0);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        actionBar.setTitle(String.format("Bình luận của \"%s\"",intent.getStringExtra("TITLE")));
        initRecyclerView();
        loadJSONfirst(Integer.toString(id), "0");
        //loadComment();
        //loadJSON(Integer.toString(id));
    }

    private void initRecyclerView() {
        commentRecyclerView = findViewById(R.id.comment_recycler_view);

        dataCommentRecyclerViewAdapter = new DataCommentRecyclerViewAdapter(requestManager);

    }

    private void loadComment() {
        // load api

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadNextDataFromApi(page);
            }
        };
        commentRecyclerView.addOnScrollListener(scrollListener);

        Log.d("ZingDemoApi", "load more");
    }

    public void loadNextDataFromApi(int offset) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        loadJSON(Integer.toString(id), Integer.toString(offset));

    }

    private void loadJSONfirst(String id, String page) {

        CommentActivity.this.subscribe(RestApi.getInstance().getDataComment(id, page)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                , new Consumer<DataComment>() {
                    @Override
                    public void accept(DataComment response) throws Exception {

                        dataCommentRecyclerViewAdapter.getCommentList().addAll(response.getCommentList());

                        commentRecyclerView.setAdapter(dataCommentRecyclerViewAdapter);
                        linearLayoutManager = new LinearLayoutManager(getBaseContext());
                        commentRecyclerView.setAdapter(dataCommentRecyclerViewAdapter);

                        commentRecyclerView.setLayoutManager(linearLayoutManager);
                        loadComment();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable error) throws Exception {
                        Log.d("ZingDemoApi", "Error: " + error);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d("ZingDemoApi", "Completed load comment API");
                    }
                });


    }

    private void loadJSON(String id, String page) {

        CommentActivity.this.subscribe(RestApi.getInstance().getDataComment(id, page)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                , new Consumer<DataComment>() {
                    @Override
                    public void accept(DataComment response) throws Exception {

                        CommentActivity.this.handleResponse(response);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable error) throws Exception {
                        Log.d("ZingDemoApi", "Error: " + error);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d("ZingDemoApi", "Completed load comment API");
                    }
                });


    }

    private void handleResponse(DataComment mDataComment) {

        dataCommentRecyclerViewAdapter.getCommentList().addAll(mDataComment.getCommentList());
        dataCommentRecyclerViewAdapter.notifyItemRangeInserted(dataCommentRecyclerViewAdapter.getItemCount(), mDataComment.getCommentList().size());

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
