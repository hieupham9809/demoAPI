package com.example.zingdemoapi.datamodel;

import java.util.ArrayList;
import java.util.List;

public class DataComment {
    int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    List<Comment> commentList = new ArrayList<>();

    public DataComment(){

    }

}
