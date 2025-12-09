package com.example.dbwiki.data.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "https://dragonball-api.com/api/";
    private static Retrofit retrofit;

    // Inicializados de la instancia
    public static Retrofit getInstance() {
        if (retrofit == null) {
            // Inicializamos la instancia con la URL base
            // y la librería que mapea JSON a Java (Gson Converter)
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    // Inicializamos la interfaz, conectando con el cliente creado anteriormente
    public static DbzApi getCharacterApi() {
        return getInstance().create(DbzApi.class);
    }
}
