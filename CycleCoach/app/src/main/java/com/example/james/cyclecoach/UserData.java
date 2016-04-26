package com.example.james.cyclecoach;

import java.io.Serializable;

/**
 * Created by Matt on 4/3/2016.
 */
public class UserData implements Serializable {

    String name; //user's name
    int frequency; //user's goal for ride frequency
    int days; //days between each ride
    float distance; //user's goal for distance per ride
    String personalGoals;
    boolean firstTime;
    String lance_state;


    public UserData() {
        name = "";
        frequency = 1;
        days = 1;
        distance = 1;
        firstTime = true;
        personalGoals = "";
        lance_state = "blue";
    }
}
