package com.app.devrah.pojo;

/**
 * Created by AQSA SHaaPARR on 6/12/2017.
 */

public class MembersPojo {

    String name, userId , profile_pic ,inetial;

    public String getInetial() {
        return inetial;
    }

    public void setInetial(String inetial) {
        this.inetial = inetial;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public MembersPojo() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
