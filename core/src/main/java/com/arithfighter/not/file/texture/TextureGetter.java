package com.arithfighter.not.file.texture;

import com.arithfighter.not.file.AssetNameLibrary;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

public class TextureGetter {
    private final TextureService textureService;
    private final TextureMapProducer[] textureMapProducers;

    public TextureGetter(TextureService textureService) {
        this.textureService = textureService;

        AssetNameLibrary assetNameLibrary = new AssetNameLibrary();

        textureMapProducers = new TextureMapProducer[textureService.getKeys().length];

        for(int i =0; i< textureMapProducers.length;i++)
            textureMapProducers[i] = new TextureMapProducer(
                    assetNameLibrary.getTexturePathCollection()[i],
                textureService.getTextureMap().get(textureService.getKeys()[i])
            );
    }

    public Map<String, Texture> getGuiMap() {
        return textureMapProducers[0].getTextureMap();
    }

    public Map<String, Texture> getCardMap(){
        return textureMapProducers[1].getTextureMap();
    }

    public Map<String, Texture> getPanelMap(){
        return textureMapProducers[2].getTextureMap();
    }

    public Map<String, Texture> getAnimationMap(){
        return textureMapProducers[3].getTextureMap();
    }

    public Map<String, Texture> getObjectMap(){
        return textureMapProducers[4].getTextureMap();
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