package com.arithfighter.not.file;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

public class MyAssetProcessor {
    private final AssetManager assetManager;
    private final FileLibrary fileLibrary;
    private final AssetAccessor accessor;
    private final Map<String, Texture> textureMap;
    private final Map<String, Music> musicMap;
    private final Map<String, Sound> soundMap;

    public MyAssetProcessor(){
        assetManager = new AssetManager();
        fileLibrary = new FileLibrary();
        accessor = new AssetAccessor(assetManager);
        textureMap = new HashMap<>();
        musicMap = new HashMap<>();
        soundMap = new HashMap<>();
    }

    public void load(){
        loadTextures(fileLibrary.getWidgetFiles());

        loadTextures(fileLibrary.getCardFiles());

        loadTextures(fileLibrary.getPanelFiles());

        loadTextures(fileLibrary.getSheetFiles());

        loadMusic();

        loadSound();

        assetManager.finishLoading();

        for (int i =0; i<getWidgets().length;i++)
            textureMap.put(fileLibrary.getWidgetFiles()[i], getWidgets()[i]);
        for (int i = 0; i<getCards().length;i++)
            textureMap.put(fileLibrary.getCardFiles()[i], getCards()[i]);
        for (int i =0; i<getPanels().length;i++)
            textureMap.put(fileLibrary.getPanelFiles()[i], getPanels()[i]);

        for (int i = 0;i<getMusics().length;i++)
            musicMap.put(fileLibrary.getMusicFiles()[i], getMusics()[i]);

        for (int i = 0;i<getSounds().length;i++)
            soundMap.put(fileLibrary.getSoundFiles()[i], getSounds()[i]);
    }

    public AssetManager getAssetManager(){
        return assetManager;
    }

    public Map<String, Texture> getTextureMap() {
        return textureMap;
    }

    public Map<String, Music> getMusicMap() {
        return musicMap;
    }

    public Map<String, Sound> getSoundMap() {
        return soundMap;
    }

    private void loadTextures(String[] files){
         for (String file : files)
            assetManager.load(file, Texture.class);
    }

    private void loadMusic(){
        for(String file : fileLibrary.getMusicFiles())
            assetManager.load(file, Music.class);
    }

    private void loadSound(){
        for(String file: fileLibrary.getSoundFiles())
            assetManager.load(file, Sound.class);
    }

    public void update(int millis){
        assetManager.update(millis);
    }

    public Texture[] getWidgets() {
        return accessor.getTextures(fileLibrary.getWidgetFiles());
    }

    public Texture[] getCards(){
        return accessor.getTextures(fileLibrary.getCardFiles());
    }

    public Texture[] getPanels(){
        return accessor.getTextures(fileLibrary.getPanelFiles());
    }

    public Music[] getMusics(){
        return accessor.getMusics(fileLibrary.getMusicFiles());
    }

    public Sound[] getSounds(){
        return accessor.getSounds(fileLibrary.getSoundFiles());
    }

    public Texture[] getSpriteSheet(){
        return accessor.getTextures(fileLibrary.getSheetFiles());
    }

    public void dispose(){
        assetManager.dispose();
    }
}

class AssetAccessor{
    private final AssetManager assetManager;

    public AssetAccessor(AssetManager assetManager){
        this.assetManager = assetManager;
    }

    public Texture[] getTextures(String[] files){
        int length = files.length;
        Texture[] array = new Texture[length];

        for (int i = 0; i < length; i++)
            array[i] = assetManager.get(files[i]);

        return array;
    }

    public Music[] getMusics(String[] files){
        int length = files.length;
        Music[] audios = new Music[length];

        for (int i = 0; i < length; i++)
            audios[i] = assetManager.get(files[i]);

        return audios;
    }

    public Sound[] getSounds(String[] files){
        int length = files.length;
        Sound[] audios = new Sound[length];

        for (int i = 0; i < length; i++)
            audios[i] = assetManager.get(files[i]);

        return audios;
    }
}
