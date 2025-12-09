package com.example.dbwiki;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dbwiki.adapter.Adapter;
import com.example.dbwiki.databinding.FragmentCharactersBinding;
import com.example.dbwiki.repository.CharacterRepository;

import java.util.List;

public class CharactersFragment extends Fragment {
    private FragmentCharactersBinding binding;
    private Adapter adapter;
    private CharacterRepository repository;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCharactersBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtenemos la lista desde el Repository
        repository = new CharacterRepository();
        List<Character> personajes = repository.getPersonajes();

        // Configuramos el RecyclerView
        adapter = new Adapter(requireContext(), personajes);
        binding.recyclerView.setAdapter(adapter);

        // Definimos el LayoutManager (en cuadrícula de 2 columnas)
        binding.recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
    }

}