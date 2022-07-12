package com.arithfighter.not.file.texture;

import com.arithfighter.not.file.AssetNameLibrary;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

public class TextureGetter {
    private final TextureService textureService;
    private final Map<String, Texture> guiMap;

    public TextureGetter(TextureService textureService) {
        this.textureService = textureService;

        AssetNameLibrary assetNameLibrary = new AssetNameLibrary();
        guiMap = new HashMap<>();
        Texture[] guiTextures = textureService.getTextureMap().get(textureService.getKeys()[0]);

        for (int i = 0;i<guiTextures.length;i++)
            guiMap.put(assetNameLibrary.getTexturePathCollection()[0][i], guiTextures[i]);
    }

    public Map<String, Texture> getGuiMap() {
        return guiMap;
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
