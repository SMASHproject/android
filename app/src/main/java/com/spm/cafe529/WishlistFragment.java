package com.spm.cafe529;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.spm.cafe529.Library.HttpTask;
import com.spm.cafe529.Library.TaskListener;
import com.spm.cafe529.Listener.OnMenuClickListener;
import com.spm.cafe529.Struct.WishItem;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by 성호 on 2015-04-07.
 */
public class WishlistFragment extends MainActivity.PlaceholderFragment implements View.OnClickListener {

    OnMenuClickListener listener;
    ListView menuList;

    //XXX
    Button orderBtn;
    Context context;

    //adapter
    WishlistAdapter adapter;

    TaskListener taskListener;

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

        //XXX
        orderBtn = (Button) rootView.findViewById(R.id.wishlist_button);
        orderBtn.setOnClickListener(this);
        context = rootView.getContext();

        taskListener = new TaskListener() {
            String state;

            @Override
            public void onReceived(JSONObject json) {
                try {
                    Log.d("JSON.tostring:", json.toString());
                    state = json.getString("state");
                    if (state.equals("success")) {
                        Toast.makeText(getActivity(), "Order Accepted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "Order Failed", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Order Failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled() {

            }
        };

        return rootView;
    }

    public void updateItem(String item) {

        adapter.addItem(item);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.wishlist_button) {
            final Dialog login = new Dialog(context);
            login.setContentView(R.layout.dialog_login);
            login.setTitle("Order");

            Button btnLogin = (Button) login.findViewById(R.id.btnLogin);
            Button btnCancel = (Button) login.findViewById(R.id.btnCancel);
            final EditText txtUsername = (EditText) login.findViewById(R.id.txtUsername);
            final EditText txtPassword = (EditText) login.findViewById(R.id.txtPassword);

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (txtUsername.getText().toString().trim().length() > 0 && txtPassword.getText().toString().trim().length() > 0) {
                        //Here, validate code.
                        JSONObject object = new JSONObject();
                        try {
                            object.put("phone_number", txtUsername.getText().toString());
                            object.put("password", txtPassword.getText().toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                            return;
                        }

                        HttpTask httpTask = new HttpTask("http://166.104.245.69/login.php", object, taskListener);
                        httpTask.execute();

                        //Toast.makeText(getActivity(), "Order Accepted", Toast.LENGTH_LONG).show();
                        //login.dismiss();
                    } else {
                        Toast.makeText(getActivity(), "Please enter name & pwd", Toast.LENGTH_LONG).show();
                    }
                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    login.dismiss();
                }
            });

            // Make dialog visible
            login.show();
        }
    }

    private String SendByHttp(String msg) {
        if (msg == null)
            msg = "";

        DefaultHttpClient client = new DefaultHttpClient();

        try {
            HttpPost post = new HttpPost("166.104.245.69/login.php");

            HttpParams httpParams = client.getParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, 3000);
            HttpConnectionParams.setSoTimeout(httpParams, 3000);

            HttpResponse response = client.execute(post);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));

            String line = null;
            String result = "";

            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            client.getConnectionManager().shutdown();
            return "";
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnMenuClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "must implement onMenuClickListener");
        }
    }

}
