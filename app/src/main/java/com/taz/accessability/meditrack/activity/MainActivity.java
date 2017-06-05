package com.taz.accessability.meditrack.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.taz.accessability.meditrack.R;
import com.taz.accessability.meditrack.constants.Constants;
import com.taz.accessability.meditrack.fragment.FragmentAll;
import com.taz.accessability.meditrack.fragment.FragmentSettings;
import com.taz.accessability.meditrack.fragment.FragmentToday;

public class MainActivity extends AppCompatActivity {


    String Fragment_Today = "FragmentToday";
    String Fragment_All = "FragmentAll";
    String Fragment_Settings = "FragmentSettings";
    BottomBar bottomBar;
    boolean BackButtonPressed = false;
    TextView textViewToolbarTitle;
    String toolbarTitle = Constants.TODAYS_MEDICINES;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
                        toolbarTitle = Constants.TODAYS_MEDICINES;
                        break;
                    case R.id.tab_all:
                        selectedFragment = FragmentAll.newInstance();
                        toolbarTitle = Constants.ALL_MEDICINE;
                        break;
                    case R.id.tab_settings:
                        selectedFragment = FragmentSettings.newInstance();
                        toolbarTitle = Constants.SETTINGS;
                        break;
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.contentContainer, selectedFragment , Integer.toString(getFragmentCount())).addToBackStack(null);
                transaction.commit();
                textViewToolbarTitle.setText(toolbarTitle);
            }
        });

        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.contentContainer, FragmentToday.newInstance());
        transaction.commit();
        textViewToolbarTitle.setText(toolbarTitle);

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
            textViewToolbarTitle.setText(Constants.TODAYS_MEDICINES);
        }

        else if(f instanceof FragmentAll ){
            bottomBar.selectTabAtPosition(1);
            textViewToolbarTitle.setText(Constants.ALL_MEDICINE);
        }
        else if(f instanceof FragmentSettings){
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
}
