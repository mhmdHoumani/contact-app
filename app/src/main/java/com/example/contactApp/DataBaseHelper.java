package com.example.contactApp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ContactManager";
    private static final String TABLE_CONTACTS = "contacts";

    private static final String KEY_ID = "contact_ID";
    private static final String KEY_FIRST_NAME = "firstName";
    private static final String KEY_LAST_NAME = "lastName";
    private static final String KEY_PHONE_NO = "phone_no";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_CONTACTS + "( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_FIRST_NAME + " TEXT,"
                + KEY_LAST_NAME + " TEXT,"
                + KEY_PHONE_NO + " INTEGER)";
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

    public int addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT " + KEY_FIRST_NAME + ", " + KEY_LAST_NAME
                + " from " + TABLE_CONTACTS
                + " where " + KEY_FIRST_NAME + "=?"
                + " AND " + KEY_LAST_NAME + "=?"
                + " AND " + KEY_PHONE_NO + "=?";
        String[] argsString = {contact.getFirstName(), contact.getLastName(), String.valueOf(contact.getPhone_no())};
        Cursor cursor = db.rawQuery(sql, argsString);
        if(!cursor.moveToFirst()) {
            ContentValues values = new ContentValues();
            values.put(KEY_FIRST_NAME, contact.getFirstName());
            values.put(KEY_LAST_NAME, contact.getLastName());
            values.put(KEY_PHONE_NO, contact.getPhone_no());
            long contact_ID = db.insert(TABLE_CONTACTS, null, values);
            db.close();
            return (int) contact_ID;
        }
        return -1;
    }

    public List<Contact> getAllContacts() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_CONTACTS;
        Cursor cursor = db.rawQuery(query, null);
        List<Contact> contacts = new ArrayList();
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        Integer.parseInt(cursor.getString(3)));
                contacts.add(contact);
            } while (cursor.moveToNext());
        }
        db.close();
        return contacts;
    }

    public int deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = KEY_ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(contact.getContact_ID())};
        return db.delete(TABLE_CONTACTS, where, whereArgs);
    }
}
