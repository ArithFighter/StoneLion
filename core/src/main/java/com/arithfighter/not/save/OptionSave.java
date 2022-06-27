package com.arithfighter.not.save;

import com.arithfighter.not.scene.OptionEvent;
import com.arithfighter.not.scene.scene.Option;
import com.badlogic.gdx.Preferences;

public class OptionSave {
    private final OptionEvent optionEvent;
    private final Preferences pref;
    private final String soundVolumeKey;
    private final String musicVolumeKey;

    public OptionSave(GameSave gameSave, Option option){
        optionEvent = option;
        pref = gameSave.getPreferences();

        soundVolumeKey = gameSave.getOptionKeys()[0];
        musicVolumeKey = gameSave.getOptionKeys()[1];
    }

    public void loadSave(){
        optionEvent.setSoundVolume(pref.getInteger(soundVolumeKey));
        optionEvent.setMusicVolume(pref.getInteger(musicVolumeKey));
    }

    public void saveOption(){
        pref.putInteger(soundVolumeKey, optionEvent.getSoundVolume());
        pref.putInteger(musicVolumeKey, optionEvent.getMusicVolume());
        pref.flush();
    }
}
