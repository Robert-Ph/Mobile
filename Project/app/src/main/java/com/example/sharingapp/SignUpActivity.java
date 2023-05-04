package com.example.sharingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        EditText dateOfBirth = findViewById(R.id.dateOfBirth);
        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get current date
                final Calendar current = Calendar.getInstance();
                int result_year = current.get(Calendar.YEAR);
                int result_month = current.get(Calendar.MONTH);
                int result_day = current.get(Calendar.DAY_OF_MONTH);

                //create DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(SignUpActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view  , int y, int m, int d) {
                        dateOfBirth.setText(d + "/"+(m+1)+"/"+y);
                    }
                },result_year,result_month,result_day);
                datePickerDialog.show();
            }
        });
    }
}