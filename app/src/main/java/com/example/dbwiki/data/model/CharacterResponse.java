package com.example.dbwiki.data.model;

import java.util.List;

public class CharacterResponse {
    public List<CharacterEntry> items;

    public List<CharacterEntry> getItems() { return items; }

    public class CharacterEntry {
        private int id;
        private String name;
        private String ki;
        private String maxKi;
        private String race;
        private String image;
        private String affiliation;

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }
        public String getAffiliation(){
            return affiliation;
        }
        public String getKi(){
            return ki;
        }
        public String getMaxKi(){
            return maxKi;
        }
        public String getRace(){
            return race;
        }
        public String getImage(){
            return image;
        }
    }
    }
