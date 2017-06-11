package com.taz.accessability.meditrack.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.taz.accessability.meditrack.R;
import com.taz.accessability.meditrack.app.MyApplication;

/**
 * Created by tahzuddin on 11/06/17.
 */

public class CursorAdapterSimple extends SimpleCursorAdapter {

    public CursorAdapterSimple(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //get reference to the row
        View view = super.getView(position, convertView, parent);
//        view.setBackgroundColor(Color.rgb(238, 233, 233));
        view.setBackgroundColor(MyApplication.getInstance().getResources().getColor(R.color.colorPrimary));


        TextView textView = (TextView)view;
        textView.setTextColor(MyApplication.getInstance().getResources().getColor(android.R.color.white));

        return view;


//        return super.getView(position, convertView, parent);
    }
}
