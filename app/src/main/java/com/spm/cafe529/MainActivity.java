package com.spm.cafe529;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.spm.cafe529.Listener.OnMenuClickListener;
import com.spm.cafe529.Listener.OnWishItemClickListener;


public class MainActivity extends FragmentActivity implements OnMenuClickListener, OnWishItemClickListener {

            private final static int ADVERTISE_FRAGMENT = 1;
            private final static int MAIN_FRAGMENT = 2;
            private final static int MENU_FRAGMENT = 3;
            private final static int WISHLIST_FRAGMENT = 4;

            private final static int ORDER_BUTTON_CLICKED = 0x10;


            Fragment dummyFragment;
            static WishlistFragment wishlistFragment;


            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                //Fragment initialize
                dummyFragment = getSupportFragmentManager().findFragmentById(R.id.dummy_fragment);
//
                //first transaction
                FragmentManager fragmentManager = dummyFragment.getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_layout,PlaceholderFragment.newInstance(ADVERTISE_FRAGMENT)).commit();
                fragmentManager = dummyFragment.getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.sub_layout,PlaceholderFragment.newInstance(MAIN_FRAGMENT)).commit();

            }

    @Override
    public void OnItemClicked(String item) {

//        WishlistFragment wishlistFragment = (WishlistFragment) getSupportFragmentManager().findFragmentById(R.id.wishlist_fragment);
//        wishlistFragment.updateItem(item);\
        if (wishlistFragment != null)
            wishlistFragment.updateItem(item);
    }


    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

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
            }

            return fragment;
        }

        public PlaceholderFragment() {
        }

    }
    @Override
    public void onMenuClicked(int selector) {
        if(selector == ORDER_BUTTON_CLICKED){
            FragmentManager fragmentManager = dummyFragment.getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_layout,PlaceholderFragment.newInstance(MENU_FRAGMENT)).commit();
            fragmentManager.beginTransaction().replace(R.id.sub_layout,PlaceholderFragment.newInstance(WISHLIST_FRAGMENT)).commit();
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            Log.v("KEy Pressed", "BACK KEY");
            return true; //나중에 바꿔줄것
        }

        return super.onKeyDown(keyCode, event);
    }
}
