package com.example.frigolo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class BD extends SQLiteOpenHelper {

    private static final String DB_NAME = "FRIGOLO_DB";
    private static final int DB_VERSION = 1;

    private static final String FRIDGE_TABLE = "FRIDGE";
    private static final String FRIDGE_ID = "f_id";
    private static final String FRIDGE_NAME = "f_name";
    private static final String FRIDGE_TYPE = "f_type";

    private static final String ALIMENT_TABLE = "ALIMENT";
    private static final String ALIMENT_ID = "a_id";
    private static final String ALIMENT_NAME = "a_name";
    private static final String ALIMENT_CATEGORY = "a_category";
    private static final String ALIMENT_BARCODE = "a_barcode";

    private static final String ELEMENT_TABLE = "ELEMENT";
    private static final String ELEMENT_ID = "e_id";
    private static final String ELEMENT_NAME = "e_name";
    private static final String ELEMENT_CATEGORY = "e_category";

    private static final String LIST_TABLE = "LIST";
    private static final String LIST_ID = "l_id";
    private static final String LIST_NAME = "l_name";
    private static final String LIST_CREATION_DATE = "l_creation_date";
    private static final String LIST_MODIFICATION_DATE = "l_modification_date";

    private static final String FRIDGE_TO_ALIMENT_TABLE = "FRIDGE_TO_ALIMENT";
    private static final String FRIDGE_TO_ALIMENT_FRIDGE_ID = "fta_fridge_id";
    private static final String FRIDGE_TO_ALIMENT_ALIMENT_ID = "fta_aliment_id";
    private static final String FRIDGE_TO_ALIMENT_QUANTITY = "fta_quantity";
    private static final String FRIDGE_TO_ALIMENT_EXPIRATION = "fta_expiration_date";

    private static final String LIST_TO_ELEMENT_TABLE = "LIST_TO_ELEMENT";
    private static final String LIST_TO_ELEMENT_LIST_ID = "lte_list_id";
    private static final String LIST_TO_ELEMENT_ELEMENT_ID = "lte_element_id";
    private static final String LIST_TO_ELEMENT_DONE = "lte_done";

    private static final String TABLES_DROP = "DROP TABLE IF EXISTS " + LIST_TO_ELEMENT_TABLE + "; " +
            "DROP TABLE IF EXISTS " + ELEMENT_TABLE + "; " +
            "DROP TABLE IF EXISTS " + LIST_TABLE + "; " +
            "DROP TABLE IF EXISTS " + FRIDGE_TO_ALIMENT_TABLE + "; " +
            "DROP TABLE IF EXISTS " + ALIMENT_TABLE + "; " +
            "DROP TABLE IF EXISTS " + FRIDGE_TABLE;

    private SQLiteDatabase db;

    public BD(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_FRIDGE = "CREATE TABLE " + FRIDGE_TABLE + "(" +
                FRIDGE_ID + " INTEGER PRIMARY KEY, " +
                FRIDGE_NAME + " VARCHAR(50) UNIQUE, " +
                FRIDGE_TYPE + " VARCHAR(10))";

        String CREATE_ALIMENT = "CREATE TABLE " + ALIMENT_TABLE + "(" +
                ALIMENT_ID + " INTEGER PRIMARY KEY, " +
                ALIMENT_NAME + " VARCHAR(50) UNIQUE, " +
                ALIMENT_CATEGORY + " VARCHAR(20), " +
                ALIMENT_BARCODE + " VARCHAR(20) UNIQUE)";

        String CREATE_FTA = "CREATE TABLE " + FRIDGE_TO_ALIMENT_TABLE + "(" +
                FRIDGE_TO_ALIMENT_FRIDGE_ID + " INTEGER, " +
                FRIDGE_TO_ALIMENT_ALIMENT_ID + " INTEGER, " +
                FRIDGE_TO_ALIMENT_QUANTITY + " INTEGER, " +
                FRIDGE_TO_ALIMENT_EXPIRATION + " DATE, " +
                "FOREIGN KEY(" + FRIDGE_TO_ALIMENT_FRIDGE_ID + ") REFERENCES " + FRIDGE_TABLE + "(" + FRIDGE_ID + ") ON DELETE CASCADE, " +
                "FOREIGN KEY(" + FRIDGE_TO_ALIMENT_ALIMENT_ID + ") REFERENCES " + ALIMENT_TABLE + "(" + ALIMENT_ID + ") ON DELETE CASCADE, " +
                "PRIMARY KEY (" + FRIDGE_TO_ALIMENT_ALIMENT_ID + ", " + FRIDGE_TO_ALIMENT_FRIDGE_ID + "))";

        String CREATE_LIST = "CREATE TABLE " + LIST_TABLE + "(" +
                LIST_ID + " INTEGER PRIMARY KEY, " +
                LIST_NAME + " VARCHAR(50) UNIQUE, " +
                LIST_CREATION_DATE + " DATE, " +
                LIST_MODIFICATION_DATE + " DATE)";

        String CREATE_ELEMENT = "CREATE TABLE " + ELEMENT_TABLE + "(" +
                ELEMENT_ID + " INTEGER PRIMARY KEY, " +
                ELEMENT_NAME + " VARCHAR(50) UNIQUE, " +
                ELEMENT_CATEGORY + " VARCHAR(100))";

        String CREATE_LTE = "CREATE TABLE " + LIST_TO_ELEMENT_TABLE + "(" +
                LIST_TO_ELEMENT_ELEMENT_ID + " INTEGER, " +
                LIST_TO_ELEMENT_LIST_ID + " INTEGER, " +
                LIST_TO_ELEMENT_DONE + " BOOLEAN, " +
                "FOREIGN KEY(" + LIST_TO_ELEMENT_ELEMENT_ID + ") REFERENCES " + ELEMENT_TABLE + "(" + ELEMENT_ID + ") ON DELETE CASCADE, " +
                "FOREIGN KEY(" + LIST_TO_ELEMENT_LIST_ID + ") REFERENCES " + LIST_TABLE + "(" + LIST_ID + ") ON DELETE CASCADE, " +
                "PRIMARY KEY (" + LIST_TO_ELEMENT_LIST_ID + ", " + LIST_TO_ELEMENT_ELEMENT_ID + "))";

        db.execSQL(CREATE_FRIDGE);
        db.execSQL(CREATE_ALIMENT);
        db.execSQL(CREATE_FTA);
        db.execSQL(CREATE_LIST);
        db.execSQL(CREATE_ELEMENT);
        db.execSQL(CREATE_LTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TABLES_DROP);
        onCreate(db);
    }

    public void save(Fridge fridge) {
        ContentValues values = new ContentValues();
        values.put(FRIDGE_NAME, fridge.getName());
        values.put(FRIDGE_TYPE, fridge.getType());
        this.db.insertOrThrow(FRIDGE_TABLE, null, values);
    }

    public void save(Aliment aliment) {
        try {
            ContentValues values = new ContentValues();
            values.put(ALIMENT_NAME, aliment.getName());
            values.put(ALIMENT_CATEGORY, aliment.getCategory());
            values.put(ALIMENT_BARCODE, aliment.getCode_bar());
            this.db.insertOrThrow(ALIMENT_TABLE, null, values);
        } catch (SQLiteConstraintException ex) {
            Log.w("USER_ACTIVITY", "ALIMENT ALREADY IN THE DATABASE");
        }
    }

    public void save(FTA fta) {
        ContentValues values = new ContentValues();
        values.put(FRIDGE_TO_ALIMENT_ALIMENT_ID, fta.getAlimentId());
        values.put(FRIDGE_TO_ALIMENT_FRIDGE_ID, fta.getFridgeId());
        values.put(FRIDGE_TO_ALIMENT_QUANTITY, fta.getQuantite());
        values.put(FRIDGE_TO_ALIMENT_EXPIRATION, fta.getDate());
        this.db.insertOrThrow(FRIDGE_TO_ALIMENT_TABLE, null, values);
    }

    public void save(List list) {
        ContentValues values = new ContentValues();
        values.put(LIST_NAME, list.getName());
        values.put(LIST_CREATION_DATE, list.getDateCreation());
        values.put(LIST_MODIFICATION_DATE, list.getDateModification());
        this.db.insertOrThrow(LIST_TABLE, null, values);
    }

    public void save(Element element) {
        ContentValues values = new ContentValues();
        values.put(ELEMENT_NAME, element.getName());
        values.put(ELEMENT_CATEGORY, element.getCategory());
        this.db.insertOrThrow(ELEMENT_TABLE, null, values);
    }

    public void save(LTE lte) {
        ContentValues values = new ContentValues();
        values.put(LIST_TO_ELEMENT_ELEMENT_ID, lte.getElementId());
        values.put(LIST_TO_ELEMENT_LIST_ID, lte.getListId());
        values.put(LIST_TO_ELEMENT_DONE, lte.getDone());
        this.db.insertOrThrow(LIST_TO_ELEMENT_TABLE, null, values);
    }

    public java.util.List<Fridge> getAllFridge() {
        java.util.List<Fridge> fridge=new ArrayList<Fridge>();
        String query="SELECT * FROM "+FRIDGE_TABLE;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            do {
                Fridge f=new Fridge();
                f.setId(Integer.valueOf(cursor.getString(0)));
                f.setName(cursor.getString(1));
                f.setType(cursor.getString(2));
                fridge.add(f);

            }while(cursor.moveToNext());
        }

        return fridge;
    }

}
