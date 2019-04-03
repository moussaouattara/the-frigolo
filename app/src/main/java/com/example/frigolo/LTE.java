package com.example.frigolo;

class LTE {

    private String list_name;
    private String element_name;
    private Boolean done;

    LTE(String list_name, String element_name, Boolean done) {
        this.element_name = element_name;
        this.list_name = list_name;
        this.done = done; }

    String getElementName() {
        return this.element_name; }

    String getListName() {
        return this.list_name; }

    Boolean getDone() {
        return this.done; }}
