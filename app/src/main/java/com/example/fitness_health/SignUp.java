package com.example.fitness_health;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.fitness_health.databinding.ActivitySignUpBinding;

public class SignUp extends AppCompatActivity {

    ActivitySignUpBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout using View Binding
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Create a DatabaseHelper instance
        databaseHelper = new DatabaseHelper(this);

        // Set a click listener on the Sign Up button
        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the values of the email, password, and confirm password fields
                String email = binding.signupEmail.getText().toString();
                String password = binding.signupPassword.getText().toString();
                String confirmPassword = binding.signupConformPassword.getText().toString();

                // Check if any field is empty, and show a toast message if true
                if(email.equals("")||password.equals("")||confirmPassword.equals(""))
                    Toast.makeText(SignUp.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                else{
                    // Check if the password and confirm password fields match
                    if(password.equals(confirmPassword)){
                        // Check if the email already exists in the database
                        Boolean checkUserEmail = databaseHelper.checkEmail(email);
                        if(checkUserEmail == false){
                            // If the email doesn't exist, insert the data into the database
                            Boolean insert = databaseHelper.insertData(email, password);
                            if(insert == true){
                                // Show a success message and redirect to the login page
                                Toast.makeText(SignUp.this, "Signup Successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                            }else{
                                // Show a failure message if the data insertion failed
                                Toast.makeText(SignUp.this, "Signup Failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            // Show a message if the email already exists in the database
                            Toast.makeText(SignUp.this, "User already exists! Please login", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        // Show a message if the password and confirm password fields don't match
                        Toast.makeText(SignUp.this, "Invalid Password!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Set a click listener on the Redirect Text (already a member)
        binding.SignupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirect to the login page
                Intent intent = new Intent(SignUp.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
