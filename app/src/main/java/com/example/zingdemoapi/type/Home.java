package com.example.zingdemoapi.type;

import java.util.ArrayList;
import java.util.List;

public class Home {

//    private List<Banner> bannerArrayList = new ArrayList<>();
//    private List<Video> videoArrayList = new ArrayList<>();
//    private List<Program> programArrayList = new ArrayList<>();
    private List<BoxObject> boxObjectList = new ArrayList<>();

    public int size(){
        return boxObjectList.size();
    }

    public void add(BoxObject boxObject){
        boxObjectList.add(boxObject);
    }

    public BoxObject get (int i){
        return boxObjectList.get(i);
    }
//    public int size(){
//        return bannerArrayList.size() + videoArrayList.size() + programArrayList.size();
//    }
//    public void add(Banner banner){
//        bannerArrayList.add(banner);
//    }
//    public void add(Video video){
//        videoArrayList.add(video);
//    }
//    public void add(Program program){
//        programArrayList.add(program);
//    }
//    public int getType(int i) throws IndexOutOfBoundsException{
//        int index = i;
//        if (index < bannerArrayList.size()){
//            return 1;
//        }
//        index -= bannerArrayList.size();
//        if (index < videoArrayList.size()){
//            return 2;
//        }
//        index -= videoArrayList.size();
//        if (index < programArrayList.size()){
//            return 3;
//        }
//        return 0;
//    }
//    public Object get(int i) throws IndexOutOfBoundsException{
//        int index = i;
//        if (index < bannerArrayList.size()){
//            return bannerArrayList.get(index);
//        }
//        index -= bannerArrayList.size();
//        if (index < videoArrayList.size()){
//            return videoArrayList.get(index);
//        }
//        index -= videoArrayList.size();
//        if (index < programArrayList.size()){
//            return programArrayList.get(index);
//        }
//        return new Exception("Index out of bound");
//    }
}
