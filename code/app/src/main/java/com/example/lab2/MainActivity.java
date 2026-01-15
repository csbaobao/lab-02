package com.example.lab2;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> cities;
    private ArrayAdapter<String> adapter;
    private int sel = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.cityList);
        Button addBtn = findViewById(R.id.addBtn);
        Button delBtn = findViewById(R.id.delBtn);

        cities = new ArrayList<>();
        cities.add("Edmonton");
        cities.add("Vancouver");

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_single_choice,
                cities);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            sel = position;
            listView.setItemChecked(position, true);
        });

        addBtn.setOnClickListener(v -> showAddDialog());

        delBtn.setOnClickListener(v -> {
            if (sel == -1) {
                Toast.makeText(this, "Please select a city first", Toast.LENGTH_SHORT).show();
                return;
            }

            String name = cities.get(sel);
            new AlertDialog.Builder(this)
                    .setTitle("Delete City")
                    .setMessage("Delete: " + name + " ?")
                    .setPositiveButton("DELETE", (d, w) -> {
                        cities.remove(sel);
                        sel = -1;
                        adapter.notifyDataSetChanged();
                        listView.clearChoices();
                    })
                    .setNegativeButton("CANCEL", null)
                    .show();
        });
    }

    private void showAddDialog() {
        EditText input = new EditText(this);
        input.setHint("City name");

        new AlertDialog.Builder(this)
                .setTitle("Add City")
                .setView(input)
                .setPositiveButton("CONFIRM", (dialog, which) -> {
                    String name = input.getText().toString().trim();

                    if (name.length() == 0) {
                        Toast.makeText(this, "Invalid name", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (cities.contains(name)) {
                        Toast.makeText(this, "City already exists", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    cities.add(name);
                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton("CANCEL", null)
                .show();
    }
}
