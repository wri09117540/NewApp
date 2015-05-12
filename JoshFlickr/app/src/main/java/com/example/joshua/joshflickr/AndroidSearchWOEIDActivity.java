package com.example.joshua.joshflickr;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

/*
    Code from
    http://android-er.blogspot.co.uk/2012/03/search-woeid-from-httpqueryyahooapiscom.html
 */

public class AndroidSearchWOEIDActivity extends Activity {

    private List<TopLocationObject> topLocations;

    //Example for "New York"
//http://query.yahooapis.com/v1/public/yql?q=select*from geo.places where text="New York"&format=xml
    final String yahooapisBase = "http://query.yahooapis.com/v1/public/yql?q=select*from%20geo.places%20where%20text=";
    final String yahooapisFormat = "&format=xml";
    String yahooAPIsQuery;

    EditText place;
    EditText country;
    Button search;
    ListView listviewWOEID;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_search_woeid);

        //Needed to conduct HTTPClient in UI Thread
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        topLocations = new ArrayList<TopLocationObject>();

        place = (EditText)findViewById(R.id.place);
        country = (EditText)findViewById(R.id.country);
        search = (Button)findViewById(R.id.search);
        listviewWOEID = (ListView)findViewById(R.id.woeidlist);

        search.setOnClickListener(searchOnClickListener);

        //List item click that builds an array to send in Intent
        //This enables use of same activity but from different activity
        listviewWOEID.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String woeid = (String)parent.getItemAtPosition(position);
                String placeName = place.getText().toString();
                String countryName = country.getText().toString();

                topLocations.clear(); //Needs to clear list before new item
                topLocations.add(new TopLocationObject(placeName,countryName, R.drawable.search, woeid));

                Intent i = new Intent(AndroidSearchWOEIDActivity.this, SpecificLocationActivity.class);
                i.putExtra("EXTRA_TOP_LOCATION", topLocations.get(position));
                startActivity(i);
            }
        });
    }


    Button.OnClickListener searchOnClickListener
            = new Button.OnClickListener(){

        @Override
        public void onClick(View arg0) {
            //Now checks country has information in
            if(place.getText().toString().equals("") || country.getText().toString().equals("")){
                Toast.makeText(getBaseContext(),
                        "Enter place!",
                        Toast.LENGTH_LONG).show();
            }else{
                ArrayList<String> l = QueryYahooAPIs();

                ArrayAdapter<String> aa = new ArrayAdapter<String>(
                        getBaseContext(), android.R.layout.simple_list_item_1, l);

                listviewWOEID.setAdapter(aa);
            }
        }

    };

    private ArrayList<String> QueryYahooAPIs(){

        //Mod to except country
        String uriPlace = Uri.encode(place.getText().toString() + " " + country.getText().toString());

        yahooAPIsQuery = yahooapisBase
                + "%22" + uriPlace + "%22"
                + yahooapisFormat;

        String woeidString = QueryYahooWeather(yahooAPIsQuery);
        Document woeidDoc = convertStringToDocument(woeidString);
        return  parseWOEID(woeidDoc);

    }

    private ArrayList<String> parseWOEID(Document srcDoc){

        ArrayList<String> listWOEID = new ArrayList<String>();

        NodeList nodeListDescription = srcDoc.getElementsByTagName("woeid");
        if(nodeListDescription.getLength()>=0){
            for(int i=0; i<nodeListDescription.getLength(); i++){
                listWOEID.add(nodeListDescription.item(i).getTextContent());
            }
        }else{
            listWOEID.clear();
        }

        return listWOEID;
    }

    private Document convertStringToDocument(String src){
        Document dest = null;

        DocumentBuilderFactory dbFactory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder parser;

        try {
            parser = dbFactory.newDocumentBuilder();
            dest = parser.parse(new ByteArrayInputStream(src.getBytes()));
        } catch (ParserConfigurationException e1) {
            e1.printStackTrace();
            Toast.makeText(getBaseContext(),
                    e1.toString(), Toast.LENGTH_LONG).show();
        } catch (SAXException e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(),
                    e.toString(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(),
                    e.toString(), Toast.LENGTH_LONG).show();
        }

        return dest;

    }

    private String QueryYahooWeather(String queryString){

        String qResult = "";

        HttpClient httpClient = new DefaultHttpClient();

        //return Uri.encode(queryString);

        HttpGet httpGet = new HttpGet(queryString);

        try {
            HttpEntity httpEntity = httpClient.execute(httpGet).getEntity();

            if (httpEntity != null){
                InputStream inputStream = httpEntity.getContent();
                Reader in = new InputStreamReader(inputStream);
                BufferedReader bufferedreader = new BufferedReader(in);
                StringBuilder stringBuilder = new StringBuilder();

                String stringReadLine = null;

                while ((stringReadLine = bufferedreader.readLine()) != null) {
                    stringBuilder.append(stringReadLine + "\n");
                }

                qResult = stringBuilder.toString();
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

        return qResult;

    }

}