package com.arithfighter.not;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GameSave {
    private final Preferences preferences;
    private final String[] optionKeys = {
            "sound volume",
            "music volume"
    };
    private final String[] TokenKey = {
            "knight tokens",
            "ninja tokens",
            "hunter tokens",
            "paladin tokens",
            "warrior tokens"
    };

    public GameSave(){
        preferences = Gdx.app.getPreferences("Stone-Lion-Data");

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
        for (String s: TokenKey){
            if (!preferences.contains(s))
                preferences.putInteger(s, 0);
        }
    }

    public Preferences getPreferences(){
        return preferences;
    }

    public String[] getOptionKeys(){
        return optionKeys;
    }

    public String[] getTokenKey() {
        return TokenKey;
    }
}
