package com.example.frigolo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentFridgeViewAliment extends android.app.Fragment {


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (container != null) {
            container.removeAllViews();
        }

        View view = inflater.inflate(R.layout.activity_myfridge_fridgeitem_view, container, false);
        TextView fname = view.findViewById(R.id.nameOfFridge);
        Log.i("Debug",fname.getText().toString());
        fname.setText(MainActivity.fname);
        Log.i("Debug",fname.getText().toString());

        return view;
    }




}
