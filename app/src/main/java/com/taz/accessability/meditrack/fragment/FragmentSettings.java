package com.taz.accessability.meditrack.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.taz.accessability.meditrack.R;
import com.taz.accessability.meditrack.activity.EditActivity;

/**
 * Created by tahzuddin on 03/06/17.
 */

public class FragmentSettings extends Fragment implements View.OnClickListener {



    RelativeLayout RL_addMedicine;

    public static FragmentSettings newInstance() {
        FragmentSettings fragment = new FragmentSettings();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        RL_addMedicine = (RelativeLayout)v.findViewById(R.id.id_RL_addMedi);
        RL_addMedicine.setOnClickListener(this);


        return v;
    }


    public void goToEditPage(View v){
        startActivity(new Intent(getActivity(), EditActivity.class));
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.id_RL_addMedi:
                startActivity(new Intent(getActivity(), EditActivity.class));
                break;
        }

    }
}
