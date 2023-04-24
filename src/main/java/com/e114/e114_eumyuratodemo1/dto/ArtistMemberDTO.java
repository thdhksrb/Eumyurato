package com.e114.e114_eumyuratodemo1.dto;

public class ArtistMemberDTO extends MemberDTO{
    private String nid;
    private String sex;
    private String brith;
    private String genre;
    private String registCon;
    private String point;

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBrith() {
        return brith;
    }

    public void setBrith(String brith) {
        this.brith = brith;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getRegistCon() {
        return registCon;
    }

    public void setRegistCon(String registCon) {
        this.registCon = registCon;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }
}
