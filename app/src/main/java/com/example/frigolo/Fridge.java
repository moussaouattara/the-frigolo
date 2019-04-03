package com.example.frigolo;

public class Fridge {

    private String name;
    private String type;

    public Fridge(String name,String type) {
        this.name = name;
        this.type = type; }

    public Fridge(){

    }

    public String getName() {
        return this.name; }

    public void setName(String name){
        this.name=name;
    }
    public void setType(String type){
        this.type=type;
    }

    public String getType() {
        return this.type; }}
