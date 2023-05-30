package com.example.app;

import android.app.Activity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Adapter extends RecyclerView.Adapter<Adapter.MyAdapterViewHolder> {
    private ArrayList<String> mSubjectsList;
    private Activity mActivity;
    private RadioButtonClickListener radioButtonClickListener;
    private ArrayList<Integer> selectedGrades;
    private Map<Integer, Integer> lastSelectedPositions;
    private TextView mTv;

    public Adapter(Activity activity, ArrayList<String> subjectsList) {
        mSubjectsList = subjectsList;
        mActivity = activity;
        selectedGrades = new ArrayList<>();
        lastSelectedPositions = new HashMap<>();
        for (int i = 0; i < mSubjectsList.size(); i++) {
            selectedGrades.add(0);
            lastSelectedPositions.put(i, 0);
        }
    }
    public void setSelectedGrades(ArrayList<Integer> grades) {
        selectedGrades = grades;
        notifyDataSetChanged();
    }

    public ArrayList<Integer> getSelectedGrades() {
        return selectedGrades;
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
        void onRadioButtonClicked(int value, int position);
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
            mTv = itemView.findViewById(R.id.subjectName);

            gradeTwoRadioButton.setOnClickListener(this);
            gradeThreeRadioButton.setOnClickListener(this);
            gradeFourRadioButton.setOnClickListener(this);
            gradeFiveRadioButton.setOnClickListener(this);
        }
        //bląd ze scrollowaniem
        public void bindData(int position) {
            int grade = selectedGrades.get(position);
            setCheckedRadioButton(grade);
            String subject = mSubjectsList.get(position);
            mTv.setText(subject);
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
                int position = getAdapterPosition();
                selectedGrades.set(position, value);
                lastSelectedPositions.put(position, value);
                radioButtonClickListener.onRadioButtonClicked(value, position);
                notifyDataSetChanged();
            }
        }
        private void setCheckedRadioButton(int grade) {
            //błąd - w radio group da się zrobić set checked, to sprawdzić, ustawić domyślną wartość oceny
            gradeTwoRadioButton.setChecked(grade == 2);
            gradeThreeRadioButton.setChecked(grade == 3);
            gradeFourRadioButton.setChecked(grade == 4);
            gradeFiveRadioButton.setChecked(grade == 5);
        }
    }
}
