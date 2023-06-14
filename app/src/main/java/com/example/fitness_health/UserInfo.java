package com.example.fitness_health;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UserInfo extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText fNameEditText;
    private EditText lNameEditText;
    private EditText birthdayEditText;
    private EditText waterIntakeEditText;
    private Button saveButton;
    private DatabaseHelper databaseHelper;
    private int year, month, day;
// git
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        // Initialize the views
        fNameEditText = findViewById(R.id.UserFName);
        lNameEditText = findViewById(R.id.LUserName);
        birthdayEditText = findViewById(R.id.UserBirthDay);
        waterIntakeEditText = findViewById(R.id.WaterIntake);
        saveButton = findViewById(R.id.Go);

        // Initialize the DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        birthdayEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInputs()) {
                    saveUserInfo();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class); // Home fragment
                    startActivity(intent);
                }
            }
        });
    }

    private void showDatePickerDialog() {
        year = 2000;
        month = Calendar.JANUARY;
        day = 1;

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                year,
                month,
                day);
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        this.year = year;
        this.month = month;
        this.day = dayOfMonth;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy, MMMM, d", Locale.US);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        String selectedDate = dateFormat.format(calendar.getTime());

        birthdayEditText.setText(selectedDate);
        birthdayEditText.setError(null);
    }



    private boolean validateInputs() {
        String fName = fNameEditText.getText().toString().trim();
        String lName = lNameEditText.getText().toString().trim();
        String birthday = birthdayEditText.getText().toString().trim();
        String waterIntake = waterIntakeEditText.getText().toString().trim();

        if (fName.isEmpty() || lName.isEmpty() || birthday.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy, MMMM, d", Locale.US);
        dateFormat.setLenient(false);

        try {
            Date parsedDate = dateFormat.parse(birthday);

        } catch (ParseException e) {
            Toast.makeText(this, "Invalid birthday format. Please use dd/MM/yyyy", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void saveUserInfo() {
        // Get the user inputs
        String fName = fNameEditText.getText().toString().trim();
        String lName = lNameEditText.getText().toString().trim();
        String birthday = birthdayEditText.getText().toString().trim();
        String waterIntake = waterIntakeEditText.getText().toString().trim();

        // Insert the user information into the database
        boolean isInserted = databaseHelper.insertData(fName, lName, birthday, waterIntake);

        if (isInserted) {
            Toast.makeText(this, "User information saved successfully", Toast.LENGTH_SHORT).show();
            // Add any additional logic or actions after saving the information
        } else {
            Toast.makeText(this, "Failed to save user information", Toast.LENGTH_SHORT).show();
        }
    }
}
