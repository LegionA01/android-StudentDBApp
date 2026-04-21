package com.saru.studentdbapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "StudentDB";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE student(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "course TEXT, " +
                "marks INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS student");
        onCreate(db);
    }

    // ➤ ADD
    public boolean insertStudent(String name, String course, int marks) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("course", course);
        cv.put("marks", marks);

        return db.insert("student", null, cv) != -1;
    }

    // ➤ VIEW
    public Cursor getAllStudents() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM student", null);
    }

    // ➤ UPDATE
    public boolean updateStudent(int id, String name, String course, int marks) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("course", course);
        cv.put("marks", marks);

        int result = db.update("student", cv, "id=?",
                new String[]{String.valueOf(id)});

        return result > 0;
    }

    // ➤ DELETE
    public boolean deleteStudent(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete("student", "id=?",
                new String[]{String.valueOf(id)});

        return result > 0;
    }
}