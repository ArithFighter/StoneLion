package com.arithfighter.not;

import com.arithfighter.not.file.FileLibrary;
import com.arithfighter.not.file.TextureNames;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.HashMap;
import java.util.Map;

public class TextureAtlasService {
    private final Map<String, TextureAtlas> atlasMap;

    public TextureAtlasService(TextureAtlas[] atlases) {
        atlasMap = new HashMap<>();

        FileLibrary fileLibrary = new FileLibrary();

        for (int i = 0; i < fileLibrary.getAtlasFiles().length; i++)
            atlasMap.put(fileLibrary.getAtlasFiles()[i], atlases[i]);
    }

    public Map<String, TextureAtlas> getAtlasMap() {
        return atlasMap;
    }
}