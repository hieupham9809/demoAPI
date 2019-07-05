package com.example.zingdemoapi.ui.activity;

import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.zingdemoapi.R;
import com.example.zingdemoapi.adapter.DataCommentRecyclerViewAdapter;
import com.example.zingdemoapi.adapter.EndlessParentScrollListener;
import com.example.zingdemoapi.adapter.EndlessRecyclerViewScrollListener;
import com.example.zingdemoapi.adapter.ProgramInfoDataAdapter;
import com.example.zingdemoapi.adapter.viewholder.ProgramInfoViewHolder;
import com.example.zingdemoapi.datamodel.DataComment;
import com.example.zingdemoapi.datamodel.ProgramInfo;
import com.example.zingdemoapi.request.RestApi;

import io.reactivex.android.schedulers.AndroidSchedulers;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ProgramInfoActivitys extends BaseActivity {
    private RecyclerView mRecyclerView;
    private RequestManager requestManager;

    private RecyclerView commentRecyclerView;
    private NestedScrollView nestedScrollView;

    private EndlessRecyclerViewScrollListener scrollListener;
    private DataCommentRecyclerViewAdapter dataCommentRecyclerViewAdapter;
    private LinearLayoutManager linearLayoutManager;
    private int id;

    private Button btnLoad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitys_program_info);

        //actionBar.setDisplayShowHomeEnabled(true);
        requestManager = Glide.with(this);
        Intent intent = getIntent();
        id = intent.getIntExtra("IDPROGRAM", 0);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        actionBar.setTitle(intent.getStringExtra("TITLE"));
        initRecyclerView();
        loadJSONfirst(Integer.toString(id), "0");
        //loadComment();
        //loadJSON(Integer.toString(id));
    }

    private void initRecyclerView() {
        btnLoad = findViewById(R.id.btn_load_comments);
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadComment();
            }
        });
        commentRecyclerView = findViewById(R.id.comment_recycler_views);

        //nestedScrollView = (NestedScrollView) findViewById(R.id.scrollViews);
        dataCommentRecyclerViewAdapter = new DataCommentRecyclerViewAdapter(requestManager);

    }

    private void loadComment(){
        // load api
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        //commentRecyclerView.setLayoutManager(linearLayoutManager);
//        commentRecyclerView.setAdapter(dataCommentRecyclerViewAdapter);
//
//        commentRecyclerView.setLayoutManager(linearLayoutManager);

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadNextDataFromApi(page);
            }
        };
        commentRecyclerView.addOnScrollListener(scrollListener);
        //commentRecyclerView.setNestedScrollingEnabled(false);

//        commentRecyclerView.setAdapter(dataCommentRecyclerViewAdapter);
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        commentRecyclerView.setLayoutManager(linearLayoutManager);
//        nestedScrollView.setOnScrollChangeListener(new EndlessParentScrollListener(linearLayoutManager) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount) {
//                loadNextDataFromApi(page);
//            }
//        });
        Log.d("ZingDemoApi", "load more");
    }
    public void loadNextDataFromApi(int offset) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        loadJSON(Integer.toString(id), Integer.toString(offset));
        //  --> Append the new data objects to the existing set of items inside the array of items

        //Log.d("ZingDemoApi", "adapter list size: "+ dataCommentRecyclerViewAdapter.getCommentList().size());
        //Log.d("ZingDemoApi", "before dataComment list size: "+ dataComment.getCommentList().size());
//        ArrayList<Comment> commentList = new ArrayList<>(dataComment.getCommentList());
//        Log.d("ZingDemoApi", "before dataComment list size: "+ dataComment.getCommentList().size());
//
//        dataCommentRecyclerViewAdapter.getCommentList().addAll(commentList);
//
//        Log.d("ZingDemoApi", "after dataComment list size: "+ commentList.size());

        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
    }
    private void loadJSONfirst(String id, String page) {

        ProgramInfoActivitys.this.subscribe(RestApi.getInstance().getDataComment(id, page)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                , new Consumer<DataComment>() {
                    @Override
                    public void accept(DataComment response) throws Exception {


                        dataCommentRecyclerViewAdapter.getCommentList().addAll(response.getCommentList());



//        dataCommentRecyclerViewAdapter = new DataCommentRecyclerViewAdapter(requestManager);
                    commentRecyclerView.setAdapter(dataCommentRecyclerViewAdapter);
                        linearLayoutManager = new LinearLayoutManager(getBaseContext());
                        //commentRecyclerView.setLayoutManager(linearLayoutManager);
                        commentRecyclerView.setAdapter(dataCommentRecyclerViewAdapter);

                        commentRecyclerView.setLayoutManager(linearLayoutManager);
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

        ProgramInfoActivitys.this.subscribe(RestApi.getInstance().getDataComment(id, page)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                , new Consumer<DataComment>() {
                    @Override
                    public void accept(DataComment response) throws Exception {

                        ProgramInfoActivitys.this.handleResponse(response);
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
    private void handleResponse(DataComment mDataComment){
        //Log.d("ZingDemoApi", "response list size: "+mDataComment.getCommentList().size());
        //dataComment = mDataComment;
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
