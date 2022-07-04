package com.arithfighter.not;

import com.arithfighter.not.file.FileLibrary;
import com.arithfighter.not.file.MyAssetProcessor;
import com.arithfighter.not.file.TextureNames;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

public class TextureService {
    private final Map<String, Texture[]> textureMap;

    public TextureService(TextureAtlasService textureAtlasService) {
        textureMap = new HashMap<>();

        TextureNames textureNames = new TextureNames();

        int widgetLen = textureNames.getWidgetNames().length;
        int cardLen = textureNames.getCardFiles().length;
        int objectLen = textureNames.getObjectNames().length;

        Texture[][] textures = {
                new Texture[widgetLen],
                new Texture[cardLen],
                new Texture[objectLen]
        };

        FileLibrary fileLibrary = new FileLibrary();

        for (int j = 0; j < widgetLen; j++)
            textures[0][j] = textureAtlasService.getAtlasMap().get(fileLibrary.getAtlasFiles()[0]).findRegion(textureNames.getWidgetNames()[j]).getTexture();

        for (int j = 0; j < cardLen; j++)
            textures[1][j] = textureAtlasService.getAtlasMap().get(fileLibrary.getAtlasFiles()[1]).findRegion(textureNames.getCardFiles()[j]).getTexture();

        for (int j = 0; j < objectLen; j++)
            textures[2][j] = textureAtlasService.getAtlasMap().get(fileLibrary.getAtlasFiles()[2]).findRegion(textureNames.getObjectNames()[j]).getTexture();


        for (int i = 0; i<fileLibrary.getAtlasFiles().length;i++)
            textureMap.put(fileLibrary.getAtlasFiles()[i], textures[i]);
    }

    public Map<String, Texture[]> getTextureMap() {
        return textureMap;
    }
}
