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


public class ComicsAdapter extends RecyclerView.Adapter<ComicsAdapter.ComicsViewHolder> {

    private Activity activity;
    private List<String> comicsList;
    private SharedPreferences sharedPreferences;
    private String id;

    public ComicsAdapter(Activity activity, List<String> comicsList) {

        this.activity = activity;
        this.comicsList = comicsList;
    }


    @Override
    public ComicsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_comic, parent, false);

       sharedPreferences = activity.getSharedPreferences("info_heroe", Context.MODE_PRIVATE);
        id = sharedPreferences.getString("id",null);

        return new ComicsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ComicsViewHolder holder, int position) {

        String comics = comicsList.get(position);
        holder.vincula(comics);

    }

    @Override
    public int getItemCount() {
        return comicsList.size();
    }


    public class ComicsViewHolder extends RecyclerView.ViewHolder{

        ImageView image_comics;
        TextView tvIdComics;
        TextView tvUrlComics;
        TextView tvDesComics;

        public ComicsViewHolder(View itemView) {
            super(itemView);

            image_comics = itemView.findViewById(R.id.img_comics);
            tvIdComics = itemView.findViewById(R.id.heroe_id);
            tvUrlComics = itemView.findViewById(R.id.heroe_url);
            tvDesComics = itemView.findViewById(R.id.heroe_description);

        }

        public void vincula(String comics) {
            fillFields(comics);

        }

        public void fillFields(String comics) {

                Picasso.with(activity)
                        .load(comics)
                        .into(image_comics);



        }
    }

}
