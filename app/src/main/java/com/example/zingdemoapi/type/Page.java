package com.example.zingdemoapi.type;

public class Page {
//    @SerializedName("id")
//    @Expose
    private Integer id;
//    @SerializedName("objectType")
//    @Expose
    private String objectType;
//    @SerializedName("title")
//    @Expose
    private String title;
//    @SerializedName("description")
//    @Expose

    private String thumbnail;
//    @SerializedName("thumbnail_medium")
//    @Expose
    private String thumbnailMedium;
//    @SerializedName("require_premium")
//    @Expose
    private Boolean requirePremium;
//    @SerializedName("login")
//    @Expose
    private Boolean login;
//    @SerializedName("iab")
//    @Expose
    private Boolean iab;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getThumbnailMedium() {
        return thumbnailMedium;
    }

    public void setThumbnailMedium(String thumbnailMedium) {
        this.thumbnailMedium = thumbnailMedium;
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
