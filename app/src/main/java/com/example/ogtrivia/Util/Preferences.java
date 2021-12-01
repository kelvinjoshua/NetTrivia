package com.example.ogtrivia.Util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    //Access to all shared preference methods
    public SharedPreferences preferences;

    //We pass our activity as the context
    public Preferences(Activity context) {
        //RETRIEVE PREFERENCES
        this.preferences = context.getPreferences(Context.MODE_PRIVATE);
    }

    public void saveHighest(int score){
        int currentScore = score;
        int previous = preferences.getInt("HIGHEST",0);
        //Compare previous to current,if previous is higher edit preferences
        if(currentScore >previous){
            preferences.edit().putInt("HIGHEST",currentScore).apply();
        }
    }

    public int getHighest(){
        int highestValue = preferences.getInt("HIGHEST",0);
        return highestValue;
    }

    public  void  saveState(int state){
        preferences.edit().putInt("QUEST",state).apply();

    }
    public int getState(){
        return preferences.getInt("QUEST",0);
    }

}
