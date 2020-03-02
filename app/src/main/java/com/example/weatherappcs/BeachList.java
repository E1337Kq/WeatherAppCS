package com.example.weatherappcs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ListMenuItemView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BeachList extends AppCompatActivity {

    private static final String TAG="BeachList";
    private ArrayAdapter adapter;
    private TextView selection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beach_list);

        ListView list = (ListView)findViewById(R.id.theList);
        EditText theFilter = (EditText)findViewById(R.id.searchFilter);
        Log.d(TAG, "OnCreate: Started.");

        final ArrayList<String> names = new ArrayList<>();
        names.add("Richard's Head Beach - Budva");
        names.add("Mogren Beach - Budva");
        names.add("Red Beach - Bar");
        names.add("Bigovica Beach - Bar");

        String listString = "";

        for (String s : names)
        {
            listString += s + "\t";
        }

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String tHolder = names.get(position);

                Intent myIntent = new Intent(view.getContext(), MainActivity.class);
                myIntent.putExtra("tHolder", tHolder);
                startActivityForResult(myIntent, 0);
            }
        });


        adapter = new ArrayAdapter(this, R.layout.list_item_layout, names);
        list.setAdapter(adapter);

        theFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                (BeachList.this).adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}