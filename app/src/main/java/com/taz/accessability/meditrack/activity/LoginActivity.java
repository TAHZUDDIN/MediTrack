package com.taz.accessability.meditrack.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.taz.accessability.meditrack.R;

public class LoginActivity extends AppCompatActivity {


    private Animation inAnimation;
    LinearLayout LlMainView;
    LinearLayout LLSosView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LlMainView = (LinearLayout)findViewById(R.id.id_LL_nameAgeView);
        LLSosView = (LinearLayout)findViewById(R.id.id_LL_SOsView);

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



    public void NameAndAgeInput(View v){

        showSosView();
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
