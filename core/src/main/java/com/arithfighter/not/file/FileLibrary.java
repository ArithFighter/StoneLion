package com.arithfighter.not.file;

public class FileLibrary {
    private final String[] musicFiles = {
            "music/character-select.ogg",
            "music/theme.ogg",
            "music/game-complete.ogg"
    };

    private final String[] soundFiles = {
            "sound/accept.ogg",
            "sound/score.ogg",
            "sound/denied.ogg",
            "sound/select.ogg"
    };

    private final String[] sheetFiles = {
            "spritesheet/card-fade-in.png",
            "spritesheet/card-fade-out.png",
            "spritesheet/lion/stone-lion.png"
    };

    private final String[] atlasFiles = {
            "atlas/game-gui.atlas",
            "atlas/game-cards.atlas",
            "atlas/game-object.atlas"
    };

    public String[] getMusicFiles() {
        return musicFiles;
    }

    public String[] getSoundFiles() {
        return soundFiles;
    }

    public String[] getSheetFiles() {
        return sheetFiles;
    }

    public String[] getAtlasFiles() {
        return atlasFiles;
    }
}
