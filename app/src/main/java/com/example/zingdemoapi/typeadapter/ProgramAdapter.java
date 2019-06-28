package com.example.zingdemoapi.typeadapter;

import com.example.zingdemoapi.datamodel.Program;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class ProgramAdapter extends TypeAdapter<Program> {
    @Override
    public void write(JsonWriter out, Program value) throws IOException {

    }

    @Override
    public Program read(JsonReader in) throws IOException {
        Program program = new Program();
        in.beginObject();
        while (in.hasNext()){
            String name = in.nextName();
            switch (name){

                case "id":
                    program.setId(in.nextInt());
                    break;
                case "objectType":
                    program.setObjectType(in.nextString());
                    break;
                case "title":
                    program.setTitle(in.nextString());
                    break;
                case "thumbnail":
                    program.setThumbnail(in.nextString());
                    break;
                case "require_premium":
                    program.setRequirePremium(in.nextBoolean());
                    break;
                case "login":
                    program.setLogin(in.nextBoolean());
                    break;
                case "iab":
                    program.setIab(in.nextBoolean());
                    break;

                default:
                    in.skipValue();
                    break;

            }
        }
        in.endObject();
        return program;
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
