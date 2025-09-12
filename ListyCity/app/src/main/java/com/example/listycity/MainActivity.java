package com.example.listycity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    EditText cityInput;
    Button addButton, confirmButton, deleteButton;

    int selectedPosition = -1; // keeps track of selected city

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_list);
        cityInput = findViewById(R.id.city_input);
        addButton = findViewById(R.id.add_button);
        confirmButton = findViewById(R.id.confirm_button);
        deleteButton = findViewById(R.id.delete_button);

        String[] cities = {"Edmonton", "Vancouver","Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};
        dataList = new ArrayList<>(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        // Handle list item click and highlight selection
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPosition = position;

                // Highlight selected item
                for(int i=0;i<cityList.getChildCount();i++){
                    cityList.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                }
                view.setBackgroundColor(Color.LTGRAY);
            }
        });

        // ADD CITY: show EditText + CONFIRM
        addButton.setOnClickListener(v -> {
            cityInput.setVisibility(View.VISIBLE);
            confirmButton.setVisibility(View.VISIBLE);
            cityInput.requestFocus();
        });

        // CONFIRM: add city to the list
        confirmButton.setOnClickListener(v -> {
            String newCity = cityInput.getText().toString().trim();
            if (!newCity.isEmpty()) {
                dataList.add(newCity);
                cityAdapter.notifyDataSetChanged();
                cityInput.setText("");
            }
            cityInput.setVisibility(View.GONE);
            confirmButton.setVisibility(View.GONE);
        });

        // DELETE CITY: remove selected city
        deleteButton.setOnClickListener(v -> {
            if (selectedPosition != -1) {
                dataList.remove(selectedPosition);
                cityAdapter.notifyDataSetChanged();
                selectedPosition = -1;

                // Reset background of all items
                for(int i=0;i<cityList.getChildCount();i++){
                    cityList.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                }
            }
        });
    }
}
