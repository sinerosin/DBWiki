package com.example.dbwiki.ui.character;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dbwiki.adapter.Adapter;
import com.example.dbwiki.databinding.FragmentCharactersBinding;
import com.example.dbwiki.data.repository.CharacterRepository;
import com.example.dbwiki.viewmodel.CharacterViewModel;

import java.util.List;

public class CharactersFragment extends Fragment {
    private FragmentCharactersBinding binding;
    private Adapter adapter;
    private CharacterViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCharactersBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(CharacterViewModel.class);

        configurarRecyclerView();
        observarCharacterList();
        configurarPaginacion();

        // Lanzamos la carga inicial
        viewModel.cargarCharacterList();
    }
    private void configurarRecyclerView() {
        adapter = new Adapter(requireContext());
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
    private void observarCharacterList() {
        viewModel.characterList.observe(getViewLifecycleOwner(), resource -> {
            if (resource == null) return;

            // Gestionamos los diferentes estados
            switch (resource.status) {
                case LOADING:
                    binding.progressLoadingPokedex.setVisibility(View.VISIBLE);
                    binding.layoutErrorPokedex.setVisibility(View.GONE);
                    binding.recyclerView.setVisibility(View.VISIBLE);
                    break;

                case SUCCESS:
                    binding.progressLoadingPokedex.setVisibility(View.GONE);
                    binding.layoutErrorPokedex.setVisibility(View.GONE);
                    binding.recyclerView.setVisibility(View.VISIBLE);

                    // Añadimos a la lista del RecyclerView los 20 nuevos Pokémon recibidos
                    adapter.addCharacterList(resource.data);
                    break;

                case ERROR:
                    binding.progressLoadingPokedex.setVisibility(View.GONE);
                    binding.recyclerView.setVisibility(View.GONE);
                    binding.layoutErrorPokedex.setVisibility(View.VISIBLE);
                    binding.tvErrorPokedex.setText(resource.message);
                    break;
            }
        });
    }

    // OJO: hacer esto solo si tu API tiene paginación
    private void configurarPaginacion() {
        // Añadimos un listener al RecyclerView para detectar el scroll
        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                // Comprobamos si el usuario ha llegado al final del RecyclerView.
                // canScrollVertically(1) devuelve false cuando NO se puede seguir bajando.
                if (!recyclerView.canScrollVertically(1)) {

                    // Si estamos en el final, pedimos al ViewModel que cargue la siguiente página
                    viewModel.cargarCharacterList();
                }
            }
        });
    }

}