package com.example.frigolo;

class Aliment {

    private String name;
    private String category;
    private String code_bar;

    public Aliment(String name, String category, String code_bar) {
        this.name = name;
        this.category = category;
        this.code_bar = code_bar; }

    public String getName() {
        return this.name; }

    public String getCategory() {
        return this.category; }

    public String getCode_bar() {
        return this.code_bar; }}
