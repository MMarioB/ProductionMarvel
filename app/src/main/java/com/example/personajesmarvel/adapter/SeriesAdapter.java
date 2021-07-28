package com.example.personajesmarvel.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.personajesmarvel.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.SeriesViewHolder> {

    private Activity activity;
    private List<String> seriesList;
    private SharedPreferences sharedPreferences;
    private String id;

    public SeriesAdapter(Activity activity, List<String> seriesList) {

        this.activity = activity;
        this.seriesList = seriesList;
    }


    @Override
    public SeriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_serie, parent, false);

       sharedPreferences = activity.getSharedPreferences("info_heroe", Context.MODE_PRIVATE);
        id = sharedPreferences.getString("id",null);

        return new SeriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SeriesViewHolder holder, int position) {

        String series = seriesList.get(position);
        holder.vincula(series);

    }

    @Override
    public int getItemCount() {
        return seriesList.size();
    }


    public class SeriesViewHolder extends RecyclerView.ViewHolder{

        ImageView image_series;
        TextView tvIdSeries;
        TextView tvUrlSeries;
        TextView tvDesSeries;

        public SeriesViewHolder(View itemView) {
            super(itemView);

            image_series = itemView.findViewById(R.id.img_series);
            tvIdSeries = itemView.findViewById(R.id.heroe_id);
            tvUrlSeries = itemView.findViewById(R.id.heroe_url);
            tvDesSeries = itemView.findViewById(R.id.heroe_description);

        }

        public void vincula(String series) {
            fillFields(series);

        }

        public void fillFields(String series) {

                Picasso.with(activity)
                        .load(series)
                        .into(image_series);

        }
    }

}
