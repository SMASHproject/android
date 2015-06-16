package com.spm.cafe529;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.spm.cafe529.Library.HttpTask;
import com.spm.cafe529.Library.TaskListener;
import com.spm.cafe529.Listener.OnMenuClickListener;
import com.spm.cafe529.Listener.OnWishItemClickListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class MainActivity extends FragmentActivity implements OnMenuClickListener, OnWishItemClickListener {

    public final static int TABLE_NUM = 1;
    // SharedPreferences에 저장할 때 key 값으로 사용됨.
    public static final String PROPERTY_REG_ID = "registration_id";
    private final static int ADVERTISE_FRAGMENT = 1;
    private final static int MAIN_FRAGMENT = 2;
    private final static int MENU_FRAGMENT = 3;
    private final static int WISHLIST_FRAGMENT = 4;
    private final static int SIGNUP_FRAGMENT = 5;
    private final static int ORDER_BUTTON_CLICKED = 0x10;
    private final static int SIGNUP_BUTTON_CLICKED = 0x20;
    private final static int MAIN_BUTTON_CLICKED = 0x30;
    private final static int BACK_BUTTON_CLICKED = 0x40;
    //GCM 내용 변수들
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    // SharedPreferences에 저장할 때 key 값으로 사용됨.
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static final String TAG = "ICELANCER";
    static WishlistFragment wishlistFragment;
    private static int depth;
    String SENDER_ID = "116594608999";
    GoogleCloudMessaging gcm;
    Context context;
    String regid;
    Fragment dummyFragment;

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        //GCM
        if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(this);
            regid = getRegistrationId(context);

            if (regid.isEmpty()) {
                registerInBackground();
            }
        } else {
            Log.i(TAG, "No valid Google Play Services APK found.");
        }

        //Fragment initialize
        dummyFragment = getSupportFragmentManager().findFragmentById(R.id.dummy_fragment);
        depth = 1;
//
        //first transaction
        FragmentManager fragmentManager = dummyFragment.getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_layout, PlaceholderFragment.newInstance(ADVERTISE_FRAGMENT)).commit();
        fragmentManager = dummyFragment.getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.sub_layout, PlaceholderFragment.newInstance(MAIN_FRAGMENT)).commit();

    }

    @Override
    public void OnItemClicked(String item) {

        if (wishlistFragment != null)
            wishlistFragment.updateItem(item);
    }

    @Override
    public void onMenuClicked(int selector) {
        if (selector == ORDER_BUTTON_CLICKED) {
            FragmentManager fragmentManager = dummyFragment.getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_layout, PlaceholderFragment.newInstance(MENU_FRAGMENT)).commit();
            fragmentManager.beginTransaction().replace(R.id.sub_layout, PlaceholderFragment.newInstance(WISHLIST_FRAGMENT)).commit();
            depth = 2;
        } else if (selector == SIGNUP_BUTTON_CLICKED) {
            FragmentManager fragmentManager = dummyFragment.getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.sub_layout, PlaceholderFragment.newInstance(SIGNUP_FRAGMENT)).commit();
        } else if (selector == MAIN_BUTTON_CLICKED) {
            FragmentManager fragmentManager = dummyFragment.getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.sub_layout, PlaceholderFragment.newInstance(MAIN_FRAGMENT)).commit();
        } else if (selector == BACK_BUTTON_CLICKED) {

            switch (depth) {
                case 1:
                    //앱종료 나중에 지우기
                    android.os.Process.killProcess(android.os.Process.myPid());
                    break;
                case 2:
                    FragmentManager fragmentManager = dummyFragment.getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.main_layout, PlaceholderFragment.newInstance(ADVERTISE_FRAGMENT)).commit();
                    fragmentManager.beginTransaction().replace(R.id.sub_layout, PlaceholderFragment.newInstance(MAIN_FRAGMENT)).commit();
                    depth = 1;
                    break;
            }
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.v("KEy Pressed", "BACK KEY");
            onMenuClicked(BACK_BUTTON_CLICKED);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    /*    GCM functions    */
    public void openAlarmActivity() {
        Log.v("heelo", "asdad");
    }

    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    regid = gcm.register(SENDER_ID);
                    //여기다가
                    registerIDtoDB();
                    msg = "Device registered, registration ID=" + regid;
                    Log.i("REGID", regid);

                    storeRegistrationId(context, regid);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                Log.v("GCM Registered", msg);
            }

        }.execute(null, null, null);
    }

    private void storeRegistrationId(Context context, String regid) {
        final SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regid);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPlayServices();
    }

    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }

        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    private SharedPreferences getGCMPreferences(Context context) {
        return getSharedPreferences(MainActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i("ICELANCER", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    //GCM httpTask
    private void registerIDtoDB() {


        JSONObject requestjson = new JSONObject();
        try {
            requestjson.put("table_num", TABLE_NUM);
            requestjson.put("regid", regid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpTask httpTask = new HttpTask("http://166.104.245.69/register_id.php", requestjson, new TaskListener() {
            @Override
            public void onReceived(JSONObject json) {

            }

            @Override
            public void onCancelled() {

            }
        });
        httpTask.execute();
    }

    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = null;

            switch (sectionNumber) {
                case ADVERTISE_FRAGMENT:
                    fragment = new AdvertiseFragment();

                    break;
                case MAIN_FRAGMENT:
                    fragment = new MainFragment();
                    break;
                case MENU_FRAGMENT:
                    fragment = new MenuFragment();
                    break;
                case WISHLIST_FRAGMENT:
                    fragment = new WishlistFragment();
                    wishlistFragment = (WishlistFragment) fragment;
                    break;
                case SIGNUP_FRAGMENT:
                    fragment = new SignUpFragment();
            }

            return fragment;
        }

    }
}
