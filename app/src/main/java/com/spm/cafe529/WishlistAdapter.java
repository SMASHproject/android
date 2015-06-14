package com.spm.cafe529;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.spm.cafe529.Struct.WishItem;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by sungho2 on 2015-05-29.
 */
public class WishlistAdapter extends ArrayAdapter<WishItem> implements View.OnClickListener {
    int resource;
    LayoutInflater inflater;
    ArrayList<WishItem> list;
    ViewGroup parent;

    public WishlistAdapter(Context context, int resource, ArrayList<WishItem> mArraylist) {
        super(context, resource, mArraylist);

        this.resource = resource;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        list = mArraylist;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        this.parent = parent;
        if (convertView == null) {
            convertView = inflater.inflate(resource, null);
        }
            //textview
            TextView itemTextView = (TextView) convertView.findViewById(R.id.item_name);
            TextView quantityTextView = (TextView) convertView.findViewById(R.id.item_quantity);

            Log.v("listview item", position + getItem(position).getItemName() + getItem(position).getItemCount());
            itemTextView.setText(getItem(position).getItemName());
            quantityTextView.setText(getItem(position).getItemCount()+"");

            //button
            Button plusBtn = (Button) convertView.findViewById(R.id.plus_btn);
            Button minusBtn = (Button) convertView.findViewById(R.id.minus_btn);
            plusBtn.setOnClickListener(this);
            minusBtn.setOnClickListener(this);
            plusBtn.setTag(position*10 + 1); // 31
            minusBtn.setTag(position * 10 + 2);  // 32


        return convertView;
    }

    @Override
    public void onClick(View v) {

        Log.v("Clicked", "tag : "+v.getTag());

        int tag = (int) v.getTag();
        int position = tag / 10;
        int type = tag % 10;

        if(getItem(position).calcItem(type));

    }

    public void addItem(String item) {
        for (int i=0; i<list.size(); i++){

            if(list.get(i).getItemName().equals(item)){
                if(getItem(i).calcItem(1)){
                    notifyDataSetChanged();
                }
                return;
            }
        }

        WishItem wishItem = new WishItem(item, 1);
        add(wishItem);

    }


}
