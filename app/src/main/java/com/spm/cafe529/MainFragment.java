package com.spm.cafe529;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.spm.cafe529.Listener.OnMenuClickListener;

/**
 * Created by 성호 on 2015-04-07.
 */
public class MainFragment extends MainActivity.PlaceholderFragment implements View.OnClickListener{

    OnMenuClickListener listener;

    //test
    Button btnToMenu;
    Button btnToSignup;

    //constants
    public final static int ORDER_BUTTON_CLICKED = 0x10;
    public final static int SIGNUP_BUTTON_CLICKED = 0x20;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        //Buttons in MainMenu
        btnToMenu = (Button)rootView.findViewById(R.id.button_to_order);
        btnToMenu.setOnClickListener(this);

        btnToSignup = (Button)rootView.findViewById(R.id.button_to_signup);
        btnToSignup.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if(id == R.id.button_to_order){
            listener.onMenuClicked(ORDER_BUTTON_CLICKED);
        }else if(id == R.id.button_to_signup){
            listener.onMenuClicked(SIGNUP_BUTTON_CLICKED);
        }
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
