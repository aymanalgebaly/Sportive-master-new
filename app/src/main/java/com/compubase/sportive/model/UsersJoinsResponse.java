package com.compubase.sportive.model;//
//  UsersJoinsResponse.java
//  Model Generated using http://www.jsoncafe.com/ 
//  Created on August 23, 2019

import org.json.*;
import java.util.*;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class UsersJoinsResponse {

	@SerializedName("Datee")
	private String datee;
	@SerializedName("Datee1")
	private String datee1;
	@SerializedName("des")
	private String des;
	@SerializedName("email")
	private String email;
	@SerializedName("famous")
	private String famous;
	@SerializedName("game_name")
	private String gameName;
	@SerializedName("id")
	private int id;
	@SerializedName("id_center")
	private int idCenter;
	@SerializedName("id_user")
	private int idUser;
	@SerializedName("id1")
	private int id1;
	@SerializedName("images")
	private String images;
	@SerializedName("lang")
	private String lang;
	@SerializedName("lat")
	private String lat;
	@SerializedName("name")
	private String name;
	@SerializedName("password")
	private String password;
	@SerializedName("phone")
	private String phone;
	@SerializedName("type")
	private String type;
	@SerializedName("fb")
	@Expose
	private String fb;

	@SerializedName("services")
	@Expose
	private String services;

	@SerializedName("website")
	@Expose
	private String website;

	public String getFb() {
		return fb;
	}

	public void setFb(String fb) {
		this.fb = fb;
	}

	public String getServices() {
		return services;
	}

	public void setServices(String services) {
		this.services = services;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public void setDatee(String datee) {
		this.datee = datee;
	}

	public String getDatee() {
		return this.datee;
	}

	public void setDatee1(String datee1) {
		this.datee1 = datee1;
	}

	public String getDatee1() {
		return this.datee1;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getDes() {
		return this.des;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}

	public void setFamous(String famous) {
		this.famous = famous;
	}

	public String getFamous() {
		return this.famous;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getGameName() {
		return this.gameName;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public void setIdCenter(int idCenter) {
		this.idCenter = idCenter;
	}

	public int getIdCenter() {
		return this.idCenter;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public int getIdUser() {
		return this.idUser;
	}

	public void setId1(int id1) {
		this.id1 = id1;
	}

	public int getId1() {
		return this.id1;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public String getImages() {
		return this.images;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getLang() {
		return this.lang;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLat() {
		return this.lat;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}
}

	/**
	 * Instantiate the instance using the passed jsonObject to set the properties values
	 */

	/**
	 * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
	 */
