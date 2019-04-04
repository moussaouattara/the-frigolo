package com.example.frigolo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.zip.Inflater;

public class FragmentFridgeAddAliment extends android.app.Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (container != null) {
            container.removeAllViews();
        }
        View view = inflater.inflate(R.layout.activity_myfridge_add_aliment, container, false);
        TextView fname = view.findViewById(R.id.nameOfFridge);


        return view;
    }
}
