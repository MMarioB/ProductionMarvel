package com.example.personajesmarvel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.personajesmarvel.adapter.PersonajesAdapter;
import com.example.personajesmarvel.adapter.RecyclerViewAdapter;
import com.example.personajesmarvel.env.Keys;
import com.example.personajesmarvel.models.Characters;
import com.example.personajesmarvel.models.Personaje;
import com.example.personajesmarvel.models.Result;
import com.example.personajesmarvel.service.MarvelInterfaceAPI;
import com.example.personajesmarvel.service.Middleware;
import com.example.personajesmarvel.service.PaginationScrollListener;
import com.example.personajesmarvel.service.RetrofitInstance;

import java.io.IOError;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvLista;
    private Middleware middleware;
    private long inicio = 0;

    private static final int PAGE_START = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 0;
    private int PAGE_SIZE = 10;
    private int currentPage = PAGE_START;

    RecyclerViewAdapter adapter;
    LinearLayoutManager llm;
    List<Personaje> listado;
    Activity activity;
    Context context = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvLista = findViewById(R.id.lista);
        activity = MainActivity.this;
        middleware = new Middleware();

        RelativeLayout item = findViewById(R.id.rl_layout);
        final View view = getLayoutInflater().inflate(R.layout.activity_main, null);
        item.addView(view);

        traerDatos(view);
    }

    private void traerDatos(final View view) {
        try {
            RetrofitInstance
                    .listaPersonajes()
                    .create(MarvelInterfaceAPI.class)
                    .lista(middleware.timestamp(), inicio, Keys.PUBLIC_KEY, middleware.md5())
                    .enqueue(new Callback<Characters>() {
                        @Override
                        public void onResponse(Call<Characters> call, Response<Characters> response) {
                            listado = new ArrayList<>();

                            int total = Integer.parseInt(response.body().getData().getCount());

                            for (int i = 0; i < (total - 1); i++) {

                                String id = response.body().getData().getResults().get(i).getId();
                                String nom = response.body().getData().getResults().get(i).getName();
                                String url = response.body().getData().getResults().get(i).getThumbnail().getPath()
                                        + "." + response.body().getData().getResults().get(i).getThumbnail().getExtension();
                                String descrip = response.body().getData().getResults().get(i).getDescription();

                                Personaje p = new Personaje(id, nom, url, descrip);

                                listado.add(p);

                                System.out.println(url);
                                System.out.println(i + "----" + descrip.length());

                            }


                            if (listado.size() != 0) {
                                adapter = new RecyclerViewAdapter(activity);
                                llm = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
                                rvLista.setLayoutManager(llm);
                                rvLista.setAdapter(adapter);

                                rvLista.addOnScrollListener(new PaginationScrollListener(llm) {
                                    @Override
                                    protected void loadMoreItems() {
                                        isLoading = true;
                                        currentPage += 1;
                                        inicio += 20;
                                        cargarDatos(view, currentPage);
                                    }

                                    @Override
                                    public int getTotalPageCount() {
                                        return TOTAL_PAGES;
                                    }

                                    @Override
                                    public boolean isLastPage() {
                                        return isLastPage;
                                    }

                                    @Override
                                    public boolean isLoading() {
                                        return isLoading;
                                    }
                                });

                                cargarDatos(view, currentPage);


                            } else {
                                Toast.makeText(activity, "No hay datos", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Characters> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (IOError e) {
            e.printStackTrace();
            Toast.makeText(activity, "ERROR", Toast.LENGTH_SHORT).show();
        }
    }

   private void cargarDatos(View view, final int page) {

        try {
            RetrofitInstance
                    .listaPersonajes()
                    .create(MarvelInterfaceAPI.class)
                    .lista(middleware.timestamp(), inicio, Keys.PUBLIC_KEY, middleware.md5())
                    .enqueue(new Callback<Characters>() {
                        @Override
                        public void onResponse(Call<Characters> call, Response<Characters> response) {
                            if (response.body() != null) {

                                Characters c = response.body();


                                if (Integer.parseInt(c.getData().getTotal()) > 0) {
                                    if (page == 0) {
                                        TOTAL_PAGES = (int) (Math.floor(Integer.parseInt(c.getData().getTotal()) / PAGE_SIZE));
                                        if (Integer.parseInt(c.getData().getTotal()) % PAGE_SIZE != 0) {
                                            TOTAL_PAGES++;
                                        }
                                    }

                                    adapter.addAll((ArrayList<Result>) c.getData().getResults());
                                    adapter.removeLoadingFooter();
                                    isLoading = false;

                                    if (currentPage + 1 < TOTAL_PAGES) {
                                        adapter.addLoadingFooter();
                                    } else {
                                        isLastPage = true;
                                    }

                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<Characters> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "No hay internet", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (IOError e){
            e.printStackTrace();
            Toast.makeText(activity, "No hay datos", Toast.LENGTH_SHORT).show();
        }
    }


}