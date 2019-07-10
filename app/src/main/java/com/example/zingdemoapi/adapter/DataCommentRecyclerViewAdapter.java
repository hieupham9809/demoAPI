package com.example.zingdemoapi.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.example.zingdemoapi.R;
import com.example.zingdemoapi.datamodel.Comment;
import com.example.zingdemoapi.datamodel.Constant;
import com.example.zingdemoapi.request.GlideRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class DataCommentRecyclerViewAdapter extends RecyclerView.Adapter<DataCommentRecyclerViewAdapter.EndCommentViewHolder> {

    public List<Comment> getCommentList() {
        return commentList;
    }

    private List<Comment> commentList = new ArrayList<>();

    private RequestManager requestManager;

    public DataCommentRecyclerViewAdapter( RequestManager mRequestManager){
        requestManager = mRequestManager;
    }
    @NonNull
    @Override
    public EndCommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == Constant.END_COMMENT_VIEW_TYPE){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_recycler_end_item, viewGroup, false);
            return new EndCommentViewHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_recycler_item, viewGroup, false);
            return new CommentViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position){
        return commentList.get(position).getCommentId();
    }
    @Override
    public void onBindViewHolder(@NonNull EndCommentViewHolder endCommentViewHolder, int i) {
        int type = getItemViewType(i);
        if (type == Constant.END_COMMENT_VIEW_TYPE){
            endCommentViewHolder.endComment.setText(R.string.end_comment);

        } else {
            CommentViewHolder commentViewHolder = (CommentViewHolder)endCommentViewHolder;
            commentViewHolder.authorName.setText(commentList.get(i).getUserFullname());
            commentViewHolder.dateComment.setText(getDate(commentList.get(i).getTime()));
            commentViewHolder.content.setText(commentList.get(i).getContent());

            GlideRequest.getInstance().loadImage(requestManager, commentList.get(i).getUserAvatar(), commentViewHolder.avatar);
        }

    }

    @Override
    public int getItemCount() {
        if (commentList != null){
            return commentList.size();
        } else {
            return 0;
        }
    }
    private String getDate(long time) {
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();//get your local time zone.
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        sdf.setTimeZone(tz);//set time zone.
        String localTime = sdf.format(new Date(time * 1000));
        Date date = new Date();
        try {
            date = sdf.parse(localTime);//get local date
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.toString();
    }
    public class CommentViewHolder extends EndCommentViewHolder{
        private ImageView avatar;
        private TextView authorName;
        private TextView dateComment;
        private TextView content;

        CommentViewHolder (View view){
            super(view);

            avatar = view.findViewById(R.id.iv_author_avatar);
            authorName = view.findViewById(R.id.tv_author_name);
            dateComment = view.findViewById(R.id.tv_date_comment);
            content = view.findViewById(R.id.tv_content_comment);
        }
    }
    public class EndCommentViewHolder extends RecyclerView.ViewHolder{
        private TextView endComment;

        public EndCommentViewHolder(@NonNull View itemView) {
            super(itemView);

            endComment = itemView.findViewById(R.id.tv_end_item);
        }
    }

}
