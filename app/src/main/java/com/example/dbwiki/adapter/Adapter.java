package com.example.dbwiki.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dbwiki.R;
import com.example.dbwiki.data.model.CharacterResponse;
import com.example.dbwiki.data.model.Charactermodel; // Importar Charactermodel
import com.example.dbwiki.databinding.ViewholderCharacterBinding;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.CharacterViewHolder> {
    // Se cambia a Object para admitir Charactermodel y CharacterResponse.CharacterEntry
    List<Object> characterList;
    private final LayoutInflater inflater;

    // Constructor: recibe el contexto y la lista de personajes
    public Adapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        this.characterList = new ArrayList<>();
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
        Object item = characterList.get(position);

        String nombreText;
        String idText;
        String numeroFormateado;

        if (item instanceof CharacterResponse.CharacterEntry) {
            // Lógica existente para CharactersFragment (listado paginado)
            CharacterResponse.CharacterEntry entry = (CharacterResponse.CharacterEntry) item;
            numeroFormateado = String.format("#%03d", entry.getId());
            nombreText = entry.getName();
        } else if (item instanceof Charactermodel) {
            // Lógica para AffiliationFragments (listado completo)
            Charactermodel model = (Charactermodel) item;
            numeroFormateado = String.format("#%03d", model.getId());
            nombreText = model.getNombre();
        } else {
            return;
        }

        // Respetando la lógica de mapeo original
        holder.binding.nombre.setText(nombreText);
        holder.binding.id.setText(numeroFormateado);
    }

    // Indica cuántos elementos hay en la lista
    @Override
    public int getItemCount() {

        return characterList !=null ? characterList.size() : 0;
    }

    // Permite actualizar la lista completa desde fuera del adaptador (para listas estáticas, ej: afiliaciones)
    public void establecerLista(List<?> list) {
        this.characterList.clear();
        if (list != null) {
            this.characterList.addAll(list);
        }
        notifyDataSetChanged();
    }

    // Método original para el listado paginado (CharactersFragment)
    public void addCharacterList(List<CharacterResponse.CharacterEntry> nuevos) {
        if (nuevos == null || nuevos.isEmpty()) {
            return;
        }
        int inicio = characterList.size();

        // Añadimos al final de la lista los nuevos elementos recibidos
        this.characterList.addAll(nuevos);

        // Notificamos al RecyclerView que hemos insertado un rango de elementos,
        // para que solo actualice esa parte y no toda la lista (más eficiente).
        notifyItemRangeInserted(inicio, nuevos.size());
        Log.d("AdapterDebug", "Añadidos " + nuevos.size() + " items. Total ahora: " + characterList.size());
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