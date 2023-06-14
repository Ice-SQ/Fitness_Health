package com.example.fitness_health;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Create a new Handler object to delay the execution of code
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Create an Intent to start the LoginActivity
                Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Finish the SplashScreen activity so it is not accessible via back button
            }
        }, 3000); // Delay the execution of code inside run() by 3000 milliseconds (3 seconds)
    }
}
