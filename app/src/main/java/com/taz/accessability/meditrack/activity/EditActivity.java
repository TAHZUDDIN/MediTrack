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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.shawnlin.numberpicker.NumberPicker;
import com.skyfishjy.library.RippleBackground;
import com.taz.accessability.meditrack.R;
import com.taz.accessability.meditrack.constants.Constants;
import com.taz.accessability.meditrack.data.MedicinesDbHandler;
import com.taz.accessability.meditrack.data.TimeOfDoseDbHandler;
import com.taz.accessability.meditrack.data.UserInfoDbHandler;
import com.taz.accessability.meditrack.data.model.UserInfo;
import com.taz.accessability.meditrack.util.MyBounceInterpolator;
import com.taz.accessability.meditrack.util.Util;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import static com.taz.accessability.meditrack.R.id.id_textView_PickTime_one;


public class EditActivity extends AppCompatActivity implements TextWatcher,
        View.OnClickListener,NumberPicker.OnValueChangeListener{

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

    NumberPicker numberPickerQuantityAtaTime ,
                 numberPickerDosesPerDay,
                 numberPickerMedicinesPurchased   ;


    MaterialSpinner spinner;

    TextView textViewPickTimeOne,
            textViewPickTimeTwo,
            textViewPickTimeThree;


    SingleDateAndTimePicker singleDateAndTimePickerOne,
            singleDateAndTimePickerTwo,
            singleDateAndTimePickerThree;

    TextView textViewSaveMedicine;

    TextInputLayout textInputLayoutMedicineName;

    String MedicineName;
    String doseTimeOne,
            doseTimeTwo,
            doseTimeThree;

    int saveConditions = 0;
    String dosesPerDay;
    String quantity_atatime;
    String medicinesPurchased;
    String diseFrequency;

    RelativeLayout RL_button_sos_main;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        textViewSaveMedicine = (TextView)findViewById(R.id.id_TV_save_Medicine);
//        textViewSaveMedicine.setClickable(false);
        textViewSaveMedicine.setOnClickListener(this);


        textInputLayoutMedicineName=(TextInputLayout)findViewById(R.id.id_medicine_name_text_input);


        numberPickerQuantityAtaTime =(NumberPicker)findViewById(R.id.id_number_picker_qty_ata_time);
        numberPickerDosesPerDay =(NumberPicker)findViewById(R.id.id_number_picker_dosePerDay);
        numberPickerDosesPerDay.setOnValueChangedListener(this);
        numberPickerMedicinesPurchased =(NumberPicker)findViewById(R.id.id_number_picker_medicesPurchaded);


//        singleDateAndTimePickerOne = (SingleDateAndTimePicker)findViewById(R.id.id_SingleDateAndTimePicker_one);
//        singleDateAndTimePickerTwo = (SingleDateAndTimePicker)findViewById(R.id.id_SingleDateAndTimePicker_two);
//        singleDateAndTimePickerThree = (SingleDateAndTimePicker)findViewById(R.id.id_SingleDateAndTimePicker_three);


        textViewPickTimeOne = (TextView)findViewById(id_textView_PickTime_one);
        textViewPickTimeOne.setOnClickListener(this);
        textViewPickTimeTwo = (TextView)findViewById(R.id.id_textView_PickTime_Two);
        textViewPickTimeTwo.setOnClickListener(this);
        textViewPickTimeThree = (TextView)findViewById(R.id.id_textView_PickTime_Three);
        textViewPickTimeThree.setOnClickListener(this);




        RL_button_sos_main = (RelativeLayout) findViewById(R.id.is_button_sos_main);
        RL_button_sos_main.setOnClickListener(this);
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
        setTextUserInfo();





        spinner = (MaterialSpinner) findViewById(R.id.id_spinner_dos_frequency);
        spinner.setItems("daily", "weekly");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
//                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });


        showHideTimePicker(1);

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

            case R.id.id_textView_PickTime_one:
                showTimePopUp("one");

                break;

            case R.id.id_textView_PickTime_Two:
                showTimePopUp("two");
                break;

            case R.id.id_textView_PickTime_Three:
                showTimePopUp("three");
                break;

            case R.id.id_TV_save_Medicine:
                saveMedicine();
                break;
        }
    }




    public void startAnimationButton() {

        RippleBackground rippleBackground=(RippleBackground)findViewById(R.id.content);
        rippleBackground.startRippleAnimation();

        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);

        // Use bounce interpolator with amplitude 0.2 and frequency 10
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 10);
        myAnim.setInterpolator(interpolator);
        RL_button_sos_main.startAnimation(myAnim);
    }



    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        textViewSvaeUserUpdate.setClickable(true);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }



    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        showHideTimePicker(newVal);
    }



    public void showHideTimePicker(int newVl_number_Picker){

        switch (newVl_number_Picker){
            case  1:
                textViewPickTimeOne.setVisibility(View.VISIBLE);
                textViewPickTimeTwo.setVisibility(View.GONE);
                textViewPickTimeThree.setVisibility(View.GONE);
                break;
            case  2:
                textViewPickTimeOne.setVisibility(View.VISIBLE);
                textViewPickTimeTwo.setVisibility(View.VISIBLE);
                textViewPickTimeThree.setVisibility(View.GONE);
                break;
            case  3:
                textViewPickTimeOne.setVisibility(View.VISIBLE);
                textViewPickTimeTwo.setVisibility(View.VISIBLE);
                textViewPickTimeThree.setVisibility(View.VISIBLE);
                break;
        }

    }



    public void showTimePopUp(final String timeOrder){

        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                        if(timeOrder.equals("one"))
                            textViewPickTimeOne.setText(hourOfDay+":"+minute);
                        if(timeOrder.equals("two"))
                            textViewPickTimeTwo.setText(hourOfDay+":"+minute);
                        if(timeOrder.equals("three"))
                            textViewPickTimeThree.setText(hourOfDay+":"+minute);

                    }
                },
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        );
        tpd.show(getFragmentManager(),"TimePickerDialog");
    }



    public void saveMedicine(){
        doseTimeOne = textViewPickTimeOne.getText().toString();
        doseTimeTwo = textViewPickTimeTwo.getText().toString();
        doseTimeThree = textViewPickTimeThree.getText().toString();
        MedicineName = textInputLayoutMedicineName.getEditText().getText().toString();

        switch (numberPickerDosesPerDay.getValue()){

            case 1:
                if(!MedicineName.equals("") && !doseTimeOne.equals("Pick Time First"))
                    Save(1);
               break;
            case 2:
                if(!MedicineName.equals("")  && !doseTimeOne.equals("Pick Time First") && !doseTimeTwo.equals("Pick Time Second"))
                    Save(2);
                break;

            case 3:
                if(!MedicineName.equals("")  && !doseTimeOne.equals("Pick Time First") && !doseTimeTwo.equals("Pick Time Second")&&
                        !doseTimeThree.equals("Pick Time Third"))
                    Save(3);
                break;
        }
    }



    public void Save(int condition){



        MedicineName = textInputLayoutMedicineName.getEditText().getText().toString();
        dosesPerDay= String.valueOf(numberPickerDosesPerDay.getValue());
        quantity_atatime = String.valueOf(numberPickerQuantityAtaTime.getValue());
        medicinesPurchased = String.valueOf(numberPickerMedicinesPurchased.getValue());
        diseFrequency = spinner.getText().toString();


        ContentValues values = new ContentValues();
        values.put(MedicinesDbHandler.COL_NAME, MedicineName);
        values.put(MedicinesDbHandler.COL_DOS_FREQUENCY, diseFrequency);
        values.put(MedicinesDbHandler.COL_DOS_QUANTITY, quantity_atatime);
        values.put(MedicinesDbHandler.COL_DOSES_PERDAY, dosesPerDay);
        values.put(MedicinesDbHandler.COL_NUMBER_PURCHASED, medicinesPurchased);

        long returnValue =MedicinesDbHandler.getInstance(this).insert(values);
        Util.ToastDisplay(this, "returnValue "+returnValue);
        if(returnValue != 5 && returnValue != -1)
            switch (condition){
                case 1:
                    ContentValues cv = getContentValuesTimeOfDose(returnValue,doseTimeOne);
                    long returnTimeDb =TimeOfDoseDbHandler.getInstance(this).insert(cv);
                    Util.ToastDisplay(this, "returnValue DOSE "+returnTimeDb);
                    break;

                case 2:
                    ContentValues cv_21 = getContentValuesTimeOfDose(returnValue,doseTimeOne);
                    long returnTimeDb_21 =TimeOfDoseDbHandler.getInstance(this).insert(cv_21);

                    ContentValues cv_22 = getContentValuesTimeOfDose(returnValue,doseTimeTwo);
                    long returnTimeDb_22 =TimeOfDoseDbHandler.getInstance(this).insert(cv_22);
                    Util.ToastDisplay(this, "returnTimeDb_21 "+returnTimeDb_21);
                    Util.ToastDisplay(this, "returnTimeDb_22 "+returnTimeDb_22);
                    break;

                case 3:
                    ContentValues cv_31= getContentValuesTimeOfDose(returnValue,doseTimeOne);
                    long returnTimeDb_31 =TimeOfDoseDbHandler.getInstance(this).insert(cv_31);

                    ContentValues cv_32 = getContentValuesTimeOfDose(returnValue,doseTimeTwo);
                    long returnTimeDb_32 =TimeOfDoseDbHandler.getInstance(this).insert(cv_32);

                    ContentValues cv_33 = getContentValuesTimeOfDose(returnValue,doseTimeThree);
                    long returnTimeDb_33 =TimeOfDoseDbHandler.getInstance(this).insert(cv_33);
                    Util.ToastDisplay(this, "returnTimeDb_31"+returnTimeDb_31);
                    Util.ToastDisplay(this, "returnTimeDb_32"+returnTimeDb_32);
                    Util.ToastDisplay(this, "returnTimeDb_33"+returnTimeDb_33);
                    break;

            }


        setResult(Constants.START_ACTIVITY_FOR_RESULT_ADD_MEDIA_INFO);
        finish();
    }



    public ContentValues getContentValuesTimeOfDose(long medicineId, String doseTime){

        ContentValues values = new ContentValues();
        values.put(TimeOfDoseDbHandler.COL_MEDICINE_ID, medicineId);
        values.put(TimeOfDoseDbHandler.COL_DODE_TIME, doseTime);
        return values;

    }



}
