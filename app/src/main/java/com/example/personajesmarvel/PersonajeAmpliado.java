package com.example.personajesmarvel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personajesmarvel.adapter.ComicsAdapter;
import com.example.personajesmarvel.adapter.SeriesAdapter;
import com.example.personajesmarvel.env.Keys;
import com.example.personajesmarvel.models.Characters;
import com.example.personajesmarvel.service.MarvelInterfaceAPI;
import com.example.personajesmarvel.service.Middleware;
import com.example.personajesmarvel.service.RetrofitInstance;
import com.squareup.picasso.Picasso;

import java.io.IOError;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonajeAmpliado extends AppCompatActivity {

    private ImageView imgH;
    private TextView tvHero;
    private RecyclerView rv;
    private RecyclerView rv2;
    private Button btnReload;
    private LinearLayout loading;
    private LinearLayout loading2;

    String url;
    String id;
    String name;

    List<String> comicsList;
    List<String> seriesList;
    Context context = PersonajeAmpliado.this;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personaje_ampliado);

        Intent intent = getIntent();
        String heroeID = intent.getStringExtra("id");
        String heroeURL = intent.getStringExtra("url");
        String heroeNombre = intent.getStringExtra("nombre");

        imgH = findViewById(R.id.imgHero);
        tvHero = findViewById(R.id.tvHero);
        rv = findViewById(R.id.recyclerComics);
        rv2 = findViewById(R.id.recyclerSeries);
        loading = findViewById(R.id.detailsCLoading);
        loading2 = findViewById(R.id.detailsSLoading);
        btnReload = findViewById(R.id.btnReload);

        activity = PersonajeAmpliado.this;

        if (heroeID != null && heroeURL != null && heroeNombre != null) {
            id = heroeID;
            url = heroeURL;
            name = heroeNombre;

        }

        Middleware middleware = new Middleware();


        tvHero.setText(heroeNombre);

        Picasso.with(this)
                .load(url)
                .into(imgH);


        cargarComics(middleware);

        cargarSeries(middleware);

    }

    private void cargarSeries(Middleware middleware) {
        try {
            RetrofitInstance
                    .listaSeries(id)
                    .create(MarvelInterfaceAPI.class)
                    .getSeries(middleware.timestamp(), 100, Keys.PUBLIC_KEY, middleware.md5())
                    .enqueue(new Callback<Characters>() {
                        @Override
                        public void onResponse(Call<Characters> call, Response<Characters> response) {

                            try {
                                if (response.body() != null) {
                                    response.body().getData().getCount();
                                    int total = Integer.parseInt(response.body().getData().getCount());
                                    btnReload.setVisibility(View.GONE);


                                    seriesList = new ArrayList<>();
                                    for (int i = 0; i < (total - 1); i++) {


                                        String extension = response.body().getData().getResults().get(i).getThumbnail().getExtension();
                                        String url = response.body().getData().getResults().get(i).getThumbnail().getPath() + "." + extension;


                                        seriesList.add(url);

                                        if (seriesList.size() != 0) {
                                            rv2.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                                            rv2.setAdapter(new SeriesAdapter(activity,
                                                    seriesList));
                                            loading2.setVisibility(View.GONE);
                                        }

                                    }
                                } else {
                                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                                }


                            } catch (IOError error) {
                                Toast.makeText(context, "No hay datos", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<Characters> call, Throwable t) {
                            Toast.makeText(PersonajeAmpliado.this, "No internet", Toast.LENGTH_SHORT).show();
                            loading2.setVisibility(View.GONE);
                        }
                    });

        } catch (IOError e) {
            e.printStackTrace();
            Toast.makeText(activity, "No hay datos", Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarComics(final Middleware middleware) {
        try {
            RetrofitInstance
                    .listaComics(id)
                    .create(MarvelInterfaceAPI.class)
                    .getComics(middleware.timestamp(), 100, Keys.PUBLIC_KEY, middleware.md5())
                    .enqueue(new Callback<Characters>() {
                        @Override
                        public void onResponse(Call<Characters> call, Response<Characters> response) {

                            try {
                                if (response.body() != null) {
                                    response.body().getData().getCount();
                                    int total = Integer.parseInt(response.body().getData().getCount());
                                    btnReload.setVisibility(View.GONE);
                                    comicsList = new ArrayList<>();
                                    for (int i = 0; i < (total - 1); i++) {
                                        String extension = response.body().getData().getResults().get(i).getThumbnail().getExtension();
                                        String url = response.body().getData().getResults().get(i).getThumbnail().getPath() + "." + extension;
                                        comicsList.add(url);
                                        System.out.println(url);

                                        if (comicsList.size() != 0) {
                                            rv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                                            rv.setAdapter(new ComicsAdapter(activity,
                                                    comicsList));
                                            loading.setVisibility(View.GONE);
                                        }

                                    }
                                } else {
                                    Toast.makeText(context, "No hay datos", Toast.LENGTH_SHORT).show();
                                }


                            } catch (IOError error) {
                                Toast.makeText(context, "No hay datos", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<Characters> call, Throwable t) {
                            Toast.makeText(PersonajeAmpliado.this, "No hay internet", Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                        }
                    });

        } catch (IOError e) {
            e.printStackTrace();
            Toast.makeText(activity, "No hay datos", Toast.LENGTH_SHORT).show();
        }
    }
}