package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements TextWatcher {

    EditText nameTextEdit;
    EditText surnameTextEdit;
    EditText marksTextEdit;
    Button marksButton;
    Button finalButton;
    TextView averageText;
    Toast toastEmpty;
    Toast toastMarks;
    Toast finalToast;
    private EditText focusedEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameTextEdit = findViewById(R.id.nameEditText);
        surnameTextEdit = findViewById(R.id.surnameEditText);
        marksTextEdit = findViewById(R.id.marksEditText);
        marksButton = findViewById(R.id.button);
        finalButton = findViewById(R.id.finalButton);
        averageText = findViewById(R.id.averageText);
        toastEmpty = Toast.makeText(this, getString(R.string.inputEmptyError), Toast.LENGTH_SHORT);
        toastMarks = Toast.makeText(this, getString(R.string.marksError), Toast.LENGTH_SHORT);

        Bundle bundleMarks = getIntent().getExtras();
        if (bundleMarks != null) {
            double data = Double.parseDouble(bundleMarks.getString("average"));

            if (data >= 3) {
                finalButton.setText(getString(R.string.superMsg));
                finalToast = Toast.makeText(this, getString(R.string.finalMsgp), Toast.LENGTH_LONG);
            } else {
                finalButton.setText(getString(R.string.failedMsg));
                finalToast = Toast.makeText(this, getString(R.string.finalMsgn), Toast.LENGTH_LONG);
            }
            String text = getString(R.string.averageMsg) + data;
            averageText.setText(text);
            finalButton.setVisibility(View.VISIBLE);
            averageText.setVisibility(View.VISIBLE);
        }

        nameTextEdit.setOnFocusChangeListener(nameListener);
        surnameTextEdit.setOnFocusChangeListener(surnameListener);
        marksTextEdit.setOnFocusChangeListener(marksListener);
        nameTextEdit.addTextChangedListener(this);
        surnameTextEdit.addTextChangedListener(this);
        marksTextEdit.addTextChangedListener(this);

        marksButton.setOnClickListener(marksButtonListener);
        finalButton.setOnClickListener(finalButtonListener);
    }

    private void updateButtonVisibility() {
        boolean isNameValid = validateTextInput(nameTextEdit);
        boolean isSurnameValid = validateTextInput(surnameTextEdit);
        boolean isMarksValid = validateMarks();

        if (isNameValid && isSurnameValid && isMarksValid) {
            marksButton.setVisibility(View.VISIBLE);
        } else {
            marksButton.setVisibility(View.INVISIBLE);
        }
    }

    private boolean validateMarks() {
        String marks = marksTextEdit.getText().toString().trim();

        if (TextUtils.isEmpty(marks) && focusedEditText == marksTextEdit) {
            marksTextEdit.setError(getString(R.string.marksError));
            return false;
        } else {
            int temp;
            try {
                temp = Integer.parseInt(marks);
            } catch (NumberFormatException e) {
                if (focusedEditText == marksTextEdit) {
                    marksTextEdit.setError(getString(R.string.marksError));
                }
                return false;
            }

            if (temp < 5 || temp > 15) {
                if (focusedEditText == marksTextEdit) {
                    marksTextEdit.setError(getString(R.string.marksError));
                }
                return false;
            }
        }

        marksTextEdit.setError(null);
        return true;
    }

    private boolean validateTextInput(EditText editText) {
        String text = editText.getText().toString().trim();
        if (TextUtils.isEmpty(text) && focusedEditText == editText) {
            editText.setError(getString(R.string.inputEmptyError));
            return false;
        } else {
            editText.setError(null);
            return true;
        }
    }

    View.OnFocusChangeListener surnameListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                validateTextInput(surnameTextEdit);
            }
            focusedEditText = hasFocus ? surnameTextEdit : null;
            updateButtonVisibility();
        }
    };

    View.OnFocusChangeListener nameListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                validateTextInput(nameTextEdit);
            }
            focusedEditText = hasFocus ? nameTextEdit : null;
            updateButtonVisibility();
        }
    };

    View.OnFocusChangeListener marksListener = (v, hasFocus) -> {
        if (!hasFocus) {
            validateMarks();
        }
        focusedEditText = hasFocus ? marksTextEdit : null;
        updateButtonVisibility();
    };

    View.OnClickListener marksButtonListener = view -> {
        Intent intent = new Intent(MainActivity.this, MarksActivity.class);
        intent.putExtra("data", marksTextEdit.getText().toString());
        startActivity(intent);
    };

    View.OnClickListener finalButtonListener = view -> {
        finalToast.show();
        finishAffinity();
    };

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        updateButtonVisibility();
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }
}
