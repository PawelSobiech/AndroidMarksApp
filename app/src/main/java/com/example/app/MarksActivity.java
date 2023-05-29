package com.example.app;

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

public class MarksActivity extends AppCompatActivity implements Adapter.RadioButtonClickListener {
    private ArrayList<String> mSubjectsList;
    private ArrayList<String> tempList;
    private Button averageButton;
    private Adapter adapter;
    private Toast toast;
    private int sum = 0;
    private double average;
    private ArrayList<Integer> selectedGrades;
    private static final String SELECTED_GRADES_KEY = "selected_grades";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks);
        averageButton = findViewById(R.id.averageButton);
        RecyclerView subjectsRecyclerView = findViewById(R.id.gradesRecyclerView);
        mSubjectsList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.subjectsArray)));
        tempList = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            int data = Integer.parseInt(bundle.getString("data"));
            for (int i = 0; i < data && i < mSubjectsList.size(); i++) {
                tempList.add(mSubjectsList.get(i));
            }
        }

        selectedGrades = new ArrayList<>(tempList.size());
        if (savedInstanceState != null) {
            // przywrocenie stanu przyciskow radio
            selectedGrades = savedInstanceState.getIntegerArrayList(SELECTED_GRADES_KEY);
        } else {
            selectedGrades = new ArrayList<>(tempList.size());
            for (int i = 0; i < tempList.size(); i++) {
                selectedGrades.add(0);
            }
        }
        averageButton.setOnClickListener(averageButtonListener);

        adapter = new Adapter(this, tempList);
        adapter.setRadioButtonClickListener(this);
        adapter.setSelectedGrades(selectedGrades);
        subjectsRecyclerView.setAdapter(adapter);
        subjectsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        calculateAverage();
    }

    @Override
    public void onRadioButtonClicked(int value, int position) {
        selectedGrades.set(position, value);
        calculateAverage();
    }

    private void calculateAverage() {
        sum = 0;
        for (int grade : selectedGrades) {
            sum += grade;
        }
        average = (double) sum / selectedGrades.size();
    }

    View.OnClickListener averageButtonListener = view -> {
        average = (double) sum / tempList.size();
        toast = Toast.makeText(this, String.valueOf(average) ,Toast.LENGTH_SHORT);
        toast.show();
        Intent intent = new Intent(MarksActivity.this, MainActivity.class);
        intent.putExtra("average", Double.toString(average));
        startActivity(intent);
    };
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //zapisanie przyciskow radio
        outState.putIntegerArrayList(SELECTED_GRADES_KEY, adapter.getSelectedGrades());
    }
}
