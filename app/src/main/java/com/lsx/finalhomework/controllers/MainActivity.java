package com.lsx.finalhomework.controllers;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.lsx.finalhomework.R;
import com.lsx.finalhomework.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private boolean isShowBottomNav = true;

    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_booklist, R.id.navigation_cart, R.id.navigation_order)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        navController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
            if (navDestination.getId() == R.id.navigation_booklist || navDestination.getId() == R.id.navigation_cart || navDestination.getId() == R.id.navigation_order) {
                showBottomNav();
            } else {
                hideBottomNav();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }

    /**
     * 显示导航
     */
    private void showBottomNav() {
        if (!isShowBottomNav) {
            binding.navView.setVisibility(View.VISIBLE);
            isShowBottomNav = true;
        }
    }

    /**
     * 隐藏导航
     */
    private void hideBottomNav() {
        if (isShowBottomNav) {
            binding.navView.setVisibility(View.GONE);
            isShowBottomNav = false;
        }
    }
}