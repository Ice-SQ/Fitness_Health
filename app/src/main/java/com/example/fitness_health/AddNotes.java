package com.example.fitness_health;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddNotes extends AppCompatActivity {

    private Button addNote;
    private TextInputEditText title, description;

    DatabaseHelper databaseHelper;

    // Variables to hold the fetched data from the previous activity
    private boolean fetchedStatus;
    private int fetchedKey;
    private String fetchedTitle, fetchedDescription, fetchedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        // Get the intent that started this activity
        Intent intent = getIntent();

        // Retrieve the data passed from the previous activity
        fetchedStatus = intent.getBooleanExtra("status", false);
        fetchedKey = intent.getIntExtra("key", 0);
        fetchedTitle = intent.getStringExtra("title");
        fetchedDescription = intent.getStringExtra("description");
        fetchedDate = intent.getStringExtra("date");

        // Initialize the DatabaseHelper for database operations
        databaseHelper = new DatabaseHelper(this);

        // Initialize the UI elements
        addNote = findViewById(R.id.addNote);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);

        // Update the UI based on the fetched status
        if (fetchedStatus) {
            // If status is true, display "ADD NOTE" button
            addNote.setText("ADD NOTE");
        } else {
            // If status is false, populate the title and description fields and display "UPDATE NOTE" button
            title.setText(fetchedTitle);
            description.setText(fetchedDescription);
            addNote.setText("UPDATE NOTE");
        }

        // Set a click listener for the addNote button
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fetchedStatus) {
                    // If fetchedStatus is true, call addNote method
                    addNote();
                } else {
                    // If fetchedStatus is false, call updateNoteDb method
                    updateNoteDb(fetchedKey, title.getText().toString(), description.getText().toString());
                }
            }
        });
    }

    // Method to add a new note to the database
    public void addNote() {
        // Get the current date
        Calendar calendar = Calendar.getInstance();

        // Define the desired date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        // Format the current date to the desired format
        String currentDate = dateFormat.format(calendar.getTime());

        boolean isInserted;

        // Insert the note into the database using the DatabaseHelper
        isInserted = databaseHelper.insertNotes(
                title.getText().toString(),
                description.getText().toString(),
                currentDate);

        if (isInserted) {
            // Display a success message if the note is inserted successfully
            Toast.makeText(AddNotes.this, "Note Added!", Toast.LENGTH_SHORT).show();
        } else {
            // Display an error message if the note insertion fails
            Toast.makeText(AddNotes.this, "Failed to Add!", Toast.LENGTH_SHORT).show();
        }

        // Finish the activity to return to the previous screen
        finish();
    }

    // Method to update an existing note in the database
    public void updateNoteDb(int key, String title, String description) {
        // Get the current date
        Calendar calendar = Calendar.getInstance();

        // Define the desired date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        // Format the current date to the desired format
        String currentDate = dateFormat.format(calendar.getTime());

        boolean isInserted;

        // Update the note in the database using the DatabaseHelper
        isInserted = databaseHelper.updateNote(
                key,
                title,
                description,
                currentDate);

        if (isInserted) {
            // Display a success message if the note is updated successfully
            Toast.makeText(AddNotes.this, "Note updated!", Toast.LENGTH_SHORT).show();
        } else {
            // Display an error message if the note update fails
            Toast.makeText(AddNotes.this, "Error occurred while updating the Note", Toast.LENGTH_SHORT).show();
        }

        // Finish the activity to return to the previous screen
        finish();
    }
}
