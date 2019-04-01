package com.example.frigolo;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class List {

    private String name;
    private Date date_creation;
    private Date date_modification;

    List(String name) {
        this.name = name;
        this.date_creation = new Date();
        this.date_modification = null; }

    List(String name, String date_creation, String date_modification) {
        this.name = name;

        try { this.date_creation = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date_creation); }
        catch(ParseException ex) { Log.e("USER_ACTIVITY", "ERROR PARSING CREATION DATE !"); }

        try { this.date_modification = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date_modification); }
        catch(ParseException ex) { Log.e("USER_ACTIVITY", "ERROR PARSING MODIFICATION DATE !"); }}

    String getName() {
        return this.name; }

    String getDateModification() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.date_creation); }

    String getDateCreation() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.date_modification); }}
