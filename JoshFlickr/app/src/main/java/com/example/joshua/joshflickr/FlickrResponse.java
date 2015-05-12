package com.example.joshua.joshflickr;

import java.util.List;

/**
 * Created by Joshua on 11/05/2015.
 */
public class FlickrResponse {
    private FlickrPhotos photos;

    public FlickrPhotos getPhotos() {return photos;}

    public class FlickrPhotos
    {
        private List<FlickrPhoto> photo;

        public List<FlickrPhoto> getPhoto() {return photo;}
    }

    public class FlickrPhoto
    {
        private String id;
        private String title;
        private String url_c;
        private String url_m;

        private String latitude;
        private String longitude;

        public String getTitle() {
            return title;
        }

        public String getUrl_c() {
            return url_c;
        }

        public String getUrl_m() {
            return url_m;
        }

        public String getImageURL(){
            return url_m;
        }

        public String getID()
        {
            return id;
        }

        public String getId() {
            return id;
        }

        public String getLat() {
            return latitude;
        }

        public String getLong() {
            return longitude;
        }
    }
}

