package com.example.joshua.joshflickr;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class SinglePhoto extends ActionBarActivity {

    private ImageView singleImage;
    private TextView photoTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_photo);

        //Gets the image ID
        Intent intent = getIntent();
        String URL = intent.getExtras().getString("PHOTO_URL");
        String Title = intent.getExtras().getString("PHOTO_TITLE");

        singleImage = (ImageView)findViewById(R.id.imgSinglePhoto);
        photoTitle = (TextView)findViewById(R.id.txtPhotoTitle);

        photoTitle.setText(Title);
        Picasso.with(this).load(URL).into(singleImage);

    }
}
