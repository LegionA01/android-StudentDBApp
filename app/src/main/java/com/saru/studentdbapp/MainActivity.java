package com.saru.studentdbapp;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etId, etName, etCourse, etMarks;
    Button btnAdd, btnView, btnUpdate, btnDelete;
    RecyclerView recyclerView;

    DatabaseHelper db;
    ArrayList<Student> list;
    StudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etId = findViewById(R.id.etId);
        etName = findViewById(R.id.etName);
        etCourse = findViewById(R.id.etCourse);
        etMarks = findViewById(R.id.etMarks);

        btnAdd = findViewById(R.id.btnAdd);
        btnView = findViewById(R.id.btnView);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        recyclerView = findViewById(R.id.recyclerView);

        db = new DatabaseHelper(this);
        list = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // ➤ ADD
        btnAdd.setOnClickListener(v -> {
            db.insertStudent(
                    etName.getText().toString(),
                    etCourse.getText().toString(),
                    Integer.parseInt(etMarks.getText().toString())
            );
            Toast.makeText(this, "Inserted", Toast.LENGTH_SHORT).show();
        });

        // ➤ VIEW
        btnView.setOnClickListener(v -> loadData());

        // ➤ UPDATE
        btnUpdate.setOnClickListener(v -> {
            db.updateStudent(
                    Integer.parseInt(etId.getText().toString()),
                    etName.getText().toString(),
                    etCourse.getText().toString(),
                    Integer.parseInt(etMarks.getText().toString())
            );
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
        });

        // ➤ DELETE
        btnDelete.setOnClickListener(v -> {
            db.deleteStudent(
                    Integer.parseInt(etId.getText().toString())
            );
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadData() {

        list.clear();

        Cursor cursor = db.getAllStudents();

        while (cursor.moveToNext()) {
            list.add(new Student(
                    cursor.getInt(0),   // ID
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3)
            ));
        }

        adapter = new StudentAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }
}