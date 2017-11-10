package com.app.devrah.pojo;

/**
 * Created by AQSA SHaaPARR on 6/12/2017.
 */

public class MembersPojo {

    String name;
    String userId;
    String profile_pic;
    String inetial;

    public String getGp_pic() {
        return gp_pic;
    }

    public void setGp_pic(String gp_pic) {
        this.gp_pic = gp_pic;
    }

    String gp_pic;

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

    public String getTick() {
        return tick;
    }

    public void setTick(String tick) {
        this.tick = tick;
    }

    public String tick;

    public String getIsCardMember() {
        return isCardMember;
    }

    public void setIsCardMember(String isCardMember) {
        this.isCardMember = isCardMember;
    }

    public String isCardMember;
}
