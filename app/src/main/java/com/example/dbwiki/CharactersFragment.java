package com.example.dbwiki;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CharactersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CharactersFragment extends Fragment {
    private static final String ARG_AFFILIATION = "affiliation";
    private RecyclerView recyclerView;
    private CharacterAdapter adapter;
    public static CharactersFragment newInstance(String affiliation) {
        CharactersFragment fragment = new CharactersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_AFFILIATION, affiliation);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_characters, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setHasFixedSize(true);

        String affiliation = getArguments() != null ? getArguments().getString(ARG_AFFILIATION) : "Z Fighter";

        CharactersViewModel viewModel = new ViewModelProvider(requireActivity()).get(CharactersViewModel.class);

        viewModel.getGroupedCharacters().observe(getViewLifecycleOwner(), map -> {
            List<Character> list = map.get(affiliation);
            if (list != null && !list.isEmpty()) {
                if (adapter == null) {
                    adapter = new CharacterAdapter(list);
                    recyclerView.setAdapter(adapter);
                } else {
                    adapter.updateData(list);
                }
            }
        });
    }
}