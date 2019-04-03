package com.example.frigolo;

public class Fridge {

    private String name;
    private String type;

    public Fridge(String name,String type) {
        this.name = name;
        this.type = type; }

    public String getName() {
        return this.name; }


    public String getType() {
        return this.type; }

    public String toString(){
        return this.name+" : "+this.type;
    }
}
