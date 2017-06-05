package com.taz.accessability.meditrack.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.taz.accessability.meditrack.R;

import java.util.Date;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
    }




    public void PickTime(View v){

        new SingleDateAndTimePickerDialog.Builder(this)
                //.bottomSheet()
                //.curved()
                //.minutesStep(15)

                //.displayHours(false)
                //.displayMinutes(false)

                .title("Simple")
                .listener(new SingleDateAndTimePickerDialog.Listener() {
                    @Override
                    public void onDateSelected(Date date) {

                    }
                }).display();

    }
}
