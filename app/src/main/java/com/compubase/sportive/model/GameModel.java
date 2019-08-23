package com.compubase.sportive.model;

public class GameModel {

    private String name,game;

    public GameModel() {
    }

    public GameModel(String name, String game) {
        this.name = name;
        this.game = game;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }
}
