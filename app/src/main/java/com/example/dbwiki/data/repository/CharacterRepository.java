package com.example.dbwiki.data.repository;

import com.example.dbwiki.data.model.Charactermodel; // Importación asumida (corregida previamente)
import com.example.dbwiki.data.model.CharacterResponse;
import com.example.dbwiki.data.remote.DbzApi;
import com.example.dbwiki.data.remote.Resource;
import com.example.dbwiki.data.remote.RetrofitClient;

import java.util.List; // Necesitas importar List

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterRepository {
    private final static  int CHARACTER_LIMIT =29;
    private int PAGE_NUM=1;
    private final DbzApi api;

    public CharacterRepository() {
        // Obtenemos la instancia de la API desde Retrofit
        api = RetrofitClient.getCharacterApi();
    }

    // Callback propio para enviar el resultado al ViewModel
    public interface CharacterCallback {
        void onResult(Resource<Charactermodel> result);
    }
    public interface CharacterListCallback {
        void onResult(Resource<List<CharacterResponse.CharacterEntry>> result);
    }
    public void getCharacter(String name, CharacterCallback callback) {

        // Avisamos al ViewModel de que hemos comenzado la operación
        callback.onResult(Resource.loading());

        // Llamada en segundo plano (asíncrona) - CORRECCIÓN: Espera List<Charactermodel>
        api.getCharacterByName(name.toLowerCase()).enqueue(new Callback<List<Charactermodel>>() {

            @Override
            public void onResponse(Call<List<Charactermodel>> call, Response<List<Charactermodel>> response) {

                // La API respondió, ahora comprobamos si tiene cuerpo Y si la lista NO está vacía
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {

                    // CORRECCIÓN: Extraemos el primer personaje de la lista
                    Charactermodel character = response.body().get(0);
                    callback.onResult(Resource.success(character));

                } else {

                    // La API respondió, pero no hay personaje (404) o la lista está vacía
                    callback.onResult(Resource.error("Personaje no encontrado"));
                }
            }

            @Override
            public void onFailure(Call<List<Charactermodel>> call, Throwable t) {

                // Fallo de red, sin respuesta de la API
                callback.onResult(Resource.error("Error de red: " + t.getMessage()));
            }
        });
    }
    public void getCharacterList(CharacterListCallback callback) {

        // Avisamos de que empieza la carga
        callback.onResult(Resource.loading());

        api.getCharacterList(CHARACTER_LIMIT, PAGE_NUM).enqueue(new Callback<CharacterResponse>() {
            @Override
            public void onResponse(Call<CharacterResponse> call, Response<CharacterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Recuperamos la lista de Pokémon directamente
                    List<CharacterResponse.CharacterEntry> lista = response.body().getItems();
                    callback.onResult(Resource.success(lista));
                } else {
                    callback.onResult(Resource.error("No se pudo cargar la Pokédex"));
                }

                // Incrementamos el offset
                PAGE_NUM ++;
            }

            @Override
            public void onFailure(Call<CharacterResponse> call, Throwable t) {
                callback.onResult(Resource.error("Error de red: " + t.getMessage()));
            }
        });
    }
}