package com.compubase.sportive.model;

public class GameModel {

    private String name,game,id;

    public GameModel() {
    }

    public GameModel(String name, String game ,String id) {
        this.name = name;
        this.game = game;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }
}
