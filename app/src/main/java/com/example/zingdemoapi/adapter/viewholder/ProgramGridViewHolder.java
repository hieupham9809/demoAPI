package com.example.zingdemoapi.adapter.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.example.zingdemoapi.R;
//import com.example.zingdemoapi.adapter.ProgramGridViewAdapter;
import com.example.zingdemoapi.adapter.ProgramRecyclerViewAdapter;
import com.example.zingdemoapi.datamodel.Program;
import com.example.zingdemoapi.ui.view.ExpandableHeightGridView;

import java.util.List;

public class ProgramGridViewHolder extends BaseHomeViewHolder<Program> {
//    private ExpandableHeightGridView gridView;
//    private GridView gridView;
    private RecyclerView recyclerView;
    private ProgramRecyclerViewAdapter programRecyclerViewAdapter;
    private Context context;
//    private ProgramGridViewAdapter programGridViewAdapter;
    private TextView tvTypeTitle;
    public ProgramGridViewHolder(@NonNull View itemView, Context mContext, RequestManager mRequestManager) {
        super(itemView, mRequestManager);
        context = mContext;
        //gridView = itemView.findViewById(R.id.grid_view);
        recyclerView = itemView.findViewById(R.id.recycler_view_program);
        tvTypeTitle = itemView.findViewById(R.id.tv_type_title);
    }

    public void setData(List<Program> list){
        if (programRecyclerViewAdapter == null){
            programRecyclerViewAdapter = new ProgramRecyclerViewAdapter(context, requestManager);

        }
        //programGridViewAdapter.setmResources(list, title);
        recyclerView.setAdapter(programRecyclerViewAdapter);
        //gridView.setExpanded(true);

    }
    RecyclerView.OnItemTouchListener mScrollTouchListener = new RecyclerView.OnItemTouchListener() {
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            int action = e.getAction();
            if (action == MotionEvent.ACTION_MOVE) {
                rv.getParent().requestDisallowInterceptTouchEvent(true);
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    };

    @Override
    public void setData(List<Program> list, String title){
        tvTypeTitle.setAllCaps(true);
        tvTypeTitle.setText(title);
        if (programRecyclerViewAdapter == null){
            programRecyclerViewAdapter = new ProgramRecyclerViewAdapter(context, requestManager);

        }
        programRecyclerViewAdapter.setProgramAndTitle(list, title);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(programRecyclerViewAdapter);
        // ** To allow nested scroll
        //recyclerView.addOnItemTouchListener(mScrollTouchListener);


    }
}
