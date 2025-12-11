package com.example.dbwiki.ui.detalle;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dbwiki.R;
import com.example.dbwiki.data.model.Charactermodel;
import com.example.dbwiki.databinding.FragmentDetailBinding;

public class DetailFragment extends Fragment {
    private Charactermodel personaje;
    private FragmentDetailBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            personaje = (Charactermodel) getArguments().getSerializable("personaje");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (personaje != null) {
            // Mostramos los datos del animal en la interfaz
            binding.tvCharacterName.setText(personaje.getNombre());
            binding.tvCharacterMaxKi.setText(personaje.getMaxki());
            binding.tvCharacterKi.setText(personaje.getKi());
            binding.tvCharacterId.setText("#" + String.valueOf(personaje.getId()));
            binding.tvCharacterRace.setText(personaje.getRace());
            Glide.with(this)
                    .load(personaje.getImagen())
                    .into(binding.imgCharacter);
        } else {
            // En caso de error, podríamos volver atrás o mostrar un mensaje
            Toast.makeText(requireContext(), "No se pudo cargar el detalle del personaje", Toast.LENGTH_SHORT).show();
            requireActivity().onBackPressed();
        }
    }

}