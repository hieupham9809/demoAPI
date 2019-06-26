package com.example.zingdemoapi.type;

import java.util.ArrayList;
import java.util.List;

public class BoxObject {
    protected List<BaseClass> pageList = new ArrayList<>();

    protected Integer type;

    protected String title;

    protected String objectType;

    protected Integer objectId;

    protected Integer totalPage;

    protected Integer boxId;

    protected Integer placeholder;

    protected Boolean hasmore;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getBoxId() {
        return boxId;
    }

    public void setBoxId(Integer boxId) {
        this.boxId = boxId;
    }

    public Integer getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(Integer placeholder) {
        this.placeholder = placeholder;
    }

    public List<BaseClass> getPageList() {
        return pageList;
    }

    public void setPages(List<BaseClass> baseClasses) {
        this.pageList = baseClasses;
    }

    public Boolean getHasmore() {
        return hasmore;
    }

    public void setHasmore(Boolean hasmore) {
        this.hasmore = hasmore;
    }

}
