package com.arithfighter.not;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GameSave {
    private final Preferences preferences;
    private final String[] optionKeys = {
            "sound volume",
            "music volume"
    };
    private final String[] highScoreKey = {
            "knight best",
            "ninja best",
            "hunter best",
            "paladin best",
            "warrior best"
    };

    public GameSave(){
        preferences = Gdx.app.getPreferences("Numbers-on-Table-data");

        initOptionSave();

        initHighScore();
    }

    private void initOptionSave(){
        for (String s:optionKeys){
            if (!preferences.contains(s))
                preferences.putInteger(s, 3);
        }
    }

    private void initHighScore(){
        for (String s:highScoreKey){
            if (!preferences.contains(s))
                preferences.putInteger(s, 99999);
        }
    }

    public Preferences getPreferences(){
        return preferences;
    }

    public String[] getOptionKeys(){
        return optionKeys;
    }

    public String[] getHighScoreKey() {
        return highScoreKey;
    }
}
