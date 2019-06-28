package com.example.zingdemoapi.datamodel;

import java.util.List;

public abstract class BaseClass {
    protected Integer id;

    protected String objectType;

    protected String thumbnail;

    protected Boolean requirePremium;

    protected Boolean login;

    protected Boolean iab;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Boolean getRequirePremium() {
        return requirePremium;
    }

    public void setRequirePremium(Boolean requirePremium) {
        this.requirePremium = requirePremium;
    }

    public Boolean getLogin() {
        return login;
    }

    public void setLogin(Boolean login) {
        this.login = login;
    }

    public Boolean getIab() {
        return iab;
    }

    public void setIab(Boolean iab) {
        this.iab = iab;
    }
}
