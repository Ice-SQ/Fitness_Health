package com.example.fitness_health;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

public class NotesActivity extends AppCompatActivity {

    private LinkedHashMap<Integer, String[]> notes;
    private DatabaseHelper databaseHelper;
    private FloatingActionButton addNotes;
    private LinearLayout notesContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        setContentView(R.layout.activity_notes);

        // Retrieve notes from the database
        notes = databaseHelper.getNotes(this);

        // Initialize UI elements
        addNotes = findViewById(R.id.addNotes);
        notesContainer = findViewById(R.id.notesContainer);

        // Set click listener for the "Add Notes" floating action button
        addNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the AddNotes activity
                Intent intent = new Intent(NotesActivity.this, AddNotes.class);
                intent.putExtra("status", true);
                startActivity(intent);
            }
        });

        // Create card views for each note
        createCardViews();
    }

    public void createCardViews() {
        // Clear the existing card views container
        notesContainer.removeAllViews();

        // Iterate over the notes and create a card view for each note
        for (Map.Entry<Integer, String[]> entry : notes.entrySet()) {
            String[] data = entry.getValue();
            Integer key = entry.getKey();
            String title = data[0];
            String description = data[1];
            String date = data[2];

            // Create a new CardView
            CardView cardView = new CardView(this);
            CardView.LayoutParams cardViewParams = new CardView.LayoutParams(
                    CardView.LayoutParams.MATCH_PARENT,
                    CardView.LayoutParams.WRAP_CONTENT
            );
            cardViewParams.setMargins(16, 16, 16, 50);
            cardView.setLayoutParams(cardViewParams);

            cardView.setId(key);
            cardView.setCardElevation(8);
            cardView.setCardBackgroundColor(Color.parseColor("#ffffff"));
            cardView.setRadius(20);

            // Create a relative layout to hold the note details
            RelativeLayout relativeLayout = new RelativeLayout(this);
            RelativeLayout.LayoutParams relativeLayoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT
            );
            relativeLayout.setLayoutParams(relativeLayoutParams);
            relativeLayout.setPadding(50, 30, 50, 30);

            // Create a linear layout to hold the update and delete buttons
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            ));
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setId(View.generateViewId());

            // Create TextView for the note title
            TextView titleName = new TextView(this);
            titleName.setLayoutParams(new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            ));
            titleName.setText(title);
            titleName.setTextSize(20);
            titleName.setMaxLines(1);
            titleName.setTypeface(null, Typeface.BOLD);
            titleName.setId(View.generateViewId());
            titleName.setTextColor(Color.parseColor("#000000"));

            // Create TextView for the note creation date
            TextView createdOn = new TextView(this);
            createdOn.setLayoutParams(new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            ));
            createdOn.setText("Created on: " + date);
            createdOn.setTextSize(15);
            createdOn.setId(View.generateViewId());

            // Create TextView for the note description
            TextView noteDescription = new TextView(this);
            noteDescription.setLayoutParams(new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            ));
            noteDescription.setText(description);
            noteDescription.setTextSize(18);
            noteDescription.setId(View.generateViewId());

            // Create delete button to delete the note
            ImageButton deleteButton = new ImageButton(this);
            LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            deleteButton.setLayoutParams(buttonParams);
            deleteButton.setImageResource(R.drawable.baseline_delete_24);
            deleteButton.setBackground(null);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDeleteFileDialog(key);
                }
            });

            // Create update button to edit the note
            ImageButton updateButton = new ImageButton(this);
            LinearLayout.LayoutParams buttonParams2 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            updateButton.setLayoutParams(buttonParams2);
            updateButton.setImageResource(R.drawable.baseline_edit_24);
            updateButton.setBackground(null);
            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateToEdit(key, title, description);
                }
            });

            // Set layout params for the note title
            RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            params1.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            params1.addRule(RelativeLayout.ALIGN_PARENT_START);
            params1.setMargins(0, 0, 0, 10);
            titleName.setLayoutParams(params1);

            // Set layout params for the note creation date
            RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            params2.addRule(RelativeLayout.BELOW, titleName.getId());
            params2.addRule(RelativeLayout.ALIGN_PARENT_START);
            params2.setMargins(0, 0, 0, 20);
            createdOn.setLayoutParams(params2);

            // Set layout params for the note description
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            params.addRule(RelativeLayout.BELOW, createdOn.getId());
            params.setMargins(0, 25, 0, 0);
            noteDescription.setLayoutParams(params);

            // Set layout params for the linear layout holding the buttons
            RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            params3.addRule(RelativeLayout.ALIGN_PARENT_END);
            params3.addRule(RelativeLayout.ALIGN_TOP, noteDescription.getId());
            params3.setMargins(0, 0, 0, 0);
            linearLayout.setLayoutParams(params3);

            // Add views to the relative layout
            relativeLayout.setLayoutParams(relativeLayoutParams);
            relativeLayout.addView(titleName);
            relativeLayout.addView(createdOn);
            relativeLayout.addView(noteDescription);
            linearLayout.addView(updateButton);
            linearLayout.addView(deleteButton);
            relativeLayout.addView(linearLayout);

            // Add relative layout to the card view
            cardView.addView(relativeLayout);

            // Add card view to the container
            notesContainer.addView(cardView);
        }
    }

    private void showDeleteFileDialog(int key) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Note");
        builder.setMessage("Are you sure you want to delete this note?");

        // Set the positive button (delete)
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Delete the note from the database
                Integer delete_data = databaseHelper.deleteNote(key);

                if (delete_data > 0) {
                    Toast.makeText(getApplicationContext(), "Note deleted successfully", Toast.LENGTH_SHORT).show();
                    notes = databaseHelper.getNotes(NotesActivity.this);
                    createCardViews();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to delete the Note", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set the negative button (cancel)
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Cancel the deletion or perform any other actions
            }
        });

        // Show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void navigateToEdit(int key, String title, String description) {
        // Start the AddNotes activity with edit mode
        Intent intent = new Intent(this, AddNotes.class);
        intent.putExtra("status", false);
        intent.putExtra("key", key);
        intent.putExtra("title", title);
        intent.putExtra("description", description);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the notes from the database and recreate card views
        notes = databaseHelper.getNotes(this);
        createCardViews();
    }
}
