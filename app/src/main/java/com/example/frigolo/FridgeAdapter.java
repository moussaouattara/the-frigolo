package com.example.frigolo;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;


public class FridgeAdapter extends BaseAdapter {
    private static ArrayList<Fridge> searchArrayList;

    private LayoutInflater mInflater;

    public FridgeAdapter(Context context, ArrayList<Fridge> results) {
        searchArrayList = results;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return searchArrayList.size();
    }

    @Override
    public Object getItem(int p) {
        return searchArrayList.get(p);
    }

    @Override
    public long getItemId(int p) {
        return p;
    }

    @Override
    public View getView(int p, View v, ViewGroup parent) {
        ViewHolder holder;

        if (v == null) {
            v = mInflater.inflate(R.layout.fridgelist, null);
            holder = new ViewHolder();

            holder.name = (TextView) v.findViewById(R.id.fridgename);
            holder.type = (TextView) v.findViewById(R.id.fridgetype);

            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        holder.name.setText(searchArrayList.get(p).getName());
        holder.type.setText(searchArrayList.get(p).getType());
        return v;
    }

    static class ViewHolder {
        TextView name, type;


    }

}