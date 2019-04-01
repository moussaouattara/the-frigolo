package com.example.frigolo;

class FTA {

    private String fridge_name;
    private String aliment_name;
    private Integer quantity;
    private String date;

    FTA(String fridge_name, String aliment_name, Integer quantity, String date) {
        this.aliment_name = aliment_name;
        this.fridge_name = fridge_name;
        this.quantity = quantity;
        this.date = date; }

    String getAlimentName() {
        return this.aliment_name; }

    String getFridgeName() {
        return this.fridge_name; }

    Integer getQuantite() {
        return this.quantity; }

    String getDate() {
        return this.date; }}
