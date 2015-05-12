package com.example.joshua.joshflickr;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private Button btnLocations;
    private Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLocations = (Button)findViewById(R.id.btnLocations);
        btnSearch = (Button)findViewById(R.id.btnSearch);

        btnLocations.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //your code here!
                Intent i = new Intent(MainActivity.this, LocationsActivity.class);
                startActivity(i);
            }
        });

        btnSearch.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //your code here!
                Intent i = new Intent(MainActivity.this, AndroidSearchWOEIDActivity.class);
                startActivity(i);
            }
        });
    }
}
