package com.arithfighter.ccg.file;

public class FileLibrary {
    private final String[] textureFiles = {
            "widget/Card_template.png",
            "widget/BoardArea.png",
            "widget/sum-Display-block.png",
            "widget/numberBox.png",
            "widget/Energy-bar.png",
            "widget/white-block.png",
            "widget/Button1.png"
    };

    private final String[] cardFiles = {
            "cards/Number-re0.png",
            "cards/Number-1.png",
            "cards/Number-2.png",
            "cards/Number-3.png",
            "cards/Number-4.png",
            "cards/Number-5.png",
            "cards/Number-6.png",
            "cards/Number-7.png",
            "cards/Number-8.png",
            "cards/Number-9.png",
            "cards/Number-re12.png",
            "cards/Number-re15.png",
            "cards/Number-neg1.png",
            "cards/Number-neg3.png",
            "cards/Number-neg7.png",
            "cards/Number-max.png",
    };

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

    public String[] getTextureFiles(){
        return textureFiles;
    }

    public String[] getCardFiles(){
        return cardFiles;
    }

    public String[] getMusicFiles() {
        return musicFiles;
    }

    public String[] getSoundFiles() {
        return soundFiles;
    }
}
