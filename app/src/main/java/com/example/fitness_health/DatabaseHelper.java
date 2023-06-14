package com.example.fitness_health;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String databaseName = "SignLog.db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        // Create "users" table to store user email and password
        MyDatabase.execSQL("CREATE TABLE users(ID INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT , password TEXT)");

        // Create "notes" table to store user notes
        MyDatabase.execSQL("CREATE TABLE notes(ID INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, description TEXT, date TEXT)");

        // Create "userinfo" table to store user information
        createUserInfoTable(MyDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        // Drop the existing tables during database upgrade
        MyDB.execSQL("DROP TABLE IF EXISTS users");
        MyDB.execSQL("DROP TABLE IF EXISTS userinfo");
    }

    public Boolean insertData(String email, String password) {
        // Validate the email and password before inserting into the database
        if (!isValidEmail(email)) {
            return false;
        }

        if (!isValidPassword(password)) {
            return false;
        }

        SQLiteDatabase MyDatabase = this.getWritableDatabase();

        // Get the current maximum number in the table
        Cursor cursor = MyDatabase.rawQuery("SELECT MAX(ID) FROM users", null);
        int currentNumber = 0;
        if (cursor.moveToFirst()) {
            currentNumber = cursor.getInt(0);
        }
        cursor.close();

        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", currentNumber + 1);
        contentValues.put("email", email);
        contentValues.put("password", password);
        long result = MyDatabase.insert("users", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean insertData(String fName, String lName, String birthday, String waterIntake) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();

        // Get the current maximum UserID in the table
        Cursor cursor = MyDatabase.rawQuery("SELECT MAX(UserID) FROM userinfo", null);
        int currentUserId = 0;
        if (cursor.moveToFirst()) {
            currentUserId = cursor.getInt(0);
        }
        cursor.close();

        ContentValues contentValues = new ContentValues();
        contentValues.put("UserID", currentUserId + 1);
        contentValues.put("Fname", fName);
        contentValues.put("Lname", lName);
        contentValues.put("Birthday", birthday);
        contentValues.put("WaterIntake", waterIntake);
        long result = MyDatabase.insert("userinfo", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean checkEmail(String email) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();

        // Check if the given email exists in the "users" table
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM users WHERE email = ?", new String[]{email});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();

        // Check if the given email and password combination exists in the "users" table
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM users WHERE email = ? AND password = ?", new String[]{email, password});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isValidEmail(String email) {
        // Validate the email using regular expression pattern matching
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        // Validate the password length (minimum length of 6 characters)
        return password.length() >= 6;
    }

    public void createUserInfoTable(SQLiteDatabase MyDatabase) {
        // Create "userinfo" table to store user information
        MyDatabase.execSQL("CREATE TABLE userinfo(UserID INTEGER PRIMARY KEY AUTOINCREMENT, Fname TEXT, Lname TEXT, Birthday TEXT, WaterIntake TEXT)");
    }

    public Cursor getUserInfo() {
        SQLiteDatabase MyDatabase = this.getReadableDatabase();

        // Retrieve all data from the "userinfo" table
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM userinfo", null);
        return cursor;
    }

    public Boolean insertNotes(String title, String note, String date) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("description", note);
        contentValues.put("date", date);
        long results = sqLiteDatabase.insert("notes",null,contentValues);

        return results != -1;
    }

    @SuppressLint("Range")
    public LinkedHashMap<Integer, String[]> getNotes(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM notes";
        Cursor cursor = db.rawQuery(query, null);
        LinkedHashMap<Integer, String[]> dataHashMap = new LinkedHashMap<>();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            String[] data = new String[3];
            // Populate the String array with values as needed
            data[0] = cursor.getString(cursor.getColumnIndex("title"));
            data[1] = cursor.getString(cursor.getColumnIndex("description"));
            data[2] = cursor.getString(cursor.getColumnIndex("date"));
            dataHashMap.put(id, data);
        }

        cursor.close();
        db.close();
        return dataHashMap;
    }

    public int deleteNote(int key){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        // Delete the note with the given key (ID)
        Integer delete_data = sqLiteDatabase.delete("notes","ID = ? ", new String[] {String.valueOf(key)});
        sqLiteDatabase.close();
        return delete_data;
    }

    public boolean updateNote(
            int key,
            String title,
            String description,
            String date){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("description", description);
        contentValues.put("date", date);
        // Update the note with the given key (ID)
        sqLiteDatabase.update("notes", contentValues, "ID = ?", new String[]{String.valueOf(key)});
        sqLiteDatabase.close();
        return true;
    }
}
