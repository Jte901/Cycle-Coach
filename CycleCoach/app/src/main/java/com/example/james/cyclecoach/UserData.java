package com.example.james.cyclecoach;

/**
 * Created by Matt on 4/3/2016.
 */
public class UserData {

    String name; //user's name
    int frequency; //user's goal for ride frequency
    int days; //days between each ride
    float distance; //user's goal for distance per ride
    boolean firstTime;

    public UserData() {
        name = "";
        frequency = 1;
        days = 1;
        distance = 1;
        firstTime = true;
    }
}
