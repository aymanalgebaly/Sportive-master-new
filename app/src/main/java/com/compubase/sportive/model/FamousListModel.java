
package com.compubase.sportive.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FamousListModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("images")
    @Expose
    private String images;
    @SerializedName("famous")
    @Expose
    private String famous;
    @SerializedName("Datee")
    @Expose
    private String datee;
    @SerializedName("des")
    @Expose
    private String des;
    @SerializedName("history")
    @Expose
    private Object history;
    @SerializedName("img_1")
    @Expose
    private Object img1;
    @SerializedName("img_2")
    @Expose
    private Object img2;
    @SerializedName("img_3")
    @Expose
    private Object img3;
    @SerializedName("img_4")
    @Expose
    private Object img4;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getFamous() {
        return famous;
    }

    public void setFamous(String famous) {
        this.famous = famous;
    }

    public String getDatee() {
        return datee;
    }

    public void setDatee(String datee) {
        this.datee = datee;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public Object getHistory() {
        return history;
    }

    public void setHistory(Object history) {
        this.history = history;
    }

    public Object getImg1() {
        return img1;
    }

    public void setImg1(Object img1) {
        this.img1 = img1;
    }

    public Object getImg2() {
        return img2;
    }

    public void setImg2(Object img2) {
        this.img2 = img2;
    }

    public Object getImg3() {
        return img3;
    }

    public void setImg3(Object img3) {
        this.img3 = img3;
    }

    public Object getImg4() {
        return img4;
    }

    public void setImg4(Object img4) {
        this.img4 = img4;
    }

}
