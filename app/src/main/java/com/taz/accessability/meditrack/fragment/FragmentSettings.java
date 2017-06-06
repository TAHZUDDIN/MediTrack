package com.taz.accessability.meditrack.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taz.accessability.meditrack.R;
import com.taz.accessability.meditrack.activity.EditActivity;
import com.taz.accessability.meditrack.constants.Constants;
import com.taz.accessability.meditrack.data.UserInfoDbHandler;
import com.taz.accessability.meditrack.data.model.UserInfo;

/**
 * Created by tahzuddin on 03/06/17.
 */

public class FragmentSettings extends Fragment implements View.OnClickListener {




    CardView cardViewName,cardViewMedia, cardViewSos;
    UserInfo userInfo;
    TextView textViewName, textViewAge, textViewSosName, textViewSosNumber;

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
        userInfo =(UserInfo) UserInfoDbHandler.getInstance(getActivity()).get();

        cardViewName = (CardView)v.findViewById(R.id.id_card_view_Name);
        cardViewMedia = (CardView)v.findViewById(R.id.id_card_view_Medi);
        cardViewSos = (CardView)v.findViewById(R.id.id_card_view_sos);
        cardViewName.setOnClickListener(this);
        cardViewSos.setOnClickListener(this);
        cardViewMedia.setOnClickListener(this);

        textViewName = (TextView)v.findViewById(R.id.id_name);
        textViewAge =(TextView)v.findViewById(R.id.id_age);
        textViewSosName=(TextView)v.findViewById(R.id.id_sos_name);
        textViewSosNumber =(TextView)v.findViewById(R.id.sos_number);


        if(userInfo != null){

            textViewName.setText(userInfo.getName());
            textViewAge.setText(userInfo.getAge());
            textViewSosNumber.setText(userInfo.getSosNumber());
            textViewSosName.setText(userInfo.getSosName());

        }


        return v;
    }





    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.id_card_view_Name:
                goToEditPageEditUserInfo();
                break;

            case R.id.id_card_view_Medi:
                goToEditPageAddMedicine();
                break;

            case R.id.id_card_view_sos:
                goToEditPageEditUserInfo();
                break;
        }

    }




    public void goToEditPageEditUserInfo(){
        Intent i = new Intent(getActivity(),EditActivity.class);
        i.putExtra(Constants.EDIT_USER_INFO,Constants.EDIT_USER_INFO);
        getActivity().startActivityForResult(i, Constants.START_ACTIVITY_FOR_RESULT_EDIT_USER_INFO);
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }



    public void goToEditPageAddMedicine(){
        Intent i = new Intent(getActivity(), EditActivity.class);
        i.putExtra(Constants.ADD_MEDI_INFO,Constants.ADD_MEDI_INFO);
        getActivity().startActivityForResult(i, Constants.START_ACTIVITY_FOR_RESULT_ADD_MEDIA_INFO);
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }



   public void UpdateUi(){
       userInfo =(UserInfo) UserInfoDbHandler.getInstance(getActivity()).get();
       if(userInfo != null){
           textViewName.setText(userInfo.getName());
           textViewAge.setText(userInfo.getAge());
           textViewSosNumber.setText(userInfo.getSosNumber());
           textViewSosName.setText(userInfo.getSosName());
       }

    }








}
