package com.example.frigolo;

public class Fridge {

    private String name;
    private String type;
    private int id;

    public Fridge(String name,    String type) {
        this.name = name;
        this.type = type; }

    public Fridge(){

    }

    public String getName() {
        return this.name; }

    public int getId() {
        return this.id; }

    public void setName(String name){
        this.name=name;
    }
    public void setType(String type){
        this.type=type;
    }
    public void setId(int id){
        this.id=id;
    }

    public String getType() {
        return this.type; }}
