package com.example.frigolo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class FTAAdapter extends BaseAdapter {
    private static ArrayList<FTA> searchArrayList;

    private LayoutInflater mInflater;

    public FTAAdapter(Context context, ArrayList<FTA> results) {
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
            v = mInflater.inflate(R.layout.alimentlist, null);
            holder = new FTAAdapter.ViewHolder();

            holder.name = (TextView) v.findViewById(R.id.alimentname);
            holder.quantite = (TextView) v.findViewById(R.id.alimentquantite);
            holder.date = (TextView) v.findViewById(R.id.alimentdate);

            v.setTag(holder);
        } else {
            holder = (FTAAdapter.ViewHolder) v.getTag();
        }

        holder.name.setText(searchArrayList.get(p).getAlimentName());
        holder.quantite.setText(searchArrayList.get(p).getQuantite());
        holder.quantite.setText(searchArrayList.get(p).getDate());
        return v;
    }

    static class ViewHolder {
        TextView name, quantite,date;


    }

}