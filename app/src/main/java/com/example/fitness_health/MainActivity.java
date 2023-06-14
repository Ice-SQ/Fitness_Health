package com.example.fitness_health;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

import com.example.fitness_health.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding; // declare a variable of type ActivityMainBinding

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); // Inflate the view hierarchy from the layout file "activity_main.xml" into the activity's content view
        replaceFragment(new HomeFragment()); // Replace the current fragment with a new instance of HomeFragment

        binding.bottomNavigationView.setOnItemSelectedListener(item -> { // Set an item selected listener for the bottom navigation view

            switch (item.getItemId()){

                case R.id.home:
                    replaceFragment(new HomeFragment()); // Replace the current fragment with a new instance of HomeFragment
                    break;
                case R.id.Calculator:
                    replaceFragment(new CalculatorFragment()); // Replace the current fragment with a new instance of CalculatorFragment
                    break;
                case R.id.steps:
                    replaceFragment(new StepsFragment()); // Replace the current fragment with a new instance of StepsFragment
                    break;
                case R.id.Exercies:
                    replaceFragment(new ExercisesFragment()); // Replace the current fragment with a new instance of ExercisesFragment
                    break;
                case R.id.maps:
                    replaceFragment(new GoogleMapsFragment()); // Replace the current fragment with a new instance of GoogleMapsFragment
                    break;

            }

            return true; // Return true to indicate that the item selection has been handled
        });
    }


    public void setSelectedItem(int itemId) {
        binding.bottomNavigationView.setSelectedItemId(itemId);
    }


    public void hideBottomNavigationView() {
        binding.bottomNavigationView.setVisibility(View.GONE);
    }



    private void replaceFragment(Fragment fragment){ // Method to replace the current fragment with a new one

        FragmentManager fragmentManager = getSupportFragmentManager(); // Get the support FragmentManager for interacting with fragments associated with this activity
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction(); // Start a new fragment transaction
        fragmentTransaction.replace(R.id.frame_layout,fragment); // Replace the current fragment container with the given fragment
        fragmentTransaction.commit(); // Commit the transaction
    }
}
