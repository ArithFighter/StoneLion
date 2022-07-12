package com.arithfighter.not.file.audio;

import com.arithfighter.not.file.AssetAccessor;
import com.arithfighter.not.file.AssetNameLibrary;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioService {
    private final Sound[] sounds;
    private final Music[] music;

    public AudioService(AssetAccessor assetAccessor){
        AssetNameLibrary f = new AssetNameLibrary();

        sounds = assetAccessor.getSounds(f.getSoundPath());
        music = assetAccessor.getMusics(f.getMusicPath());
    }

    public Sound[] getSounds() {
        return sounds;
    }

    public Music[] getMusic() {
        return music;
    }
}
