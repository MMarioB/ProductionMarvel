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
import com.example.personajesmarvel.service.MarvelInterfaceAPI;
import com.example.personajesmarvel.service.Middleware;
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

    PersonajesAdapter adapter;
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
        try{
            RetrofitInstance
                    .listaPersonajes()
                    .create(MarvelInterfaceAPI.class)
                    .lista(middleware.timestamp(),inicio, Keys.PUBLIC_KEY, middleware.md5())
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


                            }


                            if (listado.size() != 0) {
                                adapter = new PersonajesAdapter(activity,listado);
                                llm = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
                                rvLista.setLayoutManager(llm);
                                rvLista.setAdapter(adapter);


                            } else {
                                Toast.makeText(activity, "No hay datos", Toast.LENGTH_SHORT).show();
                            }
                        }



                        @Override
                        public void onFailure(Call<Characters> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (IOError e){
            e.printStackTrace();
            Toast.makeText(activity, "ERROR", Toast.LENGTH_SHORT).show();
        }
    }
}