package com.example.dbwiki.data.model;

import java.util.List;

public class CharacterResponse {
    public List<CharacterEntry> items;

    public List<CharacterEntry> getItems() { return items; }

    public class CharacterEntry {
        private int id;
        private String name;
           // URL al detalle del Pokémon

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        // (Opcional) método de ayuda para obtener el id desde la URL
    }
}
