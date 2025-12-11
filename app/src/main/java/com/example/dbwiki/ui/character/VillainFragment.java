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

import com.example.dbwiki.R;
import com.example.dbwiki.adapter.Adapter;
import com.example.dbwiki.data.model.CharacterResponse;
import com.example.dbwiki.databinding.FragmentOtherBinding;
import com.example.dbwiki.databinding.FragmentVillainBinding;
import com.example.dbwiki.viewmodel.CharacterViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VillainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VillainFragment extends Fragment {
    private FragmentVillainBinding binding;

    private Adapter adapter;
    private CharacterViewModel viewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentVillainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(CharacterViewModel.class);

        configurarRecyclerView();
        observarCharacterList();

        viewModel.cargarCharacterList();
    }
    private void configurarRecyclerView() {
        adapter = new Adapter(requireContext());
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }
    private void observarCharacterList() {
        viewModel.characterList.observe(getViewLifecycleOwner(), resource -> {
            if (resource == null) return;

            switch (resource.status) {
                case LOADING:
                    binding.recyclerView.setVisibility(View.VISIBLE);
                    break;

                case SUCCESS:
                    if (resource.data == null || resource.data.isEmpty()) {
                        binding.recyclerView.setVisibility(View.GONE);
                        return;
                    }

                    // FILTRAMOS solo los personajes con affiliation = "Other"
                    List<CharacterResponse.CharacterEntry> otrosPersonajes = new ArrayList<>();
                    for (CharacterResponse.CharacterEntry character : resource.data) {
                        String affiliation = character.getAffiliation(); // ¡Este campo existe en la API!
                        if (affiliation != null && (affiliation.equalsIgnoreCase("Freelancer") || affiliation.equalsIgnoreCase("Army of Frieza") || affiliation.equalsIgnoreCase("Villain"))) {
                            otrosPersonajes.add(character);
                        }
                    }

                    binding.recyclerView.setVisibility(View.VISIBLE);


                    // Solo añadimos los que son "Other"
                    adapter.establecerLista(otrosPersonajes);

                    break;

                case ERROR:
                    binding.recyclerView.setVisibility(View.GONE);


                    break;
            }
        });
    }

}