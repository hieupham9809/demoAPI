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
import com.example.zingdemoapi.datamodel.Comment;
import com.example.zingdemoapi.datamodel.Constant;
import com.example.zingdemoapi.datamodel.DataComment;
import com.example.zingdemoapi.request.RestApi;

import io.reactivex.android.schedulers.AndroidSchedulers;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CommentActivity extends BaseActivity {
    private RequestManager requestManager;

    private RecyclerView commentRecyclerView;

    private DataCommentRecyclerViewAdapter dataCommentRecyclerViewAdapter;
    private LinearLayoutManager linearLayoutManager;
    private int id;
    private int page = Constant.INITIAL_PAGE;
    private boolean isPageLoaded = false;
    private boolean isEnd = false;
    private Comment endComment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        endComment = new Comment();
        requestManager = Glide.with(this);
        Intent intent = getIntent();
        if (intent.hasExtra(Constant.PROGRAMID)) {
            id = intent.getIntExtra(Constant.PROGRAMID, 0);
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(String.format(getString(R.string.title_comment_activity) + "\"%s\"", intent.getStringExtra(Constant.TITLE)));
        }
        initRecyclerView();
        addLoadmoreListener();


    }
    @Override
    protected void onResume(){
        super.onResume();
        loadPageComment(id, page);
    }
    private void initRecyclerView() {
        commentRecyclerView = findViewById(R.id.comment_recycler_view);

        dataCommentRecyclerViewAdapter = new DataCommentRecyclerViewAdapter(requestManager);

    }

    private void addLoadmoreListener() {
        // load api
        linearLayoutManager = new LinearLayoutManager(getBaseContext());

        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore() {
                page++;
                isPageLoaded = false;
                loadPageComment(id, page);
            }

        };
        commentRecyclerView.addOnScrollListener(scrollListener);

    }


    private void loadPageComment(int id, final int page) {
        if (!isPageLoaded && !isEnd) {
            isPageLoaded = true;

            CommentActivity.this.subscribe(RestApi.getInstance().getDataComment(id, page)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                    , new Consumer<DataComment>() {
                        @Override
                        public void accept(DataComment response) throws Exception {

                            int currentSize = dataCommentRecyclerViewAdapter.getItemCount();
                            if (response.getCommentList().size() == 0){
                                isEnd = true;
                                endComment.setCommentId(Constant.END_COMMENT_VIEW_TYPE);
                                dataCommentRecyclerViewAdapter.getCommentList().add(endComment);
                                dataCommentRecyclerViewAdapter.notifyItemRangeInserted(currentSize, 1);
                            } else {
                                dataCommentRecyclerViewAdapter.getCommentList().addAll(response.getCommentList());
                                //Log.d("ZingDemoApi", "load api, comment list size: " + dataCommentRecyclerViewAdapter.getItemCount());

                                if (page == Constant.INITIAL_PAGE) {
                                    commentRecyclerView.setAdapter(dataCommentRecyclerViewAdapter);

                                    commentRecyclerView.setLayoutManager(linearLayoutManager);

                                } else {
                                    dataCommentRecyclerViewAdapter.notifyItemRangeInserted(currentSize, response.getCommentList().size());

                                }
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable error) throws Exception {
                            Log.d(getString(R.string.app_tag), getString(R.string.error_message) + error);
                        }
                    }, new Action() {
                        @Override
                        public void run() throws Exception {
                            Log.d(getString(R.string.app_tag), getString(R.string.comment_complete_message));
                        }
                    });
        }
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
