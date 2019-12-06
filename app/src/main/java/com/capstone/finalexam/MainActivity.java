package com.capstone.finalexam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
private EditText Text;
private Button Add;
private ListView listView;
private static ArrayList<String> value = new ArrayList ();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        Text=findViewById (R.id.editText);
        Add=findViewById (R.id.button);
        Add.setOnClickListener (this);
        listView=findViewById (R.id.list);
        populate ();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                if(value.contains(Text.getText().toString())) {
                    Toast.makeText(MainActivity.this,"Item already in list", Toast.LENGTH_LONG).show();
                } else {
                    value.add(Text.getText ().toString ());
                    populate ();
                }



        }
    }

    private void populate(){
        ArrayAdapter<String> placesAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, value);
        listView.setAdapter(placesAdapter);
        listView.setOnItemClickListener(listClickedHandler);
    }

    private AdapterView.OnItemClickListener listClickedHandler = (parent, v, position, id) -> {

        Intent i = new Intent (getApplicationContext (), TitlesActivity.class);
        Log.d ("test", "onItemClick: "+value.get (position));
        i.putExtra ("Title", value.get (position));
        startActivity (i);
    };

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putStringArrayList ("list", value);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        value = savedInstanceState.getStringArrayList ("list");
    }
}
