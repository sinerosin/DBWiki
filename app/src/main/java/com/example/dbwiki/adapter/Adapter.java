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
    import com.example.dbwiki.databinding.ViewholderCharacterBinding;

    import java.util.ArrayList;
    import java.util.List;

    public class Adapter extends RecyclerView.Adapter<Adapter.CharacterViewHolder> {
        List<CharacterResponse.CharacterEntry> characterList;     // Lista de animales a mostrar
        private final LayoutInflater inflater;    // Crea (infla) las vistas desde XML

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
            CharacterResponse.CharacterEntry entry= characterList.get(position);
            int id = entry.getId();
            String numeroFormateado = String.format("#%03d", id);
            // Enlazamos los datos con los elementos del layout
           holder.binding.nombre.setText(numeroFormateado);
           holder.binding.id.setText(entry.getName());
        }

        // Indica cuántos elementos hay en la lista
        @Override
        public int getItemCount() {

            return characterList !=null ? characterList.size() : 0;
        }

        // Permite actualizar la lista completa desde fuera del adaptador
        public void establecerLista(List<CharacterResponse.CharacterEntry> characterList) {
            this.characterList = characterList;
            notifyDataSetChanged(); // Notifica al RecyclerView que los datos han cambiado
        }
        public void addCharacterList(List<CharacterResponse.CharacterEntry> nuevos) {
            // Guardamos el índice donde empieza la nueva inserción
            if (nuevos == null || nuevos.isEmpty()) {
                return; // Si no hay nada que añadir, salimos del método.
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
