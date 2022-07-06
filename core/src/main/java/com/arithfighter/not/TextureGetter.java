package com.arithfighter.not;

import com.badlogic.gdx.graphics.Texture;

public class TextureGetter {
    private final TextureService textureService;

    public TextureGetter(TextureService textureService) {
        this.textureService = textureService;
    }

    public Texture[] getGUIs(){
        return textureService.getTextures(textureService.getKeys()[0]);
    }

    public Texture[] getCards(){
        return textureService.getTextures(textureService.getKeys()[1]);
    }

    public Texture[] getPanels(){
        return textureService.getTextures(textureService.getKeys()[2]);
    }

    public Texture[] getAnimations(){
        return textureService.getTextures(textureService.getKeys()[3]);
    }

    public Texture[] getObjects(){
        return textureService.getTextures(textureService.getKeys()[4]);
    }
}
