package com.example.dbwiki.repository;

import java.util.ArrayList;
import java.util.List;

public class CharacterRepository {
    private final static int LIMIT_Character = 20;
    private int OFFSET_Character = 0;

    private final PokemonApi api;
    private List<Character> personajes;

    public CharacterRepository() {
        personajes=new ArrayList<>();
    }
    public List<Character> getPersonajes() {
        return personajes;
    }
}
