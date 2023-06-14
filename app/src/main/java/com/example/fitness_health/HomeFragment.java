package com.example.fitness_health;

import android.content.Intent;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeFragment extends Fragment {

    private CardView hCal;
    private CardView steps;
    private CardView notes;
    private CardView waterIntake;
    private CardView Exercises;
    private BottomNavigationView bottomNavigationView;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize CardViews and BottomNavigationView
        hCal = view.findViewById(R.id.HealthCard);
        steps = view.findViewById(R.id.StepsCard);
        notes = view.findViewById(R.id.NotesCard);
        waterIntake = view.findViewById(R.id.WaterIntakeCardview);
        Exercises = view.findViewById(R.id.ExercisesCardView);

        hCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create CalculatorFragment instance
                Fragment fragment = new CalculatorFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.Home, fragment); // Replace the current fragment with CalculatorFragment
                transaction.addToBackStack(null);
                transaction.commit();
                // Set the selected item in the BottomNavigationView to Calculator
                ((MainActivity) requireActivity()).setSelectedItem(R.id.Calculator);
            }
        });

        steps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create StepsFragment instance
                Fragment fragment = new StepsFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.Home, fragment); // Replace the current fragment with StepsFragment
                transaction.addToBackStack(null);
                transaction.commit();
                // Set the selected item in the BottomNavigationView to Steps
                ((MainActivity) requireActivity()).setSelectedItem(R.id.steps);
            }
        });

        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the NotesActivity
                Intent intent = new Intent(getActivity(), NotesActivity.class);
                startActivity(intent);
            }
        });

        waterIntake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the WaterIntakeActivity
                Intent intent = new Intent(getActivity(), WaterIntakeActivity.class);
                startActivity(intent);
            }
        });

        Exercises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create ExercisesFragment instance
                Fragment fragment = new ExercisesFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.Home, fragment); // Replace the current fragment with ExercisesFragment
                transaction.addToBackStack(null);
                transaction.commit();
                // Set the selected item in the BottomNavigationView to Exercises
                ((MainActivity) requireActivity()).setSelectedItem(R.id.Exercies);
            }
        });

        return view;
    }
}
