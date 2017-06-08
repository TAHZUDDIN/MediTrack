package com.taz.accessability.meditrack.fragment;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.taz.accessability.meditrack.R;
import com.taz.accessability.meditrack.adapter.TodayMediAdapter;
import com.taz.accessability.meditrack.data.MedicinesDbHandler;
import com.taz.accessability.meditrack.data.model.Medicines;

import java.util.List;

/**
 * Created by tahzuddin on 03/06/17.
 */

public class FragmentToday  extends Fragment implements View.OnClickListener {

    List<Medicines> medicines;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerViewMovies;
    TodayMediAdapter todaysMoviesAdapter;
    RelativeLayout Rl_ProgressBar;
    CoordinatorLayout parent_coordinatorLayout;
    SwipeRefreshLayout swipeRefreshLayout;
    RelativeLayout RL_button_sos_main;



    public static FragmentToday newInstance() {
        FragmentToday fragment = new FragmentToday();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_today, container, false);

        recyclerViewMovies = (RecyclerView)v.findViewById(R.id.id_recyclerView);
        Rl_ProgressBar = (RelativeLayout)v.findViewById(R.id.id_RL_progressbar);
        Rl_ProgressBar.setVisibility(View.GONE);
        parent_coordinatorLayout =(CoordinatorLayout)v.findViewById(R.id.id_coordinatorLayout);
        swipeRefreshLayout = (SwipeRefreshLayout)v.findViewById(R.id.id_swipeRefreshLayout);
        initAdapterAndCall();
        return v;
    }




    public void initAdapterAndCall() {

        medicines = MedicinesDbHandler.getInstance(getActivity()).getAllMedicines();
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMovies.setLayoutManager(linearLayoutManager);
        todaysMoviesAdapter = new TodayMediAdapter(medicines);
//        todaysMoviesAdapter.setStartActivityListener(this);
        recyclerViewMovies.setAdapter(todaysMoviesAdapter);
        todaysMoviesAdapter.notifyDataSetChanged();
    }






    @Override
    public void onClick(View v) {

    }
}