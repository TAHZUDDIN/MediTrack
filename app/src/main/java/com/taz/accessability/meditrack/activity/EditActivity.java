package com.taz.accessability.meditrack.activity;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.taz.accessability.meditrack.R;
import com.taz.accessability.meditrack.constants.Constants;
import com.taz.accessability.meditrack.data.UserInfoDbHandler;
import com.taz.accessability.meditrack.data.model.UserInfo;
import com.taz.accessability.meditrack.util.MyBounceInterpolator;
import com.taz.accessability.meditrack.util.Util;

import org.w3c.dom.Text;

import java.util.Date;

public class EditActivity extends AppCompatActivity implements TextWatcher,View.OnClickListener{

    TextView textViewToolbarTitle;
    String toolbarTitle;
    LinearLayout LL_Parent_UserInfo;
    NestedScrollView NestedScrollview_Parent_Medicine;

    UserInfo userInfo;
    TextInputLayout textInputLayoutName,
                    textInputLayoutAge,
                    textInputLayoutSosName,
                    textInputLayoutSosNumber;

    TextView textViewSvaeUserUpdate;
    TextView textView_button_sos_main;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        textView_button_sos_main = (TextView)findViewById(R.id.is_button_sos_main);
        textView_button_sos_main.setOnClickListener(this);
        startAnimationButton();

        textViewToolbarTitle =(TextView)findViewById(R.id.id_toolbar_title);
        LL_Parent_UserInfo =(LinearLayout)findViewById(R.id.id_LL_UserInfo);
        NestedScrollview_Parent_Medicine =(NestedScrollView) findViewById(R.id.id_NestedScroll_Medicine);
        setViewType();

        userInfo =(UserInfo) UserInfoDbHandler.getInstance(this).get();
        textInputLayoutName = (TextInputLayout)findViewById(R.id.id_text_input_Name);
        textInputLayoutAge = (TextInputLayout)findViewById(R.id.id_text_input_Age);
        textInputLayoutSosName = (TextInputLayout)findViewById(R.id.id_text_input_sosName);
        textInputLayoutSosNumber = (TextInputLayout)findViewById(R.id.id_text_input_sosNumber);
        textViewSvaeUserUpdate = (TextView)findViewById(R.id.id_textView_Save_UserUpdate);
        textViewSvaeUserUpdate.setClickable(false);
        textViewSvaeUserUpdate.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        setTextUserInfo();





        MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner);
        spinner.setItems("daily", "weekly");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
//                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });

    }



    private void setTextUserInfo() {
        if(userInfo != null){
            textInputLayoutName.getEditText().setText(userInfo.getName());
            textInputLayoutAge.getEditText().setText(userInfo.getAge());
            textInputLayoutSosName.getEditText().setText(userInfo.getSosName());
            textInputLayoutSosNumber.getEditText().setText(userInfo.getSosNumber());

            textInputLayoutName.getEditText().addTextChangedListener(this);
            textInputLayoutAge.getEditText().addTextChangedListener(this);
            textInputLayoutSosName.getEditText().addTextChangedListener(this);
            textInputLayoutSosNumber.getEditText().addTextChangedListener(this);

        }
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


    public void updateUserInfo(View v){

        ContentValues values = new ContentValues();
        values.put(UserInfoDbHandler.COL_NAME, textInputLayoutName.getEditText().getText().toString());
        values.put(UserInfoDbHandler.COL_AGE, textInputLayoutAge.getEditText().getText().toString());
        values.put(UserInfoDbHandler.COL_SOSNAME, textInputLayoutSosName.getEditText().getText().toString());
        values.put(UserInfoDbHandler.COL_SOS_NUMBER, textInputLayoutSosNumber.getEditText().getText().toString());

        UserInfoDbHandler.getInstance(this).insertOrUpdate(values);

        Util.ToastDisplay(this, "Updated user info");

        setResult(Constants.START_ACTIVITY_FOR_RESULT_EDIT_USER_INFO);
        finish();

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.is_button_sos_main:
                Util.makeACall(EditActivity.this);
                break;
        }
    }


    public void startAnimationButton() {
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);

        // Use bounce interpolator with amplitude 0.2 and frequency 10
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 10);
        myAnim.setInterpolator(interpolator);
        textView_button_sos_main.startAnimation(myAnim);
    }



    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        textViewSvaeUserUpdate.setClickable(true);
        textViewSvaeUserUpdate.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
