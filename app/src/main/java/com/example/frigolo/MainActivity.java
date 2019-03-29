package com.example.frigolo;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.main_activity);
        BD db = new BD(this);
        db.save(new Aliment("Pâtes", "Féculent", "1234567891234"));
        db.save(new Aliment("Jambon", "Viande", "1234567891235"));
        db.save(new Aliment("Fromage blanc", "Fromage", "1234567891236"));
    }}
