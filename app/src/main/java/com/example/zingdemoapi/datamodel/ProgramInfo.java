package com.example.zingdemoapi.datamodel;

import java.util.List;

public class ProgramInfo {

    private Integer id;

    private String name;

    private String thumbnail;

    private List<Genre> genres = null;

    private String description;

    private String url;

    private Boolean hasSubTitle;

    private String format;

    private Integer listen;

    private Integer comment;

    private Double rating;

    private Boolean requirePremium;

    private Integer subscription;

    private Boolean isSubs;

    private Boolean isFullEpisode;

    private String releaseDate;

    private String showTimes;

    private String pg;

    private List<Artist> artists = null;

    private String duration;

    private String cover;

    private String banner;

    private Integer createdDate;

    private Integer modifiedDate;

    private List<Serie> series = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getHasSubTitle() {
        return hasSubTitle;
    }

    public void setHasSubTitle(Boolean hasSubTitle) {
        this.hasSubTitle = hasSubTitle;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Integer getListen() {
        return listen;
    }

    public void setListen(Integer listen) {
        this.listen = listen;
    }

    public Integer getComment() {
        return comment;
    }

    public void setComment(Integer comment) {
        this.comment = comment;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Boolean getRequirePremium() {
        return requirePremium;
    }

    public void setRequirePremium(Boolean requirePremium) {
        this.requirePremium = requirePremium;
    }

    public Integer getSubscription() {
        return subscription;
    }

    public void setSubscription(Integer subscription) {
        this.subscription = subscription;
    }

    public Boolean getIsSubs() {
        return isSubs;
    }

    public void setIsSubs(Boolean isSubs) {
        this.isSubs = isSubs;
    }

    public Boolean getIsFullEpisode() {
        return isFullEpisode;
    }

    public void setIsFullEpisode(Boolean isFullEpisode) {
        this.isFullEpisode = isFullEpisode;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getShowTimes() {
        return showTimes;
    }

    public void setShowTimes(String showTimes) {
        this.showTimes = showTimes;
    }

    public String getPg() {
        return pg;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public Boolean getSubs() {
        return isSubs;
    }

    public void setSubs(Boolean subs) {
        isSubs = subs;
    }

    public Boolean getFullEpisode() {
        return isFullEpisode;
    }

    public void setFullEpisode(Boolean fullEpisode) {
        isFullEpisode = fullEpisode;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public List<Serie> getSeries() {
        return series;
    }

    public void setPg(String pg) {
        this.pg = pg;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public void setSeries(List<Serie> series) {
        this.series = series;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public Integer getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Integer createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Integer modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

}
