package com.arithfighter.not;

import com.arithfighter.not.file.FileLibrary;
import com.arithfighter.not.file.MyAssetProcessor;
import com.arithfighter.not.file.TextureNames;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

public class TextureService {
    private final Map<String, Texture[]> textureMap;
    private final String[] keys ={
            "widgets",
            "cards",
            "objects",
            "spriteSheet"
    };

    public TextureService(TextureAtlasService textureAtlasService, MyAssetProcessor myAssetProcessor) {
        textureMap = new HashMap<>();

        TextureNames textureNames = new TextureNames();

        int widgetLen = textureNames.getWidgetNames().length;
        int cardLen = textureNames.getCardFiles().length;
        int objectLen = textureNames.getObjectNames().length;
        int sheetLength = myAssetProcessor.getSpriteSheet().length;

        TextureRegion[][] textureRegions = {
                new TextureRegion[widgetLen],
                new TextureRegion[cardLen],
                new TextureRegion[objectLen]
        };

        Texture[][] textures = {
                new Texture[widgetLen],
                new Texture[cardLen],
                new Texture[objectLen],
                new Texture[sheetLength],
        };

        FileLibrary fileLibrary = new FileLibrary();
        Map<String, TextureAtlas> atlasMap = textureAtlasService.getAtlasMap();

        for (int j = 0; j < widgetLen; j++){
            TextureAtlas ta = atlasMap.get(fileLibrary.getAtlasFiles()[0]);
            textureRegions[0][j] = ta.findRegion("Button");
        }

        for (int j = 0; j < cardLen; j++){
            TextureAtlas ta = atlasMap.get(fileLibrary.getAtlasFiles()[1]);
            textureRegions[1][j] = ta.findRegion(textureNames.getCardFiles()[j]);
        }

        for (int j = 0; j < objectLen; j++){
            TextureAtlas ta = atlasMap.get(fileLibrary.getAtlasFiles()[0]);
            textureRegions[2][j] = ta.findRegion(textureNames.getObjectNames()[j]);
        }

        for (int i = 0;i<widgetLen;i++)
            textures[0][i] = textureRegions[0][i].getTexture();

        for (int i = 0;i<cardLen;i++)
            textures[1][i] = textureRegions[1][i].getTexture();

        for (int i = 0;i<objectLen;i++)
            textures[2][i] = textureRegions[2][i].getTexture();

        for (int i = 0;i<sheetLength;i++)
            textures[3][i] = myAssetProcessor.getSpriteSheet()[i];

        for (int i = 0; i< keys.length;i++)
            textureMap.put(keys[i], textures[i]);
    }

    public Map<String, Texture[]> getTextureMap() {
        return textureMap;
    }

    public String[] getKeys() {
        return keys;
    }
}
