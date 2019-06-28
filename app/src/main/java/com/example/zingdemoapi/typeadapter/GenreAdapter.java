package com.example.zingdemoapi.typeadapter;

import com.example.zingdemoapi.datamodel.Genre;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class GenreAdapter extends TypeAdapter<Genre> {
    @Override
    public void write(JsonWriter out, Genre value) throws IOException {

    }

    @Override
    public Genre read(JsonReader in) throws IOException {
        Genre genre = new Genre();
        in.beginObject();
        while (in.hasNext()){
            String name = in.nextName();
            if (name.equals("id")){
                genre.setId(in.nextInt());
            } else {
                genre.setName(in.nextString());
            }
        }
        in.endObject();
        return genre;
    }
}
