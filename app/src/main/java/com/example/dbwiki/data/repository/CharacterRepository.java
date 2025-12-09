package com.example.dbwiki.data.repository;

import com.example.dbwiki.data.remote.DbzApi;
import com.example.dbwiki.data.remote.Resource;
import com.example.dbwiki.data.remote.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterRepository {
    private final DbzApi api;

    public CharacterRepository() {
        // Obtenemos la instancia de la API desde Retrofit
        api = RetrofitClient.getCharacterApi();
    }

    // Callback propio para enviar el resultado al ViewModel
    public interface CharacterCallback {
        void onResult(Resource<Character> result);
    }
    public void getCharacter(String name, CharacterCallback callback) {

        // Avisamos al ViewModel de que hemos comenzado la operación
        callback.onResult(Resource.loading());

        // Llamada en segundo plano (asíncrona)
        api.getCharacterByName(name.toLowerCase()).enqueue(new Callback<Character>() {

            @Override
            public void onResponse(Call<Character> call, Response<Character> response) {

                // La API respondió, ahora comprobamos si tiene cuerpo
                if (response.isSuccessful() && response.body() != null) {

                    // Enviamos al ViewModel el Pokémon recibido
                    callback.onResult(Resource.success(response.body()));

                } else {

                    // La API respondió, pero no hay Pokémon (404)
                    callback.onResult(Resource.error("Personaje no encontrado"));
                }
            }

            @Override
            public void onFailure(Call<Character> call, Throwable t) {

                // Fallo de red, sin respuesta de la API
                callback.onResult(Resource.error("Error de red: " + t.getMessage()));
            }
        });
    }
}
