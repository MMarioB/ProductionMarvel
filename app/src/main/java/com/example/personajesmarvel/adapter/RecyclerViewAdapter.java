package com.example.personajesmarvel.adapter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.personajesmarvel.R;
import com.example.personajesmarvel.models.Personaje;
import com.example.personajesmarvel.models.Result;

import java.util.ArrayList;
import java.util.List;
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;
    private List<Result> resultList;
    private SharedPreferences sharedPreferences;
    private String id;

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;


    public RecyclerViewAdapter(Activity activity) {

        this.activity = activity;
        resultList = new ArrayList<>();
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    public List<Result> getResultList() {
        return resultList;
    }

    public void setResultList(List<Result> resultList){
        this.resultList = resultList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType){
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View view2 = inflater.inflate(R.layout.refresh, parent, false);
                viewHolder = new cargarVH(view2);
                break;
        }

        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater){
        RecyclerView.ViewHolder viewHolder;
        View view = inflater.inflate(R.layout.personaje_card_view, parent, false);
        viewHolder = new loadVH(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final Result result = resultList.get(position);

        String resultId = resultList.get(position).getId();
        String resultName = resultList.get(position).getName();
        String resultThumbnail = resultList.get(position).getThumbnail().getPath()
                + "." + resultList.get(position).getThumbnail().getExtension();

        String resultDescription = resultList.get(position).getDescription();

        Personaje p = new Personaje(resultId, resultName, resultThumbnail,resultDescription);

        switch (getItemViewType(position)){
            case ITEM:

                if (result.getId() != null && result.getName() != null && result.getThumbnail() != null ){
                    final loadVH loadVH = (loadVH) holder;

                    loadVH.tId.setText(p.getId());
                    loadVH.tName.setText(p.getNombre());
                    loadVH.tUrl.setText(p.getUrl());
                    loadVH.tDescrip.setText(p.getDescription());

                    Glide.with(activity)
                            .load(p.getUrl())
                            .apply(RequestOptions.centerCropTransform())
                            .into(loadVH.imghero);

                }

                break;
            case LOADING:
                break;
        }

    }

    @Override
    public int getItemCount() {
        return resultList == null ? 0 : resultList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == resultList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public void add(Result result) {
        resultList.add(result);
        notifyItemInserted(resultList.size() - 1);
    }

    public void addAll(ArrayList<Result> mcList) {
        for (Result mc : mcList) {
            add(mc);
        }
    }

    public void remove(Result result) {
        int position = resultList.indexOf(result);
        if (position > -1) {
            resultList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
    }

    public void removeLoadingFooter() {
        if (isLoadingAdded) {
            isLoadingAdded = false;

            int position = resultList.size() - 1;
            Result item = getItem(position);

            if (item.getId() == null) {
                resultList.remove(position);
                notifyItemRemoved(position);
            }
        }
    }

    public Result getItem(int position) {
        return resultList.get(position);
    }

    protected class loadVH extends RecyclerView.ViewHolder {
        TextView tName;
        TextView tId;
        TextView tUrl;
        TextView tDescrip;
        ImageView imghero;

        public loadVH(View view) {
            super(view);

            tName = itemView.findViewById(R.id.nombre_personaje);
            tId = itemView.findViewById(R.id.idPersonaje);
            tUrl = itemView.findViewById(R.id.urlPersonaje);
            tDescrip = itemView.findViewById(R.id.descripcionPersonaje);
            imghero = itemView.findViewById(R.id.personaje_imagen);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();

                    if (resultList.get(position).getId() == tId.getText().toString()){
                        Toast.makeText(activity, tName.getText().toString(), Toast.LENGTH_SHORT).show();

                       /* Intent goDetails = new Intent(activity, DetailsActivity.class);
                        goDetails.putExtra("id", tId.getText().toString());
                        goDetails.putExtra("url", tUrl.getText().toString());
                        goDetails.putExtra("name", tName.getText().toString());
                        goDetails.putExtra("description", tDescrip.getText().toString());
                        activity.startActivity(goDetails);*/

                    }

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