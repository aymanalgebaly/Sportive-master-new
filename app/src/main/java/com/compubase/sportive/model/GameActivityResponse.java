package com.compubase.sportive.model;//
//  GameActivityResponse.java
//  Model Generated using http://www.jsoncafe.com/ 
//  Created on August 23, 2019

import org.json.*;
import java.util.*;
import com.google.gson.annotations.SerializedName;


public class GameActivityResponse{

	@SerializedName("coach")
	private String coach;
	@SerializedName("Datee")
	private String datee;
	@SerializedName("id")
	private int id;
	@SerializedName("id_center")
	private int idCenter;
	@SerializedName("name_game")
	private String nameGame;

	public void setCoach(String coach){
		this.coach = coach;
	}
	public String getCoach(){
		return this.coach;
	}
	public void setDatee(String datee){
		this.datee = datee;
	}
	public String getDatee(){
		return this.datee;
	}
	public void setId(int id){
		this.id = id;
	}
	public int getId(){
		return this.id;
	}
	public void setIdCenter(int idCenter){
		this.idCenter = idCenter;
	}
	public int getIdCenter(){
		return this.idCenter;
	}
	public void setNameGame(String nameGame){
		this.nameGame = nameGame;
	}
	public String getNameGame(){
		return this.nameGame;
	}

	/**
	 * Instantiate the instance using the passed jsonObject to set the properties values
	 */

	/**
	 * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
	 */
}