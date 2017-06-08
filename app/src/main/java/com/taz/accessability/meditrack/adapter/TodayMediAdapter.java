package com.taz.accessability.meditrack.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taz.accessability.meditrack.R;
import com.taz.accessability.meditrack.app.MyApplication;
import com.taz.accessability.meditrack.data.TimeOfDoseDbHandler;
import com.taz.accessability.meditrack.data.model.Medicines;
import com.taz.accessability.meditrack.data.model.TimeOfDoses;
import com.taz.accessability.meditrack.listener.StartActivityListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tahzuddin on 04/06/17.
 */

public class TodayMediAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {



    private static final int TYPE_DEFAULT = 0;
    // IF the view under inflation and population is header or Item
    private static final int TYPE_NO_ITEM = 1;
    Context context;

    List<Medicines> medicines;


    public StartActivityListener startActivityListener;


    public void setStartActivityListener(StartActivityListener startActivityListener) {
        this.startActivityListener = startActivityListener;
    }



    public TodayMediAdapter(List<Medicines> medicines) {
        this.medicines = new ArrayList<>();
        this.medicines = medicines;
        this.context = MyApplication.getInstance();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType){
            case TYPE_DEFAULT:
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_medicines_layout, parent, false); //Inflating the layout
                ViewHolder vhItem = new ViewHolder(v); //Creating ViewHolder and passing the object of type view
                return vhItem;

            case TYPE_NO_ITEM:
                View v_no_item = LayoutInflater.from(parent.getContext()).inflate(R.layout.no_item, parent, false); //Inflating the layout
                ViewHolderNoItem vhNoItem = new ViewHolderNoItem(v_no_item); //Creating ViewHolder and passing the object of type view
                return vhNoItem;
        }
        return null;
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()){
            case TYPE_DEFAULT:
                String times = "";
                Medicines medicine = medicines.get(position);
                List<TimeOfDoses> timeOfDoses = TimeOfDoseDbHandler.getInstance(context).getAllTimesOfDoses(medicine);
                for(TimeOfDoses td: timeOfDoses){
                    times += td.getDosetime()+", ";
                }
                TodayMediAdapter.ViewHolder vhDefault = (TodayMediAdapter.ViewHolder)holder;
                vhDefault.parentLayout.setTag(medicine);
                vhDefault.textViewDosesPurchased.setText(medicine.getNo_dose_purchased());
                vhDefault.textViewdosFrequency.setText(medicine.getDose_frequency());
                vhDefault.textViewdosPerday.setText(medicine.getDoses_perday());
                vhDefault.textViewName.setText(medicine.getName());
                vhDefault.textViewQtyAtAtime.setText(medicine.getDose_quantity());
                vhDefault.textViewtime.setText(times);
                break;


            case TYPE_NO_ITEM:
                break;
        }

    }




    @Override
    public int getItemCount() {
        if(medicines.size() ==0)
            return 1;
        else
            return medicines.size();
    }


    @Override
    public int getItemViewType(int position) {
        return medicines.size()==0 ? TYPE_NO_ITEM : TYPE_DEFAULT;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_LinLay_parent_inflatingMedicines:
                startActivityListener.startActivityMethod(((Medicines)view.getTag()));
                break;
            default:
                break;
        }
    }


    public class ViewHolderNoItem extends RecyclerView.ViewHolder{

        public ViewHolderNoItem(View itemView) {
            super(itemView);
        }
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout parentLayout;
        TextView textViewName,
                textViewdosFrequency,
                textViewQtyAtAtime,
                textViewdosPerday,
                textViewtime,
                textViewDosesPurchased
                        ;

        public ViewHolder(View itemView) {
            super(itemView);
            parentLayout = (LinearLayout) itemView.findViewById(R.id.id_LinLay_parent_inflatingMedicines);
//            parentLayout.setOnClickListener(MedicinesAllAdapter.this);

            textViewName = (TextView)itemView.findViewById(R.id.id_name);
            textViewdosFrequency = (TextView)itemView.findViewById(R.id.id_DoseFrequency);
            textViewQtyAtAtime = (TextView)itemView.findViewById(R.id.id_qty_at_atime);
            textViewdosPerday = (TextView)itemView.findViewById(R.id.id_dose_perday);
            textViewtime = (TextView)itemView.findViewById(R.id.id_times);
            textViewDosesPurchased = (TextView)itemView.findViewById(R.id.id_medicine_purchased);

        }
    }


}