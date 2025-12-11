package com.example.dbwiki.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dbwiki.R;
import com.example.dbwiki.data.model.CharacterResponse;
import com.example.dbwiki.data.model.Charactermodel;
import com.example.dbwiki.databinding.ViewholderCharacterBinding;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.CharacterViewHolder> {
    List<CharacterResponse.CharacterEntry> characterList;
    private final LayoutInflater inflater;

    public Adapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        this.characterList = new ArrayList<>();
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.viewholder_character, parent, false);
        return new CharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        CharacterResponse.CharacterEntry entry= characterList.get(position);
        Charactermodel personaje = new Charactermodel(
                entry.getId(),
                entry.getName(),
                entry.getImage(),
                entry.getKi(),
                entry.getMaxKi(),
                entry.getRace(),
                entry.getAffiliation()
        );
        int id = entry.getId();
        String numeroFormateado = String.format("#%03d", id);
        holder.binding.nombre.setText(numeroFormateado);
        holder.binding.id.setText(entry.getName());
        Glide.with(holder.itemView.getContext())
                .load(personaje.getImagen())
                .into(holder.binding.imagen);

        holder.itemView.setOnClickListener(v -> {

            Bundle bundle = new Bundle();
            bundle.putSerializable("personaje", personaje );

            // 2. Navegar al fragmento de detalle usando NavController
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.detailFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {

        return characterList !=null ? characterList.size() : 0;
    }

    public void establecerLista(List<CharacterResponse.CharacterEntry> characterList) {
        this.characterList = characterList;
        notifyDataSetChanged();
    }
    public void addCharacterList(List<CharacterResponse.CharacterEntry> nuevos) {
        if (nuevos == null || nuevos.isEmpty()) {
            return;
        }
        int inicio = characterList.size();

        this.characterList.addAll(nuevos);

        notifyItemRangeInserted(inicio, nuevos.size());
        Log.d("AdapterDebug", "Añadidos " + nuevos.size() + " items. Total ahora: " + characterList.size());
    }

    public static class CharacterViewHolder extends RecyclerView.ViewHolder {

        ViewholderCharacterBinding binding;

        public CharacterViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ViewholderCharacterBinding.bind(itemView);
        }
    }
}