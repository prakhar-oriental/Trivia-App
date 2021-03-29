package com.example.trivia.util;

import android.app.Activity;
import android.content.SharedPreferences;

public class Prefs {
    private SharedPreferences preferences;

    public Prefs(Activity activity) {
        this.preferences = activity.getPreferences(activity.MODE_PRIVATE);
    }
    public void saveHighScore(int score)
    {
        int currentScore = score;
        int lastScore = preferences.getInt("high score",0);
        if(currentScore>lastScore)
        {
            preferences.edit().putInt("high score",currentScore).apply();
        }
    }
    public int getHighScore()
    {
        return  preferences.getInt("high score",0);
    }
   /*  public void setState(int index)
     {
         preferences.edit().putInt("state_text",index).apply();
     }
      public int getState()
     {
         return  preferences.getInt("state_text",0);
     }*/
}
