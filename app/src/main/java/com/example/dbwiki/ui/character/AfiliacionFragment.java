package com.example.dbwiki.ui.character;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dbwiki.databinding.FragmentAfiliacionBinding;
import com.google.android.material.tabs.TabLayoutMediator;


public class AfiliacionFragment extends Fragment {

    private FragmentAfiliacionBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAfiliacionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1️⃣ Configuramos el adaptador que define qué fragment se muestra en cada pestaña
        establecerAdaptadorViewPager();

        // 2️⃣ Vinculamos el TabLayout con el ViewPager2 para sincronizar ambos componentes
        vincularTabLayoutConViewPager();
    }
    private void establecerAdaptadorViewPager() {
        binding.viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                // Devuelve el fragment correspondiente a cada pestaña
                switch (position) {
                    default:
                    case 0: return new ZfighterFragment();
                    case 1: return new VillainFragment();
                    case 2: return new TroopersFragment();
                    case 3: return new OtherFragment();
                }
            }

            @Override
            public int getItemCount() {
                // Número total de pestañas
                return 4;
            }
        });
    }
    private void vincularTabLayoutConViewPager() {
        new TabLayoutMediator(binding.tabLayout, binding.viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Z Fighter");
                            break;
                        case 1:
                            tab.setText("Villain");
                            break;
                        case 2:
                            tab.setText("Pride Troopers");
                            break;
                        case 3:
                            tab.setText("Other");
                            break;
                    }
                }).attach();
    }
}