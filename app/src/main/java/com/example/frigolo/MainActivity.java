package com.example.frigolo;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

//    private TextView mTextMessage;
    private static FragmentHome fHome =new FragmentHome();
    private static FragmentSetting fSetting =new FragmentSetting();
    private static FragmentFridge fFridge =new FragmentFridge();
    private static FragmentFridgeAdd fFridgeA = new FragmentFridgeAdd();
    private static FragmentFridgeView fFridgeV = new FragmentFridgeView();



    private ListView listcontent;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_scan:
//                    mTextMessage.setText(R.string.title_scan);
                    return true;
                case R.id.navigation_myfridge:
//                    mTextMessage.setText(R.string.title_myfridge);
                    getFragmentManager().beginTransaction().replace(R.id.fragment, fFridgeV).commit();
                    tampilFridge();
                    return true;
                case R.id.navigation_setting:
//                    mTextMessage.setText(R.string.title_setting);
                    getFragmentManager().beginTransaction().replace(R.id.fragment, fSetting).commit();
                    return true;
            }
            return false;
        }
    };

    public static Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        MainActivity.context = getApplicationContext();
        setContentView(R.layout.activity_main);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

       getFragmentManager().beginTransaction().replace(R.id.fragment, fHome).commit();

    }

    public static Context getAppContext() {
        return MainActivity.context;
    }

    public void goAddFridge(View view){
        getFragmentManager().beginTransaction().replace(R.id.fragment, fFridgeA).commit();

    }


    public void goViewFridge(View view){
        getFragmentManager().beginTransaction().replace(R.id.fragment, fFridgeV).commit();
        tampilFridge();

    }



    public void addfridge(View view){



        EditText name = (EditText) findViewById(R.id.fridge_add_type);
        EditText type = (EditText) findViewById(R.id.fridge_add_name);

        Log.e("EditView", String.valueOf(name));
        Log.e("EditView", String.valueOf(type));

        if (name.getText().toString().matches("") || type.getText().toString().matches("")) {
            Toast.makeText(this,"Enter value",Toast.LENGTH_LONG).show();
        }
        else {
            try {
                BD db = new BD(this);
                db.save(new Fridge(name.getText().toString(), type.getText().toString()));
            }
            catch (SQLiteConstraintException e){
                Toast.makeText(this,"Name already pick",Toast.LENGTH_LONG).show();
            }


        }


    }

    public void tampilFridge() {
        // TODO Auto-generated method stub
        try {
            Fridge komponenfridge;
            ArrayList<Fridge> isifridge = new ArrayList<Fridge>();

            this.listcontent=(ListView) findViewById(R.id.fridge_list);
            isifridge.clear();
            BD databaseHandler = new BD(MainActivity.getAppContext());
            ArrayList<Fridge> data = (ArrayList<Fridge>) databaseHandler.getAllFridge();


            for (int p = 0; p < data.size(); p++) {
                komponenfridge = new Fridge();
                Fridge baris = data.get(p);
                Log.e("baris", baris.getName());
                Log.e("baris", baris.getType());
                komponenfridge.setName(baris.getType());
                komponenfridge.setType(baris.getName());
                komponenfridge.setId(baris.getId());
                isifridge.add(komponenfridge);
            }
            Log.e("Info :","Try to adapt");
            FridgeAdapter datakamus = new FridgeAdapter(MainActivity.getAppContext(), isifridge);
            listcontent.setAdapter(datakamus);
        }catch (java.lang.NullPointerException e){

        }
    }


}
