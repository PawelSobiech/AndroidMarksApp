package com.example.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MarksActivity extends AppCompatActivity {
    private ArrayList<String> tempList;
    private Adapter adapter;
    private int sum = 0;
    private double average;
    private ArrayList<Integer> selectedMarks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks);
        Button averageButton = findViewById(R.id.averageButton);
        RecyclerView subjectsRecyclerView = findViewById(R.id.marksRecyclerView);
        ArrayList<String> mSubjectsList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.subjectsArray)));
        tempList = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int data = Integer.parseInt(bundle.getString("data"));
            for (int i = 0; i < data && i < mSubjectsList.size(); i++) {
                tempList.add(mSubjectsList.get(i));
            }
        }

        selectedMarks = new ArrayList<>(tempList.size());
        if (savedInstanceState != null) {
            // Przywrócenie stanu przycisków radio
            selectedMarks = savedInstanceState.getIntegerArrayList("selected_marks");
        } else {
            selectedMarks = new ArrayList<>(tempList.size());
            for (int i = 0; i < tempList.size(); i++) {
                selectedMarks.add(2);
            }
        }
        averageButton.setOnClickListener(averageButtonListener);

        adapter = new Adapter(tempList);
        adapter.setSelectedMarks(selectedMarks);
        subjectsRecyclerView.setAdapter(adapter);
        subjectsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        calculateAverage();
    }

    private void calculateAverage() {
        sum = 0;
        for (int mark : selectedMarks) {
            sum += mark;
        }
        average = (double) sum / selectedMarks.size();
    }

    View.OnClickListener averageButtonListener = view -> {
        average = (double) sum / tempList.size();
        Toast toast = Toast.makeText(this, String.valueOf(average), Toast.LENGTH_SHORT);
        toast.show();
        Intent intent = new Intent(MarksActivity.this, MainActivity.class);
        intent.putExtra("average", Double.toString(average));
        startActivity(intent);
    };

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Zapisanie przycisków radio
        outState.putIntegerArrayList("selected_marks", adapter.getSelectedMarks());
    }
}
