package com.taz.accessability.meditrack.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.taz.accessability.meditrack.R;

public class ContetProviderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contet_provider);
    }





    public void onClickAddName(View view) {
        // Add a new student record
//        ContentValues values = new ContentValues();
//        values.put(AppContentProvider.NAME,
//                ((EditText)findViewById(R.id.editText2)).getText().toString());
//
//        values.put(AppContentProvider.GRADE,
//                ((EditText)findViewById(R.id.editText3)).getText().toString());
//
//        Uri uri = getContentResolver().insert(
//                AppContentProvider.CONTENT_URI, values);
//
//        Toast.makeText(getBaseContext(),
//                uri.toString(), Toast.LENGTH_LONG).show();
    }
    public void onClickRetrieveStudents(View view) {
        // Retrieve student records
//        String URL = "content://com.taz.accessability.meditrack.AppContentProvider";
//
//        Uri students = Uri.parse(URL);
//        Cursor c = managedQuery(students, null, null, null, "name");
//
//        if (c.moveToFirst()) {
//            do{
//                Toast.makeText(this,
//                        c.getString(c.getColumnIndex(AppContentProvider._ID)) +
//                                ", " +  c.getString(c.getColumnIndex( AppContentProvider.NAME)) +
//                                ", " + c.getString(c.getColumnIndex( AppContentProvider.GRADE)),
//                        Toast.LENGTH_SHORT).show();
//            } while (c.moveToNext());
//        }
    }





}
