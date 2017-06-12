package com.taz.accessability.meditrack.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.skyfishjy.library.RippleBackground;
import com.taz.accessability.meditrack.R;
import com.taz.accessability.meditrack.adapter.SearchResultAdapter;
import com.taz.accessability.meditrack.constants.Constants;
import com.taz.accessability.meditrack.data.MedicinesDbHandler;
import com.taz.accessability.meditrack.data.model.Medicines;
import com.taz.accessability.meditrack.listener.StartActivityListener;
import com.taz.accessability.meditrack.util.MyBounceInterpolator;
import com.taz.accessability.meditrack.util.Util;

import java.util.List;

/**
 * Created by tahzuddin on 11/06/17.
 */

public class SearchActivity extends AppCompatActivity implements View.OnClickListener,StartActivityListener {


    TextView textViewToolbarTitle;
    List<Medicines> medicines;
    RecyclerView recyclerViewMovies;
    LinearLayoutManager linearLayoutManager;
    SearchResultAdapter searchResultAdapter;
    RelativeLayout RL_button_sos_main,
                   RL_button_sos_main_search;

    String nameLike;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        textViewToolbarTitle = (TextView)findViewById(R.id.id_toolbar_title);
        textViewToolbarTitle.setText("Search Result");

        RL_button_sos_main = (RelativeLayout) findViewById(R.id.is_button_sos_main);
        RL_button_sos_main.setOnClickListener(this);
        startAnimationButton();




//        initAdapterAndCall();

        handleIntent(getIntent());
    }

    public void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    public void onListItemClick(ListView l,
                                View v, int position, long id) {
        // call detail activity for clicked entry
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query =
                    intent.getStringExtra(SearchManager.QUERY);
            doSearch(query);
        }
        else if(intent.getStringExtra(Constants.SEARCH_SUGGESTION_SELECTION) != null){

            nameLike = getIntent().getStringExtra(Constants.SEARCH_SUGGESTION_SELECTION);
            doSearch(nameLike);
        }
    }



    private void doSearch(String queryStr) {
//        Util.ToastDisplay(this, "queryStr "+queryStr);
        nameLike = queryStr;
        initAdapterAndCall();
        // get a Cursor, prepare the ListAdapter
        // and set it
    }



    public void initAdapterAndCall() {
        recyclerViewMovies = (RecyclerView)findViewById(R.id.id_recyclerView);
        medicines = MedicinesDbHandler.getInstance(this).getAllMedicinesLIKE(nameLike);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMovies.setLayoutManager(linearLayoutManager);
        searchResultAdapter = new SearchResultAdapter(medicines);
        searchResultAdapter.setStartActivityListener(this);
        recyclerViewMovies.setAdapter(searchResultAdapter);

        searchResultAdapter.notifyDataSetChanged();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.is_button_sos_main:
                Util.makeACall(this);
                break;
        }
    }




    @Override
    public void startActivityMethod(Medicines medicines) {
        Intent i = new Intent(this, EditActivity.class);
        i.putExtra(Constants.MEDICINE,medicines);
        i.putExtra(Constants.EDIT_MEDI_INFO,Constants.EDIT_MEDI_INFO);
        startActivityForResult(i, Constants.START_ACTIVITY_FOR_RESULT_EDIT_MEDIA_INFO);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
         if(requestCode== Constants.START_ACTIVITY_FOR_RESULT_EDIT_MEDIA_INFO){
             UpdateUi();
         }
    }



    public void UpdateUi(){
        initAdapterAndCall();
    }



    public void startAnimationButton() {
        RippleBackground rippleBackground_search=(RippleBackground)findViewById(R.id.content);
        rippleBackground_search.startRippleAnimation();
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        // Use bounce interpolator with amplitude 0.2 and frequency 10
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 10);
        myAnim.setInterpolator(interpolator);
        RL_button_sos_main.startAnimation(myAnim);
    }


}
