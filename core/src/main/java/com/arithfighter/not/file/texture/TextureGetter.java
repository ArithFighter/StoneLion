package com.arithfighter.not.file.texture;

import com.badlogic.gdx.graphics.Texture;

public class TextureGetter {
    private final TextureService textureService;

    public TextureGetter(TextureService textureService) {
        this.textureService = textureService;
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
