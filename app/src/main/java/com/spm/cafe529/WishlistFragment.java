package com.spm.cafe529;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 성호 on 2015-04-07.
 */
public class WishlistFragment extends MainActivity.PlaceholderFragment implements View.OnClickListener{

    OnMenuClickListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_wishlist, container, false);
        return rootView;
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
