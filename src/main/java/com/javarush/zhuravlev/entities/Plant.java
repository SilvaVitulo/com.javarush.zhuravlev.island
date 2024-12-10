package com.javarush.zhuravlev.entities;

public class Plant {
    public String emoji;
    public double weight;
    public int maxPerCell;
    public int maxSpeed;
    public String simpleNameLowerCase;
    public int actualSatiety;

    public Plant() {
        this.weight = 1;
        this.maxPerCell = 200;
        this.maxSpeed = 0;
        this.simpleNameLowerCase = "plant";
        this.emoji = "\uD83C\uDF31";
        this.actualSatiety = 100;
    }
}
