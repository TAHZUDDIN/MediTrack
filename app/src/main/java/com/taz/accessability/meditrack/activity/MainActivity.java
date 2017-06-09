package com.taz.accessability.meditrack.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabSelectListener;
import com.skyfishjy.library.RippleBackground;
import com.taz.accessability.meditrack.R;
import com.taz.accessability.meditrack.constants.Constants;
import com.taz.accessability.meditrack.fragment.FragmentAll;
import com.taz.accessability.meditrack.fragment.FragmentSettings;
import com.taz.accessability.meditrack.fragment.FragmentToday;
import com.taz.accessability.meditrack.util.MyBounceInterpolator;
import com.taz.accessability.meditrack.util.Util;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    BottomBar bottomBar;
    boolean BackButtonPressed = false;
    TextView textViewToolbarTitle;
    String toolbarTitle = Constants.TODAYS_MEDICINES;
    FragmentSettings fragmentSettings;
    FragmentAll  fragmentAll;
    RelativeLayout RL_button_sos_main,
                   RL_button_sos_main_search ;
    Toolbar toolbar ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.toolbar);

        RippleBackground rippleBackground=(RippleBackground)findViewById(R.id.content);
        rippleBackground.startRippleAnimation();


        RippleBackground rippleBackground_search=(RippleBackground)findViewById(R.id.content_search);
        rippleBackground_search.startRippleAnimation();

        RL_button_sos_main = (RelativeLayout) findViewById(R.id.is_button_sos_main);
        RL_button_sos_main.setOnClickListener(this);
//        startAnimationButton();


        RL_button_sos_main_search = (RelativeLayout) findViewById(R.id.is_button_sos_main_search);
        RL_button_sos_main_search.setOnClickListener(this);
        startAnimationButton();
        
        textViewToolbarTitle =(TextView)findViewById(R.id.id_toolbar_title);
        bottomBar = (BottomBar) findViewById(R.id.bottom_Bar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {




                if(BackButtonPressed == true){
                    return;
                }


                Fragment selectedFragment = null;
                switch (tabId) {
                    case R.id.tab_today:
                        selectedFragment = FragmentToday.newInstance();
                        toolbar.setVisibility(View.VISIBLE);
                        toolbarTitle = Constants.TODAYS_MEDICINES;
                        break;
                    case R.id.tab_all:
                        fragmentAll = FragmentAll.newInstance();
                        selectedFragment = fragmentAll;
                        toolbar.setVisibility(View.GONE);
                        toolbarTitle = Constants.ALL_MEDICINE;
                        break;
                    case R.id.tab_settings:
                        fragmentSettings = FragmentSettings.newInstance();
                        selectedFragment = fragmentSettings;
                        toolbar.setVisibility(View.VISIBLE);
                        toolbarTitle = Constants.SETTINGS;
                        break;
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.contentContainer, selectedFragment , Integer.toString(getFragmentCount())).addToBackStack(null);
                transaction.commit();
                textViewToolbarTitle.setText(toolbarTitle);

            }
        });


        for (int i = 0; i < bottomBar.getTabCount(); i++) {
            BottomBarTab tab = bottomBar.getTabAtPosition(i);
            tab.setGravity(Gravity.CENTER);

            View icon = tab.findViewById(com.roughike.bottombar.R.id.bb_bottom_bar_icon);
            // the paddingTop will be modified when select/deselect,
            // so, in order to make the icon always center in tab,
            // we need set the paddingBottom equals paddingTop
            icon.setPadding(0, icon.getPaddingTop(), 0, icon.getPaddingTop());

            View title = tab.findViewById(com.roughike.bottombar.R.id.bb_bottom_bar_title);
            title.setVisibility(View.GONE);
        }




        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.contentContainer, FragmentToday.newInstance());
        transaction.commit();
        textViewToolbarTitle.setText(toolbarTitle);

    }



    public void startAnimationButton() {
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);

        // Use bounce interpolator with amplitude 0.2 and frequency 10
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 10);
        myAnim.setInterpolator(interpolator);
        RL_button_sos_main.startAnimation(myAnim);

        RL_button_sos_main_search.startAnimation(myAnim);
    }


    @Override
    public void onBackPressed() {
        BackButtonPressed = true;
        if(getSupportFragmentManager().getBackStackEntryCount() > 1){
            getSupportFragmentManager().popBackStack();
            ChechkActiveFragment();
        }
        else
            finish();
    }



    public void ChechkActiveFragment(){

        Fragment f = getFragmentAt(getFragmentCount() - 2);
        if (f instanceof FragmentToday ){
            bottomBar.selectTabAtPosition(0);
            toolbar.setVisibility(View.VISIBLE);
            textViewToolbarTitle.setText(Constants.TODAYS_MEDICINES);
        }

        else if(f instanceof FragmentAll ){
            bottomBar.selectTabAtPosition(1);
            toolbar.setVisibility(View.GONE);
            textViewToolbarTitle.setText(Constants.ALL_MEDICINE);
        }
        else if(f instanceof FragmentSettings){
            toolbar.setVisibility(View.VISIBLE);
            bottomBar.selectTabAtPosition(2);
            textViewToolbarTitle.setText(Constants.SETTINGS);
        }
        BackButtonPressed = false;
    }


    // Method to find Second Fragment in
    private Fragment getFragmentAt(int index) {
        return getFragmentCount() > 0 ? getSupportFragmentManager().findFragmentByTag(Integer.toString(index)) : null;
    }
    protected int getFragmentCount() {
        return getSupportFragmentManager().getBackStackEntryCount();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode== Constants.START_ACTIVITY_FOR_RESULT_EDIT_USER_INFO)
        {
            if(fragmentSettings != null)
                fragmentSettings.UpdateUi();

        }
        else if(requestCode== Constants.START_ACTIVITY_FOR_RESULT_EDIT_MEDIA_INFO){

            if(fragmentAll != null)
                fragmentAll.UpdateUi();

        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.is_button_sos_main:
                Util.makeACall(MainActivity.this);
                break;

        }
    }
}
