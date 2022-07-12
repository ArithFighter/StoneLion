package com.arithfighter.not.audio;

import com.arithfighter.not.file.AssetAccessor;
import com.arithfighter.not.file.FileLibrary;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioService {
    private final Sound[] sounds;
    private final Music[] music;

    public AudioService(AssetAccessor assetAccessor){
        FileLibrary f = new FileLibrary();

        sounds = assetAccessor.getSounds(f.getSoundFiles());
        music = assetAccessor.getMusics(f.getMusicFiles());
    }

    public Sound[] getSounds() {
        return sounds;
    }

    public Music[] getMusic() {
        return music;
    }
}
