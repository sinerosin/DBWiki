package com.example.dbwiki.data.remote;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DbzApi {
    @GET("Characters")
    Call<Character> getCharacterByName(@Query("name") String name);
}
