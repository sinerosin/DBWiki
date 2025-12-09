package com.example.dbwiki.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dbwiki.R;
import com.example.dbwiki.databinding.ViewholderCharacterBinding;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.CharacterViewHolder> {
    private List<Character> personajes;            // Lista de animales a mostrar
    private final LayoutInflater inflater;    // Crea (infla) las vistas desde XML

    // Constructor: recibe el contexto y la lista de personajes
    public Adapter(Context context, List<Character> personajes) {
        this.personajes = personajes;
        this.inflater = LayoutInflater.from(context);
    }

    // Crea un nuevo ViewHolder cuando el RecyclerView lo necesita
    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla el layout del ViewHolder (viewholder_animal.xml)
        View view = inflater.inflate(R.layout.viewholder_character, parent, false);
        return new CharacterViewHolder(view);
    }

    // Rellena los datos en el ViewHolder correspondiente a una posición concreta
    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        Character personaje = personajes.get(position);

        // Enlazamos los datos con los elementos del layout
       //holder.binding.tvNombre.setText(personaje.getNombre());
        //holder.binding.ivAnimal.setImageResource(personaje.getImagen());
    }

    // Indica cuántos elementos hay en la lista
    @Override
    public int getItemCount() {
        return personajes != null ? personajes.size() : 0;
    }

    // Permite actualizar la lista completa desde fuera del adaptador
    public void establecerLista(List<Character> personajes) {
        this.personajes = personajes;
        notifyDataSetChanged(); // Notifica al RecyclerView que los datos han cambiado
    }

    // Clase interna ViewHolder que representa un solo elemento con ViewBinding
    public static class CharacterViewHolder extends RecyclerView.ViewHolder {

        // Objeto de enlace al layout viewholder_animal.xml
        ViewholderCharacterBinding binding;

        // El constructor recibe la vista del layout inflado
        public CharacterViewHolder(@NonNull View itemView) {
            super(itemView);
            // Asociamos el objeto binding con la vista
            binding = ViewholderCharacterBinding.bind(itemView);
        }
    }
}
