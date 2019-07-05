package com.example.zingdemoapi.typeadapter;

import android.util.Log;

import com.example.zingdemoapi.datamodel.Comment;
import com.example.zingdemoapi.datamodel.DataComment;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataCommentAdapter extends TypeAdapter<DataComment> {
    @Override
    public void write(JsonWriter out, DataComment value) throws IOException {

    }

    @Override
    public DataComment read(JsonReader in) throws IOException {

        DataComment dataComment = new DataComment();
        in.beginObject();
        while (in.hasNext()){
            if (in.nextName().equals("data")){
                in.beginObject();
                while (in.hasNext()){
                    String name = in.nextName();
                    switch (name){
                        case "total":
                            dataComment.setTotal(in.nextInt());
                            break;
                        case "comments":
                            List<Comment> commentList = new ArrayList<>();
                            CommentAdapter commentAdapter = new CommentAdapter();
                            in.beginArray();
                            while (in.hasNext()){
                                commentList.add(commentAdapter.read(in));
                            }
                            in.endArray();
                            dataComment.setCommentList(commentList);
                            break;
                        default:
                            in.skipValue();
                            break;
                    }

                }
                in.endObject();
            } else{
                in.skipValue();
            }

        }

        in.endObject();

        return dataComment;
    }
}
