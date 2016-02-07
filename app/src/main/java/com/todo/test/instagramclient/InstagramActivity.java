package com.todo.test.instagramclient;

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

import cz.msebera.android.httpclient.Header;

public class InstagramActivity extends AppCompatActivity {

    private static final String CLIENT_ID = "e05c462ebd86446ea48a5af73769b602";
    private ArrayList<InstagramPhoto> instagramPhotos;

    private InstagramPhotoAdapter instagramPhotoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram);

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
                                if(photoJson.getJSONObject("user").getString("profile_picture") != null ){
                                    photo.setProfilePic(photoJson.getJSONObject("user").getString("profile_picture"));
                                }

                                if(photoJson.getJSONObject("caption").getString("text") != null){
                                    photo.setCaption(photoJson.getJSONObject("caption").getString("text"));
                                }
                                if(photoJson.getJSONObject("images").getJSONObject("standard_resolution") != null && photoJson.getJSONObject("images").getJSONObject("standard_resolution").getString("url") != null){
                                    photo.setImageUrl(photoJson.getJSONObject("images").getJSONObject("standard_resolution").getString("url"));
                                    photo.setImageHeight(photoJson.getJSONObject("images").getJSONObject("standard_resolution").getInt("height"));
                                }

                                if(photoJson.getJSONObject("likes") != null){
                                    photo.setLikesCount(photoJson.getJSONObject("likes").getInt("count"));
                                }
                                instagramPhotos.add(photo);
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
                //callback here for datachange
                instagramPhotoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
}
