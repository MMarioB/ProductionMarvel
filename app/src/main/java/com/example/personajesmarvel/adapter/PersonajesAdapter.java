package com.example.personajesmarvel.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.personajesmarvel.R;
import com.example.personajesmarvel.models.Personaje;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

public class PersonajesAdapter extends RecyclerView.Adapter<PersonajesAdapter.PersonajeViewHolder> {

    private Activity activity;
    private List<Personaje> listado;

    public PersonajesAdapter(Activity activity, List<Personaje> listado) {

        this.activity = activity;
        this.listado = listado;
    }


    @Override
    public PersonajeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_horizontal, parent, false);

        return new PersonajeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PersonajeViewHolder holder, int position) {

        Personaje p = listado.get(position);
        Random rd = new Random(); // creating Random object
        float rating = rd.nextFloat() * (5 - 1) + 1;

        holder.nombre.setText(p.getNombre());
        holder.id.setText(p.getId());
        holder.url.setText(p.getUrl());
        holder.descripcion.setText(p.getDescription());
        holder.ratingBar.setRating(rating);

        Picasso.with(activity)
                .load(p.getUrl())
                .into(holder.img);

    }

    @Override
    public int getItemCount() {
        return listado.size();
    }


    public class PersonajeViewHolder extends RecyclerView.ViewHolder{

        TextView nombre;
        TextView id;
        TextView url;
        TextView descripcion;
        ImageView img;
        RatingBar ratingBar;

        public PersonajeViewHolder(View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.heroe_name);
            id = itemView.findViewById(R.id.heroe_id);
            url = itemView.findViewById(R.id.heroe_url);
            descripcion = itemView.findViewById(R.id.heroe_description);
            img = itemView.findViewById(R.id.heroe_image);
            ratingBar = itemView.findViewById(R.id.ratingBar);


        }

    }



}
