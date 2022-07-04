package com.arithfighter.not;

import com.arithfighter.not.file.FileLibrary;
import com.arithfighter.not.file.MyAssetProcessor;
import com.arithfighter.not.file.TextureNames;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextureService {
    private final Map<String, TextureRegion[]> textureRegionMap;
    private final List<Texture> animateSheetList;
    private final String[] keys ={
            "widgets",
            "cards",
            "objects"
    };

    public TextureService(TextureAtlasService textureAtlasService, MyAssetProcessor myAssetProcessor) {
        textureRegionMap = new HashMap<>();

        animateSheetList = new ArrayList<>();

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
            TextureAtlas ta = atlasMap.get(fileLibrary.getAtlasFiles()[2]);
            textureRegions[2][j] = ta.findRegion(textureNames.getObjectNames()[j]);
        }

        for (int i = 0;i<sheetLength;i++)
            animateSheetList.add(myAssetProcessor.getSpriteSheet()[i]);

        for (int i = 0; i< keys.length;i++)
            textureRegionMap.put(keys[i], textureRegions[i]);
    }

    public List<Texture> getAnimateSheetList() {
        return animateSheetList;
    }

    public Map<String, TextureRegion[]> getTextureRegionMap() {
        return textureRegionMap;
    }

    public String[] getKeys() {
        return keys;
    }
}
