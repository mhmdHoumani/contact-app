package com.example.contactApp;

public class Contact {
    private int contact_ID, phone_no;
    private String firstName, lastName;

    public Contact(String firstName, String lastName, int phone_no) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone_no = phone_no;
    }

    public Contact(int contact_ID, String firstName, String lastName, int phone_no) {
        this.contact_ID = contact_ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone_no = phone_no;
    }


    public int getContact_ID() {
        return this.contact_ID;
    }

    public int getPhone_no() {
        return this.phone_no;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    @Override
    public String toString() {
        return this.firstName + " " + this.lastName;
    }
}
