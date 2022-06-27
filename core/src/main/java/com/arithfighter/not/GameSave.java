package com.arithfighter.not;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GameSave {
    private final Preferences preferences;
    private final String[] optionKeys = {
            "sound volume",
            "music volume"
    };

    public GameSave(){
        preferences = Gdx.app.getPreferences("Stone-Lion-Data");

        initOptionSave();
    }

    private void initOptionSave(){
        for (String s:optionKeys){
            if (!preferences.contains(s))
                preferences.putInteger(s, 3);
        }
    }

    public Preferences getPreferences(){
        return preferences;
    }

    public String[] getOptionKeys(){
        return optionKeys;
    }
}
