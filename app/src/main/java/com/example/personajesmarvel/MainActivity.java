package com.example.personajesmarvel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.personajesmarvel.adapter.RecyclerViewAdapter;
import com.example.personajesmarvel.models.Personaje;
import com.example.personajesmarvel.service.MarvelInterfaceAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    List<Personaje> personajes = new ArrayList<>();
    RecyclerViewAdapter rva;
    MarvelInterfaceAPI marvelInterfaceAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Keys.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        marvelInterfaceAPI = retrofit.create(MarvelInterfaceAPI.class);

        Call<List<Personaje>> call = marvelInterfaceAPI.lista();

        call.enqueue(new Callback<List<Personaje>>() {
            @Override
            public void onResponse(Call<List<Personaje>> call, Response<List<Personaje>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                personajes = response.body();
                Log.d("Tamaño", String.valueOf(personajes.size()));

                RecyclerView rv = findViewById(R.id.lista);
                RecyclerViewAdapter rva = new RecyclerViewAdapter(personajes, MainActivity.this);
                rv.setLayoutManager(
                        new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                rv.setAdapter(rva);
            }

            @Override
            public void onFailure(Call<List<Personaje>> call, Throwable t) {

            }
        });

    }
}