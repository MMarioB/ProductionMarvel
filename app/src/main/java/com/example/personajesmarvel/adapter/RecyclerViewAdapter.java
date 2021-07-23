package com.example.personajesmarvel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personajesmarvel.R;
import com.example.personajesmarvel.models.Personaje;
import com.example.personajesmarvel.service.MarvelInterfaceAPI;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private List<Personaje> mData;
    private Context mContext;
    MarvelInterfaceAPI marvelInterfaceAPI;
    String resultado;

    public RecyclerViewAdapter(List<Personaje> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.personaje_card_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nombre.setText(mData.get(position).getNome());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nombre;
        ImageView imagen_personaje;

        MyViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre_personaje);
            imagen_personaje = itemView.findViewById(R.id.personaje_imagen);
        }
    }
}
