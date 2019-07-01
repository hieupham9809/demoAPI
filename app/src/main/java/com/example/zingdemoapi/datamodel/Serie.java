package com.example.zingdemoapi.datamodel;

public class Serie extends BaseProgramInfo{

    private String thumbnail;

    private Integer total;

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
