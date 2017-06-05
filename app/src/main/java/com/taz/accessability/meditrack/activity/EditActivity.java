package com.taz.accessability.meditrack.activity;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.taz.accessability.meditrack.R;
import com.taz.accessability.meditrack.constants.Constants;

import java.util.Date;

public class EditActivity extends AppCompatActivity {

    TextView textViewToolbarTitle;
    String toolbarTitle;
    LinearLayout LL_Parent_UserInfo;
    NestedScrollView NestedScrollview_Parent_Medicine;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        textViewToolbarTitle =(TextView)findViewById(R.id.id_toolbar_title);
        LL_Parent_UserInfo =(LinearLayout)findViewById(R.id.id_LL_UserInfo);
        NestedScrollview_Parent_Medicine =(NestedScrollView) findViewById(R.id.id_NestedScroll_Medicine);
        setViewType();


        MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner);
        spinner.setItems("daily", "weekly");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
//                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });

    }

    private void setViewType() {

        String typeUserInfoEdit = getIntent().getStringExtra(Constants.EDIT_USER_INFO);
        String typeMedicineAdd = getIntent().getStringExtra(Constants.ADD_MEDI_INFO);
        String typeMedicineEdit = getIntent().getStringExtra(Constants.EDIT_MEDI_INFO);

        if(typeUserInfoEdit != null){
            toolbarTitle = Constants.EDIT_INFO;
            VisibilityHideShow(true);
        }
        else if(typeMedicineAdd != null){
            toolbarTitle = Constants.ADD_MEDICINE;
            VisibilityHideShow(false);
        }
        else if(typeMedicineEdit != null){
            toolbarTitle = Constants.EDIT_MEDI_INFO;
            VisibilityHideShow(false);
        }
        textViewToolbarTitle.setText(toolbarTitle);
    }



    public void VisibilityHideShow(boolean UserInfo){
        if(UserInfo){
            LL_Parent_UserInfo.setVisibility(View.VISIBLE);
            NestedScrollview_Parent_Medicine.setVisibility(View.GONE);
        }
        else {
            LL_Parent_UserInfo.setVisibility(View.GONE);
            NestedScrollview_Parent_Medicine.setVisibility(View.VISIBLE);
        }

    }


    public void PickTime(View v){

        new SingleDateAndTimePickerDialog.Builder(this)
                //.bottomSheet()
                //.curved()
                //.minutesStep(15)

                //.displayHours(false)
                //.displayMinutes(false)

                .title("Set Time")
                .listener(new SingleDateAndTimePickerDialog.Listener() {
                    @Override
                    public void onDateSelected(Date date) {

                    }
                }).display();

    }
}
