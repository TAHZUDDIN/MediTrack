package com.taz.accessability.meditrack.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.taz.accessability.meditrack.R;
import com.taz.accessability.meditrack.data.UserInfoDbHandler;
import com.taz.accessability.meditrack.data.model.UserInfo;

public class LoginActivity extends AppCompatActivity {


    private Animation inAnimation;
    LinearLayout LlMainView;
    LinearLayout LLSosView;
    EditText userName, userAge, sosName, sosNumber;
    String UserName, UserAge, SosName, SosNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         if(checkUserInfoAvailable() != null){
             startActivity(new Intent(this,MainActivity.class));
             finish();
         }

        LlMainView = (LinearLayout)findViewById(R.id.id_LL_nameAgeView);
        LLSosView = (LinearLayout)findViewById(R.id.id_LL_SOsView);

        userName = (EditText)findViewById(R.id.user_name);
        userAge=(EditText)findViewById(R.id.user_age);
        sosName =(EditText)findViewById(R.id.sos_name);
        sosNumber =(EditText)findViewById(R.id.sos_number);

        inAnimation = AnimationUtils.loadAnimation(this, R.anim.animate_bottom_in);
        inAnimation.setDuration(1000);
        inAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                LlMainView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        LlMainView.startAnimation(inAnimation);

    }

    private UserInfo checkUserInfoAvailable() {
        UserInfo userInfo =(UserInfo)UserInfoDbHandler.getInstance(this).get();
        return userInfo;
    }


    public void NameAndAgeInput(View v){
        if(!userName.getText().toString().equals("") && !userAge.getText().toString().equals("")){
            UserName = userName.getText().toString();
            UserAge = userAge.getText().toString();
            showSosView();
        }
        else
            Toast.makeText(this, "Name and Age is Required", Toast.LENGTH_SHORT).show();

    }


    public void sosNameAndNumber(View v){
        if(!sosName.getText().toString().equals("") && !sosNumber.getText().toString().equals("")){
            SosName = sosName.getText().toString();
            SosNumber = sosNumber.getText().toString();

            proceedToStore();
        }
        else
            Toast.makeText(this, "SOS Name and Numberis Required", Toast.LENGTH_SHORT).show();
    }



    private void proceedToStore() {

        ContentValues values = new ContentValues();
        values.put(UserInfoDbHandler.COL_NAME, UserName);
        values.put(UserInfoDbHandler.COL_AGE, UserAge);
        values.put(UserInfoDbHandler.COL_SOSNAME, SosName);
        values.put(UserInfoDbHandler.COL_SOS_NUMBER, SosNumber);

        UserInfoDbHandler.getInstance(this).insertOrUpdate(values);



        startActivity(new Intent(this,MainActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();


    }


    private void showSosView() {
//        toolbar.setVisibility(View.GONE);
        Animation outAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up_out);
        outAnimation.setDuration(1000);
        inAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                LLSosView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

        });

        outAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                LlMainView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        LlMainView.startAnimation(outAnimation);
        LLSosView.startAnimation(inAnimation);
    }





    private void showMainLoginView() {

        Animation outAnimation = AnimationUtils.loadAnimation(this, R.anim.animate_bottom_out);
        outAnimation.setDuration(1000);
        Animation inAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in_top);
        inAnimation.setDuration(1000);
        inAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                LlMainView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

        });

        outAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                LLSosView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        LLSosView.startAnimation(outAnimation);
        LlMainView.startAnimation(inAnimation);
    }




    @Override
    public void onBackPressed() {
        if(LLSosView.getVisibility() == View.VISIBLE)
            showMainLoginView();
        else
            super.onBackPressed();
    }
}
