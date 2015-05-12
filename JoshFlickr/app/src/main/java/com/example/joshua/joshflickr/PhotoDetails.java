package com.example.joshua.joshflickr;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Joshua on 11/05/2015.
 */
public interface PhotoDetails {
    @GET("/services/rest/?api_key=388f8897eb43fb01d206f25fb899e4a9&format=json&method=flickr.photos.getinfo&nojsoncallback=1")
    void getPhotoDetails(@Query("photo_id") String photo_id, Callback<FlickrResponse> cb);

}
