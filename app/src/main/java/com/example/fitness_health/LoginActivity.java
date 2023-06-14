package com.example.fitness_health;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.fitness_health.databinding.ActivityLoginBinding;

// Defining the LoginActivity class which extends AppCompatActivity
public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding; // Initializing ActivityLoginBinding object
    DatabaseHelper databaseHelper; // Initializing DatabaseHelper object

    // onCreate method which gets called when the activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout using the View Binding library
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initializing the DatabaseHelper object
        databaseHelper = new DatabaseHelper(this);

        // Set the click listener for the login button
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the email and password entered by the user
                String email = binding.loginEmail.getText().toString();
                String password = binding.loginPassword.getText().toString();

                // If either field is empty, show an error message
                if (email.equals("") || password.equals(""))
                    Toast.makeText(LoginActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                else {
                    // Check if the email and password match with a user in the database
                    Boolean checkCredentials = databaseHelper.checkEmailPassword(email, password);

                    // If they match, show a success message and start the MainActivity
                    if (checkCredentials == true){
                        Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),UserInfo.class); // Home fragment
                        startActivity(intent);
                    }
                    // If they don't match, show an error message
                    else {
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Set the click listener for the sign up redirect text
        binding.loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the SignUp activity
                Intent intent = new Intent(LoginActivity.this,SignUp.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
