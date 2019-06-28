package com.example.zingdemoapi.typeadapter;

import com.example.zingdemoapi.datamodel.Serie;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class SerieAdapter extends TypeAdapter<Serie> {
    @Override
    public void write(JsonWriter out, Serie value) throws IOException {

    }

    @Override
    public Serie read(JsonReader in) throws IOException {
        Serie serie = new Serie();
        in.beginObject();
        while (in.hasNext()){
            String name = in.nextName();
            switch (name){
                case "id":
                    serie.setId(in.nextInt());
                    break;
                case "name":
                    serie.setName(in.nextString());
                    break;
                case "thumbnail":
                    serie.setThumbnail(in.nextString());
                    break;
                case "total":
                    serie.setTotal(in.nextInt());
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();
        return serie;
    }
}
