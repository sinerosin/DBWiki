package com.example.dbwiki.ui.character;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dbwiki.data.model.Charactermodel;
import com.example.dbwiki.viewmodel.CharacterViewModel;
import com.example.dbwiki.databinding.FragmentSearchBinding;

public class SearchFragment extends Fragment {
    private FragmentSearchBinding binding;
    private CharacterViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentSearchBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(CharacterViewModel.class);

        configurarSearchView();
        observarViewModel();
    }

    private void configurarSearchView() {
        binding.searchViewCharacterName.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.buscarCharacter(query);
                binding.searchViewCharacterName.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    private void observarViewModel() {
        viewModel.informacionCharacter.observe(getViewLifecycleOwner(), resource -> {

            if (resource == null) return;

            switch (resource.status) {

                case LOADING:
                    // Mostrar spinner
                    binding.progressLoading.setVisibility(View.VISIBLE);

                    // Ocultar el contenido y el error
                    binding.layoutCharacterContent.setVisibility(View.GONE);
                    binding.layoutError.setVisibility(View.GONE);
                    break;


                case SUCCESS:
                    // Ocultar spinner
                    binding.progressLoading.setVisibility(View.GONE);

                    // Mostrar contenido
                    binding.layoutCharacterContent.setVisibility(View.VISIBLE);
                    binding.layoutError.setVisibility(View.GONE);

                    // Cargar datos del pokemon
                    mostrarCharacter(resource.data);
                    break;


                case ERROR:
                    // Ocultar spinner y contenido
                    binding.progressLoading.setVisibility(View.GONE);
                    binding.layoutCharacterContent.setVisibility(View.GONE);

                    // Mostrar layout de error
                    binding.layoutError.setVisibility(View.VISIBLE);
                    binding.tvErrorMessage.setText(resource.message);

                    // Opcional: limpiar datos previos
                    limpiarVista();
                    break;
            }
        });
    }
    private void mostrarCharacter(Charactermodel charactermodel) {
        binding.tvCharacterName.setText(charactermodel.getNombre());
        binding.tvCharacterId.setText("#" + String.valueOf(charactermodel.getId()));

    }

    private void limpiarVista() {
        binding.tvCharacterName.setText("");
        binding.tvCharacterId.setText("");
        binding.tvPokemonHeight.setText("");
        binding.tvPokemonWeight.setText("");
        binding.tvPokemonTypes.setText("");
        binding.imgPokemonNormal.setImageDrawable(null);
        binding.imgPokemonShiny.setImageDrawable(null);
    }

}