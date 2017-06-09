package com.taz.accessability.meditrack.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.skyfishjy.library.RippleBackground;
import com.taz.accessability.meditrack.R;
import com.taz.accessability.meditrack.activity.EditActivity;
import com.taz.accessability.meditrack.adapter.MedicinesAllAdapter;
import com.taz.accessability.meditrack.constants.Constants;
import com.taz.accessability.meditrack.data.MedicinesDbHandler;
import com.taz.accessability.meditrack.data.model.Medicines;
import com.taz.accessability.meditrack.listener.StartActivityListener;
import com.taz.accessability.meditrack.util.MyBounceInterpolator;
import com.taz.accessability.meditrack.util.Util;

import java.util.List;

/**
 * Created by tahzuddin on 03/06/17.
 */

public class FragmentAll  extends Fragment implements View.OnClickListener,StartActivityListener {

    List <Medicines> medicines;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerViewMovies;
    MedicinesAllAdapter moviesAdapter;
    RelativeLayout Rl_ProgressBar;
    CoordinatorLayout parent_coordinatorLayout;
    SwipeRefreshLayout swipeRefreshLayout;
    RelativeLayout RL_button_sos_main;



    public static FragmentAll newInstance() {
        FragmentAll fragment = new FragmentAll();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_all, container, false);
        startAnimationSosButton(v);

        recyclerViewMovies = (RecyclerView)v.findViewById(R.id.id_recyclerView);
        Rl_ProgressBar = (RelativeLayout)v.findViewById(R.id.id_RL_progressbar);
        Rl_ProgressBar.setVisibility(View.GONE);
        parent_coordinatorLayout =(CoordinatorLayout)v.findViewById(R.id.id_coordinatorLayout);
//        swipeRefreshLayout = (SwipeRefreshLayout)v.findViewById(R.id.id_swipeRefreshLayout);
        initAdapterAndCall();
        return v;
    }




    public void initAdapterAndCall() {

        medicines = MedicinesDbHandler.getInstance(getActivity()).getAllMedicines();
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMovies.setLayoutManager(linearLayoutManager);
        moviesAdapter = new MedicinesAllAdapter(medicines);
        moviesAdapter.setStartActivityListener(this);
        recyclerViewMovies.setAdapter(moviesAdapter);

        moviesAdapter.notifyDataSetChanged();
    }




    public void startAnimationSosButton(View v){

        RL_button_sos_main = (RelativeLayout)v.findViewById(R.id.is_button_sos_main);
        RL_button_sos_main.setOnClickListener(this);

        RippleBackground rippleBackground=(RippleBackground)v.findViewById(R.id.content);
        rippleBackground.startRippleAnimation();

        final Animation myAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.bounce);

        // Use bounce interpolator with amplitude 0.2 and frequency 10
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 10);
        myAnim.setInterpolator(interpolator);
        RL_button_sos_main.startAnimation(myAnim);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.is_button_sos_main:
                Util.makeACall(getActivity());
                break;
        }

    }

    @Override
    public void startActivityMethod(Medicines medicines) {
        Intent i = new Intent(getActivity(), EditActivity.class);
        i.putExtra(Constants.MEDICINE,medicines);
        i.putExtra(Constants.EDIT_MEDI_INFO,Constants.EDIT_MEDI_INFO);
        getActivity().startActivityForResult(i, Constants.START_ACTIVITY_FOR_RESULT_EDIT_MEDIA_INFO);
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


    public void UpdateUi(){

        initAdapterAndCall();

    }
}
