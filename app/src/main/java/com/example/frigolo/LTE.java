package com.example.frigolo;

class LTE {

    private Integer element_id;
    private Integer list_id;
    private Boolean done;

    public LTE(Integer element_id, Integer list_id, Boolean done) {
        this.element_id = element_id;
        this.list_id = list_id;
        this.done = done; }

    public Integer getElementId() {
        return element_id; }

    public Integer getListId() {
        return list_id; }

    public Boolean getDone() {
        return this.done; }}
