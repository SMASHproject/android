package com.spm.cafe529;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.spm.cafe529.Listener.OnMenuClickListener;
import com.spm.cafe529.Struct.WishItem;

import java.util.ArrayList;

/**
 * Created by 성호 on 2015-04-07.
 */
public class WishlistFragment extends MainActivity.PlaceholderFragment implements View.OnClickListener {

    OnMenuClickListener listener;
    ListView menuList;

    //adapter
    WishlistAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wishlist, container, false);

        menuList = (ListView) rootView.findViewById(R.id.wishlist_list);

        adapter = new WishlistAdapter(rootView.getContext(), R.layout.wishlist_row, new ArrayList<WishItem>());
        menuList.setAdapter(adapter);

        return rootView;
    }

    public void updateItem(String item){
        adapter.addItem(item);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnMenuClickListener) activity;
        } catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + "must implement onMenuClickListener");
        }
    }

}
