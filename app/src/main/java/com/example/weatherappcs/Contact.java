package com.example.weatherappcs;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Contact extends AppCompatActivity {

    public EditText mEmail;
    public TextView mSubject;
    public TextView mMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mEmail = (EditText)findViewById(R.id.mailID);
        mMessage = (TextView) findViewById(R.id.messageID);
        mSubject = (TextView) findViewById(R.id.subjectID);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMail();
            }
        });
    }

    private void sendMail() {

        String mail = mEmail.getText().toString().trim();
        String message = mMessage.getText().toString().trim();
        String subject = mSubject.getText().toString().trim();

        JavaMailAPI javaMailAPI = new JavaMailAPI(this,mail,subject,message);

        javaMailAPI.execute();

    }
}
