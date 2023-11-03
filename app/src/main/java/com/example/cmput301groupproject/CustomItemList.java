package com.example.cmput301groupproject;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomItemList extends ArrayAdapter<HouseholdItem> {
    private final Context context;
    private ArrayList<HouseholdItem> items;

    public CustomItemList(Context context, ArrayList<HouseholdItem> items) {
        super(context, 0, items);
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_display_content, parent, false);
        }

        HouseholdItem item = items.get(position);
        TextView itemMake = view.findViewById(R.id.item_make);
        TextView itemDescription = view.findViewById(R.id.item_description);

        itemMake.setText(item.getMake());
        itemDescription.setText(item.getDescription());
        return view;
    }
}