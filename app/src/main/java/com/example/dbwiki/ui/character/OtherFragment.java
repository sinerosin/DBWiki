package com.example.dbwiki.ui.character;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dbwiki.R;
import com.example.dbwiki.adapter.Adapter;
import com.example.dbwiki.data.model.Charactermodel;
import com.example.dbwiki.data.remote.Resource;
import com.example.dbwiki.viewmodel.CharacterViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OtherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OtherFragment extends Fragment {

    private static final String ARG_AFFILIATION = "param1"; // Usamos param1 para coincidir con la llamada de newInstance en AfiliacionFragment.java
    private static final String ARG_PARAM2 = "param2";
    private String affiliation;
    private String mParam2;

    private Adapter adapter;
    private CharacterViewModel viewModel;

    public OtherFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1 (Affiliation name).
     * @param param2 Parameter 2.
     * @return A new instance of fragment OtherFragment.
     */
    public static OtherFragment newInstance(String param1, String param2) {
        OtherFragment fragment = new OtherFragment();
        Bundle args = new Bundle();
        args.putString(ARG_AFFILIATION, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Obtenemos la afiliación pasada
            affiliation = getArguments().getString(ARG_AFFILIATION);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_other, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(CharacterViewModel.class);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        configurarRecyclerView(recyclerView);
        observarViewModel();

        // Iniciar la carga de la lista filtrada
        if (affiliation != null) {
            viewModel.loadAffiliationList(affiliation);
        }
    }

    private void configurarRecyclerView(RecyclerView recyclerView) {
        adapter = new Adapter(requireContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void observarViewModel() {
        MutableLiveData<Resource<List<Charactermodel>>> targetList = viewModel.otherList; // Observamos la lista específica de Other

        targetList.observe(getViewLifecycleOwner(), resource -> {
            if (resource == null) return;

            switch (resource.status) {
                case LOADING:
                    // Implementar la lógica de carga si es necesario
                    break;

                case SUCCESS:
                    adapter.establecerLista(resource.data);
                    break;

                case ERROR:
                    // Implementar la lógica de error si es necesario
                    break;
            }
        });
    }
}