package com.example.joshua.joshflickr;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Downloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class SpecificLocationActivity extends ActionBarActivity {

    private TextView lbLocationTitle;
    private TextView lbCountry;
    private ImageView imgSpecificFlag;
    private ImageView imgPhoto;

    private GridView gridView;
    private List<FlickrResponse.FlickrPhoto> photos;

    TopLocationObject location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_location);

        location = (TopLocationObject) getIntent().getSerializableExtra("EXTRA_TOP_LOCATION");

        lbLocationTitle = (TextView)findViewById(R.id.lbLocation);
        lbCountry = (TextView)findViewById(R.id.lbCountry);
        imgSpecificFlag = (ImageView)findViewById(R.id.imgSpecificFlag);
        imgPhoto = (ImageView)findViewById(R.id.imgPhoto);

        gridView = (GridView)findViewById(R.id.gridView);
        photos = new ArrayList<>();

        lbLocationTitle.setText(location.getCityName());
        lbCountry.setText(location.getCountryName());
        imgSpecificFlag.setImageResource(location.getFlagResource());

        getDataFromFlickr();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FlickrResponse.FlickrPhoto photo = photos.get(position);

                Intent i = new Intent(SpecificLocationActivity.this, SinglePhoto.class);
                i.putExtra("PHOTO_URL", photo.getUrl_c());
                i.putExtra("PHOTO_TITLE", photo.getTitle());
                startActivity(i);
            }
        });

    }

    private void getDataFromFlickr()
    {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.flickr.com")
                .build();

        FlickrService service = restAdapter.create(FlickrService.class);
        service.getPhotosForLocation(location.getWoeid() , new Callback<FlickrResponse>() {
            @Override
            public void success(FlickrResponse flickrSearchResponse, Response response) {
                Log.d("DANIELLE_FLICKR", "success");

                if (flickrSearchResponse.getPhotos().getPhoto() != null &&
                        flickrSearchResponse.getPhotos().getPhoto().size() > 0){

                    photos = flickrSearchResponse.getPhotos().getPhoto();

                    PhotoAdapter adapter = new PhotoAdapter(SpecificLocationActivity.this);
                    gridView.setAdapter(adapter);

                }

            }//end on success

            @Override
            public void failure(RetrofitError retrofitError) {

                Log.d("DANIELLE_FLICKR", retrofitError.getLocalizedMessage());

            }//end on failure

        }//end callbacks

        );//end get photos request
    }

    private class PhotoAdapter extends BaseAdapter {
        private Context mContext;

        public PhotoAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return photos.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(
                        R.layout.cell_photo, null);
            }

            FlickrResponse.FlickrPhoto photo = photos.get(position);

            ImageView imgPicture = (ImageView)convertView.findViewById(R.id.imgPhoto);

            if (photo.getUrl_m() != null){
                Log.d("DANIELLE_FLICKR", photo.getImageURL());
                Picasso.with(mContext).load(photo.getImageURL()).into(imgPicture);
            } else {
                imgPicture.setBackgroundColor(Color.parseColor("#cc0000"));
            }

            return convertView;
        }//end get view

    }//end top image adapter
}
