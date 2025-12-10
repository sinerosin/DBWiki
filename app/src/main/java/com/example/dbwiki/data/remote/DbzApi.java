package com.example.dbwiki.data.remote;

import com.example.dbwiki.data.model.Charactermodel; // Importación asumida (corregida previamente)
import com.example.dbwiki.data.model.CharacterResponse;

import java.util.List; // Necesitas importar List
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DbzApi {
    @GET("Characters")
    Call<List<Charactermodel>> getCharacterByName(@Query("name") String name); // CORRECCIÓN: Ahora espera una lista
    @GET("Characters")
    Call<CharacterResponse>getCharacterList(@Query("limit")int limit, @Query("page") int page);

}