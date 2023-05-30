package com.example.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Adapter extends RecyclerView.Adapter<Adapter.MyAdapterViewHolder> {
    private final ArrayList<String> mSubjectsList;
    private ArrayList<Integer> selectedMarks;
    private final Map<Integer, Integer> lastSelectedPositions;

    public Adapter(ArrayList<String> subjectsList) {
        mSubjectsList = subjectsList;
        selectedMarks = new ArrayList<>();
        lastSelectedPositions = new HashMap<>();
        for (int i = 0; i < mSubjectsList.size(); i++) {
            selectedMarks.add(0);
            lastSelectedPositions.put(i, 0);
        }
    }

    public void setSelectedMarks(ArrayList<Integer> marks) {
        selectedMarks = marks;
        notifyDataSetChanged();
    }

    public ArrayList<Integer> getSelectedMarks() {
        return selectedMarks;
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

    class MyAdapterViewHolder extends RecyclerView.ViewHolder {
        RadioButton markTwoRadioButton;
        RadioButton markThreeRadioButton;
        RadioButton markFourRadioButton;
        RadioButton markFiveRadioButton;
        RadioGroup markRadioGroup;
        TextView mTv;

        public MyAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            markTwoRadioButton = itemView.findViewById(R.id.markTwoRadioButton);
            markThreeRadioButton = itemView.findViewById(R.id.markThreeRadioButton);
            markFourRadioButton = itemView.findViewById(R.id.markFourRadioButton);
            markFiveRadioButton = itemView.findViewById(R.id.markFiveRadioButton);
            markRadioGroup = itemView.findViewById(R.id.marksRadioGroup);
            mTv = itemView.findViewById(R.id.subjectName);

            markRadioGroup.setOnCheckedChangeListener((radioGroup, checkedId) -> {
                int value = 0;
                switch (checkedId) {
                    case R.id.markTwoRadioButton:
                        value = 2;
                        break;
                    case R.id.markThreeRadioButton:
                        value = 3;
                        break;
                    case R.id.markFourRadioButton:
                        value = 4;
                        break;
                    case R.id.markFiveRadioButton:
                        value = 5;
                        break;
                }
                int position = getAdapterPosition();
                selectedMarks.set(position, value);
                lastSelectedPositions.put(position, value);
            });
        }

        public void bindData(int position) {
            int mark = selectedMarks.get(position);
            setCheckedRadioButton(mark);
            String subject = mSubjectsList.get(position);
            mTv.setText(subject);
        }

        private void setCheckedRadioButton(int mark) {
            markTwoRadioButton.setChecked(mark == 2);
            markThreeRadioButton.setChecked(mark == 3);
            markFourRadioButton.setChecked(mark == 4);
            markFiveRadioButton.setChecked(mark == 5);
        }
    }
}
