package com.todo.test.instagramclient.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.todo.test.instagramclient.R;
import com.todo.test.instagramclient.db.InstagramPhoto;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by workboard on 2/3/16.
 */
public class InstagramPhotoAdapter extends ArrayAdapter<InstagramPhoto> {

    private final static String double_dash = " -- ";
    private final static String likesText = " likes";
    private final static String space = " ";
    private final static String likeImg = "\uD83D\uDC99";

    public InstagramPhotoAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position

        InstagramPhoto photo = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.photo_view, parent, false);
        }
        // instead of findviewbyid ===> user viewHolder
        // Lookup view for data population
        TextView ivUser = (TextView) convertView.findViewById(R.id.ivUser);
        TextView ivCaption = (TextView) convertView.findViewById(R.id.ivCaption);
        TextView ivLike = (TextView) convertView.findViewById(R.id.ivLike);
        TextView ivTime = (TextView) convertView.findViewById(R.id.ivTime);

        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        ImageView ivProfile = (ImageView) convertView.findViewById(R.id.ivProfilePic);


        // Populate the data into the template view using the data object
        ivUser.setText(photo.getUserName()+double_dash);
        ivCaption.setText(photo.getCaption());
        ivLike.setText(likeImg+space+String.valueOf(photo.getLikesCount()) + likesText);
        ivTime.setText("+" + getDays(photo.getPicTime())+" days");
        ivPhoto.setImageResource(0);

        Picasso.with(getContext()).load(photo.getImageUrl()).into(ivPhoto);
        Picasso.with(getContext()).load(photo.getProfilePic()).into(ivProfile);

        // Return the completed view to render on screen
        return convertView;
    }

    private int getDays(long picTime){
        Log.i("DEBUG", "+++++++++++++++"+picTime);
        Date picDateTime = new Date ();
        //multiply the timestampt with 1000 as java expects the time in milliseconds
        picDateTime.setTime(picTime*1000);

        Date currentDate = new Date ();
        Log.i("DEBUG", "+++++++---------++++++++"+System.currentTimeMillis());
        currentDate.setTime(System.currentTimeMillis());

        //To calculate the days difference between two dates
        int diffInDays = (int)( (currentDate.getTime() - picDateTime.getTime()) / (1000 * 60 * 60 * 24) );
        return Math.abs(diffInDays);
    }
}
