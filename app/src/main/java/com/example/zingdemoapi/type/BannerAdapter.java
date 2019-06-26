package com.example.zingdemoapi.type;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BannerAdapter extends TypeAdapter<Banner> {

    @Override
    public void write(JsonWriter out, Banner value) throws IOException {

    }

    @Override
    public Banner read(JsonReader in) throws IOException {
        Banner banner = new Banner();

        in.beginObject();
        while (in.hasNext()){
            String name = in.nextName();
            switch (name){

                case "id":
                    banner.setId(in.nextInt());
                    break;
                case "objectType":
                    banner.setObjectType(in.nextString());
                    break;
                case "thumbnail":
                    banner.setThumbnail(in.nextString());
                    break;
                case "require_premium":
                    banner.setRequirePremium(in.nextBoolean());
                    break;
                case "login":
                    banner.setLogin(in.nextBoolean());
                    break;
                case "iab":
                    banner.setIab(in.nextBoolean());
                    break;

                default:
                    in.skipValue();
                    break;

            }
        }

        in.endObject();
        return banner;
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
//                    case "thumbnail":
//                        page.setThumbnail(in.nextString());
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
