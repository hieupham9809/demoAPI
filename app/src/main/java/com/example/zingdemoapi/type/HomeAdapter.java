package com.example.zingdemoapi.type;

import android.util.Log;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends TypeAdapter<Home> {

    @Override
    public void write(JsonWriter out, Home value) throws IOException {

    }

    @Override
    public Home read(JsonReader in) throws IOException {
        Home home = new Home();
        in.beginObject();

        while (in.hasNext()){

            if (in.nextName().equals("data")){

                in.beginObject();
                while (in.hasNext()){
                    if (in.nextName().equals("home")){
                        in.beginArray();
                        while (in.hasNext()){
                            List<BaseClass> pageList = new ArrayList<>();
                            BoxObject boxObject = new BoxObject();
                            boolean flag = true;
                            in.beginObject();
                            while (in.hasNext()){
                                String name = in.nextName();
                                switch(name){
                                    case "type":
                                        int type = in.nextInt();
                                        if (type != Type.BANNER && type != Type.PROGRAM && type != Type.VIDEO){
                                            flag = false;
                                            while(in.hasNext()){
                                                in.skipValue();
                                            }
                                        }else{
                                            boxObject.setType(type);
                                        }
                                        break;
                                    case "title":
                                        boxObject.setTitle(in.nextString());
                                        //Log.d("MovieDB", "HERE");

                                        break;
                                    case "object_type":
                                        boxObject.setObjectType(in.nextString());
                                        break;
                                    case "object_id":
                                        boxObject.setObjectId(in.nextInt());
                                        break;
                                    case "total_page":
                                        boxObject.setTotalPage(in.nextInt());
                                        break;
                                    case "box_id":
                                        boxObject.setBoxId(in.nextInt());
                                        break;
                                    case "placeholder":
                                        boxObject.setPlaceholder(in.nextInt());
                                        break;
                                    case "hasmore":
                                        boxObject.setHasmore(in.nextBoolean());
                                        break;
                                    case "pages":
                                        in.beginArray();

                                        if (boxObject.getType() == Type.BANNER){
                                            BannerAdapter bannerAdapter = new BannerAdapter();

                                            while (in.hasNext()){
                                                pageList.add(bannerAdapter.read(in));

                                            }


                                        }else if (boxObject.getType() == Type.PROGRAM){
                                            ProgramAdapter programAdapter = new ProgramAdapter();
                                            while (in.hasNext()){
                                                pageList.add(programAdapter.read(in));
                                            }
                                        } else{
                                            VideoAdapter videoAdapter = new VideoAdapter();
                                            while(in.hasNext()){
                                                pageList.add(videoAdapter.read(in));
                                            }
                                        }


                                        in.endArray();
                                        boxObject.setPages(pageList);

                                        break;

                                    default:
                                        in.skipValue();
                                        break;

                                }
                            }



                            //while (in.hasNext()){
//                            if (in.nextName().equals("type")){
//                                //Log.d("MovieDB", "HERE");
//
//                                int type = in.nextInt();
//                                //Log.d("MovieDB", "type" + type );
//
//                                switch (type){
//                                    case Type.BANNER:
//                                        BannerAdapter bannerAdapter = new BannerAdapter();
//                                        home.add(bannerAdapter.read(in));
//                                        break;
//                                    case Type.VIDEO:
//                                        VideoAdapter videoAdapter = new VideoAdapter();
//                                        home.add(videoAdapter.read(in));
//                                        break;
//                                    case Type.PROGRAM:
//                                        ProgramAdapter programAdapter = new ProgramAdapter();
//                                        home.add(programAdapter.read(in));
//                                        break;
//                                    default:
//                                        while(in.hasNext()){
//                                            in.skipValue();
//                                        }
//                                        //in.skipValue();
//                                        break;
//
//                                }
//                                //break;
//                            }
                            //}
                            in.endObject();
                            if (flag){
                                home.add(boxObject);
                            }
                        }
                        in.endArray();
                    }else{
                        in.skipValue();
                    }
                }

                in.endObject();
            }
            else {
                in.skipValue();
            }
        }

        in.endObject();
        return home;
    }


}
