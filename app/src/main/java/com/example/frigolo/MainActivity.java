package com.example.frigolo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

//    private TextView mTextMessage;
    private static FragmentHome fHome =new FragmentHome();
    private static FragmentSetting fSetting =new FragmentSetting();
    private static FragmentFridge fFridge =new FragmentFridge();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_scan:
//                    mTextMessage.setText(R.string.title_scan);
                    getFragmentManager().beginTransaction().replace(R.id.fragment, fHome).commit();
                    return true;
                case R.id.navigation_myfridge:
//                    mTextMessage.setText(R.string.title_myfridge);
                    getFragmentManager().beginTransaction().replace(R.id.fragment, fFridge).commit();
                    return true;
                case R.id.navigation_setting:
//                    mTextMessage.setText(R.string.title_setting);
                    getFragmentManager().beginTransaction().replace(R.id.fragment, fSetting).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        MenuItem i=(MenuItem) findViewById(R.id.navigation);
        getFragmentManager().beginTransaction().replace(R.id.fragment, fHome).commit();

        BD db = new BD(this);


    }


}
