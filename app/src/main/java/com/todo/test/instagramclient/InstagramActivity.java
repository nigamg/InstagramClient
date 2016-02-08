package com.todo.test.instagramclient;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.todo.test.instagramclient.adapter.InstagramPhotoAdapter;
import com.todo.test.instagramclient.db.InstagramPhoto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class InstagramActivity extends AppCompatActivity {

    private static final String CLIENT_ID = "e05c462ebd86446ea48a5af73769b602";
    private ArrayList<InstagramPhoto> instagramPhotos;

    private InstagramPhotoAdapter instagramPhotoAdapter;

    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram);

        // Lookup the swipe container view

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchInstagramFeeds();
            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        instagramPhotos = new ArrayList<>();

        instagramPhotoAdapter = new InstagramPhotoAdapter(this, instagramPhotos);

        ListView lvPhotos = (ListView) findViewById(R.id.lvMovies);
        lvPhotos.setAdapter(instagramPhotoAdapter);

        fetchInstagramFeeds();
    }

    public void fetchInstagramFeeds(){
        String url =  "https://api.instagram.com/v1/media/popular?client_id="+CLIENT_ID;

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, null, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //super.onSuccess(statusCode, headers, response);
                Log.i("DEBUG", response.toString());
                JSONArray photosJson = null;
                try{
                    photosJson = response.getJSONArray("data");
                    for(int i = 0; i < photosJson.length(); i ++){

                        try{
                            JSONObject photoJson = photosJson.getJSONObject(i);
                            if(photoJson != null){
                                InstagramPhoto photo = new InstagramPhoto();
                                if(photoJson.getJSONObject("user").getString("username") != null){
                                    photo.setUserName(photoJson.getJSONObject("user").getString("username"));
                                }
                                try{
                                    if(photoJson.getJSONObject("user") != null && photoJson.getJSONObject("user").getString("profile_picture") != null ){
                                        photo.setProfilePic(photoJson.getJSONObject("user").getString("profile_picture"));
                                    }
                                }catch (Exception e){
                                    //// TODO: 2/7/16
                                }

                                if(photoJson.getJSONObject("caption").getString("text") != null){
                                    photo.setCaption(photoJson.getJSONObject("caption").getString("text"));
                                }
                                if(photoJson.getLong("created_time") != 0){
                                    photo.setPicTime(photoJson.getLong("created_time"));
                                }
                                if(photoJson.getJSONObject("comments") != null){
                                    try{
                                        JSONArray data = photoJson.getJSONObject("comments").getJSONArray("data");
                                        if(data != null){
                                            photo.setNumberOfComments(data.length());
                                            JSONObject j = (JSONObject) data.get(0);
                                            Log.i("DEBUG", "---comments--"+j.getString("text"));
                                            if(j.getJSONObject("from") != null){
                                                // set comments
                                                ArrayList<String> comments = new ArrayList<String>();
                                                comments.add(j.getJSONObject("from").getString("username"));
                                                comments.add(j.getString("text"));
                                                photo.setComments(comments);
                                            }
                                        }else{
                                            photo.setNumberOfComments(0);
                                        }
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                                if(photoJson.getJSONObject("images").getJSONObject("standard_resolution") != null && photoJson.getJSONObject("images").getJSONObject("standard_resolution").getString("url") != null){
                                    photo.setImageUrl(photoJson.getJSONObject("images").getJSONObject("standard_resolution").getString("url"));
                                    photo.setImageHeight(photoJson.getJSONObject("images").getJSONObject("standard_resolution").getInt("height"));
                                }

                                if(photoJson.getJSONObject("likes") != null){
                                    photo.setLikesCount(photoJson.getJSONObject("likes").getInt("count"));
                                }
                                instagramPhotos.add(photo);

                                // now let swipe container know, refresh is finished
                                swipeContainer.setRefreshing(false);
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
                //callback here for data-change
                instagramPhotoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
}
