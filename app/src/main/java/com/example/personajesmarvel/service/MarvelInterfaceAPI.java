package com.example.personajesmarvel.service;

import com.example.personajesmarvel.models.Personaje;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MarvelInterfaceAPI {
    @GET("characters?")
    Call<List<Personaje>> lista();
}
