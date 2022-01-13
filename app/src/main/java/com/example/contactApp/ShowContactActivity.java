package com.example.contactApp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShowContactActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_contact);
        TextView firstName = findViewById(R.id.FN);
        TextView lastName = findViewById(R.id.LN);
        TextView phone = findViewById(R.id.phoneNumber);
        TextView unique = findViewById(R.id.uniqueNumber);
        Button back = findViewById(R.id.back);
        Intent intent = getIntent();
        int id = intent.getIntExtra("showId", -1);
        String FN = intent.getStringExtra("showFirstName");
        String LN = intent.getStringExtra("showLastName");
        int phoneNumber = intent.getIntExtra("showPhoneNumber", -1);
        unique.setText(id + "");
        firstName.setText(FN);
        lastName.setText(LN);
        phone.setText(phoneNumber + "");

        back.setOnClickListener(v -> {
            setResult(RESULT_OK, intent);
            finish();
        });
    }
}