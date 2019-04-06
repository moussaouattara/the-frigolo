package com.example.frigolo;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;

import java.util.ArrayList;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

//    private TextView mTextMessage;
    private static FragmentHome fHome =new FragmentHome();
    private static FragmentSetting fSetting =new FragmentSetting();
    private static FragmentFridge fFridge =new FragmentFridge();
    private static FragmentFridgeAdd fFridgeA = new FragmentFridgeAdd();
    private static FragmentFridgeView fFridgeV = new FragmentFridgeView();
    private static FragmentFridgeViewAliment fFridgeAV = new FragmentFridgeViewAliment();
    private static FragmentFridgeAddAliment fFridgeAA = new FragmentFridgeAddAliment();



    public static  String fname ="";
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

    public void goAddFridgeAliment(View view){
        getFragmentManager().beginTransaction().replace(R.id.fragment, fFridgeAA).commit();
//        TextView fname = (TextView) findViewById(R.id.nameOfFridge);
//        TextView fname2 = (TextView) findViewById(R.id.aliment_add_fname);
//        fname2.setText(fname.getText().toString());
    }


    public void goAddFridge(View view){
        getFragmentManager().beginTransaction().replace(R.id.fragment, fFridgeA).commit();
    }

    public void goViewFridgeAliment(ArrayList<FTA> ftalist,String name){

        getFragmentManager().beginTransaction().replace(R.id.fragment, fFridgeAV).commit();

        try {
            ArrayList<FTA> isialiment = new ArrayList<FTA>();
            this.listcontent=(ListView) findViewById(R.id.listaliment);
            isialiment.clear();
            BD databaseHandler = new BD(MainActivity.getAppContext());

            for (int p = 0; p < ftalist.size(); p++) {
                FTA baris = ftalist.get(p);
                isialiment.add(baris);
            }
            Log.e("Info :","Try to adapt");
            FTAAdapter datakamus = new FTAAdapter(MainActivity.getAppContext(), isialiment);
            listcontent.setAdapter(datakamus);
        }catch (java.lang.NullPointerException e){

        }


    }

    AdapterView.OnItemClickListener myhandler = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            try {
                BD db = new BD(getAppContext());
                Fridge fridge = (Fridge) parent.getItemAtPosition(position);
                MainActivity.fname = fridge.getName();
                ArrayList<FTA> ftalist = db.getOneFTA(fridge.getName());
                goViewFridgeAliment(ftalist,fridge.getName());
            }catch (java.lang.NullPointerException e){

            }
        }


    };

    public void goViewFridge(View view){
        getFragmentManager().beginTransaction().replace(R.id.fragment, fFridgeV).commit();
        tampilFridge();


    }



    public void addfridge(View view){
        EditText name = (EditText) findViewById(R.id.fridge_add_name);
        EditText type = (EditText) findViewById(R.id.fridge_add_type);

        Log.e("EditView", String.valueOf(name));
        Log.e("EditView", String.valueOf(type));

        if (name.getText().toString().matches("") || type.getText().toString().matches("")) {
            Toast.makeText(this,"Enter values",Toast.LENGTH_LONG).show();
        }
        else {
            try {
                BD db = new BD(this);
                db.save(new Fridge(name.getText().toString(), type.getText().toString()));
            }
            catch (SQLiteConstraintException e){
                Toast.makeText(this,"Aliment Name already pick",Toast.LENGTH_LONG).show();
            }


        }

    }

    public void addAlimentsandfta(View view){
        EditText aname = (EditText) findViewById(R.id.aliment_add_name);
        EditText aquantite = (EditText) findViewById(R.id.aliment_add_quantite);
        EditText adate = (EditText) findViewById(R.id.aliment_add_date);
        TextView fname = (TextView) findViewById(R.id.aliment_add_fname);

        if (aname.getText().toString().matches("") || aquantite.getText().toString().matches("")) {
            Toast.makeText(this,"Enter values",Toast.LENGTH_LONG).show();
        }
        else {
            try{
                Integer.parseInt(aquantite.getText().toString());

            }catch (NumberFormatException e){
                Toast.makeText(this,"Quantity should be a number",Toast.LENGTH_LONG).show();
            }
            try {
                BD db = new BD(this);
                db.save(new FTA(fname.getText().toString(),aname.getText().toString(), Integer.parseInt(aquantite.getText().toString()),adate.getText().toString()));
//                if (db.getOneAliment(aname.getText().toString())==null){
                    db.save(new Aliment(aname.getText().toString(),null,null));
//                }
                try{
                goViewFridgeAliment(db.getOneFTA(fname.getText().toString()),fname.getText().toString());
                }
                catch (SQLiteConstraintException e){
                }
            }
            catch (SQLiteConstraintException e){
                Toast.makeText(this,"Aliment Name already pick",Toast.LENGTH_LONG).show();
            }


        }


    }

    public void commingSoon(View view){
        Toast.makeText(this,"Comming soon",Toast.LENGTH_LONG).show();

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
                Fridge baris = data.get(p);
                komponenfridge = new Fridge(baris.getName(),baris.getType());
                Log.i("baris", baris.getName());
                Log.i("baris", baris.getType());
                Log.i("Frigo",baris.toString());

                isifridge.add(komponenfridge);
            }
            Log.e("Info :","Try to adapt");
            FridgeAdapter datakamus = new FridgeAdapter(MainActivity.getAppContext(), isifridge);
            listcontent.setAdapter(datakamus);
            listcontent.setOnItemClickListener(myhandler);
        }catch (java.lang.NullPointerException e){

        }
    }


}
