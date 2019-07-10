package com.example.zingdemoapi.typeadapter;

import com.example.zingdemoapi.datamodel.Comment;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class CommentAdapter extends TypeAdapter<Comment> {

    @Override
    public void write(JsonWriter out, Comment value) throws IOException {

    }

    @Override
    public Comment read(JsonReader in) throws IOException {
        Comment comment = new Comment();
        in.beginObject();
        while (in.hasNext()){
            String name = in.nextName();
            if (in.peek() == JsonToken.NULL){
                in.skipValue();
            } else {
                switch (name) {
                    case "comment_id":
                        comment.setCommentId(in.nextInt());
                        break;
                    case "owner_id":
                        comment.setOwnerId(in.nextInt());
                        break;
                    case "from_id":
                        comment.setFromId(in.nextInt());
                        break;
                    case "to_id":
                        comment.setToId(in.nextInt());
                        break;
                    case "time":
                        comment.setTime(in.nextInt());
                        break;
                    case "content":
                        comment.setContent(in.nextString());
                        break;
                    case "source":
                        comment.setSource(in.nextInt());
                        break;
                    case "user_name":
                        comment.setUserName(in.nextString());
                        break;
                    case "user_fullname":
                        comment.setUserFullname(in.nextString());
                        break;
                    case "user_avatar":
                        comment.setUserAvatar(in.nextString());
                        break;
                    default:
                        in.skipValue();
                        break;
                }
            }
        }
        in.endObject();
        return comment;
    }
}
