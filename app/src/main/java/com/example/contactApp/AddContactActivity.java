package com.example.contactApp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AddContactActivity extends AppCompatActivity {

    private EditText firstName, lastName, phone;
    private Intent intent;
    private DataBaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        phone = findViewById(R.id.phone);
        Button add = findViewById(R.id.add);
        Button cancel = findViewById(R.id.cancel);
        database = new DataBaseHelper(this);
        add.setOnClickListener(v -> {
            if (firstName.getText().toString().isEmpty() ||
                    lastName.getText().toString().isEmpty() ||
                    phone.getText().toString().isEmpty())
                Toast.makeText(AddContactActivity.this,
                        "All fields are required to add contact!!",
                        Toast.LENGTH_LONG).show();
            else {
                Contact contact = new Contact(firstName.getText().toString(), lastName.getText().toString(), Integer.parseInt(phone.getText().toString()));
                int contact_ID = database.addContact(contact);
                if(contact_ID != -1) {
                    Toast.makeText(this, "Contact created", Toast.LENGTH_SHORT).show();
                    intent = getIntent();
                    intent.putExtra("id", contact_ID);
                    intent.putExtra("firstName", firstName.getText().toString());
                    intent.putExtra("lastName", lastName.getText().toString());
                    intent.putExtra("phone", phone.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else{
                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setTitle("Already exists!")
                            .setMessage(firstName.getText().toString()+" "+lastName.getText().toString()+" have a contact in your list")
                            .setPositiveButton("OK", null)
                            .setCancelable(false).create().show();
                }
            }
        });

        cancel.setOnClickListener(v -> {
            intent = getIntent();
            setResult(RESULT_CANCELED, intent);
            finish();
        });
    }
}
