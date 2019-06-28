package com.example.zingdemoapi.typeadapter;

import com.example.zingdemoapi.datamodel.Artist;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class ArtistAdapter extends TypeAdapter<Artist> {
    @Override
    public void write(JsonWriter out, Artist value) throws IOException {

    }

    @Override
    public Artist read(JsonReader in) throws IOException {
        Artist artist = new Artist();
        in.beginObject();
        while (in.hasNext()){
            String name = in.nextName();
            switch (name){
                case "id":
                    artist.setId(in.nextInt());
                    break;
                case "name":
                    artist.setName(in.nextString());
                    break;
                case "avatar":
                    artist.setAvatar(in.nextString());
                    break;
                case "dob":
                    artist.setDob(in.nextString());
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();
        return artist;
    }
}
