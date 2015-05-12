package com.example.joshua.joshflickr;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class LocationsActivity extends ActionBarActivity {

    private ListView listLocations;

    private List<TopLocationObject> topLocations;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);

        listLocations = (ListView)findViewById(R.id.listLocations);

        topLocations = new ArrayList<TopLocationObject>();

        AddLocations();

        LocationAdapter adapter = new LocationAdapter(topLocations);
        listLocations.setAdapter(adapter);

        listLocations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TopLocationObject location = topLocations.get(position);

                Intent i = new Intent(LocationsActivity.this, SpecificLocationActivity.class);
                i.putExtra("EXTRA_TOP_LOCATION", topLocations.get(position));
                startActivity(i);
            }
        });

    }

    private class LocationAdapter extends ArrayAdapter<TopLocationObject> {

        public LocationAdapter(List<TopLocationObject> items) {
            super(LocationsActivity.this, 0, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(
                        R.layout.row_location, null);
            }

            ImageView imgFlag = (ImageView)convertView.findViewById(R.id.imgFlag);
            TextView txtCity = (TextView)convertView.findViewById(R.id.txtCity);
            TextView txtCountry = (TextView)convertView.findViewById(R.id.txtCountry);

            TopLocationObject location = topLocations.get(position);

            txtCity.setText(location.getCityName());
            txtCountry.setText(location.getCountryName());
            imgFlag.setImageResource(location.getFlagResource());


            return convertView;
        }// end get view

    }// end adapter class

    private void AddLocations(){

        topLocations.add(new TopLocationObject("Bath","United Kingdom", R.drawable.united_kingdom, "12056"));
        topLocations.add(new TopLocationObject("London Kong","United Kingdom", R.drawable.united_kingdom, "44418"));
        topLocations.add(new TopLocationObject("Edinburgh","United Kingdom", R.drawable.united_kingdom, "19344"));
        topLocations.add(new TopLocationObject("Hong Kong","China", R.drawable.china, "24865698"));
        topLocations.add(new TopLocationObject("Shanghai","China", R.drawable.china, "2151849"));
        topLocations.add(new TopLocationObject("Paris","France", R.drawable.france, "615702"));
        topLocations.add(new TopLocationObject("Berlin","Germany", R.drawable.germany, "638242"));
        topLocations.add(new TopLocationObject("Moscow","Russia", R.drawable.russia, "2122265"));
        topLocations.add(new TopLocationObject("New York","USA", R.drawable.united_states, "2459115"));
        topLocations.add(new TopLocationObject("Washington","USA", R.drawable.united_states, "2514815"));
    }

}
