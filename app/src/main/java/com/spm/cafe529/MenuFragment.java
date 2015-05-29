package com.spm.cafe529;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.spm.cafe529.Database.Database;
import com.spm.cafe529.Struct.Menu;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by 성호 on 2015-04-07.
 */
public class MenuFragment extends MainActivity.PlaceholderFragment implements View.OnClickListener{
    private static final int ROW_COUNT = 4;
    private static final int PADDING = 2;

    private GridLayout menuGrid1;

    private Database mHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ArrayList<Menu> menuList1;

        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        mHelper = new Database(rootView.getContext());

        menuGrid1 = (GridLayout) rootView.findViewById(R.id.menu_grid1);

        if (true) { // 초기 데이터가 없을 시
            getInitData();
        }
        menuList1 = getMenuList("Coffee");
        if (setGridLayout(menuGrid1, menuList1.size())) {
            Log.d("Set menuGrid1 success", "Menu list Size = " + menuList1.size());
            fillGridLayout(menuGrid1, menuList1, rootView.getContext());
        }
        else
            Log.d ("Set menuGrid1 Failed", "");

        return rootView;
    }

    private ArrayList<Menu> getMenuList(String column) {


        SQLiteDatabase db = mHelper.getReadableDatabase();
        return mHelper.getMenu(db, column);
    }

    private boolean setGridLayout (GridLayout gl, int count) {
        if (gl != null && count > 0) {
            gl.setRowCount((count / ROW_COUNT) + 1);
            gl.setColumnCount(ROW_COUNT);
            return true;
        }
        return false;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void fillGridLayout (GridLayout gl, ArrayList<Menu> list, Context context) {
        LinearLayout ll;
        TextView image, name, price;

        int count = list.size();
        for (int i = 0; i < count; i++) {
            ll = new LinearLayout(context);
            ll.setOrientation(LinearLayout.VERTICAL);
            ll.setPadding(PADDING, PADDING, PADDING, PADDING);

            //bitmap
            Log.v("path", list.get(i).getImagepath());
            Bitmap bitmap = BitmapFactory.decodeFile(list.get(i).getImagepath());
            Drawable drawable = new BitmapDrawable(getResources(),bitmap);
            image = new TextView(context);
            name = new TextView(context);
            price = new TextView(context);

            image.setBackground(drawable);
            name.setText(list.get(i).getName());
            price.setText(""+list.get(i).getPrice());

            ll.addView(image,200,200);
            ll.addView(name);
            ll.addView(price);

            ll.setTag(name.getText());
            ll.setOnClickListener(this);

            gl.addView(ll);
        }
    }

    private void getInitData () {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        mHelper.putMenu(db, "Americano", 3000, "/storage/emulated/0/Download/image/Americano.jpeg", "Coffee");
        mHelper.putMenu(db, "Latte", 4000, "/storage/emulated/0/Download/image/Latte.jpeg","Coffee");
        mHelper.putMenu(db, "Milk", 2000, "/storage/emulated/0/Download/image/Milk.jpeg","Coffee");
    }

    @Override
    public void onClick(View v) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        // DB 접근해서 데이터 가져옴.
        Log.d(v.getTag().toString(), "" + mHelper.getPrice(db, v.getTag().toString()));

    }
}
