package com.example.zingdemoapi.datamodel;

public class Artist extends BaseProgramInfo{

    private String avatar;

    private String dob;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
