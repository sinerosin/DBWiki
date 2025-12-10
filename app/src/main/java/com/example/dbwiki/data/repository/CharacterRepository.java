package com.example.dbwiki.data.repository;

import com.example.dbwiki.data.model.Charactermodel; // Importación asumida (corregida previamente)
import com.example.dbwiki.data.model.CharacterResponse;
import com.example.dbwiki.data.remote.DbzApi;
import com.example.dbwiki.data.remote.Resource;
import com.example.dbwiki.data.remote.RetrofitClient;

import java.util.ArrayList;
import java.util.List; // Necesitas importar List

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterRepository {
    private final static  int CHARACTER_LIMIT =29;
    private int PAGE_NUM=1;
    private boolean isLoading = false; // RE-INCORPORADO para evitar llamadas concurrentes en la paginación
    private boolean hasMorePages = true;
    private List<Charactermodel> fullCharacterList=null;
    private final DbzApi api;

    public CharacterRepository() {
        api = RetrofitClient.getCharacterApi();
    }
    public interface CharacterCallback {
        void onResult(Resource<Charactermodel> result);
    }
    public interface CharacterListCallback {
        void onResult(Resource<List<CharacterResponse.CharacterEntry>> result);
    }public interface FullCharacterListCallback {
        void onResult(Resource<List<Charactermodel>> result);
    }

    public void getCharacter(String name, CharacterCallback callback) {
        callback.onResult(Resource.loading());
        api.getCharacterByName(name.toLowerCase()).enqueue(new Callback<List<Charactermodel>>() {

            @Override
            public void onResponse(Call<List<Charactermodel>> call, Response<List<Charactermodel>> response) {

                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {

                    Charactermodel character = response.body().get(0);
                    callback.onResult(Resource.success(character));

                } else {
                    callback.onResult(Resource.error("Personaje no encontrado"));
                }
            }

            @Override
            public void onFailure(Call<List<Charactermodel>> call, Throwable t) {

                callback.onResult(Resource.error("Error de red: " + t.getMessage()));
            }
        });
    }
    public void getCharacterList(CharacterListCallback callback) {
        if (isLoading || !hasMorePages) { // Bloqueamos si ya estamos cargando o no hay más páginas
            return;
        }

        isLoading = true; // Marcamos que la carga ha comenzado
        callback.onResult(Resource.loading());

        api.getCharacterList(CHARACTER_LIMIT, PAGE_NUM).enqueue(new Callback<CharacterResponse>() {
            @Override
            public void onResponse(Call<CharacterResponse> call, Response<CharacterResponse> response) {
                isLoading = false; // Marcamos que la carga ha finalizado
                if (response.isSuccessful() && response.body() != null) {
                    List<CharacterResponse.CharacterEntry> lista = response.body().getItems();
                    if (lista == null || lista.isEmpty()) {
                        hasMorePages = false;
                        // Notificamos con la lista vacía para no mostrar error y detenemos el proceso.
                        callback.onResult(Resource.success(lista));
                        return;
                    }
                    callback.onResult(Resource.success(lista));
                    PAGE_NUM ++;
                } else {
                    callback.onResult(Resource.error("No se pudo cargar losCharacters"));
                }
            }

            @Override
            public void onFailure(Call<CharacterResponse> call, Throwable t) {
                isLoading = false; // Marcamos que la carga ha finalizado (por error)
                callback.onResult(Resource.error("Error de red: " + t.getMessage()));
            }
        });
    }
    public void getFilteredCharacterList(String requiredAffiliation, FullCharacterListCallback callback) {

        // 1. Si la lista completa ya está en caché, filtramos y devolvemos inmediatamente
        if (fullCharacterList != null) {
            List<Charactermodel> filteredList = filterListByAffiliation(fullCharacterList, requiredAffiliation);
            callback.onResult(Resource.success(filteredList));
            return;
        }

        // 2. Si no está en caché, avisamos de carga y la solicitamos
        callback.onResult(Resource.loading());
        api.getAllCharactersWithDetails().enqueue(new Callback<List<Charactermodel>>() {
            @Override
            public void onResponse(Call<List<Charactermodel>> call, Response<List<Charactermodel>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    // Almacenamos la lista completa en caché
                    fullCharacterList = response.body();

                    // Filtramos la lista recién obtenida
                    List<Charactermodel> filteredList = filterListByAffiliation(fullCharacterList, requiredAffiliation);

                    callback.onResult(Resource.success(filteredList));
                } else {
                    callback.onResult(Resource.error("No se pudo cargar la lista de afiliaciones."));
                }
            }

            @Override
            public void onFailure(Call<List<Charactermodel>> call, Throwable t) {
                callback.onResult(Resource.error("Error de red al cargar la lista de afiliaciones: " + t.getMessage()));
            }
        });
    }

    // Método auxiliar para realizar el filtrado de la lista
    private List<Charactermodel> filterListByAffiliation(List<Charactermodel> list, String requiredAffiliation) {
        List<Charactermodel> filtered = new ArrayList<>();

        for (Charactermodel character : list) {
            // Utilizamos getAffiliacion() que devuelve el valor normalizado
            // ("Z Fighter", "Villain", "Pride Troopers", "Other")
            if (character.getAffiliacion().equals(requiredAffiliation)) {
                filtered.add(character);
            }
        }
        return filtered;
    }
}