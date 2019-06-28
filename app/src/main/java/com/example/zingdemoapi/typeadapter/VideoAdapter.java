package com.example.zingdemoapi.typeadapter;

import com.example.zingdemoapi.datamodel.Video;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class VideoAdapter extends TypeAdapter<Video> {
    @Override
    public void write(JsonWriter out, Video value) throws IOException {

    }

    @Override
    public Video read(JsonReader in) throws IOException {
        Video video = new Video();

        in.beginObject();
        while (in.hasNext()){
            String name = in.nextName();
            switch (name){
                case "id":
                    video.setId(in.nextInt());
                    break;
                case "objectType":
                    video.setObjectType(in.nextString());
                    break;
                case "title":
                    video.setTitle(in.nextString());
                    break;
                case "description":
                    video.setDescription(in.nextString());
                    break;
                case "thumbnail":
                    video.setThumbnail(in.nextString());
                    break;
                case "thumbnail_medium":
                    video.setThumbnailMedium(in.nextString());
                    break;
                case "require_premium":
                    video.setRequirePremium(in.nextBoolean());
                    break;
                case "login":
                    video.setLogin(in.nextBoolean());
                    break;
                case "iab":
                    video.setIab(in.nextBoolean());
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }

        in.endObject();
        return video;
    }
//    public List<Page> getPagesList(JsonReader in) throws IOException{
//        List<Page> pagesList = new ArrayList<>();
//        in.beginArray();
//        while (in.hasNext()){
//            Page page = new Page();
//            in.beginObject();
//            while (in.hasNext()){
//                String name = in.nextName();
//                switch (name){
//                    case "id":
//                        page.setId(in.nextInt());
//                        break;
//                    case "objectType":
//                        page.setObjectType(in.nextString());
//                        break;
//                    case "title":
//                        page.setTitle(in.nextString());
//                        break;
//                    case "description":
//                        page.setDescription(in.nextString());
//                        break;
//                    case "thumbnail":
//                        page.setThumbnail(in.nextString());
//                        break;
//                    case "thumbnail_medium":
//                        page.setThumbnailMedium(in.nextString());
//                        break;
//                    case "require_premium":
//                        page.setRequirePremium(in.nextBoolean());
//                        break;
//                    case "login":
//                        page.setLogin(in.nextBoolean());
//                        break;
//                    case "iab":
//                        page.setIab(in.nextBoolean());
//                        break;
//                    default:
//                        in.skipValue();
//                        break;
//                }
//            }
//            in.endObject();
//            pagesList.add(page);
//        }
//        in.endArray();
//        return pagesList;
//    }

}
