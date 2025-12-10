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

public class ZfighterFragment extends Fragment {

    private static final String ARG_AFFILIATION = "affiliation";
    private static final String ARG_PARAM2 = "param2"; // Mantener por compatibilidad con el código original
    private String affiliation;
    private String mParam2;

    private Adapter adapter;
    private CharacterViewModel viewModel;

    public ZfighterFragment() {
        // Required empty public constructor
    }

    public static ZfighterFragment newInstance(String affiliation, String param2) {
        ZfighterFragment fragment = new ZfighterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_AFFILIATION, affiliation);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            affiliation = getArguments().getString(ARG_AFFILIATION);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_zfighter, container, false);
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
        MutableLiveData<Resource<List<Charactermodel>>> targetList;

        // Seleccionar el LiveData apropiado basado en la afiliación
        switch (affiliation) {
            case "Z Fighter":
                targetList = viewModel.zFighterList;
                break;
            case "Villain":
                targetList = viewModel.villainList;
                break;
            case "Pride Troopers":
                targetList = viewModel.troopersList;
                break;
            case "Other":
                targetList = viewModel.otherList;
                break;
            default:
                return;
        }

        targetList.observe(getViewLifecycleOwner(), resource -> {
            if (resource == null) return;

            // La lógica del fragmento se encargará de mostrar/ocultar el progreso o el error
            switch (resource.status) {
                case LOADING:
                    // (Lógica de UI de carga si existiera)
                    break;

                case SUCCESS:
                    // Se usa establecerLista ya que no hay paginación en esta vista
                    adapter.establecerLista(resource.data);
                    break;

                case ERROR:
                    // (Lógica de UI de error si existiera)
                    break;
            }
        });
    }
}