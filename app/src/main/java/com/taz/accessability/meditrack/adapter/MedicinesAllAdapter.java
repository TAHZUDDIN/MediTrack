package com.taz.accessability.meditrack.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taz.accessability.meditrack.R;
import com.taz.accessability.meditrack.data.model.Medicines;


import java.util.ArrayList;
import java.util.List;

import static android.R.attr.description;


public class MedicinesAllAdapter extends RecyclerView.Adapter<MedicinesAllAdapter.ViewHolder> implements View.OnClickListener {


    List<Medicines> medicines;
//    public StartActivityListener startActivityListener;
//
//
//    public void setStartActivityListener(StartActivityListener startActivityListener) {
//        this.startActivityListener = startActivityListener;
//    }


    public MedicinesAllAdapter(List<Medicines> medicines) {
        this.medicines = new ArrayList<>();
        this.medicines = medicines;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_medicines_layout, parent, false); //Inflating the layout
        ViewHolder vhItem = new ViewHolder(v); //Creating ViewHolder and passing the object of type view
        return vhItem;
    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Medicines medicine = medicines.get(position);


        holder.parentLayout.setTag(medicine);
        holder.textViewDosesPurchased.setText(medicine.getNo_dose_purchased());
        holder.textViewdosFrequency.setText(medicine.getDose_frequency());
        holder.textViewdosPerday.setText(medicine.getDoses_perday());
        holder.textViewName.setText(medicine.getName());
        holder.textViewQtyAtAtime.setText(medicine.getDose_quantity());



//        holder.title.setText(movie.getTitle());
//        holder.description.setText(movie.getOverview());
//        holder.parentLayout.setTag(movie);
    }


    @Override
    public int getItemCount() {
        return medicines.size();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_LinLay_parent_inflatingMedicines:
//                startActivityListener.startActivityMethod(((Movies.Movie) view.getTag()));
                break;
            default:
                break;
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
            parentLayout.setOnClickListener(MedicinesAllAdapter.this);

            textViewName = (TextView)itemView.findViewById(R.id.id_name);
            textViewdosFrequency = (TextView)itemView.findViewById(R.id.id_DoseFrequency);
            textViewQtyAtAtime = (TextView)itemView.findViewById(R.id.id_qty_at_atime);
            textViewdosPerday = (TextView)itemView.findViewById(R.id.id_dose_perday);
            textViewtime = (TextView)itemView.findViewById(R.id.id_times);
            textViewDosesPurchased = (TextView)itemView.findViewById(R.id.id_medicine_purchased);


        }
    }


}
