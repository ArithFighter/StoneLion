package com.arithfighter.not.file;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class AssetAccessor {
    private final AssetManager assetManager;

    public AssetAccessor(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public Texture[] getTextures(String[] files) {
        int length = files.length;
        Texture[] array = new Texture[length];

        for (int i = 0; i < length; i++)
            array[i] = assetManager.get(files[i]);

        return array;
    }

    public Music[] getMusics(String[] files) {
        int length = files.length;
        Music[] audios = new Music[length];

        for (int i = 0; i < length; i++)
            audios[i] = assetManager.get(files[i]);

        return audios;
    }

    public Sound[] getSounds(String[] files) {
        int length = files.length;
        Sound[] audios = new Sound[length];

        for (int i = 0; i < length; i++)
            audios[i] = assetManager.get(files[i]);

        return audios;
    }
}
