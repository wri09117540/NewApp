package com.example.joshua.joshflickr;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Joshua on 11/05/2015.
 */
public interface FlickrService {
    @GET("/services/rest/?api_key=388f8897eb43fb01d206f25fb899e4a9&format=json&safe_search=1&content_type=1&woe_id=12056&extras=url_c%2Curl_m&per_page=40&method=flickr.photos.search&nojsoncallback=1")
    void getPhotosForLocation(@Query("woe_id") String woe_id, Callback<FlickrResponse> cb);
}
