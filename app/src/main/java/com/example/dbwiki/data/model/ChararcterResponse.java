package com.example.dbwiki.data.model;

import java.util.List;

public class ChararcterResponse {
    public List<CharacterEntry> results;

    public List<CharacterEntry> getResults() { return results; }

    public class CharacterEntry {

        private String name;
        private String url;   // URL al detalle del Pokémon

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }

        // (Opcional) método de ayuda para obtener el id desde la URL
        public int getIdFromUrl() {
            // La URL termina en ".../pokemon/1/"
            String[] partes = url.split("/");
            String ultima = partes[partes.length - 1].isEmpty()
                    ? partes[partes.length - 2]
                    : partes[partes.length - 1];
            return Integer.parseInt(ultima);
        }
    }
}
