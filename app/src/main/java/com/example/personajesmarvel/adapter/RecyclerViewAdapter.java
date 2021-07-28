package com.example.personajesmarvel.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personajesmarvel.PersonajeAmpliado;
import com.example.personajesmarvel.R;
import com.example.personajesmarvel.models.Personaje;
import com.example.personajesmarvel.models.Result;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;
    private List<Result> listaResultados;

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;


    public RecyclerViewAdapter(Activity activity) {
        this.activity = activity;
        listaResultados = new ArrayList<>();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == ITEM) {
            viewHolder = getViewHolder(parent, inflater);
        } else if (viewType == LOADING) {
            View view2 = inflater.inflate(R.layout.refresh, parent, false);
            viewHolder = new cargarVH(view2);
        }

        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View view = inflater.inflate(R.layout.card_view_horizontal, parent, false);
        viewHolder = new loadVH(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final Result result = listaResultados.get(position);

        String resultId = listaResultados.get(position).getId();
        String resultName = listaResultados.get(position).getName();
        String resultThumbnail = listaResultados.get(position).getThumbnail().getPath()
                + "." + listaResultados.get(position).getThumbnail().getExtension();

        String resultDescription = listaResultados.get(position).getDescription();

        Personaje p = new Personaje(resultId, resultName, resultThumbnail, resultDescription);

        int itemViewType = getItemViewType(position);
        if (itemViewType == ITEM) {
            if (result.getId() != null && result.getName() != null && result.getThumbnail() != null) {
                final loadVH loadVH = (loadVH) holder;

                Random rd = new Random(); // creating Random object
                float rating = rd.nextFloat() * (5 - 1) + 1;

                loadVH.tId.setText(p.getId());
                loadVH.tName.setText(p.getNombre());
                loadVH.tUrl.setText(p.getUrl());
                loadVH.tDescrip.setText(p.getDescription());
                loadVH.ratingBar.setRating(rating);

                Picasso.with(activity)
                        .load(p.getUrl())
                        .into(loadVH.imghero);

                //Card Animation
                Animation animation = AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.slide);
                loadVH.itemView.startAnimation(animation);
            }
        } else if (itemViewType == LOADING) {
        }

    }

    @Override
    public int getItemCount() {
        return listaResultados == null ? 0 : listaResultados.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == listaResultados.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public void add(Result result) {
        listaResultados.add(result);
        notifyItemInserted(listaResultados.size() - 1);
    }

    public void addAll(ArrayList<Result> mcList) {
        for (Result mc : mcList) {
            add(mc);
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
    }

    public void removeLoadingFooter() {
        if (isLoadingAdded) {
            isLoadingAdded = false;

            int position = listaResultados.size() - 1;
            Result item = getItem(position);

            if (item.getId() == null) {
                listaResultados.remove(position);
                notifyItemRemoved(position);
            }
        }
    }

    public Result getItem(int position) {
        return listaResultados.get(position);
    }

    protected class loadVH extends RecyclerView.ViewHolder {
        TextView tName;
        TextView tId;
        TextView tUrl;
        TextView tDescrip;
        ImageView imghero;
        RatingBar ratingBar;

        public loadVH(View view) {
            super(view);

            tName = itemView.findViewById(R.id.heroe_name);
            tId = itemView.findViewById(R.id.heroe_id);
            tUrl = itemView.findViewById(R.id.heroe_url);
            tDescrip = itemView.findViewById(R.id.heroe_description);
            imghero = itemView.findViewById(R.id.heroe_image);
            ratingBar = itemView.findViewById(R.id.ratingBar);

            view.setOnClickListener(v -> {

                int position = getAdapterPosition();

                if (listaResultados.get(position).getId() == tId.getText().toString()) {
                    Toast.makeText(activity, tName.getText().toString(), Toast.LENGTH_SHORT).show();

                    Intent goDetails = new Intent(activity, PersonajeAmpliado.class);
                    goDetails.putExtra("id", tId.getText().toString());
                    goDetails.putExtra("url", tUrl.getText().toString());
                    goDetails.putExtra("nombre", tName.getText().toString());
                    goDetails.putExtra("desc", tDescrip.getText().toString());
                    activity.startActivity(goDetails);

                }

            });

        }
    }


    protected class cargarVH extends RecyclerView.ViewHolder {
        public cargarVH(View itemView) {
            super(itemView);
        }
    }


}