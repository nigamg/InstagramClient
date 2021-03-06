package com.todo.test.instagramclient.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by workboard on 2/3/16.
 */
public class InstagramPhoto {

    private String userName;
    private String caption;
    private String imageUrl;
    private int imageHeight;
    private long likesCount;
    private String profilePic;
    private long picTime;
    private int numberOfComments;
    private ArrayList<String> comments;

    public long getPicTime() {
        return picTime;
    }

    public void setPicTime(long picTime) {
        this.picTime = picTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public long getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(long likesCount) {
        this.likesCount = likesCount;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
    public int getNumberOfComments() {
        return numberOfComments;
    }

    public void setNumberOfComments(int numberOfComments) {
        this.numberOfComments = numberOfComments;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }


}
