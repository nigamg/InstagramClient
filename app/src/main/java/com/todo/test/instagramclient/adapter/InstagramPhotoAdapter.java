package com.todo.test.instagramclient.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.todo.test.instagramclient.R;
import com.todo.test.instagramclient.db.InstagramPhoto;

import java.util.Date;
import java.util.List;

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
        TextView ivNoComments = (TextView) convertView.findViewById(R.id.ivNoComments);

        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        ImageView ivProfile = (ImageView) convertView.findViewById(R.id.ivProfilePic);


        // Populate the data into the template view using the data object
        ivUser.setText(photo.getUserName()+double_dash);
        ivCaption.setText(photo.getCaption());
        ivLike.setText(likeImg+space+String.valueOf(photo.getLikesCount()) + likesText);
        ivTime.setText("+" + getDays(photo.getPicTime())+" days");
        ivPhoto.setImageResource(0);

        if(photo.getNumberOfComments() != 0){
            ivNoComments.setText(photo.getNumberOfComments()+" comments");
        }else{
            ivNoComments.setText("");
        }

        Picasso.with(getContext()).load(photo.getImageUrl()).into(ivPhoto);

        // load round share for profile pic
        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.parseColor("#a9c5ac"))
                .borderWidthDp(3)
                .cornerRadiusDp(30)
                .oval(false)
                .build();

        Picasso.with(getContext())
                .load(photo.getProfilePic())
                .fit()
                .transform(transformation)
                .into(ivProfile);


        // Return the completed view to render on screen
        return convertView;
    }

    private int getDays(long picTime){
        //Log.i("DEBUG", "+++++++++++++++"+picTime);
        Date picDateTime = new Date ();
        //multiply the timestampt with 1000 as java expects the time in milliseconds
        picDateTime.setTime(picTime*1000);

        Date currentDate = new Date ();
        //Log.i("DEBUG", "+++++++---------++++++++"+System.currentTimeMillis());
        currentDate.setTime(System.currentTimeMillis());

        //To calculate the days difference between two dates
        int diffInDays = (int)( (currentDate.getTime() - picDateTime.getTime()) / (1000 * 60 * 60 * 24) );
        return Math.abs(diffInDays);
    }
}
