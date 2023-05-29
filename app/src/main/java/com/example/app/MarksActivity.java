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
import java.util.concurrent.TimeUnit;

public class MarksActivity extends AppCompatActivity implements Adapter.RadioButtonClickListener {
    private ArrayList<String> mSubjectsList;
    private ArrayList<String> tempList;
    private Button averageButton;
    private Adapter adapter;
    private Toast toast;
    private int sum;
    private double average;

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

        averageButton.setOnClickListener(averageButtonListener);

        adapter = new Adapter(this, tempList);
        adapter.setRadioButtonClickListener(this);
        subjectsRecyclerView.setAdapter(adapter);
        subjectsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    public void onRadioButtonClicked(int value) {
        sum += value;
    }
    View.OnClickListener averageButtonListener = view -> {
        average = (double) sum / tempList.size();
        toast = Toast.makeText(this, String.valueOf(average) ,Toast.LENGTH_SHORT);
        toast.show();
        Intent intent = new Intent(MarksActivity.this, MainActivity.class);
        intent.putExtra("average", Double.toString(average));
        startActivity(intent);
    };
}
