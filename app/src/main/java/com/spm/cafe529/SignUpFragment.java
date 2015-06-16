package com.spm.cafe529;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.spm.cafe529.Library.HttpTask;
import com.spm.cafe529.Library.TaskListener;
import com.spm.cafe529.Listener.OnMenuClickListener;

import org.json.JSONObject;

/**
 * Created by park on 2015. 6. 15..
 */
public class SignUpFragment extends MainActivity.PlaceholderFragment implements View.OnClickListener{
    OnMenuClickListener listener;

    public final static int MAIN_BUTTON_CLICKED = 0x30;

    EditText txtId;
    EditText txtPwd;
    Button okBtn;
    Button backBtn;

    TaskListener taskListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_signup, container, false);

        txtId = (EditText) rootView.findViewById(R.id.signup_id);
        txtPwd = (EditText) rootView.findViewById(R.id.signup_pwd);

        okBtn = (Button) rootView.findViewById(R.id.signup_btn);
        okBtn.setOnClickListener(this);
        backBtn = (Button) rootView.findViewById(R.id.signup_backBtn);
        backBtn.setOnClickListener(this);

        taskListener = new TaskListener() {
            String state;
            @Override
            public void onReceived(JSONObject json) {
                try {
                    state = json.getString("state");
                    if (state.equals("accept")) {
                        Toast.makeText(getActivity(), "Signup Success", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "Signup Failed", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onCancelled() {

            }
        };

        return rootView;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.signup_backBtn)
            listener.onMenuClicked(MAIN_BUTTON_CLICKED);
        else if (id == R.id.signup_btn) {
            JSONObject object = new JSONObject();
            try {
                object.put("phone_number", txtId.getText().toString());
                object.put("password", txtPwd.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            HttpTask httpTask = new HttpTask("http://166.104.245.69/register.php", object, taskListener);
            httpTask.execute();

            Log.d ("Send", " register info");
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
