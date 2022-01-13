package com.example.contactApp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int add_Contact_CODE = 123, show_Contact_CODE = 456;
    private List<Contact> myContacts;
    private ArrayAdapter<String> adapter;
    private DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DataBaseHelper(this);
        myContacts = db.getAllContacts();
        Toolbar myMenu = findViewById(R.id.toolbar);
        ListView listContact = findViewById(R.id.myContact);
        setSupportActionBar(myMenu);
        adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, myContacts);
        listContact.setAdapter(adapter);

        listContact.setOnItemClickListener((parent, view, position, id) -> {
            Contact contactSelected = myContacts.get(position);
            int contact_id = contactSelected.getContact_ID();
            String FN = contactSelected.getFirstName();
            String LN = contactSelected.getLastName();
            int phone = contactSelected.getPhone_no();
            Intent intent = new Intent(this, ShowContactActivity.class);
            intent.putExtra("showId", contact_id);
            intent.putExtra("showFirstName", FN);
            intent.putExtra("showLastName", LN);
            intent.putExtra("showPhoneNumber", phone);
            startActivityForResult(intent, show_Contact_CODE);
        });

        listContact.setOnItemLongClickListener((parent, view, position, id) -> {
            Contact contact = myContacts.get(position);
            /*AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Removing contact")
                    .setMessage("Are you sure you want to remove it?")
                    .setNegativeButton("NO", null)
                    .setPositiveButton("YES", (dialog, which) -> {})
                    .setCancelable(false).create().show();*/
            int i = db.deleteContact(contact);
            if (i > 0) {
                myContacts.remove(contact);
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
            }
            return false;
        });
    }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            getMenuInflater().inflate(R.menu.mymenu, menu);
            return super.onCreateOptionsMenu(menu);
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onOptionsItemSelected (@NonNull MenuItem item){
            if (item.getItemId() == R.id.addContact) {
                //Toast.makeText(this, "creating contact", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, AddContactActivity.class);
                startActivityForResult(intent, add_Contact_CODE);
            }
            return super.onOptionsItemSelected(item);
        }

        protected void onActivityResult ( int requestCode, int resultCode, Intent intent){
            super.onActivityResult(requestCode, resultCode, intent);
            if (requestCode == add_Contact_CODE) {
                if (resultCode == RESULT_OK) {
                    int contact_ID = intent.getIntExtra("id", -1);
                    String firstName = intent.getStringExtra("firstName");
                    String lastName = intent.getStringExtra("lastName");
                    String phone = intent.getStringExtra("phone");
                    Contact contact = new Contact(contact_ID, firstName, lastName, Integer.parseInt(phone));
                    myContacts.add(contact);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
