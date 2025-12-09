package com.example.dbwiki.ui.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.dbwiki.R;
import com.example.dbwiki.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityMainBinding.inflate(getLayoutInflater())).getRoot());

        // 1. Obtenemos la referencia de la Toolbar del layout
        setSupportActionBar(binding.toolbar);

        // 2. Obtenemos el NavController desde el contenedor del grafo
        navController = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment)).getNavController();

        // 3. Configuramos la AppBar para que el título y el botón de navegación
        // se actualicen automáticamente al navegar entre fragments
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.charactersFragment,
                R.id.afiliacionFragment,
                R.id.searchFragment
        ).build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        binding.bottomNavigationView.setItemIconTintList(null);
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);
    }
    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}