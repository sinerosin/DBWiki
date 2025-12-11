package com.example.dbwiki.data.remote;

import com.example.dbwiki.data.model.Charactermodel;
import com.example.dbwiki.data.model.CharacterResponse;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DbzApi {
    @GET("Characters")
    Call<List<Charactermodel>> getCharacterByName(@Query("name") String name);
    @GET("Characters")
    Call<CharacterResponse>getCharacterList(@Query("limit")int limit, @Query("page") int page);

}