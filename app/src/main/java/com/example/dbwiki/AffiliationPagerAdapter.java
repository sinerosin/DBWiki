package com.example.dbwiki;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class AffiliationPagerAdapter extends FragmentStateAdapter {

    private final List<String> affiliations;

    public AffiliationPagerAdapter(@NonNull FragmentActivity fa, List<String> affiliations) {
        super(fa);
        this.affiliations = affiliations;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        String affiliation = affiliations.get(position);
        // Cada vez que se llama createFragment() se crea una NUEVA instancia del Fragment
        return CharactersFragment.newInstance(affiliation);
    }

    @Override
    public int getItemCount() {
        return affiliations.size();
    }
}