package com.example.frigolo;

class Aliment {

    private String name;
    private String category;
    private String code_bar;

    Aliment(String name, String category, String code_bar) {
        this.name = name;
        this.category = category;
        this.code_bar = code_bar; }

    String getName() {
        return this.name; }

    String getCategory() {
        return this.category; }

    String getCode_bar() {
        return this.code_bar; }}
