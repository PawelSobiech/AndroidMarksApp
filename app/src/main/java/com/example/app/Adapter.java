package com.example.app;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyAdapterViewHolder> {
    private ArrayList<String> mSubjectsList;
    private Activity mActivity;
    private RadioButtonClickListener radioButtonClickListener;

    public Adapter(Activity activity, ArrayList<String> subjectsList) {
        mSubjectsList = subjectsList;
        mActivity = activity;
    }
    private int lastSelectedGrade = 0;
    private int lastSelectedPosition = -1;
    public int getLastSelectedGrade() {
        return lastSelectedGrade;
    }

    public void setLastSelectedGrade(int grade) {
        lastSelectedGrade = grade;
    }
    public int getLastSelectedPosition() {
        return lastSelectedPosition;
    }
    public void setLastSelectedPosition(int pos) {
        lastSelectedPosition = pos;
    }
    @NonNull
    @Override
    public MyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowRootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new MyAdapterViewHolder(rowRootView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return mSubjectsList.size();
    }

    public interface RadioButtonClickListener {
        void onRadioButtonClicked(int value, int position); // Dodajemy argument position
    }

    public void setRadioButtonClickListener(RadioButtonClickListener listener) {
        radioButtonClickListener = listener;
    }

    class MyAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RadioButton gradeTwoRadioButton;
        RadioButton gradeThreeRadioButton;
        RadioButton gradeFourRadioButton;
        RadioButton gradeFiveRadioButton;

        public MyAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            gradeTwoRadioButton = itemView.findViewById(R.id.gradeTwoRadioButton);
            gradeThreeRadioButton = itemView.findViewById(R.id.gradeThreeRadioButton);
            gradeFourRadioButton = itemView.findViewById(R.id.gradeFourRadioButton);
            gradeFiveRadioButton = itemView.findViewById(R.id.gradeFiveRadioButton);

            gradeTwoRadioButton.setOnClickListener(this);
            gradeThreeRadioButton.setOnClickListener(this);
            gradeFourRadioButton.setOnClickListener(this);
            gradeFiveRadioButton.setOnClickListener(this);
        }

        public void bindData(int position) {
            // Wykorzystaj pozycję, aby ustawić odpowiednie wartości RadioButton
            // w zależności od stanu danych lub innych kryteriów
        }

        @Override
        public void onClick(View v) {
            onRadioButtonClicked(v);
        }

        private void onRadioButtonClicked(View view) {
            if (radioButtonClickListener != null) {
                boolean checked = ((RadioButton) view).isChecked();
                int value = 0;
                switch (view.getId()) {
                    case R.id.gradeTwoRadioButton:
                        if (checked)
                            value = 2;
                        break;
                    case R.id.gradeThreeRadioButton:
                        if (checked)
                            value = 3;
                        break;
                    case R.id.gradeFourRadioButton:
                        if (checked)
                            value = 4;
                        break;
                    case R.id.gradeFiveRadioButton:
                        if (checked)
                            value = 5;
                        break;
                }
                radioButtonClickListener.onRadioButtonClicked(value, getAdapterPosition()); // Przekazujemy również pozycję klikniętego elementu
            }
        }
    }
}
