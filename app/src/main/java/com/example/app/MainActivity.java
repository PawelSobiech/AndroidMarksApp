package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
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
    boolean[] buttonCheck = {false, false, false};

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
        toastEmpty = Toast.makeText(this, MainActivity.this.getString(R.string.inputEmptyError) ,Toast.LENGTH_SHORT);
        toastMarks = Toast.makeText(this, MainActivity.this.getString(R.string.marksError) ,Toast.LENGTH_SHORT);

        Bundle bundleMarks = getIntent().getExtras();
        if (bundleMarks != null) {
            double data = Double.parseDouble(bundleMarks.getString("average"));

            if (data >= 3) {
                finalButton.setText(MainActivity.this.getString(R.string.superMsg));
                finalToast = Toast.makeText(this, MainActivity.this.getString(R.string.finalMsgp) ,Toast.LENGTH_LONG);
            } else {
                finalButton.setText( MainActivity.this.getString(R.string.failedMsg));
                finalToast = Toast.makeText(this, MainActivity.this.getString(R.string.finalMsgn) ,Toast.LENGTH_LONG);
            }
            String text =  MainActivity.this.getString(R.string.averageMsg) + data;
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

        updateButtonVisibility();
    }
    View.OnFocusChangeListener nameListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(!hasFocus)
            {
                validateTextInput(nameTextEdit);
                updateButtonVisibility();
            }
        }
    };
    View.OnFocusChangeListener surnameListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(!hasFocus)
            {
                validateTextInput(surnameTextEdit);
                updateButtonVisibility();
            }
        }
    };
    View.OnFocusChangeListener marksListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            try{
                if (!hasFocus) {
                    validateMarks();
                    updateButtonVisibility();
                }
            }
            catch (NumberFormatException e) {
                validateMarks();
                updateButtonVisibility();
                marksTextEdit.requestFocus();
            }
        }
    };
    private void updateButtonVisibility() {
        if (buttonCheck[0] && buttonCheck[1] && buttonCheck[2]) {
            marksButton.setVisibility(View.VISIBLE);
        }
        else {
            marksButton.setVisibility(View.INVISIBLE);
        }
    }
    private void validateTextInput(EditText a)
    {
        if(TextUtils.isEmpty(a.getText().toString()))
        {
            a.setError(MainActivity.this.getString(R.string.inputEmptyError));
            compareEditTexts(a,1);
            toastEmpty.show();
        }
        else
        {
            compareEditTexts(a, 2);
        }
    }

    private boolean validateMarks() {
        String marks = marksTextEdit.getText().toString().trim();
        if (TextUtils.isEmpty(marks) || !TextUtils.isDigitsOnly(marks)) {
            marksTextEdit.setError(MainActivity.this.getString(R.string.marksError));
            buttonCheck[2] = false;
            toastMarks.show();
            return false;
        } else {
            int temp = Integer.parseInt(marks);
            if (temp < 5 || temp > 15) {
                marksTextEdit.setError(MainActivity.this.getString(R.string.marksError));
                buttonCheck[2] = false;
                toastMarks.show();
                return false;
            } else {
                buttonCheck[2] = true;
                return true;
            }
        }
    }
    private void compareEditTexts(EditText a, int place)
    {
        EditText t1 = findViewById(R.id.nameEditText);
        EditText t2 = findViewById(R.id.surnameEditText);
        if(place == 1) {
            if (a.getText().toString().equals(t1.getText().toString())) {
                buttonCheck[0] = false;
            }
            else if (a.getText().toString().equals(t2.getText().toString())) {
                buttonCheck[1] = false;
            }
        }
        else if(place == 2)
        {
            if (a.getText().toString().equals(t1.getText().toString())) {
                buttonCheck[0] = true;
            }
            else if (a.getText().toString().equals(t2.getText().toString())) {
                buttonCheck[1] = true;
            }
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Zapisz stan widoku
        outState.putInt("marksButtonVisibility", marksButton.getVisibility());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Przywróć stan widoku
        int marksButtonVisibility = savedInstanceState.getInt("marksButtonVisibility");
        marksButton.setVisibility(marksButtonVisibility);
    }
    View.OnClickListener marksButtonListener = view -> {
        Intent intent = new Intent(MainActivity.this, MarksActivity.class);
        intent.putExtra("data", marksTextEdit.getText().toString());
        startActivity(intent);
    };
    View.OnClickListener finalButtonListener = view -> {
      finalToast.show();
      this.finishAffinity();
    };
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (getCurrentFocus() == null) {
            return;
        }
        int editTextId = getCurrentFocus().getId();
        switch (editTextId) {
            case R.id.nameEditText:
                validateTextInput(nameTextEdit);
                break;
            case R.id.surnameEditText:
                validateTextInput(surnameTextEdit);
                break;
            case R.id.marksEditText:
                validateMarks();
                break;
            default:
                break;
        }
        updateButtonVisibility();
    }
    @Override
    public void afterTextChanged(Editable editable) {
    }
}