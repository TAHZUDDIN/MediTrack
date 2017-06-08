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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_movies_layout, parent, false); //Inflating the layout
        ViewHolder vhItem = new ViewHolder(v); //Creating ViewHolder and passing the object of type view
        return vhItem;
    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Medicines medicine = medicines.get(position);



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
            case R.id.id_LinLay_parent_inflatingMovies:
//                startActivityListener.startActivityMethod(((Movies.Movie) view.getTag()));
                break;
            default:
                break;
        }
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout parentLayout;
        ImageView movieImage;
        TextView title, description;

        public ViewHolder(View itemView) {
            super(itemView);
            parentLayout = (LinearLayout) itemView.findViewById(R.id.id_LinLay_parent_inflatingMovies);
            parentLayout.setOnClickListener(MedicinesAllAdapter.this);
            movieImage = (ImageView) itemView.findViewById(R.id.id_image);
            title = (TextView) itemView.findViewById(R.id.id_title);
            description = (TextView) itemView.findViewById(R.id.id_description);
        }
    }


}
