package com.example.frigolo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class BD extends SQLiteOpenHelper {

    private static final String DB_LOG = "DB_LOG";

    private static final String DB_NAME = "FRIGOLO_DB";
    private static final int DB_VERSION = 1;

    private static final String FRIDGE_TABLE = "FRIDGE";
    private static final String FRIDGE_NAME = "f_name";
    private static final String FRIDGE_TYPE = "f_type";

    private static final String ALIMENT_TABLE = "ALIMENT";
    private static final String ALIMENT_NAME = "a_name";
    private static final String ALIMENT_CATEGORY = "a_category";
    private static final String ALIMENT_BARCODE = "a_barcode";

    private static final String ELEMENT_TABLE = "ELEMENT";
    private static final String ELEMENT_NAME = "e_name";
    private static final String ELEMENT_CATEGORY = "e_category";

    private static final String LIST_TABLE = "LIST";
    private static final String LIST_NAME = "l_name";
    private static final String LIST_CREATION_DATE = "l_creation_date";
    private static final String LIST_MODIFICATION_DATE = "l_modification_date";

    private static final String FRIDGE_TO_ALIMENT_TABLE = "FRIDGE_TO_ALIMENT";
    private static final String FRIDGE_TO_ALIMENT_FRIDGE = "fta_fridge";
    private static final String FRIDGE_TO_ALIMENT_ALIMENT = "fta_aliment";
    private static final String FRIDGE_TO_ALIMENT_QUANTITY = "fta_quantity";
    private static final String FRIDGE_TO_ALIMENT_EXPIRATION = "fta_expiration_date";

    private static final String LIST_TO_ELEMENT_TABLE = "LIST_TO_ELEMENT";
    private static final String LIST_TO_ELEMENT_LIST = "lte_list";
    private static final String LIST_TO_ELEMENT_ELEMENT = "lte_element";
    private static final String LIST_TO_ELEMENT_DONE = "lte_done";

    private static final String TABLES_DROP = String.format(
                                                "DROP TABLE IF EXISTS %s;DROP TABLE IF EXISTS %s;DROP TABLE IF EXISTS %s;DROP TABLE IF EXISTS %s;DROP TABLE IF EXISTS %s;DROP TABLE IF EXISTS %s",
                                                LIST_TO_ELEMENT_TABLE,
                                                ELEMENT_TABLE,
                                                LIST_TABLE,
                                                FRIDGE_TO_ALIMENT_TABLE,
                                                ALIMENT_TABLE,
                                                FRIDGE_TABLE );

    private SQLiteDatabase db;

    BD(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.db = this.getWritableDatabase(); }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_FRIDGE        = "CREATE TABLE " + FRIDGE_TABLE + "(" +
                                      FRIDGE_NAME + " VARCHAR(50) PRIMARY KEY," +
                                      FRIDGE_TYPE + " VARCHAR(10))";

        String CREATE_ALIMENT       = "CREATE TABLE " + ALIMENT_TABLE + "(" +
                                      ALIMENT_NAME + " VARCHAR(50) PRIMARY KEY, " +
                                      ALIMENT_CATEGORY + " VARCHAR(20)," +
                                      ALIMENT_BARCODE + " VARCHAR(20) UNIQUE)";

        String CREATE_FTA           = "CREATE TABLE " + FRIDGE_TO_ALIMENT_TABLE + "(" +
                                      FRIDGE_TO_ALIMENT_FRIDGE + " VARCHAR(50)," +
                                      FRIDGE_TO_ALIMENT_ALIMENT + " VARCHAR(50)," +
                                      FRIDGE_TO_ALIMENT_QUANTITY + " INTEGER," +
                                      FRIDGE_TO_ALIMENT_EXPIRATION + " DATE," +
                                      "FOREIGN KEY(" + FRIDGE_TO_ALIMENT_FRIDGE + ") REFERENCES " + FRIDGE_TABLE + "(" + FRIDGE_NAME + ") ON DELETE CASCADE," +
                                      "FOREIGN KEY(" + FRIDGE_TO_ALIMENT_ALIMENT + ") REFERENCES " + ALIMENT_TABLE + "(" + ALIMENT_NAME + ") ON DELETE CASCADE," +
                                      "PRIMARY KEY (" + FRIDGE_TO_ALIMENT_ALIMENT + ", " + FRIDGE_TO_ALIMENT_FRIDGE + "))";

        String CREATE_LIST          = "CREATE TABLE " + LIST_TABLE + "(" +
                                      LIST_NAME + " VARCHAR(50) PRIMARY KEY," +
                                      LIST_CREATION_DATE + " DATE," +
                                      LIST_MODIFICATION_DATE + " DATE)";

        String CREATE_ELEMENT       = "CREATE TABLE " + ELEMENT_TABLE + "(" +
                                      ELEMENT_NAME + " VARCHAR(50) PRIMARY KEY," +
                                      ELEMENT_CATEGORY + " VARCHAR(100))";

        String CREATE_LTE           = "CREATE TABLE " + LIST_TO_ELEMENT_TABLE + "(" +
                                      LIST_TO_ELEMENT_LIST + " VARCHAR(50)," +
                                      LIST_TO_ELEMENT_ELEMENT + " VARCHAR(50)," +
                                      LIST_TO_ELEMENT_DONE + " BOOLEAN," +
                                      "FOREIGN KEY(" + LIST_TO_ELEMENT_ELEMENT + ") REFERENCES " + ELEMENT_TABLE + "(" + ELEMENT_NAME + ") ON DELETE CASCADE," +
                                      "FOREIGN KEY(" + LIST_TO_ELEMENT_LIST + ") REFERENCES " + LIST_TABLE + "(" + LIST_NAME + ") ON DELETE CASCADE," +
                                      "PRIMARY KEY (" + LIST_TO_ELEMENT_LIST + ", " + LIST_TO_ELEMENT_ELEMENT + "))";

        db.execSQL(CREATE_FRIDGE);
        db.execSQL(CREATE_ALIMENT);
        db.execSQL(CREATE_FTA);
        db.execSQL(CREATE_LIST);
        db.execSQL(CREATE_ELEMENT);
        db.execSQL(CREATE_LTE); }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TABLES_DROP);
        onCreate(db); }

    public void save(Fridge fridge) {
        try {
            ContentValues values = new ContentValues();
            values.put(FRIDGE_NAME, fridge.getName());
            values.put(FRIDGE_TYPE, fridge.getType());
            this.db.insertOrThrow(FRIDGE_TABLE, null, values); }
        catch(SQLiteConstraintException ex) { Log.w(DB_LOG, "FRIDGE : NAME MUST BE UNIQUE"); }}

    public Fridge getOneFridge(String name) {
        try {
            Cursor c = this.db.query(FRIDGE_TABLE,
                                     new String[] { FRIDGE_NAME, FRIDGE_TYPE },
                                     FRIDGE_NAME + "=?", new String[] { name },
                                     null, null, null );
            if (c != null) {
                c.moveToFirst();
                Fridge candidate = new Fridge(c.getString(0), c.getString(1));
                c.close();
                return  candidate; }}
        catch(CursorIndexOutOfBoundsException ex) { Log.e(DB_LOG, "Fridge getOne error !"); }
        Log.w(DB_LOG, "RETURN NULL (getOneFridge)");
        return null; }

    public ArrayList<Fridge> getAllFridge() {
        ArrayList<Fridge> candidate = new ArrayList<>();
        Cursor c = this.db.rawQuery("SELECT * FROM " + FRIDGE_TABLE, null);
        if (c != null && c.moveToFirst()) {
            do { candidate.add(new Fridge(c.getString(0), c.getString(1))); } while (c.moveToNext());
            c.close(); }
        return candidate; }

    public void save(Aliment aliment) {
        try {
            ContentValues values = new ContentValues();
            values.put(ALIMENT_NAME, aliment.getName());
            values.put(ALIMENT_CATEGORY, aliment.getCategory());
            values.put(ALIMENT_BARCODE, aliment.getCode_bar());
            this.db.insertOrThrow(ALIMENT_TABLE, null, values); }
        catch(SQLiteConstraintException ex) { Log.w(DB_LOG, "ALIMENT : ALIMENT ALREADY IN"); }}

    public Aliment getOneAliment(String name) {
        try {
            Cursor c = this.db.query(ALIMENT_TABLE,
                    new String[] { ALIMENT_NAME, ALIMENT_CATEGORY, ALIMENT_BARCODE },
                    FRIDGE_NAME + "=?", new String[] { name },
                    null, null, null );
            if (c != null && c.moveToFirst()) {
                Aliment candidate = new Aliment(c.getString(0), c.getString(1), c.getString(2));
                c.close();
                return candidate; }}
        catch(CursorIndexOutOfBoundsException ex) { Log.e(DB_LOG, "Aliment getOne error !"); }
        Log.w(DB_LOG, "RETURN NULL (getOneAliment)");
        return null; }

    public ArrayList<Aliment> getAllAliment() {
        ArrayList<Aliment> candidate = new ArrayList<>();
        Cursor c = this.db.rawQuery("SELECT * FROM " + ALIMENT_TABLE, null);
        if (c != null && c.moveToFirst()) {
            do { candidate.add(new Aliment(c.getString(0), c.getString(1), c.getString(2))); } while (c.moveToNext());
            c.close(); }
        return candidate; }

    public void save(FTA fta) {
        try {
            ContentValues values = new ContentValues();
            values.put(FRIDGE_TO_ALIMENT_ALIMENT, fta.getAlimentName());
            values.put(FRIDGE_TO_ALIMENT_FRIDGE, fta.getFridgeName());
            values.put(FRIDGE_TO_ALIMENT_QUANTITY, fta.getQuantite());
            values.put(FRIDGE_TO_ALIMENT_EXPIRATION, fta.getDate());
            this.db.insertOrThrow(FRIDGE_TO_ALIMENT_TABLE, null, values); }
        catch(SQLiteConstraintException ex) { Log.w(DB_LOG, "FTA : CONSTRAINT ERROR"); }}

    public ArrayList<FTA> getOneFTA(String fridge_name) {
        try {
            ArrayList<FTA> candidate = new ArrayList<>();
            Cursor c = this.db.query(FRIDGE_TO_ALIMENT_TABLE,
                    new String[] {FRIDGE_TO_ALIMENT_FRIDGE, FRIDGE_TO_ALIMENT_ALIMENT, FRIDGE_TO_ALIMENT_QUANTITY, FRIDGE_TO_ALIMENT_EXPIRATION },
                    FRIDGE_TO_ALIMENT_FRIDGE + "=?", new String[] { fridge_name },
                    null, null, null );
            if (c != null && c.moveToFirst()) {
                do { candidate.add(new FTA(c.getString(0), c.getString(1), Integer.parseInt(c.getString(2)), c.getString(3))); } while (c.moveToNext());
                c.close();
                return candidate; }}
        catch(CursorIndexOutOfBoundsException ex) { Log.e(DB_LOG, "FTA getOneFTA error !"); }
        Log.w(DB_LOG, "RETURN NULL (getOneFTA)");
        return null; }

    public void save(List list) {
        try {
            ContentValues values = new ContentValues();
            values.put(LIST_NAME, list.getName());
            values.put(LIST_CREATION_DATE, list.getDateCreation());
            values.put(LIST_MODIFICATION_DATE, list.getDateModification());
            this.db.insertOrThrow(LIST_TABLE, null, values); }
        catch(SQLiteConstraintException ex) { Log.w(DB_LOG, "LIST : NAME MUST BE UNIQUE"); }}

    public List getOneList(String list_name) {
        try {
            Cursor c = this.db.query(LIST_TABLE,
                    new String[] { LIST_NAME, LIST_CREATION_DATE, LIST_MODIFICATION_DATE },
                    LIST_NAME + "=?", new String[] { list_name },
                    null, null, null );
            if (c != null && c.moveToFirst()) {
                List candidate = new List(c.getString(0), c.getString(1), c.getString(2));
                c.close();
                return candidate; }}
        catch(CursorIndexOutOfBoundsException ex) { Log.e(DB_LOG, "List getOne error !"); }
        Log.w(DB_LOG, "RETURN NULL (getOneList)");
        return null; }

    public ArrayList<List> getAllList() {
        ArrayList<List> candidate = new ArrayList<>();
        Cursor c = this.db.rawQuery("SELECT * FROM " + LIST_TABLE, null);
        if (c != null && c.moveToFirst()) {
            do { candidate.add(new List(c.getString(0), c.getString(1), c.getString(2))); } while (c.moveToNext());
            c.close(); }
        return candidate; }

    public void save(Element element) {
        try {
            ContentValues values = new ContentValues();
            values.put(ELEMENT_NAME, element.getName());
            values.put(ELEMENT_CATEGORY, element.getCategory());
            this.db.insertOrThrow(ELEMENT_TABLE, null, values); }
        catch(SQLiteConstraintException ex) { Log.w(DB_LOG, "ELEMENT : ELEMENT ALREADY IN"); }}

    public Element getOneElement(String name) {
        try {
            Cursor c = this.db.query(ELEMENT_TABLE,
                    new String[] { ELEMENT_NAME, ELEMENT_CATEGORY },
                    ELEMENT_NAME + "=?", new String[] { name },
                    null, null, null );
            if (c != null && c.moveToFirst()) {
                Element candidate = new Element(c.getString(0), c.getString(1));
                c.close();
                return candidate; }}
        catch(CursorIndexOutOfBoundsException ex) { Log.e(DB_LOG, "Aliment getOne error !"); }
        Log.w(DB_LOG, "RETURN NULL (GetOneElement)");
        return null; }

    public ArrayList<Element> getAllElement() {
        ArrayList<Element> candidate = new ArrayList<>();
        Cursor c = this.db.rawQuery("SELECT * FROM " + ELEMENT_TABLE, null);
        if (c != null && c.moveToFirst()) {
            do { candidate.add(new Element(c.getString(0), c.getString(1))); } while (c.moveToNext());
            c.close(); }
        return candidate; }

    public void save(LTE lte) {
        try {
            ContentValues values = new ContentValues();
            values.put(LIST_TO_ELEMENT_ELEMENT, lte.getElementName());
            values.put(LIST_TO_ELEMENT_LIST, lte.getListName());
            values.put(LIST_TO_ELEMENT_DONE, lte.getDone());
            this.db.insertOrThrow(LIST_TO_ELEMENT_TABLE, null, values); }
        catch(SQLiteConstraintException ex) { Log.w(DB_LOG, "LTE : CONSTRAINT ERROR"); }}

    public ArrayList<LTE> getOneLTE(String list_name) {
        try {
            ArrayList<LTE> candidate = new ArrayList<>();
            Cursor c = this.db.query(LIST_TO_ELEMENT_TABLE,
                    new String[] { LIST_TO_ELEMENT_LIST, LIST_TO_ELEMENT_ELEMENT, LIST_TO_ELEMENT_DONE },
                    LIST_TO_ELEMENT_LIST + "=?", new String[] { list_name },
                    null, null, null );
            if (c != null && c.moveToFirst()) {
                do { candidate.add(new LTE(c.getString(0), c.getString(1), Boolean.parseBoolean(c.getString(2)))); } while (c.moveToNext());
                c.close();
                return candidate; }}
        catch(CursorIndexOutOfBoundsException ex) { Log.e(DB_LOG, "FTA getOneFTA error !"); }
        Log.w(DB_LOG, "RETURN NULL (getOneLTE)");
        return null; }}
