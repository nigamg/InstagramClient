package com.todo.test.instagramclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.todo.test.instagramclient.R;
import com.todo.test.instagramclient.db.InstagramPhoto;

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
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);


        // Populate the data into the template view using the data object
        ivUser.setText(photo.getUserName()+double_dash);
        ivCaption.setText(photo.getCaption());
        ivLike.setText(likeImg+space+String.valueOf(photo.getLikesCount()) + likesText);
        ivPhoto.setImageResource(0);

        Picasso.with(getContext()).load(photo.getImageUrl()).into(ivPhoto);

        // Return the completed view to render on screen
        return convertView;
    }
}
