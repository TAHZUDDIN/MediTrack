package com.taz.accessability.meditrack.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taz.accessability.meditrack.R;

/**
 * Created by tahzuddin on 03/06/17.
 */

public class FragmentToday  extends Fragment {



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
        return inflater.inflate(R.layout.fragment_today, container, false);
    }




}
