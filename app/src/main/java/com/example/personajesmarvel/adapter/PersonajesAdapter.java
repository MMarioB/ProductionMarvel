package com.example.personajesmarvel.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.personajesmarvel.R;
import com.example.personajesmarvel.models.Personaje;

import java.util.List;

public class PersonajesAdapter extends RecyclerView.Adapter<PersonajesAdapter.PersonajeViewHolder> {

    private Activity activity;
    private List<Personaje> listado;

    public PersonajesAdapter(Activity activity, List<Personaje> listado) {

        this.activity = activity;
        this.listado = listado;
    }


    @Override
    public PersonajeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.personaje_card_view, parent, false);

        return new PersonajeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PersonajeViewHolder holder, int position) {

        Personaje p = listado.get(position);
        holder.nombre.setText(p.getNombre());
        holder.id.setText(p.getId());
        holder.url.setText(p.getUrl());
        holder.descripcion.setText(p.getDescription());

        Glide.with(activity)
                .load(p.getUrl())
                .apply(RequestOptions.centerCropTransform())
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

        public PersonajeViewHolder(View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.nombre_personaje);
            id = itemView.findViewById(R.id.idPersonaje);
            url = itemView.findViewById(R.id.urlPersonaje);
            descripcion = itemView.findViewById(R.id.descripcionPersonaje);
            img = itemView.findViewById(R.id.personaje_imagen);


        }

    }



}
