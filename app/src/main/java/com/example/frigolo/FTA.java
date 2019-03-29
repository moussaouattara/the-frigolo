package com.example.frigolo;

class FTA {

    private Integer aliment_id;
    private Integer fridge_id;
    private Integer quantity;
    private String date;

    public FTA(Integer aliment_id, Integer fridge_id, Integer quantity, String date) {
        this.aliment_id = aliment_id;
        this.fridge_id = fridge_id;
        this.quantity = quantity;
        this.date = date; }

    public Integer getAlimentId() {
        return this.aliment_id; }

    public Integer getFridgeId() {
        return this.fridge_id; }

    public Integer getQuantite() {
        return this.quantity; }

    public String getDate() {
        return this.date; }}
