package com.arithfighter.not.file.texture;

import com.arithfighter.not.file.AssetNameLibrary;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

public class TextureGetter {
    private final TextureService textureService;
    private final TextureMapProducer guiMapProducer;

    public TextureGetter(TextureService textureService) {
        this.textureService = textureService;

        AssetNameLibrary assetNameLibrary = new AssetNameLibrary();

        guiMapProducer = new TextureMapProducer(
                assetNameLibrary.getTexturePathCollection()[0],
                textureService.getTextureMap().get(textureService.getKeys()[0])
        );
    }

    public Map<String, Texture> getGuiMap() {
        return guiMapProducer.getTextureMap();
    }

    public Texture[] getGUIs(){
        return textureService.getTextureMap().get(textureService.getKeys()[0]);
    }

    public Texture[] getCards(){
        return textureService.getTextureMap().get(textureService.getKeys()[1]);
    }

    public Texture[] getPanels(){
        return textureService.getTextureMap().get(textureService.getKeys()[2]);
    }

    public Texture[] getAnimations(){
        return textureService.getTextureMap().get(textureService.getKeys()[3]);
    }

    public Texture[] getObjects(){
        return textureService.getTextureMap().get(textureService.getKeys()[4]);
    }
}

class TextureMapProducer{
    private final Map<String, Texture> textureMap;

    public TextureMapProducer(String[] paths, Texture[] textures){
        textureMap = new HashMap<>();

        for (int i = 0;i<textures.length;i++)
            textureMap.put(paths[i], textures[i]);
    }

    public Map<String, Texture> getTextureMap() {
        return textureMap;
    }
}